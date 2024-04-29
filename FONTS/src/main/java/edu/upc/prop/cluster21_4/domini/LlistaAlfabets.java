package edu.upc.prop.cluster21_4.domini;

import java.util.HashMap;
import java.util.HashSet;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe per guardar un conjunt d'alfabets
 * @author Sergi Gonzalez Martos
 */

public class LlistaAlfabets implements Serializable{
    private HashMap<String, Alfabet> alfabets;

    /**
     * Creadora buida
     * @return No retorna cap valor
     */
    public LlistaAlfabets() {
        alfabets = new HashMap<String, Alfabet>();
    } 

    /**
     * Funció per afegir un alfabet a la llista d'alfabets
     * @param alfabet El paràmatre és l'alfabet que es vol afegir
     * @return Retorna TRUE si s'ha pogut afegir. FALSE en cas contrari.
     * @throws JaExisteixException si existeix l'alfabet al conjunt
     */
    public boolean afegeixAlfabet(Alfabet alfabet) throws JaExisteixException {
        String nom = alfabet.getNom();

        //Comprovem si l'alfabet ja existeix
        if (!alfabets.containsKey(nom)) {
            alfabets.put(nom, alfabet);
            return true;
        }

        //Si el alfabet ja existeix dins la llista d'alfabets throw exception
        throw new JaExisteixException("Ja existeix l'alfabet " + alfabet.getNom());
    }

    /**
     * Funció per esborrar un alfabet de la llista d'alfabets
     * @param nom El paràmatre és el nom de l'alfabet que es vol esborrar
     * @return Retorna TRUE si s'ha pogut esborrar. FALSE en cas contrari.
     * @throws NoTrobatException si no es pot trobar l'alfabet al conjunt
     */
    public boolean esborraAlfabet(String nom) throws NoTrobatException {

        //Comprovem si l'alfabet existeix
        if (alfabets.containsKey(nom)) {
            alfabets.remove(nom);
            return true;
        }

        //Si el alfabet no es troba dins la llista d'alfabets throw exception
        throw new NoTrobatException("No s'ha trobat l'alfabet " + nom);
    }

    /**
     * Funció per canviar un alfabet per un altre
     * @param nom El paràmatre és el nom del símbol que es vol substituir
     * @param nou_alfabet El paràmatre és l'alfabet nou
     * @return Retorna TRUE si s'ha pogut realitzar la operació. FALSE en cas contrari.
     * @throws JaExisteixException si existeix l'alfabet nou al conjunt
     * @throws NoTrobatException si no es pot trobar l'alfabet a substituir al conjunt
     */
    public boolean setAlfabet(String nom, Alfabet nou_alfabet) throws JaExisteixException, NoTrobatException {

        //Comprovem si existeix l'alfabet a borrar
        if (alfabets.containsKey(nom)) {

            String nom_aux = nou_alfabet.getNom();

            //Comprovem si no existeix l'alfabet a introduïr
            if (nom.equals(nom_aux) || !alfabets.containsKey(nom_aux)) {

                //Únicament borrarem l'alfabet si sabem que podem introduïr el segon
                alfabets.remove(nom);
                alfabets.put(nom_aux, nou_alfabet);
                return true;
            }

            //Si el alfabet ja existeix dins la llista d'alfabets throw exception
            throw new JaExisteixException("Ja existeix l'alfabet " + nom_aux);
        }

        //Si el alfabet no es troba dins la llista d'alfabets throw exception
        throw new NoTrobatException("No s'ha trobat l'alfabet " + nom);
    }

    /**
     * Funció per consultar la mida de la llista d'alfabets
     * @return Retorna la mida del conjunt d'alfabets
     */
    public int getSize() {
        return alfabets.size();
    }

    /**
     * Funció per borrar la llista d'alfabets
     * @return No retorna cap valor
     */
    public void clear() {
        alfabets.clear();
    }

    /**
     * Funció per comprovar si un alfabet existeix a la llista d'alfabets
     * @param nom El paràmatre és el nom de l'alfabet que es vol comprovar si existeix
     * @return Retorna TRUE si existeix. FALSE en cas contrari.
     */
    public boolean exists(String nom) {
        if (alfabets.containsKey(nom)) return true;
        return false;
    }

    /**
     * Funció per obtenir un alfabet amb nom "nom"
     * @param nom El paràmatre és el nom de l'alfabet que volem obtenir
     * @return Retorna l'alfabet amb el nom passat per paràmatre
     * @throws NoTrobatException si no es pot trobar l'alfabet al conjunt
     */
    public Alfabet getAlfabet(String nom) throws NoTrobatException {
        if (alfabets.containsKey(nom)) return alfabets.get(nom);
        //Si el alfabet no es troba dins la llista d'alfabets throw exception
        throw new NoTrobatException("No s'ha trobat l'alfabet " + nom);
    }

    /**
     * Funció per obtenir un array amb els noms de tots els alfabets de la llista d'alfabets
     * @return Retorna un ArrayList amb els noms dels alfabets continguts al conjunt
     */
    public ArrayList<String> getNoms() {
        ArrayList<String> noms = new ArrayList<String>(alfabets.size());
        for (Alfabet a : alfabets.values()) noms.add(a.getNom());
        return noms;
    }
}