package mainpackage;

import java.util.Random;
public class Ataque{

    private int[] dados;
    private String resultado;
    private boolean distancia;
    private int potencia;

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
            dados[i] = random.nextInt(6)+1;
            if(dados[i] >= exito){
                contExitos++;
            }
        }

        return(contExitos);
    }

    public String obtenerResultado(int nDados, int exito){
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

            return(resultado);
    }

}