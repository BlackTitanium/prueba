package mainpackage;

public class Berserker extends Zombi{

    public Berserker(Casilla c, String subtipoZ, Partida partida){
        super(c, subtipoZ, partida);
    }

    @Override
    public void reaccion(Arma arma, int a){
        super.reaccion(arma, a);
        if(arma.getAlcance()>0){
            for (int i=0; i<a; i++){
                aguante++;
            }
            casillaActual.addZombi(this);
            throw new IllegalArgumentException("Alcance");
        }
    }
}