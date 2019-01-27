package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MinesweeperAlgorithm extends JPanel implements MouseListener{
    
    private final BufferedImage flag;
    private final BufferedImage bomb;
    private boolean lose = false;
    private final int[][] chooseArray;
    private final int[][] bombArray;
    public int getBombArrayRows() {
        return bombArray.length;
    }
    public int getBomeArrayCols() {
        return bombArray[0].length;
    }
    int bombAmount = 0;
    
    private enum LevelParams {
        /*
            First and second parameter is for calculate chance of making a bomb
            Third and fourth parameter is for measure bomb map width and height
            DIFFICULTY_NAME(Section in which the number is drawn,
                            Increase random number to be higher than 0,
                            Rows of bomb array,
                            Columns of bomb array,
                            Amount of mines on bomb array)
        */
        EASY(9, 1, 10, 10),
        MEDIUM(7, 1, 20, 20),
        HARD(5, 1, 30, 30);

        int[] levelParams;

        LevelParams(int... levelParams) {
            this.levelParams = levelParams;
        }
    }
    
    public MinesweeperAlgorithm(String level) throws IOException{
        this.flag = ImageIO.read(new File(
                                 "C:\\Users\\pilar\\Documents\\NetBeansProject"+
                                 "s\\Minesweeper\\src\\minesweeper\\flag.png"));
        this.bomb = ImageIO.read(new File(
                                 "C:\\Users\\pilar\\Documents\\NetBeansProject"+
                                 "s\\Minesweeper\\src\\minesweeper\\bomb.png"));
        Random rand = new Random();
        LevelParams lp = LevelParams.valueOf(level);
        this.bombArray = new int[lp.levelParams[2]][lp.levelParams[3]];
        this.chooseArray = new int[lp.levelParams[2]][lp.levelParams[3]];
        for (int i = 0; i < bombArray.length; i++) {
            for (int j = 0; j < bombArray[0].length; j++) {
                if ((rand.nextInt(lp.levelParams[0])+lp.levelParams[1]) == 5) {
                    bombArray[i][j] = 9;
                    bombAmount+=1;
                    if (i-1 >= 0 && j-1 >= 0) {
                        if (bombArray[i-1][j-1] != 9)
                            bombArray[i-1][j-1]+=1;
                    }
                    if (j-1 >= 0) {
                        if (bombArray[i][j-1] != 9)
                            bombArray[i][j-1]+=1;
                    }
                    if (i+1 <= bombArray.length-1 && j-1 >= 0) {
                        if (bombArray[i+1][j-1] != 9)
                            bombArray[i+1][j-1]+=1;
                    }
                    if (i-1 >= 0) {
                        if (bombArray[i-1][j] != 9)
                            bombArray[i-1][j]+=1;
                    }
                    if (i+1 <= bombArray.length-1) {
                        if (bombArray[i+1][j] != 9)
                            bombArray[i+1][j]+=1;
                    }
                    if (i-1 >= 0 && j+1 <= bombArray[0].length-1) {
                        if (bombArray[i-1][j+1] != 9)
                            bombArray[i-1][j+1]+=1;
                    }
                    if (j+1 <= bombArray[0].length-1) {
                        if (bombArray[i][j+1] != 9)
                            bombArray[i][j+1]+=1;
                    }
                    if (i+1 <= bombArray.length-1 &&
                        j+1 <= bombArray[0].length-1) {
                        if (bombArray[i+1][j+1] != 9)
                            bombArray[i+1][j+1]+=1;
                    }
                }
            }
        }
        setFocusable(true);
        addMouseListener(this);
        setBackground(new Color(238, 238, 238));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(flag, 4, 4, null);
        g.setColor(Color.BLACK);
        g.drawRect((getBombArrayRows() * 32) - 100, 5, 95, 30);
        g.setFont(new Font("impact", Font.BOLD, 20));
        g.drawString("RESTART", (getBombArrayRows() * 32) - 89, 28);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("" + bombAmount, 40, 30);
        for (int i = 0; i < chooseArray.length; i++) {
            for (int j = 0; j < chooseArray[0].length; j++) {
                if (chooseArray[i][j] == 1) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(i * 32, (j * 32) + 40, 32, 32);
                    if (bombArray[i][j] == 1) {
                        g.setColor(Color.BLACK);
                    }
                    if (bombArray[i][j] == 2) {
                        g.setColor(Color.BLUE);
                    }
                    if (bombArray[i][j] == 3) {
                        g.setColor(Color.RED);
                    }
                    if (bombArray[i][j] >= 4 && bombArray[i][j] <= 8) {
                        g.setColor(Color.ORANGE);
                    }
                    g.setFont(new Font("serif", Font.BOLD, 25));
                    g.drawString("" + bombArray[i][j],
                                 10 + (32 * i), 65 + (32 * j));
                }
                if (chooseArray[i][j] == 2) {
                    g.setColor(Color.RED);
                    g.fillRect(i * 32, (j * 32) + 40, 32, 32);
                    if (bombArray[i][j] != 0) {
                        g.setColor(Color.BLACK);
                        g.setFont(new Font("serif", Font.BOLD, 25));
                        g.drawImage(bomb, 1 + (32 * i), 41 + (32 * j), null);
                    }
                }
                if (chooseArray[i][j] == 3) {
                    g.drawImage(flag, 1 + (32 * i), 41 + (32 * j), null);
                }
            }
        }
        for (int i = 0; i < bombArray.length; i++) {
            for (int j = 0; j < bombArray[0].length; j++) {
                g.setColor(Color.BLACK);
                g.drawRect(i * 32, (j * 32) + 40, 32, 32);
            }
        }
    }
    
    public void showBombArray() {
        for (int[] bombArray1 : bombArray) {
            for (int j = 0; j < bombArray[0].length; j++) {
                System.out.print(bombArray1[j] + ", ");
            }
            System.out.print("\n");
        }
        System.out.print("" + bombAmount);
    }
    
    private void bombClicked() {
        for (int i = 0; i < chooseArray.length; i++) {
            for (int j = 0; j < chooseArray[0].length; j++) {
                if (bombArray[i][j] == 9) {
                    chooseArray[i][j] = 2;
                }
            }
        }
        repaint();
    }
    
    private void showZeros(int x, int y) {
        if (bombArray[x][y] == 0 && (chooseArray[x][y] == 0 ||
                                     chooseArray[x][y] == 3)) {
            if (chooseArray[x][y] == 3){
                bombAmount+=1;
            }
            chooseArray[x][y] = 1;
            if (x-1 >= 0){
                showZeros(x-1,y);
            }
            if (x+1 <= chooseArray.length-1) {
                showZeros(x+1,y);
            }
            if (y-1 >= 0){
                showZeros(x,y-1);
            }
            if (y+1 <= chooseArray[0].length-1) {
                showZeros(x,y+1);
            }
        }
        if ((bombArray[x][y] >= 1 || bombArray[x][y] <= 8) &&
                (chooseArray[x][y] == 0 || chooseArray[x][y] == 3)) {
            if (chooseArray[x][y] == 3){
                bombAmount+=1;
            }
            chooseArray[x][y] = 1;
        }
    }
    
    private void chooseField(String typ, MouseEvent e) {
        for (int i = 0; i < chooseArray.length; i++) {
            for (int j = 0; j < chooseArray[0].length; j++) {
                if (e.getX() >= (i * 32) &&
                    e.getX() <= ((i * 32) + 32) &&
                    e.getY() >= ((j * 32) + 40) &&
                    e.getY() <= (((j * 32) + 40) + 32)){
                    if (typ.equals("COVERBOMB")){
                        if (chooseArray[i][j] == 0) {
                            chooseArray[i][j] = 3;
                            bombAmount-=1;
                        }else if (chooseArray[i][j] == 3) {
                            chooseArray[i][j] = 0;
                            bombAmount+=1;
                        }
                    }else{
                        if (bombArray[i][j] == 9) {
                            chooseArray[i][j] = 2;
                            bombClicked();
                            lose = true;
                        }else{
                            showZeros(i, j);
                        }
                    }
                }
            }
        }
        repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!lose) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                chooseField("COVERBOMB", e);
            }
            if (e.getButton() == MouseEvent.BUTTON1) {
                chooseField("DISCOVERFIELD", e);
            }
        }
        if (e.getX() >= (getBombArrayRows() * 32) - 100 &&
            e.getX() <= (getBombArrayRows() * 32) - 5 &&
            e.getY() >= 5 && e.getY() <= 30) {
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
