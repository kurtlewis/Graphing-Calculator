/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;
import javax.swing.*;
import javax.swing.SpringLayout;
import javax.swing.Scrollable;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Kurt
 */
public class EquationViewPanel extends JPanel implements Scrollable {

    private ArrayList<JCheckBox> checkBoxes;
    private JButton deleteButton, cancelButton;
    private ArrayList equations;

    public EquationViewPanel(ArrayList equations) {
        this.equations = equations;
        checkBoxes = new ArrayList();
        setLayout(new SpringLayout());
        for (int i = 0; i < equations.size(); i++) {
            Equation eq = (Equation) equations.get(i);
            add(new JLabel(eq.toString()));
            JCheckBox jcb = new JCheckBox();
            checkBoxes.add(jcb);
            add(jcb);
        }
        deleteButton = new JButton("Delete Selected Equations");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedEquations();
            }
        });
        add(deleteButton);
        cancelButton = new JButton("Close");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        add(cancelButton);
        SpringUtilities.makeCompactGrid(this, this.getComponentCount() / 2, 2, 5, 5, 10, 15);
    }
    
    public void createPanel() {
        setLayout(new SpringLayout());
        for (int i = 0; i < equations.size(); i++) {
            Equation eq = (Equation) equations.get(i);
            add(new JLabel(eq.toString()));
            JCheckBox jcb = new JCheckBox();
            checkBoxes.add(jcb);
            add(jcb);
        }
        deleteButton = new JButton("Delete Selected Equations");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedEquations();
            }
        });
        add(deleteButton);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        add(cancelButton);
        SpringUtilities.makeCompactGrid(this, this.getComponentCount() / 2, 2, 5, 5, 10, 15);
    }

    private void deleteSelectedEquations() {
        for (int i = 0; i < equations.size(); i++) {
            JCheckBox checkbox = (JCheckBox)checkBoxes.get(i);
            if (checkbox.isSelected()) {
                equations.remove(i);
                checkBoxes.remove(i);
            }
        }
        closeWindow();
    }

    private void closeWindow() {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null) {
            win.dispose();
        }
    }

    public int getScrollableUnitIncrement(Rectangle rectangle, int orientation, int direction) {
        return 5;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 5;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return new Dimension(this.getPreferredSize());
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }
}
