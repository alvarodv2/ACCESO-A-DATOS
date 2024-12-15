import java.io.*;

public class GuardadoDisco {

    public static void main(String[] args) {

        String path = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba\\Prueba.txt";

        File ficheroOriginal = new File(path);

        try {
            // Creamos un fichero temporal, en el mismo directorio que el fichero original.
            File ficheroTemporal = File.createTempFile("temp_", ".txt", ficheroOriginal.getParentFile());

            // Abrimos el fichero original para lectura.
            BufferedReader reader = new BufferedReader(new FileReader(ficheroOriginal));

            // Abrimos el fichero temporal para escritura.
            BufferedWriter writer = new BufferedWriter(new FileWriter(ficheroTemporal));

            String linea;
            boolean convertirMayus = false;

            // Leemos el fichero original linea por linea.
            while ((linea = reader.readLine()) != null) {
                // Eliminamos dobles/triples espacios en blanco.
                // \\s+ ---> Expresion regular para espacios en blanco (Con el + le indicamos que es un o mas espacios en blanco).
                linea = linea.replaceAll("\\s+", " ");

                // Convertimos minusculas a mayusculas despues de un punto.

                /* Utilizamos la clase "StringBuilder" porque es mas eficiente que "String" para el manejo
                   de cadenas. Nos permite usar metodos como .append( ) que usamos mas tarde para añadir la cadena
                   También usamos la clase "Character" para comprobar que sea una letra lo siguiente que viene*/
                StringBuilder nuevaLinea = new StringBuilder();
                for (int i = 0; i < linea.length(); i++) {
                    char ch = linea.charAt(i);

                    // Si encontramos un punto, la siguiente letra es en Mayus.
                    if (ch == '.') {
                        convertirMayus = true;
                    } else if (convertirMayus && Character.isLetter(ch)) {
                        ch = Character.toUpperCase(ch);
                        convertirMayus = false;
                    }

                    // Añadimos el caracter al StringBuilder
                    nuevaLinea.append(ch);
                }

                // Escribimos la linea modificada en el fichero temporal
                writer.write(nuevaLinea.toString());
                writer.newLine();
            }

            // Cerramos los flujos
            reader.close();
            writer.close();

            System.out.println("El fichero temporal ha sido creado correctamente");

        } catch (Exception e) {
            e.getMessage();
        }
    }
}


