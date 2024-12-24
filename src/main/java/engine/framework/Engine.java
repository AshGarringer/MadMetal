package engine.framework;

import com.sun.tools.javac.Main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cookiebot
 */

public abstract class Engine extends Canvas implements Runnable{
    
    Thread thread;
    Graphics2D graphics;
    
    public int state = 0;
    public boolean multi_state = true;
    
    public Window window;
    
    public void start(String name, int width, int height,boolean full){
        window = new Window(name,width,height,5000,full);
        window.start(this);
        this.createBufferStrategy(3);
        
        new Thread() {
            public void run() {
                load();
            }
        }.start(); 
        
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        
        int tps = 60;
        
        int tickTimer = 0;

        long sumTime = 0;
        
        long time_per_tick = (long) (1000f/tps);
        
        boolean dropFrame = false;

        while (true){
            
            long st = System.currentTimeMillis();

            tick();
            if(!dropFrame)render();
            
            tickTimer ++;
            if(tickTimer > tps)tickTimer = 0;
            
            try {
                long ticktime = time_per_tick - (System.currentTimeMillis()-st);
                sumTime += (System.currentTimeMillis()-st);

                dropFrame = false;
                if(ticktime < 0) {
                	dropFrame = true;
                }
                
                if(tickTimer == 0) {
                    System.out.println(((sumTime/(float)tps)/time_per_tick)*100 + "%");
                    sumTime = 0;
                    tickTimer = 0;
                }
                
                Thread.sleep(Math.max(ticktime,0));
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void load(){
        
    }
    
    private void render() {
    	window.update();
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }
        graphics = (Graphics2D)bs.getDrawGraphics();
        render(graphics);
        graphics.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();
        
    }
    
    public void tick(){
        
    }
    
    public void render(Graphics2D g){
        
    }
    
    public void setHints(Graphics2D g){
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}
