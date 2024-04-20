import javax.swing.*;

public class Frame extends JFrame {
    Frame(){
        setTitle("Visualise");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new Panel());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
