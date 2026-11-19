package view;

import controller.ControladorAuditorio;
import model.Auditorio;
import model.HorarioDisponibilidad;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelReservarAuditorio extends JPanel {

    private final ControladorAuditorio controladorAuditorio;
    private final Color COLOR_AZUL = new Color(8, 36, 112);

    private JComboBox<String> cmbAuditorios;
    private JLabel lblInfoAuditorio;
    private JButton btnActualizar;

    private JTable tablaCalendario;
    private DefaultTableModel modeloCalendario;

    private JComboBox<String> cmbDiaSemana;
    private JTextField txtHoraInicio;
    private JTextField txtHoraFin;
    private JButton btnReservar;
    private JLabel lblMensajeReserva;

    private final String[] DIAS = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

    public PanelReservarAuditorio(ControladorAuditorio controladorAuditorio) {
        this.controladorAuditorio = controladorAuditorio;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 12));
        setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel panelNorte = new JPanel(new BorderLayout(8, 10));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Reservar Auditorio — Calendario de Horarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBarra = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        panelBarra.setBackground(new Color(245, 247, 250));
        panelBarra.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 230), 1));

        cmbAuditorios = new JComboBox<>();
        cmbAuditorios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbAuditorios.setBackground(Color.WHITE);

        lblInfoAuditorio = new JLabel("Capacidad: - | Ubicación: -");
        lblInfoAuditorio.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblInfoAuditorio.setForeground(new Color(70, 70, 70));

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(Color.WHITE);
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBarra.add(new JLabel("Auditorio:"));
        panelBarra.add(cmbAuditorios);
        panelBarra.add(lblInfoAuditorio);
        panelBarra.add(btnActualizar);

        panelNorte.add(panelBarra, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {"Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        modeloCalendario = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCalendario = new JTable(modeloCalendario);
        tablaCalendario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCalendario.setRowHeight(28);
        tablaCalendario.setCellSelectionEnabled(true);
        tablaCalendario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaCalendario.getTableHeader().setBackground(COLOR_AZUL);
        tablaCalendario.getTableHeader().setForeground(Color.WHITE);
        tablaCalendario.setDefaultRenderer(Object.class, new RenderizadorCalendario());

        JScrollPane scrollCalendario = new JScrollPane(tablaCalendario);
        scrollCalendario.getViewport().setBackground(Color.WHITE);
        add(scrollCalendario, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout(5, 6));
        panelSur.setBackground(new Color(248, 249, 252));
        panelSur.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 230), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelControles.setBackground(new Color(248, 249, 252));

        cmbDiaSemana = new JComboBox<>(DIAS);
        cmbDiaSemana.setBackground(Color.WHITE);
        txtHoraInicio = new JTextField("08:00", 4);
        txtHoraFin = new JTextField("09:00", 4);

        btnReservar = new JButton("Reservar Horario");
        btnReservar.setBackground(COLOR_AZUL);
        btnReservar.setForeground(Color.WHITE);
        btnReservar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnReservar.setFocusPainted(false);
        btnReservar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelControles.add(new JLabel("Día:"));
        panelControles.add(cmbDiaSemana);
        panelControles.add(new JLabel("Inicio (HH:mm):"));
        panelControles.add(txtHoraInicio);
        panelControles.add(new JLabel("Fin (HH:mm):"));
        panelControles.add(txtHoraFin);
        panelControles.add(btnReservar);

        lblMensajeReserva = new JLabel(" ");
        lblMensajeReserva.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensajeReserva.setBorder(new EmptyBorder(0, 6, 0, 0));

        panelSur.add(panelControles, BorderLayout.NORTH);
        panelSur.add(lblMensajeReserva, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

        cmbAuditorios.addActionListener(e -> refrescarCalendario());
        btnActualizar.addActionListener(e -> cargarAuditorios());
        btnReservar.addActionListener(e -> ejecutarReserva());
        tablaCalendario.getSelectionModel().addListSelectionListener(e -> seleccionarCeldaCalendario());
        tablaCalendario.getColumnModel().getSelectionModel().addListSelectionListener(e -> seleccionarCeldaCalendario());

        cargarAuditorios();
    }

    public void cargarAuditorios() {
        cmbAuditorios.removeAllItems();
        List<Auditorio> lista = controladorAuditorio.listarTodos();
        if (lista != null && !lista.isEmpty()) {
            for (Auditorio a : lista) {
                cmbAuditorios.addItem(a.getNombreAuditorio());
            }
        }
        refrescarCalendario();
    }

    public void refrescarCalendario() {
        modeloCalendario.setRowCount(0);
        String nombreAuditorio = (String) cmbAuditorios.getSelectedItem();
        Auditorio auditorio = (nombreAuditorio != null) ? controladorAuditorio.buscarAuditorio(nombreAuditorio) : null;

        if (auditorio == null) {
            lblInfoAuditorio.setText("No hay auditorios registrados.");
        } else {
            lblInfoAuditorio.setText("Capacidad: " + auditorio.getCapacidad() + " pers. | Ubicación: " + auditorio.getUbicacion());
        }

        List<HorarioDisponibilidad> horarios = (auditorio != null) ? auditorio.getHorariosDisponibles() : null;

        for (int hora = 7; hora <= 20; hora++) {
            Object[] fila = new Object[7];
            fila[0] = String.format("%02d:00", hora);

            for (int d = 0; d < DIAS.length; d++) {
                fila[d + 1] = evaluarEstadoCelda(horarios, DIAS[d], hora, auditorio != null);
            }
            modeloCalendario.addRow(fila);
        }
    }

    private String evaluarEstadoCelda(List<HorarioDisponibilidad> horarios, String dia, int horaBloque, boolean hayAuditorio) {
        if (!hayAuditorio) return "-";
        if (horarios != null) {
            for (HorarioDisponibilidad h : horarios) {
                if (h.isEstaOcupado() && h.getDiaSemana() != null && h.getDiaSemana().equalsIgnoreCase(dia)) {
                    int inicio = parsearHora(h.getHoraInicio());
                    int fin = parsearHora(h.getHoraFin());
                    if (horaBloque >= inicio && horaBloque < fin) {
                        return "OCUPADO (" + h.getHoraInicio() + "-" + h.getHoraFin() + ")";
                    }
                }
            }
        }
        return "Disponible";
    }

    private int parsearHora(String horaTexto) {
        try {
            return Integer.parseInt(horaTexto.split(":")[0].trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private void seleccionarCeldaCalendario() {
        int fila = tablaCalendario.getSelectedRow();
        int col = tablaCalendario.getSelectedColumn();
        if (fila >= 0 && col >= 1) {
            lblMensajeReserva.setText(" ");
            String diaSeleccionado = DIAS[col - 1];
            cmbDiaSemana.setSelectedItem(diaSeleccionado);
            int horaSeleccionada = 7 + fila;

            txtHoraInicio.setText(String.format("%02d:00", horaSeleccionada));
            txtHoraFin.setText(String.format("%02d:00", horaSeleccionada + 1));
        }
    }

    private void ejecutarReserva() {
        String nombreAuditorio = (String) cmbAuditorios.getSelectedItem();
        String dia = (String) cmbDiaSemana.getSelectedItem();
        String horaInicio = txtHoraInicio.getText().trim();
        String horaFin = txtHoraFin.getText().trim();

        String respuesta = controladorAuditorio.reservarAuditorio(nombreAuditorio, dia, horaInicio, horaFin);

        if (respuesta.startsWith("EXITO")) {
            refrescarCalendario();
            lblMensajeReserva.setText(respuesta);
            lblMensajeReserva.setForeground(new Color(22, 128, 60));
        } else {
            lblMensajeReserva.setText(respuesta);
            lblMensajeReserva.setForeground(new Color(185, 28, 28));
        }
    }

    private static class RenderizadorCalendario extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String texto = (value != null) ? value.toString() : "";
            setHorizontalAlignment(SwingConstants.CENTER);

            if (column == 0) {
                c.setBackground(new Color(240, 242, 246));
                c.setForeground(Color.BLACK);
                setFont(getFont().deriveFont(Font.BOLD));
            } else if (texto.startsWith("OCUPADO")) {
                c.setBackground(new Color(255, 224, 224));
                c.setForeground(new Color(160, 20, 20));
            } else if ("Disponible".equals(texto)) {
                c.setBackground(isSelected ? new Color(215, 232, 255) : new Color(235, 250, 238));
                c.setForeground(new Color(25, 110, 45));
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(new Color(160, 160, 160));
            }
            return c;
        }
    }
}