import java.sql.*;
import java.util.*;

public class JDBC3 {

    public static void main(String[] args) {
        // Ejemplo de uso de los métodos
        List<List<String>> licencias = new ArrayList<>();
        List<String> licencia1 = Arrays.asList("A1", "2024-01-01 00:00:00", "2029-01-01 00:00:00");
        List<String> licencia2 = Arrays.asList("B1", "2024-01-01 00:00:00", "2029-01-01 00:00:00");
        licencias.add(licencia1);
        licencias.add(licencia2);

        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccesoDatos", "root", "")) {
            insertLicencias(conexion, "Juan", "Pérez", "123456789", "12345678X", licencias);
            eliminarLicencias(conexion, "12345678X");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean insertLicencias(Connection conexion, String nombre, String apellidos, String telefono, String dni, List<List<String>> licencias) throws SQLException {
        conexion.setAutoCommit(false);
        try {
            // Modificado para usar TYPE_SCROLL_INSENSITIVE
            String insertUserSQL = "INSERT INTO Cliente (nombre, apellidos, telefono, dni) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmtUser = conexion.prepareStatement(insertUserSQL,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY,
                    Statement.RETURN_GENERATED_KEYS)) {

                stmtUser.setString(1, nombre);
                stmtUser.setString(2, apellidos);
                stmtUser.setString(3, telefono);
                stmtUser.setString(4, dni);
                stmtUser.executeUpdate();

                ResultSet rs = stmtUser.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    String insertLicenciaSQL = "INSERT INTO licencias (ID, TIPO, EXPEDICION, CADUCIDAD) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmtLicencia = conexion.prepareStatement(insertLicenciaSQL,
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {

                        for (List<String> licencia : licencias) {
                            stmtLicencia.setInt(1, userId);
                            stmtLicencia.setString(2, licencia.get(0));
                            stmtLicencia.setString(3, licencia.get(1));
                            stmtLicencia.setString(4, licencia.get(2));
                            stmtLicencia.executeUpdate();
                        }
                    }
                }
            }

            conexion.commit();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Error de integridad: posible duplicación de datos o violación de restricciones.");
            conexion.rollback();
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("Error en la transacción de inserción de licencias.");
            conexion.rollback();
            e.printStackTrace();
            return false;
        } finally {
            conexion.setAutoCommit(true);
        }
    }

    public static boolean eliminarLicencias(Connection conexion, String dni) throws SQLException {
        conexion.setAutoCommit(false);
        try {
            // Modificado para usar TYPE_SCROLL_INSENSITIVE
            String selectIdSQL = "SELECT id FROM Cliente WHERE dni = ?";
            try (PreparedStatement stmtSelect = conexion.prepareStatement(selectIdSQL,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {

                stmtSelect.setString(1, dni);
                ResultSet rs = stmtSelect.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");

                    String deleteLicenciasSQL = "DELETE FROM licencias WHERE ID = ?";
                    try (PreparedStatement stmtDelete = conexion.prepareStatement(deleteLicenciasSQL,
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {

                        stmtDelete.setInt(1, userId);
                        stmtDelete.executeUpdate();
                    }
                }
            }

            conexion.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en la transacción de eliminación de licencias.");
            conexion.rollback();
            e.printStackTrace();
            return false;
        } finally {
            conexion.setAutoCommit(true);
        }
    }
}
