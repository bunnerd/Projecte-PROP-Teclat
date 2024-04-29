package edu.upc.prop.cluster21_4.presentacio;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VistaDialeg {

    public VistaDialeg() {}

    public int setDialeg(String titol, String text, String[] botons, String tipus) {
        int oTipus = 1;
        switch (tipus) {
            case "error": oTipus = JOptionPane.ERROR_MESSAGE; break;
            case "informacio": oTipus = JOptionPane.INFORMATION_MESSAGE; break;
            case "avis": oTipus = JOptionPane.WARNING_MESSAGE; break;
            case "pregunta": oTipus = JOptionPane.QUESTION_MESSAGE; break;
            case "missatge": oTipus = JOptionPane.PLAIN_MESSAGE; break;
        }

        JOptionPane optionPane = new JOptionPane(text, oTipus);
        optionPane.setOptions(botons);
        JDialog dialogOptionPane = optionPane.createDialog(new JFrame(),titol);
        dialogOptionPane.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogOptionPane.pack();
        dialogOptionPane.setVisible(true);

        if (optionPane.getValue() == null) return -1;
        String ssel = (String) optionPane.getValue();
        int isel;
        for (isel = 0; isel < botons.length && !botons[isel].equals(ssel); ++isel);
        return isel;
    }
}
