package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estudiante extends Usuario{
    private String codigoEstudiantil;
    private String correoInstitucional;
    private int contadorStrikes;
    private boolean estaSancionado;
    private List<Sancion> historialSanciones;

    public Estudiante() {
        super();
        this.historialSanciones = new ArrayList<>();
    }

    public Estudiante(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependencia, String firmaDigital, String codigoEstudiantil, String correoInstitucional, int contadorStrikes, boolean estaSancionado, List<Sancion> historialSanciones) {
        super(id, documentoIdentidad, nombreCompleto, telefono, escuelaDependencia, firmaDigital);
        this.codigoEstudiantil = codigoEstudiantil;
        this.correoInstitucional = correoInstitucional;
        this.contadorStrikes = contadorStrikes;
        this.estaSancionado = estaSancionado;
        this.historialSanciones = historialSanciones;
    }

    public String getCodigoEstudiantil() {
        return codigoEstudiantil;
    }

    public void setCodigoEstudiantil(String codigoEstudiantil) {
        this.codigoEstudiantil = codigoEstudiantil;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public int getContadorStrikes() {
        return contadorStrikes;
    }

    public void setContadorStrikes(int contadorStrikes) {
        this.contadorStrikes = contadorStrikes;
    }

    public boolean isEstaSancionado() {
        return estaSancionado;
    }

    public void setEstaSancionado(boolean estaSancionado) {
        this.estaSancionado = estaSancionado;
    }

    public List<Sancion> getHistorialSanciones() {
        return historialSanciones;
    }

    public void setHistorialSanciones(List<Sancion> historialSanciones) {
        this.historialSanciones = historialSanciones;
    }

    @Override
    public Map<String, String> getDatosAutocompletar(){
        Map<String, String> datos = new HashMap<>();
        datos.put("nombre completo", getNombreCompleto());
        datos.put("documento de identidad", getDocumentoIdentidad());
        datos.put("telefono", getTelefono());
        datos.put("escuela o dependencia", getEscuelaDependencia());
        datos.put("firma digital", getFirmaDigital());
        datos.put("codigo estudiantil", this.codigoEstudiantil);
        datos.put("correo institucional", this.correoInstitucional);
        return datos;
    }

    @Override
    public boolean puedeSolicitarPrestamo(){
        return !estaSancionado && contadorStrikes <3;
    }
    public void incrementarStrike() {
        this.contadorStrikes++;
        if (this.contadorStrikes >= 3) {
            this.estaSancionado = true;
        }
    }

    public void reiniciarStrikes() {
        this.contadorStrikes = 0;
        this.estaSancionado = false;
    }
}
