import java.util.Random;
import java.util.Scanner;

public class GuessNumberGame {
    public static void main(String[] args) {
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1; // 随机生成1到100之间的数字
        int playerGuess = 0;                          // 玩家猜测的数字
        int attempts = 0;                             // 玩家尝试次数
        Scanner scanner = new Scanner(System.in);     // 用于接收用户输入

        System.out.println("欢迎来到猜数字游戏！我想好了一个1到100之间的数字。");
        System.out.print("你能猜出来吗？请输入你的猜测：");

        while (true) {
            playerGuess = scanner.nextInt(); // 玩家输入数字
            attempts++;

            if (playerGuess < numberToGuess) {
                System.out.print("太小了，请再试一次：");
            } else if (playerGuess > numberToGuess) {
                System.out.print("太大了，请再试一次：");
            } else {
                System.out.println("恭喜你，猜对了！这个数字是 " + numberToGuess + "。");
                System.out.println("你总共猜了 " + attempts + " 次。");
                break; // 猜对了，游戏结束
            }
        }

        scanner.close(); // 关闭扫描器
    }
}
