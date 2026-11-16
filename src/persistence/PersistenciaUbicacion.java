package persistence;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.Ubicacion;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import java.util.ArrayList;

public class PersistenciaUbicacion {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String ruta = "src/datos/ubicaciones.json";

    public ArrayList<Ubicacion> leer() {

        File archivo = new File(ruta);
        if(!archivo.exists()) {
            return new ArrayList<>();
        }
        Type tipoLista = new TypeToken<ArrayList<Ubicacion>>() {}.getType();

        try (FileReader lector = new FileReader(ruta)) {
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escribir(ArrayList<Ubicacion> ubicaciones) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(ubicaciones, escritor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}