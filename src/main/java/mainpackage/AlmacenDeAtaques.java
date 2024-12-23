package mainpackage;

import java.util.ArrayList;

public class AlmacenDeAtaques{
    private ArrayList<String> historialAtaques;

    public AlmacenDeAtaques(){
        historialAtaques = new ArrayList<>();
    }

    public void guardarAtaque(String ataque){
        historialAtaques.add(ataque);
    }

    public ArrayList<String> getHistorialAtaques(){
        return(new ArrayList<>(historialAtaques));
    }

    public void mostrarHistorialAtaques(){
        for(String ataque : historialAtaques){
            System.out.println(ataque);
        }
    }
}