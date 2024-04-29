package edu.upc.prop.cluster21_4.domini;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.util.Arrays;

/**
 * Classe per fer cerca amb BranchBound
 * @author Sergi Gonzalez Martos
 */

public class QAP implements AlgorismeGeneracio {

    //Index -> tecla, valor -> columna
    private int[] estat; 
    
    //(Asignació òptima) Index -> tecla, valor -> columna 
    private int[] sol_aux;
    
    //Index -> columna, valor -> true(ocupada) / false(no ocupada)
    private boolean[] posOcupada;
    
    //cota màxima trobada
    private double cota_max;

    private boolean emergence = false;

    /**
     * Funció publica que executa BranchBound i tot el necessari per laseva execució
     * @param alfabet El paràmatre és l'alfabet que es vol utilitzar per crear el teclat
     * @param fs El paràmatre és la freqüència de símbols que es vol utilitzar per crear el teclat
     * @param layout El paràmatre és el layout que es vol utilitzar per crear el teclat
     * @return Retorna un array de chars amb la relació índex posició, valor tecla
     */
    public char[] executa(Alfabet alfabet, FrequenciesSimbols fs, Layout layout) {

        long tiempoIni = System.currentTimeMillis();

        //Obtenim la classe HungarianAlgorithm
        HungarianAlgorithm HA = new HungarianAlgorithm();

        //Obtenim la classe GilmoreLawler
        GilmoreLawler GL = new GilmoreLawler();

        //size alfabet
        int size = alfabet.getSize();
        
        //Inicialitzem les matrius a utilitzar
        estat = new int[size];
        sol_aux = new int[size];
        posOcupada = new boolean[size];
        
        for (int i = 0; i < size; ++i) {
            estat[i] = -1;
            sol_aux[i] = -1;
        }
        
        //Alfabet auxiliar
        Character[] alf = new Character[size];
        alf = alfabet.getAlfabet();

        //Obtenim cota_max a partir de Cerca Local
        CercaLocal HC = new CercaLocal(20);
        char[] aux_hc = new char[size];
        aux_hc = HC.executa(alfabet, fs, layout);

        boolean[] vist = new boolean[size];
        cota_max = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (!vist[j]) cota_max += layout.distanciaEntre(i, j) * fs.getFrequencia(aux_hc[i], aux_hc[j]);
            }
            vist[i] = true;
        }
        cota_max += 0.01;

        int[][] freq = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                freq[i][j] = fs.getFrequencia(alf[i], alf[j]);
            }
        }

        emergence = false;

        BranchBound(HA, GL, alf, layout, freq, 0, size);
        
        if (!emergence) {
            //sol_final[i] -> i = posició dintre del teclat | valor -> símbol corresponent 
            char[] sol_final = new char[size];
            for (int i = 0; i < size; ++i) {
                sol_final[sol_aux[i]] = alf[i];
            }
            return sol_final;
        }
        
        return null;
    }

    //Funció per calcular el disseny del teclat mitjançant BranchBound
    private void BranchBound(HungarianAlgorithm HA, GilmoreLawler GL, Character[] alfabet, Layout layout, 
                int[][] freq_aux, int tecla, int size) {

        if (emergence) return;

        if (Thread.interrupted()) {
            emergence = true;
            return;
        }

        //Cas base
        //Quan trobem una solució completa calculem el seu cost ->
        // -> si es millor que la cota_max guardem el nou cost i l'asginació
        if (tecla == size) {
            double cota = GL.Bound(HA, estat, posOcupada, alfabet, layout, freq_aux, size);

            if (cota < cota_max) {
            cota_max = cota;
            for (int i = 0; i < size; ++i) sol_aux[i] = estat[i];
            }
        }

        //Cas recursiu
        //
        else {
            double cota = GL.Bound(HA, estat, posOcupada, alfabet, layout, freq_aux, size);
            if ((cota < cota_max)) {
                for (int pos = 0; pos < size; ++pos) {
                    //Si la posició actual no està coupada i la bound es menor o igual a la cota_max continuem
                    if (!posOcupada[pos]) {

                        //Asignem a la tecla "tecla" la posició "pos"
                        estat[tecla] = pos;

                        //Marquem la posició "pos" como asignada
                        posOcupada[pos] = true;

                        //Crida recursiva
                        BranchBound(HA, GL, alfabet, layout, freq_aux, tecla+1, size);

                        if (emergence) return;

                        //La posició "pos" deixa de estar asignada
                        posOcupada[pos] = false;

                        //La tecla "tecla" deixa de estar asignada
                        estat[tecla] = -1;
                    }
                }
            }
        }
    }
}