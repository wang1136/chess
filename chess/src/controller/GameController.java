package controller;

import view.Chessboard;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        if (path.charAt(path.length()-3)!='t' || path.charAt(path.length()-2)!='x' || path.charAt(path.length()-1) !='t'){
            chessboard.chessGameFrame.addDialog("文件格式错误");
        } else {
            try {
                List<String> chessData = Files.readAllLines(Path.of(path));
                chessboard.loadGame(chessData);
                return chessData;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Chessboard getChessboard(){
        return chessboard;
    }

    public static int writeStringToTxt(String targetTxt, String str) {
        File file = new File(targetTxt);
        BufferedWriter bwriter;
        try {
            bwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bwriter.write(str);
            bwriter.flush();
            bwriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
