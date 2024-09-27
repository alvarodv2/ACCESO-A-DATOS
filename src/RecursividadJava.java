import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RecursividadJava {

    public static void main(String[] args) {
        String rutaPorDefecto = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba";

        crearEstructuraDirectorios(rutaPorDefecto);

        borrarArchivo(rutaPorDefecto);
    }

    public static void crearEstructuraDirectorios(String rutaPorDefecto) {
        String[][] estructura = {
                {"Abuelo", "Padre", "Hijo1.txt"},
                {"Abuelo", "Padre", "Hija2.txt"},
                {"Abuelo", "Madre", "Hijo3.txt"},
                {"Abuelo", "Madre", "Hija4.txt"},
                {"Abuela", "Padre", "Hijo5.txt"},
                {"Abuela", "Padre", "Hija6.txt"},
                {"Abuela", "Madre", "Hijo7.txt"},
                {"Abuela", "Madre", "Hija8.txt"}
        };

        // Creamos cada directorio y fichero
        for (String[] ruta : estructura) {

            /* Construimos el path completo para el archivo
               El uso de File.separator nos permite que se ajuste
               automáticamente dependiendo del sistema operativo
               en el que se esté usando: */
               // Windows: \   Linux: /
            File archivo = new File(rutaPorDefecto, String.join(File.separator, ruta));

            // Creamos los directorios necesarios
            File directorio = archivo.getParentFile();

            if (directorio.mkdirs()) {
                System.out.println("Directorio creado: " + directorio.getName());
            } else if (directorio.exists()) {
                System.out.println("El directorio ya existe: " + directorio.getName());
            } else {
                System.out.println("El directorio no se pudo crear: " + directorio.getName());
            }

            crearArchivo(archivo);
        }
    }

    // Creamos cada fichero
    public static void crearArchivo(File archivo) {
        try {
            if (archivo.createNewFile()) {
                System.out.println("Archivo creado: " + archivo.getName());
            } else {
                System.out.println("El archivo ya existe: " + archivo.getName());
            }
        } catch (IOException e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }

    // Metodo para borrar los ficheros .txt
    public static void borrarArchivo(String rutaPorDefecto) {

        // Usar un try-with-resources nos permite que se cierre automáticamente el flujo del Scanner
        // No tenemos que hacer scanner.close();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Introduce el nombre del fichero a borrar (Ej. Hijo1.txt): ");
            String nombreArchivo = scanner.nextLine();

            File directorio = new File(rutaPorDefecto);
            boolean archivoBorrado = borrarArchivoRecursivamente(directorio, nombreArchivo);

            if (!archivoBorrado) {
                System.out.println("El archivo no existe: " + nombreArchivo);
            }
        } catch (Exception e){
            System.out.println("Error al borrar el archivo: " + e.getMessage());
        }
    }

    // Metodo recursivo para buscar y borrar el archivo
    public static boolean borrarArchivoRecursivamente(File directorio, String nombreArchivo) {
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    if (borrarArchivoRecursivamente(archivo, nombreArchivo)) {
                        return true;
                    }
                } else if (archivo.getName().equals(nombreArchivo)) {
                    if (archivo.delete()) {
                        System.out.println("Archivo borrado: " + archivo.getName());
                        return true;
                    } else {
                        System.out.println("Error al borrar el archivo: " + archivo.getName());
                    }
                }
            }
        }
        return false;
    }

}
