package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelHistoriales extends JPanel{
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton botonHistorialAtaques, botonHistorialSuperviviente, botonHistorialZombi, botonVolver;
    
    public boolean seleccionarZombi = false;
    
    private AlmacenDeAtaques almacen;
    private InterfazPrincipal interfazPrincipal;
    private Partida partida;
    
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelHistoriales(AlmacenDeAtaques almacen, InterfazPrincipal interfazPrincipal, Partida partida){
        this.almacen = almacen;
        this.interfazPrincipal = interfazPrincipal;
        
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Historiales");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(150,20,100,20);
        add(titulo);
        
        botonHistorialAtaques = new JButton("Historial Ataques");
        botonHistorialAtaques.setBounds(32,60,155,30);
        botonHistorialAtaques.setBackground(Color.LIGHT_GRAY);
        botonHistorialAtaques.setFont(new Font("Arial", 1, 14));
        botonHistorialAtaques.setForeground(Color.BLACK);
        add(botonHistorialAtaques);
        
        botonHistorialZombi = new JButton("Historial Zombi");
        botonHistorialZombi.setBounds(212,60,155,30);
        botonHistorialZombi.setBackground(Color.LIGHT_GRAY);
        botonHistorialZombi.setFont(new Font("Arial", 1, 14));
        botonHistorialZombi.setForeground(Color.BLACK);
        add(botonHistorialZombi);
        
        botonHistorialSuperviviente = new JButton("Historial Superviviente");
        botonHistorialSuperviviente.setBounds(100,110,200,30);
        botonHistorialSuperviviente.setBackground(Color.LIGHT_GRAY);
        botonHistorialSuperviviente.setFont(new Font("Arial", 1, 14));
        botonHistorialSuperviviente.setForeground(Color.BLACK);
        add(botonHistorialSuperviviente);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", 0, 12));
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 165, 300, 410);
        add(scrollPane);
        
        botonVolver = new JButton("Volver");
        botonVolver.setBounds(150,645,100,30);
        botonVolver.setBackground(Color.LIGHT_GRAY);
        botonVolver.setFont(new Font("Arial", 1, 14));
        botonVolver.setForeground(Color.BLACK);
        botonVolver.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazPrincipal.zombiSeleccionado = null;
                textArea.setText("");
                interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
            }
        });
        add(botonVolver);
        
        botonHistorialAtaques.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(almacen.mostrarHistorialAtaquesInterfaz());
            }
        });
        
        botonHistorialZombi.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(almacen.mostrarHistorialAtaquesInterfaz());
            }
        });
        
        botonHistorialSuperviviente.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(partida.getSupervivienteActual().mostrarHistorialZombisAsesinados());
            }
        });
    }
    
    public void elegirZombi(){
        Zombi elegido = interfazPrincipal.zombiSeleccionado;
        textArea.setText(elegido.mostrarHistorialSupervivientesAtacados());
    }
}
