package game;

import engine.graphics.Text;
import engine.input.SnesController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu {


    int numModules = 2;
    int getPlayersTimer = 0;
    ArrayList<Player> players;
    Main main;
    Text text;
    BufferedImage image;

    public Menu(Main main){
        this.main = main;
        players = new ArrayList<>();
        text = new Text("Regular",40,Color.WHITE);
        image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
    }

    public void tick(){
        for(int i = 0; i < players.size(); i ++){
            Player player = players.get(i);
            player.tickMenu();
            // if a pressed, iterate player.selected[0], if b pressed, iterate player.selected[1], etc.
            for(int j = SnesController.X; j <= SnesController.Y; j++){
                if(player.controller.pressed(j)){
                    player.selected[j - SnesController.X] ++;
                    if(player.selected[j - SnesController.X] >= numModules){
                        player.selected[j - SnesController.X] = 0;
                    }
                }
            }
            // if any player presses both triggers, all players will have random modules selected
            if(player.controller.held(SnesController.LTRIGGER) && player.controller.held(SnesController.RTRIGGER)){
                for(int k = 0; k < players.size(); k ++){
                    for(int l = 0; l < 4; l ++){
                        players.get(k).selected[l] = (int)(Math.random()*numModules);
                    }
                }
            }
            if(players.get(i).controller.pressed(SnesController.START)){
                main.startGame();
            }
        }
        if(getPlayersTimer == 0) {
            getPlayers();
            getPlayersTimer = 30;
        }
        getPlayersTimer --;
    }

    public void render(Graphics2D g){
        if(image.getWidth() != main.window.getWidth() || image.getHeight() != main.window.getHeight()){
            image = new BufferedImage(main.window.getWidth(),main.window.getHeight(),BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g2 = (Graphics2D)image.getGraphics();
        main.setHints(g2);
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,main.window.getWidth(),main.window.getHeight());
        //draw players' selected modules
        int x = main.window.getWidth()/3;
        int y = main.window.getHeight()/3;
        for(int i = 0; i < players.size(); i ++){
            Player player = players.get(i);
            text.drawString(x + x*(i%2),y + y*(i/2) - 40,"Player " + (i+1),g2);
            text.drawString(x + x*(i%2),y + y*(i/2) + 10,
                    (player.selected[0]+1) + " " + (player.selected[1]+1) +
                            " " + (player.selected[2]+1) + " " + (player.selected[3]+1),g2);
        }
        g.drawImage(image,0,0,null);
    }

    public void getPlayers(){
        //load new controllers
        ArrayList<SnesController> newControllers = SnesController.getControllers();
        if(players.size() != newControllers.size()){
            while(players.size() > newControllers.size()) {
                players.remove(players.size() - 1);
            }
            while(players.size() < newControllers.size()) {
                players.add(new Player(newControllers.get(players.size()), players.size()));
            }
        }
        System.out.println(players.size());
    }
}
