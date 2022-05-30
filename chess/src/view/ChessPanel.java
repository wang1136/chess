package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ChessPanel extends JPanel {
    @Override
    public void paint(Graphics a){
        String BackgroundPath = "./chess/images/背景.png";
        Image ImgBackground = Toolkit.getDefaultToolkit().getImage(BackgroundPath);
        a.drawImage(ImgBackground,0,0,this);
    }
////        绘制棋盘
//        String BackgroundPath = "images" + File.separator + "background1.png";
//        Image ImgBackground = Toolkit.getDefaultToolkit().getImage(BackgroundPath);
//        a.drawImage(ImgBackground,0,0,this);
////        黑车
//        String RookBlackPath = "images" + File.separator + "rook-black.png";
//        Image ImgRookBlack = Toolkit.getDefaultToolkit().getImage(RookBlackPath);
//        a.drawImage(ImgRookBlack,56,120,90,90,this);
//        a.drawImage(ImgRookBlack,476,120,90,90,this);
////        黑马
//        String KnightBlackPath = "images" + File.separator + "knight-black.png";
//        Image ImgKnightBlack = Toolkit.getDefaultToolkit().getImage(KnightBlackPath);
//        a.drawImage(ImgKnightBlack,116,120,90,90,this);
//        a.drawImage(ImgKnightBlack,416,120,90,90,this);
////        黑士
//        String PawnBlackPath = "images" + File.separator + "Pawn-black.png";
//        Image ImgPawnBlack = Toolkit.getDefaultToolkit().getImage(PawnBlackPath);
//        a.drawImage(ImgPawnBlack,176,120,93,93,this);
//        a.drawImage(ImgPawnBlack,356,120,93,93,this);
////        黑后
//        String QueenBlackPath = "images" + File.separator + "Queen-black.png";
//        Image ImgQueenBlack = Toolkit.getDefaultToolkit().getImage(QueenBlackPath);
//        a.drawImage(ImgQueenBlack,238,120,88,88,this);
////        黑王
//        String KingBlackPath = "images" + File.separator + "King-black.png";
//        Image ImgKingBlack = Toolkit.getDefaultToolkit().getImage(KingBlackPath);
//        a.drawImage(ImgKingBlack,297,120,88,88,this);
////        黑兵
//        String BishopBlackPath = "images" + File.separator + "bishop-black.png";
//        Image ImgBishopBlack = Toolkit.getDefaultToolkit().getImage(BishopBlackPath);
//        a.drawImage(ImgBishopBlack,56,180,90,90,this);
//        a.drawImage(ImgBishopBlack,116,180,90,90,this);
//        a.drawImage(ImgBishopBlack,176,180,90,90,this);
//        a.drawImage(ImgBishopBlack,236,180,90,90,this);
//        a.drawImage(ImgBishopBlack,296,180,90,90,this);
//        a.drawImage(ImgBishopBlack,356,180,90,90,this);
//        a.drawImage(ImgBishopBlack,416,180,90,90,this);
//        a.drawImage(ImgBishopBlack,476,180,90,90,this);
////        白车
//        String RookWhitePath = "images" + File.separator + "rook-white.png";
//        Image ImgRookWhite = Toolkit.getDefaultToolkit().getImage(RookWhitePath);
//        a.drawImage(ImgRookWhite,56,535,90,90,this);
//        a.drawImage(ImgRookWhite,476,535,90,90,this);
////        白马
//        String KnightWhitePath = "images" + File.separator + "knight-white.png";
//        Image ImgKnightWhite = Toolkit.getDefaultToolkit().getImage(KnightWhitePath);
//        a.drawImage(ImgKnightWhite,116,535,90,90,this);
//        a.drawImage(ImgKnightWhite,416,535,90,90,this);
////        白士
//        String PawnWhitePath = "images" + File.separator + "Pawn-white.png";
//        Image ImgPawnWhite = Toolkit.getDefaultToolkit().getImage(PawnWhitePath);
//        a.drawImage(ImgPawnWhite,176,535,93,93,this);
//        a.drawImage(ImgPawnWhite,356,535,93,93,this);
////        白后
//        String QueenWhitePath = "images" + File.separator + "Queen-white.png";
//        Image ImgQueenWhite = Toolkit.getDefaultToolkit().getImage(QueenWhitePath);
//        a.drawImage(ImgQueenWhite,238,535,88,88,this);
////        白王
//        String KingWhitePath = "images" + File.separator + "King-white.png";
//        Image ImgKingWhite = Toolkit.getDefaultToolkit().getImage(KingWhitePath);
//        a.drawImage(ImgKingWhite,297,535,88,88,this);
////        白兵
//        String BishopWhitePath = "images" + File.separator + "bishop-white.png";
//        Image ImgBishopWhite = Toolkit.getDefaultToolkit().getImage(BishopWhitePath);
//        a.drawImage(ImgBishopWhite,56,475,90,90,this);
//        a.drawImage(ImgBishopWhite,116,475,90,90,this);
//        a.drawImage(ImgBishopWhite,176,475,90,90,this);
//        a.drawImage(ImgBishopWhite,236,475,90,90,this);
//        a.drawImage(ImgBishopWhite,296,475,90,90,this);
//        a.drawImage(ImgBishopWhite,356,475,90,90,this);
//        a.drawImage(ImgBishopWhite,416,475,90,90,this);
//        a.drawImage(ImgBishopWhite,476,475,90,90,this);
//    }
}
