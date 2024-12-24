package game;

import engine.input.SnesController;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    ArrayList<Player> players;
    Main main;
    int controllersConnected = 0;
    int getPlayersTimer = 0;

    public Game(ArrayList<Player> players, Main main){
        this.players = players;
        this.main = main;
        controllersConnected = players.size();
    }

    public void tick(){
        for(int i = 0; i < players.size(); i ++){
            players.get(i).tickGame();
        }
        if(getPlayersTimer == 0) {
            getPlayers();
            getPlayersTimer = 30;
        }
    }

    public void render(Graphics2D g){
        for(int i = 0; i < players.size(); i ++){
            players.get(i).render(g);
        }
    }

    public void getPlayers(){
        //load new controllers
        ArrayList<SnesController> newControllers = SnesController.getControllers();
        if(controllersConnected != newControllers.size()){
            for(int i = 0; i < players.size() && i < newControllers.size(); i ++){
                players.get(i).setController(newControllers.get(i));
            }
            controllersConnected = newControllers.size();
        }
        System.out.println(players.size());
    }

}
