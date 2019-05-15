
package Model;

import Controller.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Block {
    
    //Implementation
    private int value;

    
    //Visualization
    private double windowHeight, windowWidth;
    private double x, y;
    private int size = 60;
    private int fontSize = 15;
    //
    private boolean highlighted = false;
    private boolean compared = false;
    private boolean choosen = false;
    private boolean done = false;
    private boolean pivot = false;
    //
    private Controller controller;
    
    
    public Block(int value, double windowWidth, double windowHeight, Controller controller) {
        this.value = value;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.controller = controller;
    }
    
    public void draw(Graphics2D g) {
        if(pivot) {
            g.setColor(Color.red);
            g.fillRect((int)x, (int)y, size, size);
        }
        else if(done) {
            g.setColor(Color.darkGray);
            g.fillRect((int)x, (int)y, size, size);
        }
        else if(highlighted) {
            g.setColor(Color.yellow);
            g.fillRect((int)x, (int)y, size, size);
        }
        else if(choosen) {
            g.setColor(Color.blue);
            g.fillRect((int)x, (int)y, size, size);
        }
        else if(compared) {
            g.setColor(Color.pink);
            g.fillRect((int)x, (int)y, size, size);
        }
        
        g.setColor(Color.red);
        g.setFont(new Font("Arial Black", Font.BOLD, fontSize));    
        g.drawRect((int)x, (int)y, size, size);
        
        g.setColor(pivot ? Color.black :Color.red);
        g.drawString(Integer.toString(value), (int)x + size/2 - g.getFontMetrics().stringWidth(Integer.toString(value)) / 2, (int)y + size/2 + g.getFontMetrics().getAscent()/2 - 3);
    }
    
    public void draw(Graphics2D g, double displacement) {
        double y1 = y + displacement;
        
        if(done) {
            g.setColor(Color.darkGray);
            g.fillRect((int)x, (int)y1, size, size);
        }
        else if(highlighted) {
            g.setColor(Color.yellow);
            g.fillRect((int)x, (int)y1, size, size);
        }
        else if(choosen) {
            g.setColor(Color.blue);
            g.fillRect((int)x, (int)y1, size, size);
        }
        else if(compared) {
            g.setColor(Color.pink);
            g.fillRect((int)x, (int)y1, size, size);
        }
        
        g.setColor(Color.red);
        g.setFont(new Font("Arial Black", Font.BOLD, fontSize));    
        g.drawRect((int)x, (int)y1, size, size);
        g.drawString(Integer.toString(value), (int)x + size/2 - g.getFontMetrics().stringWidth(Integer.toString(value)) / 2, (int)y1 + size/2 + g.getFontMetrics().getAscent()/2 - 3);
    }
    
    
    public void setBlockLocation(int index, int arrayLength, double bottomMargin) {
        bottomMargin = (bottomMargin<=0) ? windowHeight/2 : bottomMargin + size/3;
        
        x = index * size + (windowWidth - arrayLength * size) / 2;        
        y = windowHeight - bottomMargin;
    }
    
    public void swap(Block b) {
        swapY(b);
        b.swapX(this);
        b.swapY(this);
    }
    
    public void swapY(Block b) {
        double targetY = y + size;
        double bTargetY = b.y - size;
        
        int animations = (Math.abs(b.x - x) <= size) ? 30 : 50;
        double verticalStep = (targetY - y)/animations;
        
            while(y < targetY) {
                y += verticalStep;
                b.y -= verticalStep;
                controller.animateShort();
            }        
        //clipping
        y = targetY;
        b.y = bTargetY;
    }
    
    public void swapX(Block b) {
        double targetX = b.x;
        double bTargetX = x;
        
        int animations = (Math.abs(b.x - x) <= size) ? 30 : 50;
        double step = (targetX - x)/animations;
        
        while(targetX > x) {
            x += step;
            b.x -= step;
            controller.animateShort();
        }
        
        //clipping
        x = targetX;
        b.x = bTargetX;
    }
    
    public void moveDown() {
        double targetY = y + size * 2;
        
        int animations = 50;
        double step = (targetY - y)/animations;
        
        while(y < targetY) {
            y += step;
            controller.animateShort();
        }
        
        //clipping
        y = targetY;
    }
    
    public void moveUp() {
        double targetY = y - size * 2;
        
        int animations = 50;
        double step = (y - targetY)/animations;
        
        while(y > targetY) {
            y -= step;
            controller.animateShort();
        }
        
        //clipping
        y = targetY;
    }
    
    public void moverRight() {
        double targetX = x + (size);
        
        int animations = 30;
        
        double step = (targetX - x)/animations;
        
        while(targetX > x) {
            x += step;
            controller.animateShort();
        }
        
        x = targetX;
    }
    
    public void moverLeft() {
        double targetX = x - (size);
        
        int animations = 30;
        
        double step = (x - targetX)/animations;
        
        while(x > targetX) {
            x -= step;
            controller.animateShort();
        }
        
        x = targetX;
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

    public boolean isHighlighted() {
        return highlighted;
    }
    
    public boolean isCompared() {
        return compared;
    }

    public boolean isChoosen() {
        return choosen;
    }

    public boolean isDone() {
        return done;
    }

    public void setPivot(boolean pivot) {
        this.pivot = pivot;
    }
    
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }
    
    
    
}
