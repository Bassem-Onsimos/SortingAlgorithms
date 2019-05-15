
package Algorithm.SelectionSort;

import Algorithm.SortingAlgorithm;
import Controller.Controller;

public class SelectionSort extends SortingAlgorithm {
    
    public SelectionSort(Controller controller) {
        super(controller);
    }

    @Override
    public void sort() {
        int min;
        
        for(int i = 0; i < array.length - 1; i++) {
            min = i;
            //
            highlight(i);
            controller.animateLong();
            //
            for(int j = i + 1; j < array.length; j++) {
                //
                compare(j);
                controller.animateLong();
                //
                
                if(array[j] < array[min]) {
                    min = j;
                    //
                    choose(j);
                    controller.animateLong();
                    //
                }
            }
            //
            resetCompared();
            //
            
            swap(i, min);           
            
            //
            setDone(i);
            resetColours();
            controller.animateLong();
            //
        }
        //
        setDone(array.length - 1);
        //
    }

    @Override
    public void sortNoAnimation() {
        
        int min;
        
        for(int i = 0; i < array.length - 1; i++) {
            min = i;
            
            for(int j = i + 1; j < array.length; j++) {
                
                if(array[j] < array[min]) {
                    min = j;                    
                }
            }
            swapNoAnimation(i, min);           
        }
        
    }
    
}
