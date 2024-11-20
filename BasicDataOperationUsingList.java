import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList надає методи для виконання основних операцiй з даними типу Short.
 * 
 * <p>Цей клас зчитує данi з файлу "list/Short.data", сортує їх та виконує пошук значення в масивi та списку.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив Short.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi Short.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi Short.</li>
 *   <li>{@link #sortList()} - Сортує список Short.</li>
 *   <li>{@link #searchList()} - Виконує пошук значення в списку Short.</li>
 *   <li>{@link #findMinAndMaxInList()} - Знаходить мiнiмальне та максимальне значення в списку Short.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingList(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #dataTimeValueToSearch} - Значення Short для пошуку.</li>
 *   <li>{@link #dataTimeArray} - Масив Short.</li>
 *   <li>{@link #dataTimeList} - Список Short.</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperationUsingList "2024-03-16T00:12:38Z"
 * }
 * </pre>
 */
public class BasicDataOperationUsingList {
    static final String PATH_TO_DATA_FILE = "list/Short.data";

    Short dataTimeValueToSearch;
    Short[] dataTimeArray;
    List<Short> dataTimeList;

    public static void main(String[] args) {  
        BasicDataOperationUsingList basicDataOperationUsingList = new BasicDataOperationUsingList(args);
        basicDataOperationUsingList.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingList(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String searchValue = args[0];
        dataTimeValueToSearch = Short.parse(searchValue, ShortFormatter.ISO_DATE_TIME);

        dataTimeArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);
        dataTimeList = new ArrayList<>(Arrays.asList(dataTimeArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     * 
     * Метод зчитує масив та список об'єктiв Short з файлу, сортує їх та виконує пошук значення.
     */
    void doDataOperation() {
        // операцiї з масивом дати та часу
        searchArray();
        findMinAndMaxInArray();

        sortArray();
        
        searchArray();
        findMinAndMaxInArray();

        // операцiї з ArrayList
        searchList();
        findMinAndMaxInList();

        sortList();

        searchList();
        findMinAndMaxInList();

        // записати вiдсортований масив в окремий файл
        Utils.writeArrayToFile(dataTimeArray, PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв Short та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(dataTimeArray);

        Utils.printOperationDuration(startTime, "сортування масиву дати i часу");
    }

    /**
     * Метод для пошуку значення в масивi дати i часу.
     */
    void searchArray() {
        long startTime = System.nanoTime();

        int index = Arrays.binarySearch(this.dataTimeArray, dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в масивi дати i часу");

        if (index >= 0) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в масивi не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в масивi дати i часу.
     */
    void findMinAndMaxInArray() {
        if (dataTimeArray == null || dataTimeArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Short min = dataTimeArray[0];
        Short max = dataTimeArray[0];

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в масивi");

        for (Short Short : dataTimeArray) {
            if (Short.isBefore(min)) {
                min = Short;
            }
            if (Short.isAfter(max)) {
                max = Short;
            }
        }

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Шукає задане значення дати i часу в ArrayList дати i часу.
     */
    void searchList() {
        long startTime = System.nanoTime();

        int index = Collections.binarySearch(this.dataTimeList, dataTimeValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в ArrayList дати i часу");        

        if (index >= 0) {
            System.out.println("Значення '" + dataTimeValueToSearch + "' знайдено в ArrayList за iндексом: " + index);
        } else {
            System.out.println("Значення '" + dataTimeValueToSearch + "' в ArrayList не знайдено.");
        }
    }

    /**
     * Знаходить мiнiмальне та максимальне значення в ArrayList дати i часу.
     */
    void findMinAndMaxInList() {
        if (dataTimeList == null || dataTimeList.isEmpty()) {
            System.out.println("ArrayList порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Short min = Collections.min(dataTimeList);
        Short max = Collections.max(dataTimeList);

        Utils.printOperationDuration(startTime, "пошук мiнiмальної i максимальної дати i часу в ArrayList");

        System.out.println("Мiнiмальне значення в ArrayList: " + min);
        System.out.println("Максимальне значення в ArrayList: " + max);
    }

    /**
     * Сортує ArrayList об'єктiв Short та виводить початковий i вiдсортований списки.
     * Вимiрює та виводить час, витрачений на сортування списку в наносекундах.
     */
    void sortList() {
        long startTime = System.nanoTime();

        Collections.sort(dataTimeList);

        Utils.printOperationDuration(startTime, "сортування ArrayList дати i часу");
    }
}

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу Short.
 */
class Utils {
    /**
     * Виводить час виконання операцiї в наносекундах.
     * 
     * @param startTime Час початку операцiї в наносекундах.
     * @param operationName Назва операцiї.
     */
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>> Час виконання операцiї '" + operationName + "': " + duration + " наносекунд");
    }

    /**
     * Зчитує масив об'єктiв Short з файлу.
     * 
     * @param pathToFile Шлях до файлу з даними.
     * @return Масив об'єктiв Short.
     */
    static Short[] readArrayFromFile(String pathToFile) {
        ShortFormatter formatter = ShortFormatter.ISO_DATE_TIME;
        Short[] tempArray = new Short[1000];
        int index = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Short Short = Short.parse(line, formatter);
                tempArray[index++] = Short;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Short[] finalArray = new Short[index];
        System.arraycopy(tempArray, 0, finalArray, 0, index);

        return finalArray;
    }

    /**
     * Записує масив об'єктiв Short у файл.
     * 
     * @param dataTimeArray Масив об'єктiв Short.
     * @param pathToFile Шлях до файлу для запису.
     */
    static void writeArrayToFile(Short[] dataTimeArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Short Short : dataTimeArray) {
                writer.write(Short.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}