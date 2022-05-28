import view.ChessGameFrame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class Main {
    int midTime = 60;
    public static void main(String[] args) {
        String path = ".\\musics\\背景音乐.wav";
        new Thread(()->{while(true) {playMusic(path);}
        }).start();// Lambda表达式
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				while (true) {
//					playMusic();
//				}
//			}
//		}).start();// 开启一个线程用来播放音乐



        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });
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
            int writeByte = 0;
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
    private  void playTimer() {
        while (midTime>0) {
            midTime--;
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
