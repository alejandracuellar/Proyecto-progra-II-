package model;

public class Docente extends Usuario{

    private String codigoDocente;
    private String correoInstitucional;

    public Docente(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependecia, String firmaDigital, String codigoDocente, String correoInstitucional) {
        super(id, documentoIdentidad, nombreCompleto, telefono, escuelaDependecia, firmaDigital);
        this.codigoDocente = codigoDocente;
        this.correoInstitucional = correoInstitucional;
    }

    public String getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(String codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "codigoDocente='" + codigoDocente + '\'' +
                ", correoInstitucional='" + correoInstitucional + '\'' +
                '}';
    }
}
