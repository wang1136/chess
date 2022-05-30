package view;


import model.*;
import controller.ClickController;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    public ChessGameFrame chessGameFrame;

    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private  ArrayList<String> record = new ArrayList<>();
    private AlphaComposite cmp =AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
    private float alpha;

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initGame();
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        if (chessComponents[0][2].getType() == 'K' && chessComponents[0][0].getType() == 'R'){
            initRookOnBoard(0,3,ChessColor.BLACK);
            initEmptySlotOnBoard(0,0,ChessColor.NONE);
        }
        if (chessComponents[0][6].getType() == 'K' && chessComponents[0][7].getType() == 'R'){
            initRookOnBoard(0,5,ChessColor.BLACK);
            initEmptySlotOnBoard(0,7,ChessColor.NONE);
        }
        if (chessComponents[7][2].getType() == 'k' && chessComponents[7][0].getType() == 'r'){
            initRookOnBoard(7,3,ChessColor.WHITE);
            initEmptySlotOnBoard(7,0,ChessColor.NONE);
        }
        if (chessComponents[7][6].getType() == 'k' && chessComponents[7][7].getType() == 'r') {
            initRookOnBoard(7, 5, ChessColor.WHITE);
            initEmptySlotOnBoard(7, 7, ChessColor.NONE);
        }
        for (int i =0;i<8;i++){
            for (int j =0;j<8;j++){
                chessComponents[i][j].setAttack(false);
            }
        }
        for (int m=0;m<8;m++){
            for (int n=0;n<8;n++) {
                if (chessComponents[m][n].canMoveToPoints(chessComponents)!=null){
                    for (int i = 0; i < chessComponents[m][n].canMoveToPoints(chessComponents).size(); i++) {
                        ChessboardPoint chessboardPoint = chessComponents[m][n].canMoveToPoints(chessComponents).get(i);
                        if (chessComponents[chessboardPoint.getX()][chessboardPoint.getY()].getType() == 'K' ||
                            chessComponents[chessboardPoint.getX()][chessboardPoint.getY()].getType() == 'k') {
                            chessComponents[chessboardPoint.getX()][chessboardPoint.getY()].setAttack(true);
                            chessComponents[chessboardPoint.getX()][chessboardPoint.getY()].repaint();
                        }
                    }
                }
            }
        }

        if(checkBlackKing() && checkWhiteKing()){
            for (int i =0;i<8;i++){
            if (chessComponents[0][i].getType()=='p') {
                int tmp = chessGameFrame.addUpDialog();
                if (tmp == 0) {
                    initQueenOnBoard(0, i, ChessColor.WHITE);
                    repaint();
                    break;
                }
                if (tmp == 1) {
                    initKnightOnBoard(0, i, ChessColor.WHITE);
                    repaint();
                    break;
                }
                if (tmp == 2) {
                    initRookOnBoard(0, i, ChessColor.WHITE);
                    repaint();
                    break;
                }
                if (tmp == 3) {
                    initBishopOnBoard(0, i, ChessColor.WHITE);
                    repaint();
                    break;
                }
            }
            if (chessComponents[7][i].getType()=='P') {
                int tmp = chessGameFrame.addUpDialog();
                if (tmp == 0) {
                    initQueenOnBoard(7, i, ChessColor.BLACK);
                    repaint();
                    break;
                }
                if (tmp == 1) {
                    initKnightOnBoard(7, i, ChessColor.BLACK);
                    repaint();
                    break;
                }
                if (tmp == 2) {
                    initRookOnBoard(7, i, ChessColor.BLACK);
                    repaint();
                    break;
                }
                if (tmp == 3) {
                    initBishopOnBoard(7, i, ChessColor.BLACK);
                    repaint();
                    break;
                }
            }
            }
            record.add(stringChessboard());
            if(record.size()>=10){
                if (Objects.equals(record.get(record.size() - 1), record.get(record.size() - 5)) && Objects.equals(record.get(record.size() - 1), record.get(record.size() - 9))){
                    if (Objects.equals(record.get(record.size() - 2), record.get(record.size() - 6)) && Objects.equals(record.get(record.size() - 2), record.get(record.size() - 10))){
                        chessGameFrame.addDialog("三次重复和棋");
                    }
                }
            }
        }
//        boolean tmp =false;
//        for (int i =0;i<8;i++){
//            for (int j =0;j<8;j++){
//                if (chessComponents[i][j].getChessColor()==ChessColor.WHITE && chessComponents[i][j].canMoveToPoints(chessComponents)!=null){
//                    tmp = true;
//                    break;
//                }
//            }
//        }
//        if (!tmp){
//            chessGameFrame.addDialog("无子可动和棋");
//        }
//        for (int i =0;i<8;i++){
//            for (int j =0;j<8;j++){
//                if (chessComponents[i][j].getChessColor()==ChessColor.BLACK && chessComponents[i][j].canMoveToPoints(chessComponents)!=null){
//                    tmp = true;
//                    break;
//                }
//            }
//        }
//        if (!tmp){
//            chessGameFrame.addDialog("无子可动和棋");
//        }
        repaint();
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        chessGameFrame.changeColor();
    }

    private void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    private void initEmptySlotOnBoard(int row, int col,ChessColor color){
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessComponent.setChessComponents(chessComponents);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(cmp.derive(alpha));
        super.paintComponent(g2d);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
        if (loadCheck(chessData)){
           initiateEmptyChessboard();
           for (int k=0;k<8;k++){
               for (int m=0;m<8;m++){
                   if (chessData.get(k).charAt(m)=='K'){
                       initKingOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='k'){
                       initKingOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='Q'){
                       initQueenOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='q'){
                       initQueenOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='R'){
                       initRookOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='r'){
                       initRookOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='B'){
                       initBishopOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='b'){
                       initBishopOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='P'){
                       initPawnOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='p'){
                       initPawnOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='N'){
                       initKnightOnBoard(k, m, ChessColor.BLACK);
                   }
                   if (chessData.get(k).charAt(m)=='n'){
                       initKnightOnBoard(k, m, ChessColor.WHITE);
                   }
                   if (chessData.get(k).charAt(m)=='_'){
                       initEmptySlotOnBoard(k, m, ChessColor.NONE);
                   }
               }
           }
           if (chessData.get(8).charAt(0) == 'w'){
               currentColor = ChessColor.WHITE;
           } else {
               currentColor = ChessColor.BLACK;
           }
           record = new ArrayList<>();
           for (int i=9;i<chessData.size();i++){
               record.add(chessData.get(i));
           }
           repaint();
       }
    }

    public boolean loadCheck(List<String> chessData) {
        if (chessData.size() > 7) {
            for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 8) {
                    System.out.println("棋盘并非8*8");
                    chessGameFrame.addDialog("棋盘并非8*8");
                    return false;
                }
                for (int j = 0; j < 8; j++) {
                    if (chessData.get(i).charAt(j) != '_'
                            && chessData.get(i).charAt(j) != 'R' && chessData.get(i).charAt(j) != 'r'
                            && chessData.get(i).charAt(j) != 'N' && chessData.get(i).charAt(j) != 'n'
                            && chessData.get(i).charAt(j) != 'B' && chessData.get(i).charAt(j) != 'b'
                            && chessData.get(i).charAt(j) != 'Q' && chessData.get(i).charAt(j) != 'q'
                            && chessData.get(i).charAt(j) != 'K' && chessData.get(i).charAt(j) != 'k'
                            && chessData.get(i).charAt(j) != 'P' && chessData.get(i).charAt(j) != 'p') {
                        System.out.println("棋子并非是六种之一，棋子并非黑白棋子");
                        chessGameFrame.addDialog("棋子并非是六种之一，棋子并非黑白棋子");
                        return false;
                    }
                }
                if (chessData.size()==8) {
                    System.out.println("缺少下一步行棋方");
                    chessGameFrame.addDialog("缺少下一步行棋方");
                    return false;
                } else {
                    if(chessData.get(8).length()!=1){
                        System.out.println("缺少下一步行棋方");
                        chessGameFrame.addDialog("缺少下一步行棋方");
                        return false;
                    }
                }
                int counterWhite=0;
                int WhiteBisNum=0;
                int counterBlack=0;
                int BlackBisNum=0;
                for (int k=9;k<chessData.size();k++){
                    counterWhite=0;
                    WhiteBisNum=0;
                    counterBlack=0;
                    BlackBisNum=0;
                    for (int m =0;m<64;m++){
                        if(m<8 && chessData.get(k).charAt(m)=='P'){
                            System.out.println("储存的步骤非法");
                            chessGameFrame.addDialog("储存的步骤非法");
                            return false;
                        }
                        if(m>58 && chessData.get(k).charAt(m)=='p'){
                            System.out.println("储存的步骤非法");
                            chessGameFrame.addDialog("储存的步骤非法");
                            return false;
                        }
                        if (chessData.get(k).charAt(m)=='B'){
                            BlackBisNum+=1;
                            if((m/8)%2==0){
                                if (m%2==0){
                                    counterBlack+=0;
                                } else {
                                    counterBlack+=1;
                                }
                            } else {
                                if (m%2==0){
                                    counterBlack+=1;
                                } else {
                                    counterBlack+=0;
                                }
                            }
                        }
                        if(chessData.get(k).charAt(m)=='b'){
                            WhiteBisNum+=1;
                            if((m/8)%2==0){
                                if (m%2==0){
                                    counterWhite+=0;
                                } else {
                                    counterWhite+=1;
                                }
                            } else {
                                if (m%2==0){
                                    counterWhite+=1;
                                } else {
                                    counterWhite+=0;
                                }
                            }
                        }
                    }
                    if(BlackBisNum == 2 && counterBlack % 2 ==0){
                        System.out.println("储存的步骤非法");
                        chessGameFrame.addDialog("储存的步骤非法");
                        return false;
                    }
                    if(WhiteBisNum == 2 && counterWhite % 2 ==0){
                        System.out.println("储存的步骤非法");
                        chessGameFrame.addDialog("储存的步骤非法");
                        return false;
                    }
                }
            }
        } else {
            System.out.println("棋盘并非8*8");
            chessGameFrame.addDialog("棋盘并非8*8");
            return false;
        }

        return true;
    }

    public void initGame(){
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, 7, ChessColor.BLACK);
        initRookOnBoard(7, 0, ChessColor.WHITE);
        initRookOnBoard(7, 7, ChessColor.WHITE);
        initBishopOnBoard(0, 2,ChessColor.BLACK);
        initBishopOnBoard(0, 5,ChessColor.BLACK);
        initBishopOnBoard(7, 2,ChessColor.WHITE);
        initBishopOnBoard(7, 5,ChessColor.WHITE);
        initKnightOnBoard(0, 1,ChessColor.BLACK);
        initKnightOnBoard(0, 6,ChessColor.BLACK);
        initKnightOnBoard(7, 1,ChessColor.WHITE);
        initKnightOnBoard(7, 6,ChessColor.WHITE);
        initKingOnBoard(0, 4,ChessColor.BLACK);
        initKingOnBoard(7, 4,ChessColor.WHITE);
        initPawnOnBoard(1, 0,ChessColor.BLACK);
        initPawnOnBoard(1, 1,ChessColor.BLACK);
        initPawnOnBoard(1, 2,ChessColor.BLACK);
        initPawnOnBoard(1, 3,ChessColor.BLACK);
        initPawnOnBoard(1, 4,ChessColor.BLACK);
        initPawnOnBoard(1, 5,ChessColor.BLACK);
        initPawnOnBoard(1, 6,ChessColor.BLACK);
        initPawnOnBoard(1, 7,ChessColor.BLACK);
        initPawnOnBoard(6, 0,ChessColor.WHITE);
        initPawnOnBoard(6, 1,ChessColor.WHITE);
        initPawnOnBoard(6, 2,ChessColor.WHITE);
        initPawnOnBoard(6, 3,ChessColor.WHITE);
        initPawnOnBoard(6, 4,ChessColor.WHITE);
        initPawnOnBoard(6, 5,ChessColor.WHITE);
        initPawnOnBoard(6, 6,ChessColor.WHITE);
        initPawnOnBoard(6, 7,ChessColor.WHITE);
        initQueenOnBoard(0, 3,ChessColor.BLACK);
        initQueenOnBoard(7, 3,ChessColor.WHITE);
        record = new ArrayList<String>();
        record.add(stringChessboard());
    }

    public void resetGame(){
       initGame();
       this.currentColor = ChessColor.WHITE;
        chessGameFrame.changeColor();
       repaint();
    }

    public String stringListChessboard(){
        StringBuilder tmp = new StringBuilder();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                tmp.append(chessComponents[i][j].getType());
            }
            tmp.append("\n");
        }
        if (currentColor == ChessColor.BLACK){
            tmp.append('b');
        } else {
            tmp.append('w');
        }
        for (int k=0;k<record.size();k++){
            tmp.append("\n");
            tmp.append(record.get(k));
        }
        return tmp.toString();
    }

    public String stringChessboard(){
        StringBuilder tmp = new StringBuilder();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                tmp.append(chessComponents[i][j].getType());
            }
        }
        if (currentColor == ChessColor.BLACK){
            tmp.append('b');
        } else {
            tmp.append('w');
        }
        return tmp.toString();
    }
    
    public void loadFromString (String a){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                char c = a.charAt(8 * i + j);
                if (c =='K'){
                    initKingOnBoard(i, j, ChessColor.BLACK);
                }
                if (c =='k'){
                    initKingOnBoard(i, j, ChessColor.WHITE);
                }
                if (c =='Q'){
                    initQueenOnBoard(i, j, ChessColor.BLACK);
                }
                if (c =='q'){
                    initQueenOnBoard(i, j, ChessColor.WHITE);
                }
                if (c =='R'){
                    initRookOnBoard(i, j, ChessColor.BLACK);
                }
                if (c =='r'){
                    initRookOnBoard(i, j, ChessColor.WHITE);
                }
                if (c =='B'){
                    initBishopOnBoard(i, j, ChessColor.BLACK);
                }
                if (c =='b'){
                    initBishopOnBoard(i, j, ChessColor.WHITE);
                }
                if (c =='P'){
                    initPawnOnBoard(i, j, ChessColor.BLACK);
                }
                if (c =='p'){
                    initPawnOnBoard(i, j, ChessColor.WHITE);
                }
                if (c =='N'){
                    initKnightOnBoard(i, j, ChessColor.BLACK);
                }
                if (c =='n'){
                    initKnightOnBoard(i, j, ChessColor.WHITE);
                }
                if (c =='_'){
                    initEmptySlotOnBoard(i, j, ChessColor.NONE);
                }
            }
        }
    }

    public void setAlpha(float alpha){
        this.alpha=alpha;
    }

    public void setChessComponents(String chessComponents){
        initiateEmptyChessboard();
        loadFromString(chessComponents);
        repaint();
    }

//    public void review() throws InterruptedException {
//        for (int i=0; i<record.size();i++){
//            String tmp =this.getRecord().get(i);
//            loadFromString(tmp);
//            this.repaint();
//            if (i!=0){
//                try {
//                    System.out.println("start");
//                    Thread.sleep(1000);
//                    System.out.println("end");
//                }
//                catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    }


    public ArrayList<String> getRecord(){
        return record;
    }

    public ChessComponent[][] getChessComponents(){
        return chessComponents;
    }

    public void changeBackgroundColor(){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                chessComponents[i][j].changeBackgroundColor();
            }
        }
    }

    public boolean checkWhiteKing(){
        for (int i = 0; i<8;i++){
            for (int j =0;j<8;j++){
                if (chessComponents[i][j].getType() =='k'){
                    return true;
                }
            }
        }
        System.out.println("Black Win");
        chessGameFrame.addDialog("Black Win");
        return false;
    }

    public boolean checkBlackKing(){
        for (int i = 0; i<8;i++){
            for (int j =0;j<8;j++){
                if (chessComponents[i][j].getType() =='K'){
                    return true;
                }
            }
        }
        System.out.println("White Win");
        chessGameFrame.addDialog("White Win");
        return false;
    }

}
