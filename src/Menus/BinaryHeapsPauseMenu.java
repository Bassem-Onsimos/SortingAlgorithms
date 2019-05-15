
package Menus;

import Controller.Controller;
import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GameMenu.MenuItem;

public class BinaryHeapsPauseMenu extends AbstractMenu {
    
    private Controller controller;
    
    public BinaryHeapsPauseMenu(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void initiate() {
        
        setTitle("Binary Heaps");
        
        addItem(new MenuItem("Build Max Heap") {
            @Override
            public void function() {
                controller.setState(State.inGame);
                controller.setFunction(Controller.Function.buildMaxHeap);                     
            }
        });

        addItem(new MenuItem("Heap Sort") {
            @Override
            public void function() {
                controller.setState(State.inGame);
                controller.setFunction(Controller.Function.heapSort);
            }
        });
        
        addItem(new MenuItem("Main Menu") {
            @Override
            public void function() {
                controller.setState(State.startMenu);
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
