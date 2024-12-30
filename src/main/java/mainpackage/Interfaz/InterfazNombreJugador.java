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
import java.awt.event.*; // Porque vamos a trabajar con eventos: botones, combox, ...
import java.awt.*; // Porque vamos a trabajar con colores

public class InterfazNombreJugador extends JFrame implements ActionListener{
    private JTextField textfield1;
    private JButton botonOk;
    public static String nombre = "";
    private int num;
    
    public InterfazNombreJugador(){
        setTitle("Nombre del jugador");
        setBounds(0, 0, 300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        
        num = InterfazPrincipal.numJ;
        JLabel label = new JLabel("Ingrese el nombre del Superviviente " + num + " : ");
        label.setBounds(40,20,220,30);
        label.setFont(new Font("Arial", 0, 12));
        label.setForeground(Color.black);
        add(label);
        
        // El JTextField, donde se introduce el nombre
        textfield1 = new JTextField();
        textfield1.setBounds(45,60,200,30);
        textfield1.setBackground(Color.white);
        textfield1.setFont(new Font("Arial", 0, 12));
        textfield1.setForeground(Color.black);
        add(textfield1);
        
        botonOk = new JButton("Ok");
        botonOk.setBounds(100,110,100,30);
        botonOk.setBackground(Color.LIGHT_GRAY);
        botonOk.setFont(new Font("Andale Mono", 1, 14));
        botonOk.setForeground(Color.BLACK);
        add(botonOk);
        botonOk.addActionListener(this);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == botonOk){
            nombre = textfield1.getText().trim();
            if(nombre.equals("")){
                JOptionPane.showMessageDialog(null,"Debes ingresar un nombre."); // Muestra un mensaje
            } else {
                setVisible(false);
            }
        }
    }
    
    public static void main(String args[]){
        SwingUtilities.invokeLater(InterfazNombreJugador::new);
    }
}
