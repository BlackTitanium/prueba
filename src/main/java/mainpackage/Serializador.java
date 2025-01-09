package mainpackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializador implements Serializable {
    public static void serializarPartida(Partida partida, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(partida);
        }
    }

    public static Partida deserializarPartida(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Partida) ois.readObject();
        }
    }

    public static void serializarInterfaz(InterfazPrincipal interfaz, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(interfaz);
        }
    }

    public static InterfazPrincipal deserializarInterfaz(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (InterfazPrincipal) ois.readObject();
        }
    }
}
