package mainpackage;

public class Toxico extends Zombi {
    public Toxico(Tablero t, Casilla c, tipoZombi tipoZ) {
        super(t, c, tipoZ);
    }

    @Override

    public void reaccion(Arma arma, int a){
        super.reaccion(arma, a);
        if(aguante == 0){
            if(casillaActual.getContadorSupervivientes()!=0){
                casillaActual.getSuperviviente().addMordeduras();
            }
        }
    }
}