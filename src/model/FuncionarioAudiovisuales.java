package model;

public class FuncionarioAudiovisuales {
    private String id;
    private String nombre;
    private String documentoIdentidad;
    private String contrasena;

    public FuncionarioAudiovisuales() {}


    public FuncionarioAudiovisuales(String id, String nombre, String documentoIdentidad, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.documentoIdentidad = documentoIdentidad;
        this.contrasena = contrasena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "FuncionarioAudiovisuales{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }


}
