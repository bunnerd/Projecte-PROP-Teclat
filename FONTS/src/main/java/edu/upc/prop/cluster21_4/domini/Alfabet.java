package edu.upc.prop.cluster21_4.domini;

import java.util.HashSet;
import java.util.Set;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe per representar un alfabet
 * @author Sergi Gonzalez Martos
 */

public class Alfabet implements Serializable {

    //Set on es guarden el símbols del alfabet
    private Set<Character> alfabet;

    //String que representa el nom del alfabet
    private String nom;
    

    //HashMap que serveix per identificar símbols amb accents y dividirlo en dos (símbol sense accent) (accent)
    private static final HashMap<Character, Character[]> acentos = new HashMap<>()
    {
        {
            put('\u00E1', new Character[]{'\u00B4', 'a'}); // á
            put('\u00E9', new Character[]{'\u00B4', 'e'}); // é
            put('\u00ED', new Character[]{'\u00B4', 'i'}); // í
            put('\u00F3', new Character[]{'\u00B4', 'o'}); // ó
            put('\u00FA', new Character[]{'\u00B4', 'u'}); // ú
            put('\u00E0', new Character[]{'`', 'a'});    // à
            put('\u00E8', new Character[]{'`', 'e'});    // è
            put('\u00EC', new Character[]{'`', 'i'});    // ì
            put('\u00F2', new Character[]{'`', 'o'});    // ò
            put('\u00F9', new Character[]{'`', 'u'});    // ù
        }
    };

    /**
     * Creadora buida d'un alfabet
     */
    public Alfabet() {
        this.alfabet = new HashSet<Character>();
        this.nom = null;
    } 

    /**
     * Creadora d'alfabet amb el símbols passats com a paràmetre
     * @param alfabet Set de Characters que representen els símbols del alfabet
     */
    public Alfabet(Set<Character> alfabet) {
        Set<Character> alf = new HashSet<Character>();
        
        //Per cada símbol del set donat:
        /*
         * 1) El passem a minúscula en cas de estar en mayúscula
         * 2) Mirem si és un símbol amb accent. Si ho és el tractem
         * 3) Afegim el símbol al set auxiliar
         */
        
         for (char simbol : alfabet) {

            //Pas 1)
            simbol = Character.toLowerCase(simbol);
            
            //Pas 2)
            if (acentos.containsKey(simbol))
            {
                //Pas 3)
                alf.add(acentos.get(simbol)[0]);
                alf.add(acentos.get(simbol)[1]);
            } 
            
            //Pas 3)
            else alf.add(simbol); 
        }

        this.alfabet = alf;
        this.nom = null;
    }

    /**
     * Creadora d'alfabet amb el símbols i el nom passats com a paràmetre
     * @param alfabet Set de Characters que representen els símbols del alfabet
     * @param nom String que representa el nom del alfabet
     */
    public Alfabet(Set<Character> alfabet, String nom) {
        Set<Character> alf = new HashSet<Character>();

        //Per cada símbol del set donat:
        /*
         * 1) El passem a minúscula en cas de estar en mayúscula
         * 2) Mirem si és un símbol amb accent. Si ho és el tractem
         * 3) Afegim el símbol al set auxiliar
         */

        for (char simbol : alfabet) {

            //Pas 1)
            simbol = Character.toLowerCase(simbol);

            //Pas 2)
            if (acentos.containsKey(simbol))
            {
                //Pas 3)
                alf.add(acentos.get(simbol)[0]);
                alf.add(acentos.get(simbol)[1]);
            }  

            //Pas 3)
            else alf.add(simbol); 
        }

        this.alfabet = alf;
        this.nom = nom;
    }

    /**
     * Funció que retorna una copia del alfabet
     * @return Retorna una còpia de l'alfabet
     */
    public Alfabet copiar() {
        //Alf serà l'alfabet copia
        Alfabet alf = new Alfabet();
        
        //Aux és un alfabet auxiliar amb el mateix alfabet que this
        Character[] aux = alfabet.toArray(new Character[getSize()]);
        
        //Copiem els símbols d'un alfabet a l'altre
        for (int i = 0; i < alfabet.size(); ++i) {
            Character c = aux[i];
            try {
                alf.afegeixSimbol(c); 
            } 
            catch (Exception e) {
            }
        }

        return alf;
    }

    /**
     * Aquesta funció serveix per afegir un símbol a l'alfabet
     * @param simbol El paràmatre és el símbol que es vol afegir al alfabet
     * @return Retorna TRUE si s'ha pogut afegir. FALSE en cas contrari.
     * @throws JaExisteixException si existeix el símbol a l'alfabet
     */
    public boolean afegeixSimbol(Character simbol) throws JaExisteixException {

        //Si el símbol ja existeix dins l'alfabet throw exception
        if (alfabet.contains(simbol)) {
            throw new JaExisteixException("Ja existeix el simbol " + simbol);
        }


        //Passos a realitzar per afegir el símbol donat
        /*
         * 1) El passem a minúscula en cas de estar en mayúscula
         * 2) Mirem si és un símbol amb accent. Si ho és el tractem
         * 3) Afegim el símbol al set auxiliar
         */
        
        //Pas 1)
        simbol = Character.toLowerCase(simbol);
       
        //Pas 2)
        if (acentos.containsKey(simbol))
        {
            //Pas 3)
            alfabet.add(acentos.get(simbol)[0]);
            alfabet.add(acentos.get(simbol)[1]);
        }

        //Pas 3)
        else alfabet.add(simbol);

        return true;
    }

    /**
     * Funció per esborrar un símbol del alfabet
     * @param simbol El paràmatre és el símbol que es vol esborrar de l'alfabet
     * @return Retorna TRUE si s'ha pogut esborrar. FALSE en cas contrari.
     * @throws NoTrobatException si no es pot trobar el símbol a l'alfabet
     */
    public boolean esborraSimbol(Character simbol) throws NoTrobatException {

        //Comprovem si el símbol existeix
        if (alfabet.contains(simbol)) {
            alfabet.remove(simbol);
            return true;
        }

        //Si el símbol no es troba dins l'alfabet throw exception
        throw new NoTrobatException("No s'ha trobat el simbol " + simbol);
    }

    /**
     * Funció per canviar l'alfabet per un altre
     * @param nou_alfabet El paràmatre és el nou alfabet
     * @return no retorna cap valor
     */
    public void setAlfabet(Alfabet nou_alfabet) {
        Alfabet aux = nou_alfabet.copiar();
        this.alfabet = aux.alfabet;
        this.nom = aux.nom;
    }

    /**
     * Funció per canviar el nom per un altre
     * @param nou_nom El paràmatre és el nou nom de l'alfabet
     * @return no retorna cap valor
     */
    public void setNom(String nou_nom) {
        this.nom = nou_nom;
    }

    /**
     * Funció per canviar un símbol per un altre
     * @param simbol El paràmatre és el nou símbol
     * @param index El paràmatre és l'index del símbol a substituir
     * @throws NoTrobatException si no es pot trobar el símbol a l'alfabet
     */
    public void setSimbol(Character simbol, int index) throws NoTrobatException {
        Character[] aux = alfabet.toArray(new Character[getSize()]);
        Character c = aux[index];
        alfabet.remove(c);
        alfabet.add(simbol);
    }

    /**
     * Funció per fusionar un array d'alfabets en un únic
     * @param alfabets_f El paràmatre és un array amb els diferents alfabets a fusionar
     * @return Retorna un alfabet que conté els alfabets passats per paràmetre
     */
    public static Alfabet mergeAlfabets(ArrayList<Alfabet> alfabets_f) {

        //Alfabet fusió
        Alfabet alfabet_fusio = new Alfabet();

        //Set on guardarem tots el símbols
        Set<Character> aux_fusio = new HashSet<Character>();
        
        //Apuntem al alfabet de alfabet_fusio
        aux_fusio = alfabet_fusio.alfabet;
        
        //Per cada alfabet del array agafem el seu set i el fusionem amb el set de alfabet_fusio
        for (Alfabet a : alfabets_f) {
            Set<Character> aux = new HashSet<Character>();
            aux = a.alfabet;
            aux_fusio.addAll(aux);
        }

        return alfabet_fusio;
    }

    /**
     * Funció override de la funció toString. Serveix per convertir l'alfabet en un string
     * @return Retorna l'alfabet en format String
     */
    @Override
    public String toString() {
        Character[] aux = alfabet.toArray(new Character[getSize()]);
        Arrays.sort(aux);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < aux.length; ++i) {
            s.append(aux[i]);
        }
        return s.toString();
    }

    /**
     * Funció per comprovar si dos alfabets son iguals
     * @param a El paràmatre és l'alfabet amb el que es vol comparar
     * @return Retorna TRUE si els dos alfabets són iguals. FALSE en cas contrari
     * @throws NoTrobatException si no es pot trobar el símbol a l'alfabet
     */
    public boolean equals(Alfabet a) throws NoTrobatException {
        Set<Character> aux = new HashSet<Character>(a.getSize());
        for (int i = 0; i < a.getSize(); ++i) aux.add(a.getSimbol(i));
        return this.alfabet.equals(aux);
    }

    /**
     * Funció per comprovar si un símbol existeix a l'alfabet
     * @param simbol El paràmatre és el símbol que volem comprovar si existeix a l'alfabet
     * @return Retorna TRUE si el símbol existeix. FALSE en cas contrari
     */
    public boolean exists(Character simbol) {
        if (alfabet.contains(simbol)) return true;
        return false;
    }

    /**
     * Funció per obtenir l'alfabet
     * @return Retorna un array de Characters amb el símbols de l'alfabet
     */
    public Character[] getAlfabet() {
        Character[] aux = alfabet.toArray(new Character[getSize()]);
        return aux;
    }

    /**
     * Funció per obtenir el símbol a la posició "index" de l'alfabet
     * @param index El paràmatre és la posició a l'alfabet del símbol que es vol obtenir
     * @return Retorna el símbol a la posició "index"
     * @throws NoTrobatException si no es pot trobar el símbol a l'alfabet
     */
    public Character getSimbol(int index) throws NoTrobatException {
        int cont = 0;
        if (index < alfabet.size()) {
            for (char c : alfabet) {
                if (cont == index) return c;
                ++cont;
            }
        }

        //Si el símbol no es troba dins l'alfabet throw exception
        throw new NoTrobatException("no s'ha trobat el index " + index);
    }

    /**
     * Funció per consultar la mida de l'alfabet
     * @return Retorna la mida de l'alfabet
     */
    public int getSize() {
        return alfabet.size();
    }

    /**
     * Funció per consultar nom alfabet
     * @return Retorna el nom de l'alfabet
     */
    public String getNom() {
        return nom;
    }
}