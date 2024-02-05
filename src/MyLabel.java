package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

public class MyLabel extends JLabel {
    Font header = new Font("Serif", Font.BOLD, 50);
//    Border border = BorderFactory.createLineBorder(Color.green);
    public MyLabel(){
        this.setFont(header);
//        this.setBorder(border);
        this.setForeground(Color.white);
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
