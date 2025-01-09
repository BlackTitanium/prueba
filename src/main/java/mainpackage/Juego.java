package mainpackage;
import java.io.Serializable;
import java.util.Scanner;

public class Juego implements Serializable{
    private Partida partida;
    private static Scanner scanner = new Scanner(System.in);
    private InterfazPrincipal interfazPrincipal;
    private Serializador serializador = new Serializador();
    private AlmacenPartidas almacenPartidas;

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.mostrarMenu();
    }
    public void mostrarMenu() {
        try { 
            almacenPartidas = Serializador.deserializarAlmacenPartidas("bin/almacenpartidas.ser");
        } catch (Exception e) {
            almacenPartidas = new AlmacenPartidas();
        }
        System.out.println("Bienvenido a APOOcalipsis Zombi");
        System.out.println("Elige una opción:\n1. Iniciar partida\n2. Cargar partida\n3. Simulaciones\n4. Salir");
        int eleccion = scanner.nextInt();
        switch(eleccion){
            case 1:
                iniciarPartida();
                break;
            case 2:
                System.out.println("Introduce el ID de partida a cargar: ");
                int a = scanner.nextInt();
                cargarPartida(a);
                break;
            case 3:
                // Simulaciones
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        main(new String[]{"0"});
    }
    public void iniciarPartida(){
        partida = new Partida(almacenPartidas);
    }

    public void cargarPartida(int a){
        try {
            if (a < 0 || a >= almacenPartidas.getContadorPartidas()) {
                throw new IndexOutOfBoundsException("Invalid game ID: " + a);
            }
            partida = almacenPartidas.getPartida(a);
            interfazPrincipal = almacenPartidas.getInterfaz(a);
            interfazPrincipal.partida = partida;
            partida.setInterfazPrincipal(interfazPrincipal);
            //partida.setTablero(new Tablero(partida)); // Reinitialize the tablero
            partida.setTablero(partida.getTablero());
            interfazPrincipal.setVisible(true); // Ensure the interface is visible
            partida.activarActionListeners();
            partida.reiniciarJuego();
            System.out.println("Partida and Interfaz deserialized successfully.");
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }
}
