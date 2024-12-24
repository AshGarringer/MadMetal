package game;

import engine.input.SnesController;

import java.awt.*;

public class Player {

    //Player Class
    //Each player has 4 weapons, called modules, which are attached to the player's four sides.
    //Each module functions as an individual weapon,
    //      and can be fired independently by the corresponding button on the controller.

    Integer[] selected;
    Module[] modules;
    SnesController controller;
    public boolean controllerLost = false;

    public Player(SnesController controller, int playerNumber){
        this.controller = controller;
        modules = new Module[4];
        selected = new Integer[]{-1,-1,-1,-1};
    }

    public void tickMenu(){
        controller.update();
    }

    public void render(Graphics2D g){
        //render player
    }

    public void tickGame(){
        controller.update();
    }

    public void setController (SnesController controller){
        this.controller = controller;
    }
}
