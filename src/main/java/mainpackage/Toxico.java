package mainpackage;

public class Toxico extends Zombi {
    public Toxico(Casilla c, String subtipoZ, Partida partida, int id) {
        super(c, subtipoZ,partida,id);
    }

    public Toxico(Casilla c, String subtipoZ, Partida partida, int id, String tipo) {
        super(c, subtipoZ,partida,id,tipo);
    }

//    @Override
//    public void reaccion(Arma arma, int a){
//        super.reaccion(arma, a);
//        if(aguante == 0){
//            if(casillaActual.getContadorSupervivientes()!=0){
//                casillaActual.getSuperviviente(0).addMordeduras();
//            }
//        }
//    }

    @Override
    public void reaccion(Arma arma){
        if(arma.getPotencia() >= aguante){
            if(casillaActual.getContadorSupervivientes()!=0){
                atacar(casillaActual.getSuperviviente(0)); // Como se ha muerto y hay un superviviente en su casilla lo ataca
            }
            this.estadoActual = estado.MUERTO;
        }else{
            this.estadoActual = estado.VIVO;
        }        
    }

    @Override
    public void atacar(Superviviente s){
        s.addMordeduras();
        if(s.getMordeduras() == 2){
            s.setEstado(Superviviente.estado.MUERTO);
            if(this.estadoActual == estado.MUERTO){
                StringBuilder sb = new StringBuilder();
                sb.append(s.infoSuperviviente());
                sb.append("herida").append("\n").append("\n");
                añadirSupervivienteAtacado(sb.toString());
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(s.infoSuperviviente());
        sb.append("mordedura").append("\n").append("\n");
        añadirSupervivienteAtacado(sb.toString());
    }
}