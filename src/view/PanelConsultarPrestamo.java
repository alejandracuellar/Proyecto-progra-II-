package view;

import controller.ControladorPrestamo;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PanelConsultarPrestamo extends JPanel {

    private final ControladorPrestamo controladorPrestamo;

    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JComboBox<String> cmbTipoBusqueda;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnLimpiar;

    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstadoBusqueda;

    public PanelConsultarPrestamo(ControladorPrestamo controladorPrestamo) {
        this.controladorPrestamo = controladorPrestamo;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 15));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Consultar Préstamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panelFiltros.setBackground(new Color(245, 247, 250));
        panelFiltros.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 230), 1));

        JLabel lblCriterio = new JLabel("Buscar por:");
        lblCriterio.setFont(new Font("Segoe UI", Font.BOLD, 13));

        cmbTipoBusqueda = new JComboBox<>(new String[]{
                "Código de Préstamo",
                "Código de Equipo"
        });
        cmbTipoBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTipoBusqueda.setBackground(Color.WHITE);

        lblCodigo = new JLabel("Código de Préstamo:");
        lblCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        txtCodigo = new JTextField(16);
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(COLOR_AZUL);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(Color.WHITE);
        btnLimpiar.setForeground(new Color(50, 50, 50));
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelFiltros.add(lblCriterio);
        panelFiltros.add(cmbTipoBusqueda);
        panelFiltros.add(lblCodigo);
        panelFiltros.add(txtCodigo);
        panelFiltros.add(btnBuscar);
        panelFiltros.add(btnLimpiar);

        panelNorte.add(panelFiltros, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {
                "Cód. Préstamo", "Solicitante", "Equipos", "Destino",
                "Fecha Ejecución", "Devolución Esperada", "Estado"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaResultados.setRowHeight(26);
        tablaResultados.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaResultados.getTableHeader().setBackground(COLOR_AZUL);
        tablaResultados.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaResultados);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.CENTER);

        lblEstadoBusqueda = new JLabel("Seleccione el criterio de búsqueda e ingrese el código.");
        lblEstadoBusqueda.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblEstadoBusqueda.setForeground(new Color(90, 90, 90));
        add(lblEstadoBusqueda, BorderLayout.SOUTH);

        cmbTipoBusqueda.addActionListener(e -> {
            String seleccion = (String) cmbTipoBusqueda.getSelectedItem();
            if ("Código de Préstamo".equals(seleccion)) {
                lblCodigo.setText("Código de Préstamo:");
            } else {
                lblCodigo.setText("Código de Equipo:");
            }
            limpiarResultados();
        });

        btnBuscar.addActionListener(e -> ejecutarBusqueda());
        txtCodigo.addActionListener(e -> ejecutarBusqueda());
        btnLimpiar.addActionListener(e -> limpiarResultados());
    }

    private void ejecutarBusqueda() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese un código para realizar la consulta.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTabla.setRowCount(0);
        String criterio = (String) cmbTipoBusqueda.getSelectedItem();

        if ("Código de Préstamo".equals(criterio)) {
            Prestamo prestamo = controladorPrestamo.buscarPrestamo(codigo);
            if (prestamo != null) {
                agregarFilaPrestamo(prestamo);
                lblEstadoBusqueda.setText("Se encontró 1 préstamo con código: " + codigo);
            } else {
                lblEstadoBusqueda.setText("No se encontró ningún préstamo con el código '" + codigo + "'.");
            }
        } else {
            List<Prestamo> lista = controladorPrestamo.buscarPrestamosPorEquipo(codigo);
            if (!lista.isEmpty()) {
                for (Prestamo p : lista) {
                    agregarFilaPrestamo(p);
                }
                lblEstadoBusqueda.setText("Se encontraron " + lista.size() + " préstamo(s) asociados al equipo '" + codigo + "'.");
            } else {
                lblEstadoBusqueda.setText("No hay préstamos registrados para el equipo '" + codigo + "'.");
            }
        }
    }

    private void agregarFilaPrestamo(Prestamo p) {
        String nombreSolicitante = obtenerNombreSolicitante(p.getUsuarioSolicitante());
        String equiposTexto = (p.getEquiposSolicitados() != null)
                ? p.getEquiposSolicitados().stream()
                .map(r -> r.getCodigoEquipo() + " (" + r.getDescripcion() + ")")
                .collect(Collectors.joining(", "))
                : "Sin equipos";

        String fechaInicio = (p.getFechaEjecucion() != null) ? p.getFechaEjecucion().format(FORMATO_FECHA) : "-";
        String fechaFin = (p.getFechaDevolucionEsperada() != null) ? p.getFechaDevolucionEsperada().format(FORMATO_FECHA) : "-";
        String estado = (p.getEstadoPrestamo() != null) ? p.getEstadoPrestamo().name() : "-";

        modeloTabla.addRow(new Object[]{
                p.getCodigoFormatoFisico(),
                nombreSolicitante,
                equiposTexto,
                p.getDestinoUbicacion(),
                fechaInicio,
                fechaFin,
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

    private void limpiarResultados() {
        txtCodigo.setText("");
        modeloTabla.setRowCount(0);
        lblEstadoBusqueda.setText("Seleccione el criterio de búsqueda e ingrese el código.");
        txtCodigo.requestFocus();
    }
}
