package edu.upc.prop.cluster21_4.domini;
import java.util.LinkedList;
import java.util.List;

import edu.upc.prop.cluster21_4.domini.HillClimbing.FuncioSuccessors;
import edu.upc.prop.cluster21_4.domini.HillClimbing.Successor;

/**
 * Classe que implementa la interficie funcio successors
 * @author Sandra Vázquez
 */
public class SuccessorsHillClimbing implements FuncioSuccessors
{
    /**
     * Genera els successors de l'estat donat, aplicant tots els operadors que es pugui
     * @param estat l'estat a partir del qual es generen successors
     * @return Els estats successors
     */
    public List<Successor> getSuccessors(Object estat)
    {
        EstatHillClimbing estatAct = (EstatHillClimbing)estat;
        LinkedList<Successor> llista = new LinkedList<>();

        // Operador d'intercanviar 2 caracters
        for (int i = 0; i < estatAct.getNumCaracters(); ++i)
        {
            for (int j = i+1; j < estatAct.getNumCaracters(); ++j)
            {
                EstatHillClimbing estatAux = EstatHillClimbing.copia(estatAct);
                estatAux.intercanvia2Caracters(i, j);
                llista.add(new Successor(estatAux, "intercanvi" + i + j));
            }
        }
        return llista;
    }    
}