package edu.upc.prop.cluster21_4.presentacio;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.upc.prop.cluster21_4.domini.Excepcions.JaExisteixException;
import edu.upc.prop.cluster21_4.domini.Excepcions.NoTrobatException;

import java.util.*;

public class VistaSecundaria {

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

    private static String[] sino = {"Si","No"};
    private static String[] error = {"D'acord"};

    private ControladorPresentacio ctrlPresentacio;

    private JFrame frameVista = new JFrame();

    private JPanel panelContinguts = new JPanel();
    private JButton buttonCancel = new JButton("Cancelar");
    private JButton buttonGuardar = new JButton("Guardar");

    private JPanel panel_afegirAlfabet = new JPanel();
    private JTextArea textAreaInfo = new JTextArea(5,25);
    private JTextField textFieldTitolAlfabet = new JTextField(25);
    private boolean found_s = false;

    private JPanel panel_afegirDada = new JPanel();
    private JTextField textFieldTitolDada = new JTextField(25);
    private JTextArea textAreaInfoDada = new JTextArea(10,25);
    private JButton buttonCancelarDada = new JButton("Cancelar");
    private JButton buttonGuardarDada = new JButton("Guardar");
    private int tipus_dada;
    
    private JPanel panel_afegirFrequencia = new JPanel();
    private JTextField textFieldTitolFrequencia = new JTextField(25);
    private JButton buttonGuardarFreq = new JButton("Guardar");
    private JButton buttonCancelarFreq = new JButton("Cancelar");
    private JPanel panelLlistaDades = new JPanel();   
    private DefaultListModel<String> modelDades = new DefaultListModel<String>();
    private JList<String> listDades = new JList<String>(modelDades);

    private JPanel panel_afegirTeclat = new JPanel();
    private JTextField textFieldTitolTeclat = new JTextField(25);
    private ButtonGroup grupTeclatDades = new ButtonGroup();
    private ButtonGroup grupTeclatMetode = new ButtonGroup();
    private JRadioButton rbTeclatTextLlista = new JRadioButton("Text/Llista de freqüències");
    private JRadioButton rbTeclatFrequencia = new JRadioButton("Freqüència");
    private JRadioButton rbTeclatQAP = new JRadioButton("<html>Branch and bound - Mètode òptim però pot trigar molt.</html>");
    private JRadioButton rbTeclatHC = new JRadioButton("<html>Cerca local - Mètode ràpid. La seva qualitat serà més<br> alta amb més iteracions, però també augmentarà el<br> temps de generació.</html>"); 
    private JTextField iteracionsHC = new JTextField("20", 10);
    private JButton buttonSelDades = new JButton("Seleccionar dades");
    private JButton buttonSelAlfabet = new JButton("Seleccionar");
    
    private JButton buttonGenerarTeclat = new JButton("Generar");
    private JButton buttonCancelarTeclat = new JButton("Cancelar");
    private String nomDadesTeclat;
    private String nomAlfabetTeclat;
    private String metodeTeclat;
    SwingWorker<Void, Void> workerGenTeclat;

    private JPanel panel_mostrarPuntuacio = new JPanel();
    private JLabel labelPuntuacio = new JLabel();

    private JPanel panel_actual = new JPanel();

    public VistaSecundaria (ControladorPresentacio ctrlPresentacio) throws Exception {
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }

    public void ferVisible() {
        frameVista.pack();
        frameVista.setLocationRelativeTo(null);
        frameVista.setVisible(true);
    }

    public void ferInvisible() {
        frameVista.setVisible(false);
    }

    public void afegirAlfabet() {
        frameVista.setMinimumSize(new Dimension(450, 250));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        textAreaInfo.setText("");
        textFieldTitolAlfabet.setText("");
        panelContinguts.remove(panel_actual);
        panelContinguts.add(panel_afegirAlfabet);
        panel_actual = panel_afegirAlfabet;
        
        ferVisible();
    }

    public void afegirFrequencia() {
        frameVista.setMinimumSize(new Dimension(450, 370));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        textAreaInfo.setText("");
        textFieldTitolFrequencia.setText("");

        ArrayList<String> list = ctrlPresentacio.getNomsDades();
        modelDades.clear();
        for (String s : list) {
            modelDades.addElement(s);
        }

        panelContinguts.remove(panel_actual);
        panelContinguts.add(panel_afegirFrequencia);
        panel_actual = panel_afegirFrequencia;
        ferVisible();
    }

    public void afegirDada(int r) {
        frameVista.setMinimumSize(new Dimension(450, 320));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        textAreaInfoDada.setText("");
        textFieldTitolDada.setText("");
        panelContinguts.remove(panel_actual);
        panelContinguts.add(panel_afegirDada);
        panel_actual = panel_afegirDada;
        
        tipus_dada = r;

        ferVisible();
    }

    public void afegirTeclat() {
        frameVista.setMinimumSize(new Dimension(460, 480));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        netejar_panelAfegirTeclat();
        panelContinguts.remove(panel_actual);
        panelContinguts.add(panel_afegirTeclat);
        panel_actual = panel_afegirTeclat;
        ferVisible();
    }

    public void mostraPuntuacio(String puntuacio) {
        frameVista.setMinimumSize(new Dimension(320, 100));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        labelPuntuacio.setText("Distància recorreguda: " + puntuacio);
        panelContinguts.remove(panel_actual);
        panelContinguts.add(panel_mostrarPuntuacio);
        panel_actual = panel_mostrarPuntuacio;
        ferVisible();
    }

    public void setDadesTeclat(String nomDades) {
        nomDadesTeclat = nomDades;
    }

    public void setAlfabetTeclat(String nomAlfabet) {
        nomAlfabetTeclat = nomAlfabet;
    }

//interfaces Listeneres

    public void actionPerformed_buttonGuardar(ActionEvent event) throws JaExisteixException {
        String nomAlfabet = textFieldTitolAlfabet.getText();
        if (nomAlfabet.isBlank()) {
            // No s'ha posat nom a la frequencia
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error nom", "El nom posat no és vàlid", error, "error");
        } else if (textAreaInfo.getText().isBlank()) {
            // No s'ha posat nom a la frequencia
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error contingut", "El contingut posat no és vàlid", error, "error");
        } else {
            char[] alf = textAreaInfo.getText().toCharArray();
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
                ctrlPresentacio.afegeixAlfabet(nomAlfabet, list);
                ctrlPresentacio.tornarVistaPrincipal();
            }
            else {
                found_s = false;
                VistaDialeg vistaDialegError = new VistaDialeg();
                vistaDialegError.setDialeg("Error contingut", "L'alfabet no pot contenir símbols especials", error, "error");
            }
        }
    }

    public void actionPerformed_buttonGuardarFreq(ActionEvent event) throws JaExisteixException, NoTrobatException {
        String nomFreq = textFieldTitolFrequencia.getText();
        if (nomFreq.isBlank()) {
            // No s'ha posat nom a la frequencia
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error nom", "El nom posat no és vàlid", error, "error");
        } else if (listDades.getSelectedValue() == null) {
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error contingut", "El contingut posat no és vàlid", error, "error");
        }else {
            String[] dades = listDades.getSelectedValuesList().toArray(new String[0]);
            ArrayList<String> listF = new ArrayList<String>();
            for (String c : dades) listF.add(c);
            ctrlPresentacio.afegeixFrequencia(nomFreq, listF);
            ctrlPresentacio.tornarVistaPrincipal();
        }
    }

    public void actionPerformed_buttonGuardarDada(ActionEvent event) throws Exception {
        String s = textFieldTitolDada.getText();
        if (s.isBlank()) {
            // No s'ha posat nom a la dada
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error nom", "El nom posat no és vàlid", error, "error");
        } else if (textAreaInfoDada.getText().isBlank()) {
            // No hi ha contingut a la dada
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error contingut", "El contingut posat no és vàlid", error, "error");
        } else {
            ctrlPresentacio.afegeixDadaDirectament(s, textAreaInfoDada.getText(), tipus_dada);
            ctrlPresentacio.tornarVistaPrincipal();
        }
    }

    public void actionPerformed_buttonCancelAlfabet(ActionEvent event) {
        if (!textAreaInfo.getText().isBlank() || !textFieldTitolAlfabet.getText().isBlank()) {
            VistaDialeg vistaDialeg = new VistaDialeg();
            int sel = vistaDialeg.setDialeg("Avis", "Estas segur que no vols guardar els canvis?", sino, "avis");
            if (sel == 0) ctrlPresentacio.tornarVistaPrincipal();
        } else ctrlPresentacio.tornarVistaPrincipal();
    }

    public void actionPerformed_buttonCancelFreq(ActionEvent event) {
        if (!textFieldTitolFrequencia.getText().isBlank()) {
            VistaDialeg vistaDialeg = new VistaDialeg();
            int sel = vistaDialeg.setDialeg("Avis", "Estas segur que no vols guardar els canvis?", sino, "avis");
            if (sel == 0) ctrlPresentacio.tornarVistaPrincipal();
        } ctrlPresentacio.tornarVistaPrincipal();
    }

    public void actionPerformed_buttonCancelDades(ActionEvent event) {
        if (!textAreaInfoDada.getText().isBlank() || !textFieldTitolDada.getText().isBlank()) {
            VistaDialeg vistaDialeg = new VistaDialeg();
            int sel = vistaDialeg.setDialeg("Avis", "Estas segur que no vols guardar els canvis?", sino, "avis");
            if (sel == 0) ctrlPresentacio.tornarVistaPrincipal();
        } else ctrlPresentacio.tornarVistaPrincipal();
    }

    public void actionPerformed_buttonCancelTeclat(ActionEvent event) {
        if (frameVista.getCursor() != Cursor.getDefaultCursor()) frameVista.setCursor(Cursor.getDefaultCursor());
        ctrlPresentacio.tornarVistaPrincipal();
    }

    public void actionPerformed_buttonGenerarTeclat(ActionEvent event) {
        try {
            int iteracions = Integer.parseInt(iteracionsHC.getText());
            String nomTeclat = textFieldTitolTeclat.getText();
            if (nomTeclat.isBlank()) {
                // No s'ha posat nom al teclat
                VistaDialeg vistaDialeg = new VistaDialeg();
                vistaDialeg.setDialeg("Error nom", "El nom posat al teclat no és vàlid", error, "error");
            } else {
                // Es genera el teclat amb un text o una llista de frequencies
                if (rbTeclatTextLlista.isSelected() && nomDadesTeclat != null) {
                    desactivaComponentsGeneracioTeclat();
                    frameVista.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    workerGenTeclat = new SwingWorker<Void, Void>() {
                        protected Void doInBackground() throws Exception {
                            // S'ha escollit un text o una llista de freq com a dades
                            ctrlPresentacio.afegeixTeclatDades(textFieldTitolTeclat.getText(), nomDadesTeclat, metodeTeclat, iteracions);
                            frameVista.setCursor(Cursor.getDefaultCursor());
                            ctrlPresentacio.tornarVistaPrincipal();
                            return null;
                        }
                    };
                    workerGenTeclat.execute();
                }
                // Es genera un teclat amb una freq de simbols
                else if (rbTeclatFrequencia.isSelected() && nomDadesTeclat != null) {
                    desactivaComponentsGeneracioTeclat();
                    frameVista.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    // S'ha escollit un alfabet
                    if (nomAlfabetTeclat != null) {
                        workerGenTeclat = new SwingWorker<Void, Void>() {
                            protected Void doInBackground() throws Exception {
                                // S'ha escollit un alfabet
                                ctrlPresentacio.afegeixTeclatAlfFreq(textFieldTitolTeclat.getText(), nomDadesTeclat, nomAlfabetTeclat, metodeTeclat, iteracions);
                                frameVista.setCursor(Cursor.getDefaultCursor());
                                ctrlPresentacio.tornarVistaPrincipal();
                                return null;
                            }
                        };
                        workerGenTeclat.execute();
                    }
                    // No s'ha escollit cap alfabet
                    else {
                        workerGenTeclat = new SwingWorker<Void, Void>() {
                            protected Void doInBackground() throws Exception {
                                // No s'ha escollit un alfabet
                                ctrlPresentacio.afegeixTeclatFrequencia(textFieldTitolTeclat.getText(), nomDadesTeclat, metodeTeclat, iteracions);
                                ctrlPresentacio.tornarVistaPrincipal();
                                return null;
                            }
                        };
                        workerGenTeclat.execute();
                    }
                }
                else {
                    // No s'ha escollit res com a dades
                    VistaDialeg vistaDialeg = new VistaDialeg();
                    vistaDialeg.setDialeg("Error dades", "Cap opció de dades seleccionada", error, "error");
                }
            }
        } catch (Exception e) {
            VistaDialeg vistaDialeg = new VistaDialeg();
            vistaDialeg.setDialeg("Error dades", e.getMessage(), error, "error");
        }
    }

//Listeners

    private void asignar_listenersComponents() {

        buttonGuardar.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        actionPerformed_buttonGuardar(event);
                    } catch (JaExisteixException e) {
                        e.printStackTrace();
                    }
                }
            }
        );

        buttonCancel.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    actionPerformed_buttonCancelAlfabet(event);
                }
            }
        );

        ////////////////////////////// Frequencia //////////////////////////////
        
        buttonGuardarFreq.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        actionPerformed_buttonGuardarFreq(event);
                    } catch (NoTrobatException e) {
                        String[] ok = {"OK"};
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");                    
                    }
                      catch (JaExisteixException e) {
                        String[] ok = {"OK"};
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");                    
                    }
                }
            }
        );

        buttonCancelarFreq.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    actionPerformed_buttonCancelFreq(event);
                }
            }
        );
        ////////////////////////////// Dada //////////////////////////////

        buttonGuardarDada.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        actionPerformed_buttonGuardarDada(event);
                    } catch (Exception e) {
                        String[] ok = {"OK"};
                        VistaDialeg vistaDialegError = new VistaDialeg();
                        vistaDialegError.setDialeg("Error", e.getMessage(), ok, "error");    
                    }
                }
            }
        );

        buttonCancelarDada.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    actionPerformed_buttonCancelDades(event);
                }
            }
        );
                
        ////////////////////////////// Teclat //////////////////////////////

        rbTeclatTextLlista.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (!buttonSelDades.isEnabled()) buttonSelDades.setEnabled(true);
                    if (buttonSelAlfabet.isEnabled()) buttonSelAlfabet.setEnabled(false);
                }
            }
        );

        rbTeclatFrequencia.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (!buttonSelDades.isEnabled()) buttonSelDades.setEnabled(true);
                    if (!buttonSelAlfabet.isEnabled()) buttonSelAlfabet.setEnabled(true);
                }
            }
        );

        rbTeclatHC.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    metodeTeclat = "CercaLocal";
                }
            }
        );

        rbTeclatQAP.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    metodeTeclat = "BranchAndBound";
                }
            }
        );

        buttonSelDades.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (rbTeclatTextLlista.isSelected())
                        ctrlPresentacio.vistaTerciariaSeleccionaDades();
                    else if (rbTeclatFrequencia.isSelected())
                        ctrlPresentacio.vistaTerciariaSeleccionaFrequencia();
                }
            }
        );

        buttonSelAlfabet.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    ctrlPresentacio.vistaTerciariaSeleccionaAlfabet();
                }
            }
        );

        buttonGenerarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    actionPerformed_buttonGenerarTeclat(event);
                }
            }
        );

        buttonCancelarTeclat.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    actionPerformed_buttonCancelTeclat(event);
                    frameVista.setCursor(Cursor.getDefaultCursor());
                    if (workerGenTeclat != null && !workerGenTeclat.isDone()) workerGenTeclat.cancel(true);
                }
            }
        );

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                if (workerGenTeclat != null && !workerGenTeclat.isDone()) workerGenTeclat.cancel(true);
                ctrlPresentacio.tornarVistaPrincipal();
            }
        });
    }

//Inicialitzar

    private void inicialitzarComponents() {
        inicialitzar_frameVista();
        inicialitzar_panelAfegirAlfabet();
        asignar_listenersComponents();
        inicialitzar_panelAfegirTeclat();
        inicialitzar_panelAfegirFrequencia();
        inicialitzar_panelAfegirDada();
        inicialitzar_panelMostrarPuntuacio();
    }

    private void inicialitzar_frameVista() {
        frameVista.setResizable(false);
        
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts);
    }

    private void inicialitzar_panelAfegirAlfabet() {
        panel_afegirAlfabet.setLayout(new BorderLayout(10,10));
        panel_afegirAlfabet.setBorder(new EmptyBorder(20,20,20,20));
        JPanel panelInformacio = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Nom alfabet: ");
        textFieldTitolAlfabet.setText("");
        panelInformacio.add(label,BorderLayout.WEST);
        panelInformacio.add(textFieldTitolAlfabet,BorderLayout.EAST);
        panelInformacio.add(new JScrollPane(textFieldTitolAlfabet));
        panel_afegirAlfabet.add(panelInformacio, BorderLayout.NORTH);
        
        JPanel panelContingutAlfabet = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Contingut:");
        textAreaInfo.setText("");
        panelContingutAlfabet.add(label2,BorderLayout.NORTH);
        panelContingutAlfabet.add(textAreaInfo,BorderLayout.CENTER);
        textAreaInfo.setLineWrap(true);
        textAreaInfo.setWrapStyleWord(true);
        panelContingutAlfabet.add(new JScrollPane(textAreaInfo));
        panel_afegirAlfabet.add(panelContingutAlfabet, BorderLayout.CENTER);   
        
        JPanel panelOpcions = new JPanel(new FlowLayout());
        panelOpcions.add(buttonGuardar);
        panelOpcions.add(buttonCancel);
        panel_afegirAlfabet.add(panelOpcions, BorderLayout.SOUTH);
    }

    private void inicialitzar_panelAfegirFrequencia() {
        panel_afegirFrequencia.setLayout(new BorderLayout(10,10));
        panel_afegirFrequencia.setBorder(new EmptyBorder(20,20,20,20));
        JPanel panelInformacio = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Nom freqüència:  ");
        textFieldTitolFrequencia.setText("");
        panelInformacio.add(label,BorderLayout.WEST);
        panelInformacio.add(textFieldTitolFrequencia,BorderLayout.EAST);
        panelInformacio.add(new JScrollPane(textFieldTitolFrequencia));
        panel_afegirFrequencia.add(panelInformacio, BorderLayout.NORTH);

        //Panel dades
        JLabel labelDades = new JLabel("Texts/Llistes de paraules");
        panelLlistaDades.setLayout(new BorderLayout());
        panelLlistaDades.add(labelDades,BorderLayout.NORTH);
        listDades.setFixedCellHeight(25);
        listDades.setFixedCellWidth(200);
        listDades.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        panelLlistaDades.add(listDades,BorderLayout.WEST);
        panelLlistaDades.add(new JScrollPane(listDades));

        panel_afegirFrequencia.add(panelLlistaDades, BorderLayout.CENTER);

        JPanel panelOpcions = new JPanel(new FlowLayout());
        panelOpcions.add(buttonGuardarFreq);
        panelOpcions.add(buttonCancelarFreq);
        panel_afegirFrequencia.add(panelOpcions, BorderLayout.SOUTH);
    }

    private void inicialitzar_panelAfegirDada() {  
        panel_afegirDada.setLayout(new BorderLayout(10,10));
        panel_afegirDada.setBorder(new EmptyBorder(20,20,20,20));
        JPanel panelInformacio = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Nom text/llista: ");
        textFieldTitolDada.setText("");
        panelInformacio.add(label,BorderLayout.WEST);
        panelInformacio.add(textFieldTitolDada,BorderLayout.EAST);
        panelInformacio.add(new JScrollPane(textFieldTitolDada));
        panel_afegirDada.add(panelInformacio, BorderLayout.NORTH);
        
        JPanel panelContingutDades = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Contingut:");
        textAreaInfoDada.setText("");
        panelContingutDades.add(label2,BorderLayout.NORTH);
        panelContingutDades.add(textAreaInfoDada,BorderLayout.CENTER);
        textAreaInfoDada.setLineWrap(true);
        textAreaInfoDada.setWrapStyleWord(true);
        panelContingutDades.add(new JScrollPane(textAreaInfoDada));
        panel_afegirDada.add(panelContingutDades, BorderLayout.CENTER);         
        
        JPanel panelOpcions = new JPanel(new FlowLayout());
        panelOpcions.add(buttonGuardarDada);
        panelOpcions.add(buttonCancelarDada);
        panel_afegirDada.add(panelOpcions, BorderLayout.SOUTH);
    }

    private void inicialitzar_panelAfegirTeclat() {
        panel_afegirTeclat.setLayout(new GridBagLayout());
        panel_afegirTeclat.setBorder(new EmptyBorder(10,20,10,20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Nom del teclat
        JPanel panelNom = new JPanel();
        panelNom.add(new JLabel("Nom del teclat"));
        
        panelNom.add(textFieldTitolTeclat);
        panel_afegirTeclat.add(panelNom, gbc);

        // Dades per crear el teclat
        gbc.gridy = 1;
        JPanel panelDades = new JPanel(new GridBagLayout());
        GridBagConstraints gbcDades = new GridBagConstraints();
        gbcDades.anchor = GridBagConstraints.WEST;
        gbcDades.insets = new Insets(0, 2, 0, 2);
        
        gbcDades.gridx = 0;
        gbcDades.gridy = 0;
        panelDades.add(new JLabel("Dades"), gbcDades);
        grupTeclatDades.add(rbTeclatTextLlista);
        grupTeclatDades.add(rbTeclatFrequencia);
        gbcDades.gridy = 1;
        panelDades.add(rbTeclatTextLlista, gbcDades);
        gbcDades.gridy = 2;
        panelDades.add(rbTeclatFrequencia, gbcDades);
        gbcDades.gridy = 3;
        buttonSelDades.setEnabled(false);
        panelDades.add(buttonSelDades, gbcDades);
        panel_afegirTeclat.add(panelDades, gbc);

        // Alfabet
        gbc.gridy = 2;
        JPanel panelAlfabet = new JPanel();
        panelAlfabet.add(new JLabel("Alfabet (opcional)"));
        panelAlfabet.add(buttonSelAlfabet);
        panel_afegirTeclat.add(panelAlfabet, gbc);
        
        // Metode de generacio
        gbc.gridy = 3;
        JPanel panelMetode = new JPanel(new GridBagLayout());
        GridBagConstraints gbcMetode = new GridBagConstraints();
        gbcMetode.gridx = 0;
        gbcMetode.gridy = 0;
        gbcMetode.anchor = GridBagConstraints.WEST;
        gbcMetode.insets = new Insets(0, 2, 0, 2);
        
        panelMetode.add(new JLabel("Mètode de generació"), gbcMetode);
        grupTeclatMetode.add(rbTeclatHC);
        grupTeclatMetode.add(rbTeclatQAP);
        gbcMetode.gridy = 1;
        panelMetode.add(rbTeclatHC, gbcMetode);
        gbcMetode.gridy = 2;
        gbcMetode.insets = new Insets(0, 22, 0, 0);
        panelMetode.add(new JLabel("Nombre d'iteracions:"), gbcMetode);
        gbcMetode.gridy = 3;
        panelMetode.add(iteracionsHC, gbcMetode);
        gbcMetode.gridy = 4;
        gbcMetode.insets = new Insets(0, 2, 0, 2);
        panelMetode.add(rbTeclatQAP, gbcMetode);
        panel_afegirTeclat.add(panelMetode, gbc);

        // Botons generar i cancelar
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelOpcions = new JPanel();
        panelOpcions.add(buttonGenerarTeclat);
        panelOpcions.add(buttonCancelarTeclat);
        panel_afegirTeclat.add(panelOpcions, gbc);
    }

    private void inicialitzar_panelMostrarPuntuacio() {
        panel_mostrarPuntuacio.setLayout(new BorderLayout());
        panel_mostrarPuntuacio.setBorder(new EmptyBorder(20,20,20,20));
        panel_mostrarPuntuacio.add(labelPuntuacio, BorderLayout.CENTER);
    }

    // torna a activar els components necessaris del panel d’afegir teclat i posa les seleccions per defecte
    private void netejar_panelAfegirTeclat() {
        nomAlfabetTeclat = null;
        nomDadesTeclat = null;
        metodeTeclat = "CercaLocal";
        iteracionsHC.setText("20");
        textFieldTitolTeclat.setText("");
        grupTeclatDades.clearSelection();
        rbTeclatHC.setEnabled(true);
        rbTeclatHC.setSelected(true);
        rbTeclatQAP.setEnabled(true);
        rbTeclatFrequencia.setEnabled(true);
        rbTeclatTextLlista.setEnabled(true);
        buttonSelDades.setEnabled(true);
        buttonSelAlfabet.setEnabled(false);
        buttonGenerarTeclat.setEnabled(true);
        textFieldTitolTeclat.setEnabled(true);
        iteracionsHC.setEnabled(true);
    }

    // desactiva tots els components del panel d’afegir teclat menys el botó cancelar
    private void desactivaComponentsGeneracioTeclat() {
        rbTeclatHC.setEnabled(false);
        rbTeclatQAP.setEnabled(false);
        rbTeclatFrequencia.setEnabled(false);
        rbTeclatTextLlista.setEnabled(false);
        buttonSelDades.setEnabled(false);
        buttonSelAlfabet.setEnabled(false);
        buttonGenerarTeclat.setEnabled(false);
        textFieldTitolTeclat.setEnabled(false);
        iteracionsHC.setEnabled(false);
    }
}