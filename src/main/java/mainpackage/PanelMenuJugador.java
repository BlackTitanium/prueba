package mainpackage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelMenuJugador extends JPanel{
    private JButton botonMoverse, botonBuscar, botonAtacar, botonElegirArma, botonNada, botonAlmacenDeAtaques, botonArma1, botonArma2, botonInv1, botonInv2, botonInv3, botonInv4, botonInv5;
    private JLabel turnoDe, numAcciones, contZombis, mordeduras;
    
    public boolean movimientoActivado = false;
    public boolean atacarActivado = false;
    
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;    

    private int ranuraElegida;
    
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

        botonArma1 = new JButton("Arma 1");
        botonArma1.setBounds(70,355,120,30);
        botonArma1.setBackground(Color.LIGHT_GRAY);
        botonArma1.setFont(new Font("Arial", 1, 14));
        botonArma1.setForeground(Color.BLACK);
        add(botonArma1);

        botonArma2 = new JButton("Arma 2");
        botonArma2.setBounds(230,355,120,30);
        botonArma2.setBackground(Color.LIGHT_GRAY);
        botonArma2.setFont(new Font("Arial", 1, 14));
        botonArma2.setForeground(Color.BLACK);
        add(botonArma2);

        botonInv1 = new JButton("Inventario 1");
        botonInv1.setBounds(70,415,120,30);
        botonInv1.setBackground(Color.LIGHT_GRAY);
        botonInv1.setFont(new Font("Arial", 1, 14));
        botonInv1.setForeground(Color.BLACK);
        add(botonInv1);

        botonInv2 = new JButton("Inventario 2");
        botonInv2.setBounds(230,415,120,30);
        botonInv2.setBackground(Color.LIGHT_GRAY);
        botonInv2.setFont(new Font("Arial", 1, 14));
        botonInv2.setForeground(Color.BLACK);
        add(botonInv2);

        botonInv3 = new JButton("Inventario 3");
        botonInv3.setBounds(70,475,120,30);
        botonInv3.setBackground(Color.LIGHT_GRAY);
        botonInv3.setFont(new Font("Arial", 1, 14));
        botonInv3.setForeground(Color.BLACK);
        add(botonInv3);

        botonInv4 = new JButton("Inventario 4");
        botonInv4.setBounds(230,475,120,30);
        botonInv4.setBackground(Color.LIGHT_GRAY);
        botonInv4.setFont(new Font("Arial", 1, 14));
        botonInv4.setForeground(Color.BLACK);
        add(botonInv4);

        botonInv5 = new JButton("Inventario 5");
        botonInv5.setBounds(150,535,120,30);
        botonInv5.setBackground(Color.LIGHT_GRAY);
        botonInv5.setFont(new Font("Arial", 1, 14));
        botonInv5.setForeground(Color.BLACK);
        add(botonInv5);
        
        botonAlmacenDeAtaques = new JButton("Historial Ataques");
        botonAlmacenDeAtaques.setBounds(70,650,155,30);
        botonAlmacenDeAtaques.setBackground(Color.LIGHT_GRAY);
        botonAlmacenDeAtaques.setFont(new Font("Arial", 1, 14));
        botonAlmacenDeAtaques.setForeground(Color.BLACK);
        add(botonAlmacenDeAtaques);

        activacionBotones(true);
        actualizarLabels();
        
        botonMoverse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                movimientoActivado = true;
                activacionBotones(false);
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.MOVER);
                System.out.println("Movimiento activado: " + movimientoActivado);
            }
        });
        
        botonBuscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.BUSCAR);
                activacionBotones(false);
                activacionInventario(true);
            }
        });
        
        botonAtacar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                atacarActivado = true;
                activacionBotones(false);
                activacionArmas(atacarActivado);
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.ATACAR);
            }
        });
        
        botonElegirArma.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.INVENTARIO);
            }
        });
        
        botonNada.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.NADA);
            }
        });

        botonArma1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.armaActiva = partida.getSupervivienteActual().getArmas()[0];
                activacionArmas(false);
                activacionInventario(true);
            }
        });
        botonAlmacenDeAtaques.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal,"PanelAlmacenDeAtaques");
            }
        });
        botonArma1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 1;
                partida.activarSuperviviente(ranuraElegida, 0, 0);
                activacionBotones(true);
            }
        });
        botonArma2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 2;
            }
        });
        botonInv1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 1;
                partida.activarSuperviviente(ranuraElegida, 0, 0);
                activacionBotones(true);
                actualizarLabels();
            }
        });
        botonInv2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 2;
            }
        });
        botonInv3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 3;
            }
        });
        botonInv4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 4;
            }
        });
        botonInv5.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ranuraElegida = 5;
            }
        });
    }
    
    public void activacionBotones(boolean enabled) {
        botonMoverse.setEnabled(enabled);
        botonBuscar.setEnabled(enabled);
        botonAtacar.setEnabled(enabled);
        botonElegirArma.setEnabled(enabled);
        botonNada.setEnabled(enabled);
        botonArma1.setEnabled(false);
        botonArma2.setEnabled(false);
        botonInv1.setEnabled(false);
        botonInv2.setEnabled(false);
        botonInv3.setEnabled(false);
        botonInv4.setEnabled(false);
        botonInv5.setEnabled(false);
    }
    public void activacionInventario(boolean enabled) {
        botonInv1.setEnabled(enabled);
        botonInv2.setEnabled(enabled);
        botonInv3.setEnabled(enabled);
        botonInv4.setEnabled(enabled);
        botonInv5.setEnabled(enabled);
    }

    public void activacionArmas(boolean enabled) {
        botonArma1.setEnabled(enabled);
        botonArma2.setEnabled(enabled);
    }

    public void actualizarLabels(){
        turnoDe.setText("Turno de: " + partida.getSupervivienteActual().getNombre());
        numAcciones.setText("Acciones: " + partida.getSupervivienteActual().getAcciones());
        contZombis.setText("Numero de zombis asesinados: " + partida.getSupervivienteActual().getContadorZombis());
        mordeduras.setText("Mordeduras : " + partida.getSupervivienteActual().getMordeduras());
        try {
            botonArma1.setText(partida.getSupervivienteActual().getArmas()[0].getNombre());
            botonArma1.setToolTipText(partida.getSupervivienteActual().getArmas()[0].toString());
        } catch (NullPointerException e) {
            botonArma1.setText("Arma 1: Vacío");
            botonArma1.setToolTipText("");
        }
        try {
            botonArma2.setText(partida.getSupervivienteActual().getArmas()[1].getNombre());
            botonArma2.setToolTipText(partida.getSupervivienteActual().getArmas()[1].toString());
        } catch (NullPointerException e) {
            botonArma2.setText("Arma 2: Vacío");
            botonArma2.setToolTipText("");
        }
        try {
            botonInv1.setText(partida.getSupervivienteActual().getInventario()[0].getNombre());
            botonInv1.setToolTipText(partida.getSupervivienteActual().getInventario()[0].toString());
        } catch (NullPointerException e) {
            botonInv1.setText("Inventario 1: Vacío");
            botonInv1.setToolTipText("");
        }
        try {
            botonInv2.setText(partida.getSupervivienteActual().getInventario()[1].getNombre());
            botonInv2.setToolTipText(partida.getSupervivienteActual().getInventario()[1].toString());
        } catch (NullPointerException e) {
            botonInv2.setText("Inventario 2: Vacío");
            botonInv2.setToolTipText("");
        }
        try {
            botonInv3.setText(partida.getSupervivienteActual().getInventario()[2].getNombre());
            botonInv3.setToolTipText(partida.getSupervivienteActual().getInventario()[2].toString());
        } catch (NullPointerException e) {
            botonInv3.setText("Inventario 3: Vacío");
            botonInv3.setToolTipText("");
        }
        try {
            botonInv4.setText(partida.getSupervivienteActual().getInventario()[3].getNombre());
            botonInv4.setToolTipText(partida.getSupervivienteActual().getInventario()[3].toString());
        } catch (NullPointerException e) {
            botonInv4.setText("Inventario 4: Vacío");
            botonInv4.setToolTipText("");
        }
        try {
            botonInv5.setText(partida.getSupervivienteActual().getInventario()[4].getNombre());
            botonInv5.setToolTipText(partida.getSupervivienteActual().getInventario()[4].toString());
        } catch (NullPointerException e) {
            botonInv5.setText("Inventario 5: Vacío");
            botonInv5.setToolTipText("");
        }
    }
}
