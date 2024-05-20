
package snake.game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Board extends JPanel implements ActionListener{
    private Image apple;
    private Image dot;
    private Image head;
    
    private int dots;
    private  final int Random_position=29;
    private int apple_x;
    private int apple_y;
    private Timer timer;
    private  final int All_dots =900;
    private  final int dot_size=10;
    private final int x[]=new int[All_dots];
    private final int y[]=new int[All_dots];
    private boolean leftdirection= false;
    private boolean rightdirection= true;
    private boolean updirection= false;
    private boolean downdirection= false;
    private boolean inGame=true;

            Board(){
     addKeyListener(new TAdapter());
     setBackground(Color.BLACK);
     setPreferredSize(new Dimension(300,300));
     setFocusable(true);
     initgame();
     loadimages();
    }
    public void loadimages(){
        ImageIcon i1 =new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 =new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 =new ImageIcon(ClassLoader.getSystemResource("snake/game/icons/head.png"));
        head = i3.getImage();
    }
    public  void initgame(){
       dots=3;
       for(int i=0; i<dots; i++){
          y[i]=50;
          x[i]=50-i*dot_size;
       }
       locateapple();
       timer=new Timer(140, this);
       timer.start();
    }
    public void locateapple(){
       int r=(int)(Math.random()* Random_position);
       apple_x= dot_size*r;
       r=(int)(Math.random()* Random_position);
        apple_y= dot_size*r;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
         g.drawImage(apple,apple_x, apple_y,this);
        for(int i=0; i<dots; i++){
            if(i==0){
                g.drawImage(head,x[i], y[i], this);
            }
            else{
                 g.drawImage(dot,x[i], y[i], this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        }else{
            gameover(g);
        }
    }
    public void gameover(Graphics g){
        String msg ="GAME OVER";
        Font font =new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrices= getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(300-metrices.stringWidth(msg))/2,300/2);
    }
    
    public void move(){
        for(int i=dots; i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(updirection){
            y[0]= y[0]-dot_size;
        }
        if(downdirection){
            y[0]= y[0]+dot_size;
        }
        if(rightdirection){
            x[0]= x[0]+dot_size;
        }
        if(leftdirection){
            x[0]= x[0]-dot_size;
        }
       
    }
    public void checkapple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateapple();
        }
    }
    public void checkcollison(){
        for(int i= dots; i>0; i--){
            if((i > 4) && (x[0]==x[i]) && (y[0]==y[i])){
                inGame=false;
            }
        }
        if(y[0]>=300){
            inGame=false;
        }
        
         if(x[0]>=300){
            inGame=false;
        }
          if(y[0]<0){
            inGame=false;
        }
           if(x[0]<0){
            inGame=false;
        }
           if(!inGame){
               timer.stop();
           }
    }
    
     public void actionPerformed(ActionEvent ae){
         if(inGame){
         move();
         checkapple();
         checkcollison();
         }
         repaint();
     }
     public class TAdapter extends KeyAdapter{
       public void keyPressed(KeyEvent e){
           int key=e.getKeyCode();
           if(key == KeyEvent.VK_LEFT  && (!rightdirection)){
               leftdirection=true;
               updirection=false;
               downdirection=false;
           }
            if(key == KeyEvent.VK_RIGHT  && (!leftdirection)){
               rightdirection=true;
               updirection=false;
               downdirection=false;
           }
             if(key == KeyEvent.VK_UP  && (!downdirection)){
               updirection=true;
               leftdirection=false;
               rightdirection=false;
           }
              if(key == KeyEvent.VK_DOWN  && (!updirection)){
               downdirection=true;
               leftdirection=false;
               rightdirection=false;
           }
       }  
     }
}
