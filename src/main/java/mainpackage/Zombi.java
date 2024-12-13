package mainpackage;

public abstract class Zombi extends Entidad {
    enum tipoZombi {CAMINANTE, CORREDOR, ABOMINACION};
    tipoZombi tipo;
    protected int activaciones, aguante;

    public Zombi(Tablero t, Casilla c, tipoZombi tipoZ){
        super(t, c);
        this.tipo = tipoZ;
        switch(tipo){
            case CAMINANTE:
                activaciones = 1;
                aguante = 1;
                break;
            case CORREDOR:
                activaciones = 2;
                aguante = 1;
                break;
            case ABOMINACION:
                activaciones = 1;
                aguante = 3;
                break;
        }
    }
    public void activar(){
        for (int i=0; i<activaciones; i++){
            if(casillaActual.getContadorSupervivientes()!=0){
                atacar(casillaActual.getSuperviviente());
            } else {
                if(Math.abs(casillaActual.getX()-tableroActual.objetivoZombi(casillaActual).getX())>Math.abs(casillaActual.getY()-tableroActual.objetivoZombi(casillaActual).getY())){
                    mover(1,0);
                } else{
                    mover(0,1);
                }
            }
        }
    }
    public void reaccion(Ataque a){
        if 
    }
}