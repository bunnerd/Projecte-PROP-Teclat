package edu.upc.prop.cluster21_4.domini.HillClimbing;

/**
 * Interficie que defineix una funcio heuristica per un estat. Un valor mes petit vol dir millor valor heuristic
 * @author Jon Campillo
 */
public interface FuncioHeuristica 
{
    /**
     * Calcula heuristic per l'estat donat
     * @param estat l'estat pel qual es calcula el valor de la funció
     * @return El valor de la funcio heuristica per aquest estat
     */
    public double getHeuristic(Object estat);
}