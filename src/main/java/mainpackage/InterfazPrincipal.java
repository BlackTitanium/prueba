package mainpackage;

import java.util.Random;
import javax.swing.*; // Porque estamos haciendo una interfaz gráfica
import javax.swing.event.*; // Cuando usamos CheckBox
import java.awt.*; // Porque vamos a trabajar con colores
import java.awt.event.*; // Porque vamos a trabajar con eventos: botones, combox, ...
import mainpackage.*; // Para usar Casilla

import java.util.ArrayList;
import java.util.List;

public class InterfazPrincipal extends JFrame{
    private static final int SIZE = 10;  // Tamaño del tablero 10x10
    public JButton[][] botones = new JButton[SIZE][SIZE];   
    private Point elementoSeleccionado = null;  // Guarda la posición del elemento seleccionado
    
    public CardLayout cardLayout;
    public JPanel panelTablero, panelDerechoPrincipal, panelBotonesPermanentes;
    
    PanelMenuJugador panelMenuJugador;
    
    public int nJugadores = 0;
    public boolean movimientoActivado = false;
    
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
        PanelInicio panelInicio = new PanelInicio(partida, this);
        panelDerechoPrincipal.add(panelInicio,"PanelInicio");
        
        // Añadir paneles al JFrame
        add(panelTablero, BorderLayout.CENTER);  // Tablero en el centro (ocupa la izquierda)
        add(panelDerechoPrincipal, BorderLayout.EAST);    // Panel de control a la derecha
        
        setVisible(true);
    }
    
    public void inicializarPaneles(Partida p){
        panelMenuJugador = new PanelMenuJugador(p,this);
        panelDerechoPrincipal.add(panelMenuJugador,"PanelMenuJugador");
        panelDerechoPrincipal.revalidate();
        panelDerechoPrincipal.repaint();
    }
    
    // Inicializar el tablero con Casillas y Botones
    private void inicializarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
//                casillas[i][j] = new Casilla(i, j);  // Crear nueva casilla
                botones[i][j] = new JButton();
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setFont(new Font("Arial",0,10));
                botones[i][j].setText("<html></html>");
                //botones[i][j].addActionListener(new MoverElemento(i, j));
                panelTablero.add(botones[i][j]);
            }
        }
    }
    
    public void añadirActionListener(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {    
                botones[i][j].addActionListener(new MoverElemento(i, j));
            }
        }
    }
    
    public void reiniciarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                botones[i][j].setText("");
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                tablero.reiniciarTablero();
            }
        }
    }
    
    // Acción al hacer clic en una celda
    private class MoverElemento implements ActionListener {
        private int x, y;
        public MoverElemento(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton boton = botones[x][y];
            
            if (elementoSeleccionado == null && boton.getText().equals(partida.getSupervivienteActual().getNombre())) {
                // Selecciona un elemento para mover
                elementoSeleccionado = new Point(x, y);
                boton.setBackground(Color.DARK_GRAY);  // Resaltar elemento
            } else if (elementoSeleccionado != null) {
                // Verificar si el movimiento es a una casilla adyacente 
                 if (Math.abs(elementoSeleccionado.x - x) <= 1 && Math.abs(elementoSeleccionado.y - y) <= 1) {                    
                    // Mover el superviviente a la nueva casilla y marcarla como ocupada
//                    tablero.getCasilla(elementoSeleccionado.x, elementoSeleccionado.y).removeSuperviviente(partida.getSupervivienteActual());
//                    tablero.getCasilla(x,y).addSuperviviente(partida.getSupervivienteActual());
                    tablero.posicionesOcupadas[x][y] = true;
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    
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
                    textoBotonOrigen = textoBotonOrigen.replace(partida.getSupervivienteActual().getNombre() + "<br>","");
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
                    
                    panelMenuJugador.activacionBotones(true);
                    movimientoActivado = false;
                }
            }
        }
    }
    
    public static void main(String args[]){
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override public void run() {
//                new InterfazPrincipal(new Partida());
//            }
//        });
//        new InterfazPrincipal(new Partida(2));
    }
}
