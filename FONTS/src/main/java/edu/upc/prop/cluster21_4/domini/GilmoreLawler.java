package edu.upc.prop.cluster21_4.domini;

import java.util.Arrays;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

/**
 * Classe per calcular cota inferior amb Gilmore-Lawler
 * @author Sergi Gonzalez Martos
 */

public class GilmoreLawler {
    /**
     * Funció publica que calcula la cota inferior amb el mètode de Gilmore-Lawler
     * @param HA El paràmatre és una instància del Hungarian Algorithm
     * @param estat El paràmatre és un array que representa l'estat actual de la solució parcial
     * @param posOcupada El paràmatre és un array que conté les posicions ocupades
     * @param alfabet El paràmatre és l'alfabet que es vol utilitzar per crear el teclat
     * @param layout El paràmatre és el layout que es vol utilitzar per crear el teclat
     * @param freq_aux El paràmatre és la freqüència de símbols que es vol utilitzar per crear el teclat
     * @param size El paràmatre és la mida de l'alfabet
     * @return Retorna el valor de la cota inferior del sub-arbre
     */
    public double Bound(HungarianAlgorithm HA, int[] estat, boolean[] posOcupada, 
                        Character[] alfabet, Layout layout, int[][] freq_aux, int size) {
        
        //Cont -> contador del nombre de teclas assignades
        int cont = 0;
        for (int i = 0; i < size; ++i) {
            if (estat[i] == -1) i = size;
            else ++cont;
        }
        
        //new_size -> nombre de teclas que queden per asignar
        int new_size = size - cont;
        
        //Array que conté les ubicacion lliures
        int[] posDisp = new int[new_size];
        int j = 0;
        if (new_size > 0) {
            for (int i = 0; i < size; ++i) {
                //Si la posició i no està asignada la guardem a posDisp[]
                if (posOcupada[i] == false) {
                    posDisp[j] = i;
                    ++j;
                }
            }
        }

        //Primer terme
        double t1 = 0;
        boolean[] vist = new boolean[cont];
        for (int tecla = 0; tecla < cont; ++tecla) {
            vist[tecla] = true;
            for (int tecla2 = 0; tecla2 < cont; ++tecla2) {
                if (!vist[tecla2]) t1 += layout.distanciaEntre(estat[tecla], estat[tecla2]) * freq_aux[tecla][tecla2];
            }
        }

        //Segon y tercer element
        //El següent cost s'aconsegueix calculant l'asignació òptima de C (C = C1 + C2)
        
        double t2 = 0;
        if (new_size > 0) {
            
            double[][] C = new double[new_size][new_size];
            
            //tecla x
            for (int tecla = 0; tecla < new_size; ++tecla) {
                //pos y
                for (int pos = 0; pos < new_size; ++pos) {
                    //Tecles assignades
                    for (int tecla2 = 0; tecla2 < cont; ++tecla2) {
                        C[tecla][pos] += layout.distanciaEntre(posDisp[pos], estat[tecla2]) * freq_aux[tecla+cont][tecla2];
                    }
                }
            }

            //L'array T conté les freqüències entre la tecla x (no asignada) i la resta de tecles no assignades
            double[] T = new double[new_size-1];
            
            //L'array D conté la distància entre la posició y (no asignada) i la resta de posiciones no assignades
            double[] D = new double[new_size-1];

            //Tecla x
            for (int tecla = 0; tecla < new_size; ++tecla) {
                int index = 0;
                for (int i = 0; i < new_size; ++i) {
                    if (tecla != i) {
                        T[index] = (freq_aux[tecla+cont][i+cont])/2.0;
                        ++index;
                    }
                }
                Arrays.sort(T);
                //Pos y
                for (int pos = 0; pos < new_size; ++pos) {
                    int index2 = 0;
                    for (int i = 0; i < new_size; ++i) {
                        if (pos != i) {
                            D[index2] = (layout.distanciaEntre(posDisp[pos], posDisp[i]))/2.0;
                            ++index2;
                        }
                    }
                    Arrays.sort(D);
                    //Producte escalar
                    for (int i = 0; i < new_size-1; ++i) {
                        C[tecla][pos] += T[i]*D[(new_size-2-i)];
                    }
                }
            }

            //Una vegada tenim la matriu C[][] calculem l'asignació òptima mitjançant l'Hungarian Algorithm
            t2 = HA.HungarianAlgorithmOPT(C, new_size);

        }
        return (t1+t2);
    }
}
