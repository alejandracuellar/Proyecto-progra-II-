package model;

public class Estudiante extends Usuario{
    private String codigoEstudiantil;
    private String correoInstitucional;
    private int contadorStrikes;
    private boolean estaSancionado;

    public Estudiante(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependecia, String firmaDigital, String codigoEstudiantil, String correoInstitucional, int contadorStrikes, boolean estaSancionado) {
        super(id, documentoIdentidad, nombreCompleto, telefono, escuelaDependecia, firmaDigital);
        this.codigoEstudiantil = codigoEstudiantil;
        this.correoInstitucional = correoInstitucional;
        this.contadorStrikes = contadorStrikes;
        this.estaSancionado = estaSancionado;
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

    @Override
    public String toString() {
        return "Estudiante{" +
                "codigoEstudiantil='" + codigoEstudiantil + '\'' +
                ", correoInstitucional='" + correoInstitucional + '\'' +
                ", contadorStrikes=" + contadorStrikes +
                ", estaSancionado=" + estaSancionado +
                '}';
    }
}
