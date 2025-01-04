package mainpackage;

public class Berserker extends Zombi{

    public Berserker(Tablero t, Casilla c, String tipoZ, String subtipoZ){
        super(t, c, tipoZ, subtipoZ);
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