package game;

import engine.framework.Engine;

import java.awt.*;

public class Main extends Engine {

    int state = 0;
    // 0 = Menu
    // 1 = Game
    Game game;
    Menu menu;

    public Main(){
        // initialize menu and game
        menu = new Menu(this);
        this.start("Game", 800, 600, false);
    }

    public void startGame(){
        state = 1;
        game = new Game(menu.players, this);
    }

    @Override
    public void tick(){
        if(state == 0){
            menu.tick();
        }
        if(state == 1){
            game.tick();
        }
    }

    @Override
    public void render(Graphics2D g){
        this.setHints(g);
        if(state == 0){
            menu.render(g);
        }
        if(state == 1){
            game.render(g);
        }
    }
}
