package view;

import controller.ControladorSancion;
import model.Sancion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelVerSanciones extends JPanel {

    private final ControladorSancion controladorSancion;
    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JTable tablaSanciones;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;

    public PanelVerSanciones(ControladorSancion controladorSancion) {
        this.controladorSancion = controladorSancion;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Listado General de Sanciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);

        btnActualizar = new JButton("Actualizar Lista");
        btnActualizar.setBackground(Color.WHITE);
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelNorte.add(lblTitulo, BorderLayout.WEST);
        panelNorte.add(btnActualizar, BorderLayout.EAST);
        add(panelNorte, BorderLayout.NORTH);

        String[] columnas = {"ID Sanción", "Fecha Inicio", "Fecha Fin", "Motivo", "Strikes", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaSanciones = new JTable(modeloTabla);
        tablaSanciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaSanciones.setRowHeight(28);
        tablaSanciones.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaSanciones.getTableHeader().setBackground(COLOR_AZUL);
        tablaSanciones.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tablaSanciones);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);

        btnActualizar.addActionListener(e -> cargarTablaSanciones());

        cargarTablaSanciones();
    }

    public void cargarTablaSanciones() {
        modeloTabla.setRowCount(0);
        List<Sancion> lista = controladorSancion.listarTodasLasSanciones();
        if (lista != null) {
            for (Sancion s : lista) {
                Object[] fila = {
                        s.getIdSancion(),
                        s.getFechaInicio() != null ? s.getFechaInicio().format(FORMATO) : "-",
                        s.getFechaFin() != null ? s.getFechaFin().format(FORMATO) : "-",
                        s.getMotivo(),
                        s.getNumeroStrikes(),
                        s.isActiva() ? "ACTIVA" : "INACTIVA"
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}