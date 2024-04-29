package edu.upc.prop.cluster21_4.persistencia;

import edu.upc.prop.cluster21_4.domini.*;
import java.io.*;

/**
 * Controlador de persistencia del sistema
 * @author Jon Campillo
 */
public class ControladorPersistencia 
{
    private static final String PATH = "./EXE/dades/";
    private static final String TECLATS = "teclats.dat";
    private static final String ALFABETS = "alfabets.dat";
    private static final String DADES = "dades.dat";
    private static final String FREQS = "frequencies.dat";

    /**
     * Carrega els teclats
     * @return Una LlistaTeclats amb els teclats del carregats
     */
    public LlistaTeclats carregaTeclats()
    {
        LlistaTeclats teclats = (LlistaTeclats)carregaObj(TECLATS);
        if(teclats == null)
            return new LlistaTeclats();
            
        return teclats;
    }

    /**
     * Carrega els alfabets
     * @return Una LlistaAlfabets amb els alfabets carregats
     */
    public LlistaAlfabets carregaAlfabets()
    {
        LlistaAlfabets alfabets = (LlistaAlfabets)carregaObj(ALFABETS);
        if(alfabets == null)
        {
            return new LlistaAlfabets();
        }
        //Si no,

        return alfabets;
    }

    /**
     * Carrega les dades
     * @return Una LlistaDadesParaules amb les dades carregades
     */
    public LlistaDadesParaules carregaDades()
    {
        LlistaDadesParaules dades = (LlistaDadesParaules)carregaObj(DADES);
        if(dades == null)
            return new LlistaDadesParaules();
        //Si no,
            
        return dades;
    }

    /**
     * Carrega les frequencies
     * @return Una LlistaFreqSimbols amb les frequencies carregades
     */
    public LlistaFreqSimbols carregaFrequencies()
    {
        LlistaFreqSimbols freqs = (LlistaFreqSimbols)carregaObj(FREQS);
        if(freqs == null)
            return new LlistaFreqSimbols();
        //Si no,   
        return freqs;
    }
    
    /**
     * Desa tots els teclats
     * @param teclats llista de teclats per desar
     */
    public void desaTeclats(LlistaTeclats teclats)
    {
        desaObj(TECLATS, teclats);
    }

    /**
     * Desa tots els alfabets
     * @param alfabets llista d'alfabets per desar
     */
    public void desaAlfabets(LlistaAlfabets alfabets)
    {
        desaObj(ALFABETS, alfabets);
    }

    /**
     * Desa totes les dades
     * @param dades llista de dades per desar
     */
    public void desaDades(LlistaDadesParaules dades)
    {
        desaObj(DADES, dades);
    }

    /**
     * Desa totes les frequencies de simbols
     * @param frequencies llista de frequencies per desar
     */
    public void desaFrequencies(LlistaFreqSimbols frequencies)
    {
        desaObj(FREQS, frequencies);
    }

    private Object carregaObj(String nomFitxer)
    {
        try {
            FileInputStream file = new FileInputStream(PATH+nomFitxer);
            ObjectInputStream obj = new ObjectInputStream(file);

            Object result = obj.readObject();
            obj.close();                
            return result;
        }
        catch (FileNotFoundException e){}
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void desaObj(String nomFitxer, Object objecte)
    {
        File fitxer = new File(PATH+nomFitxer);
        try 
        {
            //Crea el fitxer si no existeix
            fitxer.createNewFile();

            FileOutputStream out = new FileOutputStream(fitxer);
            ObjectOutputStream obj = new ObjectOutputStream(out);
            obj.writeObject(objecte);
            obj.close();
        } 
        catch (Exception e) 
        {
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("Current directory: " + currentDirectory);
            e.printStackTrace();
        }
    }
}
