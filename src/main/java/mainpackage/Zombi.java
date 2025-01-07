package mainpackage;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;

public class Zombi extends Entidad implements Serializable{
    public static String[] tiposZombi = {"CAMINANTE", "CORREDOR", "ABOMINACION"};
    protected int activaciones, aguante;
    protected String tipo;
    protected String subtipo;
    protected int identificador;
    private Partida partida;
    private ArrayList<String> supervivientesAtacados;

    public Zombi(Casilla c, String subtipoZ, Partida partida, int id){
        super(partida, c);
        this.identificador = id;
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
        this.supervivientesAtacados = new ArrayList<>();
    }

    public Zombi(Casilla c, String subtipoZ, Partida partida, int id, String tipos){
        super(partida, c);
        this.identificador = id;
        this.partida = partida;
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
        this.supervivientesAtacados = new ArrayList<>();
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
                atacar(casillaActual.getSuperviviente());
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

    public String infoZombi(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(identificador).append("\n");
        sb.append("Zombi: ").append(tipo).append(" ").append(subtipo).append("\n");
        sb.append("Aguante: ").append(aguante).append("\n");
        sb.append("Activaciones: ").append(activaciones).append("\n");
        sb.append("Características:\n");
        if(subtipo.contains("NORMAL")){
            sb.append("Se elimina con un ataque de potencia igual a su aguante.\n");
        } else if(subtipo.contains("TOXICO")){
            sb.append("Al morir en la misma casilla que un Superviviente\n");
            sb.append("causa una herida al atacante debido a su sangre tóxica.\n");
        } else if(subtipo.contains("BERSERKER")){
            sb.append("Inmune a ataques a distancia\n");
            sb.append("(desde casillas diferentes a las que se encuentre el zombi)\n");
        }
        return sb.toString();
    }

    public void añadirSupervivienteAtacado(String infoSupervivienteAtacado){
        StringBuilder sb = new StringBuilder();
        sb.append(infoSupervivienteAtacado);
        sb.append("mordedura/herida").append("\n"); // ESTO HAY QUE MODIFICARLO
        supervivientesAtacados.add(sb.toString());
    }
}