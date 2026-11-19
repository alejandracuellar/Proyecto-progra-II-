package view;

import controller.ControladorAuditorio;
import controller.ControladorMenu;
import controller.ControladorPrestamo;
import controller.ControladorRecurso;
import controller.ControladorSancion;
import controller.ControladorUsuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VistaMenuPrincipal extends JFrame {

    private final ControladorMenu controladorMenu;

    private final Color COLOR_FONDO_DERECHO = Color.WHITE;
    private final Color COLOR_MENU_AZUL = new Color(8, 36, 112);
    private final Color COLOR_SUBMENU = new Color(243, 243, 243);

    private JPanel panelSubmenuCentral;
    private CardLayout cardLayoutCentral;

    private JPanel panelContenidoDerecho;
    private CardLayout cardLayoutDerecho;

    private PanelHistorialPrestamos panelHistorial;
    private PanelEliminarEquipo panelEliminarEquipo;
    private PanelReservarAuditorio panelReservarAuditorio;
    private PanelVerSanciones panelVerSanciones;
    private PanelEliminarSancion panelEliminarSancion;

    private JButton btnPrestamos;
    private JButton btnUsuarios;
    private JButton btnEquipos;
    private JButton btnAuditorios;
    private JButton btnSanciones;
    private JButton btnGuardarSalir;

    public VistaMenuPrincipal() {
        this(new ControladorMenu());
    }

    public VistaMenuPrincipal(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;

        ControladorPrestamo ctrlPrestamo = controladorMenu.getControladorPrestamo();
        ControladorRecurso ctrlRecurso = controladorMenu.getControladorRecurso();
        ControladorAuditorio ctrlAuditorio = controladorMenu.getControladorAuditorio();
        ControladorUsuario ctrlUsuario = controladorMenu.getControladorUsuario();
        ControladorSancion ctrlSancion = controladorMenu.getControladorSancion();

        this.panelHistorial = new PanelHistorialPrestamos(ctrlPrestamo);
        this.panelEliminarEquipo = new PanelEliminarEquipo(ctrlRecurso);
        this.panelReservarAuditorio = new PanelReservarAuditorio(ctrlAuditorio);
        this.panelVerSanciones = new PanelVerSanciones(ctrlSancion);
        this.panelEliminarSancion = new PanelEliminarSancion(ctrlSancion);

        setTitle("Sistema De Gestión De Prestamo De Audiovisuales");
        setSize(1150, 720);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(construirMenuLateral(), BorderLayout.WEST);

        cardLayoutCentral = new CardLayout();
        panelSubmenuCentral = new JPanel(cardLayoutCentral);
        panelSubmenuCentral.setPreferredSize(new Dimension(240, 0));
        panelSubmenuCentral.setBackground(COLOR_SUBMENU);
        panelSubmenuCentral.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(215, 215, 215)));

        panelSubmenuCentral.add(crearSubmenuPrestamos(), "SUB_PRESTAMOS");
        panelSubmenuCentral.add(crearSubmenuUsuarios(), "SUB_USUARIOS");
        panelSubmenuCentral.add(crearSubmenuEquipos(), "SUB_EQUIPOS");
        panelSubmenuCentral.add(crearSubmenuAuditorios(), "SUB_AUDITORIOS");
        panelSubmenuCentral.add(crearSubmenuSanciones(), "SUB_SANCIONES");

        add(panelSubmenuCentral, BorderLayout.CENTER);

        cardLayoutDerecho = new CardLayout();
        panelContenidoDerecho = new JPanel(cardLayoutDerecho);
        panelContenidoDerecho.setPreferredSize(new Dimension(740, 0));
        panelContenidoDerecho.setBackground(COLOR_FONDO_DERECHO);

        panelContenidoDerecho.add(new PanelAgregarPrestamo(ctrlPrestamo), "AGREGAR_PRESTAMO");
        panelContenidoDerecho.add(new PanelDevolverPrestamo(ctrlPrestamo), "DEVOLVER_PRESTAMO");
        panelContenidoDerecho.add(new PanelConsultarPrestamo(ctrlPrestamo), "CONSULTAR_PRESTAMO");
        panelContenidoDerecho.add(panelHistorial, "HISTORIAL_PRESTAMOS");

        panelContenidoDerecho.add(new PanelAgregarUsuario(ctrlUsuario), "AGREGAR_USUARIO");
        panelContenidoDerecho.add(panelEliminarEquipo, "ELIMINAR_EQUIPO");
        panelContenidoDerecho.add(panelReservarAuditorio, "RESERVAR_AUDITORIO");

        panelContenidoDerecho.add(panelVerSanciones, "VER_SANCIONES");
        panelContenidoDerecho.add(panelEliminarSancion, "ELIMINAR_SANCION");
        panelContenidoDerecho.add(new PanelConsultarSancion(ctrlSancion), "CONSULTAR_SANCION");

        add(panelContenidoDerecho, BorderLayout.EAST);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controladorMenu.cerrarAplicacion();
            }
        });
    }

    private JPanel construirMenuLateral() {
        JPanel menu = new JPanel(new BorderLayout());
        menu.setPreferredSize(new Dimension(200, 0));
        menu.setBackground(COLOR_MENU_AZUL);
        menu.setBorder(new EmptyBorder(20, 12, 20, 12));

        JLabel lblSistema = new JLabel("<html>SISTEMA DE GESTIÓN<br>AUDIOVISUALES</html>");
        lblSistema.setForeground(Color.WHITE);
        lblSistema.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSistema.setBorder(new EmptyBorder(0, 8, 25, 0));
        menu.add(lblSistema, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel(new GridLayout(8, 1, 0, 8));
        panelOpciones.setBackground(COLOR_MENU_AZUL);

        btnPrestamos = crearBotonMenu("Prestamos");
        btnUsuarios = crearBotonMenu("Usuarios");
        btnEquipos = crearBotonMenu("Equipos");
        btnAuditorios = crearBotonMenu("Auditorios");
        btnSanciones = crearBotonMenu("Sanciones");
        btnGuardarSalir = crearBotonMenu("Guardar y salir");

        btnPrestamos.addActionListener(e -> cambiarModulo("SUB_PRESTAMOS", "AGREGAR_PRESTAMO"));
        btnUsuarios.addActionListener(e -> cambiarModulo("SUB_USUARIOS", "AGREGAR_USUARIO"));
        btnEquipos.addActionListener(e -> abrirEliminarEquipo());
        btnAuditorios.addActionListener(e -> abrirReservarAuditorio());
        btnSanciones.addActionListener(e -> abrirVerSanciones());
        btnGuardarSalir.addActionListener(e -> controladorMenu.cerrarAplicacion());

        panelOpciones.add(btnPrestamos);
        panelOpciones.add(btnUsuarios);
        panelOpciones.add(btnEquipos);
        panelOpciones.add(btnAuditorios);
        panelOpciones.add(btnSanciones);
        panelOpciones.add(new JLabel());
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
        btnHistorial.addActionListener(e -> abrirHistorialPrestamos());

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
        btnEliminar.addActionListener(e -> abrirEliminarEquipo());

        panel.add(btnEliminar);
        return panel;
    }

    private JPanel crearSubmenuAuditorios() {
        JPanel panel = crearBaseSubmenu("Auditorios");

        JButton btnReservar = crearBotonSubmenu("Reservar auditorio");
        btnReservar.addActionListener(e -> abrirReservarAuditorio());

        panel.add(btnReservar);
        return panel;
    }

    private JPanel crearSubmenuSanciones() {
        JPanel panel = crearBaseSubmenu("Sanciones");

        JButton btnVer = crearBotonSubmenu("Ver sanciones");
        JButton btnEliminar = crearBotonSubmenu("Eliminar sanción");
        JButton btnConsultar = crearBotonSubmenu("Consultar estudiante");

        btnVer.addActionListener(e -> abrirVerSanciones());
        btnEliminar.addActionListener(e -> abrirEliminarSancion());
        btnConsultar.addActionListener(e -> cardLayoutDerecho.show(panelContenidoDerecho, "CONSULTAR_SANCION"));

        panel.add(btnVer);
        panel.add(btnEliminar);
        panel.add(btnConsultar);
        return panel;
    }

    private void cambiarModulo(String submenu, String panelDerecho) {
        cardLayoutCentral.show(panelSubmenuCentral, submenu);
        cardLayoutDerecho.show(panelContenidoDerecho, panelDerecho);
    }

    private void abrirHistorialPrestamos() {
        panelHistorial.cargarHistorial();
        cardLayoutDerecho.show(panelContenidoDerecho, "HISTORIAL_PRESTAMOS");
    }

    private void abrirEliminarEquipo() {
        panelEliminarEquipo.cargarTablaEquipos();
        cardLayoutCentral.show(panelSubmenuCentral, "SUB_EQUIPOS");
        cardLayoutDerecho.show(panelContenidoDerecho, "ELIMINAR_EQUIPO");
    }

    private void abrirReservarAuditorio() {
        panelReservarAuditorio.cargarAuditorios();
        cardLayoutCentral.show(panelSubmenuCentral, "SUB_AUDITORIOS");
        cardLayoutDerecho.show(panelContenidoDerecho, "RESERVAR_AUDITORIO");
    }

    private void abrirVerSanciones() {
        panelVerSanciones.cargarTablaSanciones();
        cardLayoutCentral.show(panelSubmenuCentral, "SUB_SANCIONES");
        cardLayoutDerecho.show(panelContenidoDerecho, "VER_SANCIONES");
    }

    private void abrirEliminarSancion() {
        panelEliminarSancion.cargarSancionesActivas();
        cardLayoutDerecho.show(panelContenidoDerecho, "ELIMINAR_SANCION");
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
}