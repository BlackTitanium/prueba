package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;

public class AlmacenPartidas implements Serializable {
    public ArrayList<Partida> partidas = new ArrayList<>();
    public ArrayList<InterfazPrincipal> interfaces = new ArrayList<>();

    private int contadorPartidas = 0;

    public void addInterfaz(InterfazPrincipal interfaz) {
        interfaces.add(interfaz);
    }

    public InterfazPrincipal getInterfaz(int index) {
        if (index < 0 || index >= interfaces.size()) {
            throw new IndexOutOfBoundsException("Invalid interface index: " + index);
        }
        return interfaces.get(index);
    }

    public void addPartida(Partida partida) {
        partidas.add(partida);
        contadorPartidas++;
    }

    public Partida getPartida(int index) {
        if (index < 0 || index >= partidas.size()) {
            throw new IndexOutOfBoundsException("Invalid partida index: " + index);
        }
        return partidas.get(index);
    }

    public int getContadorPartidas() {
        return contadorPartidas;
    }
}
