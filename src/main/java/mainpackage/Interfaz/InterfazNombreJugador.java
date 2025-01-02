package mainpackage.Interfaz;

import java.awt.Color; // Porque estamos haciendo una interfaz gráfica
import java.awt.Font; // Porque vamos a trabajar con eventos: botones, combox, ...
import java.awt.event.ActionEvent; // Porque vamos a trabajar con colores
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.*;

public class InterfazNombreJugador /*extends JDialog implements ActionListener*/{
    private JDialog dialogo;
    private JTextField textfield1;
    private JButton botonOk;
    public static String nombre = "";
    private int num;
    
    public InterfazNombreJugador(JFrame parent){
//        super(parent, "Nombre del jugador", true);
        dialogo = new JDialog(parent, "Nombre del jugador", true);
        dialogo.setBounds(0, 0, 300, 200);
        dialogo.setLocationRelativeTo(null);
        dialogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogo.setResizable(false);
        dialogo.setLayout(null);
        
        num = InterfazPrincipal.numJ;
        JLabel label = new JLabel("Ingrese el nombre del Superviviente " + num + " : ");
        label.setBounds(40,20,220,30);
        label.setFont(new Font("Arial", 0, 12));
        label.setForeground(Color.black);
        dialogo.add(label);
        
        // El JTextField, donde se introduce el nombre
        textfield1 = new JTextField();
        textfield1.setBounds(45,60,200,30);
        textfield1.setBackground(Color.white);
        textfield1.setFont(new Font("Arial", 0, 12));
        textfield1.setForeground(Color.black);
        dialogo.add(textfield1);
        
        botonOk = new JButton("Ok");
        botonOk.setBounds(100,110,100,30);
        botonOk.setBackground(Color.LIGHT_GRAY);
        botonOk.setFont(new Font("Andale Mono", 1, 14));
        botonOk.setForeground(Color.BLACK);
        botonOk.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == botonOk){
                    nombre = textfield1.getText().trim();
                    if(nombre.equals("")){
                        JOptionPane.showMessageDialog(null,"Debes ingresar un nombre."); // Muestra un mensaje
                    } else {
                        //setVisible(false); // Cierra la ventana, se deja de ver
                        dialogo.dispose(); // Cierra la ventana completamentes
                    }
                }
            }
        });
        dialogo.add(botonOk);
        
//        dialogo.setVisible(true);
    }
    
//    @Override
//    public void actionPerformed(ActionEvent e){
//        if(e.getSource() == botonOk){
//            nombre = textfield1.getText().trim();
//            if(nombre.equals("")){
//                JOptionPane.showMessageDialog(this,"Debes ingresar un nombre."); // Muestra un mensaje
//            } else {
//                //setVisible(false); // Cierra la ventana, se deja de ver
//                dispose(); // Cierra la ventana completamentes
//            }
//        }
//    }
    public void mostrar() {
        dialogo.setVisible(true);
    }
}
