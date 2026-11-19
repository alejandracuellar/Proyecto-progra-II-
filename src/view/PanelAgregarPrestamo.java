package view;

import controller.ControladorPrestamo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PanelAgregarPrestamo extends JPanel {

    private final ControladorPrestamo controladorPrestamo;

    private final Color COLOR_AZUL = new Color(8, 36, 112);
    private final Color COLOR_EXITO = new Color(22, 128, 60);
    private final Color COLOR_ERROR = new Color(185, 28, 28);
    private final Color COLOR_NEUTRO = new Color(90, 90, 90);
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private JTextField txtCodigoFormato;
    private JComboBox<String> cmbTipoSolicitante;
    private JTextField txtCodigoSolicitante;
    private JLabel lblDatosSolicitante;

    private JTextField txtCodigoEquipo;
    private JLabel lblEstadoEquipo;
    private DefaultListModel<String> modeloListaEquipos;
    private JList<String> listaEquipos;

    private JTextField txtDestino;
    private JTextField txtFechaEjecucion;
    private JTextField txtFechaDevolucion;
    private JLabel lblEstadoFechas;

    private JTextField txtDocenteResponsable;
    private JLabel lblEstadoDocente;

    private JTextField txtFuncionarioEntrega;
    private JTextArea txtObservaciones;

    private JLabel lblMensajeGeneral;

    public PanelAgregarPrestamo(ControladorPrestamo controladorPrestamo) {
        this.controladorPrestamo = controladorPrestamo;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 8));
        setBorder(new EmptyBorder(15, 35, 15, 35));

        JLabel lblTitulo = new JLabel("Agregar Préstamo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(COLOR_AZUL);
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 6, 3, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int fila = 0;

        // 1. Código Formato Físico
        agregarEtiqueta(panelFormulario, "Código Formato Físico (*):", gbc, 0, fila);
        txtCodigoFormato = crearCampoTexto(18);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtCodigoFormato, gbc);
        gbc.gridwidth = 1;

        // 2. Tipo de Solicitante
        fila++;
        agregarEtiqueta(panelFormulario, "Tipo de Solicitante (*):", gbc, 0, fila);
        cmbTipoSolicitante = new JComboBox<>(new String[]{"ESTUDIANTE", "DOCENTE", "FUNCIONARIO"});
        cmbTipoSolicitante.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTipoSolicitante.setBackground(Color.WHITE);
        gbc.gridx = 1;
        panelFormulario.add(cmbTipoSolicitante, gbc);

        // 3. Código Solicitante + Botón Verificar + Feedback debajo
        fila++;
        agregarEtiqueta(panelFormulario, "Código Solicitante (*):", gbc, 0, fila);
        txtCodigoSolicitante = crearCampoTexto(15);
        gbc.gridx = 1;
        panelFormulario.add(txtCodigoSolicitante, gbc);

        JButton btnBuscarSolicitante = crearBotonSecundario("Verificar Solicitante");
        gbc.gridx = 2;
        panelFormulario.add(btnBuscarSolicitante, gbc);

        fila++;
        lblDatosSolicitante = crearLabelFeedback("Ingrese el código y presione 'Verificar Solicitante'.");
        agregarFeedbackEnFila(panelFormulario, lblDatosSolicitante, gbc, fila);

        // 4. Código Equipo + Botones + Feedback debajo
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
        lblEstadoEquipo = crearLabelFeedback(" ");
        agregarFeedbackEnFila(panelFormulario, lblEstadoEquipo, gbc, fila);

        // 5. Lista de Equipos Seleccionados (sin JScrollPane)
        fila++;
        agregarEtiqueta(panelFormulario, "Equipos Seleccionados:", gbc, 0, fila);
        modeloListaEquipos = new DefaultListModel<>();
        listaEquipos = new JList<>(modeloListaEquipos);
        listaEquipos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        listaEquipos.setVisibleRowCount(3);
        listaEquipos.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190)));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(listaEquipos, gbc);
        gbc.gridwidth = 1;

        // 6. Destino / Salón
        fila++;
        agregarEtiqueta(panelFormulario, "Destino / Salón (*):", gbc, 0, fila);
        txtDestino = crearCampoTexto(20);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtDestino, gbc);
        gbc.gridwidth = 1;

        // 7. Fecha Inicio y Devolución + Feedback debajo
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
        lblEstadoFechas = crearLabelFeedback(" ");
        agregarFeedbackEnFila(panelFormulario, lblEstadoFechas, gbc, fila);

        // 8. Cód. Docente Responsable + Feedback debajo
        fila++;
        agregarEtiqueta(panelFormulario, "Cód. Docente Responsable (si aplica):", gbc, 0, fila);
        txtDocenteResponsable = crearCampoTexto(18);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtDocenteResponsable, gbc);
        gbc.gridwidth = 1;

        fila++;
        lblEstadoDocente = crearLabelFeedback(" ");
        agregarFeedbackEnFila(panelFormulario, lblEstadoDocente, gbc, fila);

        // 9. Doc. Funcionario Entrega
        fila++;
        agregarEtiqueta(panelFormulario, "Doc. Funcionario Entrega:", gbc, 0, fila);
        txtFuncionarioEntrega = crearCampoTexto(18);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtFuncionarioEntrega, gbc);
        gbc.gridwidth = 1;

        // 10. Observaciones (sin JScrollPane)
        fila++;
        agregarEtiqueta(panelFormulario, "Observaciones:", gbc, 0, fila);
        txtObservaciones = new JTextArea(2, 20);
        txtObservaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        txtObservaciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190)),
                new EmptyBorder(4, 6, 4, 6)
        ));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(txtObservaciones, gbc);
        gbc.gridwidth = 1;

        // Directamente al centro sin JScrollPane
        add(panelFormulario, BorderLayout.CENTER);

        // Panel Sur: JLabel de resultado general + Botones
        JPanel panelSur = new JPanel(new BorderLayout(8, 4));
        panelSur.setBackground(Color.WHITE);

        lblMensajeGeneral = new JLabel(" ");
        lblMensajeGeneral.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panelSur.add(lblMensajeGeneral, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 2));
        panelBotones.setBackground(Color.WHITE);

        JButton btnLimpiar = crearBotonSecundario("Limpiar Formulario");
        JButton btnRegistrar = new JButton("Registrar Préstamo");
        btnRegistrar.setBackground(COLOR_AZUL);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setPreferredSize(new Dimension(180, 34));

        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegistrar);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        add(panelSur, BorderLayout.SOUTH);

        // --- EVENTOS CON LAMBDAS EN UNA SOLA LÍNEA ---
        btnBuscarSolicitante.addActionListener(e -> verificarSolicitante());
        btnAgregarEquipo.addActionListener(e -> agregarEquipoALista());
        btnQuitarEquipo.addActionListener(e -> quitarEquipoDeLista());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegistrar.addActionListener(e -> guardarPrestamo());

        txtDocenteResponsable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                verificarDocenteResponsable();
            }
        });
    }

    private void verificarSolicitante() {
        String tipo = (String) cmbTipoSolicitante.getSelectedItem();
        String codigo = txtCodigoSolicitante.getText().trim();

        if (codigo.isEmpty()) {
            mostrarFeedback(lblDatosSolicitante, "Error: Ingrese un código primero.", false);
            return;
        }

        Map<String, String> datos = controladorPrestamo.buscarDatosSolicitante(tipo, codigo);
        if (datos == null || datos.isEmpty()) {
            mostrarFeedback(lblDatosSolicitante, "El " + tipo.toLowerCase() + " con código '" + codigo + "' no existe en el sistema.", false);
        } else {
            mostrarFeedback(lblDatosSolicitante, "Encontrado: " + datos.get("nombreCompleto") + " | Doc: " + datos.get("documentoIdentidad"), true);
        }
    }

    private void verificarDocenteResponsable() {
        String codigoDoc = txtDocenteResponsable.getText().trim();
        if (codigoDoc.isEmpty()) {
            lblEstadoDocente.setText(" ");
            return;
        }

        Map<String, String> datos = controladorPrestamo.buscarDatosSolicitante("DOCENTE", codigoDoc);
        if (datos == null || datos.isEmpty()) {
            mostrarFeedback(lblEstadoDocente, "El docente con código '" + codigoDoc + "' no existe en el sistema.", false);
        } else {
            mostrarFeedback(lblEstadoDocente, "Docente verificado: " + datos.get("nombreCompleto"), true);
        }
    }

    private void agregarEquipoALista() {
        String cod = txtCodigoEquipo.getText().trim();
        if (cod.isEmpty()) {
            mostrarFeedback(lblEstadoEquipo, "Ingrese el código del equipo.", false);
            return;
        }
        if (modeloListaEquipos.contains(cod)) {
            mostrarFeedback(lblEstadoEquipo, "El equipo '" + cod + "' ya está en la lista.", false);
            return;
        }

        modeloListaEquipos.addElement(cod);
        txtCodigoEquipo.setText("");
        txtCodigoEquipo.requestFocus();
        mostrarFeedback(lblEstadoEquipo, "Equipo '" + cod + "' agregado a la lista.", true);
    }

    private void quitarEquipoDeLista() {
        int idx = listaEquipos.getSelectedIndex();
        if (idx != -1) {
            String eliminado = modeloListaEquipos.remove(idx);
            mostrarFeedback(lblEstadoEquipo, "Equipo '" + eliminado + "' quitado de la lista.", true);
        } else {
            mostrarFeedback(lblEstadoEquipo, "Seleccione un equipo de la lista para quitar.", false);
        }
    }

    private void guardarPrestamo() {
        lblEstadoFechas.setText(" ");
        lblMensajeGeneral.setText(" ");

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
            mostrarFeedback(lblEstadoFechas, "Formato de fecha inválido. Use: yyyy-MM-dd HH:mm (Ej: 2026-11-18 21:16)", false);
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
            limpiarCampos();
            mostrarFeedback(lblMensajeGeneral, respuesta, true);
        } else {
            mostrarFeedback(lblMensajeGeneral, respuesta, false);
        }
    }

    private void limpiarCampos() {
        txtCodigoFormato.setText("");
        txtCodigoSolicitante.setText("");
        lblDatosSolicitante.setText("Ingrese el código y presione 'Verificar Solicitante'.");
        lblDatosSolicitante.setForeground(COLOR_NEUTRO);

        txtCodigoEquipo.setText("");
        lblEstadoEquipo.setText(" ");
        modeloListaEquipos.clear();

        txtDestino.setText("");
        txtFechaEjecucion.setText(LocalDateTime.now().format(FORMATO_FECHA));
        txtFechaDevolucion.setText(LocalDateTime.now().plusHours(2).format(FORMATO_FECHA));
        lblEstadoFechas.setText(" ");

        txtDocenteResponsable.setText("");
        lblEstadoDocente.setText(" ");

        txtFuncionarioEntrega.setText("");
        txtObservaciones.setText("");
        lblMensajeGeneral.setText(" ");
    }

    private void mostrarFeedback(JLabel etiqueta, String mensaje, boolean esValido) {
        etiqueta.setText(mensaje);
        etiqueta.setForeground(esValido ? COLOR_EXITO : COLOR_ERROR);
    }

    private JLabel crearLabelFeedback(String textoInicial) {
        JLabel lbl = new JLabel(textoInicial);
        lbl.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lbl.setForeground(COLOR_NEUTRO);
        return lbl;
    }

    private void agregarFeedbackEnFila(JPanel panel, JLabel etiqueta, GridBagConstraints gbc, int fila) {
        Insets original = gbc.insets;
        gbc.insets = new Insets(0, 6, 4, 6);
        gbc.gridx = 1;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        panel.add(etiqueta, gbc);
        gbc.gridwidth = 1;
        gbc.insets = original;
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