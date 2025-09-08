import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int sumN = 0;
        Scanner newScanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите путь: ");
            String path = newScanner.nextLine();
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


            int totalLines = 0;
            int maxLength = Integer.MIN_VALUE;
            int minLength = Integer.MAX_VALUE;

            try {

                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();

                    if (length > 1024) {
                        throw new TooLongException("Строка длиной более 1024 символа");
                    }

                    totalLines++;
                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (length < minLength) {
                        minLength = length;
                    }
                }


                System.out.println("Общее количество строк: " + totalLines);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);

                reader.close();

            } catch (TooLongException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}