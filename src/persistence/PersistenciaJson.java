package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class PersistenciaJson {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Object leer(String ruta, Type tipoLista) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return null;
        }
        try (FileReader lector = new FileReader(ruta)) {
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escribir(String ruta, Object datos) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(datos, escritor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}