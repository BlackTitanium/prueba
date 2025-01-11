package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelSimulacion extends JPanel{
    private JButton botonListo, botonVolver;    
    private JPanel panelZombis, panelNumeroZombis, panelZombisTipo, panelZombisSubTipo;
    private JComboBox nZombis, tipoZombis, subTipoZombis;
    
    public Zombi zombiTemp;
    public boolean colocarZombi = false;
    
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelSimulacion(Partida partida, InterfazPrincipal interfazPrincipal){
        this.partida = partida;
        this.interfazPrincipal = interfazPrincipal;
        
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Simular");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(150,20,100,30);
        add(titulo);
        
        panelZombis = new JPanel();
        panelZombis.setLayout(null);
        panelZombis.setBounds(50,70,350,300);
        
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
        subTipoZombis.addItem("TÓXICO");
        subTipoZombis.addItem("BERSERKER");
        
        botonListo = new JButton("Listo");
        botonListo.setBounds(95,120,100,30);
        botonListo.setBackground(Color.LIGHT_GRAY);
        botonListo.setFont(new Font("Arial", 1, 14));
        botonListo.setForeground(Color.BLACK);
        
        panelZombis.add(panelNumeroZombis);
        panelZombis.add(panelZombisTipo);
        panelZombis.add(panelZombisSubTipo);
        panelZombis.add(botonListo);
        
        add(panelZombis);
        
        activarActionListeners();
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
                        //panelConfigZombis.setVisible(true);
                        interfazPrincipal.mostrarMensaje("Selecciona donde quieteres poner al zombi.");
                        colocarZombi = true;
                        int idAux = 10000;
                        zombiTemp = new Zombi(null,tipoZombi,subTipoZombi,partida,idAux);
                    }                    
                }
            }
        });
        botonVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
            }
        });
    }
}
