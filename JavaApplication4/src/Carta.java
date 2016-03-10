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
    private int myNumber; // El numero de la carta: As = 1, J-K = 11-13
     
    public Carta(Suit aSuit, int aNumber)
    {
        this.mySuit = aSuit;
        
        if(aNumber >= 1 && aNumber <= 13)
        {
            this.myNumber = aNumber;
        }else
        {
            System.err.println(aNumber + " no es un numero valido");
            System.exit(1);
        }
    }
    
    public int getNumber()
    {
        return myNumber;
    }
        
}
