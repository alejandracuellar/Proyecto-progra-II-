package service;

import model.Estudiante;
import model.Sancion;
import java.time.LocalDateTime;

public class ServicioSancion {

    public void aplicarStrikeYSancionSiAplica(Estudiante estudiante, LocalDateTime fechaMomento) {
        estudiante.incrementarStrike();

        if (estudiante.isEstaSancionado()) {
            Sancion nuevaSancion = new Sancion(
                    "SAN-" + System.currentTimeMillis(),
                    fechaMomento,
                    "Sanción por acumulación de 3 strikes (Devolución tardía de equipos).",
                    estudiante.getContadorStrikes()
            );
            estudiante.getHistorialSanciones().add(nuevaSancion);
        }
    }
}