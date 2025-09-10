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


            int totalRequests = 0;
            int googleBotCount = 0;
            int yandexBotCount = 0;

            try {
                Statictics stat = new Statictics();
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;

                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    LogEntry entry = new LogEntry(line);
                    stat.addEntry(entry);

                    if (length > 1024) {
                        throw new TooLongException("Строка длиной более 1024 символа");
                    }

                    totalRequests++;


                    int lastQuote = line.lastIndexOf("\"");
                    int secondLastQuote = line.lastIndexOf("\"", lastQuote - 1);
                    if (lastQuote == -1 || secondLastQuote == -1) {
                        continue;
                    }
                    String userAgent = line.substring(secondLastQuote + 1, lastQuote);

                    int start = userAgent.lastIndexOf('(');
                    int end = userAgent.lastIndexOf(')');
                    if (start == -1 || end == -1) {
                        continue;
                    }
                    String brakets = userAgent.substring(start + 1, end);

                    String[] parts = brakets.split(";");
                    if (parts.length >= 2) {
                        String fragment = parts[1].trim();
                        int slashIndex = fragment.indexOf('/');
                        if (slashIndex != -1) {
                            fragment = fragment.substring(0, slashIndex);
                        }
                        if (fragment.equals("Googlebot")) {
                            googleBotCount++;
                        }
                        if (fragment.equals("YandexBot")) {
                            yandexBotCount++;
                        }
                    }
                }
                System.out.println("Общее число запросов: " + totalRequests);
                if (totalRequests > 0) {
                    System.out.printf("Доля Googlebot: %.2f%%\n", (googleBotCount * 100.0 / totalRequests));
                    System.out.printf("Доля YandexBot: %.2f%%\n", (yandexBotCount * 100.0 / totalRequests));
                    System.out.printf("Средний трафик в час: %.2f байт\n", stat.getTrafficRate());
                    System.out.printf("Средний трафик в час: %.2f байт\n", stat.getTrafficRate());
                    System.out.println("Доля инф. системы в общем количестве:");
                    stat.getProportionOs().forEach((key, value) -> System.out.println(key + ":" + String.format("%.2f", value)));
                    System.out.println();
                    System.out.println("Доля браузера в общем количестве:");
                    stat.getProportionBrowser().forEach((key, value) -> System.out.println(key + ":" + String.format("%.2f", value)));
                    System.out.println();
                }
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