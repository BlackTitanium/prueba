package mainpackage;

public class Berserker extends Zombi{

    public Berserker(Casilla c, String subtipoZ, Partida partida, int id){
        super(c, subtipoZ, partida, id);
    }

    public Berserker(Casilla c, String subtipoZ, Partida partida, int id, String tipo) {
        super(c, subtipoZ,partida,id,tipo);
    }

//    @Override
//    public void reaccion(Arma arma, int a){
//        super.reaccion(arma, a);
//        if(arma.getAlcance()>0){
//            for (int i=0; i<a; i++){
//                aguante++;
//            }
//            casillaActual.addZombi(this);
//            throw new IllegalArgumentException("Alcance");
//        }
//    }

    @Override
    public void reaccion(Arma arma){
        if(arma.getAlcance()>0){
            throw new IllegalArgumentException("Alcance");
        } else{
            super.reaccion(arma);
        }
    }
}