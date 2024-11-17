

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FootballGame extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int playerX = 100, playerY = 300; // 球员初始位置
    private int ballX = 150, ballY = 320;     // 足球初始位置
    private int ballSpeedX = 0, ballSpeedY = 0; // 足球速度
    private boolean hasBall = true;           // 球员是否持球
    private int score = 0;                    // 得分
    private BufferedImage fieldImage;         // 背景图片

    public FootballGame() {
        // 加载足球场背景图
        try {
            fieldImage = ImageIO.read(new File("resources/field.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置面板
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.GREEN);

        // 定时器
        timer = new Timer(20, this);
        timer.start();

        addKeyListener(this);
        setFocusable(true);
    }

    // 绘制组件
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制背景
        if (fieldImage != null) {
            g.drawImage(fieldImage, 0, 0, null);
        }

        // 绘制球员
        g.setColor(Color.BLUE);
        g.fillRect(playerX, playerY, 20, 40);

        // 绘制足球
        g.setColor(Color.ORANGE);
        g.fillOval(ballX, ballY, 20, 20);

        // 显示得分
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 650, 50);
    }

    // 更新游戏状态
    @Override
    public void actionPerformed(ActionEvent e) {
        // 更新足球位置
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // 速度衰减，模拟摩擦力
        ballSpeedX *= 0.98;
        ballSpeedY *= 0.98;

        // 检查足球是否停止
        if (Math.abs(ballSpeedX) < 0.1 && Math.abs(ballSpeedY) < 0.1) {
            ballSpeedX = 0;
            ballSpeedY = 0;
        }

        // 球进入球门 (简单检查：右侧边界)
        if (ballX > 770 && ballY > 250 && ballY < 350) {
            score++;
            resetBall();
        }

        repaint();
    }

    // 重置足球到球员位置
    private void resetBall() {
        ballX = playerX + 30;
        ballY = playerY + 10;
        hasBall = true;
    }

    // 键盘输入处理
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // 球员移动
        if (key == KeyEvent.VK_LEFT && playerX > 0) playerX -= 10;
        if (key == KeyEvent.VK_RIGHT && playerX < 760) playerX += 10;
        if (key == KeyEvent.VK_UP && playerY > 0) playerY -= 10;
        if (key == KeyEvent.VK_DOWN && playerY < 560) playerY += 10;

        // 移动足球跟随球员
        if (hasBall) {
            ballX = playerX + 30;
            ballY = playerY + 10;
        }

        // 射门
        if (key == KeyEvent.VK_SPACE && hasBall) {
            ballSpeedX = 10;
            ballSpeedY = (int) (Math.random() * 6 - 3); // 随机角度
            hasBall = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // 主方法
    public static void main(String[] args) {
        JFrame frame = new JFrame("Football Game");
        FootballGame game = new FootballGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
