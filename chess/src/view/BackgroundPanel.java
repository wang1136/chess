package view;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    @Override
    public void paint(Graphics a){
        String BackgroundPath = "./chess/images/主窗体背景图片.png";
        Image ImgBackground = Toolkit.getDefaultToolkit().getImage(BackgroundPath);
        a.drawImage(ImgBackground,0,0,this);
    }
}
