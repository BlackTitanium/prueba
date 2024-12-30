/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainpackage.Interfaz;

/**
 *
 * @author monthy765
 */

import javax.swing.*; // Porque estamos haciendo una interfaz gráfica
import javax.swing.event.*; // Cuando usamos CheckBox
import java.awt.*; // Porque vamos a trabajar con colores
import java.awt.event.*; // Porque vamos a trabajar con eventos: botones, combox, ...
import mainpackage.*; // Para usar Casilla

public class InterfazInicio extends JFrame implements ActionListener{
    private JComboBox nSupervivientes;
    private JButton empezar;
    public static int nJugadores = 0;
    
    public InterfazInicio(){
        setTitle("Inicio");
        setBounds(0, 0, 300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        
        JLabel titulo = new JLabel("Juego");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(120,20,60,30);
        add(titulo);
        
        // Panel para agrupar JLabel y JComboBox
        JPanel panelCombo = new JPanel();
        panelCombo.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        panelCombo.setPreferredSize(new Dimension(100, 30));
        panelCombo.setBounds(50,70,200,30);
        
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
        
        empezar = new JButton("Jugar");
        empezar.setBounds(100,110,100,30);
        empezar.setBackground(Color.LIGHT_GRAY);
        empezar.setFont(new Font("Andale Mono", 1, 14));
        empezar.setForeground(Color.BLACK);
        add(empezar);
        empezar.addActionListener(this);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == empezar){
            String nSup = nSupervivientes.getSelectedItem().toString();
            if(nSup.equals("")){
                JOptionPane.showMessageDialog(null,"Debes poner el número de supervivientes.");
            } else {
                nJugadores = Integer.parseInt(nSup); // Convertir a entero el número de jugadores
                new InterfazPrincipal().setVisible(true);
                setVisible(false);
            }
        }
    }
    
    public static void main(String args[]){
        SwingUtilities.invokeLater(InterfazInicio::new);
    }
}
