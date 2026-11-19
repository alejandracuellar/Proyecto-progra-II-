package view;

import controller.ControladorRecurso;
import model.Recurso;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelEliminarEquipo extends JPanel {

    private final ControladorRecurso controladorRecurso;
    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final Color COLOR_EXITO = new Color(22, 128, 60);
    private final Color COLOR_ERROR = new Color(185, 28, 28);
    private final Color COLOR_NEUTRO = new Color(90, 90, 90);

    private JTextField txtCodigoEquipo;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JLabel lblMensajeAccion;

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado;

    public PanelEliminarEquipo(ControladorRecurso controladorRecurso) {
        this.controladorRecurso = controladorRecurso;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 12));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 10));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Eliminar Equipo Audiovisual");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelAccionContenedor = new JPanel(new BorderLayout(5, 6));
        panelAccionContenedor.setBackground(Color.WHITE);

        JPanel panelAccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panelAccion.setBackground(new Color(245, 247, 250));
        panelAccion.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 230), 1));

        JLabel lblCodigo = new JLabel("Código del Equipo:");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        txtCodigoEquipo = new JTextField(16);
        txtCodigoEquipo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnEliminar = new JButton("Eliminar Equipo");
        btnEliminar.setBackground(new Color(180, 35, 35));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.setBackground(Color.WHITE);
        btnActualizar.setForeground(new Color(50, 50, 50));
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelAccion.add(lblCodigo);
        panelAccion.add(txtCodigoEquipo);
        panelAccion.add(btnEliminar);
        panelAccion.add(btnActualizar);

        lblMensajeAccion = new JLabel("Seleccione un equipo de la tabla o escriba su código para eliminarlo.");
        lblMensajeAccion.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblMensajeAccion.setForeground(COLOR_NEUTRO);

        panelAccionContenedor.add(panelAccion, BorderLayout.NORTH);
        panelAccionContenedor.add(lblMensajeAccion, BorderLayout.SOUTH);

        panelNorte.add(panelAccionContenedor, BorderLayout.CENTER);
        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {
                "Código", "Descripción", "Marca", "Categoría", "Estado Actual", "Requiere Aval"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaEquipos.setRowHeight(26);
        tablaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEquipos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaEquipos.getTableHeader().setBackground(COLOR_AZUL);
        tablaEquipos.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaEquipos);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        add(scrollTabla, BorderLayout.CENTER);

        lblEstado = new JLabel("Total de equipos en inventario: 0");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblEstado.setForeground(COLOR_NEUTRO);
        add(lblEstado, BorderLayout.SOUTH);

        // Eventos con lambdas en una sola línea
        tablaEquipos.getSelectionModel().addListSelectionListener(e -> seleccionarFilaEquipo(e.getValueIsAdjusting()));
        btnActualizar.addActionListener(e -> actualizarVista());
        btnEliminar.addActionListener(e -> ejecutarEliminacion());
        txtCodigoEquipo.addActionListener(e -> ejecutarEliminacion());

        cargarTablaEquipos();
    }

    private void seleccionarFilaEquipo(boolean ajustando) {
        if (!ajustando) {
            int filaSeleccionada = tablaEquipos.getSelectedRow();
            if (filaSeleccionada != -1) {
                String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
                txtCodigoEquipo.setText(codigo);
                mostrarFeedback("Equipo '" + codigo + "' seleccionado para eliminar.", COLOR_NEUTRO);
            }
        }
    }

    private void actualizarVista() {
        cargarTablaEquipos();
        mostrarFeedback("Lista de equipos actualizada.", COLOR_NEUTRO);
    }

    public void cargarTablaEquipos() {
        modeloTabla.setRowCount(0);
        List<Recurso> lista = controladorRecurso.listarTodos();

        if (lista != null) {
            for (Recurso r : lista) {
                String estado = (r.getEstadoRecurso() != null) ? r.getEstadoRecurso().name() : "-";
                String aval = r.isRequiereAutorizacionDocente() ? "Sí" : "No";

                modeloTabla.addRow(new Object[]{
                        r.getCodigoEquipo(),
                        r.getDescripcion(),
                        r.getMarca(),
                        r.getCategoria(),
                        estado,
                        aval
                });
            }
            lblEstado.setText("Total de equipos en inventario: " + lista.size());
        }
    }

    private void ejecutarEliminacion() {
        String codigo = txtCodigoEquipo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarFeedback("Seleccione un equipo de la tabla o escriba su código para eliminar.", COLOR_ERROR);
            txtCodigoEquipo.requestFocus();
            return;
        }

        String respuesta = controladorRecurso.eliminarRecurso(codigo);
        if (respuesta.startsWith("EXITO")) {
            txtCodigoEquipo.setText("");
            cargarTablaEquipos();
            mostrarFeedback(respuesta, COLOR_EXITO);
        } else {
            mostrarFeedback(respuesta, COLOR_ERROR);
        }
    }

    private void mostrarFeedback(String mensaje, Color color) {
        lblMensajeAccion.setText(mensaje);
        lblMensajeAccion.setForeground(color);
    }
}