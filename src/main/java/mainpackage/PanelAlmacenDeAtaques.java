package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAlmacenDeAtaques extends JPanel{
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton botonVolver;
    private AlmacenDeAtaques almacen;
    private InterfazPrincipal interfazPrincipal;
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelAlmacenDeAtaques(AlmacenDeAtaques almacen, InterfazPrincipal interfazPrincipal){
        this.almacen = almacen;
        this.interfazPrincipal = interfazPrincipal;
        
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Historial de Ataques");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(105,20,190,20);
        add(titulo);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", 0, 12));
        textArea.setText(almacen.mostrarHistorialAtaquesInterfaz());
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 65, 300, 560);
        add(scrollPane);
        
        botonVolver = new JButton("Volver");
        botonVolver.setBounds(150,645,100,30);
        botonVolver.setBackground(Color.LIGHT_GRAY);
        botonVolver.setFont(new Font("Arial", 1, 14));
        botonVolver.setForeground(Color.BLACK);
        botonVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
            }
        });
        add(botonVolver);
    }
}
