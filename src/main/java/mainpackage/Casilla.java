package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Casilla implements Serializable{

    private int x, y, contadorZombis, contadorSupervivientes;
    public ArrayList<Zombi> zombis;
    public ArrayList<Superviviente> supervivientes;
    private static Random random = new Random();
    private boolean buscada = false;

    public Casilla(int a, int b){
        zombis = new ArrayList<>();
        supervivientes = new ArrayList<>();
        x = a; y = b;
    }
    public void setSupervivientes(ArrayList<Superviviente> supervivientes){
        this.supervivientes = supervivientes;
    }

    public void setContadorSupervivientes(int contadorSupervivientes){
        this.contadorSupervivientes = contadorSupervivientes;
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

    public Superviviente getSuperviviente(int a){
        return supervivientes.get(a);
    }

    public Zombi getZombi(int a){
        return zombis.get(a);
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

    public Equipo buscar(InterfazPrincipal interfazPrincipal){
        int a = random.nextInt(2);
        if (buscada == true){
            interfazPrincipal.mostrarMensaje("Ya has buscado en esta casilla");
            return null;
        }
        if (a == 0){
            buscada = true;
            return new Arma();
        }else{
            buscada = true;
            return new Provision();
        }
    }
}