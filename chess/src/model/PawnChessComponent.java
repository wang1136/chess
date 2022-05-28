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

public class PawnChessComponent extends ChessComponent{
    private static Image Pawn_WHITE;
    private static Image Pawn_BLACK;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (Pawn_WHITE == null) {
            Pawn_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (Pawn_BLACK == null) {
            Pawn_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = Pawn_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = Pawn_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if (color==ChessColor.WHITE){
            type = 'p';
        } else {
            type = 'P';
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
        if (getChessColor() == ChessColor.BLACK){
            if (tmpX == 1 ){
                if (boundary(tmpX+2,tmpY) && chessComponents[tmpX+2][tmpY].chessColor == ChessColor.NONE && chessComponents[tmpX+1][tmpY].chessColor == ChessColor.NONE ){
                    tmp.add(new ChessboardPoint(tmpX+2,tmpY));
                }
            }
            if (boundary(tmpX+1,tmpY) && chessComponents[tmpX+1][tmpY].chessColor == ChessColor.NONE){
                tmp.add(new ChessboardPoint(tmpX+1,tmpY));
            }
            if (boundary(tmpX+1,tmpY+1) && chessComponents[tmpX+1][tmpY+1].getChessColor() == ChessColor.WHITE){
                tmp.add(new ChessboardPoint(tmpX+1,tmpY+1));
            }
            if (boundary(tmpX+1,tmpY -1) && chessComponents[tmpX+1][tmpY-1].getChessColor() == ChessColor.WHITE){
                tmp.add(new ChessboardPoint(tmpX+1,tmpY-1));
            }
        }

        if (getChessColor() == ChessColor.WHITE) {
            if (tmpX == 6) {
                if (boundary(tmpX - 2, tmpY) && chessComponents[tmpX - 2][tmpY].chessColor == ChessColor.NONE && chessComponents[tmpX - 1][tmpY].chessColor == ChessColor.NONE) {
                    tmp.add(new ChessboardPoint(tmpX - 2, tmpY));
                }
            }
            if (boundary(tmpX - 1, tmpY) && chessComponents[tmpX - 1][tmpY].chessColor == ChessColor.NONE) {
                tmp.add(new ChessboardPoint(tmpX - 1, tmpY));
            }
            if (boundary(tmpX - 1, tmpY - 1) && chessComponents[tmpX - 1][tmpY - 1].getChessColor() == ChessColor.BLACK) {
                tmp.add(new ChessboardPoint(tmpX - 1, tmpY - 1));
            }
            if (boundary(tmpX - 1, tmpY + 1) && chessComponents[tmpX - 1][tmpY + 1].getChessColor() == ChessColor.BLACK) {
                tmp.add(new ChessboardPoint(tmpX - 1, tmpY + 1));
            }
        }

        tmp.sort(Comparator.comparingInt(ChessboardPoint::getY));
        tmp.sort(Comparator.comparingInt(ChessboardPoint::getX));
        return tmp;

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
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
