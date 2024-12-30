package mainpackage;

import java.util.ArrayList;

public class Casilla{

    private int x, y, contadorZombis, contadorSupervivientes;
    public ArrayList<Zombi> zombis;
    public ArrayList<Superviviente> supervivientes;

    public Casilla(int a, int b){
        x = a; y = b;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
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

    public Entidad getSuperviviente(){
        return supervivientes.get(0);
    }

    public Entidad getZombi(){
        return zombis.get(0);
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
    
    public void removeZombie(Zombi z){
        if (zombis.remove(z)) {
            contadorZombis--;
        }
    }
}