package edu.upc.prop.cluster21_4.domini.HillClimbing;

/**
 * Classe que representa un successor a Hill-Climbing. Es guarda l'estat i l'operador aplicat.
 * @author Jon Campillo
 */
public class Successor 
{
    private Object estat;
    private String accio;
    
    /**
     * Constructora
     * @param estat l'estat successor
     * @param accio una descripcio de l'operador aplicat
     */
    public Successor(Object estat, String accio)
    {
        this.estat = estat;
        this.accio = accio;
    }

    public String getAccio(){
        return accio;
    }

    public Object getEstat(){
        return estat;
    }
}