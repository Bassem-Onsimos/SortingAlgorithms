
package Algorithm.BinaryHeaps;

import Controller.Controller;

public class MaxHeap extends BinaryHeap {

    public MaxHeap(Controller controller) {
        super(controller);
    }

    //O(log n)
    public void maxHeapify(int[] array, int index) {
        
        int left = leftChild(index);
        int right = rightChild(index);        
        int largest = index;
        //
        highlight(index);
        controller.animateLong();        
        //
        if(isValid(left)) {
            //
            compare(left);
            controller.animateLong();
            //
            if(array[left] > array[largest]) largest = left;
        }
        if(isValid(right)) {
            //
            compare(right);
            controller.animateLong();
            //
            if(array[right] > array[largest]) largest = right;
        }
        
        if(largest != index) {
            //
            resetCompared();
            choose(largest);
            controller.animateLong();
            //
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            //
            swap(index, largest);
            highlight(largest);
            choose(index);
            controller.animateLong();
            resetChoosen();
            //
            maxHeapify(array, largest);
        }
        
        //
        resetColours();
        //
    }
    
    //O(n)
    public void buildMaxHeap() {
        
        heapSize = array.length;
        
        for(int i = (array.length / 2) - 1; i >= 0; i--) {
            maxHeapify(array, i);
        }    
        
        //
        readyTreeAnimation();
    }
    
    //O(n log n)
    public void heapSort() {
                
        buildMaxHeap();
        
        for(int i = array.length-1; i >= 0; i--) {
            highlight(0);
            controller.animateLong();
            choose(i);
            controller.animateLong();
            //
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            //
            highlight(i);
            choose(0);
            controller.animateLong();
            //            
            heapSize--;
            //
            swap(0, i);
            setDone(i);
            controller.animateLong();
            //
            maxHeapify(array, 0);
        }
        controller.animateLong();
    }
    
    //O(log n)
    public void maxHeapifyNoAnimation(int[] array, int index) {
        
        int left = leftChild(index);
        int right = rightChild(index);        
        int largest = index;
        
        if(isValid(left)) {
            if(array[left] > array[largest]) largest = left;
        }
        if(isValid(right)) {
            if(array[right] > array[largest]) largest = right;
        }
        
        if(largest != index) {           
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;
            
            maxHeapifyNoAnimation(array, largest);
        }
 
    }
    
    //O(n)
    public void buildMaxHeapNoAnimation() {
        
        heapSize = array.length;
        
        for(int i = (array.length / 2) - 1; i >= 0; i--) {
            maxHeapifyNoAnimation(array, i);
        }    
        
    }
    
    //O(n log n)
    public void heapSortNoAnimation() {
                
        buildMaxHeapNoAnimation();
        
        for(int i = array.length-1; i >= 0; i--) {
            
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
                   
            heapSize--;
            
            maxHeapifyNoAnimation(array, 0);
        }
    }

    @Override
    protected long sortNoAnimation() {
        buildMaxHeapNoAnimation();
        
        int testArray[] = new int[array.length];
        
        for(int i = 0; i < array.length; i++)
            testArray[i] = array[i];
        
        for(int i=0; i<3; i++) {
            setTestArray(testArray);
            heapSortNoAnimation();
        }
        
        long time = 0;
        
        for(int i = 0; i < 100; i++) {
            setTestArray(testArray);
            long startingTime = System.nanoTime();
            heapSortNoAnimation();
            time += (System.nanoTime() - startingTime);
        }
        
        return (time / 100) / 1000;        
    }
    
    
    
}
