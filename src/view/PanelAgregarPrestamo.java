package view;

import controller.ControladorPrestamo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanelAgregarPrestamo extends JPanel {

    private final ControladorPrestamo controladorPrestamo;

    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JTextField txtCodigoFormato;
    private JComboBox<String> cmbTipoSolicitante;
    private JTextField txtCodigoSolicitante;
    private JLabel lblDatosSolicitante;

    private JTextField txtCodigoEquipo;
    private DefaultListModel<String> modeloListaEquipos;
    private JList<String> listaEquipos;

    private JTextField txtDestino;
    private JTextField txtFechaEjecucion;
    private JTextField txtFechaDevolucion;
    private JTextField txtDocenteResponsable;
    private JTextField txtFuncionarioEntrega;
    private JTextArea txtObservaciones;

    public PanelAgregarPrestamo(ControladorPrestamo controladorPrestamo) {
        this.controladorPrestamo = controladorPrestamo;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 15));
        setBorder(new EmptyBorder(20, 35, 20, 35));

        JLabel lblTitulo = new JLabel("Agregar Préstamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int fila = 0;

        agregarEtiqueta(panelFormulario, "Código Formato Físico (*):", gbc, 0, fila);
        txtCodigoFormato = crearCampoTexto(18);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtCodigoFormato, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Tipo de Solicitante (*):", gbc, 0, fila);
        cmbTipoSolicitante = new JComboBox<>(new String[]{"ESTUDIANTE", "DOCENTE", "FUNCIONARIO"});
        cmbTipoSolicitante.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTipoSolicitante.setBackground(Color.WHITE);
        gbc.gridx = 1;
        panelFormulario.add(cmbTipoSolicitante, gbc);

        fila++;
        agregarEtiqueta(panelFormulario, "Código Solicitante (*):", gbc, 0, fila);
        txtCodigoSolicitante = crearCampoTexto(15);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigoSolicitante, gbc);

        JButton btnBuscarSolicitante = crearBotonSecundario("Verificar Solicitante");
        gbc.gridx = 2;
        panelFormulario.add(btnBuscarSolicitante, gbc);

        fila++;
        lblDatosSolicitante = new JLabel("Ingrese el código y presione 'Verificar Solicitante'.");
        lblDatosSolicitante.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblDatosSolicitante.setForeground(new Color(90, 90, 90));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(lblDatosSolicitante, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Código Equipo (*):", gbc, 0, fila);
        txtCodigoEquipo = crearCampoTexto(15);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigoEquipo, gbc);

        JPanel panelBotonesEquipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelBotonesEquipo.setBackground(Color.WHITE);
        JButton btnAgregarEquipo = crearBotonSecundario("+ Agregar");
        JButton btnQuitarEquipo = crearBotonSecundario("- Quitar");
        panelBotonesEquipo.add(btnAgregarEquipo);
        panelBotonesEquipo.add(btnQuitarEquipo);
        gbc.gridx = 2;
        panelFormulario.add(panelBotonesEquipo, gbc);

        fila++;
        agregarEtiqueta(panelFormulario, "Equipos Seleccionados:", gbc, 0, fila);
        modeloListaEquipos = new DefaultListModel<>();
        listaEquipos = new JList<>(modeloListaEquipos);
        listaEquipos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JScrollPane scrollLista = new JScrollPane(listaEquipos);
        scrollLista.setPreferredSize(new Dimension(250, 65));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(scrollLista, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Destino / Salón (*):", gbc, 0, fila);
        txtDestino = crearCampoTexto(20);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtDestino, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Fecha Inicio (yyyy-MM-dd HH:mm) (*):", gbc, 0, fila);
        txtFechaEjecucion = crearCampoTexto(18);
        txtFechaEjecucion.setText(LocalDateTime.now().format(FORMATO_FECHA));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtFechaEjecucion, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Devolución Esperada (yyyy-MM-dd HH:mm) (*):", gbc, 0, fila);
        txtFechaDevolucion = crearCampoTexto(18);
        txtFechaDevolucion.setText(LocalDateTime.now().plusHours(2).format(FORMATO_FECHA));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtFechaDevolucion, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Cód. Docente Responsable (si aplica):", gbc, 0, fila);
        txtDocenteResponsable = crearCampoTexto(18);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtDocenteResponsable, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Doc. Funcionario Entrega:", gbc, 0, fila);
        txtFuncionarioEntrega = crearCampoTexto(18);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtFuncionarioEntrega, gbc);
        gbc.gridwidth = 1;

        fila++;
        agregarEtiqueta(panelFormulario, "Observaciones:", gbc, 0, fila);
        txtObservaciones = new JTextArea(2, 20);
        txtObservaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtObservaciones.setLineWrap(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(scrollObs, gbc);
        gbc.gridwidth = 1;

        JScrollPane scrollFormulario = new JScrollPane(panelFormulario);
        scrollFormulario.setBorder(null);
        scrollFormulario.getViewport().setBackground(Color.WHITE);
        add(scrollFormulario, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        panelSur.setBackground(Color.WHITE);

        JButton btnLimpiar = crearBotonSecundario("Limpiar Formulario");
        JButton btnRegistrar = new JButton("Registrar Préstamo");
        btnRegistrar.setBackground(COLOR_AZUL);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setPreferredSize(new Dimension(180, 36));

        panelSur.add(btnLimpiar);
        panelSur.add(btnRegistrar);
        add(panelSur, BorderLayout.SOUTH);

        btnBuscarSolicitante.addActionListener(e -> verificarSolicitante());

        btnAgregarEquipo.addActionListener(e -> {
            String cod = txtCodigoEquipo.getText().trim();
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el código del equipo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!modeloListaEquipos.contains(cod)) {
                modeloListaEquipos.addElement(cod);
                txtCodigoEquipo.setText("");
                txtCodigoEquipo.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Ese equipo ya está en la lista.", "Duplicado", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnQuitarEquipo.addActionListener(e -> {
            int idx = listaEquipos.getSelectedIndex();
            if (idx != -1) {
                modeloListaEquipos.remove(idx);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegistrar.addActionListener(e -> guardarPrestamo());
    }

    private void verificarSolicitante() {
        String tipo = (String) cmbTipoSolicitante.getSelectedItem();
        String codigo = txtCodigoSolicitante.getText().trim();

        if (codigo.isEmpty()) {
            lblDatosSolicitante.setText("Error: Ingrese un código primero.");
            lblDatosSolicitante.setForeground(Color.RED);
            return;
        }

        Map<String, String> datos = controladorPrestamo.buscarDatosSolicitante(tipo, codigo);
        if (datos.isEmpty()) {
            lblDatosSolicitante.setText("No encontrado en el sistema.");
            lblDatosSolicitante.setForeground(Color.RED);
        } else {
            lblDatosSolicitante.setText("Encontrado: " + datos.get("nombreCompleto") + " | Doc: " + datos.get("documentoIdentidad"));
            lblDatosSolicitante.setForeground(new Color(0, 128, 0));
        }
    }

    private void guardarPrestamo() {
        String codigoFormato = txtCodigoFormato.getText().trim();
        String tipoSolicitante = (String) cmbTipoSolicitante.getSelectedItem();
        String codigoSolicitante = txtCodigoSolicitante.getText().trim();
        String destino = txtDestino.getText().trim();
        String docenteResp = txtDocenteResponsable.getText().trim();
        String funcionarioEnt = txtFuncionarioEntrega.getText().trim();
        String observaciones = txtObservaciones.getText().trim();

        List<String> codigosEquipos = new ArrayList<>();
        for (int i = 0; i < modeloListaEquipos.size(); i++) {
            codigosEquipos.add(modeloListaEquipos.getElementAt(i));
        }

        LocalDateTime fechaInicio;
        LocalDateTime fechaFin;
        try {
            fechaInicio = LocalDateTime.parse(txtFechaEjecucion.getText().trim(), FORMATO_FECHA);
            fechaFin = LocalDateTime.parse(txtFechaDevolucion.getText().trim(), FORMATO_FECHA);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido. Use: yyyy-MM-dd HH:mm (Ejemplo: 2026-11-16 14:30)",
                    "Error de Fecha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String respuesta = controladorPrestamo.registrarPrestamo(
                codigoFormato,
                tipoSolicitante,
                codigoSolicitante,
                codigosEquipos,
                destino,
                fechaInicio,
                fechaFin,
                docenteResp,
                funcionarioEnt,
                observaciones
        );

        if (respuesta.startsWith("EXITO")) {
            JOptionPane.showMessageDialog(this, respuesta, "Préstamo Registrado", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, respuesta, "No se pudo registrar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtCodigoFormato.setText("");
        txtCodigoSolicitante.setText("");
        lblDatosSolicitante.setText("Ingrese el código y presione 'Verificar Solicitante'.");
        lblDatosSolicitante.setForeground(new Color(90, 90, 90));
        txtCodigoEquipo.setText("");
        modeloListaEquipos.clear();
        txtDestino.setText("");
        txtFechaEjecucion.setText(LocalDateTime.now().format(FORMATO_FECHA));
        txtFechaDevolucion.setText(LocalDateTime.now().plusHours(2).format(FORMATO_FECHA));
        txtDocenteResponsable.setText("");
        txtFuncionarioEntrega.setText("");
        txtObservaciones.setText("");
    }

    private void agregarEtiqueta(JPanel panel, String texto, GridBagConstraints gbc, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(lbl, gbc);
    }

    private JTextField crearCampoTexto(int columnas) {
        JTextField txt = new JTextField(columnas);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return txt;
    }

    private JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(240, 240, 240));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}


