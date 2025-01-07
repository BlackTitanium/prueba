package mainpackage;

import java.awt.BorderLayout;
import java.awt.CardLayout; // Porque estamos haciendo una interfaz gráfica
import java.awt.Color; // Cuando usamos CheckBox
import java.awt.Dimension; // Porque vamos a trabajar con colores
import java.awt.Font; // Porque vamos a trabajar con eventos: botones, combox, ...
import java.awt.GridLayout; // Para usar Casilla
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InterfazPrincipal extends JFrame{
    private static final int SIZE = 10;  // Tamaño del tablero 10x10
    public JButton[][] botones = new JButton[SIZE][SIZE];   
    private Point elementoSeleccionado = null;  // Guarda la posición del elemento seleccionado
    public boolean accionRealizada = false;
    
    public CardLayout cardLayout;
    public JPanel panelTablero, panelDerechoPrincipal, panelBotonesPermanentes;
    public Arma armaActiva;
    
    PanelMenuJugador panelMenuJugador;
    PanelInicio panelInicio;
    
    public int nJugadores = 0;
    
    public Partida partida;
    private Tablero tablero;
                
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
        inicializarTablero();
        
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
        
        setVisible(true);
    }
    
    public void inicializarPaneles(){
        System.out.println("Inicializando PanelMenuJugador...");
        panelMenuJugador = new PanelMenuJugador(partida,this);
        panelDerechoPrincipal.add(panelMenuJugador,"PanelMenuJugador");
        panelDerechoPrincipal.revalidate();
        panelDerechoPrincipal.repaint();
        System.out.println("PanelMenuJugador añadido al CardLayout.");
    }
    
    // Inicializar el tablero con Casillas y Botones
    private void inicializarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setForeground(Color.BLACK);
                botones[i][j].setFont(new Font("Arial",0,10));
                botones[i][j].setText("<html></html>");
                int I = i, J = j;
                botones[i][j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        if(panelMenuJugador.movimientoActivado){
                            moverElemento(botones[I][J], I, J);
                        } else if(panelMenuJugador.atacarActivado){
                            atacar(botones[I][J], I, J);
                        } else{
                            JOptionPane.showMessageDialog(null,"No puede moverse en este momento");
                        }
                    }
                });
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
    
    public void gestorTurnos() {
            for (int i = 0; i < partida.getSupervivientes().size(); i++) {
                partida.setTurnoActual(i);
                partida.faseSuperviviente();
                Superviviente supervivienteActual = partida.getSupervivienteActual();
                while (supervivienteActual.getAcciones() > 0) {
                    // Mostrar el panel de control del jugador
                    cardLayout.show(panelDerechoPrincipal, "PanelMenuJugador");
                    panelMenuJugador.activacionBotones(true);
                    // Esperar a que el jugador seleccione una acción
                    while (!accionRealizada) {
                        try {
                            this.wait(10000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    accionRealizada = false;
                // Actualizar para el siguiente jugador
                    panelMenuJugador.actualizarLabels();
                    panelMenuJugador.activacionBotones(true);
                    partida.avanzarTurno();
                }
                // After all players have taken their turns, handle the zombie phase
                partida.faseZombie();
                // Handle new zombie appearances
                partida.faseApariciónZombi();
            }
        } 
    
    public void accionTerminada(){
        synchronized(this){
            accionRealizada = true;
            this.notify();
        }
    }
    // Acción al hacer clic en una casilla del tablero
    public void moverElemento(JButton boton, int x, int y){
        if(panelMenuJugador.movimientoActivado){
            Superviviente supervivienteActual = partida.getSupervivienteActual();
            if (elementoSeleccionado == null && boton.getText().contains(supervivienteActual.getNombre())) {
                // Selecciona un elemento para mover
                elementoSeleccionado = new Point(x, y);
                boton.setBackground(Color.DARK_GRAY);  // Resaltar elemento
                boton.setForeground(Color.WHITE);
            } else if (elementoSeleccionado != null) {
                // Verificar si el movimiento es a una casilla adyacente 
                 if (Math.abs(elementoSeleccionado.x - x) <= 1 && Math.abs(elementoSeleccionado.y - y) <= 1) {                  
                    // Mover el superviviente a la nueva casilla y marcarla como ocupada
                    partida.activarSuperviviente(0, x, y);

                    tablero.posicionesOcupadas[x][y] = true;
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setForeground(Color.BLACK);
                    tablero.posicionesOcupadas[x][y] = true;
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setForeground(Color.BLACK);

                    // Coger el texto del nuevo boton y añadirle el superviviente
                    StringBuilder sb = new StringBuilder();
                    String textoBotonDestino = botones[x][y].getText();
                    textoBotonDestino = textoBotonDestino.replace("</html>", ""); // Quitamos el cierre de HTML
                    sb.append(textoBotonDestino);
                    sb.append(partida.getSupervivienteActual().getNombre());
                    sb.append("<br>"); // Salto de linea en HTML
                    sb.append("</html>"); // Colocamos el cierre HTML
                    botones[x][y].setText(sb.toString());

                    // Quitamos del boton el nombre del superviviente
                    String textoBotonOrigen = botones[elementoSeleccionado.x][elementoSeleccionado.y].getText();
                    textoBotonOrigen = textoBotonOrigen.replace(supervivienteActual.getNombre() + "<br>","");
                    if(tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y).getContadorSupervivientes() == 0){
                        if(tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y).getContadorZombis() == 0){
                            // Marcamos la casilla como vacia
                            tablero.posicionesOcupadas[elementoSeleccionado.x][elementoSeleccionado.y] = false;
                            botones[elementoSeleccionado.x][elementoSeleccionado.y].setText("<html></html>"); // Vacio
                        } else{
                            botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigen);
                        }
                    } else{
                        botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigen);
                    }
                    elementoSeleccionado = null;
                    panelMenuJugador.movimientoActivado = false;
                    panelMenuJugador.activacionBotones(true);
                    accionTerminada();
                }
            }
        } else{
            JOptionPane.showMessageDialog(null,"No puede moverse en este momento");
        }
    }

    public void atacar(JButton boton, int x, int y){
        if(panelMenuJugador.atacarActivado){
            Superviviente supervivienteActual = partida.getSupervivienteActual();
            ArrayList<Casilla> casillasAlcance = supervivienteActual.elegirObjetivo(armaActiva);
            for (int i = 0; i < casillasAlcance.size(); i++){
                botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setBackground(Color.RED);
                botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setForeground(Color.WHITE);
            }
            if(elementoSeleccionado == null && boton.getText().contains(supervivienteActual.getNombre())){
                elementoSeleccionado = new Point(x, y);
                boton.setBackground(Color.DARK_GRAY);
                boton.setForeground(Color.WHITE);
            } else if(elementoSeleccionado != null){               
                    // Atacar al zombi
                    //partida.activarSuperviviente(, x, y);
                    // Actualizar el botón
                    StringBuilder sb = new StringBuilder();
                    String textoBotonDestino = botones[x][y].getText();
                    textoBotonDestino = textoBotonDestino.replace("</html>", "");
                    sb.append(textoBotonDestino);
                    sb.append(partida.getSupervivienteActual().getNombre());
                    sb.append("<br>");
                    sb.append("</html>");
                    botones[x][y].setText(sb.toString());
                    // Actualizar el botón de origen
                    String textoBotonOrigen = botones[elementoSeleccionado.x][elementoSeleccionado.y].getText();
                    textoBotonOrigen = textoBotonOrigen.replace(supervivienteActual.getNombre() + "<br>", "");
                    if(tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y).getContadorSupervivientes() == 0){
                        if(tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y).getContadorZombis() == 0){
                            tablero.posicionesOcupadas[elementoSeleccionado.x][elementoSeleccionado.y] = false;
                            botones[elementoSeleccionado.x][elementoSeleccionado.y].setText("<html></html>");
                        } else{
                            botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigen);
                        }
                    } else{
                        botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigen);
                    }
                    elementoSeleccionado = null;
                    panelMenuJugador.activacionBotones(true);
                    panelMenuJugador.atacarActivado = false;
                    accionTerminada();
                }
            }
        }
         
    
    public static void main(String args[]){ 
        Juego juego = new Juego();
        juego.iniciarPartida();
    }
}
