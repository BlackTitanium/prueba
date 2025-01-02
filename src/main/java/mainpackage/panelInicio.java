package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class panelInicio extends JPanel{
    private JComboBox nSupervivientes;
    private JButton botonJugar;
    public static int nJugadores = 0;
    
    // TAMAÑO 400 * 600 (Ancho, Alto)
    public panelInicio(){
        setSize(400,600);
        setLayout(null);
        
        JLabel titulo = new JLabel("Juego");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(170,20,60,30);
        add(titulo);
        
        // Panel para agrupar JLabel y JComboBox
        JPanel panelCombo = new JPanel();
        panelCombo.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        panelCombo.setPreferredSize(new Dimension(100, 30));
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
        botonJugar.setFont(new Font("Andale Mono", 1, 14));
        botonJugar.setForeground(Color.BLACK);
        add(botonJugar);
        botonJugar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == botonJugar){
                    String nSup = nSupervivientes.getSelectedItem().toString();
                    if(nSup.equals("")){
                        JOptionPane.showMessageDialog(null,"Debes poner el número de supervivientes.");
                    } else {
                        nJugadores = Integer.parseInt(nSup); // Convertir a entero el número de jugadores
                        InterfazPrincipal.cardLayout.show(InterfazPrincipal.panelDerechoPrincipal, "Panel Control");
                    }
                }
            }
        });
    }
}
