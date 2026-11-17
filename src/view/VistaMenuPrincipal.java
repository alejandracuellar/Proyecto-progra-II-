package view;

import controller.ControladorMenu;
import controller.ControladorPrestamo;
import controller.ControladorRecurso;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VistaMenuPrincipal extends JFrame {

    private final ControladorMenu controladorMenu;

    private final Color COLOR_FONDO_DERECHO = Color.WHITE;
    private final Color COLOR_MENU_AZUL = new Color(8,36,112);
    private final Color COLOR_SUBMENU = new Color(243,243,243);

    private JPanel panelSubmenuCentral;
    private CardLayout cardLayoutCentral;

    private JPanel panelContenidoDerecho;
    private CardLayout cardLayoutDerecho;

    private PanelHistorialPrestamos panelHistorial;
    private PanelEliminarEquipo panelEliminarEquipo;

    public VistaMenuPrincipal(){
        this(new ControladorMenu());
    }

    public VistaMenuPrincipal(ControladorMenu controladorMenu){
        this.controladorMenu = controladorMenu;

        ControladorPrestamo ctrlPrestamo = controladorMenu.getControladorPrestamo();
        ControladorRecurso ctrlRecurso = controladorMenu.getControladorRecurso();
        this.panelHistorial = new PanelHistorialPrestamos(ctrlPrestamo);
        this.panelEliminarEquipo = new PanelEliminarEquipo(ctrlRecurso);

        setTitle("Sistema De Gestión De Prestamo De Audiovisuales");
        setSize(1150,720);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //panel izquierdo
        JPanel panelMenu = construirMenuLateral();
        add(panelMenu, BorderLayout.WEST);

        //panel central
        cardLayoutCentral = new CardLayout();
        panelSubmenuCentral = new JPanel(cardLayoutCentral);
        panelSubmenuCentral.setPreferredSize(new Dimension(240,0));
        panelSubmenuCentral.setBackground(COLOR_SUBMENU);
        panelSubmenuCentral.setBorder(BorderFactory.createMatteBorder(0,0,0,1,new Color(215,215,215)));

        panelSubmenuCentral.add(crearSubmenuPrestamos(), "SUB_PRESTAMOS");
        panelSubmenuCentral.add(crearSubmenuUsuarios(), "SUB_USUARIOS");
        panelSubmenuCentral.add(crearSubmenuEquipos(), "SUB_EQUIPOS");
        panelSubmenuCentral.add(crearSubmenuSanciones(), "SUB_SANCIONES");

        add(panelSubmenuCentral, BorderLayout.CENTER);

        //panel derecho
        cardLayoutDerecho = new CardLayout();
        panelContenidoDerecho = new JPanel(cardLayoutDerecho);
        panelContenidoDerecho.setPreferredSize(new Dimension(740,0));
        panelContenidoDerecho.setBackground(COLOR_FONDO_DERECHO);

        panelContenidoDerecho.add(new PanelAgregarPrestamo(ctrlPrestamo), "AGREGAR_PRESTAMO");
        panelContenidoDerecho.add(new PanelDevolverPrestamo(ctrlPrestamo), "DEVOLVER_PRESTAMO");
        panelContenidoDerecho.add(new PanelConsultarPrestamo(ctrlPrestamo), "CONSULTAR_PRESTAMO");
        panelContenidoDerecho.add(panelHistorial, "HISTORIAL_PRESTAMOS");

        panelContenidoDerecho.add(crearPanelPlaceholder("Agregar usuario"), "AGREGAR_USUARIO");

        panelContenidoDerecho.add(panelEliminarEquipo, "ELIMINAR_EQUIPO");

        panelContenidoDerecho.add(crearPanelPlaceholder("Agregar sanción"), "AGREGAR_SANCION");
        panelContenidoDerecho.add(crearPanelPlaceholder("Eliminar sanción"), "ELIMINAR_SANCION");
        panelContenidoDerecho.add(crearPanelPlaceholder("Ver sanciones"), "VER_SANCIONES");

        add(panelContenidoDerecho, BorderLayout.EAST);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                controladorMenu.cerrarAplicacion();
            }
        });
    }


    private JPanel construirMenuLateral(){
        JPanel menu = new JPanel(new BorderLayout());
        menu.setPreferredSize(new Dimension(200,0));
        menu.setBackground(COLOR_MENU_AZUL);
        menu.setBorder(new EmptyBorder(20,12,20,12));

        JLabel lblSistema = new JLabel("<html>SISTEMA DE GESTIÓN<br>AUDIOVISUALES</html>");
        lblSistema.setForeground(Color.WHITE);
        lblSistema.setFont(new Font("Segoe UI", Font.BOLD,14));
        lblSistema.setBorder(new EmptyBorder(0,8,25,0));
        menu.add(lblSistema, BorderLayout.NORTH);

        //botones
        JPanel panelOpciones = new JPanel(new GridLayout(8,1,0,8));
        panelOpciones.setBackground(COLOR_MENU_AZUL);

        JButton btnPrestamos = crearBotonMenu("Prestamos");
        JButton btnUsuarios = crearBotonMenu("Usuarios");
        JButton btnEquipos = crearBotonMenu("Equipos");
        JButton btnSanciones = crearBotonMenu("Sanciones");
        JButton btnGuardarSalir = crearBotonMenu("Guardar y salir");

        btnPrestamos.addActionListener(e -> {
            cardLayoutCentral.show(panelSubmenuCentral, "SUB_PRESTAMOS");
            cardLayoutDerecho.show(panelContenidoDerecho, "AGREGAR_PRESTAMO");
        });

        btnUsuarios.addActionListener(e -> {
            cardLayoutCentral.show(panelSubmenuCentral, "SUB_USUARIOS");
            cardLayoutDerecho.show(panelContenidoDerecho, "AGREGAR_USUARIO");
        });

        btnEquipos.addActionListener(e -> {
            panelEliminarEquipo.cargarTablaEquipos();
            cardLayoutCentral.show(panelSubmenuCentral, "SUB_EQUIPOS");
            cardLayoutDerecho.show(panelContenidoDerecho, "ELIMINAR_EQUIPO");
        });

        btnSanciones.addActionListener(e -> {
            cardLayoutCentral.show(panelSubmenuCentral, "SUB_SANCIONES");
            cardLayoutDerecho.show(panelContenidoDerecho, "AGREGAR_SANCION");
        });

        btnGuardarSalir.addActionListener(e -> controladorMenu.cerrarAplicacion());
        panelOpciones.add(btnPrestamos);
        panelOpciones.add(btnUsuarios);
        panelOpciones.add(btnEquipos);
        panelOpciones.add(btnSanciones);
        panelOpciones.add(new JLabel()); // Separador
        panelOpciones.add(btnGuardarSalir);

        menu.add(panelOpciones, BorderLayout.CENTER);
        return menu;
    }

    private JPanel crearSubmenuPrestamos() {
        JPanel panel = crearBaseSubmenu("Préstamos");

        JButton btnAgregar = crearBotonSubmenu("Agregar préstamo");
        JButton btnDevolver = crearBotonSubmenu("Devolver préstamo");
        JButton btnConsultar = crearBotonSubmenu("Consultar préstamo");
        JButton btnHistorial = crearBotonSubmenu("Historial de préstamos");

        btnAgregar.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "AGREGAR_PRESTAMO"));
        btnDevolver.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "DEVOLVER_PRESTAMO"));
        btnConsultar.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "CONSULTAR_PRESTAMO"));
        btnHistorial.addActionListener(e -> {
            panelHistorial.cargarHistorial();
            cardLayoutDerecho.show(panelContenidoDerecho, "HISTORIAL_PRESTAMOS");
        });

        panel.add(btnAgregar);
        panel.add(btnDevolver);
        panel.add(btnConsultar);
        panel.add(btnHistorial);
        return panel;
    }

    private JPanel crearSubmenuUsuarios() {
        JPanel panel = crearBaseSubmenu("Usuarios");

        JButton btnAgregar = crearBotonSubmenu("Agregar usuario");
        btnAgregar.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "AGREGAR_USUARIO"));

        panel.add(btnAgregar);
        return panel;
    }

    private JPanel crearSubmenuEquipos() {
        JPanel panel = crearBaseSubmenu("Equipos");

        JButton btnEliminar = crearBotonSubmenu("Eliminar equipo");
        btnEliminar.addActionListener(e -> {
            panelEliminarEquipo.cargarTablaEquipos();
            cardLayoutDerecho.show(panelContenidoDerecho, "ELIMINAR_EQUIPO");
        });

        panel.add(btnEliminar);
        return panel;
    }

    private JPanel crearSubmenuSanciones() {
        JPanel panel = crearBaseSubmenu("Sanciones");

        JButton btnAgregar = crearBotonSubmenu("Agregar sanción");
        JButton btnEliminar = crearBotonSubmenu("Eliminar sanción");
        JButton btnVer = crearBotonSubmenu("Ver sanciones");

        btnAgregar.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "AGREGAR_SANCION"));
        btnEliminar.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "ELIMINAR_SANCION"));
        btnVer.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "VER_SANCIONES"));

        panel.add(btnAgregar);
        panel.add(btnEliminar);
        panel.add(btnVer);
        return panel;
    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(COLOR_MENU_AZUL);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel crearBaseSubmenu(String titulo) {
        JPanel panel = new JPanel(new GridLayout(10, 1, 0, 10));
        panel.setBackground(COLOR_SUBMENU);
        panel.setBorder(new EmptyBorder(25, 15, 20, 15));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblTitulo.setForeground(COLOR_MENU_AZUL);
        panel.add(lblTitulo);
        return panel;
    }

    private JButton crearBotonSubmenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(35, 35, 35));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                new EmptyBorder(5, 12, 5, 12)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel crearPanelPlaceholder(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lbl.setForeground(COLOR_MENU_AZUL);
        panel.add(lbl, BorderLayout.NORTH);

        return panel;
    }

}
