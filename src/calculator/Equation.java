/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kurt
 */
public class Equation {

    private ArrayList points;
    private double[] constants = new double[20];
    private Color color;
    private final int NULL = 0, CONSTANT = 1, VARIABLE = 2, ADDITION = 3, SUBTRACTION = 4, MULTIPLICATION = 5, DIVISION = 6, POWER = 7;
    private final int P_OPEN = 8, P_CLOSE = 9;
    private int[] equation = new int[200];
    private int equation_length, constants_length;
    private String entry;
    private int equation_index, constants_index;

    public Equation() {
        color = Color.GREEN;
        points = new ArrayList();
    }

    public Equation(String entry, Color color) throws InvalidEquationException{
        points = new ArrayList();
        this.color = color;
        this.entry = entry;

        //parsing the equation
        //adding parentheses where necessary for the recursive solving algorithm
        entry = "( ( " + entry;
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == '+' || (entry.charAt(i) == '-' && entry.charAt(i + 1) == ' ')) {
                entry = entry.substring(0, i - 1) + " ) " + entry.charAt(i) + " ( " + entry.substring(i + 2);
                i += 4;
            }
        }
        entry += " ) ) ";
        
        //fix any instances of capital letters
        while (entry.indexOf("X") != -1) {
            entry = entry.replaceAll("X", "x");
        }       
        
        //remove extra spaces
        while (entry.indexOf("  ") != -1) {
            entry = entry.replaceAll("  ", " ");
        }
        
        //negative varialbes being rewritten as the correct format
        entry = entry.replaceAll("-x", "( -1 * x )");
        
        
        System.out.println(entry);
        constants_length = 0;
        int index1 = 0, index2 = 0;
        equation_length = 0;
        boolean entered, alternation = true;
        //begin indexing the different parts of the equation
        do {
            if (entry.charAt(index2) == ' ') {
                entered = false;
                String s;
                s = entry.substring(index1, index2);
                System.out.println(s);
                if (!entered && s.equals("x")) {
                    equation[equation_length] = VARIABLE;
                    entered = true;
                    if (!alternation) {
                        throw new InvalidEquationException("Missing Opperand");
                    } else {
                        alternation = false;
                    }
                }
                if (!entered && s.equals("+")) {
                    equation[equation_length] = ADDITION;
                    entered = true;
                    if (alternation) {
                        throw new InvalidEquationException("Missing variable or constant");
                    }  else {
                        alternation = true;
                    }
                }
                if (!entered && s.equals("-")) {
                    equation[equation_length] = SUBTRACTION;
                    entered = true;
                    if (alternation) {
                        throw new InvalidEquationException("Missing variable or constant");
                    } else {
                        alternation = true;
                    }
                }
                if (!entered && s.equals("*")) {
                    equation[equation_length] = MULTIPLICATION;
                    entered = true;
                    if (alternation) {
                        throw new InvalidEquationException("Missing variable or constant");
                    } else {
                        alternation = true;
                    }
                }
                if (!entered && s.equals("/")) {
                    equation[equation_length] = DIVISION;
                    entered = true;
                    if (alternation) {
                        throw new InvalidEquationException("Missing variable or constant");
                    } else {
                        alternation = true;
                    }
                }
                if (!entered && s.equals("^")) {
                    throw new InvalidEquationException("<html>Try re-entering the term to a power manually as an enumeration.<br>For example ( x + 5 ) ^ 3 would become ( ( x + 5 ) * ( x + 5 ) * ( x + 5 ) ).<br>Using copy + paste can expedite this process.</html>");
                    
                }
                if (!entered && s.equals("(")) {
                    equation[equation_length] = P_OPEN;
                    entered = true;
                }
                if (!entered && s.equals(")")) {
                    equation[equation_length] = P_CLOSE;
                    entered = true;
                }
                if (!entered) {
                    try {
                        constants[constants_length] = Double.parseDouble(s);
                        constants_length++;
                        equation[equation_length] = CONSTANT;
                        if (!alternation) {
                            throw new InvalidEquationException("Missing Opperand");
                        } else {
                            alternation = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entry Error");
                        throw new InvalidEquationException("Unrecognized entry: " + s);
                    }
                }
                equation_length++;
                index1 = index2 + 1;
                index2++;
            } else {
                index2++;
            }
        } while (index2 < entry.length());
        for (int i = 0; i < equation.length; i++) {
            if (equation[i] != NULL) {
                System.out.println(equation[i]);
            }
        }
    }

    public void graph(double minX, double minY, double maxX, double maxY, double xScale, double yScale) {
        points = new ArrayList();
        for (double i = minX * xScale; i < maxX * xScale; i++) {
            points.add(new Point((int) (i), (int) (-getValue(i / xScale) * yScale)));
        }
    }

    public double getValue(double x) {
        equation_index = 0;
        constants_index = 0;
        return function(x);
    }

    //recursively solve the function based on parenthesis
    public double function(double x) {
        double output = 0;
        do {
            switch (equation[equation_index]) {
                case CONSTANT: 
                    output = constants[constants_index];
                    constants_index++;
                    equation_index++;
                    break;
                case VARIABLE:
                    output = x;
                    equation_index++;
                    break;
                case ADDITION:
                    equation_index++;
                    switch (equation[equation_index]) {
                        case CONSTANT:
                            output += constants[constants_index];
                            constants_index++;
                            equation_index++;
                            break;
                        case VARIABLE:
                            output += x;
                            equation_index++;
                            break;
                        case P_OPEN:
                            equation_index++;
                            output += function(x);
                            break;

                    }
                    break;
                case SUBTRACTION:
                    equation_index++;
                    switch (equation[equation_index]) {
                        case CONSTANT:
                            output -= constants[constants_index];
                            constants_index++;
                            equation_index++;
                            break;
                        case VARIABLE:
                            output -= x;
                            equation_index++;
                            break;
                        case P_OPEN:
                            equation_index++;
                            output -= function(x);
                            break;

                    }
                    break;
                case MULTIPLICATION:
                    equation_index++;
                    switch (equation[equation_index]) {
                        case CONSTANT:
                            output *= constants[constants_index];
                            constants_index++;
                            equation_index++;
                            break;
                        case VARIABLE:
                            output *= x;
                            equation_index++;
                            break;
                        case P_OPEN:
                            equation_index++;
                            output *= function(x);
                            break;

                    }
                    break;
                case DIVISION:
                    equation_index++;
                    switch (equation[equation_index]) {
                        case CONSTANT:
                            output /= constants[constants_index];
                            constants_index++;
                            equation_index++;
                            break;
                        case VARIABLE:
                            output /= x;
                            equation_index++;
                            break;
                        case P_OPEN:
                            equation_index++;
                            output /= function(x);
                            break;

                    }
                    break;
                case P_OPEN:
                    equation_index ++;
                    output = function(x);
                    break;
                case P_CLOSE:
                    equation_index++;
                    return output;
            }
        } while (equation_index < equation_length);
        return output;
    }

    public void paint(Graphics2D g2d, int offsetX, int offsetY) {
        g2d.setColor(color);
        Point p1, p2;
        p1 = (Point) points.get(0);
        for (int i = 1; i < points.size(); i++) {
            p2 = (Point) points.get(i);
            g2d.drawLine(p1.x + offsetX, p1.y + offsetY, p2.x + offsetX, p2.y + offsetY);
            p1 = p2;
        }
    }
    
    @Override
    public String toString() {
        return entry;
    }
}
