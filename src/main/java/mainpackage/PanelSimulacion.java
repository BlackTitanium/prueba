package mainpackage;

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.Map;

public class PanelSimulacion extends JPanel{
    private JButton botonListo, botonVolver, botonAñadir, botonMoverSimulacion;    
    private JPanel panelZombis, panelNumeroZombis, panelZombisTipo, panelZombisSubTipo, panelInventarioAñadir;
    private JComboBox nZombis, tipoZombis, subTipoZombis;
    private DefaultListModel<String> modeloInventario;
    private JList<String> listaInventario;
    private JScrollPane scrollInventario;
    private Map<String, String> tooltips;
    
    public Zombi zombiTemp;
    public boolean colocarZombi = false;
    public boolean moverSimulacion = false;
    
    public Equipo equipoTemp;
    
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelSimulacion(Partida partida, InterfazPrincipal interfazPrincipal){
        this.partida = partida;
        this.interfazPrincipal = interfazPrincipal;
        
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Simulación");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(150,20,150,30);
        add(titulo);
        
        panelZombis = new JPanel();
        panelZombis.setLayout(null);
        panelZombis.setBounds(50,70,350,160);
        
        panelNumeroZombis = new JPanel();
        panelNumeroZombis.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); 
        panelNumeroZombis.setPreferredSize(new Dimension(350, 30));
        panelNumeroZombis.setBounds(0,0,350,30);
        
        JLabel nZombisLabel = new JLabel("Número de Zombis: ");
        nZombisLabel.setFont(new Font("Arial", 0, 12));
        panelNumeroZombis.add(nZombisLabel);
        
        nZombis = new JComboBox();
        nZombis.setBackground(Color.LIGHT_GRAY);
        nZombis.setFont(new Font("Arial", 0, 12));
        nZombis.setForeground(Color.BLACK);
        panelNumeroZombis.add(nZombis);
        
        nZombis.addItem("");
        nZombis.addItem("1");
        nZombis.addItem("2");
        nZombis.addItem("3");
        nZombis.addItem("4");
        
        panelZombisTipo = new JPanel();
        panelZombisTipo.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelZombisTipo.setPreferredSize(new Dimension(350, 30));
        panelZombisTipo.setBounds(0,40,350,30);
        
        JLabel tipoZombisLabel = new JLabel("Tipo de Zombis: ");
        tipoZombisLabel.setFont(new Font("Arial", 0, 12));
        panelZombisTipo.add(tipoZombisLabel);
        
        tipoZombis = new JComboBox();
        tipoZombis.setBackground(Color.LIGHT_GRAY);
        tipoZombis.setFont(new Font("Arial", 0, 12));
        tipoZombis.setForeground(Color.BLACK);        
        panelZombisTipo.add(tipoZombis);
        
        tipoZombis.addItem("");
        tipoZombis.addItem("CAMINANTE");
        tipoZombis.addItem("CORREDOR");
        tipoZombis.addItem("ABOMINACION");
        
        panelZombisSubTipo = new JPanel();
        panelZombisSubTipo.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelZombisSubTipo.setPreferredSize(new Dimension(350, 30));
        panelZombisSubTipo.setBounds(0,80,350,30);
        
        JLabel subTipoZombisLabel = new JLabel("Subtipo de Zombis: ");
        subTipoZombisLabel.setFont(new Font("Arial", 0, 12));
        panelZombisSubTipo.add(subTipoZombisLabel);
        
        subTipoZombis = new JComboBox();
        subTipoZombis.setBackground(Color.LIGHT_GRAY);
        subTipoZombis.setFont(new Font("Arial", 0, 12));
        subTipoZombis.setForeground(Color.BLACK);        
        panelZombisSubTipo.add(subTipoZombis);
        
        subTipoZombis.addItem("");
        subTipoZombis.addItem("NORMAL");
        subTipoZombis.addItem("TOXICO");
        subTipoZombis.addItem("BERSERKER");
        
        botonListo = new JButton("Listo");
        botonListo.setBounds(95,130,100,30);
        botonListo.setBackground(Color.LIGHT_GRAY);
        botonListo.setFont(new Font("Arial", 1, 14));
        botonListo.setForeground(Color.BLACK);
        
        panelZombis.add(panelNumeroZombis);
        panelZombis.add(panelZombisTipo);
        panelZombis.add(panelZombisSubTipo);
        panelZombis.add(botonListo);
        
        add(panelZombis);
        
        panelInventarioAñadir = new JPanel();
        panelInventarioAñadir.setBounds(0,250,400,200);
        panelInventarioAñadir.setLayout(null);
        
        JLabel labelInventario = new JLabel("Selecciona y añade el equipo que quieras.");
        labelInventario.setFont(new Font("Arial", 0, 12));
        labelInventario.setBounds(50,10,250,12);
        panelInventarioAñadir.add(labelInventario);
        
        // Inventario
        modeloInventario = new DefaultListModel<>();
        listaInventario = new JList<>(modeloInventario);
        listaInventario.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> lista, Object descripcion, int indice, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(lista, descripcion, indice, isSelected, cellHasFocus);
                if (c instanceof JComponent) {
                    JComponent jc = (JComponent) c;
                    jc.setToolTipText(tooltips.get(descripcion));
                }
                return c;
            }
        });

        scrollInventario = new JScrollPane(listaInventario);
        scrollInventario.setBounds(50, 40, 150, 160);
        panelInventarioAñadir.add(scrollInventario);

        // Botón para transferir al inventario
        botonAñadir = new JButton("Añadir");
        botonAñadir.setBounds(220, 105, 100, 30);
        panelInventarioAñadir.add(botonAñadir);
        
        add(panelInventarioAñadir);
        
        botonMoverSimulacion = new JButton("Mover Superviviente");
        botonMoverSimulacion.setBounds(100,480,200,30);
        botonMoverSimulacion.setBackground(Color.LIGHT_GRAY);
        botonMoverSimulacion.setFont(new Font("Arial", 1, 14));
        botonMoverSimulacion.setForeground(Color.BLACK);
        add(botonMoverSimulacion);     
        
        botonVolver = new JButton("Volver");
        botonVolver.setBounds(270,620,100,30);
        botonVolver.setBackground(Color.LIGHT_GRAY);
        botonVolver.setFont(new Font("Arial", 1, 14));
        botonVolver.setForeground(Color.BLACK);
        add(botonVolver);        
        
        // Inicializar los tooltips y el inventario de prueba
        inicializarDescripciones();
        inicializarInventario();
        
        activarActionListeners();
    }
    
    private void inicializarDescripciones() {
        tooltips = new HashMap<>();
        Arma armaEspada = new Arma(1);
        Arma armaEscopeta = new Arma(2);
        Arma armaPistola = new Arma(3);
        Arma armaFrancotirador = new Arma(4);
        tooltips.put("Espada", armaEspada.toString());
        tooltips.put("Escopeta", armaEscopeta.toString());
        tooltips.put("Pistola", armaPistola.toString());
        tooltips.put("Francotirador", armaFrancotirador.toString());
        Provision provisionComida = new Provision(1);
        Provision provisionBebida = new Provision(2);
        Provision provisionMedicina = new Provision(3);
        tooltips.put("Comida", provisionComida.toString());
        tooltips.put("Bebida", provisionBebida.toString());
        tooltips.put("Medicina", provisionMedicina.toString());
    }
    
    private void inicializarInventario() {
        modeloInventario.addElement("Espada");
        modeloInventario.addElement("Escopeta");
        modeloInventario.addElement("Pistola");
        modeloInventario.addElement("Francotirador");
        modeloInventario.addElement("Comida");
        modeloInventario.addElement("Bebida");
        modeloInventario.addElement("Medicinas");
    }
    
    public void activarActionListeners(){
        botonListo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == botonListo){
                    String nZombi = nZombis.getSelectedItem().toString();
                    String tipoZombi = tipoZombis.getSelectedItem().toString();
                    String subTipoZombi = subTipoZombis.getSelectedItem().toString();
                    if(nZombi.equals("") || tipoZombi.equals("") || subTipoZombi.equals("")){
                        JOptionPane.showMessageDialog(null,"Debes rellenar los campos.");
                    } else {
                        interfazPrincipal.nZombis = Integer.parseInt(nZombi); // Convertir a entero el número de jugadores
                        interfazPrincipal.mostrarMensaje("Selecciona donde quieteres poner al zombi.");
                        colocarZombi = true;
                        int idAux = 10000;
                        zombiTemp = new Zombi(null,tipoZombi,subTipoZombi,partida,idAux);
                        nZombis.setSelectedItem("");
                        tipoZombis.setSelectedItem("");
                        subTipoZombis.setSelectedItem("");
                    }
                }
            }
        });
        
        botonAñadir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String seleccion = listaInventario.getSelectedValue();
                if (seleccion != null) {
                    switch(seleccion){
                        case "Espada":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Arma(1);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(true);
                            break;
                        case "Escopeta":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Arma(2);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(true);
                            break;
                        case "Pistola":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Arma(3);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(true);
                            break;
                        case "Francotirador":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Arma(4);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(true);
                            break;
                        case "Comida":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Provision(1);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(false);
                            break;
                        case "Bebida":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Provision(2);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(false);
                            break;
                        case "Medicinas":
                            interfazPrincipal.panelMenuJugador.añadirInventario = true;
                            equipoTemp = new Provision(3);
                            interfazPrincipal.panelMenuJugador.activacionInventario(true);
                            interfazPrincipal.panelMenuJugador.activacionArmas(false);
                            break;
                    }
                    interfazPrincipal.seleccionarSuperivienteSimulacion("¿Quieres añadir el equipo a ");
                    interfazPrincipal.panelMenuJugador.labelsSimulacion();
                    interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
                    listaInventario.clearSelection();
                }else{
                    JOptionPane.showMessageDialog(interfazPrincipal, "Selecciona un objeto para añadir.");
                }
            }
        });
        
        botonVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
            }
        });
        
        botonMoverSimulacion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                interfazPrincipal.seleccionarSuperivienteSimulacion("¿Quieres mover a ");
                interfazPrincipal.mostrarMensaje("Selecciona la casilla a donde quieres moverlo.");
                Casilla casillaSuperviviente = interfazPrincipal.supervivienteSeleccionado.getCasillaActual();
                interfazPrincipal.botones[casillaSuperviviente.getX()][casillaSuperviviente.getY()].setBackground(Color.DARK_GRAY);
                interfazPrincipal.botones[casillaSuperviviente.getX()][casillaSuperviviente.getY()].setForeground(Color.WHITE);
                moverSimulacion = true;
            }
        });
    }
}
