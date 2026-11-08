package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Prestamo;
import model.Recurso;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaJson {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String ruta = "prestamos.json";
    private String ruta2 = "inventario.json";

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
            throw new RuntimeException(e);
        }
    }

    public List<Recurso> leerInventario() {
        File archivo = new File(ruta2);
        if(!archivo.exists()) {
            return new ArrayList<>();
        }
        Type tipoLista = new TypeToken<List<Recurso>>() {}.getType();

        try (FileReader lector = new FileReader(ruta2)) {
            return gson.fromJson(lector, tipoLista);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escribirInventario(List<Recurso> inventario) {
        try (FileWriter escritor = new FileWriter(ruta2)) {
            gson.toJson(inventario, escritor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}