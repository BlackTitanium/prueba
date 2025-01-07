package mainpackage;

import java.io.Serializable;
import java.util.Random;

public class Zombi extends Entidad implements Serializable{
    public static String[] tiposZombi = {"CAMINANTE", "CORREDOR", "ABOMINACION"};
    protected int activaciones, aguante;
    protected String tipo;
    protected String subtipo;

    private Partida partida;

    public Zombi(Casilla c, String subtipoZ, Partida partida){
        super(partida, c);
        //tableroActual = t;
        //casillaActual = c;
        this.partida = partida;
        String tipos = aparicionZombi();
        this.tipo = tipos;
        switch(tipo){
            case "CAMINANTE":
                activaciones = 1;
                aguante = 1;
                break;
            case "CORREDOR":
                activaciones = 2;
                aguante = 1;
                break;
            case "ABOMINACION":
                activaciones = 1;
                aguante = 3;
                break;
        }
        subtipo = subtipoZ;
    }

    public String aparicionZombi(){
        String tipos = "";
        Random random = new Random();
        int tipo = random.nextInt(3);
        int subtipo = random.nextInt(3);
        Zombi nuevoZombi =  null;
        switch(subtipo){
            case 0:
                tipos = tiposZombi[tipo];
                break;
            case 1:
                tipos = tiposZombi[tipo];
                break;
            case 2:
                tipos = tiposZombi[tipo];
                break;
        }
        return tipos;
    }

    public void activar(){
        for (int i=0; i<activaciones; i++){
            if(casillaActual.getContadorSupervivientes()!=0){
                atacar(casillaActual.getSuperviviente(0));
            } else {
                if(Math.abs(casillaActual.getX()-tableroActual.objetivoZombi(casillaActual).getX())>Math.abs(casillaActual.getY()-tableroActual.objetivoZombi(casillaActual).getY())){
                    mover(casillaActual.getX()+1,casillaActual.getY());
                } else{
                    mover(casillaActual.getX(),casillaActual.getY()+1);
                }
            }
        }
    }

    public void reaccion(Arma arma, int a){
        for (int i=0; i<a; i++){
            aguante--;
            if( aguante == 0){
                casillaActual.removeEntidad(this);
                break;
            }
        }
        
    }
    public void atacar(Superviviente s){
        s.addMordeduras();
        if(s.getMordeduras() == 2){
            casillaActual.removeEntidad(s);
            s.setEstado(Superviviente.estado.MUERTO);
        }
    }

    public String getZombiParaBoton(){
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("Z.");
        switch(tipo){
            case "CAMINANTE":
                sb.append("Ca.");
                break;
            case "CORREDOR":
                sb.append("Co.");
                break;
            case "ABOMINACION":
                sb.append("Ab.");
                break;
        }
        if(subtipo.contains("NORMAL")){
            sb.append("N");
        } else if(subtipo.contains("TOXICO")){
            sb.append("T");
        } else if(subtipo.contains("BERSERKER")){
            sb.append("B");
        }
        sb.append("<br>");
        sb.append("</html>");
        return sb.toString();
    }
}