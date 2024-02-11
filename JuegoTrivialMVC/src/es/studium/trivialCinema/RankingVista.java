package es.studium.trivialCinema;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RankingVista extends Frame {
    private static final long serialVersionUID = 1L;

    // Array con los encabezados de las columnas del ranking
    String header[] = { "Nombre de Jugadores", "Puntuaciones" };

    // Representa la tabla del ranking
    JTable tabla;

    RankingVista() {
        setTitle("Ranking"); // Título de la ventana
        setSize(280, 280); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new BorderLayout()); // Establece un diseño de borde

        // Configuración de la ventana
        setResizable(false); // No permite redimensionar la ventana

        // Panel superior con la etiqueta "RANKING"
        JPanel centrado = new JPanel();
        centrado.setAlignmentX(CENTER_ALIGNMENT);
        centrado.add(new JLabel("RANKING"));
        add(centrado, BorderLayout.NORTH);

        // Configuración de la tabla
        Color colorFondo = new Color(255, 204, 102); // Color azul claro
        tabla = new JTable() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public void setBackground(Color color) {
                super.setBackground(color);
                // Asegura que el color de fondo de las celdas sea el mismo que el color de fondo de la tabla
                if (color.equals(getSelectionBackground())) {
                    super.setBackground(color);
                }
            }
        };
        tabla.setBackground(colorFondo);

        // Configuración del color de texto centrado para las celdas de la tabla
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.setDefaultRenderer(Object.class, centerRenderer);

        // Configuración del panel con barra de desplazamiento para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Configuración del color de fondo para el panel principal
        centrado.setBackground(colorFondo);
        setVisible(true); // Hace visible la ventana
    }

    // Método para cargar datos en la tabla del ranking
    public void cargarTabla(String[][] obtenerTopTen) {
        tabla.setModel(new javax.swing.table.DefaultTableModel(obtenerTopTen, header) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
    }
}
