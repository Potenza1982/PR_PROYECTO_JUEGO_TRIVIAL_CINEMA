package es.studium.trivialCinema;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;

public class TrivialVista extends Frame
{
	private static final long serialVersionUID = 1L;

	// Para almacenar imágenes
	Image fondo, imagenesPeliculas;

	// Para mostrar la pregunta
	JLabel lblPregunta = new JLabel("¿CUÁL ES EL TÍTULO DE LA PELÍCULA?");

	// Para ingresar la respuesta
	TextField txtRespuestaPeli = new TextField(20);

	// Para mostrar la respuesta correcta (podrías considerar un nombre más descriptivo)
	TextArea txaRespuestaCorrectaPeli = new TextArea("", 3, 50, TextArea.SCROLLBARS_NONE);

	// Ventana de confirmación
	Dialog dlgConfirmacion = new Dialog(this, "Confirmación", true);

	// Botones y etiqueta en la ventana de confirmación
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");
	JLabel lblConfirmacion = new JLabel();

	// Declaración del JDialog para mostrar mensajes
	public JDialog dlgMensaje = new JDialog();
	public JLabel lblMensaje = new JLabel();

	// Inicializar botones
	Button btnSiguiente = new Button("Siguiente");

	Button btnNuevaPartida = new Button("Nueva Partida");
	Button btnSalirPartida = new Button("Salir");

	Button btnComprobar = new Button("Comprobar");

	Toolkit herramienta; // Una herramienta para cargar imágenes (como aprendimos el año anterior en clase)

	BaseDatos baseDatos; // Agrega una referencia a tu clase BaseDatos

	// Array que contiene las rutas de las imágenes de las películas
	String[] rutasDeImagenes = {
			"img_movies/pelicula_1.png",
			"img_movies/pelicula_2.png",
			"img_movies/pelicula_3.png",
			"img_movies/pelicula_4.png",
			"img_movies/pelicula_5.png",
			"img_movies/pelicula_6.png",
			"img_movies/pelicula_7.png",
			"img_movies/pelicula_8.png",
			"img_movies/pelicula_9.png",
			"img_movies/pelicula_10.png",
			"img_movies/pelicula_11.png"
	};

	// Índice de la imagen actual
	int indiceImagenActual = 0;


	TrivialVista()
	{
		System.out.println("Constructor de MenuPrincipal llamado"); // Mensaje de depuración.

		setTitle("TRIVIAL CINEMA");
		setSize(900, 500);
		setLayout(null); // Usar un diseño nulo para posicionar manualmente los elementos
		setBackground(Color.yellow);
		setResizable(false); // Deshabilita la capacidad de redimensionar la ventana.
		setLocationRelativeTo(null); // Centra la ventana en la pantalla.

		// Crea un objeto Font para especificar el tipo de letra, tamaño y estilo
		Font font = new Font("Arial Rounded", Font.BOLD, 16);

		// Establece la fuente en el TextField
		txtRespuestaPeli.setFont(font);
		txaRespuestaCorrectaPeli.setFont(font);

		// Establece el color del texto
		txtRespuestaPeli.setForeground(Color.black);
		txaRespuestaCorrectaPeli.setForeground(Color.black);

		// Establece el fondo del TextField txaRespuestaCorrectaPeli
		txaRespuestaCorrectaPeli.setBackground(Color.lightGray);  


		// Posiciona los botones en la interfaz gráfica, proporcionando coordenadas y tamaños específicos.
		btnSiguiente.setBounds(100, 430, 100, 30);
		btnNuevaPartida.setBounds(570, 380, 100, 30);
		btnSalirPartida.setBounds(570, 430, 100, 30);
		btnComprobar.setBounds(380, 230, 120, 30);

		// Agregar los botones al frame
		add(btnSiguiente);
		add(btnNuevaPartida);
		add(btnSalirPartida);
		add(btnComprobar);

		// Posicionar y agregar el TextArea en la interfaz gráfica
		txtRespuestaPeli.setBounds(310, 180, 250, 30); // (x, y, ancho, alto)
		txaRespuestaCorrectaPeli.setBounds(280, 280, 450, 60); // (x, y, ancho, alto)
		add(txtRespuestaPeli);
		add(txaRespuestaCorrectaPeli);

		// Inicializa la referencia a la clase BaseDatos
		baseDatos = new BaseDatos();

		// Establece la conexión antes de realizar operaciones en la base de datos
		baseDatos.conectar();

		// Inicializa las herramientas para manejar imágenes
		herramienta = getToolkit();
		fondo = herramienta.getImage("img/portadaVista2.jpg");
		imagenesPeliculas = herramienta.getImage(rutasDeImagenes[indiceImagenActual]);

		setVisible(true); // Hacer la ventana visible
	}

	@Override
	public void paint(Graphics g) {
	    super.paint(g); // Llama al método de la superclase para realizar tareas de pintura estándar

	    // Dibuja la imagen de fondo a lo largo de toda la ventana
	    g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
	    
	    // Dibuja la imagen de la película en una ubicación específica
	    g.drawImage(imagenesPeliculas, 40, 70, 220, 330, this);

	    // Configura el JLabel y agrégalo al frame
	    lblPregunta.setBounds(280, 120, 300, 30); // Establece las dimensiones y la posición del JLabel
	    lblPregunta.setOpaque(true); // Hace que el JLabel sea opaco
	    lblPregunta.setBackground(Color.black); // Establece el color de fondo del JLabel
	    lblPregunta.setForeground(Color.white); // Establece el color del texto del JLabel
	    Font fuente = new Font("Arial", Font.BOLD, 14); // Crea una nueva fuente
	    lblPregunta.setFont(fuente); // Aplica la fuente al JLabel
	    add(lblPregunta); // Agrega el JLabel al contenedor (frame)

	    // Ten en cuenta que este método se llama automáticamente cuando se necesita
	    // repintar la ventana y se ejecuta en el hilo de eventos gráficos.
	}

}
