package mainpackage;

public class Superviviente extends Entidad{
    private String nombre;
    private int contadorZombis, mordeduras, acciones = 3;
    private accion seleccion;
    private Arma[] arma = new Arma[2];

    public int getContadorZombis() {
        return contadorZombis;
    }

    public int getMordeduras() {
        return mordeduras;
    }

    public int getAcciones() {
        return acciones;
    }

    public accion getSeleccion() {
        return seleccion;
    }

    public String getNombre() {
        return nombre;
    }

    public Arma[] getArma() {
        return arma;
    }
   
    private enum estado {VIVO, MUERTO};
    private estado estadoActual;
    private Equipo[] inventario = new Equipo[5]; 

    public estado getEstadoActual() {
        return estadoActual;
    }
    public Equipo[] getInventario() {
        return inventario;
    }

    public Superviviente(String nombre){
        this.nombre = nombre;
        this.contadorZombis = 0;
        this.mordeduras = 0;
        this.estadoActual = estado.VIVO;
    }

    public void addMordeduras(){
        mordeduras++;
    }

    public void buscar(int a){
        inventario[a] = casillaActual.buscar();
        acciones--;
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

    public void elegirArma(int a, int b){
        if(inventario[a] instanceof Arma){
            Equipo equipoSeleccionado = inventario[a];
            arma[b] = (Arma) equipoSeleccionado;
        } else {
            throw new IllegalArgumentException("No es un arma");
        }
    }

    public Provision elegirProvision(int a){
        if(inventario[a] instanceof Provision){
            Equipo equipoSeleccionado = inventario[a];
            Provision provision = (Provision) equipoSeleccionado;
            acciones--;
            return provision;
        } else {
            throw new IllegalArgumentException("No es una provision");
        }
    }

    public Zombi elegirObjetivo(Zombi[] zombis, int a){
        return zombis[a];
    }

    @Override
    
    public Ataque atacar(int a) {
            return new Ataque(arma[a]);
    }
    
}