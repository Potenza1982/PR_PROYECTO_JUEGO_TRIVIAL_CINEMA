package es.studium.trivialCinema;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;

/**
 * Clase que representa la interfaz gráfica del menú principal del juego.
 */
public class MenuPrincipalVista extends Frame {
	private static final long serialVersionUID = 1L;

	// Panel y elementos de la interfaz
	Panel juego = new Panel();
	Image mandoPlay;

	Button nueva = new Button("Nueva Partida");
	Button ranking = new Button("Top 10");
	Button ayuda = new Button("Ayuda");
	Button salir = new Button("Salir");

	Label labelTitulo = new Label("MENÚ PRINCIPAL");

	Toolkit herramienta;

	/**
	 * Constructor de la clase MenuPrincipalVista.
	 * Se encarga de inicializar la interfaz gráfica del menú principal del juego.
	 */
	MenuPrincipalVista() {
		// Configuración de la ventana principal
		setTitle("Juego de Memorizar");
		setSize(220, 200);
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setBackground(new Color(255, 204, 102)); // Establece el color de fondo a azul claro

		// Inicialización de la herramienta para cargar imágenes
		herramienta = getToolkit();

		// Carga de la imagen del botón de "Jugar"
		mandoPlay = herramienta.getImage("game-play.png");

		// Añadir el Label "MENÚ PRINCIPAL"
		Font fontTitulo = new Font("Arial", Font.BOLD, 20);
		labelTitulo.setFont(fontTitulo);
		add(labelTitulo);

		// Añadir los botones uno debajo del otro y centrados
		add(nueva);
		add(ranking);
		add(ayuda);

		// Añadir el botón "Salir" alineado a la derecha
		Panel panelSalir = new Panel();
		panelSalir.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelSalir.add(salir);

		// Añadir el Panel del botón "Salir"
		add(panelSalir);
	}

	/**
	 * Método que se llama automáticamente para dibujar componentes gráficos en la ventana.
	 * @param g Objeto Graphics utilizado para dibujar en la ventana.
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// Dibuja la imagen del mando de juego en las coordenadas especificadas
		g.drawImage(mandoPlay, 15, 30, this);
	}

}
