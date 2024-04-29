package edu.upc.prop.cluster21_4.domini;

import java.util.HashMap;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe per representar la llista de teclats que te el sistema
 * @author Jon Campillo
 */
public class LlistaTeclats implements Serializable
{
    private HashMap<String,Teclat> teclats;

    /**
     * Constructora per defecte
     */
    public LlistaTeclats()
    {
        teclats = new HashMap<String,Teclat>();
    }

    /**
     * 
     * @return La quantitat de teclats que hi ha ara
     */
    public int size()
    {
        return teclats.size();
    }

    /**
     * Buida la llista
     */
    public void clear()
    {
        teclats.clear();
    }

    /**
     * Obte un teclat
     * @param nom nom del teclat
     * @return El teclat amb el nom especificat
     * @throws NoTrobatException si no es pot trobar el teclat
     */
    public Teclat getTeclat(String nom) throws NoTrobatException
    {
        if(teclats.containsKey(nom))
        {
            return teclats.get(nom);
        }
        else
        {
            throw new NoTrobatException("no s'ha trobat el teclat " + nom);
        }
    }

    /**
     * Busca un teclat
     * @param nom nom del teclat
     * @return True si la Llista conte un teclat identificat per "nom", false en cas contrari
     */
    public boolean existeixTeclat(String nom)
    {
        return teclats.containsKey(nom);
    }

    /**
     * Assigna tec al que abans era el Teclat identificat per "nom"
     * @param tec teclat nou
     * @param nom nom del teclat que es vol actualitzar
     * @throws NoTrobatException si no es pot trobar el teclat amb aquest nom
     * @throws JaExisteixException si ja hi ha un teclat amb el mateix nom que tec
     */
    public void setTeclat(Teclat tec, String nom) throws NoTrobatException, JaExisteixException
    {
        if(!teclats.containsKey(nom))
        {
            //Nom no trobat
            throw new NoTrobatException("no s'ha trobat el teclat " + nom);
        }
        
        if(!tec.getNom().equals(nom)) //Estem modificant el nom del teclat
        {
            if(teclats.containsKey(tec.getNom()))
            {
                //Hi ha un altre teclat amb el nou nom
                throw new JaExisteixException("ja existeix un teclat amb nom " + nom);
            }
            //Else,

            esborraTeclat(nom);
            afegeixTeclat(tec);
        }

        if(teclats.containsKey(nom)) //No estem modificant el nom del teclat
        {
            teclats.replace(nom, tec);
        }
    }

    /**
     * Afegeix un teclat a la llista
     * @param tec teclat a afegir
     * @throws JaExisteixException si ja existeix un teclat amb el mateix nom que tec
     */
    public void afegeixTeclat(Teclat tec) throws JaExisteixException
    {
        String nom = tec.getNom();

        if(teclats.containsKey(nom))
        {
            throw new JaExisteixException("ja existeix un teclat amb nom " + nom);
        }
        else
        {
            teclats.put(nom, tec);
        }
    }

    /**
     * Esborra un teclat de la llista
     * @param nom nom del teclat a esborrar
     * @throws NoTrobatException si no s'ha pogut trobar el teclat a esborrar
     */
    public void esborraTeclat(String nom) throws NoTrobatException
    {
        if(teclats.containsKey(nom))
        {
            teclats.remove(nom);
        }
        else
        {
            throw new NoTrobatException("no s'ha trobat un teclat amb nom " + nom);
        }
    }

    /**
     * Obte els noms dels teclats
     * @return Els noms de tots els teclats de la llista
     */
    public ArrayList<String> getNoms()
    {
        ArrayList<String> res = new ArrayList<String>(teclats.size());
        
        for(Teclat t : teclats.values())
        {
            res.add(t.getNom());
        }

        return res;
    }
}