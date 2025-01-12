package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazJuego {
    private Juego juego;

//    public InterfazJuego(Juego juego) {
//        this.juego = juego;
//    }

    public InterfazJuego(Juego juego) {
        this.juego = juego;
        
        // Crear el marco principal
        JFrame frame = new JFrame("APOOcalipsis Zombi");
        frame.setSize(600, 400); // Tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // Tamaño de los componentes
        int anchoComponente = 200;
        int altoComponente = 30;

        // Calcular posiciones centradas
        int posX = (frame.getWidth() - anchoComponente) / 2;
        int posYEligeOpcion = 100;
        int espacioEntreComponentes = 50;

        // Crear el título
        JLabel titulo = new JLabel("APOOcalipsis Zombi");
        titulo.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente más grande
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBounds(0, 30, frame.getWidth(), 50);
        frame.add(titulo);

        // Crear las opciones
        JLabel eligeOpcion = new JLabel("Elige una opción:");
        eligeOpcion.setBounds(posX, posYEligeOpcion, anchoComponente, altoComponente);
        frame.add(eligeOpcion);

        JButton iniciarPartida = new JButton("1. Iniciar partida");
        iniciarPartida.setBounds(posX, posYEligeOpcion + espacioEntreComponentes, anchoComponente, altoComponente);
        JButton cargarPartida = new JButton("2. Cargar partida");
        cargarPartida.setBounds(posX, posYEligeOpcion + 2 * espacioEntreComponentes, anchoComponente, altoComponente);
        JButton simulaciones = new JButton("3. Simulaciones");
        simulaciones.setBounds(posX, posYEligeOpcion + 3 * espacioEntreComponentes, anchoComponente, altoComponente);
        JButton salir = new JButton("4. Salir");
        salir.setBounds(posX, posYEligeOpcion + 4 * espacioEntreComponentes, anchoComponente, altoComponente);

        frame.add(iniciarPartida);
        frame.add(cargarPartida);
        frame.add(simulaciones);
        frame.add(salir);

        // Acción para el botón de iniciar partida
        iniciarPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                juego.iniciarPartida();
                frame.dispose();
            }
        });

        // Acción para el botón de cargar partida
        cargarPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear un nuevo cuadro de diálogo para ingresar el número de la partida
                JDialog dialog = new JDialog(frame, "Cargar Partida", true);
                dialog.setSize(400, 300); // Tamaño del cuadro de diálogo
                dialog.setLocationRelativeTo(null);
                dialog.setLayout(null);

                int dialogPosX = (dialog.getWidth() - anchoComponente) / 2;

                JLabel instruccion = new JLabel("Introduce el número de la partida:");
                instruccion.setBounds(dialogPosX - 50, 50, 300, 25);
                JTextArea numeroPartida = new JTextArea(1, 20);
                numeroPartida.setBounds(dialogPosX, 100, anchoComponente, 50);
                JButton confirmar = new JButton("Confirmar");
                confirmar.setBounds(dialogPosX + 50, 200, 100, 30);

                dialog.add(instruccion);
                dialog.add(numeroPartida);
                dialog.add(confirmar);

                // Acción para el botón de confirmar
                confirmar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int id = Integer.parseInt(numeroPartida.getText());
                        juego.cargarPartida(id);
                        dialog.dispose();
                        frame.dispose();
                    }
                });

                dialog.setVisible(true);
            }
        });

        // Acción para el botón de simulaciones
        simulaciones.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                juego.iniciarSimulacion();
                frame.dispose();
            }
        });

        // Acción para el botón de salir
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
