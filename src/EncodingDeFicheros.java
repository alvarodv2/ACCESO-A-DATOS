import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.util.Scanner;

public class EncodingDeFicheros {

    // Definimos una constante que almacena el directorio principal
    private static final String DIRECTORIO_PRINCIPAL = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba";

    public static void main(String[] args) {
        try {

            String[] parametros = introducirTodo();

            verifyTheArgument(parametros);

            readUTF8AndWriteUTF16AndISO88591(parametros[0], parametros[1], parametros[2], parametros[3]);

            System.out.println("Conversión completada con éxito.");

        } catch (FileNotFoundException e) {
            // Capturamos la excepcion si el archivo no se encuentra
            System.out.println("Error: Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            // Capturamos errores de entrada/salida
            System.out.println("Error de entrada/salida: " + e.getMessage());
        } catch (Exception e) {
            // Capturamos cualquier otro tipo de error inesperado
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private static String[] introducirTodo() {
        Scanner scanner = new Scanner(System.in);

        // Listamos los archivos del directorio
        File dir = new File(DIRECTORIO_PRINCIPAL);
        /*
         * Utilizamos una expresión "lambda" para devolver los ficheros .txt
         *
         *  1. Llamamos a dir.list() para listar los archivos.
         *
         *  2. Por cada fichero encontrado, se invoca al metodo accept() de la interfaz FilenameFilter.
         *    - El primer parámetro, "d", representa el directorio en el que estamos buscando.
         *      Es de tipo File y apunta al objeto que representa el directorio actual.
         *    - El segundo parametro "name", es un String que contiene el nombre del fichero actual que se esta evaluando.
         *
         *  3. La expresión lambda (d, name) -> name.toLowerCase().endsWith(".txt") proporciona
         *     la implementación del metodo accept(). Devuelve true si el nombre del archivo
         *     termina con ".txt", permitiendo que solo los archivos de texto sean listados.
         *
         *  4. Como resultado, obtenemos un array de Strings que contiene los nombres de todos
         *     los archivos .txt en el directorio especificado.
         */
        String[] ficheros = dir.list((d, name) -> name.toLowerCase().endsWith(".txt"));

        // Mostramos los archivos .txt en el directorio
        System.out.println(" ---- Ficheros en el directorio actual ---- ");
        if (ficheros != null && ficheros.length > 0) {
            for (String fichero : ficheros) {
                System.out.println(fichero);
            }
        } else {
            System.out.println("No se encontraron ficheros .txt");
        }

        System.out.print("Introduce el nombre del fichero de entrada (Ej: Nombre.txt): ");
        String nombreEntrada = scanner.nextLine();

        System.out.print("Introduce el encoding de entrada (ASCII, UTF-8, UTF-16, ISO-8859-1): ");
        String encodingEntrada = scanner.nextLine();

        System.out.print("Introduce el nombre del nuevo fichero recodificado de salida (Ej: nuevoFichero.txt): ");
        String nombreSalida = scanner.nextLine();

        System.out.print("Introduce el encoding de salida (UTF-16, ISO-8859-1): ");
        String encodingSalida = scanner.nextLine();

        // Devolvemos un array con los paths y encodings seleccionados
        return new String[]{
                Paths.get(DIRECTORIO_PRINCIPAL, nombreEntrada).toString(),
                encodingEntrada,
                Paths.get(DIRECTORIO_PRINCIPAL, nombreSalida).toString(),
                encodingSalida};
    }

    private static void verifyTheArgument(String[] args) throws IllegalArgumentException {
        // Comprobamos que se han proporcionado los 4 argumentos y si no lanzamos exception
        if (args.length != 4) {
            throw new IllegalArgumentException("Se requieren 4 argumentos: <rutaEntrada> <encodingEntrada> <rutaSalida> <encodingSalida>");
        }

        // Validamos los encodings proporcionados y en caso de error lanzamos una exception
        if (!isValidEncoding(args[1]) || !isValidEncoding(args[3])) {
            throw new IllegalArgumentException("Encoding no válido. Los encodings permitidos son: ASCII, UTF-8, UTF-16, ISO-8859-1.");
        }
    }

    private static boolean isValidEncoding(String encoding) {
        // Comprobamos si el encoding es uno de los permitidos con equalsIgnoreCase().
        return encoding.equalsIgnoreCase("ASCII") ||
                encoding.equalsIgnoreCase("UTF-8") ||
                encoding.equalsIgnoreCase("UTF-16") ||
                encoding.equalsIgnoreCase("ISO-8859-1");
    }

    // Al hacer uso de "throws IOException" en el metodo, hacemos que se propague la excepcion hacia arriba, para ser controlada en el main
    private static void readUTF8AndWriteUTF16AndISO88591(String rutaEntrada, String encodingEntrada, String rutaSalida, String encodingSalida) throws IOException {
        // Establecemos los charset de entrada y salida
        Charset charsetEntrada = Charset.forName(encodingEntrada);
        Charset charsetSalida = Charset.forName(encodingSalida);

        // Leemos el archivo de entrada. Utilizamos un try-with-resources para el cierre de flujo automatico
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rutaEntrada), charsetEntrada))) {
            // Creamos el fichero de salida
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(rutaSalida), charsetSalida))) {
                String linea;
                // Leemos cada línea del fichero de entrada
                while ((linea = reader.readLine()) != null) {
                    // Escribimos la linea leida en el nuevo fichero con el nuevo encoding
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (FileAlreadyExistsException e) {
            // Capturamos la excepcion si el archivo de salida ya existe
            System.out.println("Error: El archivo de salida ya existe." + e.getMessage());
        }
    }
}
