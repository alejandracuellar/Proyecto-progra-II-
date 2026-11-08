package model;

import java.util.HashMap;
import java.util.Map;

public class Elemento extends Recurso{

    private String marca;
    private String numeroSerie;
    private String categoria;
    private String especificacionesAdicionales;

    public Elemento(String codigoEquipo, String descripcion, String estado, boolean requiereAutorizacionDocente,
                    Ubicacion ubicacionActual, String marca, String numeroSerie, String categoria, String especificacionesAdicionales) {
        super(codigoEquipo, descripcion, estado, requiereAutorizacionDocente, ubicacionActual);
        this.marca = marca;
        this.numeroSerie = numeroSerie;
        this.categoria = categoria;
        this.especificacionesAdicionales = especificacionesAdicionales;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEspecificacionesAdicionales() {
        return especificacionesAdicionales;
    }

    public void setEspecificacionesAdicionales(String especificacionesAdicionales) {
        this.especificacionesAdicionales = especificacionesAdicionales;
    }

    @Override
    public Map<String, String> getDatosAutocompletar() {
        Map<String, String> datos = new HashMap<>();
        datos.put("codigoEquipo", getCodigoEquipo());
        datos.put("descripcion", getDescripcion());
        datos.put("marca", this.marca);
        datos.put("numeroSerie", this.numeroSerie);
        datos.put("categoria", this.categoria);
        datos.put("especificacionesAdicionales", this.especificacionesAdicionales);
        datos.put("estado", getEstado());
        datos.put("requiereAutorizacionDocente", String.valueOf(isRequiereAutorizacionDocente()));
        if (getUbicacionActual() != null) {
            datos.put("ubicacionActual", String.valueOf(getUbicacionActual()));
        }
        return datos;
    }
}
