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

    public void setInterfaz(int index, InterfazPrincipal interfaz) {
        if (index < 0 || index >= interfaces.size()) {
            throw new IndexOutOfBoundsException("Invalid interface index: " + index);
        }
        interfaces.set(index, interfaz);
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

    public void setPartida(int index, Partida partida) {
        if (index < 0 || index >= partidas.size()) {
            throw new IndexOutOfBoundsException("Invalid partida index: " + index);
        }
        partidas.set(index, partida);
    }

    public int getContadorPartidas() {
        return contadorPartidas;
    }
}
