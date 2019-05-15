
package Model;

import Controller.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Node {
    
    //Implementation
    private int value;
    
    //Visualization
    private double x, y;
    private int diameter;
    private int fontSize;
    //
    private boolean highlighted = false;
    private boolean compared = false;
    private boolean choosen = false;
    private boolean done = false;
    //
    private Controller controller;
    
    public Node(int value, Controller controller) {
        this.value = value;
        this.controller = controller;
    }
    
    public void draw(Graphics2D g) {
        if(done) {
            g.setColor(Color.darkGray);
            g.fillOval((int)x, (int)y, diameter, diameter);
        }
        else if(highlighted) {
            g.setColor(Color.yellow);
            g.fillOval((int)x, (int)y, diameter, diameter);
        }
        else if(choosen) {
            g.setColor(Color.blue);
            g.fillOval((int)x, (int)y, diameter, diameter);
        }
        else if(compared) {
            g.setColor(Color.pink);
            g.fillOval((int)x, (int)y, diameter, diameter);
        }
        
        g.setColor(Color.red);
        g.drawOval((int)x, (int)y, diameter, diameter);
        g.setFont(new Font("Arial Black", Font.BOLD, fontSize));
        g.drawString(Integer.toString(value), (int)x + diameter/2 - g.getFontMetrics().stringWidth(Integer.toString(value)) / 2, (int)y + diameter/2 + g.getFontMetrics().getAscent()/2 - 3);   
    }
    
    public void drawAnimation(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval((int)x, (int)y, diameter, diameter);
    }
    
    public void connect(Graphics2D g, Node n) {
        if(!getBounds().intersects(n.getBounds())) {
            g.setColor(n.isDone() ? Color.gray : Color.white);
            g.drawLine((int)x + diameter/2, (int)y + diameter + 3, (int)n.getX() + diameter/2, (int)n.getY() - 1);
        }    
    }
    
    public void move(double startX, double startY) {
        
        double targetX = x;
        double targetY = y;
        
        x = startX;
        y = startY;
        
        double animations = 50;
        double horizontalStep = (targetX - x) / animations;
        double verticalStep = (targetY - y) / animations;
                
        if(horizontalStep < 0) {      
            while( (x > targetX) || (y < targetY) ) {
                if(x > targetX) x += horizontalStep;
                if(y < targetY) y += verticalStep;
                controller.animateShort();
            }
        }
        else {
            while( (x < targetX) || (y < targetY) ) {
                if(x < targetX) x += horizontalStep;
                if(y < targetY) y += verticalStep;
                controller.animateShort();
            }
        }
        
        x = targetX;
        y = targetY;
    }
    
    public void swap(Node n) {
        double startX = x;
        double startY = y;
        
        double targetX = n.x;
        double targetY = n.y;
        
        double animations = 50;        
        double horizontalStep = (targetX - x) / animations;
        double verticalStep = (targetY - y) / animations;
        
        
        if(horizontalStep < 0) {      
            while( (x > targetX) || (y < targetY) ) {
                if(x > targetX) {
                    x += horizontalStep;
                    n.x -= horizontalStep;
                }
                if(y < targetY) {
                    y += verticalStep;
                    n.y -= verticalStep;
                }     
                controller.animateShort();
            }
        }
        else {
            while( (x < targetX) || (y < targetY) ) {
                if(x < targetX) {
                    x += horizontalStep;
                    n.x -= horizontalStep;
                }
                if(y < targetY) {
                    y += verticalStep;
                    n.y -= verticalStep;
                }
                controller.animateShort();
            }
        }
        
        //clipping
        x = targetX;
        n.x = startX;
            
        y = targetY;
        n.y = startY;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x - diameter/2;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y - diameter/2;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
        this.fontSize = Math.max(15, diameter / 4);
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void setCompared(boolean compared) {
        this.compared = compared;
    }

    public void setChoosen(boolean choosen) {
        this.choosen = choosen;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    
    public Rectangle2D getBounds() {
        return new Rectangle((int)x, (int)y, (int)diameter, (int)diameter);
    }
    
}
