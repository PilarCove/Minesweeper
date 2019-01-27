package minesweeper;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

public class GameWindow {
    
    public MinesweeperAlgorithm ma;
    
    public GameWindow() throws IOException{
        this.ma = new MinesweeperAlgorithm("EASY");
        int WIDTH = (ma.getBombArrayRows()*32) + 7;
        int HEIGHT = (ma.getBomeArrayCols()*32) + 70;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();
        int X = (screenWidth/2)-(WIDTH/2);
        int Y = (screenHeight/2)-(HEIGHT/2);
        JFrame frame= new JFrame();	
        frame.setTitle("Minesweeper");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(X, Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(ma);
        frame.setVisible(true);
    }
}
