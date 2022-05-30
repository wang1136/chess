package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public JLabel statusLabel = new JLabel("The current player is WHITE");
    public JLabel time = new JLabel("Time: 60");
    public ChessPanel background = new ChessPanel();
    JLayeredPane layeredPane;
    Chessboard chessboard;
    public ChessGameFrame(int width, int height) {
        layeredPane = new JLayeredPane();
        setTitle("chess"); //设置标题
        this.WIDTH = 1200;
        this.HEIGHT = 800;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        ImageIcon ig = new ImageIcon("./chess/images/图标.png");
        setIconImage(ig.getImage());

        setLayeredPane(layeredPane);
        background = new ChessPanel();
        background.setBounds(0,0,WIDTH,HEIGHT);
        background.setOpaque(true);

        layeredPane.setSize(WIDTH,HEIGHT);
        layeredPane.setVisible(true);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);

        chessboard= new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        addChessboard();
        addLabel();
        addResetButton();
        addLoadButton();
        addSaveButton();
        addRetractButton();
//        addReviewButton();
//        addChangeStyle();
//        addTime();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {

        chessboard.chessGameFrame = this;
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        chessboard.setOpaque(true);
        chessboard.setAlpha(1f);
        layeredPane.add(chessboard,JLayeredPane.MODAL_LAYER);
    }
    public void changeColor(){
        statusLabel.setText("The current player is" +" "+gameController.getChessboard().getCurrentColor());
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel.setLocation(HEIGHT-50, HEIGHT / 10+20);
        statusLabel.setSize(500, 100);
        Color tmp = new Color(255, 215, 0);
        statusLabel.setForeground(tmp);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        layeredPane.add(statusLabel,JLayeredPane.MODAL_LAYER);
    }


    /**
     * 在游戏面板中增加一个按钮，
     */

    private void addResetButton() {
        JButton button = new JButton("Reset game");
        button.addActionListener(e -> {
           gameController.getChessboard().resetGame();
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
    }
    private void addRetractButton() {
        JButton button = new JButton("Retract");
        button.addActionListener(e -> {
           Chessboard tmp = gameController.getChessboard();
           if (tmp.getRecord().size()>1){
               tmp.getRecord().remove(tmp.getRecord().size()-1);
               gameController.getChessboard().setChessComponents(tmp.getRecord().get(tmp.getRecord().size()-1));
               gameController.getChessboard().swapColor();
           }
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);

    }
//    private void addReviewButton() {
//        JButton button = new JButton("Review game");
//        button.addActionListener(e -> {
//            try {
//                gameController.getChessboard().review();
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }
//        });
//        button.setLocation(HEIGHT, HEIGHT / 10 + 300);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
//
//    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);

        button.addActionListener(e -> {
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            String f = fd.getSelectedFile().getAbsolutePath();
            gameController.loadGameFromFile(f);
        });
    }
    private void addSaveButton() {
        JButton button = new JButton("Save game");
        button.addActionListener(e -> {
            JFileChooser fd = new JFileChooser();
            //fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fd.showOpenDialog(null);
            String f = fd.getSelectedFile().getAbsolutePath();
            gameController.writeStringToTxt(f, gameController.getChessboard().stringListChessboard());
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
    }
    private void addChangeStyle(){
        JButton button = new JButton("Change style");
        button.addActionListener((e -> {
            gameController.getChessboard().changeBackgroundColor();
        }));
        button.setLocation(HEIGHT, HEIGHT / 10 + 500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(button,JLayeredPane.MODAL_LAYER);
    }
    public void addDialog(String message){
        JOptionPane.showMessageDialog(null,message);
    }
    public int addUpDialog(){
        int tmp = JOptionPane.showOptionDialog(this, "选择升变为：","升变",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,
                null,new String[]{"皇后","骑士","战车","主教"},"皇后");
        System.out.println(tmp);
        return tmp;
    }

    public void addTime(){
        time.setLocation(HEIGHT-10, HEIGHT / 10 - 40);
        time.setSize(300, 60);
        time.setForeground(Color.RED);
        time.setFont(new Font("Rockwell", Font.BOLD, 20));
        layeredPane.add(time,JLayeredPane.MODAL_LAYER);
    }
//    public void changeTime(){
//        statusLabel.setText("The current player is" +" "+gameController.getChessboard().getTime());
//    }
}
