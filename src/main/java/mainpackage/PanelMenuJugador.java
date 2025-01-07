package mainpackage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelMenuJugador extends JPanel{
    private JButton botonMoverse, botonBuscar, botonAtacar, botonElegirArma, botonNada;
    JLabel turnoDe, numAcciones;
    
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
        
        JPanel panelCombo1 = new JPanel();
        panelCombo1.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        panelCombo1.setBounds(15,60,300,15);
        
        turnoDe = new JLabel();
        turnoDe.setFont(new Font("Arial", 1, 15));
        panelCombo1.add(turnoDe);
        
        numAcciones = new JLabel();
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
        actualizarLabels();
        
        botonElegirArma = new JButton("Elegir Arma");
        botonElegirArma.setBounds(230,200,120,30);
        botonElegirArma.setBackground(Color.LIGHT_GRAY);
        botonElegirArma.setFont(new Font("Arial", 1, 14));
        botonElegirArma.setForeground(Color.BLACK);
        add(botonElegirArma);
        
        botonMoverse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
//                interfazPrincipal.movimientoActivado = true;
                movimientoActivado = true;
                activacionBotones(false);
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
    }
}
