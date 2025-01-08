package mainpackage;

import javax.swing.JOptionPane;

public class InterfazDeSINo {
    public static int mostrarConfirmacion(String mensaje) {
        // Mostrar cuadro de diálogo con opciones Sí y No
        String[] opciones = {"Sí", "No"};
        return JOptionPane.showOptionDialog(
                null, 
                mensaje, 
                "Confirmación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[0]
        );
    }
}
