package mainpackage;

import java.util.Random;
public class Ataque{

    private int[] dados;
    private String resultado;
    private boolean distancia;
    private int potencia;
    private static int nCarasDado = 6;

    public Ataque(boolean dist, int poten){
        dados = new int[0];
        distancia = dist;
        potencia = poten;
    }

    public int numExitos(int nDados, int exito){
        dados = new int[nDados];
        Random random = new Random();
        int contExitos = 0;

        for(int i = 0; i < nDados; i++){
            dados[i] = random.nextInt(nCarasDado)+1;
            if(dados[i] >= exito){
                contExitos++;
            }
        }

        return(contExitos);
    }

    public String obtenerResultado(int nDados, int exito, AlmacenDeAtaques almacen){
            int nExitos = numExitos(nDados, exito);

            StringBuilder sb = new StringBuilder();

            sb.append("Resultado del ataque:\n");
            sb.append("Número de dados lanzados: ").append(nDados).append("\n");
            sb.append("Éxito requerido: ").append(exito).append("\n");
            sb.append("Resultados de los dados: ");

            for(int dado : dados){
                sb.append(dado).append(" ");
            }
            sb.append("\n");

            sb.append("Exitos obtenidos: ").append(nExitos).append("\n");

            resultado = sb.toString();

            almacen.guardarAtaque(resultado);

            return(resultado);
    }
}