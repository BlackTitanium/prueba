package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Casilla implements Serializable{

    private int x, y, contadorZombis, contadorSupervivientes;
    public static ArrayList<Zombi> zombis;
    public static ArrayList<Superviviente> supervivientes;
    private static Random random = new Random();
    private boolean buscada = false;

    public Casilla(int a, int b){
        x = a; y = b;
    }

    public static void inicializarArrayList(){
        zombis = new ArrayList<>();
        supervivientes = new ArrayList<>();
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void reiniciarCasilla(){
        contadorSupervivientes = 0;
        contadorZombis = 0;
        supervivientes.clear();
        zombis.clear();
    }

    public int getContadorZombis(){
        return contadorZombis;
    }
    public int getContadorSupervivientes(){
        return contadorSupervivientes;
    }
    public void addZombi(Zombi z){
        zombis.add(z);
        contadorZombis++;
    }
    public void addSuperviviente(Superviviente s){
        supervivientes.add(s);
        contadorSupervivientes++;
    }

    public Superviviente getSuperviviente(){
        return supervivientes.get(0);
    }

    public Zombi getZombi(int a){
        return zombis.get(a);
    }

    @Override

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Casilla casilla = (Casilla) obj;
        return this.x == casilla.x && this.y == casilla.y;
    }
    
    public void removeSuperviviente(Superviviente s) {
        if (supervivientes.remove(s)) { // remove(s) busca y elimina el superviviente "s" en "supervivientes" y devuelve "true" si lo logra
            contadorSupervivientes--;
        }
    }
    
    public void removeZombi(Zombi z){
        if (zombis.remove(z)) {
            contadorZombis--;
        }
    }

    public void removeEntidad(Entidad e){
        if(e instanceof Zombi){
            removeZombi((Zombi) e);
        }else if(e instanceof Superviviente){
            removeSuperviviente((Superviviente) e);
        }
    }

    public void addEntidad(Entidad e){
        if(e instanceof Zombi){
            addZombi((Zombi) e);
        }else if(e instanceof Superviviente){
            addSuperviviente((Superviviente) e);
        }
    }

    public Equipo buscar(){
        int a = random.nextInt(2);
        int b = random.nextInt(3);
        if (buscada == true){
            return null;
        }
        if (a == 0){
            buscada = true;
            return new Arma(b+1);
        }else{
            buscada = true;
            return new Provision(b+1);
        }
    }
}