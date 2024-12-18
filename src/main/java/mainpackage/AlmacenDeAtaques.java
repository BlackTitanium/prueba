package mainpackage;

import java.util.ArrayList;
import java.util.Iterator;

public class AlmacenDeAtaques{
    private ArrayList<String> historialAtaques;

    public AlmacenDeAtaques(){
        historialAtaques = new ArrayList<>();
    }

    public void guardarAtaque(String ataque){
        historialAtaques.add(ataque);
    }

    public ArrayList<String> getHistorialAtaques(){
        return(historialAtaques);
    }

    public void mostrarHistorialAtaques(){
        Iterator<String> i = historialAtaques.iterator();
        while(i.hasNext()){
            String aux = i.next();
            System.out.println(aux);
        }
    }
}