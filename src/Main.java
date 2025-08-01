import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int sumN = 0;
        while (true) {
            System.out.println("Введите путь: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists != true) {
                System.out.println("Файл не найден");
                continue;
            }
            if (isDirectory) {
                System.out.println("Некорректный путь");
                continue;
            }

            sumN++;
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + sumN);

        }
    }
}
