package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Prestamo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersistenciaPrestamo {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String ruta = "prestamos.json";

    public ArrayList<Prestamo> leer() {

        File archivo = new File(ruta);
        if(!archivo.exists()) {
            return new ArrayList<>();
        }
        Type tipoLista = new TypeToken<ArrayList<Prestamo>>() {}.getType();

        try (FileReader lector = new FileReader(ruta)) {
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void escribir(ArrayList<Prestamo> prestamos) {
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(prestamos, escritor);
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
