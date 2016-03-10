/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
/**
 *
 * @author Daniel
 */
public class Mazo 
{
    private Carta[] myCartas;
    private int numCartas;
    
    public Mazo()
    {
        this(2, false);
    }
    
    public Mazo(int numMazos, boolean shuffle)
    {
        this.numCartas = numMazos * 52;
        this.myCartas = new Carta[this.numCartas];
        
        int c = 0; // indice de carta
        
        for (int m = 0; m < numMazos; m++) // Mazo
        {
            for (int s = 0; s < 4; s++) // Suit
            {
                for (int n = 1; n <= 13; n++) // Numero
                {
                    this.myCartas[c] = new Carta(Suit.values()[s], n); // Nueva carta
                    c++;
                }
            }
        }
        
        if(shuffle)
        {
            this.shuffle();
        }
    }
    
    public void shuffle()
    {
        Random rng = new Random();
        
        Carta temp;
        
        int j;
        
        for (int i = 0; i < this.numCartas; i++)
        {
            j = rng.nextInt(this.numCartas); // crea una carta random j para cambiar con i
            
            temp = this.myCartas[i];
            this.myCartas[i] = this.myCartas[j];
            this.myCartas[j] = temp;
        }
        
    }
    
    public Carta sacarCarta()
    {
        Carta top = this.myCartas[0];
        
        for (int c = 1; c < this.numCartas; c++)
        {
            this.myCartas[c-1] = this.myCartas[c];
        }
        this.myCartas[this.numCartas-1] = null;
        
        this.numCartas--;
        
        return top;
    }
    
    
}
