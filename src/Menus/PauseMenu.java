
package Menus;

import Controller.Controller;
import GameEngine.GameState.State;
import GameMenu.AbstractMenu;
import GameMenu.MenuItem;

public class PauseMenu extends AbstractMenu{

    Controller controller;
    
    public PauseMenu(Controller controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void initiate() {
        addItem(new MenuItem("Restart") {
            @Override
            public void function() {
                controller.setState(State.inGame);
                controller.setFunction(controller.getFunction());               
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
