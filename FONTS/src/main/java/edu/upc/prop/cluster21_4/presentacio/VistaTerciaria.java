package edu.upc.prop.cluster21_4.presentacio;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.*;

public class VistaTerciaria {
    private ControladorPresentacio ctrlPresentacio;

    private JFrame frameVista = new JFrame();
    private JPanel panelContinguts = new JPanel();
    private JPanel panelActual = new JPanel();

    private JPanel panelLlistaDades = new JPanel();   
    private JPanel panelOpcionsDades = new JPanel();
    private DefaultListModel<String> modelDades = new DefaultListModel<String>();
    private JList<String> listDades = new JList<String>(modelDades);
    private JButton buttonSeleccionarDades = new JButton("Seleccionar");

    private JPanel panelLlistaTexts = new JPanel();   
    private JPanel panelOpcionsTexts = new JPanel();
    private DefaultListModel<String> modelTexts = new DefaultListModel<String>();
    private JList<String> listTexts = new JList<String>(modelTexts);
    private JButton buttonSeleccionarTexts = new JButton("Seleccionar");

    private JPanel panelLlistaFreq = new JPanel();   
    private JPanel panelOpcionsFreq = new JPanel();
    private DefaultListModel<String> modelFreq = new DefaultListModel<String>();
    private JList<String> listFreq = new JList<String>(modelFreq);
    private JButton buttonSeleccionarFreq = new JButton("Seleccionar");

    private JPanel panelLlistaAlfabets = new JPanel();   
    private JPanel panelOpcionsAlfabets = new JPanel();
    private DefaultListModel<String> modelAlfabets = new DefaultListModel<String>();
    private JList<String> listAlfabets = new JList<String>(modelAlfabets);
    private JButton buttonSeleccionarAlfabets = new JButton("Seleccionar");

    public VistaTerciaria (ControladorPresentacio ctrlPresentacio) throws Exception {
        this.ctrlPresentacio = ctrlPresentacio;
        inicialitzarComponents();
    }

    private void ferVisible() {
        frameVista.pack();
        frameVista.setLocationRelativeTo(null);
        frameVista.setVisible(true);
    }

    public void ferInvisible() {
        frameVista.setVisible(false);
    }

    public void seleccionarDades() {
        ArrayList<String> list = ctrlPresentacio.getNomsDades();
        modelDades.clear();
        for (String s : list) {
            modelDades.addElement(s);
        }
        panelContinguts.remove(panelActual);
        panelContinguts.add(panelLlistaDades);
        panelActual = panelLlistaDades;
        ferVisible();
    }

    public void seleccionarText() {
        ArrayList<String> list = ctrlPresentacio.getNomsTexts();
        modelTexts.clear();
        for (String s : list) {
            modelTexts.addElement(s);
        }
        panelContinguts.remove(panelActual);
        panelContinguts.add(panelLlistaTexts);
        panelActual = panelLlistaTexts;
        ferVisible();
    }

    public void seleccionarFrequencia() {
        ArrayList<String> list = ctrlPresentacio.getNomsFrequencies();
        modelFreq.clear();
        for (String s : list) {
            modelFreq.addElement(s);
        }
        panelContinguts.remove(panelActual);
        panelContinguts.add(panelLlistaFreq);
        panelActual = panelLlistaFreq;
        ferVisible();
    }

    public void seleccionarAlfabet() {
        ArrayList<String> list = ctrlPresentacio.getNomsAlfabets();
        modelAlfabets.clear();
        for (String s : list) {
            modelAlfabets.addElement(s);
        }
        panelContinguts.remove(panelActual);
        panelContinguts.add(panelLlistaAlfabets);
        panelActual = panelLlistaAlfabets;
        ferVisible();
    }

    //Listeners

    private void mouse_listeners() {
        listDades.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    ctrlPresentacio.vistaSecundariaSetDadesTeclat(listDades.getSelectedValue());
                    ctrlPresentacio.tornarVistaSecundaria();
                }
            }
        });

        listTexts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    ctrlPresentacio.vistaPrincipalSetTextAAvaluar(listTexts.getSelectedValue());
                    ferInvisible();
                }
            }
        });

        listFreq.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    ctrlPresentacio.vistaSecundariaSetDadesTeclat(listFreq.getSelectedValue());
                    ctrlPresentacio.tornarVistaSecundaria();
                }
            }
        });

        listAlfabets.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    ctrlPresentacio.vistaSecundariaSetAlfabetTeclat(listAlfabets.getSelectedValue());
                    ctrlPresentacio.tornarVistaSecundaria();
                }
            }
        });
    }

    private void asignar_listenersComponents() {
        buttonSeleccionarDades.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    ctrlPresentacio.vistaSecundariaSetDadesTeclat(listDades.getSelectedValue());
                    ctrlPresentacio.tornarVistaSecundaria();
                }
            }
        );

        buttonSeleccionarTexts.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    ctrlPresentacio.vistaPrincipalSetTextAAvaluar(listTexts.getSelectedValue());
                    ferInvisible();
                }
            }
        );

        buttonSeleccionarFreq.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    ctrlPresentacio.vistaSecundariaSetDadesTeclat(listFreq.getSelectedValue());
                    ctrlPresentacio.tornarVistaSecundaria();
                }
            }
        );

        buttonSeleccionarAlfabets.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    ctrlPresentacio.vistaSecundariaSetAlfabetTeclat(listAlfabets.getSelectedValue());
                    ctrlPresentacio.tornarVistaSecundaria();
                }
            }
        );

        frameVista.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (panelActual == panelLlistaTexts) ctrlPresentacio.tornarVistaPrincipal();
                else ctrlPresentacio.tornarVistaSecundaria();
            }
        });
    }
    
    //Inicialitzar

    private void inicialitzarComponents() {
        inicialitzar_frameVista();
        asignar_listenersComponents();
        mouse_listeners();
        inicialitzar_panelDades();
        inicialitzar_panelTexts();
        inicialitzar_panelFrequencies();
        inicialitzar_panelAlfabets();
    }

    private void inicialitzar_frameVista() {
        frameVista.setMinimumSize(new Dimension(400,300));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContinguts);
    }

    private void inicialitzar_panelDades() {
        JLabel labelDades = new JLabel("Texts/Llistes de paraules");
        panelLlistaDades.setLayout(new BorderLayout(10,10));
        panelLlistaDades.setBorder(new EmptyBorder(10,20,10,20));
        panelLlistaDades.add(labelDades,BorderLayout.NORTH);
        panelLlistaDades.add(panelOpcionsDades,BorderLayout.EAST);
        listDades.setFixedCellHeight(25);
        listDades.setFixedCellWidth(200);
        listDades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelLlistaDades.add(listDades,BorderLayout.WEST);
        panelLlistaDades.add(new JScrollPane(listDades));

        panelOpcionsDades.setLayout(new BoxLayout(panelOpcionsDades, BoxLayout.Y_AXIS));
        panelOpcionsDades.add(buttonSeleccionarDades);
    }

    private void inicialitzar_panelTexts() {
        JLabel labelText = new JLabel("Texts");
        panelLlistaTexts.setLayout(new BorderLayout(10,10));
        panelLlistaTexts.setBorder(new EmptyBorder(10,20,10,20));
        panelLlistaTexts.add(labelText,BorderLayout.NORTH);
        panelLlistaTexts.add(panelOpcionsTexts,BorderLayout.EAST);
        listTexts.setFixedCellHeight(25);
        listTexts.setFixedCellWidth(200);
        listTexts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelLlistaTexts.add(listTexts,BorderLayout.WEST);
        panelLlistaTexts.add(new JScrollPane(listTexts));

        panelOpcionsTexts.setLayout(new BoxLayout(panelOpcionsTexts, BoxLayout.Y_AXIS));
        panelOpcionsTexts.add(buttonSeleccionarTexts);
    }

    private void inicialitzar_panelFrequencies() {
        JLabel labelAlfabet = new JLabel("Freqüències");
        panelLlistaFreq.setLayout(new BorderLayout(10,10));
        panelLlistaFreq.setBorder(new EmptyBorder(10,20,10,20));
        panelLlistaFreq.add(labelAlfabet, BorderLayout.NORTH);
        panelLlistaFreq.add(panelOpcionsFreq,BorderLayout.EAST);
        listFreq.setFixedCellHeight(25);
        listFreq.setFixedCellWidth(200);
        listFreq.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelLlistaFreq.add(listFreq,BorderLayout.WEST);
        panelLlistaFreq.add(new JScrollPane(listFreq));

        panelOpcionsFreq.setLayout(new BoxLayout(panelOpcionsFreq, BoxLayout.Y_AXIS));
        panelOpcionsFreq.add(buttonSeleccionarFreq);
    }

    private void inicialitzar_panelAlfabets() {
        JLabel labelAlfabet = new JLabel("Alfabets");
        panelLlistaAlfabets.setLayout(new BorderLayout(10,10));
        panelLlistaAlfabets.setBorder(new EmptyBorder(10,20,10,20));
        panelLlistaAlfabets.add(labelAlfabet, BorderLayout.NORTH);
        listAlfabets.setFixedCellHeight(25);
        listAlfabets.setFixedCellWidth(200);
        listAlfabets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelLlistaAlfabets.add(listAlfabets,BorderLayout.WEST);
        panelLlistaAlfabets.add(new JScrollPane(listAlfabets));

        panelOpcionsAlfabets.setLayout(new BoxLayout(panelOpcionsAlfabets, BoxLayout.Y_AXIS));
        panelOpcionsAlfabets.add(buttonSeleccionarAlfabets);
        panelLlistaAlfabets.add(panelOpcionsAlfabets,BorderLayout.EAST);
    }
}