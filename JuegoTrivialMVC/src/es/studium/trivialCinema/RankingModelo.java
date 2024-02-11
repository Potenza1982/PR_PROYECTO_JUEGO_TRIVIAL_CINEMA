package es.studium.trivialCinema;

public class RankingModelo {
    BaseDatos bd; // Instancia de la clase BaseDatos para interactuar con la base de datos

    public RankingModelo() {
        bd = new BaseDatos(); // Inicializa la instancia de BaseDatos en el constructor
    }

    // Método para obtener los datos del top ten desde la base de datos
    public String[][] obtenerTopTen() {
        String[][] datos;
        bd.conectar(); // Establece la conexión con la base de datos
        datos = bd.obtenerTopTen(); // Obtiene los datos del top ten llamando al método en la instancia de BaseDatos
        bd.desconectar(); // Cierra la conexión con la base de datos
        return datos; // Devuelve los datos obtenidos del top ten
    }

    // Nuevo método para agregar una puntuación al ranking
    public void agregarPuntuacion(String nombre, int d) {
        // Lógica para agregar la puntuación al ranking en la base de datos
        bd.conectar(); // Establece la conexión con la base de datos
        bd.guardarPuntuacion(nombre, d); // Guarda la puntuación llamando al método en la instancia de BaseDatos
        bd.desconectar(); // Cierra la conexión con la base de datos
    }
}
