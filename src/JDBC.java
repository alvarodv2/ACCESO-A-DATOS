import java.sql.*;
import java.util.*;

public class JDBC {

    private static Connection conexion;

    public static void main(String[] args) {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccesoDatos", "root", "");

            // Probamos los métodos del ejercicio JDBC
            System.out.println("Campo (nombre) del registro 1: " + selectCampo(1, "nombre"));
            System.out.println("Todos los nombres: " + selectColumna("nombre"));
            System.out.println("Datos del registro 1: " + selectRowList(1));
            System.out.println("Datos del registro 1 (Map): " + selectRowMap(1));

            // Actualizamos un unico campo
            update("74744467X", "telefono", "987654321"); // Usa el DNI como identificador

            // Actualizamos varios campos con Map
            Map<String, String> updateFields = new HashMap<>();
            updateFields.put("nombre", "Carlos");
            updateFields.put("apellidos", "Gómez");
            updateFields.put("telefono", "987654321");
            update("74744467X", updateFields);

            // Eliminar un registro
            delete("74744467X");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodo 1: selectCampo
    public static String selectCampo(int numRegistro, String nomColumna) {
        String valor = "";
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT " + nomColumna + " FROM Cliente LIMIT ?, 1")) {
            stmt.setInt(1, numRegistro - 1);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                valor = rs.getString(nomColumna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valor;
    }

    // Metodo 2: selectColumna
    public static List<String> selectColumna(String nomColumna) {
        List<String> valores = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT " + nomColumna + " FROM Cliente")) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                valores.add(rs.getString(nomColumna));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valores;
    }

    // Metodo 3: selectRowList
    public static List<String> selectRowList(int numRegistro) {
        List<String> fila = new ArrayList<>();
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT nombre, apellidos, telefono, dni FROM Cliente LIMIT ?, 1")) {
            stmt.setInt(1, numRegistro - 1);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fila.add(rs.getString("nombre"));
                fila.add(rs.getString("apellidos"));
                fila.add(String.valueOf(rs.getInt("telefono")));
                fila.add(rs.getString("dni"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fila;
    }

    // Metodo 4: selectRowMap
    public static Map<String, String> selectRowMap(int numRegistro) {
        Map<String, String> fila = new HashMap<>();
        try (PreparedStatement stmt = conexion.prepareStatement("SELECT nombre, apellidos, telefono, dni FROM Cliente LIMIT ?, 1")) {
            stmt.setInt(1, numRegistro - 1);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fila.put("nombre", rs.getString("nombre"));
                fila.put("apellidos", rs.getString("apellidos"));
                fila.put("telefono", String.valueOf(rs.getInt("telefono")));
                fila.put("dni", rs.getString("dni"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fila;
    }

    // Metodo 5.1: update con Map - Modifica varios campos de un registro
    public static void update(String dni, Map<String, String> campos) {
        try {
            // Primero, comprobamos qué campos se deben actualizar
            StringBuilder query = new StringBuilder("UPDATE Cliente SET ");
            List<String> updates = new ArrayList<>();

            if (campos.containsKey("nombre")) {
                updates.add("nombre = ?");
            }
            if (campos.containsKey("apellidos")) {
                updates.add("apellidos = ?");
            }
            if (campos.containsKey("telefono")) {
                updates.add("telefono = ?");
            }

            query.append(String.join(", ", updates));
            query.append(" WHERE dni = ?");

            try (PreparedStatement stmt = conexion.prepareStatement(query.toString())) {
                int index = 1;
                if (campos.containsKey("nombre")) {
                    stmt.setString(index++, campos.get("nombre"));
                }
                if (campos.containsKey("apellidos")) {
                    stmt.setString(index++, campos.get("apellidos"));
                }
                if (campos.containsKey("telefono")) {
                    stmt.setInt(index++, Integer.parseInt(campos.get("telefono")));
                }
                stmt.setString(index, dni); // Usamos el DNI como identificador
                stmt.executeUpdate();
                System.out.println("Registro actualizado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo 5.2: update de un solo campo - Modifica un solo campo en un registro
    public static void update(String dni, String campo, String valor) {
        try (PreparedStatement stmt = conexion.prepareStatement("UPDATE Cliente SET " + campo + " = ? WHERE dni = ?")) {
            stmt.setString(1, valor);
            stmt.setString(2, dni); // Usamos el DNI como identificador
            stmt.executeUpdate();
            System.out.println("Campo actualizado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metodo 6: delete - Elimina un registro
    public static void delete(String dni) {
        try (PreparedStatement stmt = conexion.prepareStatement("DELETE FROM Cliente WHERE dni = ?")) {
            stmt.setString(1, dni); // Usamos el DNI como identificador
            stmt.executeUpdate();
            System.out.println("Registro eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
