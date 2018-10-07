import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{
    public static int FPS = 60;
    private Thread mainThread;
    private Image ofScreen = null;
    private boolean mainThreadFlag;
    private int x;
    private  Tank player1 = new Tank(500, 500, 50,50,1, 5,8);
//    private  Tank player2  = new Tank(800, 500, 50,50,1, 5,3);
//    private  Tank player3  = new Tank(600, 600, 50,50,1, 5,3);
    private Thread mainUIThread;
    private List<Missle> missleCollection = new LinkedList<Missle>();
    private List<Drawable> drawables = new LinkedList<>();
    private KeyMonitor km = new KeyMonitor();
    private List<Tank> tankList = new LinkedList<Tank>();
    private boolean mainUIThreadFlag;
    private Image[] bg = new Image[]{new ImageIcon("drawable/background/bghuise.png").getImage(),
            new ImageIcon("drawable/background/bgblack.png")
            .getImage()};
    public GamePanel(){
        mainThreadFlag = true;
        mainThread = new Thread(this);
        mainThread.start();

        tankList.add(player1);
        drawables.add(player1);
//        drawables.add(player2);
//        drawables.add(player3);

    }
    public void player1Control(){

//        System.out.println(player1.getX() + "," + player1.getY());
        if (KeyMonitor.upButtonPressed)
            player1.startUp(Direction.UP);
        else if (KeyMonitor.downButtonPressed)
            player1.startUp(Direction.DOWN);
        else if (KeyMonitor.leftButtonPressed)
            player1.startUp(Direction.LEFT);
        else if (KeyMonitor.rightButtonPressed)
            player1.startUp(Direction.RIGHT);
        if (KeyMonitor.fireButtonPressed){
            Missle fire = player1.fire();
            if (fire != null){
            missleCollection.add(fire);
            drawables.add(fire);

            }
            KeyMonitor.fireButtonPressed = false;
        }

    }
    public void missleLogic(){
        for (int i=0; i<missleCollection.size(); i++){
            missleCollection.get(i).logic();
        }
    }
    public void impackCheck(){
        for (int k=0; k<drawables.size(); k++){
            Drawable drawable = drawables.get(k);
            if (drawable.getX() > getWidth()-200 || drawable.getX() < 50
                     || drawable.getY() > getHeight()-100 || drawable.getY() < 50){
                ((Impackable)drawable).isImpacked();
                   System.out.println(drawable.getX() + ","+drawable.getY() + " " + drawable.getX() + " " +drawable.getY());

            }
        }
       for (int i=0; i<drawables.size(); i++){
           for (int j=i+1; j<drawables.size(); j++){
               Drawable d1 = drawables.get(i);
               Drawable d2 = drawables.get(j);

               if (d1.getX() - d2.getX() < d2.getWidth()
                       && d2.getX() - d1.getX() < d1.getWidth()
                       && d1.getY() - d2.getY() < d2.getHeight()
                       && d2.getY() - d1.getY() < d1.getHeight()
                       ){
//                   System.out.println(d1.getX() + ","+d1.getY() + " " + d2.getX() + " " +d2.getY());
                   ((Impackable)d1).impack((Impackable) d2);
               }
           }
       }


    }
    @Override
    public void run() {

        while (mainThreadFlag) {
            double t1,t2,dt,sleepTime;
            double period = 1000 / (double)FPS;
            t1 = System.nanoTime();
            impackCheck();
            missleLogic();

            player1Control();
            t2 = System.nanoTime();
            dt = (t2 - t1) / 1000000L;
            sleepTime = period - dt;
//            System.out.println("FPS:" + (int)(1000 / sleepTime));
            if (sleepTime  <= 0)
                sleepTime = 0;

            try {
                Thread.sleep((int)sleepTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                t1 = System.nanoTime();
            }
            repaint();
        }

    }
   /* public void drawMissles(Graphics g){
        for (int i=0; i<missleCollection.size(); i++){
            missleCollection.get(i).draw(g);
        }
    }
    public void drawTanks(Graphics g){
        for (int i=0; i<tankList.size(); i++){
            tankList.get(i).draw(g);
        }
    }*/
   public void drawDrawables(Graphics graphics){
       for (Drawable drawable:drawables){
           if (drawable.isDead())
               drawables.remove(drawable);
           else{

           drawable.draw(graphics);
           }
       }

   }
    @Override
   public void update(Graphics graphics){
        System.out.println("update");

   }
    public void drawBackground(Graphics graphics){
        graphics.drawImage(bg[0], 0, 0, this.getWidth(), this.getHeight(), null);
        graphics.drawImage(bg[1], 50, 50, getWidth()-200, getHeight()-100, null);
    }
    @Override
    public void paint(Graphics g) {
//        System.out.println("paint");
        if (ofScreen == null)
            ofScreen = createImage(this.getSize().width, this.getSize().height);
        Graphics g2 = ofScreen.getGraphics();
        drawBackground(g2);
       drawDrawables(g2);
        g.drawImage(ofScreen, 0, 0, null);

    }
}
