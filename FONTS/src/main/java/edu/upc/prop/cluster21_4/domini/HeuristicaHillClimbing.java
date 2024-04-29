package edu.upc.prop.cluster21_4.domini;

import edu.upc.prop.cluster21_4.domini.HillClimbing.FuncioHeuristica;

/**
 * Classe que implementa la interficie funcio heuristica. Un valor mes petit vol dir millor valor heuristic
 * @author Sandra Vázquez
 */
public class HeuristicaHillClimbing implements FuncioHeuristica {
    /**
     * Calcula heuristic per l'estat donat
     * @param estat l'estat pel qual es calcula el valor de la funció
     * @return El valor de la funcio heuristica per aquest estat
     */
    public double getHeuristic(Object estat)
    {
        return ((EstatHillClimbing)estat).funcioHeuristica();
    }
}