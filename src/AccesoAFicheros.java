import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class AccesoAFicheros {

    public static void main(String[] args) {

        String rutaDirectorio = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el texto a buscar:");
        String textoBuscado = scanner.nextLine();

        buscarEnDirectorios(rutaDirectorio, textoBuscado);

        scanner.close();
    }

    public static void buscarEnDirectorios(String rutaDirectorio, String textoBuscado) {
        File directorio = new File(rutaDirectorio);
        // Expresion lambda
        File[] archivos = directorio.listFiles((d, nombre) -> nombre.endsWith(".txt"));

        if (archivos != null) {
            for (File archivo : archivos) {
                buscarEnArchivo(archivo, textoBuscado);
            }
        } else {
            System.out.println("No se encontró el directorio o no contiene archivos.");
        }
    }

    public static void buscarEnArchivo(File archivo, String textoBuscado) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 0;
            boolean encontrado = false;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                int indice = linea.indexOf(textoBuscado);

                while (indice != -1) {
                    if (!encontrado) {
                        System.out.println("Resultados en el archivo: " + archivo.getName());
                        encontrado = true;
                    }
                    System.out.printf("Línea: %d, Columna: %d%n", numeroLinea, indice + 1);
                    indice = linea.indexOf(textoBuscado, indice + 1);
                }
            }

            if (!encontrado) {
                System.out.println("No se encontró el texto '" + textoBuscado + "' en el archivo " + archivo.getName() + ".");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + archivo.getName() + ": " + e.getMessage());
        }
    }
}
