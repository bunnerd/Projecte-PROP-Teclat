package edu.upc.prop.cluster21_4.domini.HillClimbing;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe per fer cerca amb Hill-Climbing
 * @author Jon Campillo
 */
public class CercaHillClimbing 
{
    private Object estatFinal;
    private List<String> accions;

     /**
      * Realitza una cerca amb Hill-Climbing partint de l'estat donat, amb les funcions heuristica i generadora de successors especificades
      * @param estat estat inicial
      * @param funcioHeuristica la funció heurística que utilitzarà l'algorisme per guiar la cerca
      * @param funcioSuccessors funció que generarà els successors d'un estat
      */
    public CercaHillClimbing(Object estat, FuncioHeuristica funcioHeuristica, FuncioSuccessors funcioSuccessors)
    {
        accions = new ArrayList<String>();
        boolean teSuccessors = true;
        estatFinal = estat;

        while(teSuccessors)
        {
            Object millor = millorSuccessor(estatFinal, funcioSuccessors, funcioHeuristica);
            if(millor != null)
            {
                estatFinal = millor;
            }
            else
            {
                teSuccessors = false;
            }
        }
    }

    private Object millorSuccessor(Object estat, FuncioSuccessors funcioSuccessors, FuncioHeuristica funcioHeuristica)
    {
        List<Successor> succesors = funcioSuccessors.getSuccessors(estat);
        Object millor = null;
        double heuristic = funcioHeuristica.getHeuristic(estat);
        String accio = null;

        for(Successor suc : succesors)
        {
            double estHeuristic = funcioHeuristica.getHeuristic(suc.getEstat());

            if(estHeuristic < heuristic)
            {
                millor = suc.getEstat();
                heuristic = estHeuristic;
                accio = suc.getAccio();
            }
        }

        if(accio != null)
            accions.add(accio);

        return millor;
    }

    /**
     * Obte el resultat de l'algorisme
     * @return L'estat final de la cerca
     */
    public Object getEstatFinal(){
        return estatFinal;
    }

    /**
     * Obte els operadors aplicats en ordre
     * @return Descripcions dels operadors aplicats, en ordre
     */
    public List<String> getAccions(){
        return accions;
    }
}