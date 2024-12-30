package mainpackage;

import java.util.Random;
public class Ataque{

    private int[] dados;
    private String resultado;
    private int nDados;
    private int exito;
    private static int nCarasDado = 6;

    public Ataque(Arma a){
        nDados = a.getDados();
        exito = a.getExito();
    }

    public void obtenerResultado(AlmacenDeAtaques almacen, int numExitos){
        StringBuilder sb = new StringBuilder();

        sb.append("Resultado del ataque:\n");
        sb.append("Número de dados lanzados: ").append(nDados).append("\n");
        sb.append("Éxito requerido: ").append(exito).append("\n");
        sb.append("Resultados de los dados: ");

        for(int dado : dados){
            sb.append(dado).append(", ");
        }
        sb.append("\n");

        sb.append("Exitos obtenidos: ").append(numExitos).append("\n");

        resultado = sb.toString();

        almacen.guardarAtaque(resultado);
    }

    public int numExitos(AlmacenDeAtaques almacen){
        if(nDados <= 0){
            return(0);
        }
        dados = new int[nDados];
        Random random = new Random();
        int contExitos = 0;

        for(int i = 0; i < nDados; i++){
            dados[i] = random.nextInt(nCarasDado)+1;
            if(dados[i] >= exito){
                contExitos++;
            }
        }

        obtenerResultado(almacen, contExitos);
        
        return(contExitos);
    }
}