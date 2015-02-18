/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import javax.swing.SwingUtilities;

/**
 *
 * @author Kurt Lewis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //create frame once initial thread has completed
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Frame();
            }
        });
    }
}
