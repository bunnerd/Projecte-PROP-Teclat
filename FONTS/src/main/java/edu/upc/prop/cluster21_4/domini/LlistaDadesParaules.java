package edu.upc.prop.cluster21_4.domini;

import java.util.HashMap;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe per guardar elements del tipus DadesParaules
 * @author Marta Sunyer Giménez
 */

public class LlistaDadesParaules implements Serializable{
    //String == nom de DadesParaules
    HashMap<String,DadesParaules> llista;

    //Creadora

    /**
     * Creadora buida de la llista de dades
     */
    public LlistaDadesParaules() {
        llista = new HashMap<String,DadesParaules>();
    }

//Consultores

    /**
     * Retorna el número de dades en la llista
     * @return Retorna el número de dades en la llista
     */
    public int size() {
        return llista.size();
    }

    /**
     * Retorna si un element existeix dins la llista
     * @param nom nom de l'element
     * @return Retorna TRUE si l'element existeix, FALSE si no
     */
    public boolean exists(String nom) {
        return llista.containsKey(nom);
    }

    /**
     * Retorna els noms dels element de la llista
     * @return Retorna els noms dels elements de la llista
     */
    public ArrayList<String> getNoms() {
        ArrayList<String> a = new ArrayList<String>(llista.size());
        for (DadesParaules d : llista.values()) {
            a.add(d.getNom());
        }
        return a;
    }

    /**
     * Retorna els noms dels textos de la llista
     * @return Retorna els noms dels textos de la llista
     */
    public ArrayList<String> getNomsTexts() {
        ArrayList<String> a = new ArrayList<String>();
        for (DadesParaules d : llista.values()) {
            if (d instanceof Text) a.add(d.getNom());
        }
        return a;
    }

    /**
     * Retorna un element
     * @param nom nom de l'element
     * @return Retorna l'element nom
     * @throws NoTrobatException si l'element no existeix dins la llista
     */
    public DadesParaules getDadesParaules(String nom) throws NoTrobatException {
        if (!llista.containsKey(nom)) {
            throw new NoTrobatException("no s'ha trobat el les dades " + nom);
        }
        return llista.get(nom);
    }

//Modificadora

    /**
     * Modifica el nom d'un element
     * @param nom nom to set
     * @param dades element al que es modifica el nom
     * @throws NoTrobatException si l'element no existeix dins la llista
     * @throws JaExisteixException si ja hi ha un element amb el nom to set dins la llista
     */
    public void setDadesParaules(String nom, DadesParaules dades) throws NoTrobatException, JaExisteixException
    {
        if(!llista.containsKey(nom)) {
            throw new NoTrobatException("no s'han trobat les dades " + nom);
        }
        //Modifiquem el nom
        if(!dades.getNom().equals(nom)) { 
            if(llista.containsKey(dades.getNom())) {
                throw new JaExisteixException("ja existeix unes dades amb nom " + nom);
            }
            removeDadesParaules(nom);
            addDadesParaules(dades);
        }
        if(llista.containsKey(nom)) {   
            llista.replace(nom, dades);
        }
    }

    /**
     * Afegeix un element
     * @param dades l'element a afegir
     * @throws JaExisteixException si ja existeix un element amb el mateix nom
     */
    public void addDadesParaules(DadesParaules dades) throws JaExisteixException {
        if (llista.containsKey(dades.getNom())) {
            throw new JaExisteixException("ja existeixen unes dades amb nom " + dades.getNom());
        }
        llista.put(dades.getNom(),dades);
    }

    /**
     * Elimina un element
     * @param nom nom de l'element a eliminar
     * @throws NoTrobatException si ja no existeix un element amb el nom nom
     */
    public void removeDadesParaules(String nom) throws NoTrobatException {
        if (!llista.containsKey(nom)) {
            throw new NoTrobatException("no s'ha trobat les dades " + nom);
        }
        llista.remove(nom);
    }
}
