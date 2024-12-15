import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

/* Vamos a practicar con las clases JAVA para escribir ficheros BINARIOS.

  Las clases a utilizar son: File, RandomAccessFile,

  Auxiliarmente necesitaremos las clases: java.util.List, java.util.ArrayList, java.util.HashMap, java.util.Map, javafx.util.Pair.

  Vamos a escribir registros de longitud fija en un fichero de acceso aleatorio.

  Debemos realizar un programa que mediante menús permita ir leyendo / añadiendo / modificando
  registros completos dentro de un fichero binario, los registros irán numerados ya que se conoce
  el tamaño FIJO de cada registro. Cada registro debe contener varios campos: string, int y fecha como mínimo.*/

public class FicherosAccesoAleatorio {

    private File f;
    private List<String> campos;
    private List<Integer> camposLength;
    private long longReg;
    private long numReg = 0;

    FicherosAccesoAleatorio(String path, List<String> campos, List<Integer> camposLength) throws IOException {
        this.campos = campos;
        this.camposLength = camposLength;
        this.f = new File(path);
        this.longReg = 0;
        for (Integer campo : camposLength) {
            this.longReg += campo;
        }
        if (f.exists()) {
            this.numReg = f.length() / this.longReg;
        }
    }

    public long getNumReg() {
        return numReg;
    }

    public void insertar(Map<String, String> reg, long pos) throws IOException {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rws")) {
            rndFile.seek(pos * this.longReg);
            for (int i = 0; i < campos.size(); i++) {
                String nomCampo = campos.get(i);
                Integer longCampo = camposLength.get(i);
                String valorCampo = reg.get(nomCampo);
                if (valorCampo == null) {
                    valorCampo = "";
                }
                String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo);
                rndFile.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo);
            }
        } catch (Exception ex) {
            System.out.println("Error al insertar registro: " + ex.getMessage());
        }
    }

    public void leer(long pos) throws IOException {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            rndFile.seek(pos * this.longReg);
            boolean registroVacio = true;
            for (int i = 0; i < campos.size(); i++) {
                byte[] buffer = new byte[camposLength.get(i)];
                rndFile.read(buffer);
                String campo = new String(buffer, "UTF-8").trim();
                if (!campo.isEmpty()) {
                    System.out.println(campos.get(i) + ": " + campo);
                    registroVacio = false;
                }
            }
            if (registroVacio) {
                System.out.println("Número de registro vacío");
            }
        }
    }

    public void modificar(Map<String, String> reg, long pos) throws IOException {
        insertar(reg, pos);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> campos = new ArrayList<>();
        List<Integer> camposLength = new ArrayList<>();

        campos.add("DNI");
        campos.add("NOMBRE");
        campos.add("DIRECCION");
        campos.add("CP");

        camposLength.add(9);
        camposLength.add(32);
        camposLength.add(32);
        camposLength.add(5);

        try {
            FicherosAccesoAleatorioAvanzado faa = new FicherosAccesoAleatorioAvanzado("file_binario_2.dat", campos, camposLength);

            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Añadir registro");
                System.out.println("2. Leer registro");
                System.out.println("3. Modificar registro");
                System.out.println("4. Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 4) break;

                switch (opcion) {
                    case 1:
                        Map<String, String> nuevoReg = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("Introduce " + campo + ": ");
                            nuevoReg.put(campo, scanner.nextLine());
                        }
                        System.out.print("Introduce la posición del registro a almacenar: ");
                        long pos = scanner.nextLong();
                        scanner.nextLine();
                        faa.insertar(nuevoReg, pos);
                        break;
                    case 2:
                        System.out.print("Introduce número de registro a leer: ");
                        long numLeer = scanner.nextLong();
                        faa.leer(numLeer);
                        break;
                    case 3:
                        System.out.print("Introduce número de registro a modificar: ");
                        long numModificar = scanner.nextLong();
                        scanner.nextLine();
                        Map<String, String> modifReg = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("Introduce " + campo + ": ");
                            modifReg.put(campo, scanner.nextLine());
                        }
                        faa.modificar(modifReg, numModificar);
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


