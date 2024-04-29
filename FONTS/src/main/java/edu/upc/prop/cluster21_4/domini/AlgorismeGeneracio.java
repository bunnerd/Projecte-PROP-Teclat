package edu.upc.prop.cluster21_4.domini;

/**
 * Interficie que defineix la funcio necessaria per generar una assignacio a un teclat.
 * @author Sandra Vázquez
 */
public interface AlgorismeGeneracio
{
    /**
     * Executa un algorisme que retorna una assignació de símbols a posicions del teclat
     * @param alfabet l'alfabet pel qual es crearà el teclat
     * @param freq la freqüència entre els símbols 
     * @param layout el layout que tindrà el teclat
     * @return Un array de chars amb la relació índex -> posició, valor -> tecla
     * @throws Exception
     */
    public char[] executa(Alfabet alfabet, FrequenciesSimbols freq, Layout layout) throws Exception;
}