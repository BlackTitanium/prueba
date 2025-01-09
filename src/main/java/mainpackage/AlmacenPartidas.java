package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;

public class AlmacenPartidas implements Serializable {
    public static ArrayList<Partida> partidas = new ArrayList<>();
    public static ArrayList<InterfazPrincipal> interfaces = new ArrayList<>();
    
    private static int contadorPartidas = 0;

    public void addPartida(Partida partida) {
        partidas.add(partida);
        contadorPartidas++;
    }

    public Partida getPartida(int index) {
        return partidas.get(index);
    }

    public int getContadorPartidas() {
        return contadorPartidas;
    }
}
