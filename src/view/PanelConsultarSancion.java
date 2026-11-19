package view;

import controller.ControladorSancion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelConsultarSancion extends JPanel {

    private final ControladorSancion controladorSancion;
    private final Color COLOR_AZUL = new Color(8, 36, 112);

    private JTextField txtCodigoEstudiante;
    private JButton btnVerificar;
    private JLabel lblResultado;

    public PanelConsultarSancion(ControladorSancion controladorSancion) {
        this.controladorSancion = controladorSancion;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 20));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel lblTitulo = new JLabel("Consultar Estado de Sanción de Estudiante");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout(15, 20));
        panelCentro.setBackground(Color.WHITE);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        panelBusqueda.setBackground(new Color(245, 247, 250));
        panelBusqueda.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 230), 1));

        txtCodigoEstudiante = new JTextField(16);
        txtCodigoEstudiante.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnVerificar = new JButton("Verificar Estado");
        btnVerificar.setBackground(COLOR_AZUL);
        btnVerificar.setForeground(Color.WHITE);
        btnVerificar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnVerificar.setFocusPainted(false);
        btnVerificar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBusqueda.add(new JLabel("Código Estudiantil:"));
        panelBusqueda.add(txtCodigoEstudiante);
        panelBusqueda.add(btnVerificar);

        lblResultado = new JLabel("Ingrese un código estudiantil para consultar sus strikes y estado.");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblResultado.setForeground(new Color(70, 70, 70));
        lblResultado.setBorder(new EmptyBorder(15, 10, 15, 10));

        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.add(panelBusqueda, BorderLayout.NORTH);
        panelContenedor.add(lblResultado, BorderLayout.CENTER);

        panelCentro.add(panelContenedor, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        btnVerificar.addActionListener(e -> consultarEstado());
    }

    private void consultarEstado() {
        String codigo = txtCodigoEstudiante.getText().trim();
        String respuesta = controladorSancion.verificarEstadoSancionEstudiante(codigo);

        lblResultado.setText(respuesta);
        if (respuesta.startsWith("SANCIONADO")) {
            lblResultado.setForeground(new Color(180, 30, 30));
        } else if (respuesta.startsWith("HABILITADO")) {
            lblResultado.setForeground(new Color(25, 120, 45));
        } else {
            lblResultado.setForeground(new Color(180, 100, 10));
        }
    }
}