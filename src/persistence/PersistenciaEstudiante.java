package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Estudiante;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersistenciaEstudiante {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String ruta = "estudiantes.json";

    public ArrayList<Estudiante> leer() {

        File archivo = new File(ruta);
        if(!archivo.exists()) {
            return new ArrayList<>();
        }
        Type tipoLista = new TypeToken<ArrayList<Estudiante>>() {}.getType();

        try (FileReader lector = new FileReader(ruta)) {
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void escribir(ArrayList<Estudiante> estudiantes) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(estudiantes, escritor);
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
