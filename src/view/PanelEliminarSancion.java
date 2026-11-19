package view;

import controller.ControladorSancion;
import model.Sancion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelEliminarSancion extends JPanel {

    private final ControladorSancion controladorSancion;
    private final Color COLOR_AZUL = new Color(8, 36, 112);

    private JTable tablaActivas;
    private DefaultTableModel modeloTabla;
    private JTextField txtIdSancion;
    private JTextField txtCodigoEstudiante;
    private JButton btnLevantarSancion;
    private JLabel lblMensajeEstado;

    public PanelEliminarSancion(ControladorSancion controladorSancion) {
        this.controladorSancion = controladorSancion;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel lblTitulo = new JLabel("Levantar / Eliminar Sanción Manualmente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        add(lblTitulo, BorderLayout.NORTH);

        String[] columnas = {"ID Sanción", "Motivo", "Strikes", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaActivas = new JTable(modeloTabla);
        tablaActivas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaActivas.setRowHeight(28);
        tablaActivas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaActivas.getTableHeader().setBackground(COLOR_AZUL);
        tablaActivas.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tablaActivas);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout(5, 6));
        panelSur.setBackground(new Color(248, 249, 252));
        panelSur.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(215, 220, 230), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        panelControles.setBackground(new Color(248, 249, 252));

        txtIdSancion = new JTextField(12);
        txtCodigoEstudiante = new JTextField(12);

        btnLevantarSancion = new JButton("Quitar Sanción");
        btnLevantarSancion.setBackground(new Color(180, 35, 35));
        btnLevantarSancion.setForeground(Color.WHITE);
        btnLevantarSancion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLevantarSancion.setFocusPainted(false);
        btnLevantarSancion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelControles.add(new JLabel("ID Sanción:"));
        panelControles.add(txtIdSancion);
        panelControles.add(new JLabel("Código Estudiante:"));
        panelControles.add(txtCodigoEstudiante);
        panelControles.add(btnLevantarSancion);

        lblMensajeEstado = new JLabel(" ");
        lblMensajeEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensajeEstado.setBorder(new EmptyBorder(0, 12, 0, 0));

        panelSur.add(panelControles, BorderLayout.NORTH);
        panelSur.add(lblMensajeEstado, BorderLayout.SOUTH);
        add(panelSur, BorderLayout.SOUTH);

        tablaActivas.getSelectionModel().addListSelectionListener(e -> seleccionarFila());
        btnLevantarSancion.addActionListener(e -> ejecutarQuitarSancion());

        cargarSancionesActivas();
    }

    public void cargarSancionesActivas() {
        modeloTabla.setRowCount(0);
        List<Sancion> lista = controladorSancion.listarTodasLasSanciones();
        if (lista != null) {
            for (Sancion s : lista) {
                if (s.isActiva()) {
                    modeloTabla.addRow(new Object[]{
                            s.getIdSancion(),
                            s.getMotivo(),
                            s.getNumeroStrikes(),
                            "ACTIVA"
                    });
                }
            }
        }
    }

    private void seleccionarFila() {
        int fila = tablaActivas.getSelectedRow();
        if (fila >= 0) {
            lblMensajeEstado.setText(" ");
            txtIdSancion.setText(modeloTabla.getValueAt(fila, 0).toString());
        }
    }

    private void ejecutarQuitarSancion() {
        String idSancion = txtIdSancion.getText().trim();
        String codigo = txtCodigoEstudiante.getText().trim();

        String respuesta = controladorSancion.quitarSancionManualmente(idSancion, codigo);

        if (respuesta.startsWith("EXITO")) {
            txtIdSancion.setText("");
            txtCodigoEstudiante.setText("");
            cargarSancionesActivas();
            lblMensajeEstado.setText(respuesta);
            lblMensajeEstado.setForeground(new Color(22, 128, 60));
        } else {
            lblMensajeEstado.setText(respuesta);
            lblMensajeEstado.setForeground(new Color(185, 28, 28));
        }
    }
}