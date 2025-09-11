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


            try {
                Statictics stat = new Statictics();
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                int lineNumber = 0;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    int length = line.length();
                    if (length > 1024) {
                        throw new TooLongException("Строка длиной более 1024 символа. Номер строки: " + lineNumber);
                    }
                    LogEntry entry = new LogEntry(line);
                    stat.addEntry(entry);
                }
                reader.close();
                stat.printStatistics();
            } catch (TooLongException e) {
                System.out.println(e.getMessage());
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}