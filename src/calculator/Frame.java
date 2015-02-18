/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.Window;
import java.awt.Dialog.ModalityType;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import java.awt.Color;

/**
 *
 * @author Kurt Lewis
 */
public class Frame extends JFrame {

    private Board board;
    private String formattingRules = "i. Acceptable Characters:\n"
            + "    any real rational number, “x”[variable], “+”, “-”, “*”, “/”, “(“, “)”, “ ”\n"
            + "ii. All parts to an equation must be separated by a space\n"
            + "iii. All open parentheses[“(”] must be accompanied by an eventual close parentheses[“)”]\n"
            + "iv. Examples :\n"
            + "    Unacceptable: “5x+2”\n"
            + "    Acceptable: “5 * x + 2”\n"
            + "    Unacceptable: “x^2”\n"
            + "    Acceptable: “x * x”\n"
            + "    Unacceptable: “-x + 5(1/2)”\n"
            + "    Acceptable: “-1 * x + 5 * ( 1 / 2 )”\n";
    private String help = "Created by Kurt Lewis\n\n"
            + "Operation Instruction\n"
            + "*The following instructions can be found in application. Find them in “Tools” -> “Help”.*\n"
            + "I. Introduction\n"
            + "     This graphing calculator is primarily driven through its menu system, located at the top of the window. It can be used to graph most equations that follow a specific entry format. The view can be resized and scaled for easier viewing of any entered equations.\n"
            + "II. Equations\n"
            + "     A. Types\n"
            + "         The graphing calculator can graph most linear equations. Through the manual rewriting of powers, it can also graph many quadratic, cubic, and quartic equations. It cannot graph trigonometric and logarithmic equations.\n"
            + "II. The Menus\n"
            + "    A. File\n"
            + "         1. Reset\n"
            + "             The reset option clears all entered equations, and resets the window to its default values.\n"
            + "         2. Close\n"
            + "             The close option prompts the user to confirm, then cleanly and completely exits the program if okayed.\n"
            + "    B. New Equation\n"
            + "         1. Add Equation\n"
            + "                 Following the set of rules found below for formatting, equations can be entered and graphed. A color can be selected, and the addition of an equation can be canceled at any time.\n"
            + "                 a. Formatting Rules\n"
            + "                     *Formatting rules can also be found in “Formatting Quick"
            + "                     Reference”*\n"
            + "                     i. Acceptable Characters: any real rational number, “x”[variable], “+”, “-”, “*”, “/”, “(“, “)”,  “ ”\n"
            + "                     ii. All parts to an equation must be separated by a space\n"
            + "                     iii. All open parentheses[“(”] must be accompanied by an eventual close parentheses[“)”]\n"
            + "                     iv. Examples :\n"
            + "                         Unacceptable: “5x+2”\n"
            + "                         Acceptable: “5 * x + 2”\n"
            + "                         Unacceptable: “x^2”\n"
            + "                         Acceptable: “x * x”\n"
            + "                         Unacceptable: “-x + 5(1/2)”\n"
            + "                         Acceptable: “-1 * x + 5 * ( 1 / 2 )”\n"
            + "             2. List Equations\n"
            + "                 Creates a list of all equations, organized by order of entry. By selecting the checkbox next to an equation or equations, they can be removed from the graphing calculator, allowing for the removal of specific or incorrect equations.\n"
            + "    C. View\n"
            + "             1. Set View\n"
            + "                 Allows for the input of minimum and maximum X and Y values. Upon confirming, these values will become the visible area for the screen. \n"
            + "             2. Center\n"
            + "                 Centers the screen around the origin, maintaining the distance between the minimum and maximum X and Y values.\n"
            + "             3. Zoom In\n"
            + "                 Zooms in on the center of the screen by a factor of 20%.\n"
            + "             4. Zoom Out\n"
            + "                 Zooms out on the center of the screen by a factor of 20%.\n"
            + "     D. Tools\n"
            + "             1. Help\n"
            + "                 Displays a dialog panel containing  this outline.\n"
            + "             2. Formatting Quick Reference\n"
            + "                 Displays a brief summary of the rules for formatting equations for graphing.\n"
            + "             3. Toggle Domain and Range\n"
            + "                 Toggles the display of the domain and range.\n"
            + "             4. Toggle Hashmarks\n"
            + "                 Toggles the display of hash marks on the X and Y axes\n"
            + "III. Controls\n"
            + "    A.  Key Controls\n"
            + "         1. Arrow Keys\n"
            + "             Used for scrolling up, right, down, and left.\n"
            + "         2. Z Key\n"
            + "             Used to zoom in.\n"
            + "         3. X Key\n"
            + "             Used to zoom out.\n"
            + "    B. Mouse Controls\n"
            + "         1. Screen Clicks\n"
            + "             By clicking on the edges of the screen, the graph can be scrolled in the direction clicked.\n"
            + "         2. Mouse Wheel\n"
            + "             By scrolling with the mouse wheel the graph can be zoomed.";

    public Frame() {
        board = new Board();
        add(board);

        //main parts
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Graphing Calculator");
        setSize(720, 660);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        //create all resources for menu
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        //menu
        menuBar = new JMenuBar();

        //"File" Menu
        menu = new JMenu("File");
        //menu.setMnemonic('a');
        menuBar.add(menu);
        menuItem = new JMenuItem("Reset");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetActionPerformed();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Close", KeyEvent.VK_ESCAPE);
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeActionPerformed();
            }
        });
        menu.add(menuItem);

        //"New" Menu
        menu = new JMenu("Equations");
        menuBar.add(menu);
        menuItem = new JMenuItem("Add New");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEquation();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("List Equations");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEquationViewPanel();
            }
        });
        menu.add(menuItem);

        //View Menu
        menu = new JMenu("View");
        menuBar.add(menu);
        menuItem = new JMenuItem("Set View");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setViewPanel();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Center");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                centerBoard();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Zoom In");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomIn();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Zoom Out");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoomOut();

            }
        });
        menu.add(menuItem);

        //"Tools" Menu
        menu = new JMenu("Tools");
        menuBar.add(menu);
        menuItem = new JMenuItem("Help");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewHelp();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Formatting Quick Reference");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewFormattingQuickReference();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Toggle Domain and Range display");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleShowInfo();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Toggle Hashmarks");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleHashmarks();
            }
        });
        menu.add(menuItem);


        setJMenuBar(menuBar);

        //view help upon the first time opening the app
        viewHelp();
    }

    //methods for menu actions go here
    private void resetActionPerformed() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == 0) {
            board.resetGameState();
        }
    }

    private void closeActionPerformed() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == 0) {
            dispose();
            System.exit(0);
        }
    }

    private void addEquation() {
        //opens window to add an equation
        JDialogAddEquationPanel createEntityPanel = new JDialogAddEquationPanel();
        JDialog dialog = new JDialog(this, "Add Equation", ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(createEntityPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        if (createEntityPanel.isOkayed()) {
            board.addEquation(createEntityPanel.getCreatedEquation());
        }
    }

    private void showEquationViewPanel() {
        //creates window to list equations
        EquationViewPanel panel = new EquationViewPanel(board.getEquations());
        JDialog dialog = new JDialog(this, "Equations", ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void setViewPanel() {
        //creates window to change the graph's dimensions
        JDialogSetView setViewPanel = new JDialogSetView();
        JDialog dialog = new JDialog(this, "Set View", ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(setViewPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        if (setViewPanel.isOkayed()) {
            setViewPanel.setBoardView();
        }
    }

    public void viewHelp() {
        //creates window to view help
        HelpJPanel helpPanel = new HelpJPanel();
        JDialog dialog = new JDialog(this, "Help", ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(helpPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void viewFormattingQuickReference() {
        //creates window to view formatting quick reference
        JOptionPane.showMessageDialog(this, formattingRules);
    }

    public void centerBoard() {
        board.center();
    }

    public void zoomIn() {
        board.zoomIn();
    }

    public void zoomOut() {
        board.zoomOut();
    }

    public void toggleShowInfo() {
        board.toggleShowInfo();
    }
    
    public void toggleHashmarks() {
        board.toggleHashmarks();
    }

    //end methods for menu actions
    //panel for creating equations
    private class JDialogAddEquationPanel extends JPanel {

        private JTextField entry;
        private JButton okButton, cancelButton, colorButton;
        private boolean ok;
        private Color color;
        private Equation equation;

        public JDialogAddEquationPanel() {
            setLayout(new SpringLayout());
            okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    okButtonAction();
                }
            });
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelButtonAction();
                }
            });

            colorButton = new JButton("Select");
            colorButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    colorButtonActionPerformed();
                }
            });


            //the number of componenets should always be an even number in sets of two
            add(new JLabel("Equation:  y ="));
            add(entry = new JTextField(20));

            add(new JLabel("Color:"));
            add(colorButton);

            add(okButton);
            add(cancelButton);

            //set the grid size here equal to the number of components / 2
            SpringUtilities.makeCompactGrid(this, this.getComponentCount() / 2, 2, 5, 5, 10, 15);
            ok = false;
            color = Color.BLACK;
        }

        public Equation getCreatedEquation() {
            return equation;
        }

        public boolean isOkayed() {
            return ok;
        }

        private void cancelButtonAction() {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win != null) {
                win.dispose();
            }
        }

        private void colorButtonActionPerformed() {
            color = JColorChooser.showDialog(this, "Choose a Color for Your Equation", color);
        }

        private void okButtonAction() {
            //set variables here
            ok = true;
            try {
                equation = new Equation(entry.getText(), color);
                Window win = SwingUtilities.getWindowAncestor(this);
                if (win != null) {
                    win.dispose();
                }
            } catch (InvalidEquationException e) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "<html><center>Please fix any entry errors,<br>making sure to follow the conventions<br>outlined in the formatting quick reference guide.<br>" + e.toString() + "</center></html>");
                ok = false;
            }
        }
    }

    //window to adjust the view
    private class JDialogSetView extends JPanel {

        private JTextField minXTextField, minYTextField, maxXTextField, maxYTextField;
        private JButton okButton, cancelButton;
        private boolean ok;
        private double minX, minY, maxX, maxY;

        public JDialogSetView() {
            setLayout(new SpringLayout());
            okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    okButtonAction();
                }
            });
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cancelButtonAction();
                }
            });

            //the number of componenets should always be an even number in sets of two

            add(new JLabel("Min X"));
            add(minXTextField = new JTextField("" + board.getMinX()));

            add(new JLabel("Min Y"));
            add(minYTextField = new JTextField("" + board.getMinY()));

            add(new JLabel("Max X"));
            add(maxXTextField = new JTextField("" + board.getMaxX()));

            add(new JLabel("Max Y"));
            add(maxYTextField = new JTextField("" + board.getMaxY()));

            add(okButton);
            add(cancelButton);

            //set the grid size here equal to the number of components / 2
            SpringUtilities.makeCompactGrid(this, this.getComponentCount() / 2, 2, 5, 5, 10, 15);
            ok = false;
        }

        public void setBoardView() {
            board.setView(minX, minY, maxX, maxY);
        }

        public boolean isOkayed() {
            return ok;
        }

        private void okButtonAction() {
            //set variables here
            try {
                minX = Double.parseDouble(minXTextField.getText());
                minY = Double.parseDouble(minYTextField.getText());
                maxX = Double.parseDouble(maxXTextField.getText());
                maxY = Double.parseDouble(maxYTextField.getText());
                if (maxX > minX || maxY > minY) {
                    ok = true;
                    Window win = SwingUtilities.getWindowAncestor(this);
                    if (win != null) {
                        win.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "The maximum values must be larger than the minimum values.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Please fix any entry errors.");
                ok = false;
            }
        }

        private void cancelButtonAction() {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win != null) {
                win.dispose();
            }
        }
    }

    //window to show help screen
    private class HelpJPanel extends JPanel {

        private JTextArea textArea;

        public HelpJPanel() {
            //setLayout(new SpringLayout());
            textArea = new JTextArea(20, 50);
            textArea.setText(help);
            textArea.setLineWrap(false);
            JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            textArea.setCaretPosition(0);
            textArea.setEditable(false);
            add(scroll);
        }
    }
}
