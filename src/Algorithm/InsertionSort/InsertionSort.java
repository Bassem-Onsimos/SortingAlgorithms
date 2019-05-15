
package Algorithm.InsertionSort;

import Algorithm.SortingAlgorithm;
import Controller.Controller;
import Model.Block;

public class InsertionSort extends SortingAlgorithm {

    private boolean resetChoosen;
    
    public InsertionSort(Controller controller) {
        super(controller);
    }

    @Override
    public void sort() {
        
        for(int i = 1; i < array.length; i++) {
            
            int value = array[i];
            int j = i - 1;
            
            //
            highlight(i);
            controller.animateMedium();
            resetChoosen = false;
            Block current = blocks[i];
            current.moveDown();
            controller.animateMedium();
            compare(j);
            controller.animateLong();
            //
            
            while(j >= 0 && array[j] > value) {
                //
                choose(j);
                controller.animateMedium();
                //
                
                array[j + 1] = array[j];
                
                //
                blocks[j].swapX(current);
                controller.animateMedium();
                swapBlockIndex(j, j+1);
                controller.animateMedium();
                //
                
                j--;   
                
                //
                if(j >= 0) {
                    compare(j);
                    controller.animateLong();
                }
                //
            }
            
            resetCompared();
            controller.animateMedium();
            //
            array[j + 1] = value;
            //
            current.moveUp();
            controller.animateMedium();           
            resetChoosen = true;
            resetColours();
        }    
        
        for(int i = 0; i < array.length; i++){
            setDone(i);
            controller.animateMedium();
        }
        
        
    }
    
    @Override
    protected void resetBlock(int index) {
        if(index != comparedIndex) blocks[index].setCompared(false);
        if(index != highlightedIndex) blocks[index].setHighlighted(false);
        if(resetChoosen) blocks[index].setChoosen(false);
    }
    
    private void swapBlockIndex(int index1, int index2) {
        Block tempBlock = blocks[index1];
        blocks[index1] = blocks[index2];
        blocks[index2] = tempBlock;
        
        if(blocks[index1].isHighlighted()) highlight(index1);
        
        if(blocks[index2].isHighlighted()) highlight(index2);
        
        if(blocks[index1].isChoosen()) choose(index1);
        
        if(blocks[index2].isChoosen()) choose(index2);
        
    }

    @Override
    public void sortNoAnimation() {
        
        for(int i = 1; i < array.length; i++) {
            
            int value = array[i];
            int j = i - 1;
           
            while(j >= 0 && array[j] > value) {               
                array[j + 1] = array[j];
                j--;   
            }
            
            array[j + 1] = value;
        }    
        
    }
    
}
