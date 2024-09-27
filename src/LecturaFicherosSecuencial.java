import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LecturaFicherosSecuencial {

    public static void main(String[] args) {
        String rutaFichero = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba\\OtroFichero.txt";

        contarVocales(rutaFichero);
    }

    public static void contarVocales(String rutaFichero) {

        int contadorVocales = 0;

        // Leeemos la cadena utilizando FileReader
        // Utilizamos try-with-resources para el cierre de flujo automatico (FileReader y BufferedReader)
        try (FileReader fil = new FileReader(rutaFichero)) {
            // Utilizando FileReader
            int letra;
            while ((letra = fil.read()) != -1) {
                if (esVocal((char) letra)) {
                    contadorVocales++;
                }
            }
            System.out.println("Total de vocales ~ FileReader: " + contadorVocales);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }

        contadorVocales = 0;

        // Utilizamos BufferedReader
        /* FileReader esta dejando el fichero "vacio" al utilizar el -1. Por tanto,
           volvemos a utilizar FileReader para que lea desde la posicon 0 */
        try (BufferedReader brf = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = brf.readLine()) != null) {
                for (char c : linea.toCharArray()) {
                    if (esVocal(c)) {
                        contadorVocales++;
                    }
                }
            }
            System.out.println("Total de vocales ~ BufferedReader: " + contadorVocales);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    // Metodo para comprobar si es una vocal
    // "aeiouAEIOU".indexOf(caracter) != -1 devuelve un true si es vocal o false si no lo es.
    public static boolean esVocal(char caracter) {
        return "aeiouAEIOU".indexOf(caracter) != -1;
    }

}
