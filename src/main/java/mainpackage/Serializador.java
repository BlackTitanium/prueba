package mainpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializador implements Serializable {
    public static void serializarPartida(Partida partida, String filePath) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Create directories if they do not exist
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(partida);
            System.out.println("Partida serialized to " + filePath);
        } catch (IOException e) {
            System.err.println("Error serializing Partida: " + e.getMessage());
            throw e;
        }
    }

    public static Partida deserializarPartida(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("Partida deserialized from " + filePath);
            return (Partida) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing Partida: " + e.getMessage());
            throw e;
        }
    }

    public static void serializarInterfaz(InterfazPrincipal interfaz, String filePath) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Create directories if they do not exist
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(interfaz);
            System.out.println("Interfaz serialized to " + filePath);
        } catch (IOException e) {
            System.err.println("Error serializing Interfaz: " + e.getMessage());
            throw e;
        }
    }

    public static InterfazPrincipal deserializarInterfaz(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("Interfaz deserialized from " + filePath);
            return (InterfazPrincipal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing Interfaz: " + e.getMessage());
            throw e;
        }
    }

    public static void serializarAlmacenPartidas(AlmacenPartidas almacenPartidas, String filePath) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Create directories if they do not exist
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(almacenPartidas);
            System.out.println("AlmacenPartidas serialized to " + filePath);
        } catch (IOException e) {
            System.err.println("Error serializing AlmacenPartidas: " + e.getMessage());
            throw e;
        }
    }

    public static AlmacenPartidas deserializarAlmacenPartidas(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            System.out.println("AlmacenPartidas deserialized from " + filePath);
            return (AlmacenPartidas) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error deserializing AlmacenPartidas: " + e.getMessage());
            throw e;
        }
    }
}
