
import java.util.*;

/**
 * Тест производительности ArrayList и LinkedList
 * Сравнивает все основные операции: добавление, доступ, удаление, итерацию
 * в разных позициях коллекции
 *
 * @author Ilya
 * @version 1.0
 */

public class Test
{
    /** Количество операций для тестирования */
    private static int OPERATION_COUNT = 5000;


    public static void main(String[] args)
    {
        System.out.println("Сравнение производительности ARRAYLIST и LINKEDLIST");
        System.out.println("==============================================================");

        // Результаты для ArrayList
        List<TestResult> arrayListResults = testOperations(new ArrayList<>(), "ArrayList");

        // Результаты для LinkedList
        List<TestResult> linkedListResults = testOperations(new LinkedList<>(), "LinkedList");

        printTable(arrayListResults, linkedListResults);
    }

    /**
     * Запускает все тесты для одной коллекции
     *
     * @param list тестируемая коллекция
     * @param listName название коллекции для отладки
     * @return список результатов тестирования
     */

    private static List<TestResult> testOperations(List<Integer> list, String listName)
    {
        List<TestResult> results = new ArrayList<>();

        // операции

        results.add(new TestResult("add() end", OPERATION_COUNT, testAddToEnd(list)));
        results.add(new TestResult("get()", OPERATION_COUNT, testGet(list)));
        results.add(new TestResult("add(0) start", OPERATION_COUNT, testAddToBeginning(list)));
        results.add(new TestResult("remove(0) start", OPERATION_COUNT , testDeleteFromBeginning(list)));
        results.add(new TestResult("remove() end", OPERATION_COUNT, testDeleteFromEnd(list)));
        results.add(new TestResult("add(mid)", OPERATION_COUNT , testAddToMiddle(list)));
        results.add(new TestResult("remove(mid)", OPERATION_COUNT, testDeleteFromMiddle(list)));

        return results;
    }

    /**
     * Тестируем операцию добавления элементов в конец коллекции
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testAddToEnd(List<Integer> list)
    {
        list.clear();
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            list.add(i); // add() добавление без индекса - добавляет в конец
        }
        return System.nanoTime() - start;
    }

    /**
     * Тестируем операцию получения элементов по индексу
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testGet(List<Integer> list)
    {
        if (list.isEmpty())
        {
            testAddToEnd(list);
        }
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            list.get(i % list.size());
        }
        return System.nanoTime() - start;
    }

    /**
     * Тестируем операцию добавления элементов в начало коллекции
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testAddToBeginning(List<Integer> list)
    {
        list.clear();
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            list.add(0, i); // Добавление в начало
        }
        return System.nanoTime() - start;
    }

    /**
     * Тестируем операцию удаления элементов из начала коллекции
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testDeleteFromBeginning(List<Integer> list)
    {
        list.clear();
        testAddToEnd(list);
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            if (!list.isEmpty())
            {
                list.remove(0); // индекс 0, т.е удаление из начала
            }
        }
        return System.nanoTime() - start;
    }

    /**
     * Тестируем операцию удаления элементов из конца коллекции
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testDeleteFromEnd(List<Integer> list)
    {
        list.clear();
        testAddToEnd(list);
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            if (!list.isEmpty())
            {
                list.remove(list.size() - 1); // последний индекс наши и удаление с конца производим
            }
        }
        return System.nanoTime() - start;
    }

    /**
     * Тестируем операцию добавления элементов в середину коллекции
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testAddToMiddle(List<Integer> list)
    {
        list.clear();
        testAddToEnd(list);
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            int middleIndex = list.size() / 2;
            list.add(middleIndex, i); // добавление в середину
        }
        return System.nanoTime() - start;
    }

    /**
     * Тестирует операцию удаления элементов из середины коллекции
     *
     * @param list коллекция для тестирования
     * @return время выполнения в наносекундах
     */

    private static long testDeleteFromMiddle(List<Integer> list)
    {
        list.clear();
        testAddToEnd(list);
        long start = System.nanoTime();
        for (int i = 0; i < OPERATION_COUNT; i++)
        {
            if (!list.isEmpty())
            {
                int middleIndex = list.size() / 2;
                list.remove(middleIndex); // удаление из середины
            }
        }
        return System.nanoTime() - start;
    }



    /**
     * Таблица сравнения результатов
     *
     * @param arrayList результаты тестирования ArrayList
     * @param linkedList результаты тестирования LinkedList
     */

    private static void printTable(List<TestResult> arrayList, List<TestResult> linkedList)
    {
        System.out.println();
        System.out.println("Результаты (в наносекундах)");
        System.out.println("-----------------------------------------------------------");
        System.out.println("|     Метод      |  Кол-во    |  ArrayList  | LinkedList  |");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < arrayList.size(); i++)
        {
            TestResult arrResult = arrayList.get(i);
            TestResult linkResult = linkedList.get(i);

            System.out.printf("| %-14s | %-10d | %-11d | %-11d |\n",
                    arrResult.operation, arrResult.count,
                    arrResult.time, linkResult.time);
        }

        System.out.println("--------------------------------------------------------------");
        printConclusions();
    }



    /**
     * выводы
     */
    private static void printConclusions()
    {
        System.out.println();
        System.out.println("Выводы:");
        System.out.println();
        System.out.println(" ArrayList быстр в:");
        System.out.println("   Чтении по индексу (get)");
        System.out.println("   Добавлении в конец (add() end)");
        System.out.println("   Удалении с конца (remove() end)");
        System.out.println("   Итерации по элементам");
        System.out.println();
        System.out.println(" LinkedList быстр в:");
        System.out.println("   Добавлении в начало (add(0) start)");
        System.out.println("   Удалении из начала (remove(0) start)");
        System.out.println();
        System.out.println(" Какую коллекцию выбрать?");
        System.out.println("   Для частого чтения и работы с концом - ArrayList");
        System.out.println("   Для частой работы с началом - LinkedList");

    }

    /**
     * Внутренний класс для хранения результатов одного теста
     */

    static class TestResult
    {
        String operation;
        int count;
        long time;

        /**
         * Конструктор для создания результата теста
         *
         * @param operation название операции
         * @param count количество выполнений
         * @param time время выполнения в наносекундах
         */

        TestResult(String operation, int count, long time) {
            this.operation = operation;
            this.count = count;
            this.time = time;
        }
    }
}