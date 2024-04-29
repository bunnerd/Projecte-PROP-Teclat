package edu.upc.prop.cluster21_4.domini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

import edu.upc.prop.cluster21_4.domini.Excepcions.FormatIncorrecte;

/**
 * Classe per representar llistes de paraules
 * @author Marta Sunyer Giménez
 */
public class FreqParaules extends DadesParaules{
    
    //Classe auxiliar que conté la paraula i la seva freqüencia
    private class Pair implements Serializable{
        String word;
        int freq;

        //Creadora

        public Pair(String word, int freq) {
            this.word = word;
            this.freq = freq;
        }
    }

    private String name;
    private ArrayList<Pair> words;

//Creadora
    /**
     * Creadora buida d'un text
     */
    public FreqParaules() {
        this.name = null;
        this.words = null;
    }

    /**
     * Creadora de text amb el contingut i el nom passats com a paràmetre
     * @param ident identificador de la llista de paraules
     * @param words contingut de la llista de paraules
     * @throws FormatIncorrecte si el format del contingut no és correcte
     */
    public FreqParaules(String ident, String words) throws FormatIncorrecte {
        this.name = ident;
        if (!words.isEmpty()) {
            this.words = new ArrayList<Pair>();
            ArrayList<String> w = new ArrayList<String>(Arrays.asList(words.split("\\s+")));
            //Si hi ha alguna paraula sense parella
            if (w.size()%2 != 0) throw new FormatIncorrecte("Format: paraula frequencia paraula frequenica etc");
            for (int i = 0; i < w.size(); i += 2) {
                String num = w.get(i+1);
                for (int j = 0; j < num.length(); ++j) {
                    //Si la parella no és un número
                    if (!Character.isDigit(num.charAt(j))) throw new FormatIncorrecte("Format: paraula frequencia paraula frequenica etc");
                }
                this.words.add(new Pair(w.get(i),Integer.parseInt(w.get(i+1))));
            }
        } else {
            this.words = new ArrayList<Pair>(0);
        }
    }

//Consultores

    /**
     * Retorna el número de paraules en una llista de paraules
     * @return Retorna el número de paraules en la llista de paraules
     */
    public int size() {
        return words.size();
    }

    /**
     * Retorna la paraula en posició índex en la llista de paraules
     * @param index posició de la paraula
     * @return Retorna la paraula en posició índex
     */
    public String getParaula(int index) {
        return words.get(index).word;
    }

    /**
     * Retorna la freqüència de la paraula en posició índex
     * @param index posició de la paraula
     * @return Retorna la freqüència de la paraula
     */
    public int getFrequencia(int index) {
        return words.get(index).freq;
    }

    /**
     * Retorna l'identificador de la llista de paraules
     * @return Retorna el nom de la llista de paraules
     */
    public String getNom() {
        return name;
    }

    /**
     * Retorna el contingut de la llista de paraules
     * @return Retorna el contingut de la llista de paraules
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < words.size(); ++i) {
            stringBuilder.append(words.get(i).word).append(" ").append(words.get(i).freq);
            if (i < words.size() - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Retorna l'alfabet en el que està escrita la llista de paraules
     * @return Retorna l'alfabet en el que està escrit la llista de paraules
     */
    public Alfabet getAlfabet() {
        Set<Character> alf = new HashSet<Character>();
        for (int i = 0; i < words.size(); ++i) {
            String s = words.get(i).word;
            for (int j = 0; j < s.length(); ++j) {
                if (!alf.contains(s.charAt(j))) alf.add(s.charAt(j));
            }
        }
        Alfabet a = new Alfabet(alf);
        return a;
    }    

    /**
     * Retorna una còpia de la llista de paraules
     * @return Retorna una còpia de la llista de paraules
     */
    public DadesParaules copy() throws FormatIncorrecte {
        String s = this.toString();
        FreqParaules copyf = new FreqParaules(this.name, s);
        return copyf;
    }

//Modificadores

    /**
     * Modifica el identificador de la llista de paraules
     * @param name nom to set
     */
    public void setNom(String name) {
        this.name = name;
    }

    /**
     * Modifica el contingut de la llista de paraules
     * @param contingut contingut to set
     * @throws FormatIncorrecte si el format del contingut és incorrecte
     */
    public void setContingut(String contingut) throws FormatIncorrecte {
        if (!words.isEmpty()) {
            this.words = new ArrayList<Pair>();
            ArrayList<String> w = new ArrayList<String>(Arrays.asList(contingut.split("\\s+")));
            if (w.size()%2 != 0) throw new FormatIncorrecte("Format: paraula frequencia paraula frequenica etc");
            for (int i = 0; i < w.size(); i += 2) {
                String num = w.get(i+1);
                for (int j = 0; j < num.length(); ++j) {
                    if (!Character.isDigit(num.charAt(j))) throw new FormatIncorrecte("Format: paraula frequencia paraula frequenica etc");
                }
                this.words.add(new Pair(w.get(i),Integer.parseInt(w.get(i+1))));
            }
        } else {
            this.words = new ArrayList<Pair>(0);
        }
    }
}
