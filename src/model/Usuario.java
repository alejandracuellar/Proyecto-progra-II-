package model;

public abstract class Usuario {
    private String id;
    private String documentoIdentidad;
    private String nombreCompleto;
    private String telefono;
    private String escuelaDependecia;
    private String firmaDigital;

    public Usuario(String id, String documentoIdentidad, String nombreCompleto, String telefono, String escuelaDependecia, String firmaDigital) {
        this.id = id;
        this.documentoIdentidad = documentoIdentidad;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.escuelaDependecia = escuelaDependecia;
        this.firmaDigital = firmaDigital;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEscuelaDependecia() {
        return escuelaDependecia;
    }

    public void setEscuelaDependecia(String escuelaDependecia) {
        this.escuelaDependecia = escuelaDependecia;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", escuelaDependecia='" + escuelaDependecia + '\'' +
                ", firmaDigital='" + firmaDigital + '\'' +
                '}';
    }
}
