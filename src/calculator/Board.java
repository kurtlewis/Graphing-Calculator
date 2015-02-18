/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

/**
 *
 * @author Kurt Lewis
 */
public class Board extends JPanel{

    private Timer timer;
    private int B_WIDTH = 700, B_HEIGHT = 600;
    private double minX, minY, maxX, maxY, xScale, yScale;
    private boolean paused, show_info, show_hashmarks;
    private ArrayList equations;

    public Board() {
        //add listeners for user actions
        MAdapter madapter = new MAdapter();
        addKeyListener(new KAdapter());
        addMouseListener(madapter);
        addMouseWheelListener(madapter);
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);

        setSize(B_WIDTH, B_HEIGHT);

        resetGameState();
        resetTimer();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();
    }

    public void resetGameState() {
        equations = new ArrayList();
        minX = -100;
        minY = -100;
        maxX = 100;
        maxY = 100;
        xScale = B_WIDTH / (Math.abs(maxX) + Math.abs(minX));
        yScale = B_HEIGHT / (Math.abs(maxY) + Math.abs(minY));
        show_info = true;
        show_hashmarks = true;
    }
    
    public ArrayList getEquations() {
        return equations;
    }

    public void addEquation(Equation equation) {
        equation.graph(minX, minY, maxX, maxY, xScale, yScale);
        equations.add(equation);
    }

    public void graphEquations() {
        for (int i = 0; i < equations.size(); i++) {
            Equation equation = (Equation) equations.get(i);
            equation.graph(minX, minY, maxX, maxY, xScale, yScale);
        }
    }

    private void resetTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new MainTask(), 100, 40);
    }

    public void setView(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        xScale = B_WIDTH / (Math.abs(maxX) + Math.abs(minX));
        yScale = B_HEIGHT / (Math.abs(maxY) + Math.abs(minY));
    }

    public void center() {
        maxX = (Math.abs(maxX) + Math.abs(minX)) / 2;
        minX = -maxX;
        maxY = (Math.abs(maxY) + Math.abs(minY)) / 2;
        minY = -maxY;
    }

    public void zoomIn() {
        //by a factor of 20%, so 10% on each side
        double zoom;
        zoom = (Math.abs(maxX) + Math.abs(minX)) / 10;
        minX += zoom;
        maxX -= zoom;
        zoom = (Math.abs(minY) + Math.abs(maxY)) / 10;
        minY += zoom;
        maxY -= zoom;
        xScale = B_WIDTH / (Math.abs(maxX) + Math.abs(minX));
        yScale = B_HEIGHT / (Math.abs(maxY) + Math.abs(minY));
    }

    public void zoomOut() {
        //by a factor of 10%, so 5% on each side
        double zoom;
        zoom = (Math.abs(maxX) + Math.abs(minX)) / 20;
        minX -= zoom;
        maxX += zoom;
        if (minX < -1000000000) {
            minX = -1000000000;
        }
        if (maxX > 1000000000) {
            maxX = 1000000000;
        }
        zoom = (Math.abs(minY) + Math.abs(maxY)) / 20;
        minY -= zoom;
        maxY += zoom;
        if (minY < -1000000000) {
            minY = -1000000000;
        }
        if (maxY > 1000000000) {
            maxY = 1000000000;
        }
        xScale = B_WIDTH / (Math.abs(maxX) + Math.abs(minX));
        yScale = B_HEIGHT / (Math.abs(maxY) + Math.abs(minY));
        
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }
    
    public void toggleShowInfo() {
        if (show_info) {
            show_info = false;
        } else {
            show_info = true;
        }
    }
    
    public void toggleHashmarks() {
        if (show_hashmarks) {
            show_hashmarks = false;
        } else {
            show_hashmarks = true;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        int xOffset = (int) (-minX * xScale), yOffset = B_HEIGHT - (int) (-minY * yScale);
        int xInterval = B_WIDTH / ((int) (Math.abs(minX - maxX)) + 1) + 1, yInterval = B_HEIGHT / ((int) (Math.abs(minY - maxY)) + 1) + 1;
        //draw axes
        g2d.drawLine(xOffset, 0, xOffset, B_HEIGHT);
        g2d.drawLine(0, yOffset, B_WIDTH, yOffset);
        
        //draw hashmarks
        if (show_hashmarks) {
        for (int i = 0; i <= B_HEIGHT / yInterval; i++) {
            g2d.drawLine(xOffset - 1, i * yInterval, xOffset + 1, i * yInterval);
        }
        for (int i = 0; i <= B_WIDTH / xInterval; i++) {
            g2d.drawLine(i * xInterval, yOffset - 1, i * xInterval, yOffset + 1);
        }
        }
        
        //draw domain and range if the option is selected
        if (show_info) {
            g2d.drawString("Domain: " + "[" + Math.round(minX) + ", " + Math.round(maxX) + "]", 5, 15);
            g2d.drawString("Range: " + "[" + Math.round(minY) + ", " + Math.round(maxY) + "]", 5, 30);
        }


        //paint equations here
        for (int i = 0; i < equations.size(); i++) {
            Equation equation = (Equation) equations.get(i);
            equation.paint(g2d, xOffset, yOffset);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private class MainTask extends TimerTask {

        @Override
        public void run() {
            //graph the equations here and repaint the screen
            graphEquations();
            repaint();
        }
    }

    private class KAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                if (paused) {
                    paused = false;
                } else {
                    paused = true;
                }
            }

            if (key == KeyEvent.VK_UP) {
                double scroll = (Math.abs(maxY) + Math.abs(minY)) / 20;
                minY += scroll;
                maxY += scroll;
            }

            if (key == KeyEvent.VK_DOWN) {
                double scroll = (Math.abs(maxY) + Math.abs(minY)) / 20;
                minY -= scroll;
                maxY -= scroll;
            }

            if (key == KeyEvent.VK_LEFT) {
                double scroll = (Math.abs(maxX) + Math.abs(minX)) / 20;
                minX -= scroll;
                maxX -= scroll;
            }

            if (key == KeyEvent.VK_RIGHT) {
                double scroll = (Math.abs(maxX) + Math.abs(minX)) / 20;
                minX += scroll;
                maxX += scroll;
            }

            if (key == KeyEvent.VK_Z) {
                zoomIn();
            }

            if (key == KeyEvent.VK_X) {
                zoomOut();
            }
        }
    }

    private class MAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getX() < 200) {
                double scroll = (Math.abs(maxX) + Math.abs(minX)) / 20;
                minX -= scroll;
                maxX -= scroll;
            }
            if (e.getX() > B_WIDTH - 200) {
                double scroll = (Math.abs(maxX) + Math.abs(minX)) / 20;
                minX += scroll;
                maxX += scroll;
            }
            if (e.getY() < 200) {
                double scroll = (Math.abs(maxY) + Math.abs(minY)) / 20;
                minY += scroll;
                maxY += scroll;
            }
            if (e.getY() > B_HEIGHT - 200) {
                double scroll = (Math.abs(maxY) + Math.abs(minY)) / 20;
                minY -= scroll;
                maxY -= scroll;
            }
        }
        
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //zoom using the mouse wheel
            if (e.getWheelRotation() < 0) {
                zoomIn();
            } else {
                zoomOut();
            }
        }
    }
}
