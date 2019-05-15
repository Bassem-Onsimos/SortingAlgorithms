
package Controller;

import GameEngine.AbstractGame;
import Algorithm.BinaryHeaps.MaxHeap;
import Algorithm.BubbleSort.BubbleSort;
import Algorithm.InsertionSort.InsertionSort;
import Algorithm.MergeSort.MergeSort;
import Algorithm.QuickSort.QuickSort;
import Algorithm.SelectionSort.SelectionSort;
import Algorithm.SortingAlgorithm;
import Menus.BinaryHeapsPauseMenu;
import Menus.PauseMenu;
import Menus.StartMenu;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Controller extends AbstractGame {
    
    private MaxHeap heap;
    private SortingAlgorithm sortingAlgorithm;
    //
    private int[] inputArray;
    private int[] testArray;
    //
    private Random rand;
    //
    private BinaryHeapsPauseMenu binaryHeapsPauseMenu;
    private PauseMenu pauseMenu;
    //
    public enum Function {
        buildMaxHeap, heapSort, quickSort, mergeSort, insertionSort, selectionSort, bubbleSort
    };
    
    private Function function;
    private boolean working = false;
    private boolean initialized = false;
    //
    private String title;
    private String time;
    
    public Controller(int width, int height, float scale, String windowTitle) {
        super(width, height, scale, windowTitle);
    }

    @Override
    public void initiate() {       
        setDebugInfoDisplayed(false);
        setFPSlimited(true, 30);
        setStartMenu(new StartMenu(this));
        setPausable(true);
        
        binaryHeapsPauseMenu = new BinaryHeapsPauseMenu(this);
        pauseMenu = new PauseMenu(this);
        
        rand = new Random();
        initializeInputAray();
        initializeTestAray();
    }
    
    public void initiateAlgorithm() {
        initialized = false;
        heap = null;
        sortingAlgorithm = null;
        
        switch(function) {
        
            case buildMaxHeap: {
                setPauseMenu(binaryHeapsPauseMenu);
                heap = new MaxHeap(this);
                heap.setArray(inputArray);
                initialized = true;
                return;
            }
            case heapSort: {
                setPauseMenu(binaryHeapsPauseMenu);
                heap = new MaxHeap(this);
                title = "Heap Sort";
                heap.setTestArray(testArray);
                time = "Time = " + Long.toString(heap.calculateTime()) + " µs (for 1000 elements)";
                heap.setArray(inputArray);
                initialized = true;
                return;
            }
            case bubbleSort: {
                sortingAlgorithm = new BubbleSort(this);
                title = "Bubble Sort";
                break;
            }
            case insertionSort: {
                sortingAlgorithm = new InsertionSort(this);
                title = "Insertion Sort";
                break;
            }
            case mergeSort: {
                sortingAlgorithm = new MergeSort(this);
                title = "Merge Sort";
                break;
            }
            case quickSort: {
                sortingAlgorithm = new QuickSort(this);  
                title = "Quick Sort";
                break;
            }
            case selectionSort: {
                sortingAlgorithm = new SelectionSort(this);
                title = "Selection Sort";
                break;
            }
        }
        
        setPauseMenu(pauseMenu);
                
        time = "Time = " + Long.toString(sortingAlgorithm.calculateTime(testArray)) + " µs (for 1000 elements)";
        
        sortingAlgorithm.setArray(inputArray);
        
        initialized = true;       
    }
    
    @Override
    public void update() {
        if(initialized) {
            if(getInput().isKeyUp(KeyEvent.VK_SPACE) && !working) {
                working = true;

                switch(function) {

                    case buildMaxHeap:{
                        heap.visualizeTree();
                        heap.buildMaxHeap();
                        break;
                    }
                    case heapSort: {
                        heap.visualizeTree();
                        heap.heapSort();
                        break;
                    }
                    default: {
                        sortingAlgorithm.sort();
                        break;
                    }
                }  
                working = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(initialized) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(3));

            switch(function) {

                case buildMaxHeap: {
                    if(heap != null) heap.render(g);
                    break;
                }
                case heapSort: {
                    drawTitle(g);
                    if(heap != null) heap.render(g);
                    break;
                }
                default: {
                    drawTitle(g);
                    if(sortingAlgorithm != null) sortingAlgorithm.render(g);
                    break;
                }
            } 
        }
    }
    
    private void drawTitle(Graphics2D g) {
               
        Font defaultFont = g.getFont();
        
        g.setFont((function == Function.heapSort) ? new Font("American Typewriter", Font.PLAIN, 15) : new Font("American Typewriter", Font.PLAIN, 20));       
        FontMetrics metrics = g.getFontMetrics(g.getFont());
                
        int borderX = (function == Function.heapSort) ? 30 + 15 : (int)((getWidth() - metrics.stringWidth(time)) / 2.0);
        int borderWidth = metrics.stringWidth(time) + 30;
        
        g.setFont((function == Function.heapSort) ? new Font("American Typewriter", Font.BOLD, 20) : new Font("American Typewriter", Font.BOLD, 30)); 
        metrics = g.getFontMetrics(g.getFont());
        
        int titleX = borderX - 15 + (borderWidth - metrics.stringWidth(title)) / 2;
        int titleY = (function == Function.heapSort) ? 30 + (metrics.getAscent() + 5) : (int)(getHeight() / 6);
                    
        
        Stroke defaultStroke = g.getStroke();
        
        
            
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.red);
        g.drawRoundRect(borderX - 15, titleY - (metrics.getAscent() + 5), borderWidth, 2 * metrics.getHeight() + 10, 20, 20);

        g.setStroke(defaultStroke);

        g.setColor(Color.BLACK);
        g.drawString(title, titleX + 1, titleY + 1);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(title, titleX, titleY);
        
        //
        
        g.setFont((function == Function.heapSort) ? new Font("American Typewriter", Font.PLAIN, 15) : new Font("American Typewriter", Font.PLAIN, 20));          
        int subTitleY = titleY + metrics.getHeight();
        
        metrics = g.getFontMetrics(g.getFont());
        
        int subTitleX = (function == Function.heapSort) ? 30 + 15 : (int)((getWidth() - metrics.stringWidth(time)) / 2.0);
        
        
        g.setColor(Color.BLACK);
        g.drawString(time, subTitleX + 1, subTitleY + 1);
        g.setColor(Color.RED);
        g.drawString(time, subTitleX, subTitleY);
            
        g.setFont(defaultFont);
    }
    
    public void animateLong() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
        }
    }
    
    public void animateMedium() {
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
        }
    }
    
    public void animateShort() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
        }
    }
    
    private void initializeInputAray() {
        inputArray = new int[15];
        for(int i = 0; i < 15; i++) {
            inputArray[i] = rand.nextInt(100);
        }
    }
    
    private void initializeTestAray() {
        int testSize = 1000;
        testArray = new int[testSize];
        for(int i = 0; i < testSize; i++) {
            testArray[i] = rand.nextInt(testSize);
        }
    }
    
    public void setFunction(Function function) {
        this.function = function;
        initiateAlgorithm();
    }

    public Function getFunction() {
        return function;
    }
    
    
    
}
