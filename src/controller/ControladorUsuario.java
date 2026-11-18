package controller;

import model.Docente;
import model.Estudiante;
import model.FuncionarioAudiovisuales;
import model.FuncionarioSolicitante;
import service.ServicioDocente;
import service.ServicioEstudiante;
import service.ServicioFuncionarioAudiovisuales;
import service.ServicioFuncionarioSolicitante;

import java.util.ArrayList;
import java.util.Map;

public class ControladorUsuario {

    private ServicioEstudiante servicioEstudiante;
    private ServicioDocente servicioDocente;
    private ServicioFuncionarioSolicitante servicioFuncionarioSolicitante;
    private ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales;

    public ControladorUsuario(ServicioEstudiante servicioEstudiante,
                              ServicioDocente servicioDocente,
                              ServicioFuncionarioSolicitante servicioFuncionarioSolicitante,
                              ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales) {
        this.servicioEstudiante = servicioEstudiante;
        this.servicioDocente = servicioDocente;
        this.servicioFuncionarioSolicitante = servicioFuncionarioSolicitante;
        this.servicioFuncionarioAudiovisuales = servicioFuncionarioAudiovisuales;
    }


    public String registrarEstudiante(String nombre, String documento, String telefono,
                                      String correo, String codigoEstudiantil, String programa) {

        if (nombre == null || nombre.trim().isEmpty()) return "Error: El nombre es obligatorio.";
        if (documento == null || documento.trim().isEmpty()) return "Error: El documento de identidad es obligatorio.";
        if (telefono == null || telefono.trim().isEmpty()) return "Error: El teléfono es obligatorio.";
        if (correo == null || correo.trim().isEmpty()) return "Error: El correo institucional es obligatorio.";
        if (codigoEstudiantil == null || codigoEstudiantil.trim().isEmpty()) return "Error: El código estudiantil es obligatorio.";
        if (programa == null || programa.trim().isEmpty()) return "Error: El programa académico es obligatorio.";

        if (servicioEstudiante.buscarPorCodigo(codigoEstudiantil) != null) {
            return "Error: Ya existe un estudiante con el código " + codigoEstudiantil;
        }

        Estudiante est = new Estudiante();
        est.setNombreCompleto(nombre);
        est.setDocumentoIdentidad(documento);
        est.setTelefono(telefono);
        est.setCorreoInstitucional(correo);
        est.setCodigoEstudiantil(codigoEstudiantil);
        est.setEscuelaDependencia(programa);
        est.setContadorStrikes(0);
        est.setEstaSancionado(false);
        est.setSanciones(new ArrayList<>());

        servicioEstudiante.agregarEstudiante(est);
        return "EXITO: Estudiante registrado correctamente.";
    }

    public String registrarDocente(String nombre, String documento, String telefono,
                                   String correo, String codigoDocente, String departamento) {

        if (nombre == null || nombre.trim().isEmpty())
            return "Error: El nombre es obligatorio.";
        if (documento == null || documento.trim().isEmpty())
            return "Error: El documento de identidad es obligatorio.";
        if (telefono == null || telefono.trim().isEmpty())
            return "Error: El teléfono es obligatorio.";
        if (correo == null || correo.trim().isEmpty())
            return "Error: El correo institucional es obligatorio.";
        if (codigoDocente == null || codigoDocente.trim().isEmpty())
            return "Error: El código docente es obligatorio.";
        if (departamento == null || departamento.trim().isEmpty())
            return "Error: El departamento es obligatorio.";

        if (servicioDocente.buscarPorCodigo(codigoDocente) != null) {
            return "Error: Ya existe un docente con el código " + codigoDocente;
        }

        Docente doc = new Docente();
        doc.setNombreCompleto(nombre);
        doc.setDocumentoIdentidad(documento);
        doc.setTelefono(telefono);
        doc.setCorreoInstitucional(correo);
        doc.setCodigoDocente(codigoDocente);
        doc.setEscuelaDependencia(departamento);

        servicioDocente.agregarDocente(doc);
        return "EXITO: Docente registrado correctamente.";
    }

    public String registrarFuncionarioSolicitante(String nombre, String documento, String telefono,
                                                  String correo, String codigoFuncionario, String dependencia) {

        if (nombre == null || nombre.trim().isEmpty())
            return "Error: El nombre es obligatorio.";
        if (documento == null || documento.trim().isEmpty())
            return "Error: El documento de identidad es obligatorio.";
        if (telefono == null || telefono.trim().isEmpty())
            return "Error: El teléfono es obligatorio.";
        if (correo == null || correo.trim().isEmpty())
            return "Error: El correo institucional es obligatorio.";
        if (codigoFuncionario == null || codigoFuncionario.trim().isEmpty())
            return "Error: El código de funcionario es obligatorio.";
        if (dependencia == null || dependencia.trim().isEmpty())
            return "Error: La dependencia es obligatoria.";

        if (servicioFuncionarioSolicitante.buscarPorCodigo(codigoFuncionario) != null) {
            return "Error: Ya existe un funcionario con el código " + codigoFuncionario;
        }

        FuncionarioSolicitante fun = new FuncionarioSolicitante();
        fun.setNombreCompleto(nombre);
        fun.setDocumentoIdentidad(documento);
        fun.setTelefono(telefono);
        fun.setCorreoInstitucional(correo);
        fun.setCodigoFuncionario(codigoFuncionario);
        fun.setDependencia(dependencia);

        servicioFuncionarioSolicitante.agregarFuncionario(fun);
        return "EXITO: Funcionario solicitante registrado correctamente.";
    }

    public String registrarFuncionarioAudiovisuales(String nombre, String documento, String telefono,
                                                    String correo, String contrasena) {

        if (nombre == null || nombre.trim().isEmpty())
            return "Error: El nombre es obligatorio.";
        if (documento == null || documento.trim().isEmpty())
            return "Error: El documento de identidad es obligatorio.";
        if (telefono == null || telefono.trim().isEmpty())
            return "Error: El teléfono es obligatorio.";
        if (correo == null || correo.trim().isEmpty())
            return "Error: El correo institucional es obligatorio.";
        if (contrasena == null || contrasena.trim().isEmpty())
            return "Error: La contraseña es obligatoria.";

        if (servicioFuncionarioAudiovisuales.buscarPorDocumento(documento) != null) {
            return "Error: Ya existe un operador de audiovisuales con el documento " + documento;
        }

        FuncionarioAudiovisuales fa = new FuncionarioAudiovisuales();
        fa.setNombre(nombre);
        fa.setDocumentoIdentidad(documento);
                fa.setContrasena(contrasena);

        servicioFuncionarioAudiovisuales.agregarFuncionario(fa);
        return "EXITO: Operador de audiovisuales registrado correctamente.";
    }


    public Map<String, String> buscarDatosUsuario(String tipoUsuario, String codigoODocumento) {
        if (tipoUsuario == null || codigoODocumento == null || codigoODocumento.trim().isEmpty()) {
            return Map.of();
        }

        switch (tipoUsuario.toUpperCase()) {
            case "ESTUDIANTE":
                return servicioEstudiante.getDatosAutocompletar(codigoODocumento);
            case "DOCENTE":
                return servicioDocente.getDatosAutocompletar(codigoODocumento);
            case "FUNCIONARIO_SOLICITANTE":
                return servicioFuncionarioSolicitante.getDatosAutocompletar(codigoODocumento);
            case "FUNCIONARIO_AUDIOVISUALES":
                return Map.of();
            default:
                return Map.of();
        }
    }
}