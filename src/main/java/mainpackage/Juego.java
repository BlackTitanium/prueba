package mainpackage;
import java.io.Serializable;
import java.util.Scanner;

public class Juego implements Serializable{
    private Partida partida;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Bienvenido a APOOcalipsis Zombi");
        System.out.println("Elige una opción:\n1. Iniciar partida\n2. Cargar partida\n3. Simulaciones\n4. Salir");
        int eleccion = scanner.nextInt();
        switch(eleccion){
            case 1:
                Juego juego = new Juego();
                juego.iniciarPartida();
                break;
            case 2:
                // Cargar partida
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
        System.out.println("Introduce el número de supervivientes (1 a 4): ");
        int nSupervivientes = scanner.nextInt();
        partida = new Partida(nSupervivientes);
    }
}
