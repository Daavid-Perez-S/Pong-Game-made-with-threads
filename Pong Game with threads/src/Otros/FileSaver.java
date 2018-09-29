package Otros;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 *  Creado por: David Pérez Sánchez
 *  Matrícula: 163202
 *  Materia: Estructura de Datos Avanzada
 *  Universidad Politécnica de Chiapas.
 *  Fecha de Creación: 09/02/2018
 */
/** *  <b>Clase FileSaver.</b>
 * <p>
 * Esta clase genérica de tipo <code>T</code> permite guardar y recuperar
 * objetos de un archivo.</p>
 * <p>
 * La clase se crea dependiendo del tipo de dato que el usuario le indique al
 * instanciarla.
 * <br>Ejemplo:<ul><li>{@code FileSaver<String> archivo = new FileSaver<String>();}</li></ul></p>
 *
 * @author David Pérez S.
 * @param <T> La clase se creará del tipo de dato que el usuario le indique.
 */
public class FileSaver<T> {

    private FileOutputStream fo;
    private String nombreArchivo;
    private Boolean sobreEscribirDatos;

    /**
     * <b>Costructor de la clase Archivo.</b>
     *
     * @param nombreArchivo Nombre con el que el documento se creará.
     */
    public FileSaver(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * <b>Crear FileSaver Vacío.</b>
     * <p>
     * Crea un archivo con el nombre especificado por el usuario al instanciar
     * la clase.</p>
     *
     * @return Retorna TRUE si el archivo pudo ser creado, y FALSE en caso
     * contrario.
     */
    public boolean crearArchivoVacio() {
        return crearArchivoVacio(false);
    }

    /**
     * <b>Crear FileSaver Vacío.</b>
     * <p>
     * Crea un archivo con el nombre especificado por el usuario al instanciar
     * la clase.
     * <br>Es este caso, el objeto se puede escribir al final del archivo, en
     * lugar de al principio, si el usuario así lo indica.
     * <br>Esto evitará que el archivo se sobre-escriba cada vez que se guarda
     * en él.</p>
     *
     * @param sobreEscribirDatos Variable booleana que indica si el archivo se
     * se sobre-escribirán o no.
     * @return Retorna TRUE si el archivo pudo ser creado, y FALSE en caso
     * contrario.
     */
    public boolean crearArchivoVacio(boolean sobreEscribirDatos) {
        boolean bandera = true;
        FileOutputStream tmp = null;
        this.sobreEscribirDatos = sobreEscribirDatos;
        try {
            tmp = new FileOutputStream(nombreArchivo, sobreEscribirDatos);
        } catch (FileNotFoundException e) {
            bandera = false;
            System.err.println("\t[ No se pudo crear el archivo ]");
        }
        fo = tmp;
        return bandera;
    }

    /**
     * <b>Serializar Objeto.</b>
     * <p>
     * Serializa o guarda un objeto de tipo <code>T</code>, el cuál se
     * almacenará el el archivo creado previamente.</p>
     *
     * @param objeto Objeto de tipo <code>T</code> que vamos a serializar
     * (guardar) en el archivo creado previamente.
     * @return Retorna TRUE si el objeto pudo ser serializado, y FALSE en caso
     * contrario.
     * @throws java.io.FileNotFoundException
     */
    public boolean serializar(T objeto) throws FileNotFoundException {

        boolean bandera = true;
        ObjectOutputStream oos = null;
        if (fo == null) {
            System.out.println("[ Creé otro archivo ]");
            fo = new FileOutputStream(nombreArchivo, this.sobreEscribirDatos);
        }
        try {
            oos = new ObjectOutputStream(fo);
            oos.writeObject(objeto);
            oos.flush();
            System.out.println("[ Serializado ] ");
            oos.close();
        } catch (IOException e) {
            // write stack trace to standard error
            System.out.println(e);
            System.err.println("\t[ El objeto no se pudo guardar ]");
            bandera = false;
        }
        return bandera;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * <b>Deserializar o recuperar Objeto.</b>
     * <p>
     * Deserializa o recupera el objeto guardado en el archivo creado
     * previamente, retornando un objeto de tipo <code>T</code> el cuál fue
     * especificado al instanciar la clase.</p>
     *
     * @return Retorna un objeto de tipo <code>T</code>.
     */
    public T deserializar() {

        T objeto = null;
        try {
            final FileInputStream fis = new FileInputStream(nombreArchivo);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                objeto = (T) ois.readObject();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("\t[ El objeto no se pudo leer ]" + e);
        }
        return objeto;
    }
}