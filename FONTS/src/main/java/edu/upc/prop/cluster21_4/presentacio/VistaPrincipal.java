package edu.upc.prop.cluster21_4.presentacio;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;


import java.util.*;


public class VistaPrincipal {
    
    private static Set<Character> alf_cat = new HashSet<>() 
    {
        {
            add('\u0061'); add('\u0062');
            add('\u0063'); add('\u0064');
            add('\u0065'); add('\u0066');
            add('\u0067'); add('\u0068');
            add('\u0069'); add('\u006A');
            add('\u006B'); add('\u006C');
            add('\u006D'); add('\u006E');
            add('\u006F'); add('\u0070');
            add('\u0071'); add('\u0072');
            add('\u0073'); add('\u0074');
            add('\u0075'); add('\u0076');
            add('\u0077'); add('\u0078');
            add('\u0079'); add('\u007A');
            add('\u00E1'); add('\u00E9');
            add('\u00ED'); add('\u00F3');
            add('\u00FA'); add('\u00E0');
            add('\u00E8'); add('\u00EC');
            add('\u00F2'); add('\u00F9');
            add('\u00F1'); add('\u00E7');
        }
    };

    String help = "<html>L'aplicació de generació de teclats és una eina dissenyada per crear dissenys de teclats personalitzats que optimitzaran la velocitat d'escriptura.<br><br>"+
    "Escull un text o una llista de paraules i genera un teclat! És tan senzill com això.<br><br>"+
    "Si vols combinar diversos textos o llistes de paraules pots fer-ho generant una freqüència a partir d'ells.<br><br>"+
    "Finalment si a més vols afegir lletres que no surten als textos o llistes, utilitza l'alfabet desitjat al generar el teclat.<br><br>"+
    "Els teclats es generaran de forma que escriure els textos o llistes escollits sigui el més àgil possible, però si alguna cosa no t'agrada, sempre es pot editar.</html>";
    private static String[] sino = {"Si","No"};
    String[] ok = {"OK"};

    private ControladorPresentacio ctrlPresentacio;

    //Components
    private JFrame frameVista = new JFrame("Generador de teclats");
    private JPanel panelContinguts = new JPanel();  //panel que conté botons i info
    private JPanel panelInformacio = new JPanel();  //panel que conté info
    private JPanel panelBotons = new JPanel();  //Panel que conté butons
    private JPanel panelInici = new JPanel();
    private VistaDialeg vistaDialegHelp = new VistaDialeg();

    private JPanel panelLlistaAlfabet = new JPanel();   
    private JPanel panelOpcionsAlfabet = new JPanel();
    private JPanel panelVisualitzarAlfabet = new JPanel();
    private boolean found_s = false;

    private JButton buttonAlfabet = new JButton("Alfabets");
    private DefaultListModel<String> modelAlfabet = new DefaultListModel<String>();
    private JList<String> listAlfabet = new JList<String>(modelAlfabet);
    private JButton buttonAfegirAlfabet = new JButton("Afegir");
    private JButton buttonEsborrarAlfabet = new JButton("Esborrar");
    private JTextArea textAreaAlfabet = new JTextArea(5,25);
    private JTextField textFieldAlfabetTitol = new JTextField();
    private String actual_alf;
    private String actual_contingut_alf;
    private JButton buttonModificarAlfabet = new JButton("Guardar");

    private JPanel panelLlistaFrequencia = new JPanel();   
    private JPanel panelOpcionsFrequencia = new JPanel();
    private JPanel panelVisualitzarFrequencia = new JPanel();
    private JPanel panelContingutFrequencia = new JPanel();

    private JButton buttonFrequencia = new JButton("Freqüències");
    private DefaultListModel<String> modelFrequencia = new DefaultListModel<String>();
    private JList<String> listFrequencia = new JList<String>(modelFrequencia);
    private JButton buttonAfegirFrequencia = new JButton("Afegir");
    private JButton buttonEsborrarFrequencia = new JButton("Esborrar");
    private JTextField textFieldFreq = new JTextField();
    private String actual_freq;
    private JButton buttonModificarFreq = new JButton("Guardar");

    private JPanel panelLlistaDades = new JPanel();   
    private JPanel panelOpcionsDades = new JPanel();
    private JPanel panelVisualitzarDades = new JPanel();
    private JTextArea textAreaDades = new JTextArea(10,50);
    private JTextField textFieldDades = new JTextField();
    private String actual_dada;
    private String actual_contingut_dada;

    private JButton buttonDades = new JButton("Texts/Llistes de paraules");
    private DefaultListModel<String> modelDades = new DefaultListModel<String>();
    private JList<String> listDades = new JList<String>(modelDades);
    private JButton buttonAfegirDades = new JButton("Afegir");
    private JButton buttonEsborrarDades= new JButton("Esborrar");
    private JButton buttonModificarDades = new JButton("Guardar");

    private JPanel panelLlistaTeclat = new JPanel();   
    private JPanel panelOpcionsTeclat = new JPanel();
    private JPanel panelVisualitzarTeclat = new JPanel();
    private JPanel panelOpcionsVisualitzarTeclat = new JPanel();
    private JPanel panelMatriuTeclat = new JPanel();

    private JButton buttonTeclat = new JButton("Teclats");
    private DefaultListModel<String> modelTeclat = new DefaultListModel<String>();
    private JList<String> listTeclat = new JList<String>(modelTeclat);
    private JButton buttonAfegirTeclat = new JButton("Afegir");
    private JButton buttonEsborrarTeclat = new JButton("Esborrar");
    private JButton buttonModificarTeclat = new JButton("Editar");
    private JButton buttonGuardarTeclat = new JButton("Guardar");
    private JButton buttonCancelarTeclat = new JButton("Cancelar");
    private JButton buttonAvaluarTeclat = new JButton("Avaluar");
    private JTextField textFieldTeclat = new JTextField();
    private String actual_teclat;
    private char[] assignacioInicialTeclat;
    private char[] assignacioModificadaTeclat;

    private ArrayList<JButton> caractersTeclat;
    private ArrayList<JButton> especialsTeclat;
    private JButton buttonCanviaTeclatSecundari = new JButton("123");
    private JButton buttonCanviaTeclatPrincipal = new JButton("abc");
    boolean teclatEnEdicio;
    JButton teclaSeleccionada;
    boolean teclatModificat;

    private JPanel panel_actual = new JPanel();

    //Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemQuit = new JMenuItem("Surt");
    private JMenuItem menuitemHelp = new JMenuItem("Ajuda");
    private JMenu menuOpcions = new JMenu("Opcions");
    private JMenuItem menuitemAlfabet = new JMenuItem("Alfabets");
    private JMenuItem menuitemFreq = new JMenuItem("Freqüències");
    private JMenuItem menuitemDades = new JMenuItem("Texts/Llistes de paraules");
    private JMenuItem menuitemTeclat = new JMenuItem("Teclats");
    private JMenuItem menuitemInici = new JMenuItem("Inici");

    public VistaPrincipal(ControladorPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    public void ferVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }

    public void afegirElement(String s, String nom) {
        switch (s) {
            case "alfabet": modelAlfabet.addElement(nom); break;
            case "dada": modelDades.addElement(nom); break;
            case "frequencia": modelFrequencia.addElement(nom); break;
            case "teclat": modelTeclat.addElement(nom); break;
        }
    }

    public void canviarPanel(JPanel nou) {

        if (panel_actual == panelInici) {
            panelContinguts.removeAll();
            panelBotons.setLayout(new FlowLayout());
            panelBotons.add(buttonAlfabet);
            buttonAlfabet.setToolTipText("Veure els meus alfabets");
            panelBotons.add(buttonFrequencia);
            buttonFrequencia.setToolTipText("Veure les meves frequencies");
            panelBotons.add(buttonDades);
            buttonDades.setToolTipText("Veure les meves dades");
            panelBotons.add(buttonTeclat);
            buttonTeclat.setToolTipText("Veure els meus teclats");
            panelContinguts.add(panelBotons,BorderLayout.NORTH);
            panelContinguts.add(panelInformacio,BorderLayout.CENTER);
        } 
        if (nou == panelInici) {
            panelContinguts.removeAll();
            JPanel lineStart = new JPanel(new GridBagLayout());
            JPanel botons = new JPanel(new GridLayout(0,1,0,30));
            botons.setBorder(new EmptyBorder(20,30,20,20));
            lineStart.add(botons);
            botons.add(buttonAlfabet);
            botons.add(buttonFrequencia);
            botons.add(buttonDades);
            botons.add(buttonTeclat);
            panelBotons = lineStart;
            panelContinguts.add(panelBotons,BorderLayout.WEST);
            panelContinguts.add(panelInformacio,BorderLayout.CENTER);
        }
        panelInformacio.remove(panel_actual);
        panelInformacio.add(nou);
        panel_actual = nou;
        frameVista.pack();
        frameVista.repaint();
    }

    public void setTextAAvaular(String text) {
        try {
            String puntuacio = ctrlPresentacio.avaluaTeclat(actual_teclat, text);
            ctrlPresentacio.vistaSecundariaMostraPuntuacio(puntuacio);
        } catch (Exception e) {
            VistaDialeg vistaDialegError = new VistaDialeg();
            vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
        }
    }

    // Funcions per visualitzar el teclat quan es selecciona a la llista
    private void visualitzarTeclatPrincipal(String teclat) throws Exception {
        // Elimina totes les tecles del teclat anterior
        panelMatriuTeclat.removeAll();

        // Habilita l'edicio del teclat
        buttonModificarTeclat.setEnabled(true);

        // Settings del layout
        int[] columnes = new int[20];
        Arrays.fill(columnes, 30);
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = columnes;
        panelMatriuTeclat = new JPanel(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipady = 10;

        // Obtencio de la informacio del teclat
        ArrayList<String> info = ctrlPresentacio.getInfoTeclat(teclat);
        caractersTeclat = new ArrayList<>();
        assignacioInicialTeclat = info.get(0).toCharArray();
        assignacioModificadaTeclat = Arrays.copyOf(assignacioInicialTeclat, assignacioInicialTeclat.length);
        ArrayList<Integer> posLletres = traduccioIntegerTeclat(info.get(2));

        // Colocacio de les tecles de l'alfabet
        for (int i = 0; i < assignacioModificadaTeclat.length; ++i) {
            gbc.gridx = posLletres.get(2*i);
            gbc.gridy = posLletres.get(2*i+1);
            gbc.gridwidth = 2;

            // Crea i afegeix un boto amb la lletra iesima
            JButton button = new JButton(Character.toString(assignacioModificadaTeclat[i]));
            caractersTeclat.add(button);
            panelMatriuTeclat.add(caractersTeclat.get(i), gbc);

            // Assigna el comportament per poder modificar el teclat:
            // Es guarda la tecla que s'ha seleccionat primer i quan es selecciona la
            // segona llavors s'intercanvien
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        if (teclatEnEdicio) {
                            // Si es la primera tecla que es selecciona, nomes la guardem
                            if (teclaSeleccionada == null) {
                                teclaSeleccionada = button;
                                teclaSeleccionada.setBackground(Color.WHITE);
                            }
                            else {
                                // Si no, s'han d'intercanviar la seleccionada i l'actual
                                // Marca les tecles editades
                                teclaSeleccionada.setBackground(new Color(193,210,227));
                                button.setBackground(new Color(193,210,227));
                                // Intercanvia botons
                                char c1 = teclaSeleccionada.getText().charAt(0);
                                char c2 = button.getText().charAt(0);
                                teclaSeleccionada.setText(Character.toString(c2));
                                button.setText(Character.toString(c1));
                                // Canvia la informacio perque despres es guardi si l'usuari vol
                                assignacioModificadaTeclat = swap(assignacioModificadaTeclat, c1, c2);
                                if (!teclatModificat) teclatModificat = true;
                                teclaSeleccionada = null;
                                // Visualitzar canvi
                                frameVista.pack();
                                frameVista.validate();
                                frameVista.repaint();
                            }
                        }
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            });
        }

        // Colocacio de les tecles especials
        especialsTeclat = new ArrayList<>();
        ArrayList<String> especials = traduccioStringTeclat(info.get(1));
        ArrayList<Integer> posEspecials = traduccioIntegerTeclat(info.get(3));
        ArrayList<Integer> gruixEspecials = traduccioIntegerTeclat(info.get(4));
        for (int i = 0; i < especials.size(); ++i) {
            gbc.gridx = posEspecials.get(2*i);
            gbc.gridy = posEspecials.get(2*i+1);
            gbc.gridwidth = gruixEspecials.get(i);
            // Crea i afegeix un boto amb el nom corresponent
            String text = especials.get(i);
            if (text.equals("espai")) especialsTeclat.add(new JButton());
            else if (text.equals("123")) especialsTeclat.add(buttonCanviaTeclatSecundari);
            else especialsTeclat.add(new JButton(text));
            panelMatriuTeclat.add(especialsTeclat.get(i), gbc);
        }
        // Coloca el teclat al panel
        // S'ha de fer des del Event Dispatch Thread perquè els canvis es mostrin correctament
        SwingUtilities.invokeLater(() -> {
            panelVisualitzarTeclat.add(panelMatriuTeclat, BorderLayout.SOUTH);
            panelMatriuTeclat.revalidate();
            panelMatriuTeclat.repaint();
            frameVista.pack();
            frameVista.validate();
            frameVista.repaint();
        });
    }

    private void visualitzarTeclatSecundari(String teclat) throws Exception {
        // Elimina totes les tecles del teclat anterior
        panelMatriuTeclat.removeAll();

        // Deshabilita l'edicio del teclat
        buttonModificarTeclat.setEnabled(false);
        
        // Settings del layout
        int[] columnes = new int[20];
        Arrays.fill(columnes, 30);
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = columnes;
        panelMatriuTeclat = new JPanel(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipady = 10;

        // Obtencio de la informacio del teclat
        ArrayList<String> info = ctrlPresentacio.getInfoTeclat(teclat);
        String lletres = info.get(5);
        ArrayList<Integer> posLletres = traduccioIntegerTeclat(info.get(7));
        // Colocacio de les tecles "normals"
        for (int i = 0; i < lletres.length(); ++i) {
            gbc.gridx = posLletres.get(2*i);
            gbc.gridy = posLletres.get(2*i+1);
            gbc.gridwidth = 2;
            // Crea i afegeix un boto amb la lletra iesima
            panelMatriuTeclat.add(new JButton(Character.toString(lletres.charAt(i))), gbc);
        }

        ArrayList<String> especials = traduccioStringTeclat(info.get(6));
        ArrayList<Integer> posEspecials = traduccioIntegerTeclat(info.get(8));
        ArrayList<Integer> gruixEspecials = traduccioIntegerTeclat(info.get(9));

        // Colocacio de les tecles especials
        for (int i = 0; i < especials.size(); ++i) {
            gbc.gridx = posEspecials.get(2*i);
            gbc.gridy = posEspecials.get(2*i+1);
            gbc.gridwidth = gruixEspecials.get(i);
            // Crea i afegeix un boto amb el nom corresponent
            String text = especials.get(i);
            if (text.equals("espai")) panelMatriuTeclat.add(new JButton(), gbc);
            else if (text.equals("abc")) panelMatriuTeclat.add(buttonCanviaTeclatPrincipal, gbc);
            else panelMatriuTeclat.add(new JButton(text), gbc);
        }

        // Coloca el teclat al panel
        // S'ha de fer des del Event Dispatch Thread perquè els canvis es mostrin correctament
        SwingUtilities.invokeLater(() -> {
            panelVisualitzarTeclat.add(panelMatriuTeclat, BorderLayout.SOUTH);
            panelMatriuTeclat.revalidate();
            panelMatriuTeclat.repaint();
            frameVista.pack();
            frameVista.validate();
            frameVista.repaint();
        });
    }

    // transforma un string de la forma 1.5#2.0#2.5#2.0#
    // en un arraylist {3,4,5,4}
    private ArrayList<Integer> traduccioIntegerTeclat(String entrada) {
        ArrayList<Integer> list = new ArrayList<>();
        String s = new String();
        for (int i = 0; i < entrada.length(); ++i) {
            if (entrada.charAt(i) == '#') {
                double d = Double.parseDouble(s);
                int n = (int)(2*d);
                list.add(n);
                s = "";
            } else {
                s = s.concat(Character.toString(entrada.charAt(i)));
            }
        }
        return list;
    }

    // transforma un string de la forma hola#adeu#
    // en un arraylist {hola,adeu}
    private ArrayList<String> traduccioStringTeclat(String entrada) {
        ArrayList<String> list = new ArrayList<>();
        String s = new String();
        for (int i = 0; i < entrada.length(); ++i) {
            if (entrada.charAt(i) == '#') {
                list.add(s);
                s = "";
            } else {
                s = s.concat(Character.toString(entrada.charAt(i)));
            }
        }
        return list;
    }

    // Retorna l'array charArray amb els caracters c1 i c2 intercanviats
    private char[] swap(char[] charArray, char c1, char c2) {
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == c1) {
                charArray[i] = c2;
            } else if (charArray[i] == c2) {
                charArray[i] = c1;
            }
        }
        return charArray;
    }

    // Retorna les tecles i els botons del panel de visualitzar teclat a l'estat normal
    // (tot actiu, colors de les tecles normals...)
    private void surtModificacioTeclat() {
        for (JButton tecla : caractersTeclat) {
            tecla.setBackground(null);
        }
        for (JButton tecla : especialsTeclat) {
            tecla.setBackground(null);
        }
        buttonCanviaTeclatSecundari.setEnabled(true);
        buttonAvaluarTeclat.setEnabled(true);
        teclatModificat = false;
        teclatEnEdicio = false;
        teclaSeleccionada = null;
    }

//Listeners

    private void mouse_listeners() {

        listAlfabet.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (listAlfabet.getSelectedValue() != null) {
                        String selected = (String) listAlfabet.getSelectedValue();
                        try {
                            actual_alf = selected;
                            actual_contingut_alf = ctrlPresentacio.visualitzarAlfabet(selected);
                            textFieldAlfabetTitol.setText(selected);
                            textAreaAlfabet.setText(ctrlPresentacio.visualitzarAlfabet(selected));
                            canviarPanel(panelVisualitzarAlfabet);
                        } catch (Exception e) {
                            VistaDialeg vistaDialegError = new VistaDialeg();
                            vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                        }
                    }
                }
            }
        });

        listFrequencia.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (listFrequencia.getSelectedValue() != null) {
                        String selected = (String) listFrequencia.getSelectedValue();
                        try {
                            actual_freq = selected;
                            textFieldFreq.setText(selected);
                            setTextFrequencia(ctrlPresentacio.visualitzarFrequencia(selected));
                            canviarPanel(panelVisualitzarFrequencia);
                        } catch (Exception e) {
                            VistaDialeg vistaDialegError = new VistaDialeg();
                            vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                        }
                    }
                }
            }
        });

        listDades.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (listDades.getSelectedValue() != null) {
                        String selected = (String) listDades.getSelectedValue();
                        try {
                            actual_dada = selected;
                            actual_contingut_dada = ctrlPresentacio.visualitzaDada(selected);
                            textFieldDades.setText(selected);
                            textAreaDades.setText(ctrlPresentacio.visualitzaDada(selected));
                            canviarPanel(panelVisualitzarDades);
                        } catch (Exception e) {
                            VistaDialeg vistaDialegError = new VistaDialeg();
                            vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                        }
                    }
                }
            }
        });

        listTeclat.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    if (listTeclat.getSelectedValue() != null) {
                        String selected = (String) listTeclat.getSelectedValue();
                        try {
                            actual_teclat = selected;
                            textFieldTeclat.setText(selected);
                            teclatEnEdicio = false;
                            teclaSeleccionada = null;
                            canviarPanel(panelVisualitzarTeclat);
                            visualitzarTeclatPrincipal(selected);
                            panelContinguts.revalidate();
                            panelContinguts.repaint();
                        } catch (Exception e) {
                            VistaDialeg vistaDialegError = new VistaDialeg();
                            vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                        }
                    }
                }
            }
        });
    }

    private void asignar_listenersComponents() {

        ////////////////////////////// Alfabet //////////////////////////////

        buttonAlfabet.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaAlfabet);
                    }
                }   
            }
        );

        buttonAfegirAlfabet.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    try {
                        ctrlPresentacio.vistaSecundariaAfegirAlfabet();
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonEsborrarAlfabet.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        if (listAlfabet.getSelectedValue() != null) {
                            VistaDialeg vistaDialeg = new VistaDialeg();
                            int sel = vistaDialeg.setDialeg(" ", "Segur que vols esborrar els seguents elements?", sino, "avis");
                            if (sel == 0) {
                                String[] alfs = listAlfabet.getSelectedValuesList().toArray(new String[0]);
                                for (String s : alfs) {
                                    ctrlPresentacio.esborraAlfabet(s);
                                    modelAlfabet.removeElement(s);
                                }
                            }
                        }
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonModificarAlfabet.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (!textFieldAlfabetTitol.getText().equals(actual_alf) || !textAreaAlfabet.getText().equals(actual_contingut_alf)) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        int sel = vistaDialeg.setDialeg("", "Segur que vols modificar aquest element?", sino, "avis");
                        if (sel == 0) {
                            try {
                                String nomAlfabet = textFieldAlfabetTitol.getText();
                                if (nomAlfabet.isBlank()) {
                                    // No s'ha posat nom a la frequencia
                                    VistaDialeg vistaDialegError = new VistaDialeg();
                                    vistaDialegError.setDialeg("Error nom", "El nom posat no és vàlid", ok, "error");
                                } 
                                else if (textAreaAlfabet.getText().isBlank()) {
                                    // No s'ha posat nom a la frequencia
                                    VistaDialeg vistaDialegError = new VistaDialeg();
                                    vistaDialegError.setDialeg("Error contingut", "El contingut posat no és vàlid", ok, "error");
                                }
                                else {
                                    char[] alf = textAreaAlfabet.getText().toCharArray();
                                    ArrayList<Character> list = new ArrayList<Character>();
                                    for (char c: alf) {
                                        c = Character.toLowerCase(c); 
                                        if (!alf_cat.contains(c)) {
                                            found_s = true;
                                            break;
                                        }
                                    }
                                    if (!found_s) {
                                        for (char c : alf) {
                                            c = Character.toLowerCase(c);
                                            if (alf_cat.contains(c)) list.add(c);
                                        }
                                    ctrlPresentacio.modificaAlfabet(actual_alf, textFieldAlfabetTitol.getText(),list);
                                    modelAlfabet.addElement(textFieldAlfabetTitol.getText());
                                    modelAlfabet.removeElement(actual_alf);
                                    actual_alf = textFieldAlfabetTitol.getText();
                                    }
                                    else {
                                        found_s = false;
                                        VistaDialeg vistaDialegError = new VistaDialeg();
                                        vistaDialegError.setDialeg("Error contingut", "L'alfabet no pot contenir símbols especials", ok, "error");
                                    }
                                }
                            } catch (Exception e) {
                                VistaDialeg vistaDialegError = new VistaDialeg();
                                vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                            }
                        }
                    }
                }
            }
        );

        ////////////////////////////// Dades //////////////////////////////
        
        buttonDades.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaDades);
                    }
                }   
            }
        );

        buttonAfegirDades.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    try {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        String[] manera = {"Fitxer", "Escrit"};
                        int t = vistaDialeg.setDialeg("", "Com vols entrar les dades?", manera, "pregunta");
                        if (t != -1) {
                            String[] opcions = {"Text", "Llista de paraules"};
                            int r = vistaDialeg.setDialeg("Tipus de dada", "Quin tipus de dada vols entrar?", opcions, "pregunta");
                            if (r != -1) {
                                if (t == 0) {
                                    JFileChooser j = new JFileChooser();
                                    int res = j.showSaveDialog(null);
                                    if (res == JFileChooser.APPROVE_OPTION) {
                                        String path = j.getSelectedFile().getAbsolutePath();
                                        String nom = j.getSelectedFile().getName();
                                        if (r == 0) ctrlPresentacio.afegeixText(nom, path);
                                        if (r == 1) ctrlPresentacio.afegeixLlistaDeParaules(nom, path);
                                        afegirElement("dada", nom);
                                    }
                                } 
                                if (t == 1) {
                                    ctrlPresentacio.VistaSecundariaAfegirDada(r);
                                }
                            }
                        }   
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonEsborrarDades.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        if (listDades.getSelectedValue() != null) {
                            VistaDialeg vistaDialeg = new VistaDialeg();
                            int sel = vistaDialeg.setDialeg(" ", "Segur que vols esborrar els seguents elements?", sino, "avis");
                            if (sel == 0) {
                                String[] dades = listDades.getSelectedValuesList().toArray(new String[0]);
                                for (String s : dades) {
                                    ctrlPresentacio.esborraDada(s);
                                    modelDades.removeElement(s);
                                }
                            }
                        }
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonModificarDades.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (!textFieldDades.getText().equals(actual_dada) || !textAreaDades.getText().equals(actual_contingut_dada)) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        int sel = vistaDialeg.setDialeg("", "Segur que vols modificar aquest element?", sino, "avis");
                        if (sel == 0) {
                            try {
                                String nomDades = textFieldDades.getText();
                                if (nomDades.isBlank()) {
                                    // No s'ha posat nom a la frequencia
                                    VistaDialeg vistaDialegError = new VistaDialeg();
                                    vistaDialegError.setDialeg("Error nom", "El nom posat no és vàlid", ok, "error");
                                } 
                                else if (textAreaDades.getText().isBlank()) {
                                    // No s'ha posat nom a la frequencia
                                    VistaDialeg vistaDialegError = new VistaDialeg();
                                    vistaDialegError.setDialeg("Error contingut", "El contingut posat no és vàlid", ok, "error");
                                }
                                else {
                                    ctrlPresentacio.modificaDada(actual_dada, textFieldDades.getText(), textAreaDades.getText());
                                    if (actual_dada != textFieldDades.getText()) {
                                    modelDades.addElement(textFieldDades.getText());
                                    modelDades.removeElement(actual_dada);
                                    actual_dada = textFieldDades.getText();
                                }
                            }
                            } catch (Exception e) {
                                VistaDialeg vistaDialegError = new VistaDialeg();
                                vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                            }
                        }
                    }
                }
            }
        );

       ////////////////////////////// Frequencies //////////////////////////////

        buttonFrequencia.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaFrequencia);
                    }
                }   
            }
        );

        buttonAfegirFrequencia.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    try {
                        ctrlPresentacio.vistaSecundariaAfegirFrequencia();
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonEsborrarFrequencia.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        if (listFrequencia.getSelectedValue() != null) {
                            VistaDialeg vistaDialeg = new VistaDialeg();
                            int sel = vistaDialeg.setDialeg(" ", "Segur que vols borrar els seguents elements?", sino, "avis");
                            if (sel == 0) {
                                String[] frequencies = listFrequencia.getSelectedValuesList().toArray(new String[0]);
                                for (String s : frequencies) {
                                    ctrlPresentacio.esborraFrequencia(s);
                                    modelFrequencia.removeElement(s);
                                }
                            }
                        }
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonModificarFreq.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (!textFieldFreq.getText().equals(actual_freq)) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        int sel = vistaDialeg.setDialeg("", "Segur que vols modificar aquest element?", sino, "avis");
                        if (sel == 0) {
                            try {
                                String nomFreq = textFieldFreq.getText();
                                if (nomFreq.isBlank()) {
                                    // No s'ha posat nom a la frequencia
                                    VistaDialeg vistaDialegError = new VistaDialeg();
                                    vistaDialegError.setDialeg("Error nom", "El nom posat no és vàlid", ok, "error");
                                }
                                else {
                                    ctrlPresentacio.modificaFreq(actual_freq, textFieldFreq.getText());
                                    modelFrequencia.addElement(textFieldFreq.getText());
                                    modelFrequencia.removeElement(actual_freq);
                                    actual_freq = textFieldFreq.getText();
                                }
                            } catch (Exception e) {
                                VistaDialeg vistaDialegError = new VistaDialeg();
                                vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                            }
                        }
                    }
                }
            }
        );

        ////////////////////////////// Teclat //////////////////////////////

        buttonTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaTeclat);
                    }
                }   
            }
        );

        buttonAfegirTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    try {
                        ctrlPresentacio.vistaSecundariaAfegirTeclat();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        );

        buttonEsborrarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        if (listTeclat.getSelectedValue() != null) {
                            VistaDialeg vistaDialeg = new VistaDialeg();
                            int sel = vistaDialeg.setDialeg(" ", "Segur que vols esborrar els seguents elements?", sino, "avis");
                            if (sel == 0) {
                                String[] teclats = listTeclat.getSelectedValuesList().toArray(new String[0]);
                                for (String s : teclats) {
                                    ctrlPresentacio.esborraTeclat(s);
                                    modelTeclat.removeElement(s);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        );

        buttonGuardarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    // Si s'ha modificat el nom i les tecles pregunta nomes una vegada si es volen desar els canvis
                    // I si el nou nom no es correcte no canvia les tecles
                    if (!textFieldTeclat.getText().equals(actual_teclat) && teclatModificat) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        int sel = vistaDialeg.setDialeg("", "Segur que vols modificar aquest element?", sino, "avis");
                        if (sel == 0) {
                            try {
                                ctrlPresentacio.modificaTeclat(actual_teclat, textFieldTeclat.getText());
                                modelTeclat.addElement(textFieldTeclat.getText());
                                modelTeclat.removeElement(actual_teclat);
                                actual_teclat = textFieldTeclat.getText();
                                ctrlPresentacio.modificaTeclat(actual_teclat, assignacioModificadaTeclat);
                                teclatModificat = false;
                                // Surt del mode d'edicio i retorna el color normal de les tecles
                                surtModificacioTeclat();
                            } catch (Exception e) {
                                VistaDialeg vistaDialegError = new VistaDialeg();
                                vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                            }                            
                        }
                    }
                    // Nomes canvia el nom
                    else if (!textFieldTeclat.getText().equals(actual_teclat)) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        int sel = vistaDialeg.setDialeg("", "Segur que vols modificar el nom?", sino, "avis");
                        if (sel == 0) {
                            try {
                                String nomT = textFieldTeclat.getText();
                                if (nomT.isBlank()) {
                                    // No s'ha posat nom a la frequencia
                                    VistaDialeg vistaDialegError = new VistaDialeg();
                                    vistaDialegError.setDialeg("Error nom", "El nom posat no és vàlid", ok, "error");
                                } 
                                else {
                                    ctrlPresentacio.modificaTeclat(actual_teclat, textFieldTeclat.getText());
                                    modelTeclat.addElement(textFieldTeclat.getText());
                                    modelTeclat.removeElement(actual_teclat);
                                    actual_teclat = textFieldTeclat.getText();
                                    // Surt del mode d'edicio del teclat
                                    if (teclatEnEdicio) buttonModificarTeclat.doClick();
                                }
                            } catch (Exception e) {
                                VistaDialeg vistaDialegError = new VistaDialeg();
                                vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                            }                            
                        }
                    }
                    // Nomes canvien les tecles
                    else if (teclatModificat) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        int sel = vistaDialeg.setDialeg("", "Segur que vols modificar les tecles?", sino, "avis");
                        if (sel == 0) {
                            try {
                                ctrlPresentacio.modificaTeclat(actual_teclat, assignacioModificadaTeclat);
                                // Surt del mode d'edicio i retorna el color normal de les tecles
                                surtModificacioTeclat();
                            } catch (Exception e) {
                                VistaDialeg vistaDialegError = new VistaDialeg();
                                vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                            }                            
                        }
                    }
                }
            }
        );

        buttonAvaluarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    ctrlPresentacio.vistaTerciariaSeleccionaTexts();
                }
            }
        );

        buttonModificarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    // Si ja s'esta editant s'ha de premer el boto de cancelar, modificar ja no fa res
                    if (!teclatEnEdicio) {
                        teclatEnEdicio = true;
                        for (JButton tecla : especialsTeclat) {
                            tecla.setBackground(Color.LIGHT_GRAY);
                        }
                        buttonCanviaTeclatSecundari.setEnabled(false);
                        buttonAvaluarTeclat.setEnabled(false);
                    }
                }
            }
        );

        buttonCancelarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    // Si no s'esta editant el teclat cancelar no fa res
                    if (teclatEnEdicio) {
                        if (teclatModificat) {
                            // Torna a colocar les tecles com a l'inici
                            for (int i = 0; i < caractersTeclat.size(); ++i) {
                                JButton tecla = caractersTeclat.get(i);
                                tecla.setText(Character.toString(assignacioInicialTeclat[i]));
                            }
                            // Visualitzar canvi
                            frameVista.pack();
                            frameVista.validate();
                            frameVista.repaint();
                        }
                        surtModificacioTeclat();
                    }
                }
            }
        );

        buttonCanviaTeclatSecundari.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        visualitzarTeclatSecundari(actual_teclat);
                        frameVista.pack();
                        frameVista.validate();
                        frameVista.repaint();
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        buttonCanviaTeclatPrincipal.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        visualitzarTeclatPrincipal(actual_teclat);
                        frameVista.pack();
                        frameVista.validate();
                        frameVista.repaint();
                    } catch (Exception e) {
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");
                    }
                }
            }
        );

        ////////////////////////////// Menu //////////////////////////////

        menuitemHelp.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    vistaDialegHelp.setDialeg("GUIA", help, ok , "informacio");
                }
            }
        );

        menuitemAlfabet.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaAlfabet);
                    }
                }
            }
        );

        menuitemFreq.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaFrequencia);
                    }
                }
            }
        );

        menuitemDades.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaDades);
                    }
                }
            }
        );

        menuitemTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed (ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelLlistaTeclat);
                    }
                }
            }
        );

        menuitemInici.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (teclatEnEdicio) {
                        VistaDialeg vistaDialeg = new VistaDialeg();
                        vistaDialeg.setDialeg("", "Hi ha un teclat en edició.", ok, "avis");
                    } else {
                        canviarPanel(panelInici);
                    }
                }
            }
        );

        menuitemQuit.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    VistaDialeg vistaDialeg = new VistaDialeg();
                    int sel = vistaDialeg.setDialeg("", "Segur que vols sortir?", sino, "avis");
                    if (sel == 0) System.exit(0);
                }
            }
        );

        // Resize de la finestra
        frameVista.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                frameVista.setPreferredSize(frameVista.getSize());
            }
        });
    }

//Inicialitzar

    private void inicialitzarComponents() {
        inicialitzar_frameVista();
        inicialitzar_menubarVista();
        inicialitzar_panelBotons();
        inicialitzar_panelInformacio();
        inicialitzar_panelContinguts();
        carregar_dadesGuardades();
        inicialitzar_panelLlista();
        inicialitzar_panelVisualitzar();
        asignar_listenersComponents();
        mouse_listeners();
    }

    private void inicialitzar_frameVista() {
        frameVista.setMinimumSize(new Dimension(700,500));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(true);
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts);
    }

    private void inicialitzar_menubarVista() {
        menuFile.add(menuitemHelp);
        menuFile.add(menuitemQuit);
        menuOpcions.add(menuitemInici);
        menuOpcions.add(menuitemAlfabet);
        menuOpcions.add(menuitemFreq);
        menuOpcions.add(menuitemDades);
        menuOpcions.add(menuitemTeclat);
        menubarVista.add(menuFile);
        menubarVista.add(menuOpcions);
        frameVista.setJMenuBar(menubarVista);
    }

    private void inicialitzar_panelContinguts() {
        panelContinguts.setLayout(new BorderLayout());
        panelContinguts.setBorder(new EmptyBorder(20,50,50,50));
        panelContinguts.add(panelBotons,BorderLayout.WEST);
        panelContinguts.add(panelInformacio,BorderLayout.CENTER);
    }

    private void inicialitzar_panelBotons() {
        JPanel lineStart = new JPanel(new GridBagLayout());
        JPanel botons = new JPanel(new GridLayout(0,1,0,30));
        botons.setBorder(new EmptyBorder(20,30,20,20));
        lineStart.add(botons);
        botons.add(buttonAlfabet);
        botons.add(buttonFrequencia);
        botons.add(buttonDades);
        botons.add(buttonTeclat);

        panelBotons = lineStart;
    }

    private void inicialitzar_panelInformacio() {
        panelInformacio.setLayout(new BorderLayout());

        panelInici.setLayout(new BorderLayout());
        JLabel textAreaGuia = new JLabel(help);
        panelInici.add(textAreaGuia,BorderLayout.CENTER);

        panel_actual = panelInici;
        panelInformacio.add(panelInici);
    }

    private void inicialitzar_panelLlista() {
        //Alfabet
        JLabel labelAlf = new JLabel("Alfabets");
        panelLlistaAlfabet.setLayout(new BorderLayout(10,10));
        panelLlistaAlfabet.setBorder(new EmptyBorder(20,0,0,0));
        panelLlistaAlfabet.add(labelAlf,BorderLayout.NORTH);
        panelLlistaAlfabet.add(panelOpcionsAlfabet,BorderLayout.EAST);
        listAlfabet.setFixedCellHeight(25);
        listAlfabet.setFixedCellWidth(360);
        listAlfabet.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);   //shift/ctrl + click
        panelLlistaAlfabet.add(listAlfabet,BorderLayout.WEST);
        panelLlistaAlfabet.add(new JScrollPane(listAlfabet));

        panelOpcionsAlfabet.setLayout(new BoxLayout(panelOpcionsAlfabet, BoxLayout.Y_AXIS));
        panelOpcionsAlfabet.add(buttonAfegirAlfabet);
        buttonAfegirAlfabet.setToolTipText("Afegir un alfabet");
        panelOpcionsAlfabet.add(Box.createRigidArea(new Dimension(10,10)));
        panelOpcionsAlfabet.add(buttonEsborrarAlfabet);
        buttonEsborrarAlfabet.setToolTipText("Esborrar varios alfabets");
        panelOpcionsAlfabet.add(Box.createRigidArea(new Dimension(10,10)));

        //Frequencia
        JLabel labelFreq = new JLabel("Freqüències");
        panelLlistaFrequencia.setLayout(new BorderLayout(10,10));
        panelLlistaFrequencia.setBorder(new EmptyBorder(20,0,0,0));
        panelLlistaFrequencia.add(labelFreq,BorderLayout.NORTH);
        panelLlistaFrequencia.add(panelOpcionsFrequencia,BorderLayout.EAST);
        listFrequencia.setFixedCellHeight(25);
        listFrequencia.setFixedCellWidth(360);
        listFrequencia.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);   //shift/ctrl + click
        panelLlistaFrequencia.add(listFrequencia,BorderLayout.WEST);
        panelLlistaFrequencia.add(new JScrollPane(listFrequencia));

        panelOpcionsFrequencia.setLayout(new BoxLayout(panelOpcionsFrequencia, BoxLayout.Y_AXIS));
        panelOpcionsFrequencia.add(buttonAfegirFrequencia);
        buttonAfegirFrequencia.setToolTipText("Afegir una Frequencia");
        panelOpcionsFrequencia.add(Box.createRigidArea(new Dimension(10,10)));
        panelOpcionsFrequencia.add(buttonEsborrarFrequencia);
        buttonEsborrarFrequencia.setToolTipText("Esborrar varies Frequencies");
        panelOpcionsFrequencia.add(Box.createRigidArea(new Dimension(10,10)));

        //Dades
        JLabel labelDades = new JLabel("Texts/Llistes de paraules");
        panelLlistaDades.setLayout(new BorderLayout(10,10));
        panelLlistaDades.setBorder(new EmptyBorder(20,0,0,0));
        panelLlistaDades.add(labelDades,BorderLayout.NORTH);
        panelLlistaDades.add(panelOpcionsDades,BorderLayout.EAST);
        listDades.setFixedCellHeight(25);
        listDades.setFixedCellWidth(360);
        listDades.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);   //shift/ctrl + click
        panelLlistaDades.add(listDades,BorderLayout.WEST);
        panelLlistaDades.add(new JScrollPane(listDades));

        panelOpcionsDades.setLayout(new BoxLayout(panelOpcionsDades, BoxLayout.Y_AXIS));
        panelOpcionsDades.add(buttonAfegirDades);
        buttonAfegirDades.setToolTipText("Afegir unes Dades");
        panelOpcionsDades.add(Box.createRigidArea(new Dimension(10,10)));
        panelOpcionsDades.add(buttonEsborrarDades);
        buttonEsborrarDades.setToolTipText("Esborrar varies Dades");
        panelOpcionsDades.add(Box.createRigidArea(new Dimension(10,10)));

        //Teclat
        JLabel labelTeclat = new JLabel("Teclats");
        panelLlistaTeclat.setLayout(new BorderLayout(10,10));
        panelLlistaTeclat.setBorder(new EmptyBorder(20,0,0,0));
        panelLlistaTeclat.add(labelTeclat, BorderLayout.NORTH);
        panelLlistaTeclat.add(panelOpcionsTeclat,BorderLayout.EAST);
        listTeclat.setFixedCellHeight(25);
        listTeclat.setFixedCellWidth(360);
        listTeclat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);   //shift/ctrl + click
        panelLlistaTeclat.add(listTeclat,BorderLayout.WEST);
        panelLlistaTeclat.add(new JScrollPane(listTeclat));

        panelOpcionsTeclat.setLayout(new BoxLayout(panelOpcionsTeclat, BoxLayout.Y_AXIS));
        panelOpcionsTeclat.add(buttonAfegirTeclat);
        buttonAfegirTeclat.setToolTipText("Afegir un Teclat");
        panelOpcionsTeclat.add(Box.createRigidArea(new Dimension(10,10)));
        panelOpcionsTeclat.add(buttonEsborrarTeclat);
        buttonEsborrarTeclat.setToolTipText("Esborrar varios Teclat");
        panelOpcionsTeclat.add(Box.createRigidArea(new Dimension(10,10)));
    }

    private void inicialitzar_panelVisualitzar() {
        //Alfabet
        panelVisualitzarAlfabet.setLayout(new BorderLayout(10,10));
        panelVisualitzarAlfabet.setBorder(new EmptyBorder(20,0,0,0));
        panelVisualitzarAlfabet.add(textFieldAlfabetTitol,BorderLayout.NORTH);
        panelVisualitzarAlfabet.add(textAreaAlfabet,BorderLayout.WEST);
        textAreaAlfabet.setLineWrap(true);
        textAreaAlfabet.setWrapStyleWord(true);
        panelVisualitzarAlfabet.add(new JScrollPane(textAreaAlfabet));
        JPanel panelOpcionsVisAlfabet = new JPanel();
        panelOpcionsVisAlfabet.setLayout(new BoxLayout(panelOpcionsVisAlfabet, BoxLayout.Y_AXIS));
        panelOpcionsVisAlfabet.add(buttonModificarAlfabet);
        panelVisualitzarAlfabet.add(panelOpcionsVisAlfabet,BorderLayout.EAST);
        buttonModificarAlfabet.setToolTipText("Modificar alfabet");

        //Frequencia
        panelVisualitzarFrequencia.setLayout(new BorderLayout(10,10));
        panelVisualitzarFrequencia.setBorder(new EmptyBorder(20,0,0,0));
        panelVisualitzarFrequencia.add(textFieldFreq,BorderLayout.NORTH);
        panelVisualitzarFrequencia.add(panelContingutFrequencia,BorderLayout.CENTER);
        panelVisualitzarFrequencia.add(new JScrollPane(panelContingutFrequencia));
        JPanel panelOpcionsVisFrequencia = new JPanel();
        panelOpcionsVisFrequencia.setLayout(new BoxLayout(panelOpcionsVisFrequencia, BoxLayout.Y_AXIS));
        panelOpcionsVisFrequencia.add(buttonModificarFreq);
        panelVisualitzarFrequencia.add(panelOpcionsVisFrequencia,BorderLayout.EAST);

        //Dades
        panelVisualitzarDades.setLayout(new BorderLayout(10,10));
        panelVisualitzarDades.setBorder(new EmptyBorder(20,0,0,0));
        panelVisualitzarDades.add(textAreaDades,BorderLayout.WEST);
        textAreaDades.setLineWrap(true);
        textAreaDades.setWrapStyleWord(true);
        panelVisualitzarDades.add(new JScrollPane(textAreaDades));
        panelVisualitzarDades.add(textFieldDades,BorderLayout.NORTH);
        JPanel panelOpcionsVisDades = new JPanel();
        panelOpcionsVisDades.setLayout(new BoxLayout(panelOpcionsVisDades, BoxLayout.Y_AXIS));
        panelOpcionsVisDades.add(buttonModificarDades);
        panelVisualitzarDades.add(panelOpcionsVisDades,BorderLayout.EAST);
        buttonModificarDades.setToolTipText("Modificar Dades");

        //Teclat
        panelVisualitzarTeclat.setLayout(new BorderLayout(10,10));
        panelVisualitzarTeclat.setBorder(new EmptyBorder(20,0,0,0));
        panelMatriuTeclat.setLayout(new GridBagLayout());
        panelVisualitzarTeclat.add(panelMatriuTeclat,BorderLayout.CENTER);
        panelOpcionsVisualitzarTeclat.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelOpcionsVisualitzarTeclat.add(buttonModificarTeclat);
        buttonModificarTeclat.setToolTipText("Modificar posicions de tecles del teclat");
        panelOpcionsVisualitzarTeclat.add(buttonCancelarTeclat);
        buttonCancelarTeclat.setToolTipText("Cancelar l'edició del teclat");
        panelOpcionsVisualitzarTeclat.add(buttonGuardarTeclat);
        buttonGuardarTeclat.setToolTipText("Guardar Teclat");
        panelOpcionsVisualitzarTeclat.add(buttonAvaluarTeclat);
        buttonAvaluarTeclat.setToolTipText("Avaluar l'adequació del teclat per un text");
        panelVisualitzarTeclat.add(panelOpcionsVisualitzarTeclat,BorderLayout.EAST);
        panelVisualitzarTeclat.add(textFieldTeclat, BorderLayout.NORTH);
    }

    // Coloca el contingut de freq al panel de visualitzar frequencia
    private void setTextFrequencia(String freq) {
        panelContingutFrequencia.removeAll();
        String[] split = freq.split("\\s+");
        int size = (int)Math.sqrt(split.length);
        
        panelContingutFrequencia.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        
        for(int i = 0; i < split.length; ++i) {
            JLabel label = new JLabel(split[i]);
            gbc.gridx = i%size;
            gbc.gridy = i/size;
            panelContingutFrequencia.add(label, gbc);
        }
    }

    void carregar_dadesGuardades() {
        ArrayList<String> alfs = ctrlPresentacio.getNomsAlfabets();
        for (String s : alfs) modelAlfabet.addElement(s);
        ArrayList<String> freqs = ctrlPresentacio.getNomsFrequencies();
        for (String s : freqs) modelFrequencia.addElement(s);
        ArrayList<String> dades = ctrlPresentacio.getNomsDades();
        for (String s : dades) modelDades.addElement(s);
        ArrayList<String> teclats = ctrlPresentacio.getNomsTeclats();
        for (String s : teclats) modelTeclat.addElement(s);
    }
}