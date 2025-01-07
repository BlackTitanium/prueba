package mainpackage;

import java.io.Serializable;

public class Provision extends Equipo implements Serializable{
    private int valorEnergetico;  
    private String caducidad;

    
    public Provision(int tipo) {
        switch (tipo) {
            case 1:  
                nombre = "Comida";
                valorEnergetico = 600;  
                caducidad = generarFecha();  
                break;
            case 2:  
                nombre = "Bebida";
                valorEnergetico = 200;  
                caducidad = generarFecha();  
                break;
            case 3:  
                nombre = "Medicinas";
                valorEnergetico = 800;  
                caducidad = generarFecha();  
                break;
            default:
                System.out.println("Tipo de provisión no válida.");
                
                break;
        }
    }

    
    public String getNombre() {
        return nombre;
    }

    public int getValorEnergetico() {
        return valorEnergetico;
    }

    public String getCaducidad() {
        return this.caducidad;
    }

    @Override
    public String toString() {
       return ("Nombre: " + nombre + "Valor energético: " + valorEnergetico + " kCal" +  "Caducidad: " + caducidad) ;
    }
    private String generarFecha(){
        int dia=(int)(Math.random()*31)+1;
         int mes=(int)(Math.random()*12)+1;
          int anio=(int)(Math.random()*10)+2025;
          return Integer.toString(dia) +"/"+Integer.toString(mes) +
                  Integer.toString(anio);
    }

}
