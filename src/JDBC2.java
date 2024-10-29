import java.sql.*;
import java.util.*;

public class JDBC2 {
    private static Connection conexion;

    public static void main(String[] args) {
        try {
            // Establecer conexión
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/AccesoDatos", "root", "");

            // Ejemplo de uso de los nuevos métodos
            // Crear lista de licencias para insertar
            List<List<String>> licencias = new ArrayList<>();
            List<String> licencia1 = Arrays.asList("A1", "2024-01-01 00:00:00", "2029-01-01 00:00:00");
            List<String> licencia2 = Arrays.asList("B1", "2024-01-01 00:00:00", "2029-01-01 00:00:00");
            licencias.add(licencia1);
            licencias.add(licencia2);

            // Insertar usuario con licencias (nombre, apellidos, telefono, dni)
            insertLicencias("Juan", "Pérez", "123456789", "12345678X", licencias);

            // Eliminar licencias
            eliminarLicencias("12345678X");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // Metodo para insertar un usuario con sus licencias
    public static boolean insertLicencias(String nombre, String apellidos, String telefono, String dni, List<List<String>> licencias) throws SQLException {
        conexion.setAutoCommit(false);
        try {
            // Insertar usuario
            String insertUserSQL = "INSERT INTO Cliente (nombre, apellidos, telefono, dni) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmtUser = conexion.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, nombre);
                stmtUser.setString(2, apellidos);
                stmtUser.setString(3, telefono);
                stmtUser.setString(4, dni);
                stmtUser.executeUpdate();

                // Obtener el ID generado
                ResultSet rs = stmtUser.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);

                    // Insertar licencias
                    String insertLicenciaSQL = "INSERT INTO licencias (ID, TIPO, EXPEDICION, CADUCIDAD) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmtLicencia = conexion.prepareStatement(insertLicenciaSQL)) {
                        for (List<String> licencia : licencias) {
                            stmtLicencia.setInt(1, userId);
                            stmtLicencia.setString(2, licencia.get(0));  // TIPO
                            stmtLicencia.setString(3, licencia.get(1));  // EXPEDICION
                            stmtLicencia.setString(4, licencia.get(2));  // CADUCIDAD
                            stmtLicencia.executeUpdate();
                        }
                    }
                }
            }

            conexion.commit();
            return true;
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error durante rollback: " + rollbackEx.getMessage());
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // El método eliminarLicencias también necesita actualizarse
    public static boolean eliminarLicencias(String dni) throws SQLException {
        conexion.setAutoCommit(false);
        try {
            // Primero obtener el ID del usuario
            String selectIdSQL = "SELECT id FROM Cliente WHERE dni = ?";
            try (PreparedStatement stmtSelect = conexion.prepareStatement(selectIdSQL)) {
                stmtSelect.setString(1, dni);
                ResultSet rs = stmtSelect.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");

                    // Eliminar licencias
                    String deleteLicenciasSQL = "DELETE FROM licencias WHERE ID = ?";
                    try (PreparedStatement stmtDelete = conexion.prepareStatement(deleteLicenciasSQL)) {
                        stmtDelete.setInt(1, userId);
                        stmtDelete.executeUpdate();
                    }
                }
            }

            conexion.commit();
            return true;
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error durante rollback: " + rollbackEx.getMessage());
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeConnection() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}