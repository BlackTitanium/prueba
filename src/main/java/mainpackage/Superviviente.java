package mainpackage;

public class Superviviente extends Entidad{
    private String nombre;
    private int contadorZombis, mordeduras, acciones = 3;
    private accion seleccion;
    private Arma arma;

   
    private enum estado {VIVO, MUERTO};
    private estado estadoActual;
    private Equipo[] inventario; 

    public Superviviente(String nombre){
        this.nombre = nombre;
        this.contadorZombis = 0;
        this.mordeduras = 0;
        this.estadoActual = estado.VIVO;
    }

    public void addMordeduras(){
        mordeduras++;
    }

    @Override
    public void activar() {
        if (estadoActual == estado.VIVO) {
            acciones=3;
        }    if(seleccion==accion.MOVER){

         }
    }

    
    public void mover(int x, int y) {
        
        super.mover(x, y);
        for (int i=0; i<=casillaActual.getContadorZombis(); i++){
            acciones--;
        }
    }

    public void elegirArma(int a){
        if(inventario[a] instanceof Arma){
            arma = inventario[a];
        }
    }
    public Zombi elegirObjetivo(Zombi[] zombis, int a){
        return zombis[a];
    }

    public Ataque atacar() {
            return new Ataque(arma);
    }
    

}