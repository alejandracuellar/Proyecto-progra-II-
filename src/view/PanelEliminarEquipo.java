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

    private JTextField txtCodigoEquipo;
    private JButton btnEliminar;
    private JButton btnActualizar;

    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado;

    public PanelEliminarEquipo(ControladorRecurso controladorRecurso) {
        this.controladorRecurso = controladorRecurso;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout(10, 15));
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Eliminar Equipo Audiovisual");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);

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

        panelNorte.add(panelAccion, BorderLayout.CENTER);
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

        lblEstado = new JLabel("Seleccione un equipo de la tabla o escriba su código para eliminarlo.");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblEstado.setForeground(new Color(90, 90, 90));
        add(lblEstado, BorderLayout.SOUTH);

        tablaEquipos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaEquipos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String codigo = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
                    txtCodigoEquipo.setText(codigo);
                }
            }
        });

        btnActualizar.addActionListener(e -> cargarTablaEquipos());
        btnEliminar.addActionListener(e -> ejecutarEliminacion());

        cargarTablaEquipos();
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
            JOptionPane.showMessageDialog(this,
                    "Seleccione un equipo de la tabla o escriba su código.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar permanentemente el equipo con código '" + codigo + "'?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String respuesta = controladorRecurso.eliminarRecurso(codigo);
        if (respuesta.startsWith("EXITO")) {
            JOptionPane.showMessageDialog(this, respuesta, "Equipo Eliminado", JOptionPane.INFORMATION_MESSAGE);
            txtCodigoEquipo.setText("");
            cargarTablaEquipos();
        } else {
            JOptionPane.showMessageDialog(this, respuesta, "No se pudo eliminar", JOptionPane.ERROR_MESSAGE);
        }
    }
}