import javax.swing.*;
import java.awt.*;

public class Suitcase {
    private int number;
    private ImageIcon image;

    public Suitcase(int number, ImageIcon image) {
        this.number = number;
        this.image = image;
    }

    public int getNumber(){
        return number;
    }

    public ImageIcon getImage(){
        return image;
    }

    public static Suitcase[] createSuitcases() {
        Suitcase[] suitcases = new Suitcase[10];
        for(int i = 0; i <10; i++) {
            int num = i + 1;
            String imagePath = "suitcase" + num + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            suitcases[i] = new Suitcase(num, resizedIcon);
        }
        return suitcases;
    }


}
