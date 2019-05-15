
package Menus;

import Controller.Controller;
import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GameMenu.MenuItem;
import GameMenu.SubMenuInitializer;

public class StartMenu extends AbstractMenu {

    private Controller controller;
    
    public StartMenu(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void initiate() {
        
        setTitle("Sorting Algorithms");
        
        addItem(new SubMenuInitializer("O(n log(n))") {
            @Override
            public void initiate() {
                                
                addSubMenuItem(new MenuItem("Heap Sort") {
                    @Override
                    public void function() {
                        controller.setState(State.inGame);
                        controller.setFunction(Controller.Function.heapSort);                       
                    }
                });
                addSubMenuItem(new MenuItem("Quick Sort") {
                    @Override
                    public void function() {
                        controller.setState(State.inGame);
                        controller.setFunction(Controller.Function.quickSort);                       
                    }
                });
                addSubMenuItem(new MenuItem("Merge Sort") {
                    @Override
                    public void function() {
                        controller.setState(State.inGame);
                        controller.setFunction(Controller.Function.mergeSort);                     
                    }
                });
                
                addSubMenuItem(new MenuItem("Back") {
                    @Override
                    public void function() {
                        
                    }
                });
            }
        });
                
        addItem(new SubMenuInitializer("O(n\u00B2)") {
            @Override
            public void initiate() {
                
                
                addSubMenuItem(new MenuItem("Insertion Sort") {
                    @Override
                    public void function() {
                        controller.setState(State.inGame);
                        controller.setFunction(Controller.Function.insertionSort);
                    }
                });
                addSubMenuItem(new MenuItem("Selection Sort") {
                    @Override
                    public void function() {
                        controller.setState(State.inGame);
                        controller.setFunction(Controller.Function.selectionSort);
                    }
                });
                addSubMenuItem(new MenuItem("Bubble Sort") {
                    @Override
                    public void function() {
                        controller.setState(State.inGame);
                        controller.setFunction(Controller.Function.bubbleSort);
                    }
                });
                
                addSubMenuItem(new MenuItem("Back") {
                    @Override
                    public void function() {
                        
                    }
                });
            }
        });
        
        addItem(new MenuItem("Exit") {
            @Override
            public void function() {
                System.exit(0);
            }
        });
        
    }
    
}
