/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class Carta 
{
    
    private Suit mySuit; // El palo de la carta
    private String myNumber; // El numero de la carta: As = 1, J-K = 11-13
    private String[] Mynumber={"a","2","3","4","5","6","7","8","9","10","j","q","k"};

    public String getMynumber(int n) {
        return this.Mynumber[n];
    }

    public Carta() {
        
    }
    public void carta(){}
    public Carta(Suit aSuit, String Rnumber)
    {
        this.mySuit = aSuit;
        boolean band=true;
        int i=0;
        while(band && i<13){
            if(Rnumber==Mynumber[i]){
                band=false;
            }
            i++;
        }
        if(band){
            System.err.println(Rnumber+"no es una carta valida");
        }
        else{
            
            this.myNumber=Rnumber;
        }
    }

    public Suit getMySuit() {
        return mySuit;
    }
    
    public int getNumber()
    {
        
          
        int i=0;
        do {
            if(myNumber==Mynumber[i]) {
            return i+1;
            }
            i++;
        }while (i<13);
        return 0;
    }
    public Object getSNumber(){
        return this.myNumber;
    }
        
}
