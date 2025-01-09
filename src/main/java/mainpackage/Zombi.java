package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Zombi extends Entidad implements Serializable{
    public static String[] tiposZombi = {"CAMINANTE", "CORREDOR", "ABOMINACION"};
    protected int activaciones, activacionesAux, aguante;
    protected String tipo;
    protected String subtipo;
    protected int identificador;
    protected Partida partida;
    protected ArrayList<String> supervivientesAtacados;
    public enum estado {VIVO, MUERTO};
    protected estado estadoActual;

    public Zombi(Casilla c, String subtipoZ, Partida partida, int id){
        super(partida, c);
        this.identificador = id;
        this.estadoActual = estado.VIVO;
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
        this.estadoActual = estado.VIVO;
        this.partida = partida;
        this.tipo = tipos;
        switch(tipo){
            case "CAMINANTE":
                activaciones = 1;
                activacionesAux = 1;
                aguante = 1;
                break;
            case "CORREDOR":
                activaciones = 2;
                activacionesAux = 2;
                aguante = 1;
                break;
            case "ABOMINACION":
                activaciones = 1;
                activacionesAux = 1;
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

    public estado getEstadoActual() {
        return estadoActual;
    }

    public int getIdentificador(){
        return identificador;
    }

    public int getActivaciones(){
        return activacionesAux;
    }

    public void setActivaciones(){
        this.activacionesAux = activaciones;
    }

    public void activar(){
        System.out.println("INICIO DE ACTIVAR, Zombi " + this.getZombiParaBoton() + ", nº de activaciones: " + activacionesAux);
        for (int i=0; i<activaciones; i++){
            System.out.println("Activación nº: teórica: " + i + ", real: " + activacionesAux);
            if(casillaActual.getContadorSupervivientes()!=0){
                System.out.println("Zombi " + this.getZombiParaBoton() + " ataca a " + casillaActual.getSuperviviente(0).getNombre());
                if(casillaActual.getSuperviviente(0).getEstadoActual() != Superviviente.estado.MUERTO){
                    atacar(casillaActual.getSuperviviente(0));
                }
            } else {
                Casilla casillaAlaQueMoverse = partida.getTablero().calcularMovimientoZombi(this.casillaActual);
                int x = casillaAlaQueMoverse.getX();
                int y = casillaAlaQueMoverse.getY();
                System.out.println("EN ACTIVAR: Zombi " + this.getZombiParaBoton() + " se mueve a la casilla " + x + ", " + y);
                mover(x, y);
            }
            activacionesAux--;
        }
        System.out.println("FIN DE ACTIVAR, Zombi " + this.getZombiParaBoton() + ", nº de activaciones: " + activacionesAux);
    }

    @Override
    public void mover(int x, int y){
        System.out.println("EN MOVER ANTES: Zombi " + this.getZombiParaBoton() + " se mueve a la casilla " + x + ", " + y);
        posicion[0] = x;
        posicion[1] = y;
        Casilla casillaAnterior = casillaActual;
        casillaActual = partida.getTablero().getCasilla(x, y);
        casillaAnterior.removeEntidad(this);
        casillaActual.addEntidad(this);
        partida.getInterfazPrincipal().moverZombi(casillaAnterior, casillaActual, this.getZombiParaBoton());
        System.out.println("EN MOVER DESPUES: Zombi " + this.getZombiParaBoton() + " se mueve a la casilla " + x + ", " + y);
    }

//    public void reaccion(Arma arma, int a){
//        for (int i=0; i<a; i++){
//            aguante--;
//            if( aguante == 0){
//                casillaActual.removeEntidad(this);
//                break;
//            }
//        } 
//    }

    public void reaccion(Arma arma){
        if(arma.getPotencia() >= aguante){
            this.estadoActual = estado.MUERTO; // Ha muerto
        }else{
            this.estadoActual = estado.VIVO; // No ha muerto
        }        
    }

    public void atacar(Superviviente s){
        s.addMordeduras();
        if(s.getMordeduras() == 2){
            s.setEstado(Superviviente.estado.MUERTO);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(s.infoSuperviviente());
        sb.append("mordedura").append("\n").append("\n");
        añadirSupervivienteAtacado(sb.toString());
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
        sb.append("\n");
        return sb.toString();
    }

    public String zombiInformativo(){
        StringBuilder sb = new StringBuilder();
        String zombiBoton = getZombiParaBoton();
        zombiBoton = zombiBoton.replace("<html>", "");
        zombiBoton = zombiBoton.replace("<br>", "");
        zombiBoton = zombiBoton.replace("</html>", "");
        sb.append("En el tablero: ").append(zombiBoton).append("\n");
        sb.append("Corresponde a: ").append("\n");
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
        sb.append("\n");
        return sb.toString();
    }

    public void añadirSupervivienteAtacado(String infoSupervivienteAtacado){
        supervivientesAtacados.add(infoSupervivienteAtacado);
    }

    public String mostrarHistorialSupervivientesAtacados(){
        if(supervivientesAtacados.isEmpty()){
            return "No ha atacado a ningún superviviente todavía\n";
        }else{
            StringBuilder sb = new StringBuilder();
            for(String superviviente : supervivientesAtacados){
                sb.append(superviviente);
            }
            return sb.toString();
        }        
    }
}