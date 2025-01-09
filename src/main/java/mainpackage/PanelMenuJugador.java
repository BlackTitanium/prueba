package mainpackage;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelMenuJugador extends JPanel{
    public JButton botonMoverse, botonBuscar, botonAtacar, botonInventario, botonNada;
    private JButton botonArma1, botonArma2, botonInv1, botonInv2, botonInv3, botonInv4, botonInv5;
    private JButton botonAlmacenDeAtaques, botonAdmin, botonGuardarSalir;
    private CardLayout cardLayoutMenuJugador;
    private JPanel panelIntercambio, panelOpcionesInventario, panelCancelar, panelInfo;
    private JButton botonCancelar, botonUsar, botonMoverObjetos, botonInfo, botonSalirInventario;
    private JLabel turnoDe, numAcciones, contZombis, mordeduras;
    public JPanel panelInventario;
    
    public boolean movimientoActivado = false;
    public boolean atacarActivado = false, atacarBotonesActivado = false;
    public boolean buscandoActivado = false;
    public boolean inventarioOpcionesActivado = false, inventarioUsarActivado = false, inventarioMoverActivado = false;
    public int objetoSeleccionadoDeArma = 0, objetoSeleccionadoDeInventario = 0;
    public boolean primerObjetoDeInventario = false;
    public int botonSeleccionado1 = -1, botonSeleccionado2 = -1;
    
    private Equipo equipoBuscado;
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;    

    public int ranuraElegida, armaElegida;
    
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
        
        botonInventario = new JButton("Inventario");
        botonInventario.setBounds(230,235,120,30);
        botonInventario.setBackground(Color.LIGHT_GRAY);
        botonInventario.setFont(new Font("Arial", 1, 14));
        botonInventario.setForeground(Color.BLACK);
        add(botonInventario);
        
        botonNada = new JButton("Nada");
        botonNada.setBounds(140,295,120,30);
        botonNada.setBackground(Color.LIGHT_GRAY);
        botonNada.setFont(new Font("Arial", 1, 14));
        botonNada.setForeground(Color.BLACK);
        add(botonNada);
        
        panelInventario = new JPanel();
        panelInventario.setBounds(70, 355, 280, 200); // 220
        panelInventario.setLayout(null);

        botonArma1 = new JButton("Arma 1");
        botonArma1.setBounds(0,0,120,30); // 70, 355
        botonArma1.setBackground(Color.LIGHT_GRAY);
        botonArma1.setFont(new Font("Arial", 1, 14));
        botonArma1.setForeground(Color.BLACK);
        panelInventario.add(botonArma1);

        botonArma2 = new JButton("Arma 2");
        botonArma2.setBounds(160,0,120,30);
        botonArma2.setBackground(Color.LIGHT_GRAY);
        botonArma2.setFont(new Font("Arial", 1, 14));
        botonArma2.setForeground(Color.BLACK);
        panelInventario.add(botonArma2);

        botonInv1 = new JButton("Inventario 1");
        botonInv1.setBounds(0,50,120,30); // 60
        botonInv1.setBackground(Color.LIGHT_GRAY);
        botonInv1.setFont(new Font("Arial", 1, 14));
        botonInv1.setForeground(Color.BLACK);
        panelInventario.add(botonInv1);

        botonInv2 = new JButton("Inventario 2");
        botonInv2.setBounds(160,50,120,30); // 60
        botonInv2.setBackground(Color.LIGHT_GRAY);
        botonInv2.setFont(new Font("Arial", 1, 14));
        botonInv2.setForeground(Color.BLACK);
        panelInventario.add(botonInv2);

        botonInv3 = new JButton("Inventario 3");
        botonInv3.setBounds(0,100,120,30); // 120
        botonInv3.setBackground(Color.LIGHT_GRAY);
        botonInv3.setFont(new Font("Arial", 1, 14));
        botonInv3.setForeground(Color.BLACK);
        panelInventario.add(botonInv3);

        botonInv4 = new JButton("Inventario 4");
        botonInv4.setBounds(160,100,120,30); // 120
        botonInv4.setBackground(Color.LIGHT_GRAY);
        botonInv4.setFont(new Font("Arial", 1, 14));
        botonInv4.setForeground(Color.BLACK);
        panelInventario.add(botonInv4);

        botonInv5 = new JButton("Inventario 5");
        botonInv5.setBounds(80,170,120,30); // 180
        botonInv5.setBackground(Color.LIGHT_GRAY);
        botonInv5.setFont(new Font("Arial", 1, 14));
        botonInv5.setForeground(Color.BLACK);
        panelInventario.add(botonInv5);
        
        add(panelInventario);
        
        botonAlmacenDeAtaques = new JButton("Historiales");
        botonAlmacenDeAtaques.setBounds(20,650,155,30);
        botonAlmacenDeAtaques.setBackground(Color.LIGHT_GRAY);
        botonAlmacenDeAtaques.setFont(new Font("Arial", 1, 14));
        botonAlmacenDeAtaques.setForeground(Color.BLACK);
        add(botonAlmacenDeAtaques);
        
        botonAdmin = new JButton("Modo Administrador");
        botonAdmin.setBounds(200,650,180,30);
        botonAdmin.setBackground(Color.LIGHT_GRAY);
        botonAdmin.setFont(new Font("Arial", 1, 14));
        botonAdmin.setForeground(Color.BLACK);
        add(botonAdmin);
        
        cardLayoutMenuJugador = new CardLayout();
        panelIntercambio = new JPanel(cardLayoutMenuJugador);
        panelIntercambio.setPreferredSize(new Dimension(280,30));
        panelIntercambio.setBounds(0, 575, 400, 40); // 60, , 280
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
        panelInfo.setBounds(90,80,120,30);
        
        botonInfo = new JButton("Informacion");
        botonInfo.setBounds(50,0,120,30);
        botonInfo.setBackground(Color.LIGHT_GRAY);
        botonInfo.setFont(new Font("Arial", 1, 14));
        botonInfo.setForeground(Color.BLACK);
        panelInfo.add(botonInfo);
        
        botonGuardarSalir = new JButton("Guardar y Salir");
        botonGuardarSalir.setBounds(210,0,155,30);
        botonGuardarSalir.setBackground(Color.LIGHT_GRAY);
        botonGuardarSalir.setFont(new Font("Arial", 1, 14));
        botonGuardarSalir.setForeground(Color.BLACK);
        panelInfo.add(botonGuardarSalir);
        
        panelIntercambio.add(panelInfo,"PanelInfo");
        
        panelCancelar = new JPanel();
        panelCancelar.setLayout(null);
        panelCancelar.setBounds(90,80,120,30);
        
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(50,0,120,30);
        botonCancelar.setBackground(Color.LIGHT_GRAY);
        botonCancelar.setFont(new Font("Arial", 1, 14));
        botonCancelar.setForeground(Color.BLACK);
        panelCancelar.add(botonCancelar);
        
        panelIntercambio.add(panelCancelar,"PanelCancelar");
        
        panelOpcionesInventario = new JPanel();
        panelOpcionesInventario.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
        panelOpcionesInventario.setBounds(0, 0, 280, 40);
        
        botonUsar = new JButton("Usar");
        botonUsar.setBounds(20,5,60,30);
        botonUsar.setBackground(Color.LIGHT_GRAY);
        botonUsar.setFont(new Font("Arial", 1, 14));
        botonUsar.setForeground(Color.BLACK);
        panelOpcionesInventario.add(botonUsar);
        
        botonMoverObjetos = new JButton("Intercambiar");
        botonMoverObjetos.setBounds(100,5,120,30);
        botonMoverObjetos.setBackground(Color.LIGHT_GRAY);
        botonMoverObjetos.setFont(new Font("Arial", 1, 14));
        botonMoverObjetos.setForeground(Color.BLACK);
        panelOpcionesInventario.add(botonMoverObjetos);
        
        botonSalirInventario = new JButton("Cancelar");
        botonSalirInventario.setBounds(210,5,120,30);
        botonSalirInventario.setBackground(Color.LIGHT_GRAY);
        botonSalirInventario.setFont(new Font("Arial", 1, 14));
        botonSalirInventario.setForeground(Color.BLACK);
        panelOpcionesInventario.add(botonSalirInventario);
        
        panelIntercambio.add(panelOpcionesInventario,"PanelOpcionesInventario");
        
        add(panelIntercambio);
        
        activacionBotones(true);
        actualizarLabels();
        
        botonMoverse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                movimientoActivado = true;
                activacionBotones(false);
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.MOVER);
                interfazPrincipal.autoSeleccionElementoMoverse();
                System.out.println("Movimiento activado: " + movimientoActivado);
            }
        });
        
        botonBuscar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                equipoBuscado = null;
                Superviviente supervivienteActual = partida.getSupervivienteActual();
                supervivienteActual.setSeleccion(Entidad.accion.BUSCAR);
                equipoBuscado = supervivienteActual.getCasillaActual().buscar(interfazPrincipal);
                StringBuilder sb = new StringBuilder();
                sb.append("Has encontrado:").append("\n");
                sb.append(equipoBuscado.toString()).append("\n");
                sb.append("¿Quieres quedartelo?").append("\n");
                boolean resultado = interfazPrincipal.mostrarMensajeSiNo(sb.toString());
                if(resultado){
                    buscandoActivado = true;
                    StringBuilder aux = new StringBuilder();
                    aux.append("Selecciona la ranura de inventario donde guardarlo").append("\n");
                    aux.append("(Si la ranura esta ocupada se borrara su contenido)").append("\n");
                    interfazPrincipal.mostrarMensaje(aux.toString());
                    System.out.println("El equipo encontrado es: " + equipoBuscado.toString());
                    activacionBotones(false);
                    activacionInventario(true);
                }                
            }
        });
        
        botonAtacar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                atacarBotonesActivado = true;
                activacionBotones(false);
                activacionArmas(true);
            }
        });
        
        botonInventario.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inventarioOpcionesActivado = true;
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.INVENTARIO);
                activacionBotones(false);
                gestionPanelIntercambio(true,1);
            }
        });
        
        botonNada.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                partida.getSupervivienteActual().setSeleccion(Entidad.accion.NADA);
                partida.activarSuperviviente(0, 0, 0, null);
            }
        });
        botonAlmacenDeAtaques.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal,"PanelHistoriales");
            }
        });
        botonArma1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesArmas(0);
            }
        });
        botonArma2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesArmas(1);
            }
        });
        botonInv1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesInventario(0);
            }
        });
        botonInv2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesInventario(1);
            }
        });
        botonInv3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesInventario(2);
            }
        });
        botonInv4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesInventario(3);
            }
        });
        botonInv5.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                accionBotonesInventario(4);
            }
        });
        
        botonInfo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Casilla aux = partida.getTablero().getCasilla(0, 0);
                int idAux = 10000;
                Zombi normalCa = new Zombi(aux,"NORMAL",partida,idAux,"CAMINANTE");
                Zombi normalCo = new Zombi(aux,"NORMAL",partida,idAux,"CORREDOR");
                Zombi normalAb = new Zombi(aux,"NORMAL",partida,idAux,"ABOMINACION");
                Zombi toxicoCa = new Zombi(aux,"TOXICO",partida,idAux,"CAMINANTE");
                Zombi berserkCa = new Zombi(aux,"BERSERKER",partida,idAux,"CAMINANTE");
                
                StringBuilder sb = new StringBuilder();
                sb.append("Información:").append("\n");
                sb.append("Zombis:").append("\n");
                sb.append("Tipos:").append("\n");
                sb.append(normalCa.zombiInformativo());
                sb.append(normalCo.zombiInformativo());
                sb.append(normalAb.zombiInformativo());
                sb.append("Subtipos: ").append("\n");
                sb.append(normalCa.zombiInformativo());
                sb.append(toxicoCa.zombiInformativo());
                sb.append(berserkCa.zombiInformativo());
                
                JTextArea textArea = new JTextArea();
                textArea.setText(sb.toString());
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setCaretPosition(0);
                textArea.setEditable(false);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                
                JOptionPane.showMessageDialog(interfazPrincipal,scrollPane,"Información",JOptionPane.INFORMATION_MESSAGE);
                
                normalCa = null;
                normalCo = null;
                normalAb = null;
                toxicoCa = null;
                berserkCa = null;
            }
        });
        
        botonCancelar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(movimientoActivado){
                    interfazPrincipal.botones[interfazPrincipal.elementoSeleccionado.x][interfazPrincipal.elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    interfazPrincipal.botones[interfazPrincipal.elementoSeleccionado.x][interfazPrincipal.elementoSeleccionado.y].setForeground(Color.BLACK);
                    interfazPrincipal.elementoSeleccionado = null;
                    movimientoActivado = false;
                    activacionBotones(true);
                    gestionPanelIntercambio(false, 0);
                }
                if(atacarActivado){
                    interfazPrincipal.botones[interfazPrincipal.elementoSeleccionado.x][interfazPrincipal.elementoSeleccionado.y].setBackground(Color.LIGHT_GRAY);
                    interfazPrincipal.botones[interfazPrincipal.elementoSeleccionado.x][interfazPrincipal.elementoSeleccionado.y].setForeground(Color.BLACK);
                    Superviviente supervivienteActual = partida.getSupervivienteActual();
                    int xActual = supervivienteActual.getCasillaActual().getX();
                    int yActual = supervivienteActual.getCasillaActual().getY();
                    ArrayList<Casilla> casillasAlcance = partida.getTablero().elegirObjetivoSuperviviente(supervivienteActual.getArmaActiva(), xActual, yActual);
                    for (int i = 0; i < casillasAlcance.size(); i++){
                        interfazPrincipal.botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setBackground(Color.LIGHT_GRAY);
                        interfazPrincipal.botones[casillasAlcance.get(i).getX()][casillasAlcance.get(i).getY()].setForeground(Color.BLACK);
                    }
                    interfazPrincipal.elementoSeleccionado = null;
                    atacarActivado = false;
                    activacionBotones(true);
                    gestionPanelIntercambio(false, 0);
                }
            }
        });
        
        botonSalirInventario.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inventarioOpcionesActivado = false;
                activacionBotones(true);
                gestionPanelIntercambio(true,3);
            }
        });
        
        botonUsar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(partida.getSupervivienteActual().getNumeroProvisiones() <= 0){
                    JOptionPane.showMessageDialog(interfazPrincipal,"No provisiones en el inventario");
                } else if(partida.getSupervivienteActual().getMordeduras() <= 0){
                    JOptionPane.showMessageDialog(interfazPrincipal,"No tienes mordeduras que curar");
                }else{
                    inventarioUsarActivado = true;
                    activacionInventario(true);
                    gestionPanelIntercambio(false, 1);
                } 
                               
            }
        });
        botonMoverObjetos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inventarioMoverActivado = true;
                botonSeleccionado1 = -1;
                botonSeleccionado2 = -1;
                primerObjetoDeInventario = false;
                objetoSeleccionadoDeArma = 0;
                objetoSeleccionadoDeInventario = 0;
                activacionInventario(true);
                activacionArmas(true);
                gestionPanelIntercambio(false, 1);
            }
        });
        botonGuardarSalir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.guardarPartida();
                System.exit(0);
            }
        });
    }
                
    public void activacionBotones(boolean enabled) {
        botonMoverse.setEnabled(enabled);
        botonBuscar.setEnabled(enabled);
        botonAtacar.setEnabled(enabled);
        botonInventario.setEnabled(enabled);
        botonNada.setEnabled(enabled);
        botonArma1.setEnabled(false);
        botonArma2.setEnabled(false);
        botonInv1.setEnabled(false);
        botonInv2.setEnabled(false);
        botonInv3.setEnabled(false);
        botonInv4.setEnabled(false);
        botonInv5.setEnabled(false);
        gestionPanelIntercambio(true,2);
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
        mordeduras.setText("Mordeduras : " + partida.getSupervivienteActual().getMordeduras() + "/2");
        try {
            botonArma1.setText(partida.getSupervivienteActual().getArmas(0).getNombre());
            botonArma1.setToolTipText(partida.getSupervivienteActual().getArmas(0).toString());
        } catch (NullPointerException e) {
            botonArma1.setText("Arma 1: Vacío");
            botonArma1.setToolTipText("");
        }
        try {
            botonArma2.setText(partida.getSupervivienteActual().getArmas(1).getNombre());
            botonArma2.setToolTipText(partida.getSupervivienteActual().getArmas(1).toString());
        } catch (NullPointerException e) {
            botonArma2.setText("Arma 2: Vacío");
            botonArma2.setToolTipText("");
        }
        try {
            botonInv1.setText(partida.getSupervivienteActual().getInventario(0).getNombre());
            botonInv1.setToolTipText(partida.getSupervivienteActual().getInventario(0).toString());
        } catch (NullPointerException e) {
            botonInv1.setText("Inventario 1: Vacío");
            botonInv1.setToolTipText("");
        }
        try {
            botonInv2.setText(partida.getSupervivienteActual().getInventario(1).getNombre());
            botonInv2.setToolTipText(partida.getSupervivienteActual().getInventario(1).toString());
        } catch (NullPointerException e) {
            botonInv2.setText("Inventario 2: Vacío");
            botonInv2.setToolTipText("");
        }
        try {
            botonInv3.setText(partida.getSupervivienteActual().getInventario(2).getNombre());
            botonInv3.setToolTipText(partida.getSupervivienteActual().getInventario(2).toString());
        } catch (NullPointerException e) {
            botonInv3.setText("Inventario 3: Vacío");
            botonInv3.setToolTipText("");
        }
        try {
            botonInv4.setText(partida.getSupervivienteActual().getInventario(3).getNombre());
            botonInv4.setToolTipText(partida.getSupervivienteActual().getInventario(3).toString());
        } catch (NullPointerException e) {
            botonInv4.setText("Inventario 4: Vacío");
            botonInv4.setToolTipText("");
        }
        try {
            botonInv5.setText(partida.getSupervivienteActual().getInventario(4).getNombre());
            botonInv5.setToolTipText(partida.getSupervivienteActual().getInventario(4).toString());
        } catch (NullPointerException e) {
            botonInv5.setText("Inventario 5: Vacío");
            botonInv5.setToolTipText("");
        }
    }
    
    public void gestionPanelIntercambio(boolean enable, int n){
        if(n == 0){
            cardLayoutMenuJugador.show(panelIntercambio,"PanelCancelar");
        }
        if(n == 1){
            cardLayoutMenuJugador.show(panelIntercambio,"PanelOpcionesInventario");
        }
        if(n == 2){
            cardLayoutMenuJugador.show(panelIntercambio,"PanelInfo");
        }
        panelIntercambio.setVisible(enable);
    }
    
    public void accionBotonesArmas(int ranura){
        if(atacarBotonesActivado){
            atacarActivado = true;
            armaElegida = ranura;
            Superviviente supervivienteActual = partida.getSupervivienteActual();
            supervivienteActual.setArmaActiva(ranura);
            supervivienteActual.setSeleccion(Entidad.accion.ATACAR);
            interfazPrincipal.autoSeleccionElementoAtacar();
        }
        if(inventarioMoverActivado){
            logicaInventario(ranura,false); // fals porque es de armas
        }
    }
    
    public void accionBotonesInventario(int ranura){
        ranuraElegida = ranura;
        if(buscandoActivado){
            partida.activarSuperviviente(ranura, 0, 0, equipoBuscado);
            equipoBuscado = null;
            buscandoActivado = false;
            actualizarLabels();
            activacionBotones(true);
            partida.accionTerminada();
        }
        if(inventarioUsarActivado){
            partida.activarSuperviviente(0, ranura, 0, null);
            inventarioUsarActivado = false;
            actualizarLabels();
            activacionBotones(true);
            partida.accionTerminada();
        }
        if(inventarioMoverActivado){ //partida.activarSuperviviente(usarOmover, ranuraObjetoSeleccionado, ranuraObjetivo, null);
            logicaInventario(ranura,true); // true porque es de inventario
        }
    }
    public void logicaInventario(int ranura, boolean enable){
        if(botonSeleccionado1 == -1){
            botonSeleccionado1 = ranura;
            if(enable){
                objetoSeleccionadoDeInventario++;
            }else{
                objetoSeleccionadoDeArma++;
            }            
            primerObjetoDeInventario = enable;
            System.out.println("Primer boton: ranura: " + ranura + ", objetoSeleccionadoDeInventario: " + objetoSeleccionadoDeInventario +
                    ", objetoSeleccionadoDeArma: " + objetoSeleccionadoDeArma + ", primerObjetoDeInventario: " + primerObjetoDeInventario);
        }else{
            botonSeleccionado2 = ranura;
            if(enable){
                objetoSeleccionadoDeInventario++;
            }else{
                objetoSeleccionadoDeArma++;
            }
            System.out.println("Segundo boton: ranura: " + ranura + ", objetoSeleccionadoDeInventario: " + objetoSeleccionadoDeInventario +
                    ", objetoSeleccionadoDeArma: " + objetoSeleccionadoDeArma + ", primerObjetoDeInventario: " + primerObjetoDeInventario);
            if(botonSeleccionado1 != -1 && botonSeleccionado2 != -1){
                if(objetoSeleccionadoDeInventario > 0){ // Hay algun objeto de inventario
                    if(objetoSeleccionadoDeInventario > 1){ // Ambos objetos son de inventario
                        // LLAMO A INTERCAMBIARLOS (inventario a inventario)
                        partida.activarSuperviviente(1, botonSeleccionado1, botonSeleccionado2, null);
                        inventarioMoverActivado = true;
                        botonSeleccionado1 = -1;
                        botonSeleccionado2 = -1;
                        primerObjetoDeInventario = false;
                        objetoSeleccionadoDeArma = 0;
                        objetoSeleccionadoDeInventario = 0;
                        actualizarLabels();
                        activacionBotones(true);
                        partida.accionTerminada();
                    }else if(objetoSeleccionadoDeArma > 0){ // Uno de cada
                        if(primerObjetoDeInventario){ // El primer objeto es de inventario
                            // LLAMO A INTERCAMBIARLOS (inventario a arma)
                            partida.activarSuperviviente(2, botonSeleccionado1, botonSeleccionado2, null);
                            inventarioMoverActivado = true;
                            botonSeleccionado1 = -1;
                            botonSeleccionado2 = -1;
                            primerObjetoDeInventario = false;
                            objetoSeleccionadoDeArma = 0;
                            objetoSeleccionadoDeInventario = 0;
                            actualizarLabels();
                            activacionBotones(true);
                            partida.accionTerminada();
                        }else{ // El primer objeto es de arma
                            // LLAMO A INTERCAMBIARLOS (arma a inventario)
                            partida.activarSuperviviente(3, botonSeleccionado1, botonSeleccionado2, null);
                            inventarioMoverActivado = true;
                            botonSeleccionado1 = -1;
                            botonSeleccionado2 = -1;
                            primerObjetoDeInventario = false;
                            objetoSeleccionadoDeArma = 0;
                            objetoSeleccionadoDeInventario = 0;
                            actualizarLabels();
                            activacionBotones(true);
                            partida.accionTerminada();
                        }
                    }
                }else if(objetoSeleccionadoDeArma > 0){ // Hay algun objeto de arma
                    if(objetoSeleccionadoDeArma > 1){ // Ambos objetos son de arma
                        // LLAMO A INTERCAMBIARLOS (arma a arma)
                        partida.activarSuperviviente(4, botonSeleccionado1, botonSeleccionado2, null);
                        inventarioMoverActivado = true;
                        botonSeleccionado1 = -1;
                        botonSeleccionado2 = -1;
                        primerObjetoDeInventario = false;
                        objetoSeleccionadoDeArma = 0;
                        objetoSeleccionadoDeInventario = 0;
                        actualizarLabels();
                        activacionBotones(true);
                        partida.accionTerminada();
                    }else if(objetoSeleccionadoDeInventario > 0){ // Uno de cada
                        if(primerObjetoDeInventario){ // El primer objeto es de inventario
                            // LLAMO A INTERCAMBIARLOS (inventario a arma) 
                            partida.activarSuperviviente(2, botonSeleccionado1, botonSeleccionado2, null);
                            inventarioMoverActivado = true;
                            botonSeleccionado1 = -1;
                            botonSeleccionado2 = -1;
                            primerObjetoDeInventario = false;
                            objetoSeleccionadoDeArma = 0;
                            objetoSeleccionadoDeInventario = 0;
                            actualizarLabels();
                            activacionBotones(true);
                            partida.accionTerminada();
                        }else{ // El primer objeto es de arma
                            // LLAMO A INTERCAMBIARLOS (arma a inventario)
                            partida.activarSuperviviente(3, botonSeleccionado1, botonSeleccionado2, null);
                            inventarioMoverActivado = true;
                            botonSeleccionado1 = -1;
                            botonSeleccionado2 = -1;
                            primerObjetoDeInventario = false;
                            objetoSeleccionadoDeArma = 0;
                            objetoSeleccionadoDeInventario = 0;
                            actualizarLabels();
                            activacionBotones(true);
                            partida.accionTerminada();
                        }
                    }
                }
            }
        }
    }
}
