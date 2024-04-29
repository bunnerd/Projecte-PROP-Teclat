package edu.upc.prop.cluster21_4.domini;

import java.util.Random;

import edu.upc.prop.cluster21_4.domini.HillClimbing.CercaHillClimbing;

/**
 * Classe que implementa una cerca local amb Hill Climbing
 * @author Sandra Vázquez
 */
public class CercaLocal implements AlgorismeGeneracio
{
    private int iteracions;

    public CercaLocal(int iteracions)
    {
        this.iteracions = iteracions;
    }

    /**
     * Executa l'algorisme Hill-Climbing que retorna una assignació de símbols a posicions del teclat minimitzant la distància entre les els caràcters segons la freqüència amb que surten junts
     * @param alfabet l'alfabet pel qual es crearà el teclat
     * @param freq la freqüència entre els símbols 
     * @param layout el layout que tindrà el teclat
     * @return Un array de chars amb la relació índex -> posició, valor -> tecla
     */
    public char[] executa(Alfabet alfabet, FrequenciesSimbols freq, Layout layout)
    {
        HeuristicaHillClimbing heuristica = new HeuristicaHillClimbing();
        SuccessorsHillClimbing successors = new SuccessorsHillClimbing();
        double millorHeuristic = -1;
        char[] millorAssignacio = new char[layout.nombreDeTecles()];
        // Generador per generar seeds per la solucio inicial
        Random generadorRandom = new Random(42);
        for (int i = 0; i < iteracions; ++i)
        {
            if (Thread.interrupted()) return null;
            // Crea la solucio inicial
            int numRand = generadorRandom.nextInt();
            EstatHillClimbing solucioInicial = new EstatHillClimbing(layout, freq);
            solucioInicial.creaSolucioInicial(alfabet, numRand);
            // Executa l'algorisme
            CercaHillClimbing cerca = new CercaHillClimbing(solucioInicial, heuristica, successors);
            // Troba la qualitat de la solucio obtinguda per aquesta execució de l'algorisme 
            EstatHillClimbing millorSolucio = (EstatHillClimbing)cerca.getEstatFinal();
            double heuristic = millorSolucio.funcioHeuristica();
            // Si la solucio obtinguda es millor (o es la primera) se la guarda
            if (millorHeuristic > heuristic || millorHeuristic < 0)
            {
                millorAssignacio = millorSolucio.getAssignacio();
                millorHeuristic = heuristic;
            }
        }
        return millorAssignacio;
    }
}