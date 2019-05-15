
package Algorithm.QuickSort;

import Algorithm.SortingAlgorithm;
import Controller.Controller;
import Model.Block;

public class QuickSort extends SortingAlgorithm {
        
    public QuickSort(Controller controller) {
        super(controller);
    }

    @Override
    public void sort() {
        
        quickSort(0, array.length - 1);
        
        left = -1;
        right = -1;
        setPivot(-1);
        
    }
    
    private void quickSort(int low, int high) {
        
        if(low >= high) {
            highlightedIndex = -1;
            setDone(low);
            setDone(high);
            controller.animateMedium();
            return;
        }
        
        setLeft(low);
        setRight(high);
        controller.animateLong();
        
        int p = partition(low, high);
        setDone(p);
        controller.animateLong();
        
        quickSort(low, p - 1);
        quickSort(p + 1, high);
        
    }
    
    private int partition(int low, int high) {
        
        int pivot = high;

        //
        setPivot(pivot);
        //controller.animateLong();
        //
        
        int smaller = low - 1;
        highlight(low);
        controller.animateLong();
                
        for(int j = low; j < high; j++) {
            
            if(j != low) {
                compare(j);
                controller.animateLong();
            }
            
            if(array[j] <= array[pivot]) {
                smaller++;
                //
                
                if(j != low) {
                    choose(j);
                    controller.animateLong(); 
                
                    highlight(smaller);
                    controller.animateLong();
                }
                
                swap(smaller, j);
                
            }
            
            resetCompared();
        }
        
        choose(smaller + 1);
        controller.animateLong();
        
        swap(smaller + 1, pivot);
        
        controller.animateMedium();
        resetChoosen();
        controller.animateLong();
                
        return smaller + 1;
        
    }
    
    private void setPivot(int index) {
        for(int i = 0; i < array.length; i++)
            blocks[i].setPivot(false);
        
        if(index >= 0 && index < array.length) blocks[index].setPivot(true);
    }
    
    @Override
    protected void swap(int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
        
        Block tempBlock = blocks[index1];
        blocks[index1] = blocks[index2];
        blocks[index2] = tempBlock;
        
        if(index1 == choosenIndex) choose(index2);
        else if(index2 == choosenIndex) choose(index1);
        
        if(index1 != index2) blocks[index1].swap(blocks[index2]);
    }

    @Override
    public void sortNoAnimation() {
        quickSortNoAnimation(0, array.length - 1);
    }
    
    private void quickSortNoAnimation(int low, int high) {
        if(low >= high) {
            return;
        }
        
        int p = partitionNoAnimation(low, high);
        
        quickSortNoAnimation(low, p - 1);
        quickSortNoAnimation(p + 1, high);
    }
    
    private int partitionNoAnimation(int low, int high) {
        
        int pivot = high;

        int smaller = low - 1;
              
        for(int j = low; j < high; j++) {
            
            if(array[j] <= array[pivot]) {
                smaller++;               
                swapNoAnimation(smaller, j);
            }            
        }
        
        swapNoAnimation(smaller + 1, pivot);

        return smaller + 1;       
    }
   
}
