package mainpackage;

public class Toxico extends Zombi {
    public Toxico(Casilla c, String subtipoZ, Partida partida, int id) {
        super(c, subtipoZ,partida,id);
    }

    public Toxico(Casilla c, String subtipoZ, Partida partida, int id, String tipo) {
        super(c, subtipoZ,partida,id,tipo);
    }

    @Override
    public void reaccion(Arma arma, int a){
        super.reaccion(arma, a);
        if(aguante == 0){
            if(casillaActual.getContadorSupervivientes()!=0){
                casillaActual.getSuperviviente(0).addMordeduras();
            }
        }
    }

    @Override
    public int reaccion(Arma arma){
        int vivoOmuerto = super.reaccion(arma);
        
        if(vivoOmuerto == 1){
            if(casillaActual.getContadorSupervivientes()!=0){
                casillaActual.getSuperviviente(0).addMordeduras();
                return vivoOmuerto + 1; // Devuelve 2 porque hay mordedura
            }
            return vivoOmuerto; // Devuelve 1 porque muere pero no muerde
        }
        return vivoOmuerto; // Devuelve 0 porque no muere
    }
}