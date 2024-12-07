import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/short.data";
    private short valueToSearch;
    private Short[] shortArray;
    private List<Short> shortList;

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Введите значение для поиска в виде аргумента командной строки.");
        }

        short valueToSearch = Short.parseShort(args[0]);
        BasicDataOperationUsingList operation = new BasicDataOperationUsingList(valueToSearch);
        operation.performOperations();
    }

    public BasicDataOperationUsingList(short valueToSearch) {
        this.valueToSearch = valueToSearch;
        this.shortArray = readArrayFromFile(PATH_TO_DATA_FILE);
        this.shortList = Arrays.asList(shortArray);
    }

    public void performOperations() {
        System.out.println("Операции с массивом:");

        measureTime("Поиск в массиве", this::searchInArray);
        measureTime("Поиск минимального и максимального значения в массиве", this::findMinAndMaxInArray);
        measureTime("Сортировка массива", this::sortArray);
        measureTime("Поиск в массиве после сортировки", this::searchInArray);

        System.out.println("\nОперации со списком:");

        measureTime("Поиск в списке", this::searchInList);
        measureTime("Поиск минимального и максимального значения в списке", this::findMinAndMaxInList);
        measureTime("Сортировка списка", this::sortList);
        measureTime("Поиск в списке после сортировки", this::searchInList);

        writeArrayToFile(shortArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void measureTime(String operationName, Runnable operation) {
        // Форматирование текущей даты и времени
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy"));
        
        // Выводим текущее время
        System.out.println("Поточна дата і час: " + currentTime);

        long startTime = System.nanoTime();
        operation.run();
        long endTime = System.nanoTime();
        
        // Выводим время выполнения операции
        System.out.printf(">>>>>> Час виконання операції '%s': %d наносекунд%n", operationName, endTime - startTime);
    }

    private void searchInArray() {
        boolean found = Arrays.stream(shortArray).anyMatch(value -> value == valueToSearch);
        System.out.println(">>> Значення '" + valueToSearch + (found ? "' знайдено в масиві." : "' у масиві не знайдено."));
    }

    private void findMinAndMaxInArray() {
        Optional<Short> min = Arrays.stream(shortArray).min(Short::compareTo);
        Optional<Short> max = Arrays.stream(shortArray).max(Short::compareTo);

        System.out.println(">>> Мінімальне значення в масиві: " + min.orElse(null));
        System.out.println(">>> Максимальне значення в масиві: " + max.orElse(null));
    }

    private void sortArray() {
        shortArray = Arrays.stream(shortArray).sorted().toArray(Short[]::new);
        System.out.println(">>> Масив успішно відсортований.");
    }

    private void searchInList() {
        boolean found = shortList.stream().anyMatch(value -> value == valueToSearch);
        System.out.println(">>> Значення '" + valueToSearch + (found ? "' знайдено в списку." : "' у списку не знайдено."));
    }

    private void findMinAndMaxInList() {
        Optional<Short> min = shortList.stream().min(Short::compareTo);
        Optional<Short> max = shortList.stream().max(Short::compareTo);

        System.out.println(">>> Мінімальне значення у списку: " + min.orElse(null));
        System.out.println(">>> Максимальне значення у списку: " + max.orElse(null));
    }

    private void sortList() {
        shortList = shortList.stream().sorted().collect(Collectors.toList());
        System.out.println(">>> Список успішно відсортований.");
    }

    private Short[] readArrayFromFile(String pathToFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            return reader.lines().map(Short::parseShort).toArray(Short[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання файлу: " + e.getMessage(), e);
        }
    }

    private void writeArrayToFile(Short[] array, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Short value : array) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Помилка запису в файл: " + e.getMessage(), e);
        }
    }
}
