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
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JTextField txtCodigoPrestamo;
    private JButton btnBuscar;

    private JTextArea txtResumenPrestamo;

    private JTextField txtDocFuncionarioRecibe;
    private JComboBox<String> cmbEstadoEntrega;
    private JTextArea txtObservaciones;
    private JButton btnRegistrarDevolucion;
    private JButton btnLimpiar;

    private Prestamo prestamoActual;

    public PanelDevolverPrestamo(ControladorPrestamo controladorPrestamo) {
        this.controladorPrestamo = controladorPrestamo;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 15));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Devolver Préstamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

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

        panelNorte.add(panelBusqueda, BorderLayout.CENTER);
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
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        panelCentral.add(scrollObs, gbc);

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        panelSur.setBackground(Color.WHITE);

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

        panelSur.add(btnLimpiar);
        panelSur.add(btnRegistrarDevolucion);
        add(panelSur, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> buscarPrestamo());
        txtCodigoPrestamo.addActionListener(e -> buscarPrestamo());
        btnRegistrarDevolucion.addActionListener(e -> confirmarDevolucion());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private void buscarPrestamo() {
        String codigo = txtCodigoPrestamo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese el código del préstamo a devolver.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        prestamoActual = controladorPrestamo.buscarPrestamo(codigo);
        if (prestamoActual == null) {
            txtResumenPrestamo.setText("No se encontró ningún préstamo con el código '" + codigo + "'.");
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
            JOptionPane.showMessageDialog(this,
                    "Este préstamo ya se encuentra en estado " + estado + ".",
                    "Préstamo cerrado", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, respuesta, "Devolución Exitosa", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(this, respuesta, "Error en Devolución", JOptionPane.ERROR_MESSAGE);
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

    private void limpiarFormulario() {
        prestamoActual = null;
        txtCodigoPrestamo.setText("");
        txtResumenPrestamo.setText("Busque un código de préstamo para visualizar sus datos antes de registrar la devolución.");
        txtDocFuncionarioRecibe.setText("");
        cmbEstadoEntrega.setSelectedIndex(0);
        txtObservaciones.setText("");
        btnRegistrarDevolucion.setEnabled(false);
        txtCodigoPrestamo.requestFocus();
    }
}
