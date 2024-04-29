package edu.upc.prop.cluster21_4.domini.HillClimbing;

//No importa Successor perque es del mateix paquet
import java.util.List;

/**
 * Interficie que defineix una funcio generadora de succesors per Hill-Climbing
 * @author Jon Campillo
 */
public interface FuncioSuccessors 
{
    /**
     * Genera els successors de l'estat donat, aplicant tots els operadors que es pugui
     * @param estat l'estat a partir del qual es generen successors
     * @return Els estats successors
     */
    public List<Successor> getSuccessors(Object estat);
}