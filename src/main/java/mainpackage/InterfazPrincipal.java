package mainpackage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InterfazPrincipal extends JFrame{
    private static final int SIZE = 10;  // Tamaño del tablero 10x10
    public JButton[][] botones = new JButton[SIZE][SIZE];   
    public Point elementoSeleccionado = null;  // Guarda la posición del elemento seleccionado
    public boolean accionRealizada = false;
    
    public CardLayout cardLayout;
    public JPanel panelTablero, panelDerechoPrincipal;
    
    PanelInicio panelInicio;
    PanelMenuJugador panelMenuJugador;
    PanelHistoriales panelHistoriales;
    
    public int nJugadores = 0;
    private String[] nombresZombis = {"Z.Ca.N", "Z.Co.N", "Z.Ab.N", "Z.Ca.B", "Z.Co.B", "Z.Ab.B", "Z.Ca.T", "Z.Co.T", "Z.Ab.T"};
    
    public Partida partida;
    private Tablero tablero;
    
    public Zombi zombiSeleccionado;
          
    public InterfazPrincipal(Partida partida){
        this.partida = partida;
        tablero = partida.getTablero();
        
        setTitle("Juego");
        setBounds(0, 0, 1145, 745);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout()); // BorderLayout() divide el la ventana en 5 zonas (Norte, Sur, Este, Oeste y Centro)
        
        // Panel del tablero (a la izquierda)
        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(SIZE, SIZE));
        // GridLayout(,) organiza los componentes en una cuadrícula rectangular con un número fijo de filas y columnas,
        // donde todos los componentes tienen el mismo tamaño
        panelTablero.setPreferredSize(new Dimension(745, 745));  // Tamaño fijo para el tablero
        
        // Manejador de Paneles
        cardLayout = new CardLayout();
        // Panel de derecho principal (a la derecha)
        panelDerechoPrincipal = new JPanel(cardLayout);
        panelDerechoPrincipal.setPreferredSize(new Dimension(400, 745));
        
        // Añadimos voy las clases panel al panel de derecho principal
        panelInicio = new PanelInicio(partida, this);
        panelDerechoPrincipal.add(panelInicio,"PanelInicio");
        
        // Añadir paneles al JFrame
        add(panelTablero, BorderLayout.CENTER);  // Tablero en el centro (ocupa la izquierda)
        add(panelDerechoPrincipal, BorderLayout.EAST);    // Panel de control a la derecha
        inicializarTablero();
        setVisible(true);
    }
    
    public void inicializarPaneles(){
        panelMenuJugador = new PanelMenuJugador(partida,this);
        panelDerechoPrincipal.add(panelMenuJugador,"PanelMenuJugador");
        panelDerechoPrincipal.revalidate();
        panelDerechoPrincipal.repaint();
        
        panelHistoriales = new PanelHistoriales(partida.getAlmacenDeAtaques(),this, partida);
        panelDerechoPrincipal.add(panelHistoriales,"PanelHistoriales");
        panelDerechoPrincipal.revalidate();
        panelDerechoPrincipal.repaint();
    }
    
    public void actualizacionGeneralPanelMenuJugador(){
        panelMenuJugador.actualizarLabels();
    }
    
    public void mostrarMensajeDeDerrota(){
        JOptionPane.showMessageDialog(this,"Habeís perdido");
    }
    
    public void mostrarMensajeDeVictoria(){
        JOptionPane.showMessageDialog(this,"¡Habeís ganado!");
    }
    
    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this,mensaje);
    }
    
    public boolean mostrarMensajeSiNo(String mensaje){
        int respuesta = InterfazDeSINo.mostrarConfirmacion(mensaje);
        if (respuesta == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
    
    // Inicializar el tablero con Casillas y Botones
    public void inicializarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setForeground(Color.BLACK);
                botones[i][j].setFont(new Font("Arial",0,10));
                botones[i][j].setText("<html></html>");
                panelTablero.add(botones[i][j]);
            }
        }
    }
    
    public void reiniciarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                botones[i][j].setText("<html></html>");
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setForeground(Color.BLACK);
                tablero.reiniciarTablero();
            }
        }
    }

    public void activarActionListener(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int I = i, J = j;
                botones[i][j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        accionBotonesTablero(botones[I][J],I,J);
                    }
                });
            }
        }
        StringBuilder sb = new StringBuilder();
        String aux = botones[9][9].getText();
        aux = aux.replace("</html>", "");
        sb.append(aux);
        sb.append("Meta");
        sb.append("<br>");
        sb.append("</html>");
        botones[9][9].setText(sb.toString());
        botones[9][9].setBackground(Color.GREEN);
    }
    
    public void accionBotonesTablero(JButton boton, int x, int y){
        Superviviente supervivienteActual = partida.getSupervivienteActual();
        if(panelMenuJugador.movimientoActivado){
            moverElemento(boton, x, y);
            if(elementoSeleccionado != null){
                actualizarCasillas(tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y), tablero.getCasilla(x,y));
            }else{
                actualizarCasillas(tablero.getCasilla(x, y), tablero.getCasilla(x,y));
            }
        }
        if(panelMenuJugador.atacarActivado){
            if(supervivienteActual.getArmaActiva() != null){
                atacar(boton, x, y);
                if(elementoSeleccionado != null){
                    actualizarCasillas(tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y), tablero.getCasilla(x,y));
                }else{
                    actualizarCasillas(tablero.getCasilla(x, y), tablero.getCasilla(x,y));
                }
            } else{
                JOptionPane.showMessageDialog(this,"Debes seleccionar un arma");
            }
        }
        if(panelHistoriales.seleccionarZombi){
            elegirZombiInterfazPrincipal(boton, x, y);
        }
        if(panelHistoriales.seleccionarSuperviviente){
            elegirSupervivienteInterfazPrincipal(boton, x, y);
        }
    }
    
    public void supervivienteMuerto(Casilla casillaActual, String nombreNuevo){
        int x = casillaActual.getX();
        int y = casillaActual.getY();
        
        Superviviente supervivienteActual = partida.getSupervivienteActual();
        String textoBoton = botones[x][y].getText();
        textoBoton = textoBoton.replace(supervivienteActual.getNombre(), nombreNuevo);
        botones[x][y].setText(textoBoton);     
    }
    
    public void faseZombiInterfaz(){
        panelMenuJugador.actualizarLabels();
        panelMenuJugador.activacionBotones(false);
        panelMenuJugador.botonAtacar.setEnabled(false);
        panelMenuJugador.botonInventario.setEnabled(false);
        panelMenuJugador.botonNada.setEnabled(false);
    }
    
    public void matarZombi(Casilla casilla, String textoBotonZombi){
        System.out.println("En matarZombi. Se va a matar al zombi " + textoBotonZombi);
        int x = casilla.getX();
        int y = casilla.getY();
        //Quitamos de textoBotonZombi lo de html menos <br>
        textoBotonZombi = textoBotonZombi.replace("<html>","");
        textoBotonZombi = textoBotonZombi.replace("</html>","");
        
        String textoBoton = botones[x][y].getText();
        textoBoton = textoBoton.replace(textoBotonZombi, "");
        botones[x][y].setText(textoBoton);
    }
    
    public void moverZombi(Casilla origen, Casilla destino, String textoBotonZombi){
        System.out.println("En moverZombi de InterfazPrincipal. Se va a mover al zombi " + textoBotonZombi);
        int xOrigen = origen.getX();
        int yOrigen = origen.getY();
        int xDestino = destino.getX();
        int yDestino = destino.getY();
        //Quitamos de textoBotonZombi lo de html menos <br>
        textoBotonZombi = textoBotonZombi.replace("<html>","");
        textoBotonZombi = textoBotonZombi.replace("</html>","");
        
        StringBuilder sbDestino = new StringBuilder();
        String textoDestino = botones[xDestino][yDestino].getText();
        textoDestino = textoDestino.replace("</html>", ""); // Quitamos cierre de html
        sbDestino.append(textoDestino); // Se añade el texto del boton destino al sb
        sbDestino.append(textoBotonZombi); // Se añade el zombi al sb
        sbDestino.append("</html>"); // Se cierra el html
        botones[xDestino][yDestino].setText(sbDestino.toString());
        
        String textoOrigen = botones[xOrigen][yOrigen].getText();
        textoOrigen = textoOrigen.replace(textoBotonZombi, "");
        botones[xOrigen][yOrigen].setText(textoOrigen);
    }
    
    public void actualizarCasillas(Casilla origen, Casilla destino){
        Casilla[] casillas = {origen, destino};
        for (int i = 0; i < 2; i++){
            StringBuilder sb1 = new StringBuilder("<html>");
            StringBuilder sb2 = new StringBuilder();   
            if(casillas[i].getContadorSupervivientes() != 0){
                for (int k = 0; k < casillas[i].getContadorSupervivientes(); k++){
                        String nombreTemp = casillas[i].getSuperviviente(k).getNombre();
                        sb1.append(nombreTemp);
                        sb1.append("<br>");
                    }
                }
            if(casillas[i].getContadorZombis() != 0){
                for (int k = 0; k < casillas[i].getContadorZombis(); k++){
                    String zombiTemp = casillas[i].getZombi(k).getZombiParaBoton(); 
                    sb2.append(zombiTemp);      
                    sb2.append("<br>");
                }

            }
            sb1.append(sb2.toString());
            sb1.append("</html>");
            botones[casillas[i].getX()][casillas[i].getY()].setText(sb1.toString());
        }
    }
    
    public static int contarApariciones(String texto, String patron) {
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(texto);
        int contador = 0;
    
        while (matcher.find()) {
            contador++;
        }
        return contador;
    }
    public static String eliminarPatron(String texto, String patron, int veces) {
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(texto);
        StringBuilder resultado = new StringBuilder(texto);
        int eliminados = 0;
    
        while (matcher.find() && eliminados < veces) {
            int start = matcher.start();
            int end = matcher.end();
            resultado.replace(start, end, ""); // Eliminar el patrón encontrado
            matcher.reset(resultado); // Reiniciar el matcher con el nuevo texto
            eliminados++;
        }
        return resultado.toString();
    }
    
    public void autoSeleccionElementoMoverse(){
        Superviviente supervivienteActual = partida.getSupervivienteActual();
        Casilla casillaActual = supervivienteActual.getCasillaActual();
        int xActual = casillaActual.getX();
        int yActual = casillaActual.getY();
        elementoSeleccionado = new Point(xActual, yActual);
        botones[xActual][yActual].setBackground(Color.DARK_GRAY);
        botones[xActual][yActual].setForeground(Color.WHITE);
        panelMenuJugador.gestionPanelIntercambio(true,0);
    }

    // Acción al hacer clic en una casilla del tablero
    public void moverElemento(JButton boton, int x, int y){
        if(panelMenuJugador.movimientoActivado){
            Superviviente supervivienteActual = partida.getSupervivienteActual();
//            if (elementoSeleccionado == null && boton.getText().contains(supervivienteActual.getNombre())) {
//                // Selecciona un elemento para mover
//                elementoSeleccionado = new Point(x, y);
//                boton.setBackground(Color.DARK_GRAY);  // Resaltar elemento
//                boton.setForeground(Color.WHITE);
//            } else if (elementoSeleccionado != null) {
            if(elementoSeleccionado != null){
                if(x == supervivienteActual.getCasillaActual().getX() && y == supervivienteActual.getCasillaActual().getY()){
                    JOptionPane.showMessageDialog(this,"No puede moverse a la misma casilla");   
                    return;
                }
                // Verificar si el movimiento es a una casilla adyacente 
                if (Math.abs(elementoSeleccionado.x - x) <= 1 && Math.abs(elementoSeleccionado.y - y) <= 1) {                  
                    // Mover el superviviente a la nueva casilla y marcarla como ocupada
                    partida.activarSuperviviente(0, x, y,null);

                    tablero.posicionesOcupadas[x][y] = true;
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setForeground(Color.BLACK);

                    // Actualizar casillas
                    int xOrigen = elementoSeleccionado.x;
                    int yOrigen = elementoSeleccionado.y;
                    Casilla origen = tablero.getCasilla(xOrigen, yOrigen);
                    int xDestino = x;
                    int yDestino = y;
                    Casilla destino = tablero.getCasilla(xDestino, yDestino);

                    StringBuilder sb = new StringBuilder();
                    String textoBotonDestino = botones[x][y].getText();
                    textoBotonDestino = textoBotonDestino.replace("</html>", ""); // Quitamos el cierre de HTML
                    sb.append(textoBotonDestino); // Le añado el texto ya existente en el boton destino al sb
                    sb.append(partida.getSupervivienteActual().getNombre()); // Le añado al boton destino el jugador
                    sb.append("<br>");
                    String textoBotonOrigen = botones[elementoSeleccionado.x][elementoSeleccionado.y].getText();
                    StringBuilder aux = new StringBuilder();
                    aux.append(supervivienteActual.getNombre()).append("<br>");
                    String nombre = aux.toString();
                    String textoBotonOrigenFinal = textoBotonOrigen.replace(nombre,""); // Le quito el nombre del jugador
                    if(origen.getContadorZombis() > 0){ // Si hay zombis
                        supervivienteActual.lessAcciones(origen.getContadorZombis()); // Restamos 1 accion por zombi
                        for(int i = 0; i<origen.getContadorZombis();i++){
                            Zombi zombi = origen.getZombi(i);
                            String txtBotonDeLosZombis = zombi.getZombiParaBoton();
                            txtBotonDeLosZombis = txtBotonDeLosZombis.replace("<html>", ""); // Le quito lo del html
                            txtBotonDeLosZombis = txtBotonDeLosZombis.replace("</html>", "");
                            sb.append(txtBotonDeLosZombis); // Lo añado al sb (Ya va con <br>)
                            textoBotonOrigenFinal = textoBotonOrigenFinal.replace(txtBotonDeLosZombis,""); // Le quito el zombi al boton origen junto al <br>
                            zombi.mover(x, y); // Mueve al zombi
                        }
                    }
                    sb.append("</html>"); // Ponemos el cierre de nuevo
                    botones[x][y].setText(sb.toString()); // Añado el texto al boton destino
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigenFinal);
                    if(origen.getContadorZombis() == 0 && origen.getContadorSupervivientes() == 0){
                        tablero.posicionesOcupadas[elementoSeleccionado.x][elementoSeleccionado.y] = false;
                    }

                    partida.accionTerminada();
                    elementoSeleccionado = null;
                    panelMenuJugador.movimientoActivado = false;
                    panelMenuJugador.activacionBotones(true);
                    panelMenuJugador.gestionPanelIntercambio(false, 0);
                }            
            }
        } else{
            JOptionPane.showMessageDialog(this,"No puede moverse en este momento");
        }
    }
    
    public void autoSeleccionElementoAtacar(){
        Superviviente supervivienteActual = partida.getSupervivienteActual();
        Casilla casillaActual = supervivienteActual.getCasillaActual();
        int xActual = casillaActual.getX();
        int yActual = casillaActual.getY();
        elementoSeleccionado = new Point(xActual, yActual);
        botones[xActual][yActual].setBackground(Color.DARK_GRAY);
        botones[xActual][yActual].setForeground(Color.WHITE);
        System.out.println("En autoSeleccion: X actual: " + xActual + ", Y actual: " + yActual);
        ArrayList<Casilla> casillasAlcance = tablero.elegirObjetivoSuperviviente(supervivienteActual.getArmaActiva(), xActual, yActual);
        for (int i = 0; i < casillasAlcance.size(); i++){
            botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setBackground(Color.RED);
            botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setForeground(Color.WHITE);
        }
        boolean hayZombis = false;
        int contador = 0;
        for (int i = 0; i < casillasAlcance.size(); i++){
            contador = casillasAlcance.get(i).getContadorZombis();
            if(contador>0){
                hayZombis = true;
                break;
            }
        }
        if(casillaActual.getContadorZombis() > 0){
            hayZombis = true;
        }
        if(!hayZombis){
            JOptionPane.showMessageDialog(this,"No hay zombis al alcance");
            elementoSeleccionado = null;
            panelMenuJugador.atacarActivado = false;
            panelMenuJugador.activacionBotones(true);
            for (int i = 0; i < casillasAlcance.size(); i++){
                botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setBackground(Color.LIGHT_GRAY);
                botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setForeground(Color.BLACK);
            }
        }
    }
    
    public void atacar(JButton boton, int x, int y){
        if(panelMenuJugador.atacarActivado){
            Superviviente supervivienteActual = partida.getSupervivienteActual();
            int xActual = supervivienteActual.getCasillaActual().getX();
            int yActual = supervivienteActual.getCasillaActual().getY();
            System.out.println("En atacar: X actual: " + xActual + ", Y actual: " + yActual);
            ArrayList<Casilla> casillasAlcance = tablero.elegirObjetivoSuperviviente(supervivienteActual.getArmaActiva(), xActual, yActual);
            if(elementoSeleccionado != null){
                if(tablero.getCasilla(x,y).getContadorZombis() > 0){
                    System.out.println("Coordenadas objetivo: x: " + x + ", y: " + y);
                    // Atacar al zombi
                    partida.activarSuperviviente(panelMenuJugador.armaElegida, x, y, null);
                    // En caso de que haya zombis muertos ya se encarga partida
                    // Restablecer casillas
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setForeground(Color.BLACK);
                    for (int i = 0; i < casillasAlcance.size(); i++){
                        botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setBackground(Color.LIGHT_GRAY);
                        botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setForeground(Color.BLACK);
                    }
                    // Restablecer valores
                    partida.accionTerminada();
                    elementoSeleccionado = null;
                    panelMenuJugador.atacarActivado = false;
                    panelMenuJugador.activacionBotones(true);
                }else{
                    JOptionPane.showMessageDialog(this,"No hay zombis en la casilla seleccionada");
                }
            }
        }else{
            JOptionPane.showMessageDialog(this,"No puede atacar en este momento");
        }
    }
         
    public void elegirZombiInterfazPrincipal(JButton boton, int x, int y){
        if(elementoSeleccionado == null){
            elementoSeleccionado = new Point(x,y);
            boton.setBackground(Color.DARK_GRAY);
            boton.setForeground(Color.WHITE);
            int xOrigen = elementoSeleccionado.x;
            int yOrigen = elementoSeleccionado.y;
            Casilla origen = tablero.getCasilla(xOrigen, yOrigen);
            if(origen.getContadorZombis() != 0){
                for(int i = 0; i < origen.getContadorZombis(); i++){
                    StringBuilder sb = new StringBuilder();
                    sb.append("¿Quieres acceder al historial del zombi ");
                    Zombi zombi = origen.getZombi(i);
                    String txtBotonDeLosZombis = zombi.getZombiParaBoton();
                    txtBotonDeLosZombis = txtBotonDeLosZombis.replace("<html>", "");
                    txtBotonDeLosZombis = txtBotonDeLosZombis.replace("<br>", "");
                    txtBotonDeLosZombis = txtBotonDeLosZombis.replace("</html>", "");
                    sb.append(txtBotonDeLosZombis);
                    sb.append(" ?");
                    boolean respuesta = mostrarMensajeSiNo(sb.toString());
                    if(respuesta){
                        panelHistoriales.textArea.setText(zombi.mostrarHistorialSupervivientesAtacados());
                        break;
                    }
                }
                boton.setBackground(Color.LIGHT_GRAY);
                boton.setForeground(Color.BLACK);
                elementoSeleccionado = null;
                panelHistoriales.seleccionarZombi = false;
            }else{
                JOptionPane.showMessageDialog(this,"No hay zombis en esta casilla");
                boton.setBackground(Color.LIGHT_GRAY);
                boton.setForeground(Color.BLACK);
            }
        }
    }
    
    public void elegirSupervivienteInterfazPrincipal(JButton boton, int x, int y){
        if(elementoSeleccionado == null){
            elementoSeleccionado = new Point(x,y);
            boton.setBackground(Color.DARK_GRAY);
            boton.setForeground(Color.WHITE);
            int xOrigen = elementoSeleccionado.x;
            int yOrigen = elementoSeleccionado.y;
            Casilla origen = tablero.getCasilla(xOrigen, yOrigen);
            if(origen.getContadorSupervivientes() != 0){
                for(int i = 0; i < origen.getContadorSupervivientes(); i++){
                    StringBuilder sb = new StringBuilder();
                    sb.append("¿Quieres acceder al historial de ");
                    Superviviente superviviente = origen.getSuperviviente(i);
                    String txtNombreSuperviviente = superviviente.getNombre();
                    sb.append(txtNombreSuperviviente);
                    sb.append(" ?");
                    boolean respuesta = mostrarMensajeSiNo(sb.toString());
                    if(respuesta){
                        panelHistoriales.textArea.setText(superviviente.mostrarHistorialZombisAsesinados());
                        break;
                    }
                }
                boton.setBackground(Color.LIGHT_GRAY);
                boton.setForeground(Color.BLACK);
                elementoSeleccionado = null;
                panelHistoriales.seleccionarSuperviviente = false;
            }else{
                JOptionPane.showMessageDialog(this,"No hay supervivientes en esta casilla");
                boton.setBackground(Color.LIGHT_GRAY);
                boton.setForeground(Color.BLACK);
            }
        }
    }
    
    public static void main(String args[]){ 
        Juego juego = new Juego();
        juego.iniciarPartida();
    }
}
