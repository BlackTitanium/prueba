package mainpackage;

import java.util.Random;
import javax.swing.*; // Porque estamos haciendo una interfaz gráfica
import javax.swing.event.*; // Cuando usamos CheckBox
import java.awt.*; // Porque vamos a trabajar con colores
import java.awt.event.*; // Porque vamos a trabajar con eventos: botones, combox, ...
import mainpackage.*; // Para usar Casilla

import java.util.ArrayList;
import java.util.List;

public class InterfazPrincipal extends JFrame implements ActionListener{
    private static final int SIZE = 10;  // Tamaño del tablero 10x10
    public static JButton[][] botones = new JButton[SIZE][SIZE];   
    public static Casilla[][] casillas = new Casilla[SIZE][SIZE];
    private Point elementoSeleccionado = null;  // Guarda la posición del elemento seleccionado
    private JPanel panelTablero;
    public static CardLayout cardLayout;
    public static JPanel panelDerechoPrincipal;
    
    public boolean[][] posicionesOcupadas = new boolean[SIZE][SIZE];
    
    public static int numJ = 0;
    
    private Partida partida;
            
    public InterfazPrincipal(){
        setTitle("Juego");
        setBounds(0, 0, 1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout()); // BorderLayout() divide el la ventana en 5 zonas (Norte, Sur, Este, Oeste y Centro)
        
        // Panel del tablero (a la izquierda)
        panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(SIZE, SIZE));
        // GridLayout(,) organiza los componentes en una cuadrícula rectangular con un número fijo de filas y columnas,
        // donde todos los componentes tienen el mismo tamaño
        panelTablero.setPreferredSize(new Dimension(600, 600));  // Tamaño fijo para el tablero
        inicializarTablero();
        
        cardLayout = new CardLayout();
        panelDerechoPrincipal = new JPanel(cardLayout);
        
        // Panel de derecho principal (a la derecha)
        panelDerechoPrincipal = new JPanel();
        panelDerechoPrincipal.setLayout(null); // Usar setBounds(,,) para los componentes
        panelDerechoPrincipal.setPreferredSize(new Dimension(400, 600));
        
        // Añadimos voy las clases panel al panel de derecho principal
        panelDerechoPrincipal.add(new panelInicio(),"panelInicio");
        
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
                botones[i][j].addActionListener(new MoverElemento(i, j));
                panelTablero.add(botones[i][j]);
            }
        }
        colocarElementosIniciales();
    }
    
    private void colocarElementosIniciales(){
        for(int i = 0; i<panelInicio.nJugadores; i++){
            // casillas[0][0].addSuperviviente(); Argumento es el nombre 
        }
        Random random = new Random();
        posicionesOcupadas[0][0] = true; // Marcar la [0][0] como ocupada
        for (int i = 0; i < 3; i++) {
            int x, y;
            do { 
                x = random.nextInt(SIZE); 
                y = random.nextInt(SIZE); 
            } while (posicionesOcupadas[x][y]);
            
            posicionesOcupadas[x][y] = true; // Marcar la nueva casilla como ocupada
            
//            Zombi z = new Zombi();  // Crear un zombi
//            casillas[x][y].addZombi(z);  // Añadir zombi a la casilla
            botones[x][y].setText("Z");  // Mostrar "Z" en el botón
        }
    }
    
    // Añadir zombie
    public void addZombie(){
        Random random = new Random();
        int x, y;
        do { 
            x = random.nextInt(SIZE); 
            y = random.nextInt(SIZE); 
        } while (posicionesOcupadas[x][y]);
        
//        Zombi z = new Zombi();  // Crear un zombi
//        casillas[x][y].addZombi(z);  // Añadir zombi a la casilla
        botones[x][y].setText("Z");  // Mostrar "Z" en el botón
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
            if (elementoSeleccionado == null && boton.getText().equals("S" + partida.getTurnoActual())) {
                // Selecciona un elemento para mover
                elementoSeleccionado = new Point(x, y);
                boton.setBackground(Color.DARK_GRAY);  // Resaltar elemento
            } else if (elementoSeleccionado != null) {
                // Verificar si el movimiento es a una casilla adyacente 
                 if (Math.abs(elementoSeleccionado.x - x) <= 1 && Math.abs(elementoSeleccionado.y - y) <= 1) { 
                    int zombisEnCasillaOrigen = casillas[elementoSeleccionado.x][elementoSeleccionado.y].getContadorZombis();
                    int accionesExtras = zombisEnCasillaOrigen; // Una acción extra por cada zombi en la casilla de origen
                   
                   // Mover el superviviente a la nueva celda
                   botones[elementoSeleccionado.x][elementoSeleccionado.y].setText("");
                   botones[elementoSeleccionado.x][elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                   botones[x][y].setText("S" + partida.getTurnoActual());
                   casillas[elementoSeleccionado.x][elementoSeleccionado.y].removeSuperviviente(partida.getSupervivienteActual());
                   casillas[x][y].addSuperviviente(partida.getSupervivienteActual());
                   elementoSeleccionado = null;
                   
                   // Gestionar el gasto de acciones adicionales aquí... 
                   // (Esta parte depende de cómo gestiones los turnos y acciones en tu juego)
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
    }
    
    public static void main(String args[]){     
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new InterfazPrincipal();
            }
        });
    }
}
