import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Panel extends JPanel {
    final int SCREEN_WIDTH = 1200;
    final int SCREEN_HEIGHT = 600;
    final int BAR_WIDTH = 20;
    final int BAR_GAP = 40;
    Bar[] list;
    int listSize = 28;
    static int delay = 100;
    final int DELAY_SLOW = 200;
    final int DELAY_FAST = 50;
    boolean active = false;
    Random random;
    Thread thread;
    static boolean isThreadActive=false;
    Panel(){
        random = new Random();
//        timer = new Timer(DELAY, e -> repaint());
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        add(generateListButton());
        add(sortButton());

    }
    JButton generateListButton() {
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
        if(thread!=null){
            thread.interrupt();
            isThreadActive = false;
        }
        list = new Bar[listSize];
        for(int i=0 ; i< list.length ; i++){
            list[i] = new Bar(random.nextInt(400));
        }
        active = true;
        repaint();
    }
    @SuppressWarnings("BusyWait")
    void sortList(){
        if(!isThreadActive){
            thread = new Thread(()-> {
                isThreadActive= true;
                int iteration=0;
                boolean stillSorting;
                for(int i=0 ; i< list.length ; i++){
                    stillSorting = false;
                    for(int j=0 ; j< list.length -1 ; j++){
                        if(list[j].height > list[j+1].height){
                            list[j].color = Color.red;
                            list[j+1].color = Color.red;
                            delay = DELAY_SLOW;
                        }
                        else {
                            list[j].color = Color.green;
                            list[j+1].color = Color.green;
                            delay = DELAY_FAST;
                        }
                        repaint();
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if(list[j].height > list[j+1].height){
                            int temp = list[j].height;
                            list[j].height = list[j+1].height;
                            list[j+1].height = temp;
                            stillSorting = true;
                        }
                        list[j].color = Color.blue;
                        list[j+1].color = Color.blue;
                        active = true;
                        System.out.println("iteration: "+ iteration++);
                    }
                    if(!stillSorting){
                        break;
                    }
                }
                isThreadActive=false;
            });
            thread.start();
        }

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
        for(Bar j: list){
            g.setColor(j.color);
            g.drawRect(x, y, BAR_WIDTH, j.height);
            x+=BAR_GAP;
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

}
