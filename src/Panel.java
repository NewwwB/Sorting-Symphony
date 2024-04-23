import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Panel extends JPanel {
    final int SCREEN_WIDTH = 1200;
    final int SCREEN_HEIGHT = 600;
    final int BAR_WIDTH = 20;
    final int BAR_GAP = 40;
    int[] list;
    int listSize = 28;
    final int DELAY = 50;
    boolean active = false;
    Random random;
    Timer timer;

    Panel(){
        random = new Random();
//        timer = new Timer(DELAY, e -> repaint());
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        add(generateListButton());
        add(sortButton());

    }
    JButton generateListButton(){
        JButton button = new JButton();
        button.setText("generate");
        button.addActionListener(e -> generateList());
        return button;
    }
    JButton sortButton(){
        JButton button = new JButton();
        button.setText("sort");
        button.addActionListener(e -> sortList());
        return button;
    }
    void generateList(){
        list = new int[listSize];
        for(int i=0 ; i< list.length ; i++){
            list[i] = random.nextInt(400);
        }
        active = true;
        repaint();
    }
    void sortList(){
        Thread thread = new Thread(()-> {
            for(int i=0 ; i< list.length ; i++){
                for(int j=0 ; j< list.length -1 ; j++){
                    if(list[j] > list[j+1]){
                        int temp = list[j];
                        list[j] = list[j+1];
                        list[j+1] = temp;
                    }
                    active = true;
                    repaint();
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
    void draw(Graphics g){
        if(active){
            drawList(g);
            active = false;
        }
    }
    void drawList(Graphics g){
        int x= 50;
        int y= 50;
        for(int i=0 ; i<list.length ; i++){
            g.setColor(Color.blue);
            g.drawRect(x, y, BAR_WIDTH, list[i]);
            x+=BAR_GAP;
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
//        generateList();
//        drawList(g);
    }

}
