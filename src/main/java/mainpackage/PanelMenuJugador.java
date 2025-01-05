package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelMenuJugador extends JPanel{
    private JButton botonMoverse, botonBuscar, botonAtacar, botonElegirArma, botonNada;
    
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;    
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelMenuJugador(Partida Partida, InterfazPrincipal interfazprincipal){
        this.partida = Partida;
        this.interfazPrincipal = interfazprincipal;
//        this.partida = interfazPrincipal.partida;
        
        setSize(400,745);
        setLayout(null);
        
        System.out.println("Antes de imprimir los nombres");
        for(int i=0; i<interfazPrincipal.nJugadores; i++){
            String nombre = partida.getSupervivienteIndice(i).getNombre();
            System.out.print(nombre + "/");
        }
        
        JLabel titulo = new JLabel("Menu Jugador");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(125,20,150,20);
        add(titulo);
        
        JPanel panelCombo1 = new JPanel();
        panelCombo1.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        panelCombo1.setBounds(15,60,300,15);
        
//        JLabel turnoDe = new JLabel();
        JLabel turnoDe = new JLabel("Turno de: " + partida.getSupervivienteActual().getNombre());
        turnoDe.setFont(new Font("Arial", 1, 15));
        panelCombo1.add(turnoDe);
        
        JLabel numAcciones = new JLabel("Acciones: " + partida.getSupervivienteActual().getAcciones());
        numAcciones.setFont(new Font("Arial", 1, 15));
        panelCombo1.add(numAcciones);
        
        add(panelCombo1);
        
        JLabel selecOpcion = new JLabel("Selecciona la acción a realizar: ");
        selecOpcion.setFont(new Font("Arial", 0, 12));
        selecOpcion.setBounds(70,100,200,12);
        add(selecOpcion);
        
        botonMoverse = new JButton("Moverse");
        botonMoverse.setBounds(70,140,120,30);
        botonMoverse.setBackground(Color.LIGHT_GRAY);
        botonMoverse.setFont(new Font("Arial", 1, 14));
        botonMoverse.setForeground(Color.BLACK);
        add(botonMoverse);
        
        botonBuscar = new JButton("Buscar");
        botonBuscar.setBounds(230,140,120,30);
        botonBuscar.setBackground(Color.LIGHT_GRAY);
        botonBuscar.setFont(new Font("Arial", 1, 14));
        botonBuscar.setForeground(Color.BLACK);
        add(botonBuscar);
        
        botonAtacar = new JButton("Atacar");
        botonAtacar.setBounds(70,200,120,30);
        botonAtacar.setBackground(Color.LIGHT_GRAY);
        botonAtacar.setFont(new Font("Arial", 1, 14));
        botonAtacar.setForeground(Color.BLACK);
        add(botonAtacar);
        
        botonElegirArma = new JButton("Elegir Arma");
        botonElegirArma.setBounds(230,200,120,30);
        botonElegirArma.setBackground(Color.LIGHT_GRAY);
        botonElegirArma.setFont(new Font("Arial", 1, 14));
        botonElegirArma.setForeground(Color.BLACK);
        add(botonElegirArma);
        
        botonNada = new JButton("Nada");
        botonNada.setBounds(70,260,120,30);
        botonNada.setBackground(Color.LIGHT_GRAY);
        botonNada.setFont(new Font("Arial", 1, 14));
        botonNada.setForeground(Color.BLACK);
        add(botonNada);
        
        activacionBotones(true);
//        actualizarLabels(turnoDe);
        
        botonElegirArma = new JButton("Elegir Arma");
        botonElegirArma.setBounds(230,200,120,30);
        botonElegirArma.setBackground(Color.LIGHT_GRAY);
        botonElegirArma.setFont(new Font("Arial", 1, 14));
        botonElegirArma.setForeground(Color.BLACK);
        add(botonElegirArma);
        
        botonMoverse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.movimientoActivado = true;
                activacionBotones(false);
            }
        });
        
        botonBuscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        botonAtacar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        botonElegirArma.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        botonNada.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }
    
    public void activacionBotones(boolean enabled) {
        botonMoverse.setEnabled(enabled);
        botonBuscar.setEnabled(enabled);
        botonAtacar.setEnabled(enabled);
        botonElegirArma.setEnabled(enabled);
        botonNada.setEnabled(enabled);
    }
    
//    public void actualizarLabels(JLabel turnoDe){
//        turnoDe.setText("Turno de: " + partida.getSupervivienteActual().getNombre());
//    }
}
