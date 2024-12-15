import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FicherosAccesoAleatorioAvanzado {

    private File f;
    private List<String> campos;
    private List<Integer> camposLength;
    private long longReg;
    private long numReg = 0;

    // Constructor
    FicherosAccesoAleatorioAvanzado(String path, List<String> campos, List<Integer> camposLength) throws IOException {
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

    // Métodos existentes
    public long getNumReg() {
        return numReg;
    }

    public void insertar(Map<String, String> reg, long pos) throws IOException {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rws")) {
            rndFile.seek(pos * this.longReg);
            for (int i = 0; i < campos.size(); i++) {
                String nomCampo = campos.get(i);
                Integer longCampo = camposLength.get(i);
                String valorCampo = reg.getOrDefault(nomCampo, "");
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

    // NUEVO MÉTODOS EJERCICIO 9

    /**
     * Devuelve el valor de un campo especifico en un registro.
     *
     * @param numRegistro El número del registro del que se quiere obtener el campo.
     * @param nomColumna  El nombre de la columna de la que se quiere obtener el valor.
     * @return El valor del campo correspondiente al registro y columna especificados.
     * @throws IOException              Si hay un error al leer el archivo.
     * @throws IllegalArgumentException Si el nombre de la columna no se encuentra.
     */
    public String selectCampo(int numRegistro, String nomColumna) throws IOException {
        int index = campos.indexOf(nomColumna);
        if (index == -1) {
            throw new IllegalArgumentException("Columna no encontrada: " + nomColumna);
        }

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            rndFile.seek(numRegistro * this.longReg);
            byte[] buffer = new byte[camposLength.get(index)];
            rndFile.read(buffer);
            return new String(buffer, "UTF-8").trim();
        }
    }

    /**
     * Devuelve una lista con todos los valores de una columna especifica en el archivo.
     *
     * @param nomColumna El nombre de la columna de la que se quieren obtener todos los valores.
     * @return Una lista con todos los valores del campo correspondiente a la columna especificada.
     * @throws IOException              Si hay un error al leer el archivo.
     * @throws IllegalArgumentException Si el nombre de la columna no se encuentra.
     */
    public List<String> selectColumna(String nomColumna) throws IOException {
        List<String> valores = new ArrayList<>();
        int index = campos.indexOf(nomColumna);
        if (index == -1) {
            throw new IllegalArgumentException("Columna no encontrada: " + nomColumna);
        }

        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            for (long i = 0; i < numReg; i++) {
                rndFile.seek(i * this.longReg);
                byte[] buffer = new byte[camposLength.get(index)];
                rndFile.read(buffer);
                valores.add(new String(buffer, "UTF-8").trim());
            }
        }
        return valores;
    }

    /**
     * Devuelve los datos de un registro como una lista.
     *
     * @param numRegistro El número del registro que se quiere obtener como lista.
     * @return Una lista con todos los valores del registro especificado.
     * @throws IOException Si hay un error al leer el archivo.
     */
    public List<String> selectRowList(int numRegistro) throws IOException {
        List<String> fila = new ArrayList<>();
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            rndFile.seek(numRegistro * this.longReg);
            for (int i = 0; i < campos.size(); i++) {
                byte[] buffer = new byte[camposLength.get(i)];
                rndFile.read(buffer);
                fila.add(new String(buffer, "UTF-8").trim());
            }
        }
        return fila;
    }

    /**
     * Devuelve los datos de un registro como un map.
     *
     * @param numRegistro El numero del registro que se quiere obtener como mapa.
     * @return Un mapa con los nombres de los campos y sus respectivos valores del registro especificado.
     * @throws IOException Si hay un error al leer el archivo.
     */
    public Map<String, String> selectRowMap(int numRegistro) throws IOException {
        Map<String, String> fila = new HashMap<>();
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            rndFile.seek(numRegistro * this.longReg);
            for (int i = 0; i < campos.size(); i++) {
                byte[] buffer = new byte[camposLength.get(i)];
                rndFile.read(buffer);
                fila.put(campos.get(i), new String(buffer, "UTF-8").trim());
            }
        }
        return fila;
    }

    /**
     * Actualiza todos los campos de un registro especifico con los valores proporcionados en un mapa.
     *
     * @param row    El numero del registro que se desea actualizar.
     * @param values Un map con los nombres de los campos y sus nuevos valores.
     * @throws IOException Si hay un error al escribir en el archivo.
     */
    public void update(int row, Map<String, String> values) throws IOException {
        Map<String, String> currentRow = selectRowMap(row);
        for (String campo : values.keySet()) {
            if (campos.contains(campo)) {
                currentRow.put(campo, values.get(campo));
            }
        }
        insertar(currentRow, row);
    }

    /**
     * Actualiza un campo especifico de un registro con un nuevo valor.
     *
     * @param row   El numero del registro que se desea actualizar.
     * @param campo El nombre del campo que se desea modificar.
     * @param valor El nuevo valor que se asignara al campo.
     * @throws IOException              Si hay un error al escribir en el archivo.
     * @throws IllegalArgumentException Si el nombre del campo no se encuentra.
     */
    public void update(int row, String campo, String valor) throws IOException {
        if (campos.contains(campo)) {
            Map<String, String> currentRow = selectRowMap(row);
            currentRow.put(campo, valor);
            insertar(currentRow, row);
        } else {
            throw new IllegalArgumentException("Campo no encontrado: " + campo);
        }
    }

    /**
     * Elimina (limpia) los datos de un registro especifico, dejando todos los campos vacios.
     *
     * @param row El numero del registro que se desea eliminar.
     * @throws IOException Si hay un error al escribir en el archivo.
     */
    public void delete(int row) throws IOException {
        Map<String, String> emptyRow = new HashMap<>();
        for (String campo : campos) {
            emptyRow.put(campo, "");
        }
        insertar(emptyRow, row);
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
                System.out.println(" ---- Seleccione una opcion ---- ");
                System.out.println("1~ Añadir registro");
                System.out.println("2~ Leer registro");
                System.out.println("3~ Modificar registro");
                System.out.println("4~ Eliminar registro");
                System.out.println("5~ Seleccionar campo");
                System.out.println("6~ Seleccionar columna");
                System.out.println("7~ Seleccionar fila como lista");
                System.out.println("8~ Seleccionar fila como mapa");
                System.out.println("9~ Actualizar registro");
                System.out.println("10~ Actualizar campo");
                System.out.println("11~ Salir");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 11) break;

                switch (opcion) {
                    case 1:
                        Map<String, String> nuevoReg = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("Introduce " + campo + ": ");
                            nuevoReg.put(campo, scanner.nextLine());
                        }
                        System.out.print("Introduce la posicion del registro a almacenar: ");
                        long pos = scanner.nextLong();
                        scanner.nextLine();
                        faa.insertar(nuevoReg, pos);
                        break;
                    case 2:
                        System.out.print("Introduce numero de registro a leer: ");
                        long numLeer = scanner.nextLong();
                        faa.leer(numLeer);
                        break;
                    case 3:
                        System.out.print("Introduce numero de registro a modificar: ");
                        long numModificar = scanner.nextLong();
                        scanner.nextLine();
                        Map<String, String> modifReg = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("Introduce " + campo + ": ");
                            modifReg.put(campo, scanner.nextLine());
                        }
                        faa.modificar(modifReg, numModificar);
                        break;
                    case 4:
                        System.out.print("Introduce numero de registro a eliminar: ");
                        int numEliminar = scanner.nextInt();
                        faa.delete(numEliminar);
                        break;
                    case 5:
                        System.out.print("Introduce numero de registro: ");
                        int numRegistroCampo = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Introduce nombre de columna: ");
                        String nomColumna = scanner.nextLine();
                        try {
                            String valorCampo = faa.selectCampo(numRegistroCampo, nomColumna);
                            System.out.println("Valor del campo: " + valorCampo);
                        } catch (IOException | IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.print("Introduce nombre de columna: ");
                        String columnaSeleccionada = scanner.nextLine();
                        try {
                            List<String> valoresColumna = faa.selectColumna(columnaSeleccionada);
                            System.out.println("Valores de la columna " + columnaSeleccionada + ": " + valoresColumna);
                        } catch (IOException | IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 7:
                        System.out.print("Introduce número de registro a seleccionar como lista: ");
                        int numRegistroLista = scanner.nextInt();
                        try {
                            List<String> filaLista = faa.selectRowList(numRegistroLista);
                            System.out.println("Datos de la fila: " + filaLista);
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 8:
                        System.out.print("Introduce numero de registro a seleccionar como mapa: ");
                        int numRegistroMapa = scanner.nextInt();
                        try {
                            Map<String, String> filaMapa = faa.selectRowMap(numRegistroMapa);
                            System.out.println("Datos de la fila: " + filaMapa);
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 9:
                        System.out.print("Introduce numero de registro a actualizar: ");
                        int numActualizar = scanner.nextInt();
                        scanner.nextLine();
                        Map<String, String> valoresActualizar = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("Introduce " + campo + ": ");
                            valoresActualizar.put(campo, scanner.nextLine());
                        }
                        try {
                            faa.update(numActualizar, valoresActualizar);
                            System.out.println("Registro actualizado con exito.");
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 10:
                        System.out.print("Introduce numero de registro a actualizar: ");
                        int numRegistroActualizar = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Introduce nombre del campo a actualizar: ");
                        String campoActualizar = scanner.nextLine();
                        System.out.print("Introduce nuevo valor: ");
                        String nuevoValor = scanner.nextLine();
                        try {
                            faa.update(numRegistroActualizar, campoActualizar, nuevoValor);
                            System.out.println("Campo actualizado con exito.");
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

