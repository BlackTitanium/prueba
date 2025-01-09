package mainpackage;
import java.io.IOException;
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
        System.out.println("Bienvenido a APOOcalipsis Zombi");
        System.out.println("Elige una opción:\n1. Iniciar partida\n2. Cargar partida\n3. Simulaciones\n4. Salir");
        int eleccion = scanner.nextInt();
        switch(eleccion){
            case 1:
                iniciarPartida();
                break;
            case 2:
                cargarPartida();
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
//        interfazPrincipal = new InterfazPrincipal();
        partida = new Partida(almacenPartidas);
    }

    public void cargarPartida(){
        try {
            almacenPartidas = Serializador.deserializarAlmacenPartidas("bin/almacenPartidas.ser");
            System.out.println("Introduce el ID de la partida que quieres cargar");
            int idPartida = scanner.nextInt();
            partida = almacenPartidas.getPartida(idPartida);
            interfazPrincipal = partida.getInterfazPrincipal();
            interfazPrincipal.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            almacenPartidas = new AlmacenPartidas();
            System.out.println("No se ha encontrado ninguna partida guardada");
            mostrarMenu();
        }
        
    }
}
