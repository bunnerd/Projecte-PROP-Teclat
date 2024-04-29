package edu.upc.prop.cluster21_4.domini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.upc.prop.cluster21_4.domini.Excepcions.FormatIncorrecte;

/**
 * Classe per representar un text
 * @author Marta Sunyer Giménez
 */

public class Text extends DadesParaules{

    private static Character[] alf_cat = {'\u0061','\u0062','\u0063','\u0064','\u0065','\u0066','\u0067','\u0068','\u0069','\u006A','\u006B','\u006C','\u006D','\u006E','\u006F','\u0070','\u0071','\u0072','\u0073','\u0074','\u0075','\u0076','\u0077','\u0078','\u0079','\u007A',
                                            '\u00E1','\u00E9','\u00ED','\u00F3','\u00FA','\u00E0','\u00E8','\u00EC','\u00F2','\u00F9',
                                            '\u00F1','\u00E7'};

    private String name;
    private String text;
    private ArrayList<String> paraules;

    /**
     * Creadora buida d'un text
     */
    public Text() {
        this.name = null;
        this.text = null;
        this.paraules = null;
    }

    /**
     * Creadora de text amb el contingut i el nom passats com a paràmetre
     * @param ident identificador del text
     * @param text contingut del text
     */
    public Text(String ident, String text) {
        this.name = ident;
        this.text = new String(text); 
        if (!text.isEmpty()) {
            this.paraules = new ArrayList<String>();
            ArrayList<String> w = new ArrayList<String>(Arrays.asList(text.split("\\s+")));
            for (int i = 0; i < w.size(); ++i) {
                this.paraules.add(w.get(i));
            }
        } else {
            this.paraules = new ArrayList<String>(0);
        }
    }

    /**
     * Retorna el número de paraules en un text
     * @return Retorna el número de paraules que hi ha en el text
     */
    public int size() {
        return paraules.size();
    }

    /**
     * Retorna el número de símbols totals en un text
     * @return Retorna el número de símbols totals que hi ha en el text
     */
    public int numSimbol() {
        return text.length();
    }
    
    /**
     * Retorna el número d'aparicions de la paraula en posició índex
     * @param index posició de la paraula
     * @return Retorna 1
     */
    public int getFrequencia(int index) {
        return 1;
    }

    /**
     * Retorna la paraula en posició índex en el text
     * @param index posició de la paraula
     * @return Retorna la paraula en posició índex
     */
    public String getParaula(int index) {
        return paraules.get(index);
    }

    /**
     * Retorna el símbol en posició índex en el text
     * @param index posició del símbol
     * @return Retorna el símbol en posició índex
     */
    public Character getSimbol(int index) {
        return text.charAt(index);
    }

    /**
     * Retorna l'identificador del text
     * @return Retorna el nom del text
     */
    public String getNom() {
        return name;
    }

    /**
     * Retorna el contingut del text
     * @return Retorna el contingut del text
     */
    @Override
    public String toString() {
        String t = new String(text);
        return t;
    }

    /**
     * Retorna l'alfabet en el que està escrit el text
     * @return Retorna l'alfabet en el que està escrit el text
     */
    public Alfabet getAlfabet() {
        Set<Character> alf = new HashSet<Character>();
        for (int i = 0; i < text.length(); ++i) {
            if (!alf.contains(text.charAt(i))) alf.add(text.charAt(i));
        }
        Set<Character> ac = new HashSet<Character>(Arrays.asList(alf_cat));
        ac.retainAll(alf);
        Alfabet res = new Alfabet(ac);
        return res;
    }    

    /**
     * Retorna una còpia del text
     * @return Retorna una còpia del text
     */
    public DadesParaules copy() throws FormatIncorrecte {
        Text copyt = new Text();
        copyt.setNom(this.name);
        copyt.setContingut(this.text);
        return copyt;
    }

//Modificadores

    /**
     * Modifica el identificador del text
     * @param name nom to set
     */
    public void setNom(String name) {
        this.name = name;
    }

    /**
     * Modifica el contingut del text
     * @param contingut contingut to set
     */
    public void setContingut(String contingut) {
        this.text = new String(contingut); 
        if (!contingut.isEmpty()) {
            this.paraules = new ArrayList<String>();
            ArrayList<String> w = new ArrayList<String>(Arrays.asList(contingut.split("\\s+")));
            for (int i = 0; i < w.size(); ++i) {
                this.paraules.add(w.get(i));
            }
        } else {
            this.paraules = new ArrayList<String>(0);
        }
    }

}
