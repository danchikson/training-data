import java.io.*;
import java.util.*;

public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "short.data";
    short valueToSearch;
    short[] shortArray;
    List<Short> shortList;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: не передано значение для поиска.");
            return; // Прекратить выполнение, если аргумент отсутствует
        }

        short valueToSearch = Short.parseShort(args[0]); // Пытаемся преобразовать аргумент в short
        BasicDataOperationUsingList operation = new BasicDataOperationUsingList(valueToSearch);
        operation.performOperations();
    }

    public BasicDataOperationUsingList(short valueToSearch) {
        this.valueToSearch = valueToSearch;
        this.shortArray = readArrayFromFile(PATH_TO_DATA_FILE);
        this.shortList = new ArrayList<>();
        for (short value : shortArray) {
            shortList.add(value);
        }
    }

    public void performOperations() {
        System.out.println("Операции с массивом:");
        measureTime("поиск в массиве", this::searchInArray);
        measureTime("поиск минимального и максимального значения в массиве", this::findMinAndMaxInArray);
        measureTime("сортировка массива в обратном порядке", this::sortArrayInReverse);

        System.out.println("\nОперации со списком:");
        measureTime("поиск в списке", this::searchInList);
        measureTime("поиск минимального и максимального значения в списке", this::findMinAndMaxInList);
        measureTime("сортировка списка в обратном порядке", this::sortListInReverse);

        writeArrayToFile(shortArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void measureTime(String operationName, Runnable operation) {
        long startTime = System.currentTimeMillis(); // Получаем время начала
        System.out.printf("Начало операции '%s' в: %d%n", operationName, startTime);
        operation.run();
        long endTime = System.currentTimeMillis(); // Получаем время окончания
        System.out.printf("Окончание операции '%s' в: %d%n", operationName, endTime);
        System.out.printf("Время выполнения '%s': %d миллисекунд%n", operationName, (endTime - startTime));
    }

    private void searchInArray() {
        int index = binarySearch(shortArray, valueToSearch);
        if (index >= 0) {
            System.out.println("Значение найдено по индексу: " + index);
        } else {
            System.out.println("Значение не найдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (shortArray.length == 0) {
            System.out.println("Массив пуст.");
            return;
        }
        short min = shortArray[0];
        short max = shortArray[0];
        for (short value : shortArray) {
            if (value < min) min = value;
            if (value > max) max = value;
        }
        System.out.println("Минимальное значение в массиве: " + min);
        System.out.println("Максимальное значение в массиве: " + max);
    }

    private void sortArrayInReverse() {
        Arrays.sort(shortArray);
        for (int i = 0, j = shortArray.length - 1; i < j; i++, j--) {
            short temp = shortArray[i];
            shortArray[i] = shortArray[j];
            shortArray[j] = temp;
        }
    }

    private void searchInList() {
        int index = Collections.binarySearch(shortList, valueToSearch);
        if (index >= 0) {
            System.out.println("Значение найдено в списке по индексу: " + index);
        } else {
            System.out.println("Значение не найдено в списке.");
        }
    }

    private void findMinAndMaxInList() {
        short min = shortList.get(0);
        short max = shortList.get(0);
        for (short value : shortList) {
            if (value < min) min = value;
            if (value > max) max = value;
        }
        System.out.println("Минимальное значение в списке: " + min);
        System.out.println("Максимальное значение в списке: " + max);
    }

    private void sortListInReverse() {
        Collections.sort(shortList, Collections.reverseOrder());
    }

    public static short[] readArrayFromFile(String pathToFile) {
        File file = new File(pathToFile);
        if (!file.exists()) {
            // Создаем файл с тестовыми данными, если его нет
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("1\n2\n3\n4\n5\n"); // Пример данных
            } catch (IOException e) {
                throw new RuntimeException("Ошибка создания файла: " + e.getMessage(), e);
            }
        }

        List<Short> values = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                values.add(Short.parseShort(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + e.getMessage(), e);
        }

        short[] result = new short[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    public static void writeArrayToFile(short[] array, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (short value : array) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл: " + e.getMessage(), e);
        }
    }

    public static int binarySearch(short[] array, short value) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] == value) {
                return mid;
            } else if (array[mid] < value) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
