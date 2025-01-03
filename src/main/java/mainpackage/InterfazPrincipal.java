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
    public static JButton[][] botones = new JButton[SIZE][SIZE];   
    public static Casilla[][] casillas = new Casilla[SIZE][SIZE];
    private Point elementoSeleccionado = null;  // Guarda la posición del elemento seleccionado
    private JPanel panelTablero;
    public static CardLayout cardLayout;
    public static JPanel panelDerechoPrincipal;
    
    public static boolean[][] posicionesOcupadas = new boolean[SIZE][SIZE];
    
    public static int numJ = 0;
                
    public InterfazPrincipal(){
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
        panelDerechoPrincipal.add(new PanelInicio(),"PanelInicio");
        panelDerechoPrincipal.add(new PanelMenuJugador(),"PanelMenuJugador");
        
        // Añadir paneles al JFrame
        add(panelTablero, BorderLayout.CENTER);  // Tablero en el centro (ocupa la izquierda)
        add(panelDerechoPrincipal, BorderLayout.EAST);    // Panel de control a la derecha
        
        setVisible(true);
    }
    
    // Inicializar el tablero con Casillas y Botones
    private void inicializarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                casillas[i][j] = new Casilla(i, j);  // Crear nueva casilla
                botones[i][j] = new JButton();
                botones[i][j].setFocusPainted(false);
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                botones[i][j].setFont(new Font("Arial",0,10));
                botones[i][j].setText("<html></html>");
                botones[i][j].addActionListener(new MoverElemento(i, j));
                panelTablero.add(botones[i][j]);
            }
        }
    }
    
    public static void reiniciarTablero() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                botones[i][j].setText("");
                botones[i][j].setBackground(Color.LIGHT_GRAY);
                casillas[i][j].reiniciarCasilla();
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
            if (elementoSeleccionado == null && boton.getText().equals(Partida.getSupervivienteActual().getNombre())) {
                // Selecciona un elemento para mover
                elementoSeleccionado = new Point(x, y);
                boton.setBackground(Color.DARK_GRAY);  // Resaltar elemento
            } else if (elementoSeleccionado != null) {
                // Verificar si el movimiento es a una casilla adyacente 
                 if (Math.abs(elementoSeleccionado.x - x) <= 1 && Math.abs(elementoSeleccionado.y - y) <= 1) {                    
                    // Mover el superviviente a la nueva casilla y marcarla como ocupada
                    casillas[elementoSeleccionado.x][elementoSeleccionado.y].removeSuperviviente(Partida.getSupervivienteActual());
                    casillas[x][y].addSuperviviente(Partida.getSupervivienteActual());
                    posicionesOcupadas[x][y] = true;
                    botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    // Coger el texto del nuevo boton y añadirle el superviviente
                    StringBuilder sb = new StringBuilder();
                    String textoBotonDestino = botones[x][y].getText();
                    textoBotonDestino = textoBotonDestino.replace("</html>", ""); // Quitamos el cierre de HTML
                    sb.append(textoBotonDestino);
                    sb.append(Partida.getSupervivienteActual().getNombre());
                    sb.append("<br>"); // Salto de linea en HTML
                    sb.append("</html>"); // Colocamos el cierre HTML
                    botones[x][y].setText(sb.toString());
                    // Quitamos del boton el nombre del superviviente
                    String textoBotonOrigen = botones[elementoSeleccionado.x][elementoSeleccionado.y].getText();
                    textoBotonOrigen = textoBotonOrigen.replace(Partida.getSupervivienteActual().getNombre() + "<br>","");
                    if(casillas[elementoSeleccionado.x][elementoSeleccionado.y].getContadorSupervivientes() == 0){
                        if(casillas[elementoSeleccionado.x][elementoSeleccionado.y].getContadorZombis() == 0){
                            // Marcamos la casilla como vacia
                            posicionesOcupadas[elementoSeleccionado.x][elementoSeleccionado.y] = false;
                            botones[elementoSeleccionado.x][elementoSeleccionado.y].setText("<html></html>"); // Vacio
                        } else{
                            botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigen);
                        }
                    } else{
                        botones[elementoSeleccionado.x][elementoSeleccionado.y].setText(textoBotonOrigen);
                    }
                    
                    elementoSeleccionado = null;

                    // Gestionar el gasto de acciones adicionales aquí... 
                    // (Esta parte depende de cómo gestiones los turnos y acciones en tu juego)
                }
            }
        }
    }
    
    public static void main(String args[]){     
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new InterfazPrincipal();
            }
        });
    }
}
