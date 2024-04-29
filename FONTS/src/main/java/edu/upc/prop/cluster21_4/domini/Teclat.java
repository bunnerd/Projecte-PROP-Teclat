package edu.upc.prop.cluster21_4.domini;

import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

/**
 * Classe per representar un teclat
 * @author Jon Campillo
 */
public class Teclat implements Serializable
{
    private Alfabet alfabet;
    private FrequenciesSimbols freq; 
    private Layout layout;
    private char assignacio[];
    private String nom;

    private static final char SHIFT = '\u240F';
    private static final char SPACE = '\u0020';

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

    public Teclat()
    {
        alfabet = null;  freq = null;  layout = null;  assignacio = null;  nom = null;
    }

    /**
     * Constructora amb elements necessaris
     * @param alfabet
     * @param freq frequencies entre simbols de l'alfabet donat
     * @param layout un layout de la mateixa mida que l'alfabet donat
     * @param assignacio caracters de l'alfabet, on el caracter a la posicio i estara a la posicio i del layout
     */
    public Teclat(Alfabet alfabet, FrequenciesSimbols freq, Layout layout, char assignacio[])
    {
        this.alfabet = alfabet.copiar();        
        this.freq = FrequenciesSimbols.copia(freq);
        this.layout = Layout.copia(layout);
        this.assignacio = new char[assignacio.length];
        for(int i = 0; i < assignacio.length; ++i)
        {
            this.assignacio[i] = assignacio[i];
        }
        this.nom = null;
    }

    /**
     * Constructora amb elements necessaris i el nom
     * @param alfabet
     * @param freq frequencies entre simbols de l'alfabet donat
     * @param layout un layout de la mateixa mida que l'alfabet donat
     * @param assignacio caracters de l'alfabet, on el caracter a la posicio i estara a la posicio i del layout
     * @param nom el nom del teclat a crear
     * @throws JaExisteixException si ja existeix un teclat amb aquest nom
     */
    public Teclat(Alfabet alfabet, FrequenciesSimbols freq, Layout layout, char assignacio[], String nom)
    {
        this.alfabet = alfabet.copiar();           
        this.freq = FrequenciesSimbols.copia(freq);
        this.layout = Layout.copia(layout);
        this.assignacio = new char[assignacio.length];
        for(int i = 0; i < assignacio.length; ++i)
        {
            this.assignacio[i] = assignacio[i];
        }
        this.nom = nom;
    }

    /**
     * Copiadora de teclats
     * @param t el teclat a copiar
     * @return Una deep copy del teclat t
     */
    public static Teclat copia(Teclat t)
    {
        Alfabet copiaAlfabet = t.getAlfabet().copiar();
        FrequenciesSimbols copiaFreq = FrequenciesSimbols.copia(t.getFreq());
        Layout copiaLayout = Layout.copia(t.getLayout());
        char copiaAssignacio[] = new char[t.getAssignacio().length];
        for(int i = 0; i < copiaAssignacio.length; ++i)
            copiaAssignacio[i] = t.getAssignacio()[i];

        return new Teclat(copiaAlfabet,copiaFreq,copiaLayout,copiaAssignacio,t.getNom());
    }

    public Alfabet getAlfabet()
    {
        return alfabet;
    }

    public FrequenciesSimbols getFreq()
    {
        return freq;
    }

    public Layout getLayout()
    {
        return layout;
    }

    public char[] getAssignacio()
    {
        return assignacio;
    }

    public String getNom()
    {
        return nom;
    }

    public void setAlfabet(Alfabet alfabet)
    {
        this.alfabet = alfabet;
    }

    public void setFreq(FrequenciesSimbols freq)
    {
        this.freq = freq;
    }

    public void setLayout(Layout layout)
    {
        this.layout = layout;
    }

    public void setAssignacio(char[] assignacio)
    {
        this.assignacio = assignacio;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * Reimplementa toString
     * @return El teclat en format visible per consola
     */
    @Override
    public String toString()
    {
        int simbolsPerFila[] = new int[layout.files()-1];

        //Guarda quants simbols te cada fila
        for(int i = 0; i < simbolsPerFila.length; ++i)
        {
            simbolsPerFila[i] = layout.lletres(i);
        }

        String result = "";
        int comptador = 0;

        //Concatena les files normals
        for(int i = 0; i < simbolsPerFila.length; ++i)
        {
            //Centra l'ultima fila normal si te menys caracters que la resta (afegeix espais)
            if(simbolsPerFila[i] < Layout.getMidaNormalDeFila())
            {
                int espais = (Layout.getMidaNormalDeFila()-simbolsPerFila[i])/2;

                for(int j = 0; j < espais; ++j)
                    result = result.concat("   "); //3 espais, equiv. a '[a]'
            }

            //Afegeix els simbols amb format [s]
            for(int j = 0; j < simbolsPerFila[i]; ++j)
            {
                result = result.concat(formatSimbol(Character.toString(assignacio[comptador])));
                comptador++;
            }
            //Fi de fila
            result = result.concat("\n");
        }

        //Concatena fila especial
        String filaEspecial[] = Layout.getAssignacioFilaEspecial();
        for(String tecla : filaEspecial)
        {
            result = result.concat(formatSimbol(tecla));
        }

        return result;
    }

    /**
     * Retorna el teclat en un format relativament facil de processar per visualitzar-lo
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
     */
    public ArrayList<String> toStringProcessable()
    {
        ArrayList<String> result = new ArrayList<String>();
        
        //Afegeix assignacio
        String assig = "";
        for(Character c : assignacio)
        {
            assig = assig.concat(Character.toString(c));
        }
        result.add(assig);

        //Afegeix assignacio especial, posicions normals, posicions especials i amplades especials
        ArrayList<String> infoLayout = layout.toStringPrincipal();
        for(String elem : infoLayout)
            result.add(elem); 
            
        //Afegeix dades del teclat secundari
        ArrayList<String> infoSecundari = layout.toStringSecundari();
        for(String elem : infoSecundari)
            result.add(elem);

        return result;
    }

    /**
     * Intercanvia les posicions dels simbols c1 i c2
     * @param c1 primer simbol
     * @param c2 segon simbol
     * @throws NoTrobatException si algun dels dos simbols no es al teclat
     */
    public void intercanviaSimbols(Character c1, Character c2) throws NoTrobatException
    {
        int i1 = busca(c1);
        int i2 = busca(c2);

        if(i1 == -1)
            throw new NoTrobatException("no s'ha trobat el simbol " + c1 + " al teclat " + nom);
        else if(i2 == -1)
            throw new NoTrobatException("no s'ha trobat el simbol " + c2 + " al teclat " + nom);

        assignacio[i1] = c2;
        assignacio[i2] = c1;
    }

    /**
     * Calcula la distancia total recorreguda a l'hora d'escriure "text" amb aquest Teclat. Ignora els simbols que no depenen de l'assignacio del teclat.
     * @param text text que es fara servir per avaluar el teclat
     * @return La distancia total recorreguda per escriure el text
     */
    public double avalua(Text text)
    {
        //Processa simbols un a un, acumula la distancia total
        double distancia = 0.0;

        //Mapeig dels caracters als indexs per fer servir distanciaEntre
        HashMap<Character,Integer> map = new HashMap<Character,Integer>();

        for(int i = 0; i < assignacio.length; ++i)
        {
            map.put(assignacio[i], i);
        }

        //Caracters especials
        int offset = assignacio.length;
        map.put(SHIFT,offset+1);
        map.put(SPACE,offset+2);
        map.put(',',offset+3);
        map.put('.',offset+4);

        ArrayList<Character> formatText = new ArrayList<Character>(text.size());

        processaText(text,formatText);

        if(formatText.size() <= 1) return 0.0;

        //Mirem parells de caracters i la distancia entre ells
        char c1, c2;
        c1 = formatText.get(0);

        for(int i = 1; i < formatText.size(); ++i)
        {
            c2 = formatText.get(i);
            
            if(map.containsKey(c1) && map.containsKey(c2))
            {
                distancia += layout.distanciaEntre(map.get(c1), map.get(c2));
                c1 = c2;
            }
        }

        return distancia;
    }

    /**
     * Converteix un Text a un vector de simbols, on coses com les majuscules se separen en SHIFT+tecla o els accents se separen en ACCENT+tecla
     * @param text text a processar
     * @param format vector on es vol deixar el text processat
     */
    private void processaText(Text text, ArrayList<Character> format)
    {
        for(int i = 0; i < text.numSimbol(); ++i)
        {
            char ch = text.getSimbol(i);
            if(Character.isUpperCase(ch))
            {
                //Afegim el shift (mayus)
                format.add(SHIFT);
                ch = Character.toLowerCase(ch);
            }

            if(acentos.containsKey(ch))
            {
                //Afegim l'accent i el caracter basic
                format.add(acentos.get(ch)[0]);
                format.add(acentos.get(ch)[1]);
            }
            else
            {
                //Afegim nomes el caracter basic
                format.add(ch);
            }
        }
    }

    /**
     * Posa una string entre brackets que simulen una tecla
     * @param simbol
     * @return [simbol]
     */
    private String formatSimbol(String simbol)
    {
        return "[" + simbol + "]";
    }

    /**
     * Busca un caracter a l'assignacio
     * @param c caracter que es vol buscar
     * @return La posicio del caracter a l'assignacio, -1 si no hi es
     */
    private int busca(char c)
    {
        for(int i = 0; i < assignacio.length; ++i)
        {
            if(assignacio[i] == c) return i;
        }
        return -1;
    }
}