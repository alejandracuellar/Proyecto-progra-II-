package model;

import java.util.List;

public class Estudiante extends Usuario{
    private String codigoEstudiantil;
    private String correoInstitucional;
    private int contadorStrikes;
    private boolean estaSancionado;

    private List<Sancion> historialSanciones;

    public Estudiante(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependecia, String firmaDigital, String codigoEstudiantil, String correoInstitucional, int contadorStrikes, boolean estaSancionado, List<Sancion> historialSanciones) {
        super(id, documentoIdentidad, nombreCompleto, telefono, escuelaDependecia, firmaDigital);
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
    public String toString() {
        return "Estudiante{" +
                "codigoEstudiantil='" + codigoEstudiantil + '\'' +
                ", correoInstitucional='" + correoInstitucional + '\'' +
                ", contadorStrikes=" + contadorStrikes +
                ", estaSancionado=" + estaSancionado +
                ", historialSanciones=" + historialSanciones +
                '}';
    }
}
