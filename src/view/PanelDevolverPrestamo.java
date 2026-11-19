package view;

import controller.ControladorPrestamo;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class PanelDevolverPrestamo extends JPanel {
    private final ControladorPrestamo controladorPrestamo;

    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final Color COLOR_EXITO = new Color(22, 128, 60);
    private final Color COLOR_ERROR = new Color(185, 28, 28);
    private final Color COLOR_AVISO = new Color(180, 100, 10);
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JTextField txtCodigoPrestamo;
    private JButton btnBuscar;
    private JLabel lblEstadoBusqueda;

    private JTextArea txtResumenPrestamo;

    private JTextField txtDocFuncionarioRecibe;
    private JComboBox<String> cmbEstadoEntrega;
    private JTextArea txtObservaciones;
    private JLabel lblMensajeDevolucion;
    private JButton btnRegistrarDevolucion;
    private JButton btnLimpiar;

    private Prestamo prestamoActual;

    public PanelDevolverPrestamo(ControladorPrestamo controladorPrestamo) {
        this.controladorPrestamo = controladorPrestamo;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 12));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Devolver Préstamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBusquedaContenedor = new JPanel(new BorderLayout(5, 6));
        panelBusquedaContenedor.setBackground(Color.WHITE);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panelBusqueda.setBackground(new Color(245, 247, 250));
        panelBusqueda.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 230), 1));

        JLabel lblCodigo = new JLabel("Código de Préstamo (*):");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        txtCodigoPrestamo = new JTextField(18);
        txtCodigoPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnBuscar = new JButton("Buscar Préstamo");
        btnBuscar.setBackground(COLOR_AZUL);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBusqueda.add(lblCodigo);
        panelBusqueda.add(txtCodigoPrestamo);
        panelBusqueda.add(btnBuscar);

        lblEstadoBusqueda = new JLabel(" ");
        lblEstadoBusqueda.setFont(new Font("Segoe UI", Font.ITALIC, 13));

        panelBusquedaContenedor.add(panelBusqueda, BorderLayout.NORTH);
        panelBusquedaContenedor.add(lblEstadoBusqueda, BorderLayout.SOUTH);

        panelNorte.add(panelBusquedaContenedor, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 6, 8, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblSubtituloDetalle = new JLabel("Detalle del Préstamo:");
        lblSubtituloDetalle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSubtituloDetalle.setForeground(COLOR_AZUL);
        panelCentral.add(lblSubtituloDetalle, gbc);

        gbc.gridy = 1;
        txtResumenPrestamo = new JTextArea(5, 40);
        txtResumenPrestamo.setEditable(false);
        txtResumenPrestamo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtResumenPrestamo.setBackground(new Color(250, 250, 252));
        txtResumenPrestamo.setText("Busque un código de préstamo para visualizar sus datos antes de registrar la devolución.");
        txtResumenPrestamo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        panelCentral.add(txtResumenPrestamo, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblFuncionario = new JLabel("Doc. Funcionario que Recibe:");
        lblFuncionario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelCentral.add(lblFuncionario, gbc);

        gbc.gridx = 1;
        txtDocFuncionarioRecibe = new JTextField(20);
        txtDocFuncionarioRecibe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelCentral.add(txtDocFuncionarioRecibe, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblEstado = new JLabel("Estado de los Equipos Devueltos:");
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelCentral.add(lblEstado, gbc);

        gbc.gridx = 1;
        cmbEstadoEntrega = new JComboBox<>(new String[]{
                "Buen estado (Disponible)",
                "Con daños / Novedad (Mantenimiento)"
        });
        cmbEstadoEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbEstadoEntrega.setBackground(Color.WHITE);
        panelCentral.add(cmbEstadoEntrega, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblObs = new JLabel("Observaciones de Devolución:");
        lblObs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        panelCentral.add(lblObs, gbc);

        gbc.gridx = 1;
        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        txtObservaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        panelCentral.add(txtObservaciones, gbc);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout(8, 6));
        panelSur.setBackground(Color.WHITE);

        lblMensajeDevolucion = new JLabel(" ");
        lblMensajeDevolucion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelSur.add(lblMensajeDevolucion, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 4));
        panelBotones.setBackground(Color.WHITE);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(new Color(240, 240, 240));
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegistrarDevolucion = new JButton("Registrar Devolución");
        btnRegistrarDevolucion.setBackground(COLOR_AZUL);
        btnRegistrarDevolucion.setForeground(Color.WHITE);
        btnRegistrarDevolucion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrarDevolucion.setFocusPainted(false);
        btnRegistrarDevolucion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrarDevolucion.setPreferredSize(new Dimension(190, 36));
        btnRegistrarDevolucion.setEnabled(false);

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegistrarDevolucion);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarPrestamo());
        txtCodigoPrestamo.addActionListener(e -> buscarPrestamo());
        btnRegistrarDevolucion.addActionListener(e -> confirmarDevolucion());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void buscarPrestamo() {
        lblMensajeDevolucion.setText(" ");
        String codigo = txtCodigoPrestamo.getText().trim();

        if (codigo.isEmpty()) {
            mostrarFeedback(lblEstadoBusqueda, "Ingrese el código del préstamo a devolver.", COLOR_ERROR);
            txtCodigoPrestamo.requestFocus();
            return;
        }

        prestamoActual = controladorPrestamo.buscarPrestamo(codigo);
        if (prestamoActual == null) {
            mostrarFeedback(lblEstadoBusqueda, "No se encontró ningún préstamo con el código '" + codigo + "'.", COLOR_ERROR);
            txtResumenPrestamo.setText("Busque un código de préstamo válido para visualizar sus datos.");
            btnRegistrarDevolucion.setEnabled(false);
            return;
        }

        String solicitante = obtenerNombreSolicitante(prestamoActual.getUsuarioSolicitante());
        String equipos = (prestamoActual.getEquiposSolicitados() != null)
                ? prestamoActual.getEquiposSolicitados().stream()
                .map(r -> r.getCodigoEquipo() + " - " + r.getDescripcion())
                .collect(Collectors.joining(", "))
                : "Sin equipos";
        String fechaInicio = (prestamoActual.getFechaEjecucion() != null)
                ? prestamoActual.getFechaEjecucion().format(FORMATO_FECHA) : "-";
        String fechaEsperada = (prestamoActual.getFechaDevolucionEsperada() != null)
                ? prestamoActual.getFechaDevolucionEsperada().format(FORMATO_FECHA) : "-";
        String estado = (prestamoActual.getEstadoPrestamo() != null)
                ? prestamoActual.getEstadoPrestamo().name() : "-";

        txtResumenPrestamo.setText(
                "Código: " + prestamoActual.getCodigoFormatoFisico() + "   |   Estado Actual: " + estado + "\n" +
                        "Solicitante: " + solicitante + "\n" +
                        "Equipos: " + equipos + "\n" +
                        "Destino: " + prestamoActual.getDestinoUbicacion() + "\n" +
                        "Inicio: " + fechaInicio + "   |   Devolución Esperada: " + fechaEsperada
        );

        boolean cerrado = "DEVUELTO".equalsIgnoreCase(estado) || "CANCELADO".equalsIgnoreCase(estado);
        btnRegistrarDevolucion.setEnabled(!cerrado);
        if (cerrado) {
            mostrarFeedback(lblEstadoBusqueda, "Este préstamo ya se encuentra cerrado en estado " + estado + ".", COLOR_AVISO);
        } else {
            mostrarFeedback(lblEstadoBusqueda, "Préstamo activo encontrado. Complete los datos de recepción abajo.", COLOR_EXITO);
        }
    }

    private void confirmarDevolucion() {
        if (prestamoActual == null) return;

        String docFuncionario = txtDocFuncionarioRecibe.getText().trim();
        String observaciones = txtObservaciones.getText().trim();
        boolean algunEquipoDanado = (cmbEstadoEntrega.getSelectedIndex() == 1);

        String respuesta = controladorPrestamo.registrarDevolucionEquipo(
                prestamoActual.getCodigoFormatoFisico(),
                docFuncionario,
                observaciones,
                algunEquipoDanado
        );

        if (respuesta.startsWith("EXITO")) {
            limpiarFormulario();
            mostrarFeedback(lblMensajeDevolucion, respuesta, COLOR_EXITO);
        } else {
            mostrarFeedback(lblMensajeDevolucion, respuesta, COLOR_ERROR);
        }
    }

    private String obtenerNombreSolicitante(Usuario u) {
        if (u instanceof Estudiante) {
            return ((Estudiante) u).getNombreCompleto() + " (Estudiante)";
        } else if (u instanceof Docente) {
            return ((Docente) u).getNombreCompleto() + " (Docente)";
        } else if (u instanceof FuncionarioSolicitante) {
            return ((FuncionarioSolicitante) u).getNombreCompleto() + " (Funcionario)";
        }
        return "Desconocido";
    }

    private void mostrarFeedback(JLabel etiqueta, String mensaje, Color color) {
        etiqueta.setText(mensaje);
        etiqueta.setForeground(color);
    }

    private void limpiarFormulario() {
        prestamoActual = null;
        txtCodigoPrestamo.setText("");
        lblEstadoBusqueda.setText(" ");
        txtResumenPrestamo.setText("Busque un código de préstamo para visualizar sus datos antes de registrar la devolución.");
        txtDocFuncionarioRecibe.setText("");
        cmbEstadoEntrega.setSelectedIndex(0);
        txtObservaciones.setText("");
        lblMensajeDevolucion.setText(" ");
        btnRegistrarDevolucion.setEnabled(false);
        txtCodigoPrestamo.requestFocus();
    }
}