import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JavaApplication3 {

    public static void main(String[] args) {

        String ruta = "C:\\Users\\DESKTOP-6A9B\\Desktop\\DirectorioPrueba";
        if (args.length > 0) ruta = args[0];

        File fich = new File(ruta);

        if (!fich.exists()) {
            System.out.println("No existe el fichero o directorio" + ruta);
        } else {
            if (fich.isFile()) {
                System.out.println(System.lineSeparator() + ruta + " es un fichero.");
            } else {
                System.out.println(" " + ruta + " es un directorio. \nContenidos:");
                File[] ficheros = fich.listFiles();
                for (File f : ficheros) {
                    String texto = f.isDirectory() ? "/" : f.isFile() ? "_" : "?";

                    System.out.println(texto + " " + f.getName());
                }
            }
            System.out.println(System.lineSeparator());
        }

        //  Comprobamos si es un directorio o fichero
        //  Evaluamos con el metodo "isHidden" si el directorio o fichero está oculto
        String tipo = fich.isDirectory() ? "directorio " : "fichero ";
        String visibilidad = fich.isHidden() ? " está oculto." : " no está oculto.";

        if (fich.isDirectory() || fich.isFile()) {
            System.out.println("El " + tipo + "\"" + fich.getName() + "\"" + visibilidad + System.lineSeparator());
        }

        //  Comprobamos con el metodo "lastModified" cuando fue la última modificación del fichero o directorio
        //  Obtenemos la última modificación con Date
        //  Imprimimos la fecha de la modificación
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        Date ultimaModificacion = new Date(fich.lastModified());

        System.out.println("La fecha de la última modificación del " + tipo + "es: " + date.format(ultimaModificacion) + System.lineSeparator());

        //  Obtenemos la ruta del fichero o directorio
        System.out.println("La ruta del " + tipo + "es: " + fich.getPath() + System.lineSeparator());

        //  Comprobamos si es un fichero o directorio para después obtener el directorio padre
        if (fich.isDirectory() || fich.isFile()) {
            File parentFile = fich.getParentFile();
            String parentName = (parentFile != null) ? parentFile.getName() : "No tiene directorio padre";
            System.out.println("El directorio padre es: \"" + parentName + "\"" + System.lineSeparator());
        }

        //  En las siguientes líneas, comprobamos si nuestro fichero/directorio puede ser ejecutado, leído y escrito.
        if (fich.isDirectory() || fich.isFile()) {
            if (fich.canExecute()) {
                System.out.println("El " + tipo + "\"" + fich.getName() + "\" puede ser ejecutado." + System.lineSeparator());
            }
        }

        if (fich.isDirectory() || fich.isFile()) {
            if (fich.canRead()) {
                System.out.println("El " + tipo + "\"" + fich.getName() + "\" puede ser leído." + System.lineSeparator());
            }
        }

        if (fich.isDirectory() || fich.isFile()) {
            if (fich.canWrite()) {
                System.out.println("El " + tipo + "\"" + fich.getName() + "\" tiene permisos para ser escrito." + System.lineSeparator());
            }
        }


    }
}