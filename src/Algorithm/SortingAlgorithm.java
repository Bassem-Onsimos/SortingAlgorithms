
package Algorithm;

import Controller.Controller;
import Model.Block;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class SortingAlgorithm {
    
    //Implementation
    protected int[] array;
    
    //Visualization
    protected Block[] blocks;
    //
    protected double width, height;
    //
    protected Controller controller;
    protected int highlightedIndex = -1;
    protected int comparedIndex = -1;
    protected int choosenIndex = -1;
    protected int left = -1;
    protected int right = -1;
    //
    protected boolean blocksCreated;
    //
    
    public SortingAlgorithm(Controller controller) {
        width = controller.getWidth();
        height = controller.getHeight();
        
        this.controller = controller;    
    }
     
    public void setTestArray(int[] array) {
        this.array = new int[array.length];
        
        for(int i = 0; i < array.length; i++)
            this.array[i] = array[i];
        
    }
    
    public void setArray(int[] array) {
        this.array = new int[array.length];
        
        for(int i = 0; i < array.length; i++)
            this.array[i] = array[i];
        
        visualizeArray();       
    }
    
    public abstract void sort();
    
    public long calculateTime(int[] testArray) {
        
        for(int i = 0; i < 3; i++) {
            setTestArray(testArray);
            sortNoAnimation();
        }
        
        long time = 0;
        
        for(int i = 0; i < 100; i++) {
            setTestArray(testArray);
            long startingTime = System.nanoTime();
            sortNoAnimation();
            time += (System.nanoTime() - startingTime);
        }
        
        return (time / 100) / 1000;
    }
    
    protected abstract void sortNoAnimation();
    
    public int[] getArray() {
        return array;
    }
    
    protected void visualizeArray() {
        blocksCreated = false;
        blocks = new Block[array.length];
        
        for(int i = 0; i < array.length; i++) {
            blocks[i] = new Block(array[i], width, height, controller);
            blocks[i].setBlockLocation(i, array.length, 0);
        }
        blocksCreated = true;
    }
    
    protected void swap(int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
        
        Block tempBlock = blocks[index1];
        blocks[index1] = blocks[index2];
        blocks[index2] = tempBlock;
        
        if(index1 == choosenIndex) choose(index2);
        else if(index2 == choosenIndex) choose(index1);
        
        if(index1 == highlightedIndex) highlight(index2);
        else if(index2 == highlightedIndex) highlight(index1);
        
        if(index1 != index2) blocks[index1].swap(blocks[index2]);
    }
    
    public void swapNoAnimation(int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
    public void render(Graphics2D g) {
        if(blocksCreated) {
            for(int i = 0; i < array.length; i++) {
                resetBlock(i);
                blocks[i].draw(g);
                
                g.setColor(Color.red);
                g.drawString(Integer.toString(i), (float)blocks[i].getX(), (float)blocks[i].getY() - 10);
                
                if(i == left) g.drawString("Left", (float)(blocks[i].getX() + (blocks[i].getSize() - g.getFontMetrics(g.getFont()).stringWidth("Left")) / 2), (float)(blocks[i].getY() + blocks[i].getSize() + 20));
                if(i == right) g.drawString("Right", (float)(blocks[i].getX()  + (blocks[i].getSize() - g.getFontMetrics(g.getFont()).stringWidth("Right")) / 2), (float)(blocks[i].getY() + blocks[i].getSize() + 20));
            }            
        }
    }
    
    protected void setDone(int index) {
        if(index >= 0 && index < array.length) blocks[index].setDone(true);
    }
    
    protected void highlight(int index) {
        highlightedIndex = index;
        blocks[index].setHighlighted(true);
        blocks[index].setChoosen(false);
        blocks[index].setCompared(false);
    }
    
    protected void compare(int index) {
        comparedIndex = index;
        blocks[index].setHighlighted(false);
        blocks[index].setChoosen(false);
        blocks[index].setCompared(true);
    }
    
    protected void choose(int index) {
        choosenIndex = index;
        blocks[index].setHighlighted(false);
        blocks[index].setChoosen(true);
        blocks[index].setCompared(false);
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }
    
    protected void resetColours() {
        highlightedIndex = -1;
        comparedIndex = -1;
        choosenIndex = -1;
    }
    
    protected void resetChoosen() {
        choosenIndex = -1;
    }
    
    protected void resetCompared() {
        comparedIndex = -1;
    }
    
    protected void resetBlock(int index) {
        if(index != highlightedIndex && index != choosenIndex && index != comparedIndex){
            blocks[index].setHighlighted(false);
            blocks[index].setCompared(false);
            blocks[index].setChoosen(false);
        }  
    }
    
}
