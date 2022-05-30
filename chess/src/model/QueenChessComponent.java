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

public class QueenChessComponent extends ChessComponent{
    private static Image Queen_WHITE;
    private static Image Queen_BLACK;
    private Image queenImage;

    public void loadResource() throws IOException {
        if (Queen_WHITE == null) {
            Queen_WHITE = ImageIO.read(new File("./chess/images/Queen-white.png"));
        }
        if (Queen_BLACK == null) {
            Queen_BLACK = ImageIO.read(new File("./chess/images/Queen-black.png"));
        }
    }
    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = Queen_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = Queen_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if (color==ChessColor.WHITE){
            type = 'q';
        } else {
            type = 'Q';
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

        //Bishop
        for (int i=1;i<8;i++){
            if (boundary(tmpX-i,tmpY-i) ){
                if (checkChess(tmpX-i,tmpY-i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX-i,tmpY-i));
                }
                if(chessComponents[tmpX-i][tmpY-i].chessColor != ChessColor.NONE ){
                    break;
                }
            }
        }

        for (int i=1;i<8;i++){
            if (boundary(tmpX-i,tmpY+i) ){
                if (checkChess(tmpX-i,tmpY+i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX-i,tmpY+i));
                }
                if(chessComponents[tmpX-i][tmpY+i].chessColor != ChessColor.NONE ){
                    break;
                }
            }
        }

        for (int i=1;i<8;i++){
            if (boundary(tmpX+i,tmpY-i) ){
                if (checkChess(tmpX+i,tmpY-i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX+i,tmpY-i));
                }
                if(chessComponents[tmpX+i][tmpY-i].chessColor != ChessColor.NONE ){
                    break;
                }
            }
        }

        for (int i=1;i<8;i++){
            if (boundary(tmpX+i,tmpY+i) ){
                if (checkChess(tmpX+i,tmpY+i,getChessColor())){
                    tmp.add(new ChessboardPoint(tmpX+i,tmpY+i));
                }
                if(chessComponents[tmpX+i][tmpY+i].chessColor != ChessColor.NONE ){
                    break;
                }
            }
        }

        //Rook
        for (int i = 1; i < 8; i++) {
            if (boundary(tmpX - i, tmpY)) {
                if (chessComponents[tmpX-i][tmpY].getChessColor() != chessComponents[tmpX][tmpY].getChessColor()){
                    tmp.add(new ChessboardPoint(tmpX - i, tmpY));
                }
                if (chessComponents[tmpX-i][tmpY].chessColor != ChessColor.NONE) {
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (boundary(tmpX + i, tmpY)) {
                if (chessComponents[tmpX+i][tmpY].getChessColor() != chessComponents[tmpX][tmpY].getChessColor()){
                    tmp.add(new ChessboardPoint(tmpX + i, tmpY));
                }
                if (chessComponents[tmpX + i][tmpY].chessColor != ChessColor.NONE) {
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (boundary(tmpX, tmpY - i)) {
                if (chessComponents[tmpX][tmpY - i].getChessColor() != chessComponents[tmpX][tmpY].getChessColor()){
                    tmp.add(new ChessboardPoint(tmpX, tmpY - i));
                }
                if (chessComponents[tmpX][tmpY - i].chessColor != ChessColor.NONE) {
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (boundary(tmpX, tmpY + i)) {
                if (chessComponents[tmpX][tmpY + i].getChessColor() != chessComponents[tmpX][tmpY].getChessColor()){
                    tmp.add(new ChessboardPoint(tmpX, tmpY + i));
                }
                if (chessComponents[tmpX][tmpY + i].chessColor != ChessColor.NONE) {
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
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
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
