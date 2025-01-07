package mainpackage;

public class Toxico extends Zombi {
    public Toxico(Casilla c, String subtipoZ, Partida partida, int id) {
        super(c, subtipoZ,partida,id);
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