package view;

import controller.ControladorUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelAgregarUsuario extends JPanel {

    private final ControladorUsuario controladorUsuario;
    private final Color COLOR_AZUL = new Color(8, 36, 112);

    private JComboBox<String> cmbTipoUsuario;
    private JTextField txtNombre;
    private JTextField txtDocumento;
    private JTextField txtTelefono;
    private JTextField txtCorreo;

    private JLabel lblCampoExtra1;
    private JTextField txtCampoExtra1;
    private JLabel lblCampoExtra2;
    private JTextField txtCampoExtra2;
    private JPasswordField txtContrasena;
    private JPanel panelExtra2Contenedor;
    private CardLayout cardExtra2;

    private JLabel lblMensajeEstado;
    private JButton btnRegistrar;
    private JButton btnLimpiar;

    public PanelAgregarUsuario(ControladorUsuario controladorUsuario) {
        this.controladorUsuario = controladorUsuario;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel lblTitulo = new JLabel("Registrar Nuevo Usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] tipos = {
                "Estudiante",
                "Docente",
                "Funcionario Solicitante",
                "Funcionario Audiovisuales"
        };
        cmbTipoUsuario = new JComboBox<>(tipos);
        cmbTipoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTipoUsuario.setBackground(Color.WHITE);

        txtNombre = crearCampoTexto();
        txtDocumento = crearCampoTexto();
        txtTelefono = crearCampoTexto();
        txtCorreo = crearCampoTexto();

        lblCampoExtra1 = new JLabel("Código Estudiantil:");
        lblCampoExtra1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtCampoExtra1 = crearCampoTexto();

        lblCampoExtra2 = new JLabel("Programa Académico:");
        lblCampoExtra2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtCampoExtra2 = crearCampoTexto();

        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190)),
                new EmptyBorder(6, 8, 6, 8)
        ));

        cardExtra2 = new CardLayout();
        panelExtra2Contenedor = new JPanel(cardExtra2);
        panelExtra2Contenedor.setBackground(Color.WHITE);
        panelExtra2Contenedor.add(txtCampoExtra2, "TEXTO");
        panelExtra2Contenedor.add(txtContrasena, "PASSWORD");

        agregarFila(panelFormulario, gbc, 0, crearEtiqueta("Tipo de Usuario:"), cmbTipoUsuario);
        agregarFila(panelFormulario, gbc, 1, crearEtiqueta("Nombre Completo:"), txtNombre);
        agregarFila(panelFormulario, gbc, 2, crearEtiqueta("Documento de Identidad:"), txtDocumento);
        agregarFila(panelFormulario, gbc, 3, crearEtiqueta("Teléfono:"), txtTelefono);
        agregarFila(panelFormulario, gbc, 4, crearEtiqueta("Correo Institucional:"), txtCorreo);
        agregarFila(panelFormulario, gbc, 5, lblCampoExtra1, txtCampoExtra1);
        agregarFila(panelFormulario, gbc, 6, lblCampoExtra2, panelExtra2Contenedor);

        JPanel contenedorNorteForm = new JPanel(new BorderLayout());
        contenedorNorteForm.setBackground(Color.WHITE);
        contenedorNorteForm.add(panelFormulario, BorderLayout.NORTH);
        add(contenedorNorteForm, BorderLayout.CENTER);

        // Panel inferior con JLabel de feedback y botones
        JPanel panelSur = new JPanel(new BorderLayout(10, 8));
        panelSur.setBackground(Color.WHITE);

        lblMensajeEstado = new JLabel(" ");
        lblMensajeEstado.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelSur.add(lblMensajeEstado, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        panelBotones.setBackground(Color.WHITE);

        btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.setBackground(new Color(240, 240, 240));
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setBackground(COLOR_AZUL);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegistrar);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

        cmbTipoUsuario.addActionListener(e -> actualizarCamposPorTipo());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnRegistrar.addActionListener(e -> guardarUsuario());

        actualizarCamposPorTipo();
    }

    private void actualizarCamposPorTipo() {
        String tipo = (String) cmbTipoUsuario.getSelectedItem();
        if (tipo == null) return;

        lblMensajeEstado.setText(" ");
        lblCampoExtra1.setVisible(true);
        txtCampoExtra1.setVisible(true);
        cardExtra2.show(panelExtra2Contenedor, "TEXTO");

        switch (tipo) {
            case "Estudiante":
                lblCampoExtra1.setText("Código Estudiantil:");
                lblCampoExtra2.setText("Programa Académico:");
                break;
            case "Docente":
                lblCampoExtra1.setText("Código Docente:");
                lblCampoExtra2.setText("Departamento:");
                break;
            case "Funcionario Solicitante":
                lblCampoExtra1.setText("Código Funcionario:");
                lblCampoExtra2.setText("Dependencia:");
                break;
            case "Funcionario Audiovisuales":
                lblCampoExtra1.setVisible(false);
                txtCampoExtra1.setVisible(false);
                lblCampoExtra2.setText("Contraseña de Acceso:");
                cardExtra2.show(panelExtra2Contenedor, "PASSWORD");
                break;
        }
    }

    private void guardarUsuario() {
        String tipo = (String) cmbTipoUsuario.getSelectedItem();
        String nombre = txtNombre.getText().trim();
        String documento = txtDocumento.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String extra1 = txtCampoExtra1.getText().trim();
        String extra2 = txtCampoExtra2.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        if (tipo == null) return;

        String respuesta;
        switch (tipo) {
            case "Estudiante":
                respuesta = controladorUsuario.registrarEstudiante(nombre, documento, telefono, correo, extra1, extra2);
                break;
            case "Docente":
                respuesta = controladorUsuario.registrarDocente(nombre, documento, telefono, correo, extra1, extra2);
                break;
            case "Funcionario Solicitante":
                respuesta = controladorUsuario.registrarFuncionarioSolicitante(nombre, documento, telefono, correo, extra1, extra2);
                break;
            case "Funcionario Audiovisuales":
                respuesta = controladorUsuario.registrarFuncionarioAudiovisuales(nombre, documento, telefono, correo, contrasena);
                break;
            default:
                respuesta = "Error: Tipo de usuario no válido.";
        }

        if (respuesta.startsWith("EXITO")) {
            limpiarCampos();
            mostrarFeedback(respuesta, true);
        } else {
            mostrarFeedback(respuesta, false);
        }
    }

    private void limpiarFormulario() {
        limpiarCampos();
        lblMensajeEstado.setText(" ");
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCampoExtra1.setText("");
        txtCampoExtra2.setText("");
        txtContrasena.setText("");
    }

    private void mostrarFeedback(String mensaje, boolean exito) {
        lblMensajeEstado.setText(mensaje);
        lblMensajeEstado.setForeground(exito ? new Color(22, 128, 60) : new Color(185, 28, 28));
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, JLabel etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(etiqueta, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(campo, gbc);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return lbl;
    }

    private JTextField crearCampoTexto() {
        JTextField txt = new JTextField(20);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190)),
                new EmptyBorder(6, 8, 6, 8)
        ));
        return txt;
    }
}