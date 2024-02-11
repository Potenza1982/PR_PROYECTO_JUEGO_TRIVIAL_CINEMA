package es.studium.trivialCinema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuPrincipalControlador implements WindowListener, ActionListener {
    MenuPrincipalModelo modelo;
    MenuPrincipalVista vista;

    // Constructor de la clase MenuPrincipalControlador
    MenuPrincipalControlador(MenuPrincipalModelo mo, MenuPrincipalVista me) {
        this.modelo = mo;   // Inicializa el modelo con la instancia proporcionada
        this.vista = me;    // Inicializa la vista con la instancia proporcionada

        // Agrega este controlador como WindowListener a la vista principal
        this.vista.addWindowListener(this);

        // Agrega este controlador como ActionListener a los botones relevantes
        this.vista.nueva.addActionListener(this);
        this.vista.ranking.addActionListener(this);
        this.vista.ayuda.addActionListener(this);
        this.vista.salir.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vista != null && vista.isActive()) {
            if (e.getSource() == vista.nueva) {
                // Lógica para el botón "Nueva Partida"
                TrivialModelo trivialModelo = new TrivialModelo();
                TrivialVista trivialVista = new TrivialVista();
                TrivialControlador trivialControlador = new TrivialControlador(trivialModelo, trivialVista);

                trivialVista.setVisible(true);
                vista.setVisible(false); // Puedes ocultar o cerrar la vista del menú principal si es necesario
            } else if (e.getSource() == vista.ranking) {
                // Lógica para el botón "Ranking"
                new RankingControlador(new RankingModelo(), new RankingVista());
            } else if (e.getSource() == vista.ayuda) {
                // Lógica para el botón "Ayuda"
                new Ayuda();
                System.out.println("Pulsaste Ayuda");
            } else if (e.getSource() == vista.salir) {
                // Lógica para el botón "Salir"
                System.exit(0);
            }
        }
    }

}
