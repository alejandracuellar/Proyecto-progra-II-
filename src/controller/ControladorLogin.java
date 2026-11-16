package controller;

import service.ServicioFuncionarioAudiovisuales;

public class ControladorLogin {

    private ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales;

    public ControladorLogin(ServicioFuncionarioAudiovisuales servicioFuncionarioAudiovisuales) {
        this.servicioFuncionarioAudiovisuales = servicioFuncionarioAudiovisuales;
    }

    public String iniciarSesion(String identificacion, String contrasena) {

        if (identificacion == null || identificacion.trim().isEmpty()) {
            return "Error: El campo de usuario/identificación no puede estar vacío.";
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            return "Error: La contraseña no puede estar vacía.";
        }

        var funcionario = servicioFuncionarioAudiovisuales.buscarPorDocumento(identificacion);

        if (funcionario != null) {

            if (funcionario.getContrasena().equals(contrasena)) {
                return "EXITO: Bienvenido al sistema";
            } else {
                return "Error: Contraseña incorrecta.";
            }
        }

        return "Error: El usuario no existe o no tiene permisos para acceder al programa.";
    }
}