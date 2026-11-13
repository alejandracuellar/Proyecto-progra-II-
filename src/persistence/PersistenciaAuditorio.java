package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Auditorio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersistenciaAuditorio {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String ruta = "auditorios.json";

    public ArrayList<Auditorio> leer() {

        File archivo = new File(ruta);
        if(!archivo.exists()) {
            return new ArrayList<>();
        }
        Type tipoLista = new TypeToken<ArrayList<Auditorio>>() {}.getType();

        try (FileReader lector = new FileReader(ruta)) {
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void escribir(ArrayList<Auditorio> auditorios) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(auditorios, escritor);
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
