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

public class PanelInicio extends JPanel{
    private JComboBox nSupervivientes;
    private JButton botonJugar, botonListo;
    private JTextArea textAreaNombreSupervivientes;
    private JScrollPane scrollpane;
    
    private String[] nombres;
    
    private Partida partida;
    private InterfazPrincipal interfazPrincipal;
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelInicio(Partida partida, InterfazPrincipal interfazPrincipal){
        this.partida = partida;
        this.interfazPrincipal = interfazPrincipal;
        
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Juego");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(170,20,60,30);
        add(titulo);
        
        // Panel para agrupar JLabel y JComboBox
        JPanel panelCombo = new JPanel();
        panelCombo.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        panelCombo.setPreferredSize(new Dimension(200, 30));
        panelCombo.setBounds(100,70,200,30);
        
        JLabel nSupervivientesLabel = new JLabel("Número de Supervivientes: ");
        nSupervivientesLabel.setFont(new Font("Arial", 0, 12));
        panelCombo.add(nSupervivientesLabel);
        
        nSupervivientes = new JComboBox();
        nSupervivientesLabel.setMinimumSize(new Dimension(10, 10));
        nSupervivientes.setBackground(Color.LIGHT_GRAY);
        nSupervivientes.setFont(new Font("Arial", 0, 12));
        nSupervivientes.setForeground(Color.BLACK);        
        panelCombo.add(nSupervivientes);
        
        add(panelCombo);
        
        nSupervivientes.addItem("");
        nSupervivientes.addItem("1");
        nSupervivientes.addItem("2");
        nSupervivientes.addItem("3");
        nSupervivientes.addItem("4");
        
        botonJugar = new JButton("Jugar");
        botonJugar.setBounds(150,110,100,30);
        botonJugar.setBackground(Color.LIGHT_GRAY);
        botonJugar.setFont(new Font("Arial", 1, 14));
        botonJugar.setForeground(Color.BLACK);
        add(botonJugar);
        
        // Panel para la zona donde los nombres
        JPanel panelNombreSuperviviente = new JPanel();
        panelNombreSuperviviente.setLayout(null);
        panelNombreSuperviviente.setPreferredSize(new Dimension(400, 300));
        panelNombreSuperviviente.setBounds(0,170,400,300);
        panelNombreSuperviviente.setVisible(false);
        
        JLabel pedirNombres = new JLabel("Introduce el nombre de los supervivientes: ");
        pedirNombres.setFont(new Font("Arial", 0, 12));
        pedirNombres.setBounds(75,20,250,12);
        panelNombreSuperviviente.add(pedirNombres);
        
        JLabel pedirNombresInfo1 = new JLabel("(Introduce el primer nombre y pulsa intro, luego el segundo y así...");
        pedirNombresInfo1.setFont(new Font("Arial", 0, 10));
        pedirNombresInfo1.setBounds(75,35,300,20);
        panelNombreSuperviviente.add(pedirNombresInfo1);
        
        JLabel pedirNombresInfo2 = new JLabel("Un nombre por jugador)");
        pedirNombresInfo2.setFont(new Font("Arial", 0, 10));
        pedirNombresInfo2.setBounds(75,47,300,20);
        panelNombreSuperviviente.add(pedirNombresInfo2);
        
        textAreaNombreSupervivientes = new JTextArea();
        textAreaNombreSupervivientes.setFont(new Font("Arial", 0, 12));
        scrollpane = new JScrollPane(textAreaNombreSupervivientes);
        scrollpane.setBounds(75, 80, 250, 100);
        panelNombreSuperviviente.add(scrollpane);
        
        botonListo = new JButton("Listo");
        botonListo.setBounds(150,200,100,30);
        botonListo.setBackground(Color.LIGHT_GRAY);
        botonListo.setFont(new Font("Arial", 1, 14));
        botonListo.setForeground(Color.BLACK);
        panelNombreSuperviviente.add(botonListo);
        
        add(panelNombreSuperviviente);
        
        botonJugar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == botonJugar){
                    String nSup = nSupervivientes.getSelectedItem().toString();
                    if(nSup.equals("")){
                        JOptionPane.showMessageDialog(null,"Debes poner el número de supervivientes.");
                    } else {
                        interfazPrincipal.nJugadores = Integer.parseInt(nSup); // Convertir a entero el número de jugadores
                        panelNombreSuperviviente.setVisible(true);
                    }
                }
            }
        });
        
        botonListo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == botonListo){
                    String texto = textAreaNombreSupervivientes.getText();
                    nombres = texto.split("\\r?\\n");
                    if (nombres.length != interfazPrincipal.nJugadores) {
                        JOptionPane.showMessageDialog(null, "Por favor, introduce exactamente " + interfazPrincipal.nJugadores + " nombres.");
                    } else {
                        partida.colocarElementosIniciales(nombres);
                        panelNombreSuperviviente.setVisible(false);
//                        InterfazPrincipal.cardLayout.show(InterfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
                    }
                }
            }
        });
    }
}
