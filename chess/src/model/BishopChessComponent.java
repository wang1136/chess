package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BishopChessComponent extends ChessComponent {
    private static Image Bishop_WHITE;
    private static Image Bishop_BLACK;

    private Image bishopImage;

    public void loadResource() throws IOException {
        if (Bishop_WHITE == null) {
            Bishop_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (Bishop_BLACK == null) {
            Bishop_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = Bishop_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = Bishop_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if (color==ChessColor.WHITE){
            type = 'b';
        } else {
            type = 'B';
        }
    }

    @Override
    public  boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        return contains(canMoveToPoints(chessComponents),destination);
    }

    public List<ChessboardPoint> canMoveToPoints(ChessComponent[][] chessComponents){
        ArrayList<ChessboardPoint> tmp = new ArrayList<>();
        int tmpY = getChessboardPoint().getY();
        int tmpX = getChessboardPoint().getX();

        for (int i=1;i<8;i++){
            if (boundary(tmpX-i,tmpY-i) ){
                if (checkChess(tmpX-i,tmpY-i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX-i,tmpY-i));
                }
                if(chessComponents[tmpX-i][tmpY-i].getType() != '_'){
                    break;
                }
            }
        }

        for (int i=1;i<8;i++){
            if (boundary(tmpX-i,tmpY+i) ){
                if (checkChess(tmpX-i,tmpY+i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX-i,tmpY+i));
                }
                if(chessComponents[tmpX-i][tmpY+i].getType() != '_'){
                    break;
                }
            }
        }

        for (int i=1;i<8;i++){
            if (boundary(tmpX+i,tmpY-i) ){
                if (checkChess(tmpX+i,tmpY-i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX+i,tmpY-i));
                }
                if(chessComponents[tmpX+i][tmpY-i].getType() != '_'){
                    break;
                }
            }
        }

        for (int i=1;i<8;i++){
            if (boundary(tmpX+i,tmpY+i) ){
                if (checkChess(tmpX+i,tmpY+i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX+i,tmpY+i));
                }
                if(chessComponents[tmpX+i][tmpY+i].getType() != '_'){
                    break;
                }
            }
        }
        tmp.sort(Comparator.comparingInt(ChessboardPoint::getY));
        tmp.sort(Comparator.comparingInt(ChessboardPoint::getX));
        return tmp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(bishopImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(new Color(75,0,130, 150));
            g.fillRect(0, 0, getWidth() , getHeight());
        }
        if(isLight()){
            g.setColor(new Color(144,238,144,150));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        if (isPass()){
            g.setColor(new Color(84, 255, 159, 200));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }
}
