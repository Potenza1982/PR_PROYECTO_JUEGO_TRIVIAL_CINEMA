package es.studium.trivialCinema;

//Clase TrivialModelo: Maneja la lógica y la interacción con la base de datos para el juego Trivial

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TrivialModelo {
	private Connection conexion;    // Objeto para la conexión a la base de datos
	private int puntuacion;         // Almacena la puntuación del jugador
	private boolean respondido = false;  // Bandera que indica si el jugador ha respondido

	// Constructor de la clase TrivialModelo
	public TrivialModelo() {
		conectarBaseDatos();    // Inicia la conexión a la base de datos al crear una instancia del modelo
		puntuacion = 0;        // Inicializa la puntuación del jugador en cero al inicio del juego
	}

	// Método privado para establecer la conexión a la base de datos
	private void conectarBaseDatos() {
		try {
			// Cargar el controlador de la base de datos MySQL
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Parámetros para la conexión a la base de datos
			String url = "jdbc:mysql://localhost:3306/trivialcinema?useSSL=false";
			String usuario = "root";
			String contraseña = "Studium2023";

			// Establecer la conexión
			conexion = DriverManager.getConnection(url, usuario, contraseña);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// Método para obtener la respuesta correcta de una pregunta en la base de datos
	public String obtenerRespuestaCorrecta(int idPregunta) {
		String respuestaCorrecta = "";  // Variable para almacenar la respuesta correcta
		String query = "SELECT respuesta_correcta FROM respuestas_correctas WHERE id_respuesta = ?";  // Consulta SQL

		try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
			// Establece el parámetro (ID de la pregunta) en la consulta preparada
			preparedStatement.setInt(1, idPregunta);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				// Ejecuta la consulta y obtiene el conjunto de resultados
				if (resultSet.next()) {
					// Si hay al menos una fila en los resultados
					respuestaCorrecta = resultSet.getString("respuesta_correcta");  // Obtiene la respuesta correcta
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();  // Manejo de excepciones: imprime detalles en caso de error
		}

		return respuestaCorrecta;  // Devuelve la respuesta correcta obtenida de la base de datos
	}


	// Método para obtener la puntuación actual
	public int getPuntuacion() {
		return puntuacion;  // Devuelve la puntuación almacenada en el modelo
	}

	// Método para actualizar la puntuación sumando la cantidad de puntos proporcionada
	public void actualizarPuntuacion(int puntos) {
		puntuacion += puntos;  // Incrementa la puntuación actual con los puntos proporcionados
	}

	// Método para marcar que el jugador ha respondido
	public void marcarRespondido() {
		respondido = true;  // Establece la bandera respondido como verdadera
	}

	// Método para verificar si el jugador ha respondido
	public boolean haRespondido() {
		return respondido;  // Devuelve el estado de la bandera respondido
	}

	// Nuevo método para restablecer la bandera respondido a falso
	public void resetearRespondido() {
		respondido = false;  // Establece la bandera respondido como falsa
	}

	// Campo para almacenar el nombre del jugador
	private String nombreJugador;

	// Método para establecer el nombre del jugador
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;  // Asigna el nombre del jugador al campo correspondiente
	}

	// Modificar el método para finalizar la partida
	public void finalizarPartida() {
		// Actualiza la puntuación en la tabla puntuaciones
		try {
			String query = "INSERT INTO puntuaciones (nombreJugador, puntuacionJugador) VALUES (?, ?)";
			try (PreparedStatement preparedStatement = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				preparedStatement.setString(1, nombreJugador); // Utiliza setString para el nombre del jugador
				preparedStatement.setInt(2, puntuacion);       // Utiliza setInt para la puntuación
				preparedStatement.executeUpdate();

				// Obtiene el ID generado automáticamente
				ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int idPuntuacion = generatedKeys.getInt(1);
					System.out.println("Se ha insertado el registro con ID: " + idPuntuacion);
				} else {
					System.out.println("No se pudo obtener el ID del registro insertado.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Método para cerrar la conexión a la base de datos
	public void cerrarConexion() {
		try {
			if (conexion != null) {
				conexion.close();  // Cierra la conexión si no es nula
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}