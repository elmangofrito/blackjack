/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class Jugador 
{
    private int max = 10,puntaje=0;
    
    private String nombre;
    private int id;
    
    private Carta[] mano = new Carta[max];
    
    private int numCartas;
    
    public Jugador(String aNombre,int id)
    {
        this.nombre = aNombre;
        this.id=id;
        this.vaciarMano();
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void vaciarMano()
    {
        for (int c = 0; c < max; c++)
        {
            this.mano[c] = null;
        }
        this.numCartas = 0;
    }
    
    public boolean addCarta(Carta aCarta)
    {
        this.mano[this.numCartas] = aCarta;
        this.numCartas++;
        
        return(this.getSuma() <= 21);
    }
    
    public int getSuma()
    {
        int sumaMano = 0;
        int cartaNum;
        int numAses = 0;
        
        for (int c = 0; c < this.numCartas; c++)
        {
            cartaNum = this.mano[c].getNumber();
            
            if (cartaNum == 1)
            {
                numAses++;
                sumaMano += 11;
            }else if (cartaNum > 10)
            {
                sumaMano += 10;
            }else
            {
                sumaMano += cartaNum;
            }
                    
        }
        
        while (sumaMano > 21 && numAses > 0)
        {
            sumaMano -= 10;
            numAses--;
        }
        
        return sumaMano;
        
    }
}
