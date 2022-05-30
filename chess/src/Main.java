import view.BackgroundPanel;
import view.ChessGameFrame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    int midTime = 60;
    public static void main(String[] args) {
        String path = ".\\chess\\musics\\背景音乐.wav";
        JFrame enter = new JFrame();
        enter.setSize(1500,800);
        enter.setResizable(false);
        enter.setLocationRelativeTo(null); // Center the window.
        enter.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        enter.setLayout(null);
        enter.setTitle("chess");
        enter.setVisible(true);
        ImageIcon ig = new ImageIcon("./chess/images/图标.png");
        enter.setIconImage(ig.getImage());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setSize(1500,800);
        layeredPane.setVisible(true);
        enter.setLayeredPane(layeredPane);


        JPanel background = new BackgroundPanel();
        background.setBounds(0,0,1500,800);
        background.setOpaque(true);

        JButton button = new JButton("Start game");
        button.addActionListener(e -> {
            enter.setVisible(false);
            SwingUtilities.invokeLater(() -> {
            new Thread(()->{while(true) {playMusic(path);}
            }).start();
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });

        });
        button.setLocation(650, 650);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        enter.add(button,JLayeredPane.MODAL_LAYER);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
    }
    static void playMusic(String path) {// 背景音乐播放
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value可以用来设置音量，从0-2.0
            double value = 0.5;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {// 判断 播放/暂停 状态

                if(flag) {

                    nByte = ais.read(buffer, 0, SIZE);

                    sdl.write(buffer, 0, nByte);

                }else {

                    nByte = ais.read(buffer, 0, 0);

                }

            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static boolean flag = true;


}
