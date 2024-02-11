package es.studium.trivialCinema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BaseDatos {
    // Definición de los parámetros de conexión a la base de datos
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/trivialcinema";
    String userDB = "root";
    String passwordDB = "Studium2023";

    // Objetos para la gestión de la conexión y las consultas SQL
    Connection connection = null;

    // Método para establecer la conexión a la base de datos
    public void conectar() {
        try {
            // Cargar el controlador JDBC
            Class.forName(driver);
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection(url, userDB, passwordDB);
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para cerrar la conexión a la base de datos
    public void desconectar() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener los diez mejores registros de la tabla puntuaciones
    public String[][] obtenerTopTen() {
        // Consulta SQL para obtener los nombres y puntuaciones de los diez mejores jugadores
        String sentencia = "SELECT nombreJugador, puntuacionJugador FROM puntuaciones ORDER BY puntuacionJugador DESC LIMIT 10";
        // Matriz para almacenar los resultados de la consulta
        String[][] matrizResultados = new String[10][2];

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sentencia)) {

            // ArrayList para almacenar dinámicamente los resultados de la consulta
            ArrayList<String[]> resultadosList = new ArrayList<>();

            // Recorrer el resultado de la consulta
            while (resultSet.next()) {
                // Obtener el nombre y la puntuación del jugador
                String nombre = resultSet.getString("nombreJugador");
                int puntuacion = resultSet.getInt("puntuacionJugador");
                // Crear un array con el nombre y la puntuación
                String[] resultado = {nombre, String.valueOf(puntuacion)};
                // Agregar el resultado al ArrayList
                resultadosList.add(resultado);
            }

            // Verificar si la lista de resultados está vacía
            if (resultadosList.isEmpty()) {
                matrizResultados = new String[1][2];
                matrizResultados[0][0] = "NO PLAYERS";
                matrizResultados[0][1] = "0";
            } else {
                // Si hay resultados, actualizar la matrizResultados con los valores del ArrayList
                matrizResultados = new String[resultadosList.size()][2];
                for (int i = 0; i < resultadosList.size(); i++) {
                    String[] resultado = resultadosList.get(i);
                    matrizResultados[i][0] = resultado[0];
                    matrizResultados[i][1] = resultado[1];
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los mejores registros: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver la matriz con los resultados
        return matrizResultados;
    }

    // Método para obtener la última puntuación registrada
    public int obtenerUltimoTopTen() {
        // Consulta SQL para obtener la puntuación del jugador con la puntuación más alta
        String sentencia = "SELECT puntuacionJugador FROM puntuaciones ORDER BY puntuacionJugador DESC LIMIT 1";
        // Variable para almacenar el resultado
        int resultado = -1;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sentencia)) {

            // Verificar si hay algún resultado en el ResultSet
            if (resultSet.next()) {
                // Obtener la puntuación del jugador
                resultado = resultSet.getInt("puntuacionJugador");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la última puntuación: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver el resultado
        return resultado;
    }

    // Método para guardar una nueva puntuación en la base de datos
    public int guardarPuntuacion(String nombre, double puntos) {
        // Sentencia SQL para insertar un nuevo registro en la tabla puntuaciones
        String sentencia = "INSERT INTO puntuaciones (nombreJugador, puntuacionJugador) VALUES ('" + nombre + "'," + puntos + ")";
        // Variable para almacenar el resultado de la operación
        int resultado = -1;

        try (Statement statement = connection.createStatement()) {
            // Ejecutar la sentencia SQL para insertar el nuevo registro
            statement.executeUpdate(sentencia);
            // Asignar el valor 0 a resultado si la ejecución es exitosa
            resultado = 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar la puntuación: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver el resultado de la operación
        return resultado;
    }

    // Método para obtener la respuesta correcta según el índice de la imagen actual
    public String obtenerRespuestaCorrecta(int id) {
        // Consulta SQL para obtener la respuesta correcta
        String sentencia = "SELECT respuesta_correcta FROM respuestas_correctas WHERE id_respuesta = " + id;
        // Variable para almacenar el resultado
        String respuestaCorrecta = "";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sentencia)) {

            // Verificar si hay algún resultado en el ResultSet
            if (resultSet.next()) {
                // Obtener la respuesta correcta
                respuestaCorrecta = resultSet.getString("respuesta_correcta");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la respuesta correcta: " + e.getMessage());
            e.printStackTrace();
        }

        // Devolver la respuesta correcta
        return respuestaCorrecta;
    }
}
