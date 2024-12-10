package mainpackage;

public class Superviviente extends Entidad{
    private String nombre;
    private int contadorZombis, mordeduras, acciones = 3;
    private accion seleccion;

   
    private enum estado {VIVO, MUERTO};
    private estado estadoActual;
    private Equipo[] inventario; 

    public Superviviente(String nombre){
        this.nombre = nombre;
        this.contadorZombis = 0;
        this.mordeduras = 0;
        this.estadoActual = estado.VIVO;
    }

    @Override
    public void setPosicion(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void activar() {
        acciones=3;
        if(seleccion==accion.MOVER){

        }
    }

    
    public void mover(int x, int y) {
        
        super.mover(x, y);
        for (int i=0; i<=casillaActual.getContadorZombis(); i++){
            acciones--;
        }
    }

    @Override
    public void atacar(Entidad e) {
        if (e instanceof Zombi){
            ;
            }
        }
    

}