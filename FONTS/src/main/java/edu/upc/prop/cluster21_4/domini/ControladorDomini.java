package edu.upc.prop.cluster21_4.domini;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import edu.upc.prop.cluster21_4.persistencia.*;

import edu.upc.prop.cluster21_4.domini.Excepcions.FormatIncorrecte;
import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoExisteixMetodeException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Controlador del domini
 * @author Jon Campillo    
 */
public class ControladorDomini 
{
    private LlistaTeclats teclats;
    private LlistaAlfabets alfabets;
    private LlistaDadesParaules dades;
    private LlistaFreqSimbols freqs;

    private ControladorPersistencia CP;

    public ControladorDomini()
    {
        CP = new ControladorPersistencia();
        teclats = CP.carregaTeclats();
        alfabets = CP.carregaAlfabets();
        dades = CP.carregaDades();
        freqs = CP.carregaFrequencies();
    }

    /**
     * Operacio de creacio d'un teclat on es dona l'alfabet i les frequencies entre simbols
     * @param metode metode de generacio. Opcions: "BranchAndBound", "CercaLocal"
     * @param nomTeclat nom pel teclat.
     * @param nomAlfabet nom del alfabet que es fara servir
     * @param nomFreq nom de les frequencies entre simbols que es faran servir
     * @param iteracions nombre d'iteracions si es fa servir CercaLocal
     */
    public void generaTeclatAlfabetFrequencia(String metode, String nomTeclat, String nomAlfabet, String nomFreq, int iteracions) throws Exception
    {
        //Busca el teclat
        if(teclats.existeixTeclat(nomTeclat))
        {
            throw new JaExisteixException("ja existeix un teclat amb nom " + nomTeclat);
        }

        //Busca l'alfabet
        Alfabet alfabet = alfabets.getAlfabet(nomAlfabet);

        //Busca la frequencia de simbols
        FrequenciesSimbols freq = freqs.getFrequenciesSimbols(nomFreq);

        //Crea un layout
        Layout layout = new Layout(alfabet.getSize());

        char[] assignacio = null;

        if(metode.equals("BranchAndBound"))
        {
            assignacio = executaAlgorisme(new QAP(), alfabet, freq, layout);
        }
        else if(metode.equals("CercaLocal"))
        {
            assignacio = executaAlgorisme(new CercaLocal(iteracions), alfabet, freq, layout);
        }
        else
        {
            throw new NoExisteixMetodeException("no existeix el metode " + metode + " per generar un teclat");
        }

        Teclat teclat = new Teclat(alfabet, freq, layout, assignacio, nomTeclat);
        teclats.afegeixTeclat(teclat);
        CP.desaTeclats(teclats);
    }

    /**
     * Operacio de creacio d'un teclat on es donen nomes dades (text o llistes de paraules amb frequencies). Es dedueix un alfabet d'elles.
     * @param metode metode de generacio. Opcions: "BranchAndBound", "CercaLocal"
     * @param nomTeclat nom pel teclat.
     * @param nomDades nom de les dades que es faran servir
     * @param iteracions nombre d'iteracions si es fa servir CercaLocal
     */
    public void generaTeclatDades(String metode, String nomTeclat, String nomDades, int iteracions) throws Exception
    {
        //Busca el teclat
        if(teclats.existeixTeclat(nomTeclat))
        {
            throw new JaExisteixException("ja existeix un teclat amb nom " + nomTeclat);
        }

        //Busca les dades
        DadesParaules d = dades.getDadesParaules(nomDades);

        //Busca la frequencia de simbols
        FrequenciesSimbols freq = new FrequenciesSimbols(d);

        //Obte l'alfabet de freq
        Alfabet alfabet = freq.getAlfabet();

        //Crea un layout
        Layout layout = new Layout(alfabet.getSize());

        char[] assignacio = null;

        if(metode.equals("BranchAndBound"))
        {
            assignacio = executaAlgorisme(new QAP(), alfabet, freq, layout);
        }
        else if(metode.equals("CercaLocal"))
        {
            assignacio = executaAlgorisme(new CercaLocal(iteracions), alfabet, freq, layout);
        }
        else
        {
            throw new NoExisteixMetodeException("no existeix el metode " + metode + " per generar un teclat");
        }

        Teclat teclat = new Teclat(alfabet, freq, layout, assignacio, nomTeclat);
        teclats.afegeixTeclat(teclat);
        CP.desaTeclats(teclats);
    }

    /**
     * Operacio de creacio d'un teclat on es dona una frequencia entre simbols. Es treu l'alfabet d'ella
     * @param metode metode de generacio. Opcions: "BranchAndBound", "CercaLocal"
     * @param nomTeclat nom pel teclat.
     * @param nomFrequencia nom de la frequencia que es faran servir
     * @param iteracions nombre d'iteracions si es fa servir CercaLocal
     */
    public void generaTeclatFrequencia(String metode, String nomTeclat, String nomFrequencia, int iteracions) throws Exception
    {
        //Busca el teclat
        if(teclats.existeixTeclat(nomTeclat))
        {
            throw new JaExisteixException("ja existeix un teclat amb nom " + nomTeclat);
        }

        //Busca la frequencia de simbols
        FrequenciesSimbols freq = freqs.getFrequenciesSimbols(nomFrequencia);

        //Obte l'alfabet de freq
        Alfabet alfabet = freq.getAlfabet();

        //Crea un layout
        Layout layout = new Layout(alfabet.getSize());

        char[] assignacio = null;

        if(metode.equals("BranchAndBound"))
        {
            assignacio = executaAlgorisme(new QAP(), alfabet, freq, layout);
        }
        else if(metode.equals("CercaLocal"))
        {
            assignacio = executaAlgorisme(new CercaLocal(iteracions), alfabet, freq, layout);
        }
        else
        {
            throw new NoExisteixMetodeException("no existeix el metode " + metode + " per generar un teclat");
        }

        Teclat teclat = new Teclat(alfabet, freq, layout, assignacio, nomTeclat);
        teclats.afegeixTeclat(teclat);
        CP.desaTeclats(teclats);
    }

    /**
     * Retorna un teclat en un format visualitzable per text / consola
     * @return Una representacio en text del teclat
     * @throws NoTrobatException si no es pot trobar el teclat a visualitzar
     */
    public String visualitzaTeclat(String nom) throws NoTrobatException
    {
        return teclats.getTeclat(nom).toString();
    }

    /**
     * Retorna el teclat en un format relativament facil de processar per visualitzar-lo
     * @param nom el nom del teclat
     * @return
     * <b>Teclat principal</b>    <br>
     * Primera  string: <b>l'assignacio</b>, tot junt sense cap espai                  <br>
     * Segona   string: <b>l'assignacio especial</b>, amb elements separats per <b>#</b>      <br>
     * Tercera  string: les <b>posicions de les tecles</b>, separades per <b>#</b>. Format: posX1#posY1#posX2#posY2#...   <br>
     * Quarta   string: les <b>posicions</b> de les tecles de <b>l'assignacio especial</b>, en el mateix format  <br>
     * Cinquena string: <b>amplades</b> de les tecles de <b>l'assignacio especial</b> (essent 1.0 el normal), separades per <b>#</b>     <br>
     * <br>
     * <b>Teclat secundari</b>    <br>
     * Sisena  string: <b>l'assignacio</b> del <b>teclat secundari</b>, tot junt sense cap espai  <br>
     * Setena  string: <b>l'assignacio especial</b> del <b>teclat secundari</b>, amb elements separats per #  <br>
     * Vuitena string: les <b>posicions</b> de les <b>tecles</b> del <b>teclat secundari</b>, separades per <b>#</b>. Format: posX1#posY1#posX2#posY2#...       <br>
     * Novena  string: les <b>posicions</b> de les tecles de <b>l'assignacio especial</b> del <b>teclat secundari</b>, en el mateix format      <br>
     * Desena  string: <b>amplades</b> de les <b>tecles</b> de <b>l'assignacio especial</b> del <b>teclat secundari</b> (essent 1.0 el normal), separades per <b>#</b>      <br>
     * @throws NoTrobatException si no es troba el teclat
     */
    public ArrayList<String> getInfoTeclat(String nom) throws NoTrobatException
    {
        return teclats.getTeclat(nom).toStringProcessable();
    }

    /**
     * Retorna una frequencia de simbols en un format facil de visualitzar per text / consola
     * @return Una representacio en text de la frequencia de simbols (una especie de taula)
     * @throws NoTrobatException si no es pot trobar la frequencia
     */
    public String visualitzaFrequencies(String nom) throws NoTrobatException
    {
        return freqs.getFrequenciesSimbols(nom).toString();
    }

    /**
     * Retorna un alfabet en format text
     * @return Tots els simbols de l'alfabet seguits, sense espais
     * @throws NoTrobatException si no es pot trobat l'alfabet
     */
    public String visualitzaAlfabet(String nom) throws NoTrobatException
    {
        return alfabets.getAlfabet(nom).toString();
    }

    /**
     * Retorna el contingut d'unes dades
     * @return El contingut en el format que aquestes dades segueixin
     * @throws NoTrobatException si no es poden trobar les dades
     */
    public String visualitzaDades(String nom) throws NoTrobatException
    {
        return dades.getDadesParaules(nom).toString();
    }

    /**
     * Esborra un teclat del sistema
     * @param nom nom del teclat a esborrar
     * @throws NoTrobatException si no es pot trobar el teclat a esborrar
     */
    public void esborraTeclat(String nom) throws NoTrobatException
    {
        teclats.esborraTeclat(nom);
        CP.desaTeclats(teclats);
    }

    /**
     * Permet modificar el nom d'un teclat
     * @param nomTeclat nom del teclat a modificar
     * @param nomNou nom nou del teclat
     * @throws NoTrobatException si el teclat a modificar no es pot trobar
     * @throws JaExisteixException si ja existeix un teclat amb nomNou
     */
    public void modificaTeclat(String nomTeclat, String nomNou) throws NoTrobatException, JaExisteixException
    {
        Teclat tec = Teclat.copia(teclats.getTeclat(nomTeclat));
        tec.setNom(nomNou);
        teclats.setTeclat(tec, nomTeclat);
        CP.desaTeclats(teclats);
    }

    /**
     * Permet modificar els caracters assignats a un teclat. Assumeix una assignacio correcta.
     * @param nomTeclat nom del teclat a modificar
     * @param assignacioNova caracters de la nova assignacio. S'assumeix que son els mateixos, pero en qualsevol altre ordre.
     * @throws NoTrobatException si el teclat a modificar no es pot trobar
     * @throws JaExisteixException mai passa perque nomes es modifica l'assignacio
     */
    public void modificaTeclat(String nomTeclat, char[] assignacioNova) throws NoTrobatException, JaExisteixException
    {
        Teclat tec = Teclat.copia(teclats.getTeclat(nomTeclat));
        tec.setAssignacio(assignacioNova);
        teclats.setTeclat(tec, nomTeclat);
        CP.desaTeclats(teclats);
    }

    /**
     * Afegeix un alfabet al sistema
     * @param nom nom per l'alfabet
     * @param simbols els simbols que contindra, sense repeticions
     * @throws JaExisteixException si algun simbol surt mes d'un cop
     * 
     */
    public void afegeixAlfabet(String nom, ArrayList<Character> simbols) throws JaExisteixException
    {
        Alfabet alfabet = new Alfabet(new HashSet<Character>(simbols), nom);
        alfabets.afegeixAlfabet(alfabet);
        CP.desaAlfabets(alfabets);
    }

    /**
     * Esborra un alfabet del sistema
     * @param nom nom de l'alfabet a esborrar
     * @throws NoTrobatException si l'alfabet a esborrar no es pot trobar
     */
    public void esborraAlfabet(String nom) throws NoTrobatException
    {
        alfabets.esborraAlfabet(nom);
        CP.desaAlfabets(alfabets);
    }

    /**
     * Permet modificar un alfabet
     * @param nom nom de l'alfabet a modificar
     * @param nomNou nom nou de l'alfabet (donar el mateix si no es canvia)
     * @param simbolsNous tots els simbols que formaran l'alfabet modificat
     * @throws NoTrobatException si no es troba l'alfabet a modificar
     * @throws JaExisteixException si ja existeix un alfabet amb nomNou
     */
    public void modificaAlfabet(String nom, String nomNou, ArrayList<Character> simbolsNous) throws NoTrobatException, JaExisteixException
    {
        Alfabet alfabetNou = new Alfabet(new HashSet<Character>(simbolsNous), nomNou);
        alfabets.setAlfabet(nom, alfabetNou);
        CP.desaAlfabets(alfabets);
    }

    /**
     * Permet modificar el nom d'una frequencia de simbols
     * @param nom nom de la frequencia a modificar
     * @param nomNou el nom que es vol donar
     * @throws NoTrobatException si la frequencia a modificar no es pot trobar
     * @throws JaExisteixException si ja existeix una frequencia amb nomNou
     */
    public void modificaFrequencies(String nom, String nomNou) throws NoTrobatException, JaExisteixException
    {
        FrequenciesSimbols freq = freqs.getFrequenciesSimbols(nom);
        //Crea copia per si tenim algun problema, no modificar l'original
        FrequenciesSimbols nova = FrequenciesSimbols.copia(freq);
        nova.setNom(nomNou);
        freqs.setFrequencies(nom,nova);
        CP.desaFrequencies(freqs);
    }

    /**
     * Afegeix una frequencia de simbols al sistema
     * @param nom nom per la frequencia de simbols
     * @param nomsDades els noms de les dades que es volen fer servir per crear la frequencia
     * @throws NoTrobatException si alguna de les dades no s'han trobat
     * @throws JaExisteixException si ja existeix una frequencia de simbols amb aquest nom
     */
    public void afegeixFreqSimbols(String nom, ArrayList<String> nomsDades) throws NoTrobatException, JaExisteixException
    {
        //Busca les dades donades a noms
        ArrayList<DadesParaules> dadesConstructora = new ArrayList<DadesParaules>(nomsDades.size());

        for(int i = 0; i < nomsDades.size(); ++i)
        {
            String auxNom = nomsDades.get(i);
            DadesParaules dada = dades.getDadesParaules(auxNom);
            dadesConstructora.add(dada);
        }

        FrequenciesSimbols frequenciesSimbols = new FrequenciesSimbols(dadesConstructora, nom);
        freqs.afegeixFrequencies(frequenciesSimbols);
        CP.desaFrequencies(freqs);
    }

    /**
     * Esborra una frequencia de simbols del sistema
     * @param nom nom de la frequencia a esborrar
     * @throws NoTrobatException si la frequencia a esborrar no existeix
     */
    public void esborraFreqSimbols(String nom) throws NoTrobatException
    {
        freqs.eliminaFrequencies(nom);
        CP.desaFrequencies(freqs);
    }

    /**
     * Importa un text des d'un fitxer
     * @param nom nom del text que es vol crear
     * @param path path del fitxer que es vol fer servir 
     * @throws IOException si el fitxer no es pot obrir o llegir be
     * @throws FileNotFoundException si el fitxer no es pot trobar
     * @throws JaExisteixException si ja existeixen dades amb aquest nom
     */
    public void afegeixText(String nom, String path) throws IOException, FileNotFoundException, JaExisteixException
    {
        //Llegeix-lo d'un fitxer (es dona el path)
        StringBuilder contingut = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String linia;
        while ((linia = br.readLine()) != null) 
        {
            contingut.append(linia).append("\n");
        }

        br.close();

        String resultat = contingut.toString();

        Text text = new Text(nom, resultat);
        dades.addDadesParaules(text);
        CP.desaDades(dades);
    }

    /**
     * Importa un text directament
     * @param nom nom del text que es vol crear
     * @param contingut contingut del text
     * @throws JaExisteixException si ja existeixen unes dades amb aquest nom
     */
    public void afegeixTextDirectament(String nom, String contingut) throws JaExisteixException
    {
        Text text = new Text(nom, contingut);
        dades.addDadesParaules(text);
        CP.desaDades(dades);
    }

    /**
     * Afegeix una llista de paraules amb frequencies des d'un fitxer 
     * @param nom nom de la llista de paraules que es vol crear
     * @param path path del fitxer que es vol fer servir
     * @throws IOException si el fitxer no es pot obrir o llegir be
     * @throws FileNotFoundException si no es pot trobar el fitxer a path
     * @throws JaExisteixException si ja existeixen unes dades amb aquest nom
     * @throws FormatIncorrecte si el format de la llista de paraules es incorrecte
     */
    public void afegeixLlistaDeParaules(String nom, String path) throws IOException, FileNotFoundException, JaExisteixException, FormatIncorrecte
    {
        //Llegeix-lo d'un fitxer (es dona el path)
        StringBuilder contingut = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String linia;
        while ((linia = br.readLine()) != null) 
        {
            contingut.append(linia).append("\n");
        }

        br.close();

        String resultat = contingut.toString();

        FreqParaules fr = new FreqParaules(nom,resultat);
        dades.addDadesParaules(fr);
        CP.desaDades(dades);
    }

    /**
     * Afegeix una llista de paraules amb frequencies directament
     * @param nom el nom de la llista de paraules a afegir
     * @param contingut la llista en format text
     * @throws FormatIncorrecte si el format de la llista de paraules es incorrecte
     * @throws JaExisteixException si ja existeix una llista de paraules amb aquest nom
     */
    public void afegeixLlistaDeParaulesDirectament(String nom, String contingut) throws FormatIncorrecte, JaExisteixException
    {
        FreqParaules fr = new FreqParaules(nom,contingut);
        dades.addDadesParaules(fr);
        CP.desaDades(dades);
    }

    /**
     * Permet modificar unes dades
     * @param nom nom de les dades a modificar
     * @param nomNou nom nou (si no es vol modificar, donar el mateix)
     * @param nouContingut contingut de les dades modificades
     * @throws NoTrobatException si no es poden trobar les dades a modificar
     * @throws JaExisteixException si ja existeixen unes dades amb el nom nou
     * @throws FormatIncorrecte si s'espera un format de llista de paraules amb frequencies correcte, i no es rep
     */
    public void modificaDadesParaules(String nom, String nomNou, String nouContingut) throws NoTrobatException, JaExisteixException, FormatIncorrecte
    {
        DadesParaules dada = dades.getDadesParaules(nom);
        DadesParaules copia = dada.copy();
        copia.setNom(nomNou);
        copia.setContingut(nouContingut);
        dades.setDadesParaules(nom,copia);
        CP.desaDades(dades);
    }

    /**
     * Esborra unes dades del sistema
     * @param nom nom de les dades a esborrar
     * @throws NoTrobatException si no s'han pogut trobar les dades a esborrar
     */
    public void esborraDada(String nom) throws NoTrobatException
    {
        dades.removeDadesParaules(nom);
        CP.desaDades(dades);
    }

    /**
     * Retorna un nombre que representa la distancia recorreguda en escriure el text que es doni en el teclat que es doni
     * @param nomTeclat nom del teclat que es vol avaluar
     * @param nomText nom del text que es vol fer servir
     * @return La distancia recorreguda en escriure el text al teclat, en format String
     * @throws NoTrobatException si no s'ha trobat el teclat a avaluar o el text per avaluar
     */
    public String avaluaTeclat(String nomTeclat, String nomText) throws NoTrobatException
    {
        Teclat teclat = teclats.getTeclat(nomTeclat);
        Text text = (Text)dades.getDadesParaules(nomText);
        Double res = teclat.avalua(text);
        return String.format("%.2f", res);
    }

    /**
     * Obte els noms dels teclats que hi ha al sistema
     * @return Una llista amb els noms
     */
    public ArrayList<String> getNomsTeclats()
    {
        return teclats.getNoms();
    }

    /**
     * Obte els noms dels alfabets que hi ha al sistema
     * @return Una llista amb els noms
     */
    public ArrayList<String> getNomsAlfabets()
    {
        return alfabets.getNoms();
    }

    /**
     * Obte els noms de les dades (texts, llistes de paraules amb frequencies) que hi ha al sistema
     * @return Una llista amb els noms
     */
    public ArrayList<String> getNomsDades()
    {
        return dades.getNoms();
    }

    /**
     * Obte els noms dels texts que hi ha al sistema
     * @return Una llista amb els noms
     */
    public ArrayList<String> getNomsTexts()
    {
        return dades.getNomsTexts();
    }

    /**
     * Obte els noms de les frequencies entre simbols que hi ha al sistema
     * @return Una llista amb els noms
     */
    public ArrayList<String> getNomsFreqs()
    {
        return freqs.getNoms();
    }

    /**
     * Executa un algorisme de generacio de teclat
     * @param alg l'algorisme a executar
     * @param alfabet l'alfabet que es fara servir
     * @param freq les frequencies entre els simbols de l'alfabet donat
     * @param layout el layout del teclat a generar
     * @return l'assignacio de caracters de l'alfabet per un teclat
     * @throws Exception
     */
    private char[] executaAlgorisme(AlgorismeGeneracio alg, Alfabet alfabet, FrequenciesSimbols freq, Layout layout) throws Exception
    {
        return alg.executa(alfabet,freq,layout);
    }
}