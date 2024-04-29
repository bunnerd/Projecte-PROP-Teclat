package edu.upc.prop.cluster21_4.domini;

import java.util.HashMap;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.Serializable;
import java.util.ArrayList;

 /**
  * Classe per representar les frequencies entre tot parell de caracters d'un alfabet
  * @author Sandra Vázquez
  */
public class FrequenciesSimbols  implements Serializable{
    private String nom;
    private int frequencia[][];
    // Els caracters son sempre minuscules
    private HashMap<Character, Integer> traduccio;
    private Alfabet alfabet;

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
     * Constructora per defecte
     */
    public FrequenciesSimbols()
    {
        nom = null; frequencia = null; traduccio = null; alfabet = null;
    }

    /**
     * Constructora amb una única dada
     * @param dades dades a partir de les quals es calcularà la freqüència
     */
    public FrequenciesSimbols(DadesParaules dades)
    {
        // Genera l'alfabet corresponent a les dades
        alfabet = dades.getAlfabet();

        frequencia = new int[alfabet.getSize()][alfabet.getSize()];

        // Inicialitza la traduccio entre caracters i indexs
        traduccio = new HashMap<>();
        try {
            for (int i = 0; i < alfabet.getSize(); ++i)
            {
                traduccio.put(alfabet.getSimbol(i), i);
            }
        } catch (Exception e) {} //Mai es demanara un simbol iessim anterior al primer o posterior a l'ultim

        // Calcul de les frequencies
        frequencia(dades);
    }

    /**
     * Constructora por una única dada, amb nom
     * @param dades dades a partir de les quals es calcularà la freqüència
     * @param nom nom de la freqüència
     */
    public FrequenciesSimbols(DadesParaules dades, String nom)
    {
        this(dades);
        this.nom = nom;
    }

    /**
     * Constructora por una única dada, amb alfabet
     * @param dades dades a partir de les quals es calcularà la freqüència
     * @param alfabet alfabet entre els caràcters del qual es guardaran les freqüències
     */
    public FrequenciesSimbols(DadesParaules dades, Alfabet alfabet)
    {
        this.alfabet = alfabet.copiar();

        frequencia = new int[alfabet.getSize()][alfabet.getSize()];

        // Inicialitza la traduccio entre caracters i indexs
        traduccio = new HashMap<>();
        try {
            for (int i = 0; i < alfabet.getSize(); ++i)
            {
                traduccio.put(alfabet.getSimbol(i), i);
            }
        } catch (Exception e) {} //Mai es demanara un simbol iessim anterior al primer o posterior a l'ultim

        // Càlcul de les frequencies
        frequencia(dades);
    }

    /**
     * Constructora por una única dada, amb alfabet i nom
     * @param dades dades a partir de les quals es calcularà la freqüència
     * @param alfabet alfabet entre els caràcters del qual es guardaran les freqüències
     * @param nom nom de la freqüència
     */
    public FrequenciesSimbols(DadesParaules dades, Alfabet alfabet, String nom)
    {
        this(dades, alfabet);
        this.nom = nom;
    }

    /**
     * Constructora per més d'una dada
     * @param llistaDades llista de les dades a partir de les quals es calcularà la freqüència
     */
    public FrequenciesSimbols(ArrayList<DadesParaules> llistaDades)
    {
        // Es genera l'alfabet corresponent a cada dada i despres es fusionen tots en un
        ArrayList<Alfabet> llistaAlfabets = new ArrayList<>();
        for (DadesParaules dades : llistaDades)
        {
            llistaAlfabets.add(dades.getAlfabet());
        }
        alfabet = Alfabet.mergeAlfabets(llistaAlfabets);

        frequencia = new int[alfabet.getSize()][alfabet.getSize()];

        /// Inicialitza la traduccio entre caracters i indexs
        traduccio = new HashMap<>();
        try {
            for (int i = 0; i < alfabet.getSize(); ++i)
            {
                traduccio.put(alfabet.getSimbol(i), i);
            }
        } catch (Exception e) {} //Mai es demanara un simbol iessim anterior al primer o posterior a l'ultim

        // Càlcul de les frequencies
        for (DadesParaules dades : llistaDades)
        {
            frequencia(dades);
        }
    }

    /**
     * Constructora per més d'una dada, amb nom
     * @param llistaDades llista de les dades a partir de les quals es calcularà la freqüència
     * @param nom nom de la freqüència
     */
    public FrequenciesSimbols(ArrayList<DadesParaules> llistaDades, String nom)
    {
        this(llistaDades);
        this.nom = nom;
    }

    /**
     * Constructora per més d'una dada, amb alfabet
     * @param llistaDades llista de les dades a partir de les quals es calcularà la freqüència
     * @param alfabet alfabet entre els caràcters del qual es guardaran les freqüències
     */
    public FrequenciesSimbols(ArrayList<DadesParaules> llistaDades, Alfabet alfabet)
    {
        this.alfabet = alfabet.copiar();
        frequencia = new int[alfabet.getSize()][alfabet.getSize()];

        // Inicialitza la traduccio entre caracters i indexs
        traduccio = new HashMap<>();
        try {
            for (int i = 0; i < alfabet.getSize(); ++i)
            {
                traduccio.put(alfabet.getSimbol(i), i);
            }
        } catch (Exception e) {} //Mai es demanara un simbol iessim anterior al primer o posterior a l'ultim
        

        // Càlcul de les frequencies
        for (DadesParaules dades : llistaDades)
        {
            frequencia(dades);
        }
    }

    /**
     * Constructora per més d'una dada, amb alfabet i nom
     * @param llistaDades llista de les dades a partir de les quals es calcularà la freqüència
     * @param alfabet alfabet entre els caràcters del qual es guardaran les freqüències
     * @param nom nom de la freqüència
     */
    public FrequenciesSimbols(ArrayList<DadesParaules> llistaDades, Alfabet alfabet, String nom)
    {
        this(llistaDades,alfabet);
        this.nom = nom;
    }

    private void frequencia(DadesParaules dades)
    {
        for (int i = 0; i < dades.size(); ++i)
        {
            String paraula = dades.getParaula(i);
            // Per guardar les lletres com á (a amb accent) com 'a' i '´' per separat
            // Tambe guardarem els caracters ja com a minuscules
            ArrayList<Character> charsParaula = new ArrayList<>();
            for (int j = 0; j < paraula.length(); ++j)
            {
                Character c = paraula.charAt(j);

                // Si es una majuscula el tornem minuscula
                if (Character.isUpperCase(c)) c = Character.toLowerCase(c);
                
                // Si el caracter es de l'alfabet no hi ha cap problema
                if (alfabet.exists(c)) charsParaula.add(c);
                // Si no ho es potser es perque es una vocal amb accent, en aquest cas guardem
                // l'accent i la vocal per comptar la frequencia per separat
                else
                {
                    if (acentos.containsKey(c))
                    {
                        Character c1 = acentos.get(c)[0];
                        if (alfabet.exists(c1)) charsParaula.add(c1);
                        Character c2 = acentos.get(c)[1];
                        if (alfabet.exists(c2)) charsParaula.add(c2);
                    }
                }
            }
            // Calcul de les frequencies
            for (int j = 0; j < charsParaula.size() - 1; ++j)
            {
                Character c1 = charsParaula.get(j);
                Character c2 = charsParaula.get(j+1);
                int indexChar1 = traduccio.get(c1);
                int indexChar2 = traduccio.get(c2);
                frequencia[indexChar1][indexChar2] += dades.getFrequencia(i);
                if (c1 != c2) frequencia[indexChar2][indexChar1] += dades.getFrequencia(i);
            }
        }
    }

    /**
     * Retorna la freqüència entre dos caràcters
     * @param c1 el primer caràcter sobre el que consultar la freqüència
     * @param c2 el segon caràcter sobre el que consultar la freqüència
     * @return La freqüència entre c1 i c2 si els dos són a l'alfabet de la freqüència, 0 en cas contrari
     */
    public int getFrequencia(Character c1, Character c2)
    {
        if (!alfabet.exists(c1) || !alfabet.exists(c2)) return 0;
        Integer i = traduccio.get(c1);
        Integer j = traduccio.get(c2);
        return frequencia[i][j];
    }

    /**
     * Copiadora de freqüències
     * @param f la freqüència a copiar
     * @return Una deep copy de la freqüència f
     */
    public static FrequenciesSimbols copia(FrequenciesSimbols f)
    {
        FrequenciesSimbols copia = new FrequenciesSimbols();
        copia.alfabet = f.getAlfabet().copiar();
        copia.nom = f.nom;
        int n = f.frequencia.length;
        copia.frequencia = new int[n][n];
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                copia.frequencia[i][j] = f.frequencia[i][j];
            }
        }
        copia.traduccio = new HashMap<>();
        for (Character c : f.traduccio.keySet())
        {
            copia.traduccio.put(c, f.traduccio.get(c));
        }
        return copia;
    }

    /**
     * Retorna el nom de la freqüència
     * @return El nom
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * Retorna l'alfabet associat als símbols
     * @return L'alfabet de la freqüència
     */
    public Alfabet getAlfabet()
    {
        return alfabet;
    }

    /**
     * Retorna les dades de freqüències entre símbols
     * @return La matriu d'enters de freqüències
     */
    public int[][] getFrequencia()
    {
        return frequencia;
    }

    /**
     * Canvia el nom de la freqüència
     * @param s nou nom de la freqüència
     */
    public void setNom(String s)
    {
        nom = s;
    }

    /**
     * Reimplementa toString
     * @return La freqüència en format visible per consola
     */
    @Override
    public String toString()
    {
        try {            
            String res = new String();
            res += ' ';
            for (int i = 0; i < frequencia.length; ++i)
            {
                res = res + ' ' + alfabet.getSimbol(i);
            }

            res += '\n';

            for (int i = 0; i < frequencia.length; ++i)
            {
                res += alfabet.getSimbol(i);
                for (int j = 0; j < frequencia.length; ++j)
                {
                    res = res + ' ' + frequencia[i][j];
                }
                res += '\n';                
            }
            return res;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Error";
        }
        
    }
}