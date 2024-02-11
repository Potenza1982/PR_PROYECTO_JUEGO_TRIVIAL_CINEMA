package es.studium.trivialCinema;

public class JuegoPrincipal {
    public static void main(String[] args) {
        // Patrón de Desarrollo: Modelo-Vista-Controlador (MVC)

        // Crear una instancia del modelo para el menú principal
        MenuPrincipalModelo modelo = new MenuPrincipalModelo(); // Tratamiento, datos en BBDD

        // Crear una instancia de la vista para el menú principal
        MenuPrincipalVista menuPrincipal = new MenuPrincipalVista(); // Vista Principal

        // Crear una instancia del controlador para el menú principal, pasando el modelo y la vista
        new MenuPrincipalControlador(modelo, menuPrincipal); // Acción, Listeners

        /* 
         * En esta clase solamente llamamos al modelo, a la vista y al controlador del menú principal.
         * El patrón MVC se utiliza para separar la lógica de la aplicación en tres componentes principales:
         *   - Modelo: Maneja la lógica de los datos y la interacción con la base de datos.
         *   - Vista: Representa la interfaz de usuario y muestra la información al usuario.
         *   - Controlador: Gestiona las interacciones del usuario y actualiza el modelo y la vista en consecuencia.
         * En este caso, el código inicializa estos componentes para el menú principal del juego.
         */
    }
}
