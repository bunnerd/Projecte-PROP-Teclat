package edu.upc.prop.cluster21_4.domini;

import java.io.Serializable;

import edu.upc.prop.cluster21_4.domini.Excepcions.FormatIncorrecte;

/**
 * Classe per representar dades d'on es pot treure informació
 * @author Marta Sunyer Giménez
 */

public abstract class DadesParaules implements Serializable{

//Consultores

    /**
     * Retorna el número de paraules en un element
     * @return Retorna el número de paraules que hi ha en l'element
     */
    public abstract int size();

    /**
     * Retorna el número de símbols totals en un element
     * @return Retorna el número de símbols totals que hi ha en l'element
     */
    public int numSimbol() {
        return 0;
    }

    /**
     * Retorna el número d'aparicions de la paraula en posició índex
     * @param index posició de la paraula
     * @return Retorna la freqüència de la paraula en posició índex
     */
    public abstract int getFrequencia(int index);

    /**
     * Retorna la paraula en posició índex en l'element
     * @param index posició de la paraula
     * @return Retorna la paraula en posició índex
     */
    public abstract String getParaula(int index);

    /**
     * Retorna el símbol en posició índex en l'element
     * @param index posició del símbol
     * @return Retorna el símbol en posició índex
     */
    public Character getSimbol(int index) {
        return null;
    }


    /**
     * Retorna l'identificador de l'element
     * @return Retorna el nom de l'element
     */
    public abstract String getNom();

    /**
     * Retorna el contingut de l'element
     * @return Retorna el contingut de l'element
     */
    @Override
    public abstract String toString();

    /**
     * Retorna l'alfabet en el que està escrit l'element
     * @return Retorna l'alfabet en el que està escrit l'element
     */
    public abstract Alfabet getAlfabet();  

    /**
     * Retorna una còpia de l'element
     * @return Retorna una còpia de l'element
     * @throws FormatIncorrecte si el format és incorrecte
     */
    public abstract DadesParaules copy() throws FormatIncorrecte;

//Modificadores

    /**
     * Modifica el identificador de l'elemnt
     * @param name nom to set
     */
    public abstract void setNom(String name);

    /**
     * Modifica el contingut de l'element
     * @param contingut contingut to set
     * @throws FormatIncorrecte si el format és incorrecte
     */
    public abstract void setContingut(String contingut) throws FormatIncorrecte;

}
