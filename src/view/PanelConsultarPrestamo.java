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
    private final Color COLOR_EXITO = new Color(22, 128, 60);
    private final Color COLOR_ERROR = new Color(185, 28, 28);
    private final Color COLOR_NEUTRO = new Color(90, 90, 90);
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
        setLayout(new BorderLayout(15, 12));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Consultar Préstamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBusquedaContenedor = new JPanel(new BorderLayout(5, 6));
        panelBusquedaContenedor.setBackground(Color.WHITE);

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

        lblEstadoBusqueda = new JLabel("Seleccione el criterio de búsqueda e ingrese el código.");
        lblEstadoBusqueda.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblEstadoBusqueda.setForeground(COLOR_NEUTRO);

        panelBusquedaContenedor.add(panelFiltros, BorderLayout.NORTH);
        panelBusquedaContenedor.add(lblEstadoBusqueda, BorderLayout.SOUTH);

        panelNorte.add(panelBusquedaContenedor, BorderLayout.CENTER);
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

        cmbTipoBusqueda.addActionListener(e -> cambiarCriterioBusqueda());
        btnBuscar.addActionListener(e -> ejecutarBusqueda());
        txtCodigo.addActionListener(e -> ejecutarBusqueda());
        btnLimpiar.addActionListener(e -> limpiarResultados());
    }

    private void cambiarCriterioBusqueda() {
        String seleccion = (String) cmbTipoBusqueda.getSelectedItem();
        lblCodigo.setText("Código de Préstamo".equals(seleccion) ? "Código de Préstamo:" : "Código de Equipo:");
        limpiarResultados();
    }

    private void ejecutarBusqueda() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarComentario("Por favor ingrese un código para realizar la consulta.", COLOR_ERROR);
            txtCodigo.requestFocus();
            return;
        }

        modeloTabla.setRowCount(0);
        String criterio = (String) cmbTipoBusqueda.getSelectedItem();

        if ("Código de Préstamo".equals(criterio)) {
            Prestamo prestamo = controladorPrestamo.buscarPrestamo(codigo);
            if (prestamo != null) {
                agregarFilaPrestamo(prestamo);
                mostrarComentario("Se encontró 1 préstamo con código: " + codigo, COLOR_EXITO);
            } else {
                mostrarComentario("No se encontró ningún préstamo con el código '" + codigo + "'.", COLOR_ERROR);
            }
        } else {
            List<Prestamo> lista = controladorPrestamo.buscarPrestamosPorEquipo(codigo);
            if (!lista.isEmpty()) {
                for (Prestamo p : lista) {
                    agregarFilaPrestamo(p);
                }
                mostrarComentario("Se encontraron " + lista.size() + " préstamo(s) asociados al equipo '" + codigo + "'.", COLOR_EXITO);
            } else {
                mostrarComentario("No hay préstamos registrados para el equipo '" + codigo + "'.", COLOR_ERROR);
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

    private void mostrarComentario(String mensaje, Color color) {
        lblEstadoBusqueda.setText(mensaje);
        lblEstadoBusqueda.setForeground(color);
    }

    private void limpiarResultados() {
        txtCodigo.setText("");
        modeloTabla.setRowCount(0);
        mostrarComentario("Seleccione el criterio de búsqueda e ingrese el código.", COLOR_NEUTRO);
        txtCodigo.requestFocus();
    }
}