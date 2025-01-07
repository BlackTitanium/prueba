package mainpackage;

import java.awt.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelMenuJugador extends JPanel{
    private JButton botonMoverse, botonBuscar, botonAtacar, botonElegirArma, botonNada, botonAlmacenDeAtaques;
    private JLabel turnoDe, numAcciones, contZombis, mordeduras;
    
    public boolean movimientoActivado = false;
    
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;    
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelMenuJugador(Partida Partida, InterfazPrincipal interfazprincipal){
        this.partida = Partida;
        this.interfazPrincipal = interfazprincipal;
        
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Menu Jugador");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(125,20,150,20);
        add(titulo);
        
        JPanel panelLabelsVertical1 = new JPanel();
        panelLabelsVertical1.setLayout(new BoxLayout(panelLabelsVertical1, BoxLayout.Y_AXIS));
        panelLabelsVertical1.setBounds(15,60,300,70);
        
        JPanel panelLabelsHorizontal1 = new JPanel();
        panelLabelsHorizontal1.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        
        turnoDe = new JLabel();
        turnoDe.setFont(new Font("Arial", 1, 15));
        panelLabelsHorizontal1.add(turnoDe);
        
        numAcciones = new JLabel();
        numAcciones.setFont(new Font("Arial", 1, 15));
        panelLabelsHorizontal1.add(numAcciones);
        
        JPanel panelLabelsHorizontal2 = new JPanel();
        panelLabelsHorizontal2.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
        contZombis = new JLabel();
        contZombis.setFont(new Font("Arial", 1, 15));
        panelLabelsHorizontal2.add(contZombis);
        
        JPanel panelLabelsHorizontal3 = new JPanel();
        panelLabelsHorizontal3.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0));
        mordeduras = new JLabel();
        mordeduras.setFont(new Font("Arial", 1, 15));
        panelLabelsHorizontal3.add(mordeduras);
        
        panelLabelsVertical1.add(panelLabelsHorizontal1);
        panelLabelsVertical1.add(Box.createVerticalStrut(5));
        panelLabelsVertical1.add(panelLabelsHorizontal2);
        panelLabelsVertical1.add(Box.createVerticalStrut(5));
        panelLabelsVertical1.add(panelLabelsHorizontal3);
        add(panelLabelsVertical1);
        
        JLabel selecOpcion = new JLabel("Selecciona la acción a realizar: ");
        selecOpcion.setFont(new Font("Arial", 0, 12));
        selecOpcion.setBounds(70,150,200,12);
        add(selecOpcion);
        
        botonMoverse = new JButton("Moverse");
        botonMoverse.setBounds(70,175,120,30);
        botonMoverse.setBackground(Color.LIGHT_GRAY);
        botonMoverse.setFont(new Font("Arial", 1, 14));
        botonMoverse.setForeground(Color.BLACK);
        add(botonMoverse);
        
        botonBuscar = new JButton("Buscar");
        botonBuscar.setBounds(230,175,120,30);
        botonBuscar.setBackground(Color.LIGHT_GRAY);
        botonBuscar.setFont(new Font("Arial", 1, 14));
        botonBuscar.setForeground(Color.BLACK);
        add(botonBuscar);
        
        botonAtacar = new JButton("Atacar");
        botonAtacar.setBounds(70,235,120,30);
        botonAtacar.setBackground(Color.LIGHT_GRAY);
        botonAtacar.setFont(new Font("Arial", 1, 14));
        botonAtacar.setForeground(Color.BLACK);
        add(botonAtacar);
        
        botonElegirArma = new JButton("Elegir Arma");
        botonElegirArma.setBounds(230,235,120,30);
        botonElegirArma.setBackground(Color.LIGHT_GRAY);
        botonElegirArma.setFont(new Font("Arial", 1, 14));
        botonElegirArma.setForeground(Color.BLACK);
        add(botonElegirArma);
        
        botonNada = new JButton("Nada");
        botonNada.setBounds(140,295,120,30);
        botonNada.setBackground(Color.LIGHT_GRAY);
        botonNada.setFont(new Font("Arial", 1, 14));
        botonNada.setForeground(Color.BLACK);
        add(botonNada);
        
        activacionBotones(true);
        actualizarLabels();
        
        botonAlmacenDeAtaques = new JButton("Historial Ataques");
        botonAlmacenDeAtaques.setBounds(70,355,155,30);
        botonAlmacenDeAtaques.setBackground(Color.LIGHT_GRAY);
        botonAlmacenDeAtaques.setFont(new Font("Arial", 1, 14));
        botonAlmacenDeAtaques.setForeground(Color.BLACK);
        add(botonAlmacenDeAtaques);
        
        botonMoverse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
//                interfazPrincipal.movimientoActivado = true;
                movimientoActivado = true;
                activacionBotones(false);
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.MOVER);
                System.out.println("Movimiento activado: " + movimientoActivado);
//              
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
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.MOVER);
//                partida.getSupervivienteActual().activar(0, x, y);
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
    public void actualizarLabels(){
        turnoDe.setText("Turno de: " + partida.getSupervivienteActual().getNombre());
        numAcciones.setText("Acciones: " + partida.getSupervivienteActual().getAcciones());
        contZombis.setText("Numero de zombis asesinados: " + partida.getSupervivienteActual().getContadorZombis());
        mordeduras.setText("Mordeduras : " + partida.getSupervivienteActual().getMordeduras());
    }
}
