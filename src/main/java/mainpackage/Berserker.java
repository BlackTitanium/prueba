package mainpackage;

public class Berserker extends Zombi{

    public Berserker(Casilla c, String subtipoZ, Partida partida, int id){
        super(c, subtipoZ, partida, id);
    }

    public Berserker(Casilla c, String tipo, String subtipoZ, Partida partida, int id){
        super(c, tipo, subtipoZ,partida,id);
    }

    @Override
    public void reaccion(Arma arma){
        if(arma.getAlcance()>0 && partida.getSupervivienteActual().getCasillaActual() != casillaActual){
            throw new IllegalArgumentException("Alcance");
        } else{
            super.reaccion(arma);
        }
    }
}