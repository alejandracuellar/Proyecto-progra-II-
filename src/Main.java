
import model.Auditorio;
import model.Docente;
import model.Estudiante;
import model.FuncionarioAudiovisuales;
import model.FuncionarioSolicitante;
import model.Prestamo;
import model.Recurso;
import model.Sancion;
import model.Ubicacion;


import persistence.PersistenciaInventario;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PersistenciaInventario persistencia = new PersistenciaInventario();

        Type tipoDocentes = new TypeToken<ArrayList<Docente>>() {
        }.getType();
        ArrayList<Docente> listaDocentes = (ArrayList<Docente>) persistencia.leer("datos/docentes.json", tipoDocentes);
        if (listaDocentes == null) listaDocentes = new ArrayList<>();

        Type tipoEstudiantes = new TypeToken<ArrayList<Estudiante>>() {
        }.getType();
        ArrayList<Estudiante> listaEstudiantes = (ArrayList<Estudiante>) persistencia.leer("datos/estudiantes.json", tipoEstudiantes);
        if (listaEstudiantes == null) listaEstudiantes = new ArrayList<>();

        Type tipoFuncSolicitantes = new TypeToken<ArrayList<FuncionarioSolicitante>>() {
        }.getType();
        ArrayList<FuncionarioSolicitante> listaFuncSolicitantes = (ArrayList<FuncionarioSolicitante>) persistencia.leer("datos/funcionarios_solicitantes.json", tipoFuncSolicitantes);
        if (listaFuncSolicitantes == null) listaFuncSolicitantes = new ArrayList<>();

        Type tipoFuncAudiovisuales = new TypeToken<ArrayList<FuncionarioAudiovisuales>>() {
        }.getType();
        ArrayList<FuncionarioAudiovisuales> listaFuncAudiovisuales = (ArrayList<FuncionarioAudiovisuales>) persistencia.leer("datos/funcionarios_audiovisuales.json", tipoFuncAudiovisuales);
        if (listaFuncAudiovisuales == null) listaFuncAudiovisuales = new ArrayList<>();

        Type tipoRecursos = new TypeToken<ArrayList<Recurso>>() {
        }.getType();
        ArrayList<Recurso> listaRecursos = (ArrayList<Recurso>) persistencia.leer("datos/inventario.json", tipoRecursos);
        if (listaRecursos == null) listaRecursos = new ArrayList<>();

        Type tipoPrestamos = new TypeToken<ArrayList<Prestamo>>() {
        }.getType();
        ArrayList<Prestamo> listaPrestamos = (ArrayList<Prestamo>) persistencia.leer("datos/prestamos.json", tipoPrestamos);
        if (listaPrestamos == null) listaPrestamos = new ArrayList<>();

        Type tipoAuditorios = new TypeToken<ArrayList<Auditorio>>() {
        }.getType();
        ArrayList<Auditorio> listaAuditorios = (ArrayList<Auditorio>) persistencia.leer("datos/auditorios.json", tipoAuditorios);
        if (listaAuditorios == null) listaAuditorios = new ArrayList<>();

        Type tipoSanciones = new TypeToken<ArrayList<Sancion>>() {
        }.getType();
        ArrayList<Sancion> listaSanciones = (ArrayList<Sancion>) persistencia.leer("datos/sanciones.json", tipoSanciones);
        if (listaSanciones == null) listaSanciones = new ArrayList<>();

        Type tipoUbicaciones = new TypeToken<ArrayList<Ubicacion>>() {
        }.getType();
        ArrayList<Ubicacion> listaUbicaciones = (ArrayList<Ubicacion>) persistencia.leer("datos/ubicaciones.json", tipoUbicaciones);
        if (listaUbicaciones == null) listaUbicaciones = new ArrayList<>();

    }
}