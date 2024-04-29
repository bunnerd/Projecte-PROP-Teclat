package edu.upc.prop.cluster21_4.domini;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;

/**
 * Classe per representar la disposicio de tecles d'un teclat
 * @author Jon Campillo
 */

public class Layout implements Serializable
{
    //Amplada estandard de tecla
    private static final double AMPLE = 1.0;
    //Altura estandard de tecla
    private static final double ALT = 1.0;
    private static final int TECLES_ESPECIALS = 6;
    private static final String[] ASSIGNACIO_ESPECIAL = {"123","maj","espai",",",".","<-"};
    private static final int MIDA_NORMAL_FILA = 10;

    private static final double AMPLADA_MENU = 1.5*AMPLE;
    private static final double AMPLADA_MAJUS = 1.5*AMPLE;
    private static final double AMPLADA_ESPAI = 4.0*AMPLE;
    private static final double AMPLADA_COMA = 1.0*AMPLE;
    private static final double AMPLADA_PUNT = 1.0*AMPLE;
    private static final double AMPLADA_ESBORRAR = 1.0*AMPLE;

    private static final double[] AMPLADES = {AMPLADA_MENU, AMPLADA_MAJUS, AMPLADA_ESPAI, AMPLADA_COMA, AMPLADA_PUNT, AMPLADA_ESBORRAR};

    private static final char[] ASSIGNACIO_SECUNDARI = {'0','1','2','3','4','5','6','7','8','9',
                                                        '@','#','\u20AC','\u005F','&','-','+','(',')','/',
                                                        '*','"','\'',':',';','!','?','\u00B4' /*´*/,'`','%'};
    private static double[] POSX_SECUNDARI = {0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,
                                              0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,
                                              0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0,
                                                    0.0,1.5,7.0,8.0,9.0};
    private static double[] POSY_SECUNDARI = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,
                                              1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,
                                              2.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,2.0,
                                                    3.0,3.0,3.0,3.0,3.0};
    private static final String[] ASSIGNACIO_SECUNDARI_ESPECIAL = {"abc","espai", ",", ".", "<-"};
    private static final double[] AMPLADES_SECUNDARI = {1.5*AMPLE,5.5*AMPLE,1.0*AMPLE,1.0*AMPLE,1.0*AMPLE};

    private int nTecles;
    private double distancies[][]; //nTecles x nTecles
    private int files;
    private int lletresPerFila[]; //L'ultima fila te [menu especial], [majus] [espai], [,] i [.], [esborrar]
    private double posX[][];
    private double posY[][];

    /**
     * Constructora
     * @param nSimbols nombre de simbols de l'alfabet que fa servir el teclat
     */
    public Layout(int nSimbols)
    {
        nTecles = nSimbols;

        //Farem files de no mes de MIDA_NORMAL_FILA tecles
        files = nSimbols/MIDA_NORMAL_FILA + 1; //+1 per l'ultima fila (especial)
        
        //Si no quadren els simbols, creem una ultima fila mes petita
        if(nSimbols%MIDA_NORMAL_FILA != 0) files++;

        lletresPerFila = new int[files];
        for(int i = 0; i < lletresPerFila.length-2; ++i)
        {
            lletresPerFila[i] = MIDA_NORMAL_FILA;
        }

        //Penultima fila
        if(files>1)
        {
            if(nSimbols%MIDA_NORMAL_FILA != 0) //Menys simbols del normal
                lletresPerFila[lletresPerFila.length-2] = nSimbols%MIDA_NORMAL_FILA;
            else //Coincideix que te els mateixos (donaria 0 el modul, ho fem a part)
                lletresPerFila[lletresPerFila.length-2] = MIDA_NORMAL_FILA;
        }

        //Fila especial [menu especial], [majus] [espai], [,] i [.], [esborrar]
        lletresPerFila[lletresPerFila.length-1] = TECLES_ESPECIALS; 
        calculaDistancies();
    }

    /**
     * 
     * @return El nombre de simbols de l'alfabet
     */
    public int nombreDeTecles()
    {
        return nTecles;
    }

    /**
     * Assumeix 0 <= p1 < nombreDeTecles()  i  0 <= p2 < nombreDeTecles()
     * @param p1 posicio 1
     * @param p2 posicio 2
     * @return La distancia entre p1 i p2
     */
    public double distanciaEntre(int p1, int p2)
    {
        return distancies[p1][p2];
    }

    /**
     * 
     * @return El nombre de files del layout
     */
    public int files()
    {
        return files;
    }

    /**
     * Assumeix 0 <= fila < files()
     * @param fila fila per la que es vol consultar
     * @return El nombre de simbols te la fila donada
     */
    public int lletres(int fila)
    {
        return lletresPerFila[fila];
    }

    /**
     * 
     * @return L'amplada normal (de referencia) d'una tecla.
     */
    public static double ampladaTecla()
    {
        return AMPLE;
    }

    /**
     * 
     * @return L'altura normal (de referencia) d'una tecla
     */
    public static double altTecla()
    {
        return ALT;
    }

    /**
     * 
     * @return El nombre de tecles de la ultima fila, on tenim totes les tecles especials
     */
    public static int nombreDeTeclesEspecials()
    {
        return TECLES_ESPECIALS;
    }

    /**
     * 
     * @return La quantitat de tecles a una fila normal que esta plena
     */
    public static int getMidaNormalDeFila()
    {
        return MIDA_NORMAL_FILA;
    }

    /**
     * 
     * @return L'assignacio de tecles de la fila especial (ultima)
     */
    public static String[] getAssignacioFilaEspecial()
    {
        return ASSIGNACIO_ESPECIAL;
    }

    /**
     * Copiadora de layouts
     * @param altre layout a copiar
     * @return Una deep copy del layout donat
     */
    public static Layout copia(Layout altre)
    {
        return new Layout(altre.nTecles);
    }

    /**
     * Retorna informacio del layout principal en un format facilment processable
     * @return
     * Primera  string: <b>l'assignacio especial</b>, amb elements separats per <b>#</b>      <br>
     * Segona  string: les <b>posicions de les tecles</b>, separades per <b>#</b>. Format: posX1#posY1#posX2#posY2#...   <br>
     * Tercera   string: les <b>posicions</b> de les tecles de <b>l'assignacio especial</b>, en el mateix format  <br>
     * Quarta string: <b>amplades</b> de les tecles de <b>l'assignacio especial</b> (essent 1.0 el normal), separades per <b>#</b>     <br>
     */
    public ArrayList<String> toStringPrincipal()
    {
        ArrayList<String> resultat = new ArrayList<String>();

        //Assignacio especial, separat tot per #
        String assigEsp = "";
        for(String elem : ASSIGNACIO_ESPECIAL)
        {
            assigEsp = assigEsp.concat(elem+"#");
        }
        resultat.add(assigEsp);

        //Posicions normals
        String pos = "";
        for(int i = 0; i < lletresPerFila.length-1; ++i)
        {
            for(int j = 0; j < lletresPerFila[i]; ++j)
            {
                //Es treu l'offset per donar la posicio del canto superior esquerre i no del centre
                pos = pos.concat((posX[i][j]-AMPLE/2.0)+"#"+(posY[i][j]-ALT/2.0)+"#");
            }
        }
        resultat.add(pos);

        //Posicions fila especial
        String posEsp = "";
        int i = lletresPerFila.length-1;
        for(int j = 0; j < lletresPerFila[i]; ++j)
        {
            //Es treu l'offset per donar la posicio del canto superior esquerre i no del centre
            posEsp = posEsp.concat((posX[i][j]-AMPLADES[j]/2)+"#"+(posY[i][j]-ALT/2.0)+"#");
        }
        resultat.add(posEsp);

        //Amplades fila especial
        String amplada = "";
        for(double ampl : AMPLADES)
        {
            amplada = amplada.concat(ampl+"#");
        }
        resultat.add(amplada);

        return resultat;
    }

    /**
     * Retorna informacio del layout secundari en un format facilment processable
     * @return
     * Primera  string: <b>l'assignacio</b> del <b>layout secundari</b>, tot junt sense cap espai  <br>
     * Segona   string: <b>l'assignacio especial</b> del <b>layout secundari</b>, amb elements separats per #  <br>
     * Tercera string: les <b>posicions</b> de les <b>tecles</b> del <b>layout secundari</b>, separades per <b>#</b>. Format: posX1#posY1#posX2#posY2#...       <br>
     * Quarta  string: les <b>posicions</b> de les tecles de <b>l'assignacio especial</b> del <b>layout secundari</b>, en el mateix format      <br>
     * Cinquena  string: <b>amplades</b> de les <b>tecles</b> de <b>l'assignacio especial</b> del <b>layout secundari</b> (essent 1.0 el normal), separades per <b>#</b>      <br>
     */
    public ArrayList<String> toStringSecundari()
    {
        ArrayList<String> resultat = new ArrayList<String>();

        //Afegeix assignacio normal
        String assigNormal = "";
        for(Character c : ASSIGNACIO_SECUNDARI)
        {
            assigNormal = assigNormal.concat(Character.toString(c));
        }
        resultat.add(assigNormal);

        //Afegeix assignacio especial
        String assigEspecial = "";
        for(String s : ASSIGNACIO_SECUNDARI_ESPECIAL)
        {
            assigEspecial = assigEspecial.concat(s + "#");
        }
        resultat.add(assigEspecial);

        //Afegeix coordenades normals
        String coordsNormal = "";
        for(int i = 0; i < 30; ++i)
        {
            coordsNormal = coordsNormal.concat(POSX_SECUNDARI[i] + "#" + POSY_SECUNDARI[i] + "#");
        }
        resultat.add(coordsNormal);

        //Afegeix coordenades especials
        String coordsEspecial = "";
        for(int i = 30; i < POSX_SECUNDARI.length; ++i)
        {
            coordsEspecial = coordsEspecial.concat(POSX_SECUNDARI[i] + "#" + POSY_SECUNDARI[i] + "#");
        }
        resultat.add(coordsEspecial);

        //Afegeix amplades de les tecles especials
        String ampladesEspecial = "";
        for(double d : AMPLADES_SECUNDARI)
        {
            ampladesEspecial = ampladesEspecial.concat(d + "#");
        }
        resultat.add(ampladesEspecial);
        return resultat;
    }

    private void calculaDistancies()
    {
        //Coordenades de les posicions centrals de cada tecla
        posX = new double[lletresPerFila.length][];
        posY = new double[lletresPerFila.length][];
        distancies = new double[nTecles+TECLES_ESPECIALS][nTecles+TECLES_ESPECIALS];

        for(int i = 0; i < posX.length; ++i)
        {
            posX[i] = new double[lletresPerFila[i]];
            posY[i] = new double[lletresPerFila[i]];

            for(int j = 0; j < posX[i].length; ++j)
            {
                posX[i][j] = j*AMPLE + AMPLE/2;
                posY[i][j] = i*AMPLE + ALT/2;
            }
        }

        /*
         * Cas menys tecles, volem centrar la fila mes curta
         *   ..........
         *   ..........
         *     ......    <- fila curta
         * 
         */
        if(files > 1) //Pot ser buit
        {
            int teclesMenys = lletresPerFila[lletresPerFila.length-2];
            double xInicial = (MIDA_NORMAL_FILA-teclesMenys)/2.0;

            for(int j = 0; j < posX[lletresPerFila.length-2].length; ++j)
            {
                posX[lletresPerFila.length-2][j] = xInicial+j*AMPLE+AMPLE/2;
            }
        }

        /*
         * Cas de la fila especial, que te les tecles:
         * [menu especial], [majus] [espai], [,] i [.], [esborrar] 
         * Majus ocupa 1.5 tecles
         * L'espai ocupa l'amplada de 4 tecles
         * Esborrar ocupa 1.5 tecles
         */
        double posAct = 0.0;
        posX[lletresPerFila.length-1][0] = AMPLADA_MENU/2.0;  posAct += AMPLADA_MENU; //Menu especial
        posX[lletresPerFila.length-1][1] = AMPLADA_MAJUS/2.0 + posAct; posAct+= AMPLADA_MAJUS;  //Majuscules
        posX[lletresPerFila.length-1][2] = AMPLADA_ESPAI/2.0 + posAct; posAct+= AMPLADA_ESPAI;  //Espai
        posX[lletresPerFila.length-1][3] = AMPLADA_COMA/2.0  + posAct; posAct+= AMPLADA_COMA; //Coma
        posX[lletresPerFila.length-1][4] = AMPLADA_PUNT/2.0  + posAct; posAct+= AMPLADA_PUNT; //Punt
        posX[lletresPerFila.length-1][5] = AMPLADA_ESBORRAR/2.0 + posAct; posAct+=AMPLADA_ESBORRAR; //Esborrar

        //Calcul de les distancies
        int act = 0;
        for(int i = 0; i < posX.length; ++i)
        {
            for(int j = 0; j < posX[i].length; ++j)
            {
                //Calculem distancia entre la tecla act i la resta de tecles
                int actAux = 0;

                for(int k = 0; k < posX.length; ++k)
                {
                    for(int l = 0; l < posX[k].length; ++l)
                    {
                        double x = posX[k][l]-posX[i][j];
                        double y = posY[k][l]-posY[i][j];
                        distancies[act][actAux] = Math.sqrt(x*x+y*y);
                        ++actAux;
                    }
                }
                ++act;
            }
        }
    }
}