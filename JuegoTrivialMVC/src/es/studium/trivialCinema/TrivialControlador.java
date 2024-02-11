package es.studium.trivialCinema;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

//Clase controladora para la lógica del juego Trivial
public class TrivialControlador implements WindowListener, ActionListener {
	TrivialModelo modelo;        // Instancia del modelo del Trivial
	TrivialVista menuPrincipal;  // Instancia de la vista principal del Trivial
	BaseDatos baseDatos;          // Instancia para la conexión a la base de datos
	int puntuacion = 0;           // Variable para almacenar la puntuación del jugador

	// Constructor de la clase TrivialControlador
	TrivialControlador(TrivialModelo mo, TrivialVista me) {
		this.modelo = mo;               // Inicializa el modelo con la instancia proporcionada
		this.menuPrincipal = me;        // Inicializa la vista con la instancia proporcionada

		// Inicializa la clase BaseDatos y establece la conexión
		baseDatos = new BaseDatos();
		baseDatos.conectar();

		// Agrega este controlador como WindowListener a la vista principal
		this.menuPrincipal.addWindowListener(this);

		// Agrega este controlador como ActionListener a los botones relevantes
		this.menuPrincipal.btnSiguiente.addActionListener(this);
		this.menuPrincipal.btnComprobar.addActionListener(this);
		this.menuPrincipal.btnSalirPartida.addActionListener(this);
		this.menuPrincipal.btnNuevaPartida.addActionListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	// Método para manejar eventos de acción
	@Override
	public void actionPerformed(ActionEvent e) {
		if (menuPrincipal != null) {
			if (e.getSource() == menuPrincipal.btnSiguiente) {
				// Verificar si el jugador ha respondido antes de cambiar de pregunta
				if (modelo.haRespondido()) {
					// Lógica para el botón "Siguiente" después de responder
					System.out.println("Botón SIGUIENTE pulsado");
					limpiarCampos(); // Llama al método para limpiar los campos

					// Asegurarse de que no se exceda el índice de la última imagen
					if (menuPrincipal.indiceImagenActual < menuPrincipal.rutasDeImagenes.length) {
						menuPrincipal.indiceImagenActual++;
					}

					// Deshabilitar el botón "Siguiente" si es la última imagen
					if (menuPrincipal.indiceImagenActual >= menuPrincipal.rutasDeImagenes.length - 1) {
						menuPrincipal.indiceImagenActual = menuPrincipal.rutasDeImagenes.length - 1;
						menuPrincipal.btnSiguiente.setEnabled(false);
					} else {
						menuPrincipal.btnSiguiente.setEnabled(true);
					}

					// Restablecer la bandera respondido
					modelo.resetearRespondido();
				} else {
					// Mostrar un diálogo o mensaje indicando que el jugador debe responder primero
					mostrarMensaje("Debes responder a la pregunta antes de pasar a la siguiente.");
				}
			} else if (e.getSource() == menuPrincipal.btnComprobar) {
				// Lógica para el botón "Comprobar"
				System.out.println("Botón COMPROBAR pulsado");

				// Verificar si el jugador ya ha respondido
				if (modelo.haRespondido()) {
					mostrarMensaje("Ya has respondido a esta pregunta. Avanza a la siguiente.");
				} else {
					// Obtener la respuesta ingresada por el jugador
					String respuestaIngresada = menuPrincipal.txtRespuestaPeli.getText();

					// Verificar si el campo de texto está vacío
					if (respuestaIngresada.trim().isEmpty()) {
						mostrarMensaje("Debes responder algo antes de comprobar.");
					} else {
						// Obtener la respuesta correcta desde la base de datos
						String respuestaCorrecta = baseDatos.obtenerRespuestaCorrecta(menuPrincipal.indiceImagenActual + 1); // Sumamos 1 para obtener el ID correcto

						// Comparar las respuestas sin importar mayúsculas o minúsculas
						if (respuestaIngresada.equalsIgnoreCase(respuestaCorrecta)) {
							// Si la respuesta ingresada es igual a la respuesta correcta (ignorando mayúsculas y minúsculas)
							menuPrincipal.txaRespuestaCorrectaPeli.setText("Respuesta Correcta");
							// Establecer el mensaje en el área de texto de respuesta correcta
							modelo.actualizarPuntuacion(1);
							// Actualizar la puntuación del modelo (asumiendo 1 punto por respuesta correcta)
							puntuacion++;
							// Incrementar la puntuación
							System.out.println("El jugador tiene " + puntuacion + " Puntos");
							// Imprimir la puntuación actual del jugador en la consola
						} else {
							// Si la respuesta ingresada no es igual a la respuesta correcta
							menuPrincipal.txaRespuestaCorrectaPeli.setText("Respuesta Incorrecta.\nLa respuesta correcta es: " + respuestaCorrecta);
							// Establecer el mensaje de respuesta incorrecta junto con la respuesta correcta en el área de texto
						}
						// Marcar que el jugador ha respondido
						modelo.marcarRespondido();
					}
				}

			} else if (e.getSource() == menuPrincipal.btnSalirPartida) {
				// Si la fuente del evento es el botón "Salir"
				// Lógica para el botón "Salir"
				mostrarDialogoConfirmacion();
				// Mostrar el diálogo de confirmación para salir del juego
			} else if (e.getSource() == menuPrincipal.btnNuevaPartida) {
				// Si la fuente del evento es el botón "Nueva Partida"
				// Lógica para el botón "Nueva Partida"
				mostrarDialogoNuevaPartida();
				// Mostrar el diálogo para confirmar iniciar una nueva partida
			}


			// Verificar si es la última pregunta para mostrar la puntuación final
			if (menuPrincipal.indiceImagenActual >= menuPrincipal.rutasDeImagenes.length - 1) {
				// Si el índice de la imagen actual es igual o mayor al último índice de las rutas de imágenes
				// Muestra la puntuación final
				mostrarPuntuacionFinal();
			} else {
				// Si no es la última pregunta
				// Cargar la nueva imagen
				menuPrincipal.imagenesPeliculas = menuPrincipal.herramienta.getImage(menuPrincipal.rutasDeImagenes[menuPrincipal.indiceImagenActual]);
				// Vuelve a pintar la ventana para mostrar la nueva imagen
				menuPrincipal.repaint();
			}

		}
	}

	// Método para mostrar la puntuación final
	private void mostrarPuntuacionFinal() {
		// Solicitar al jugador que ingrese su nombre
		String nombreJugador = JOptionPane.showInputDialog(menuPrincipal, "Ingresa tu nombre de jugador:", "Nombre de Jugador", JOptionPane.QUESTION_MESSAGE);

		// Verificar si se proporcionó un nombre no nulo y no vacío
		if (nombreJugador != null && !nombreJugador.trim().isEmpty()) {
			// Establecer el nombre del jugador en el modelo
			modelo.setNombreJugador(nombreJugador);

			// Finalizar la partida
			modelo.finalizarPartida();

			// Mostrar el diálogo con la puntuación final
			JOptionPane.showMessageDialog(menuPrincipal, "Fin de la partida. Tu puntuación es de " + modelo.getPuntuacion() + " puntos", "Puntuación Final", JOptionPane.INFORMATION_MESSAGE);

			// Mostrar el ranking de jugadores
			mostrarRanking();
		} else {
			// Si el jugador no ingresa un nombre, puedes manejarlo como desees.
			// Por ejemplo, mostrar un mensaje o permitir que ingresen el nombre nuevamente.
			mostrarMensaje("Debes ingresar un nombre para guardar tu puntuación.");
		}
	}

	private void mostrarRanking() {
		// Crea una nueva instancia del modelo de ranking y la vista del ranking
		RankingModelo modeloRanking = new RankingModelo();
		RankingVista vistaRanking = new RankingVista();

		// Crea una instancia del controlador del ranking y pásale el modelo y la vista
		RankingControlador controladorRanking = new RankingControlador(modeloRanking, vistaRanking);

		// Cierra la ventana actual del trivial
		menuPrincipal.setVisible(false);
		menuPrincipal.dispose();  // Libera los recursos asociados con la ventana actual
	}

	// Método para limpiar los campos de los TextArea
	private void limpiarCampos() {
		menuPrincipal.txtRespuestaPeli.setText("");
		menuPrincipal.txaRespuestaCorrectaPeli.setText("");
	}

	// Método para mostrar el diálogo de confirmación al salir del juego
	private void mostrarDialogoConfirmacion() {
		// Configura el diálogo de confirmación
		menuPrincipal.dlgConfirmacion.setLayout(new FlowLayout());
		menuPrincipal.dlgConfirmacion.setSize(300, 120);
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.lblConfirmacion);

		// Establece el texto del mensaje de confirmación
		menuPrincipal.lblConfirmacion.setText("¿Estás seguro de que quieres salir del juego?");
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.lblConfirmacion);

		// Agrega los componentes al diálogo
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.btnSi);
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.btnNo);

		// Configura el fondo y propiedades del diálogo
		menuPrincipal.dlgConfirmacion.setBackground(new Color(255, 204, 102));
		menuPrincipal.dlgConfirmacion.setResizable(false);
		menuPrincipal.dlgConfirmacion.setLocationRelativeTo(null);

		// Agrega el ActionListener para los botones del diálogo de confirmación
		menuPrincipal.btnSi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lógica para el botón "Sí" (volver al menú principal)
				menuPrincipal.dlgConfirmacion.setVisible(false);
				volverAlMenuPrincipal();
			}
		});

		menuPrincipal.btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lógica para el botón "No" (continuar en el juego)
				menuPrincipal.dlgConfirmacion.setVisible(false);
			}
		});

		// Agrega el WindowListener para cerrar el diálogo al hacer clic en la "X"
		menuPrincipal.dlgConfirmacion.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Lógica para cerrar el diálogo
				menuPrincipal.dlgConfirmacion.setVisible(false);
			}

			// Otros métodos de WindowListener
			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		// Hace visible el diálogo de confirmación
		menuPrincipal.dlgConfirmacion.setVisible(true);
	}


	// Método para mostrar el diálogo de nueva partida
	private void mostrarDialogoNuevaPartida() {
		// Configura el diseño del diálogo de confirmación
		menuPrincipal.dlgConfirmacion.setLayout(new FlowLayout());
		// Establece el tamaño del diálogo
		menuPrincipal.dlgConfirmacion.setSize(380, 120);

		// Establece el mensaje de confirmación para iniciar una nueva partida
		menuPrincipal.lblConfirmacion.setText("¿Estás seguro de que quieres empezar una nueva partida?");
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.lblConfirmacion);

		// Agrega los botones "Sí" y "No" al diálogo
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.btnSi);
		menuPrincipal.dlgConfirmacion.add(menuPrincipal.btnNo);

		// Configura el fondo y propiedades visuales del diálogo
		menuPrincipal.dlgConfirmacion.setBackground(new Color(255, 204, 102));
		menuPrincipal.dlgConfirmacion.setResizable(false);
		menuPrincipal.dlgConfirmacion.setLocationRelativeTo(null);

		// Agrega el ActionListener para los botones del diálogo de confirmación
		menuPrincipal.btnSi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lógica para el botón "Sí" (iniciar nueva partida)
				menuPrincipal.dlgConfirmacion.setVisible(false);
				comenzarNuevaPartida();
			}
		});

		menuPrincipal.btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lógica para el botón "No" (continuar en el juego)
				menuPrincipal.dlgConfirmacion.setVisible(false);
			}
		});

		// Agrega el WindowListener para cerrar el diálogo al hacer clic en la "X"
		menuPrincipal.dlgConfirmacion.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Lógica para cerrar el diálogo
				menuPrincipal.dlgConfirmacion.setVisible(false);
			}

			// Otros métodos de WindowListener
			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		// Hace visible el diálogo de confirmación
		menuPrincipal.dlgConfirmacion.setVisible(true);
	}


	// Método para comenzar una nueva partida
	private void comenzarNuevaPartida() {
		// Reinicia el índice de la imagen actual
		menuPrincipal.indiceImagenActual = 0;

		// Cargar la nueva imagen desde la ruta correspondiente al índice actual
		menuPrincipal.imagenesPeliculas = menuPrincipal.herramienta.getImage(menuPrincipal.rutasDeImagenes[menuPrincipal.indiceImagenActual]);

		// Limpia los campos de texto en la interfaz gráfica
		limpiarCampos();

		// Deshabilita el botón "Siguiente" si es la última imagen
		if (menuPrincipal.indiceImagenActual >= menuPrincipal.rutasDeImagenes.length - 1) {
			menuPrincipal.btnSiguiente.setEnabled(false);
		} else {
			// Habilita el botón "Siguiente" si no es la última imagen
			menuPrincipal.btnSiguiente.setEnabled(true);
		}

		// Vuelve a pintar la ventana para mostrar la nueva imagen
		menuPrincipal.repaint();
	}


	// Método para volver al menú principal
	private void volverAlMenuPrincipal() {
		// Cierra la ventana actual del trivial
		menuPrincipal.setVisible(false);
		menuPrincipal.dispose();  // Libera los recursos asociados con la ventana actual

		// Crea una nueva instancia del menú principal y la hace visible
		MenuPrincipalVista nuevoMenuPrincipal = new MenuPrincipalVista();
		nuevoMenuPrincipal.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);  // Cierra la aplicación si la ventana principal se cierra
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		// Crea un nuevo controlador para el menú principal y asocia el modelo y la vista
		new MenuPrincipalControlador(new MenuPrincipalModelo(), nuevoMenuPrincipal);

		// Hace visible la nueva instancia del menú principal
		nuevoMenuPrincipal.setVisible(true);
	}


	// Método para mostrar un mensaje al usuario
	private void mostrarMensaje(String mensaje) {
		// Configura el diálogo de mensaje
		menuPrincipal.dlgMensaje.setLayout(new FlowLayout());
		menuPrincipal.dlgMensaje.setSize(400, 120);
		menuPrincipal.dlgMensaje.add(menuPrincipal.lblMensaje);

		// Establece el texto del mensaje en el componente de etiqueta (label)
		menuPrincipal.lblMensaje.setText(mensaje);
		menuPrincipal.dlgMensaje.add(menuPrincipal.lblMensaje);
		menuPrincipal.dlgMensaje.setBackground(new Color(255, 204, 102));
		menuPrincipal.dlgMensaje.setResizable(false);
		menuPrincipal.dlgMensaje.setLocationRelativeTo(null);

		// Agrega el WindowListener para cerrar el diálogo al hacer clic en la "X"
		menuPrincipal.dlgMensaje.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// Lógica para cerrar el diálogo
				menuPrincipal.dlgMensaje.setVisible(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		// Hace visible el diálogo de mensaje
		menuPrincipal.dlgMensaje.setVisible(true);
	}

}