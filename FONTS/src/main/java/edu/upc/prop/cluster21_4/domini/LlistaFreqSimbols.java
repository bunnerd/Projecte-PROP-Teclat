package edu.upc.prop.cluster21_4.domini;

import java.util.HashMap;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe per representar la llista de frequencies del sistema
 * @author Sandra Vázquez
 */

public class LlistaFreqSimbols implements Serializable{
    private HashMap<String, FrequenciesSimbols> llistaFreqSimbols;

    /**
     * Constructora per defecte
     */
    public LlistaFreqSimbols()
    {
        llistaFreqSimbols = new HashMap<>();
    }

    /**
     * 
     * @return La quantitat de frequüències que hi ha
     */
    public int size()
    {
        return llistaFreqSimbols.size();
    }

    /**
     * Assigna freq a la que abans era la freqüència identificada per "nom"
     * @param nom nom de la freqüència que es vol actualitzar
     * @param freq freqüència nova
     * @throws NoTrobatException si no s'ha pogut trobar una freqüència amb aquest nom
     * @throws JaExisteixException si ja hi ha una freqüència amb el mateix nom que freq
     */
    public void setFrequencies(String nom, FrequenciesSimbols freq) throws NoTrobatException, JaExisteixException
    {
        if (!llistaFreqSimbols.containsKey(nom))
            throw new NoTrobatException("No existeix la frequencia " + nom);
        if (llistaFreqSimbols.containsKey(freq.getNom()))
            throw new JaExisteixException("Ja existeix la frequencia " + freq.getNom());
        eliminaFrequencies(nom);
        afegeixFrequencies(freq);
    }

    /**
     * Afegeix una freqüència a la llista
     * @param freq freqüència a afegir
     * @throws JaExisteixException si ja hi ha una freqüència amb el mateix nom que freq
     */
    public void afegeixFrequencies(FrequenciesSimbols freq) throws JaExisteixException
    {
        if (llistaFreqSimbols.containsKey(freq.getNom()))
            throw new JaExisteixException("Ja existeix la frequencia " + freq.getNom());
        else
            llistaFreqSimbols.put(freq.getNom(), freq);
    }

    /**
     * Esborra una freqüència de la llista
     * @param nom nom de la freqüència a esborrar
     * @throws NoTrobatException si no s'ha pogut trobar una freqüència amb aquest nom
     */
    public void eliminaFrequencies(String nom) throws NoTrobatException
    {
        if (llistaFreqSimbols.containsKey(nom))
            llistaFreqSimbols.remove(nom);
        else
            throw new NoTrobatException("No s'ha trobat la frequencia " + nom);
    }

    /**
     * Obté una freqüència
     * @param nom nom de la freqüència
     * @return La freqüència amb el nom especificat
     * @throws NoTrobatException si no s'ha pogut trobar una freqüència amb aquest nom
     */
    public FrequenciesSimbols getFrequenciesSimbols(String nom) throws NoTrobatException
    {
        if (llistaFreqSimbols.containsKey(nom))
            return llistaFreqSimbols.get(nom);
        else
            throw new NoTrobatException("No s'ha trobat la frequencia " + nom);
    }

    /**
     * Obte els nims de les freqüències
     * @return Els noms de totes les freqüències de la llista
     */
    public ArrayList<String> getNoms()
    {
        ArrayList<String> llistaNoms = new ArrayList<>(llistaFreqSimbols.size());
        for (FrequenciesSimbols f : llistaFreqSimbols.values())
        {
            llistaNoms.add(f.getNom());
        }
        return llistaNoms;
    }
}