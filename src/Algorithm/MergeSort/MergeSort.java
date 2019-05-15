
package Algorithm.MergeSort;

import Algorithm.SortingAlgorithm;
import Controller.Controller;
import Model.Block;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class MergeSort extends SortingAlgorithm {
    
    ArrayList<Integer> partitions;
    Block[] sorted;
    
    public MergeSort(Controller controller) {
        super(controller);
    }
    
    @Override
    protected void visualizeArray() {
        blocksCreated = false;
        blocks = new Block[array.length];
        
        for(int i = 0; i < array.length; i++) {
            blocks[i] = new Block(array[i], width, height, controller);
            blocks[i].setBlockLocation(i, array.length, 0);
        }
        
        sorted = new Block[array.length];
        
        for(int i = 0; i < array.length; i++) {
            sorted[i] = new Block(array[i], width, height, controller);
            sorted[i].setBlockLocation(i, array.length, 0);
        }
        
        partitions = new ArrayList<>();
        
        blocksCreated = true;
    }
    
    @Override
    public void render(Graphics2D g) {
        if(blocksCreated) {
            
            double displacement = 2 * blocks[0].getSize();
            
            for(int i = 0; i < array.length; i++) {
                resetBlock(i);
                blocks[i].draw(g, - displacement/2);
                g.drawString(Integer.toString(i), (float)blocks[i].getX(), (float)(blocks[i].getY() - displacement/2 - 10));
                
                g.setColor(Color.gray);
                for(int j = 0; j < partitions.size(); j++) {
                    int p = partitions.get(j);
                    g.drawLine((int)(blocks[p].getX() + blocks[p].getSize()), (int)(blocks[p].getY() - displacement/2 - blocks[p].getSize()), (int)(blocks[p].getX() + blocks[p].getSize()), (int)(blocks[p].getY() - displacement/2 + blocks[p].getSize() * 2));
                }                
                //
                sorted[i].draw(g, displacement * 1.5);
                g.drawString(Integer.toString(i), (float)sorted[i].getX(), (float)(sorted[i].getY() + displacement*1.5 - 10));
                
                g.setColor(Color.gray);
                
                for(int j = 0; j < partitions.size(); j++) {
                    int p = partitions.get(j);
                    g.drawLine((int)(sorted[p].getX() + sorted[p].getSize()), (int)(sorted[p].getY() + displacement*1.5 - sorted[p].getSize()), (int)(sorted[p].getX() + sorted[p].getSize()), (int)(sorted[p].getY() + displacement*1.5 + sorted[p].getSize() * 2));           
                }
            }
        }      
    }
    
    public void drawBlock(Graphics2D g, int i, double displacement) {
        blocks[i].draw(g, - displacement);
        g.drawString(Integer.toString(i), (float)blocks[i].getX(), (float)(blocks[i].getY() - displacement - 10));
                
        g.setColor(Color.gray);
        for(Integer p : partitions) g.drawLine((int)(blocks[p].getX() + blocks[p].getSize()), (int)(blocks[p].getY() - displacement - blocks[p].getSize()), (int)(blocks[p].getX() + blocks[p].getSize()), (int)(blocks[p].getY() - displacement + blocks[p].getSize() * 2));
    }

    @Override
    public void sort() {
        
        mergeSort(0, array.length - 1);
        
    }
    
    
    
    private void mergeSort(int left, int right) {
        
        if(left >= right) return;
            
        int middle = left + (right - left) / 2;

        partitions.add(middle);
        controller.animateLong();

        mergeSort(left, middle);
        mergeSort(middle + 1, right);

        merge(left, middle, right);
       
        for(int i = left; i <= right; i++) {
            sorted[i].setBlockLocation(i, array.length, 0);
        }
        
        
        controller.animateLong();
        controller.animateLong();
        controller.animateLong();
        
        for(int b = left; b <= right; b++) {
            blocks[b].setDone(false);

            if(!(left==0 && right == array.length - 1))
                sorted[b].setDone(false);
        }
        controller.animateLong();
        controller.animateLong();
                   
    }
    
    private void merge(int left, int middle, int right) {
             
        for(int i = left; i <= right; i++) {
            blocks[i].setDone(true);
            sorted[i].setDone(true);
        }
        
        controller.animateLong();
        controller.animateLong();
        
        
        int n1 = middle - left + 1;
        int n2 = right - middle;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        Block[] leftBlocks = new Block[n1];
        Block[] rightBlocks = new Block[n2];
        
        for(int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
            leftBlocks[i] = sorted[left + i];
        }
        
        for(int j = 0; j < n2; j++) {
            rightArray[j] = array[middle + 1 + j];
            rightBlocks[j] = sorted[middle + 1 + j];
        }
        
        
        int i = 0, j = 0, k = left;
        
        while(i < n1 && j < n2) {
            
            if(leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                sorted[k] = leftBlocks[i];
                k++;
                i++;
            }
            else {
                array[k] = rightArray[j];
                sorted[k] = rightBlocks[j];
                k++;
                j++;
            }
        }
        
        while(i < n1) {
            array[k] = leftArray[i];
            sorted[k] = leftBlocks[i];
            k++;
            i++;
        }
        
        while(j < n2) {
            array[k] = rightArray[j];
            sorted[k] = rightBlocks[j];
            k++;
            j++;
        }
        
        partitions.remove((Integer)middle);
               
    }

    @Override
    public void sortNoAnimation() {
        mergeSortNoAnimation(0, array.length - 1);
    }
    
    private void mergeSortNoAnimation(int left, int right) {
        
        if(left >= right) return; 
        
        int middle = left + (right - left) / 2;
            
        mergeSortNoAnimation(left, middle);
        mergeSortNoAnimation(middle + 1, right);
            
        mergeNoAnimation(left, middle, right);
            
    }
    
    private void mergeNoAnimation(int left, int middle, int right) {
        
        int n1 = middle - left + 1;
        int n2 = right - middle;
        
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        
        for(int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        
        for(int j = 0; j < n2; j++) {
            rightArray[j] = array[middle + 1 + j];
        }
        
        int i = 0, j = 0, k = left;
        
        while(i < n1 && j < n2) {
            
            if(leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            }
            else {
                array[k++] = rightArray[j++];
            }
        }
        
        while(i < n1) {
            array[k++] = leftArray[i++];
        }
        
        while(j < n2) {
            array[k++] = rightArray[j++];
        }
        
    }
}
