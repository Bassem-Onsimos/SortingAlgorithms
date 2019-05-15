
package Algorithm.BubbleSort;

import Algorithm.SortingAlgorithm;
import Controller.Controller;

public class BubbleSort extends SortingAlgorithm {
    
    public BubbleSort(Controller controller) {
        super(controller);
    }
    
    @Override
    public void sort() {
        
        for(int i = 0; i < array.length; i++) {
            int done = 0;
            for(int j = 0; j < array.length - i - 1; j++) {
                
                highlight(j);
                controller.animateLong();
                
                compare(j+1);
                controller.animateLong();
                    
                if(array[j] > array[j+1]) {
                    
                    //
                    choose(j+1);
                    controller.animateLong();
                    //
                    
                    swap(j, j+1);
                    
                     //
                    highlight(j+1);
                    choose(j);
                    controller.animateLong();
           
                    resetColours();
                    highlight(j+1);
                    //controller.animateLong();
                    //                    
                }    
                done = j+1;
            }
            setDone(done);
            controller.animateLong();
        }
        resetColours();
    }

    @Override
    public void sortNoAnimation() {
        
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array.length - i - 1; j++) {
                
                if(array[j] > array[j+1]) {                                        
                    swapNoAnimation(j, j+1);                    
                }    
            }
        }
    }
    
    
    
}
