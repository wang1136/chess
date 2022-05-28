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

public class KingChessComponent extends ChessComponent{
    private static Image King_WHITE;
    private static Image King_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (King_WHITE == null) {
            King_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }
        if (King_BLACK == null) {
            King_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }
    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = King_WHITE;
            } else if (color == ChessColor.BLACK) {
                    kingImage = King_BLACK;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
            super(chessboardPoint, location, color, listener, size);
            initiateBishopImage(color);
            if (color==ChessColor.WHITE){
                type = 'k';
            } else {
                type = 'K';
            }
        }

        @Override
        public  boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
            return contains(canMoveToPoints(chessComponents),destination);
        }

        public List<ChessboardPoint> canMoveToPoints(ChessComponent[][] chessComponents){
            ArrayList<ChessboardPoint> tmp = new ArrayList<>();
            int tmpX = getChessboardPoint().getY();
            int tmpY = getChessboardPoint().getX();
            if (boundary(tmpY-1,tmpX-1) && checkChess(tmpY-1,tmpX-1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY-1,tmpX-1));
            }
            if (boundary(tmpY-1,tmpX) && checkChess(tmpY-1,tmpX,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY-1,tmpX));
            }
            if (boundary(tmpY-1,tmpX+1) && checkChess(tmpY-1,tmpX+1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY-1,tmpX+1));
            }
            if (boundary(tmpY,tmpX-1) && checkChess(tmpY,tmpX-1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY,tmpX-1));
            }
            if (boundary(tmpY,tmpX+1) && checkChess(tmpY,tmpX+1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY,tmpX+1));
            }
            if (boundary(tmpY+1,tmpX-1) && checkChess(tmpY+1,tmpX-1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY+1,tmpX-1));
            }
            if (boundary(tmpY+1,tmpX) && checkChess(tmpY+1,tmpX,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY+1,tmpX));
            }
            if (boundary(tmpY+1,tmpX+1) && checkChess(tmpY+1,tmpX+1,getChessColor())){
                tmp.add(new ChessboardPoint(tmpY+1,tmpX+1));
            }
            tmp.sort(Comparator.comparingInt(ChessboardPoint::getY));
            tmp.sort(Comparator.comparingInt(ChessboardPoint::getX));
            if (type=='K' && getChessboardPoint().getX() == 0 && getChessboardPoint().getY() == 4){
                if (chessComponents[0][0].type == 'R' && chessComponents[0][1].type == '_'
                        && chessComponents[0][2].type == '_' && chessComponents[0][3].type == '_'){
                    tmp.add(new ChessboardPoint(0,2));
                }
                if (chessComponents[0][7].type == 'R' && chessComponents[0][6].type == '_' && chessComponents[0][5].type == '_'){
                    tmp.add(new ChessboardPoint(0,6));
                }
            }
            if (type=='k' && getChessboardPoint().getX() == 7 && getChessboardPoint().getY() == 4){
                if (chessComponents[7][0].type =='r' && chessComponents[7][1].type == '_'
                        && chessComponents[7][2].type == '_' && chessComponents[7][3].type == '_'){
                    tmp.add(new ChessboardPoint(7,2));
                }
                if (chessComponents[7][7].type == 'r' && chessComponents[7][6].type == '_' && chessComponents[7][5].type == '_'){
                    tmp.add(new ChessboardPoint(7,6));
                }
            }
            return tmp;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
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

