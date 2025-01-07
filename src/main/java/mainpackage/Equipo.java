package mainpackage;

import java.io.Serializable;

public class Equipo implements Serializable{
    protected String nombre;

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }
   
}