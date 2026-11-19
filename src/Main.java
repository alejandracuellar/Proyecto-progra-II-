import view.VistaMenuPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            VistaMenuPrincipal vista = new VistaMenuPrincipal();
            vista.setVisible(true);
        });
    }

    //Puedes arreglar lo de si si ponemos un json de ubicaciones por fis
}