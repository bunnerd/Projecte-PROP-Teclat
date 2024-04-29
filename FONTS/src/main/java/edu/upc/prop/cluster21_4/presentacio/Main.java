package edu.upc.prop.cluster21_4.presentacio;

public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (
            new Runnable() {
                public void run() {
                    ControladorPresentacio ctrlPresentacio;
                    try {
                        ctrlPresentacio = new ControladorPresentacio();
                        ctrlPresentacio.inicialitzarPresentacio();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        );
    }
}
