import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите число:");
        int number = new Scanner(System.in).nextInt();
        System.out.println("Введите число:");
        int numberTwo = new Scanner(System.in).nextInt();

        int sum = number + numberTwo;
        int sub =  number - numberTwo;
        int mult =  number * numberTwo;
        double quo = (double) number / numberTwo;
        System.out.println("+ == " + sum + "\n- == " + sub + "\n* == " + mult + "\n/ == " + quo);

    }
}
