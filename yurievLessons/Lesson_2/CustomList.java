/*
 * Задание:
 *  1. Реализовать методы:
 *      + add(int element) - добавить элемент в конец массива (с проверкой длины, увеличение в полтора раза)
 *      + add(int index, int element) - добавить элемент в начало массива
 *      +                             - добавить элемент в массив по индексу
 *      + add(int[] elements) - добавить массив чисел в конец
 *      + add(int index, int[] elements) - добавить массив чисел в начало
 *      +                               - добавить массив чисел по индексу (начиная с индекса)
 *      + merge(int[] fromArray, int[] toArray) - соединить массивы с заменой элементов по индексу в целевом из исходного
 *      + remove(int index) - удалить элемент по индексу
 *      + removeElementWithValue(int element) - удалить элемент по значению
 *      + remove(int... elements) - удалить n-индексов (реализовать через int... args) с пропорциональным уменьшением массива при необходимости
 *      + removeAllOfValue(int[] elements) - принимать массив чисел и удалять всякое вхождение в этот массив (аналог replace в строках)
 *      + getArray() - возвращение массива чисел, которые вводил пользователь (с отсечением хвоста из нулей)
 *      + replaceByIndex(int index, int element) - замена в индексе (почему не через присваивание? фиг с ним, больше инкапсуляции богу инкапсуляции)
 *      + length() - вернуть длину массива без хвоста из нулей
 *
 *  2. Геттеры и сеттеры приватных переменных почистить от лишних (сгенерированных)
 */

package yurievLessons.Lesson_2;

import java.util.Arrays;
import java.util.Objects;

public class CustomList {
    private static final int DEFAULT_ARRAY_CAPACITY = 10;

    private int[] array;
    private int size;




    public CustomList() {
        this.array = new int[DEFAULT_ARRAY_CAPACITY];
        this.size = 0;
    }

    public CustomList(int size) {
        this.array = new int[size];
        this.size = size;
    }

    public CustomList(int[] array) {
        this.size = array.length;
        this.array = array;
    }




    // Исправлено в связи с коррекцией ТЗ:
    // размер выходного массива соответствует размеру массива toArray
    // todo: проговорить кейсы применения этого метода, нифига не очевидно
    private int[] merge(int[] fromArray, int[] toArray) {
        System.arraycopy(fromArray, 0, toArray, 0, toArray.length);

        return toArray;
    }

    public void add(int element) {
        if (size >= array.length) {
            array = expandArray(array);
        }
        array[size] = element;
        size++;
    }

//    public void addAll(int... elements) { - добавляет возможность записывать просто список элементов для
//                                            добавления в массив, однако создает накладные расходы в виде массива в
//                                            массиве, если передаем массив аргументов
    public void addAll(int[] elements) {
        while (size + elements.length >= array.length) {
            array = expandArray(array);
        }
        System.arraycopy(elements, 0, array, size, elements.length);
        size += elements.length;
    }

    public void insert(int index, int value) {
        if (isIndexInvalid(index)) {
            System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                              "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
            return;
        }
        if (array.length <= size) array = expandArray(array);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    public void insertAll(int index, int[] elements) {
        if (isIndexInvalid(index)) {
            System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                              "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
            return;
        }
        while (array.length <= size + elements.length) {
            array = expandArray(array);
        }
        System.arraycopy(array, index, array, index + elements.length, size - index);
        System.arraycopy(elements, 0, array, index, elements.length);
        size += elements.length;
    }

    public int length() {
        return size;
    }

    //todo: выяснить "эксепшен — это же норма!" — нужно ли генерировать свой, если есть готовый?
    //      в целом, можно возвращать null, но это все равно в итоге где-то выдаст NPE.
    //      Получается, что проверка if-ом ничего не дает в рамках данного метода:
    //      исключение всё равно вылетает и оно понимаемо без «перевода».
    public int getValue(int index) {
        if (isIndexInvalid(index)) {
            System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                              "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
        }
        return array[index];
    }

    public int[] getArray() {
        int[] userArray = new int[size];
        System.arraycopy(array, 0, userArray, 0, size);

        return userArray;
    }

    public void replaceByIndex(int index, int value) {
        if (isIndexInvalid(index)) {
            System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                              "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
            return;
        }
        array[index] = value;
    }

    public void remove(int index) {
        if (isIndexInvalid(index)) {
            System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                              "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
            return;
        }
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
    }

    public void removeByValueOnce(int value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                size--;
                return;
            }
        }
        System.out.println("Совпадений в массиве не найдено.");
    }

    public void removeByValueAll(int value) {
        boolean isSomethingRemoved = false;
        for (int i = 0; i < size; i++) {
            if (array[i] == value) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                size--;
                isSomethingRemoved = true;
            }
        }
        if (!isSomethingRemoved) System.out.println("Совпадений в массиве не найдено.");
    }

    public void removeAllOfIndexList(int... indexList) {
        for (int index : indexList) {
            if (isIndexInvalid(index)) {
                System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                                  "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
                return;
            }
        }
        for (int i = 0; i < indexList.length; i++) {
            System.arraycopy(array, indexList[i] + 1, array, indexList[i], size - indexList[i] - i - 1);
        }
        size -= indexList.length;
    }

    public void removeAllOfValueList(int[] valueList) {
        int coincidenceCounter = 0;
        int cursor = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == valueList[0]) {
                cursor = i;
                for (int element : valueList) {
                    if (array[cursor] == element) {
                        coincidenceCounter++;
                        cursor++;
                    }
                }
            }
            if (coincidenceCounter != valueList.length) {
                cursor = 0;
            } else {
                System.arraycopy(array, cursor, array, cursor - valueList.length, size - i - valueList.length);
                coincidenceCounter = 0;
                size -= valueList.length;
            }
        }
    }




    private int[] expandArray(int[] array) {
        int[] expandedArray = new int[array.length + array.length / 2];
        System.arraycopy(array, 0, expandedArray, 0, array.length);

        return expandedArray;
    }

    private boolean isIndexInvalid(int index) {
        return index > size - 1 || index < 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomList that = (CustomList) o;

        //return Arrays.equals(array, that.array);
        return length() == that.length() && Arrays.equals(getArray(), that.getArray());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(length());
        result = 31 * result + Arrays.hashCode(getArray());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                sb.append(array[i]).append(", ");
            } else {
                sb.append(array[i]).append("]");
            }
        }

        return sb.toString();
    }
}



