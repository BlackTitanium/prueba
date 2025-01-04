package mainpackage;

import javax.swing.*;
import java.awt.*;

public class PanelMenuJugador extends JPanel{
    private JButton botonMoverse, botonBuscar, botonAtacar, botonElegirArma, botonNada;
    // TAMAÑO 400 * 745 (Ancho, Alto)
    public PanelMenuJugador(){
        setSize(400,745);
        setLayout(null);
        
        JLabel titulo = new JLabel("Menu Jugador");
        titulo.setFont(new Font("Arial", 1, 20));
        titulo.setBounds(125,20,150,20);
        add(titulo);
        
        JPanel panelCombo1 = new JPanel();
        panelCombo1.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 0)); // FlowLayoaut para una distribución horizontal FlowLayout(alineación, espacioHorizontal, espacioVertical)
        panelCombo1.setBounds(15,60,300,15);
        
        JLabel turnoDe = new JLabel("Turno de: " + Partida.getSupervivienteActual().getNombre());
        turnoDe.setFont(new Font("Arial", 1, 15));
        panelCombo1.add(turnoDe);
        
        JLabel numAcciones = new JLabel("Acciones: " + Partida.getSupervivienteActual().getAcciones());
        numAcciones.setFont(new Font("Arial", 1, 15));
        panelCombo1.add(numAcciones);
        
        add(panelCombo1);
    }
}
