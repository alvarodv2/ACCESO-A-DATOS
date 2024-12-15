import java.io.*;
import java.util.Scanner;

public class EscrituraSecuencialFicheros {

    String rutaPorDefecto = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba";

    public static void main(String[] args) {

        EscrituraSecuencialFicheros escritor = new EscrituraSecuencialFicheros();
        Scanner sc = new Scanner(System.in);

        // Creamos el archivo
        System.out.println("Ingrese el nombre del archivo a crear: ");
        String nombreArchivo = sc.nextLine() + ".txt";
        File fichero = new File(escritor.rutaPorDefecto, nombreArchivo);

        try {
            if (fichero.createNewFile()) {
                System.out.println("Archivo creado correctamente: " + fichero.getAbsolutePath());
                escribirEnFichero(fichero, sc);
            } else {
                System.out.println("El archivo ya existe. Añadiendo contenido...");
                escribirEnFichero(fichero, sc);
            }

            // Listamos los archivos existentes en el directorio
            File directorio = new File(escritor.rutaPorDefecto);
            File[] archivos = directorio.listFiles((dir, name) -> name.endsWith(".txt"));
            System.out.println("Archivos existentes en el directorio:");
            for (File archivo : archivos) {
                System.out.println(archivo.getName());
            }

            // Añadimos texto al final del fichero que hayamos elegido
            System.out.println("Escribe el nombre del fichero en el que deseas añadir texto al final: ");
            String nombreFichero = sc.nextLine() + ".txt";
            File ficheroExistente = new File(escritor.rutaPorDefecto, nombreFichero);

            if (ficheroExistente.exists()) {
                escribirEnFichero(ficheroExistente, sc);
            } else {
                System.out.println("El fichero " + nombreFichero + " no existe.");
            }

            // Añadimos texto al principio del fichero
            System.out.println("Escribe el nombre del fichero en el que deseas añadir texto al principio: ");
            String nombreFicheroPrincipio = sc.nextLine() + ".txt";
            File ficheroExistentePrincipio = new File(escritor.rutaPorDefecto, nombreFicheroPrincipio);

            if (ficheroExistentePrincipio.exists()) {
                escribirEnFicheroAlPrincipio(ficheroExistentePrincipio, sc);
            } else {
                System.out.println("El fichero " + nombreFicheroPrincipio + " no existe.");
            }

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    private static void escribirEnFichero(File fichero, Scanner sc) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(fichero, true))) {
            System.out.println("Escribe lo que quieras en tu fichero: ");
            br.write(sc.nextLine());
            br.newLine();
            System.out.println("Texto añadido correctamente.");
        } catch (IOException e) {
            System.out.println("Ocurrio un error al escribir en el archivo: " + e.getMessage());
        }
    }

    private static void escribirEnFicheroAlPrincipio(File fichero, Scanner sc) {
        try {
            // Leemos el contenido actual del fichero
            String contenidoActual = "";
            try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenidoActual += linea + "\n";
                }
            }

            // Escribimos el nuevo contenido al principio
            System.out.println("Escribe el texto que deseas añadir al principio del fichero: ");
            String textoNuevo = sc.nextLine();
            try (BufferedWriter br = new BufferedWriter(new FileWriter(fichero))) {
                br.write(textoNuevo);
                br.newLine();
                br.write(contenidoActual);
            }

            System.out.println("Texto añadido al principio correctamente.");
        } catch (IOException e) {
            System.out.println("Ocurrio un error al escribir en el archivo: " + e.getMessage());
        }
    }
}
