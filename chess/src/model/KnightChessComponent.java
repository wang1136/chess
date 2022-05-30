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

public class KnightChessComponent extends ChessComponent{
    private static Image Knight_WHITE;
    private static Image Knight_BLACK;

    private Image knightImage;

    public void loadResource() throws IOException {
        if (Knight_WHITE == null) {
            Knight_WHITE = ImageIO.read(new File("./chess/images/knight-white.png"));
        }

        if (Knight_BLACK == null) {
            Knight_BLACK = ImageIO.read(new File("./chess/images/knight-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = Knight_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = Knight_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if (color==ChessColor.WHITE){
            type = 'n';
        } else {
            type = 'N';
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
        if (boundary(tmpX-2,tmpY-1) && checkChess(tmpX-2,tmpY-1,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX-2,tmpY-1));
        }
        if (boundary(tmpX-2,tmpY+1) && checkChess(tmpX-2,tmpY+1,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX-2,tmpY+1));
        }
        if (boundary(tmpX+2,tmpY-1) && checkChess(tmpX+2,tmpY-1,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX+2,tmpY-1));
        }
        if (boundary(tmpX+2,tmpY+1) && checkChess(tmpX+2,tmpY+1,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX+2,tmpY+1));
        }
        if (boundary(tmpX-1,tmpY-2) && checkChess(tmpX-1,tmpY-2,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX-1,tmpY-2));
        }
        if (boundary(tmpX-1,tmpY+2) && checkChess(tmpX-1,tmpY+2,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX-1,tmpY+2));
        }
        if (boundary(tmpX+1,tmpY-2) && checkChess(tmpX+1,tmpY-2,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX+1,tmpY-2));
        }
        if (boundary(tmpX+1,tmpY+2) && checkChess(tmpX+1,tmpY+2,getChessColor())){
            tmp.add(new ChessboardPoint(tmpX + 1,tmpY + 2));
        }

        tmp.sort(Comparator.comparingInt(ChessboardPoint::getY));
        tmp.sort(Comparator.comparingInt(ChessboardPoint::getX));
        return tmp;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
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
