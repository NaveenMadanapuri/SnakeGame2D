import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT=400;
    int B_WIDTH=400;
    int MAX_DOTS=1600;
    int DOT_SIZE=10;
    int DOTS;
    int x[]=new int[MAX_DOTS];
    int y[]=new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    //images
    Image body,head,apple;
    Timer timer;
    int DEALY=150;
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection = false;
    boolean downDirection=false;
    boolean ingame=true;
    Board(){
        TAdapter tAdaptor = new TAdapter();
        addKeyListener(tAdaptor);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
       initiGame();
       loadImages();

    }
    //initilize game
    public void initiGame(){
        DOTS=3;
        //initilize snake postion
        x [0]=250;
        y [0]=250;
        for(int i=1;i<DOTS;i++){
            x[i]=x[0]+DOT_SIZE*i;
            y[i]=y[0];
        }
        //initilize apple postion
        locateapple();
        timer = new Timer(DEALY,this);
        timer.start();
    }
    public void loadImages(){
        ImageIcon bodyIcon=new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon=new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon=new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    public void doDrawing(Graphics g) {
        if(ingame){
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else
                    g.drawImage(body, x[i], y[i], this);
            }
        }
        else{
            gameover(g);
            timer.stop();
        }

    }
    // body and border collision check

    public void locateapple(){
        apple_x=((int)(Math.random()*39))*DOT_SIZE;
        apple_y=((int)(Math.random()*39))*DOT_SIZE;
    }
    //collision with border and body
    public void checkcollision(){
        for(int i=1;i<DOTS;i++){
            if(i>4&&x[0]==x[i]&&y[0]==y[i]){
                ingame=false;
            }
        }
        if(x[0]<0){
            ingame =false;
        }
        if(x[0]>B_WIDTH){
            ingame=false;
        }
        if(y[0]<0){
            ingame=false;
        }
        if(y[0]>B_HEIGHT){
            ingame=false;
        }
    }
    //gameover msg
    public void gameover(Graphics g){
        String msg= "Game over";
        int score=(DOTS-3)*100;
        String scoremsg="Scire"+Integer.toString(score);
        Font small= new Font("Helevertics", Font.BOLD,16);
        FontMetrics fontmetrics = getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontmetrics.stringWidth(msg))/2,B_HEIGHT/4);
        g.drawString(scoremsg,(B_WIDTH-fontmetrics.stringWidth(msg))/2,3*(B_HEIGHT/4));
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(ingame){
            checkapple();
            checkcollision();
            move();
        }
    repaint();
    }
    //snake move
    public void move(){
        for(int i=DOTS-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(upDirection){
           y[0]-=DOT_SIZE;
        }
        if(downDirection){
            y[0]+=DOT_SIZE;
        }
    }
    //food wiil eat by snake
    public void checkapple(){
        if(apple_x==x[0]&&apple_y==y[0]){
            DOTS++;
            locateapple();
        }
    }
    // implementation of controls
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&!rightDirection){
               leftDirection=true;
               upDirection=false;
               downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT&&!leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP&&!downDirection){
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN&&!upDirection){
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }
    }
}
