package view;

import controller.ControladorPrestamo;
import model.Docente;
import model.Estudiante;
import model.FuncionarioSolicitante;
import model.Prestamo;
import model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PanelHistorialPrestamos extends JPanel {

    private final ControladorPrestamo controladorPrestamo;

    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JComboBox<String> cmbFiltroEstado;
    private JButton btnActualizar;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JLabel lblTotalRegistros;

    public PanelHistorialPrestamos(ControladorPrestamo controladorPrestamo) {
        this.controladorPrestamo = controladorPrestamo;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 15));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Historial de Préstamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panelControles.setBackground(new Color(245, 247, 250));
        panelControles.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 230), 1));

        JLabel lblFiltro = new JLabel("Filtrar por estado:");
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 13));

        cmbFiltroEstado = new JComboBox<>(new String[]{
                "TODOS",
                "ACTIVO",
                "DEVUELTO",
                "CANCELADO"
        });
        cmbFiltroEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbFiltroEstado.setBackground(Color.WHITE);

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.setBackground(COLOR_AZUL);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelControles.add(lblFiltro);
        panelControles.add(cmbFiltroEstado);
        panelControles.add(btnActualizar);

        panelNorte.add(panelControles, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {
                "Cód. Préstamo", "Solicitante", "Equipos", "Destino",
                "Fecha Ejecución", "Devolución Esperada", "Devolución Real", "Estado"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaHistorial.setRowHeight(26);
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaHistorial.getTableHeader().setBackground(COLOR_AZUL);
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaHistorial);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.CENTER);

        lblTotalRegistros = new JLabel("Total de registros: 0");
        lblTotalRegistros.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblTotalRegistros.setForeground(new Color(90, 90, 90));
        add(lblTotalRegistros, BorderLayout.SOUTH);

        cmbFiltroEstado.addActionListener(e -> cargarHistorial());
        btnActualizar.addActionListener(e -> cargarHistorial());

        cargarHistorial();
    }

    public void cargarHistorial() {
        modeloTabla.setRowCount(0);
        String filtroSeleccionado = (String) cmbFiltroEstado.getSelectedItem();

        List<Prestamo> listaPrestamos = controladorPrestamo.listarTodos();
        int contador = 0;

        if (listaPrestamos != null) {
            for (Prestamo p : listaPrestamos) {
                String estadoActual = (p.getEstadoPrestamo() != null) ? p.getEstadoPrestamo().name() : "DESCONOCIDO";

                if ("TODOS".equals(filtroSeleccionado) || estadoActual.equalsIgnoreCase(filtroSeleccionado)) {
                    agregarFila(p, estadoActual);
                    contador++;
                }
            }
        }

        lblTotalRegistros.setText("Mostrando " + contador + " préstamo(s).");
    }

    private void agregarFila(Prestamo p, String estado) {
        String solicitante = obtenerNombreSolicitante(p.getUsuarioSolicitante());
        String equipos = (p.getEquiposSolicitados() != null)
                ? p.getEquiposSolicitados().stream()
                .map(r -> r.getCodigoEquipo() + " (" + r.getDescripcion() + ")")
                .collect(Collectors.joining(", "))
                : "Sin equipos";

        String fechaInicio = (p.getFechaEjecucion() != null) ? p.getFechaEjecucion().format(FORMATO_FECHA) : "-";
        String fechaEsperada = (p.getFechaDevolucionEsperada() != null) ? p.getFechaDevolucionEsperada().format(FORMATO_FECHA) : "-";
        String fechaReal = (p.getFechaDevolucionReal() != null) ? p.getFechaDevolucionReal().format(FORMATO_FECHA) : "-";

        modeloTabla.addRow(new Object[]{
                p.getCodigoFormatoFisico(),
                solicitante,
                equipos,
                p.getDestinoUbicacion(),
                fechaInicio,
                fechaEsperada,
                fechaReal,
                estado
        });
    }

    private String obtenerNombreSolicitante(Usuario u) {
        if (u instanceof Estudiante) {
            return ((Estudiante) u).getNombreCompleto() + " (Est.)";
        } else if (u instanceof Docente) {
            return ((Docente) u).getNombreCompleto() + " (Doc.)";
        } else if (u instanceof FuncionarioSolicitante) {
            return ((FuncionarioSolicitante) u).getNombreCompleto() + " (Func.)";
        }
        return "Desconocido";
    }
}