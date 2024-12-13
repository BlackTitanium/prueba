package mainpackage;

public class Arma extends Equipo{
    String nombre;
    int potencia, dados, alcance, exito;

    public Arma(int a){
        switch case(a){

            case 1:
                nombre = "Espada";
                potencia = 3;
                dados = 3;
                alcance = 0;
                exito = 3;
                break;
            case 2:
                nombre = "Pistola";
                potencia = 1;
                dados = 2;
                alcance = 2;
                exito= 2;
                break;
            case 3:
                nombre = "Escopeta";
                potencia = 5;
                dados = 1;
                alcance = 1;
                exito = 4;
                break;


        }
    }
}