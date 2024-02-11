package es.studium.trivialCinema;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class RankingControlador implements WindowListener {
    RankingModelo modelo; // Modelo asociado al ranking
    RankingVista vista; // Vista del ranking
    private boolean ranking; // Variable para controlar si ya se ha mostrado el ranking

    RankingControlador(RankingModelo mo, RankingVista vi) {
        modelo = mo;
        vista = vi;
        vista.addWindowListener(this);
        ranking = false; // Inicializa la variable de control del ranking a falso al inicio
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        vista.setVisible(false); // Oculta la ventana del ranking al cerrarla
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
        if (!ranking) { // Si aún no se ha mostrado el ranking
            vista.setVisible(false); // Oculta la ventana del ranking antes de cargar los datos
            vista.cargarTabla(modelo.obtenerTopTen()); // Carga el top ten en la tabla de la vista
            vista.setVisible(true); // Muestra la ventana del ranking
            ranking = true; // Actualiza la variable de control para indicar que se ha mostrado el ranking

            // Obtener una instancia de TrivialModelo
            TrivialModelo trivialModelo = new TrivialModelo();

            // Añadir la puntuación del jugador al ranking si es mayor que cero
            try {
                if (trivialModelo.getPuntuacion() > 0) {
                    modelo.agregarPuntuacion("Jugador", trivialModelo.getPuntuacion());
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
