package edu.upc.prop.cluster21_4.domini;

import java.util.Random;

/**
 * Classe que representa un estat de l'algorisme de cerca local Hill Climbing
 * @author Sandra Vázquez
 */
public class EstatHillClimbing {
    private static FrequenciesSimbols freq;
    private static Layout layout;
    private char assignacio[];

    /**
     * Constructura
     * @param l layout, constant per tota la cerca
     * @param f frequencies entre els simbols de l'alfabet, constants per tota la cerca
     */
    public EstatHillClimbing(Layout l, FrequenciesSimbols f)
    {
        layout = l;
        freq = f;
        assignacio = new char[l.nombreDeTecles()];
    }

    /**
     * Copiadora
     * @param estat estat que es vol copiar
     * @return Una deep copy de l'estat donat
     */
    public static EstatHillClimbing copia(EstatHillClimbing estat)
    {
        EstatHillClimbing aux = new EstatHillClimbing(layout, freq);
        for (int i = 0; i < estat.getNumCaracters(); ++i)
        {
            aux.assignacio[i] = estat.assignacio[i];
        }
        return aux;
    }

    /**
     * Operador que intercanvia caracters de l'assignacio. 0 <= i < assignacio.length i 0 <= j < assignacio.length
     * @param i posicio 1
     * @param j posicio 2
     */
    public void intercanvia2Caracters(int i, int j)
    {
        char aux = assignacio[i];
        assignacio[i] = assignacio[j];
        assignacio[j] = aux;
    }

    /**
     * Calcula la funcio heuristica per aquest estat
     * @return El valor de la funcio heuristica per l'estat
     */
    public double funcioHeuristica()
    {
        double dist = 0;
        for (int i = 0; i < assignacio.length; ++i)
        {
            for (int j = 0; j < assignacio.length; ++j)
            {
                dist += layout.distanciaEntre(i, j) * freq.getFrequencia(assignacio[i], assignacio[j]);
            }
        }
        return dist;
    }

    /**
     * Obte el nombre de caracters de l'assignacio
     * @return El nombre de caracters de l'assignacio
     */
    public int getNumCaracters()
    {
        return assignacio.length;
    }

    /**
     * Crea una assignacio inicial aleatoria
     * @param alfabet l'alfabet a utilitzar
     * @param seed una seed pel generador de nombres aleatoris
     */
    public void creaSolucioInicial(Alfabet alfabet, int seed)
    {
        Alfabet aux = alfabet.copiar();
        Random random = new Random(seed);
        // Va seleccionant símbols aleatoris de l'alfabet i els coloca a la posició ièssima
        for (int i = 0; i < alfabet.getSize(); ++i)
        {
            /* Alfabet te algunes excepcions que salten si demanem simbols que no existeixen o esborrem
             * simbols que no existeixen. Com que en aquest metode es garantitza que aixo no passa, es fa
             * servir aquest try-catch per treure preocupacions innecessaries */
            try {
                char c = aux.getSimbol(random.nextInt(alfabet.getSize()-i));
                assignacio[i] = c;
                aux.esborraSimbol(c);
            } catch (Exception e) {}
        }
    }

    /**
     * Obté l'assignació
     * @return l'assignació
     */
    public char[] getAssignacio()
    {
        return assignacio;
    }
}
