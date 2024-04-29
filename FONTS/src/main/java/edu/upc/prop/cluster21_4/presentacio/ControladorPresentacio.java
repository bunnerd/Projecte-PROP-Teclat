package edu.upc.prop.cluster21_4.presentacio;

import edu.upc.prop.cluster21_4.domini.ControladorDomini;
import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.util.*;

public class ControladorPresentacio {
    private ControladorDomini ctrlDomini;
    private VistaPrincipal vistaPrincipal;
    private VistaSecundaria vistaSecundaria;
    private VistaTerciaria vistaTerciaria;

    public ControladorPresentacio() throws Exception {
        ctrlDomini = new ControladorDomini();
        vistaPrincipal = new VistaPrincipal(this);
        vistaSecundaria = new VistaSecundaria(this);
        vistaTerciaria = new VistaTerciaria(this);
    }

    public void inicialitzarPresentacio() {
        vistaPrincipal.ferVisible();
    }

    public void tornarVistaPrincipal() {
        vistaSecundaria.ferInvisible();
        vistaPrincipal.ferVisible();
        vistaPrincipal.activar();
    }
    
    public void tornarVistaSecundaria() {
        vistaTerciaria.ferInvisible();
        vistaPrincipal.ferVisible();
        vistaSecundaria.ferVisible();
        vistaSecundaria.activar();
    }

    public ArrayList<String> getNomsAlfabets() {
        return ctrlDomini.getNomsAlfabets();
    }

    public ArrayList<String> getNomsDades() {
        return ctrlDomini.getNomsDades();
    }

    public ArrayList<String> getNomsTexts() {
        return ctrlDomini.getNomsTexts();
    }

    public ArrayList<String> getNomsFrequencies() {
        return ctrlDomini.getNomsFreqs();
    }

    public ArrayList<String> getNomsTeclats() {
        return ctrlDomini.getNomsTeclats();
    }

    // Alfabet

    public void vistaSecundariaAfegirAlfabet() throws Exception {
        vistaPrincipal.desactivar();
        vistaSecundaria.afegirAlfabet();
    }

    public void afegeixAlfabet(String nom, ArrayList<Character> alf) throws JaExisteixException {
        ctrlDomini.afegeixAlfabet(nom, alf);
        vistaPrincipal.afegirElement("alfabet", nom);
    }

    public void esborraAlfabet(String nom) throws Exception {
        ctrlDomini.esborraAlfabet(nom);
    }

    public String visualitzarAlfabet(String nom) throws Exception {
        return ctrlDomini.visualitzaAlfabet(nom);
    }

    public void modificaAlfabet(String nom, String nomNou, ArrayList<Character> simbolsNous) throws Exception {
        ctrlDomini.modificaAlfabet(nom, nomNou, simbolsNous);
    }

    //Frequencia

    public void vistaSecundariaAfegirFrequencia() throws Exception {
        vistaPrincipal.desactivar();
        vistaSecundaria.afegirFrequencia();
    }

    public void afegeixFrequencia(String nom, ArrayList<String> nomsDades) throws JaExisteixException, NoTrobatException {
        ctrlDomini.afegeixFreqSimbols(nom, nomsDades);
        vistaPrincipal.afegirElement("frequencia", nom);
    }

    public void esborraFrequencia(String nom) throws Exception {
        ctrlDomini.esborraFreqSimbols(nom);
    }

    public String visualitzarFrequencia(String nom) throws Exception{
        return ctrlDomini.visualitzaFrequencies(nom);
    }

    public void modificaFreq(String nom, String nomNou) throws Exception {
        ctrlDomini.modificaFrequencies(nom, nomNou);
    }

    //Dades

    public void afegeixLlistaDeParaules(String nom, String path) throws Exception {
        ctrlDomini.afegeixLlistaDeParaules(nom, path);
    }

    public void afegeixText (String nom, String path) throws Exception {
        ctrlDomini.afegeixText(nom,path);
    }

    public void esborraDada(String nom) throws NoTrobatException {
        ctrlDomini.esborraDada(nom);
    }

    public String visualitzaDada(String nom) throws NoTrobatException {
        return ctrlDomini.visualitzaDades(nom);
    }

    public void modificaDada(String nom, String nomNou, String nouContingut) throws Exception {
        ctrlDomini.modificaDadesParaules(nom, nomNou, nouContingut);
    }

    public void VistaSecundariaAfegirDada(int r) {
        vistaPrincipal.desactivar();
        vistaSecundaria.afegirDada(r);
    }

    public void afegeixDadaDirectament(String nom, String contingut, int tipus) throws Exception {
        if (tipus == 0) ctrlDomini.afegeixTextDirectament(nom, contingut);
        if (tipus == 1) ctrlDomini.afegeixLlistaDeParaulesDirectament(nom, contingut);
        vistaPrincipal.afegirElement("dada",nom);
    }

    // Teclat

    public void vistaSecundariaAfegirTeclat() throws Exception {
        vistaPrincipal.desactivar();
        vistaSecundaria.afegirTeclat();
    }

    public void afegeixTeclatDades(String nomTeclat, String nomDades, String metode, int iteracions) throws Exception {
        ctrlDomini.generaTeclatDades(metode, nomTeclat, nomDades, iteracions);
        vistaPrincipal.afegirElement("teclat", nomTeclat);
    }

    public void afegeixTeclatFrequencia(String nomTeclat, String nomFreq, String metode, int iteracions) throws Exception {
        ctrlDomini.generaTeclatFrequencia(metode, nomTeclat, nomFreq, iteracions);
        vistaPrincipal.afegirElement("teclat", nomTeclat);
    }

    public void afegeixTeclatAlfFreq(String nomTeclat, String nomFreq, String nomAlfabet, String metode, int iteracions) throws Exception {
        ctrlDomini.generaTeclatAlfabetFrequencia(metode, nomTeclat, nomAlfabet, nomFreq, iteracions);
        vistaPrincipal.afegirElement("teclat", nomTeclat);
    }

    public void esborraTeclat(String nom) throws Exception {
        ctrlDomini.esborraTeclat(nom);
    }

    public String visualitzarTeclat(String nom) throws Exception{
        return ctrlDomini.visualitzaTeclat(nom);
    }

    public void modificaTeclat(String nom, String nomNou) throws Exception {
        ctrlDomini.modificaTeclat(nom, nomNou);
    }

    public void modificaTeclat(String nom, char[] assignacio) throws Exception {
        ctrlDomini.modificaTeclat(nom, assignacio);
    }

    public ArrayList<String> getInfoTeclat(String nom) throws Exception{
        return ctrlDomini.getInfoTeclat(nom);
    }

    public void vistaSecundariaSetDadesTeclat(String nomDades) {
        vistaSecundaria.setDadesTeclat(nomDades);
    }

    public void vistaSecundariaSetAlfabetTeclat(String nomAlfabet) {
        vistaSecundaria.setAlfabetTeclat(nomAlfabet);
    }

    public void vistaSecundariaMostraPuntuacio(String puntuacio) {
        vistaSecundaria.mostraPuntuacio(puntuacio);
    }

    public void vistaPrincipalSetTextAAvaluar(String text) {
        vistaPrincipal.setTextAAvaular(text);
    }

    public String avaluaTeclat(String teclat, String text) throws Exception {
        return ctrlDomini.avaluaTeclat(teclat, text);
    }

    // Vista terciaria

    public void vistaTerciariaSeleccionaDades() {
        vistaSecundaria.desactivar();
        vistaTerciaria.seleccionarDades();
    }

    public void vistaTerciariaSeleccionaTexts() {
        vistaPrincipal.desactivar();
        vistaTerciaria.seleccionarText();
    }

    public void vistaTerciariaSeleccionaFrequencia() {
        vistaSecundaria.desactivar();
        vistaTerciaria.seleccionarFrequencia();
    }

    public void vistaTerciariaSeleccionaAlfabet() {
        vistaSecundaria.desactivar();
        vistaTerciaria.seleccionarAlfabet();
    }
}
