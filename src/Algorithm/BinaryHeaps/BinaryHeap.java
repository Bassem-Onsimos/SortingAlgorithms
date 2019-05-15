
package Algorithm.BinaryHeaps;

import Controller.Controller;
import Model.Block;
import Model.Node;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public abstract class BinaryHeap {
    
    //Implementation
    protected int[] array;
    protected final int maximumCapacity = 31;
    protected int heapSize = 0;
    
    //Visualization
    private double verticalDistance;
    private double width;
    private double height;
    //
    protected Node[] nodes;
    protected Block[] blocks;
    private int drawnNodes;
    private int nLevels;
    //
    protected Controller controller;
    private int highlightedIndex = -1;
    private int comparedIndex = -1;
    private int choosenIndex = -1;
    //
    private boolean readyTreeAnimation;
    private int animatedNodes;
    
    public BinaryHeap(Controller controller) {
        this.width = controller.getWidth();
        this.height = controller.getHeight();
        
        this.controller = controller;
        
        nodes = new Node[maximumCapacity];
        blocks = new Block[maximumCapacity];
    }
    
    public long calculateTime() {
        return sortNoAnimation();
    }
    
    protected abstract long sortNoAnimation();
    
    public int peek() {
        return array[0];
    }
    
    protected int parent(int index) {
        return (index <= 0) ? -1 : (index - 1) / 2;
    }
    
    protected int leftChild(int index) {
        return (2 * index) + 1;
    }
    
    protected int rightChild(int index) {
        return (2 * index) + 2;
    }
    
    protected boolean isValid(int index) {
        return (index >= 0) && (index < heapSize);
    }
    
    protected boolean isValidForRender(int index) {
        return (index >= 0) && (index < array.length);
    }
    
    public int[] getArray() {
        return array;
    }
    
    public void setArray(int[] array) {
        this.array = new int[array.length];
        
        for(int i = 0; i < array.length; i++)
            this.array[i] = array[i];
        
        heapSize = array.length;
        visualizeArray();
    }
    
    public void setTestArray(int[] array) {
        this.array = new int[array.length];
        
        for(int i = 0; i < array.length; i++)
            this.array[i] = array[i];
        
        heapSize = array.length;
    }
    
    ///////////////////////////
    //for visualization
    
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(3));

            for(int i=0; i<array.length; i++) {
                if(i < drawnNodes) {
                    if(isValidForRender(leftChild(i)) && leftChild(i) < drawnNodes) nodes[i].connect(g, nodes[leftChild(i)]);
                    if(isValidForRender(rightChild(i)) && rightChild(i) < drawnNodes) nodes[i].connect(g, nodes[rightChild(i)]);
                    
                    if(i != highlightedIndex && i != choosenIndex && i != comparedIndex){
                        nodes[i].setHighlighted(false);
                        nodes[i].setCompared(false);
                        nodes[i].setChoosen(false);
                        //
                    }
                    nodes[i].draw(g);
                    
                    if(readyTreeAnimation) {
                        if(i >= animatedNodes) nodes[i].drawAnimation(g);
                    }                    
                }
                
                if(i != highlightedIndex && i != choosenIndex && i != comparedIndex){
                    blocks[i].setHighlighted(false);
                    blocks[i].setCompared(false);
                    blocks[i].setChoosen(false);
                }                     
                
                blocks[i].draw(g);
                
                g.setColor(Color.red);
                g.drawString(Integer.toString(i), (float)blocks[i].getX(), (float)blocks[i].getY() - 10);
                
            }     
        
    }
    
    private void visualizeArray() {
        
        for(int i = 0; i < array.length; i++) {
            blocks[i] = new Block(array[i], width, height, controller);
        }
        
        calculateVerticalMargins();
        
        for(int i=0; i<array.length; i++) {
            blocks[i].setBlockLocation(i, array.length, verticalDistance);
        }
    }
    
    public void visualizeTree() {
        
        drawnNodes = 0;
        
        for(int i = 0; i < array.length; i++) {
            nodes[i] = new Node(array[i], controller);
            
            if(i >= heapSize){
                nodes[i].setDone(true);
            }
        }
        
        setNodeLocations();          
        animateTreeBuild(); 
        readyTreeAnimation();
    }
    
    private void calculateVerticalMargins() {
        nLevels = 1 + (int)Math.floor(Math.log(array.length) / Math.log(2));
        verticalDistance = height / (double)(nLevels + 2); 
    }
 
    private void setNodeLocations() {                
        setLocation(0, 0, - verticalDistance/4.5, 0);
    }
    
    private void setLocation(int index, double x, double y, int accumalatedNodes) {
        
        nodes[index].setDiameter(200 / nLevels);
        
        int level = (int)Math.floor(Math.log(index + 1) / Math.log(2)) + 1;
        
        int totalNodes = (int)Math.pow(2, level) - 1;
        
        int levelNodes = (totalNodes + 1) / 2;
        
        double horizontalDistance = width / (levelNodes + 1) ;
        
        y += verticalDistance;
        x = (index - accumalatedNodes + 1) * horizontalDistance;             
        
        nodes[index].setX(x);
        nodes[index].setY(y);
        
        if(isValidForRender(leftChild(index))) setLocation(leftChild(index), x, y, totalNodes);
        if(isValidForRender(rightChild(index))) setLocation(rightChild(index), x, y, totalNodes);
    }
    
    private void animateTreeBuild() {
        for(int i = 0; i < array.length; i++) {
            drawnNodes = i+1;
            highlight(i);
            if(i==0) nodes[i].move(nodes[i].getX(), 0);
            else nodes[i].move(nodes[parent(i)].getX(), nodes[parent(i)].getY());
            resetColours();
        }
    }
    
    protected void readyTreeAnimation() {
        
        readyTreeAnimation = true;
        
        int totalNodes = (int)Math.pow(2, nLevels) - 1;        
        animatedNodes = totalNodes;
        
        for(int level = nLevels; level > 0; level--) {
            totalNodes = (int)Math.pow(2, level) - 1;
            int levelNodes = (totalNodes + 1) / 2;
            animatedNodes -= levelNodes;
            controller.animateMedium();
        }  
        
        for(int level = 1; level <= nLevels; level++) {
            totalNodes = (int)Math.pow(2, level) - 1;
            int levelNodes = (totalNodes + 1) / 2;
            animatedNodes += levelNodes;
            controller.animateMedium();
        }  
        
        controller.animateLong();
        readyTreeAnimation = false;
    }
    
    
    public void swap(int index1, int index2) {
        if(index1 != index2) {
            Node tempNode = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tempNode;

            nodes[index2].swap(nodes[index1]);

            Block tempBlock = blocks[index1];
            blocks[index1] = blocks[index2];
            blocks[index2] = tempBlock;

            blocks[index1].swap(blocks[index2]);
        }
    }
    
    public void setDone(int index) {
        if(index >= heapSize){      //extra check
            nodes[index].setDone(true);
            blocks[index].setDone(true);
         }
    }
    
    protected void highlight(int index) {
        highlightedIndex = index;
        nodes[index].setHighlighted(true);
        nodes[index].setChoosen(false);
        nodes[index].setCompared(false);
        blocks[index].setHighlighted(true);
        blocks[index].setChoosen(false);
        blocks[index].setCompared(false);
    }
    
    protected void compare(int index) {
        comparedIndex = index;
        nodes[index].setHighlighted(false);
        nodes[index].setChoosen(false);
        nodes[index].setCompared(true);
        blocks[index].setHighlighted(false);
        blocks[index].setChoosen(false);
        blocks[index].setCompared(true);
    }
    
    protected void choose(int index) {
        choosenIndex = index;
        nodes[index].setHighlighted(false);
        nodes[index].setChoosen(true);
        nodes[index].setCompared(false);
        blocks[index].setHighlighted(false);
        blocks[index].setChoosen(true);
        blocks[index].setCompared(false);
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
    
    
    
    
}
