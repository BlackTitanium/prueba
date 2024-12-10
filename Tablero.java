package mainpackage;

public class Tablero {
    private Casilla mapa[][];

    public void setMapa(Casilla c){
        mapa[c.getX()][c.getY()] = c;
    }

    public Tablero(){
        for (int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                mapa[i][j] = new Casilla(i, j);
            }
        }
    }
    
    public void mostrar(){

    }

    public Casilla getCasilla(int a, int b){
        return mapa[a][b];
    }

    public Casilla objetivoZombi(Casilla c){
        Casilla objetivo = null;
        for (int j=0; j<10; j++){
                    for(int k=0; k<10; k++){
                        if(mapa[j][k].getContadorSupervivientes()!=0){
                            if(Math.sqrt(Math.pow((c.getX()-mapa[j][k].getX()),2)-Math.pow((c.getY()-mapa[j][k].getY()),2))<
                                Math.sqrt(Math.pow((c.getX()-objetivo.getX()),2)-Math.pow((c.getY()-objetivo.getY()),2))){
                                    objetivo = mapa[j][k];
                                }
                        }
                    }
                }
        return objetivo;
    }
}