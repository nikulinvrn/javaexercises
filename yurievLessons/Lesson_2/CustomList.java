/*
 * Задание:
 *  1. Реализовать методы:
 *      add(int element) - добавить элемент в конец массива (с проверкой длины, увеличение в полтора раза)
 *      add(int index, int element) - добавить элемент в начало массива
 *                                  - добавить элемент в массив по индексу
 *      add(int[] elements) - добавить массив чисел в конец
 *      add(int index, int[] elements) - добавить массив чисел в начало
 *                                     - добавить массив чисел по индексу (начиная с индекса)
 *      merge(int[] fromArray, int[] toArray) - соединить массивы с заменой элементов по индексу в целевом из исходного. Полезно в конструкторах и добавлении
 *      remove(int index) - удалить объект по индексу
 *      remove(int element) - удалить объект по значению
 *      remove(int... elements) - удалить n-индексов (реализовать через int... args) с пропорциональным уменьшением массива при необходимости
 *      getArray() - возвращение массива чисел, которые вводил пользователь (с отсечением хвоста из нулей)
 *      removeAll(int[] elements) - принимать массив чисел и удалять всякое вхождение в этот массив (аналог replace в строках)
 *      replaceByIndex(int index, int element) - замена в индексе (почему не через присваивание? фиг с ним, больше инкапсуляции богу инкапсуляции)
 *      length() - вернуть длину массива без хвоста из нулей
 *
 *  2. Геттеры и сеттеры приватных переменных почистить от лишних (сгенерированных)
 */

package yurievLessons.Lesson_2;

import java.util.Arrays;
import java.util.Objects;

public class CustomList {
    private int[] array;
    private int size;
    private int cursor; // могу ли обойтись size'ом?
    private static final int CUSTOM_ARRAY_CAPACITY = 10;

    /**
     * Конструктор при путых параметрах: создаем массив емкостью CUSTOM_ARRAY_CAPACITY и числом пользовательских элементов 0
     */
    public CustomList() {
        this.array = new int[CUSTOM_ARRAY_CAPACITY];
        this.size = 0;
    }

    /**
     * Конструктор с передачей ссылки на существующий массив: число пользовательских элементов вычисляется через размер исходного массива, емкость CustomList'а формируем через проверку соответствия длины пользовательского массива текущей ёмкости CustomList'a.
     *
     * @param array
     */
    public CustomList(int[] array) {
        this.size = array.length;
        if (size <= CUSTOM_ARRAY_CAPACITY) {
            this.array = new int[CUSTOM_ARRAY_CAPACITY];
            merge(array, this.array);
        } else {
            int arrayCapacity = CUSTOM_ARRAY_CAPACITY;
            while (array.length > arrayCapacity) {
                arrayCapacity += arrayCapacity / 2;
            }
            this.array = new int[arrayCapacity];
            merge(array, this.array);
        }
    }

    private int[] expandArray(int[] arrayForExpend) {
        int[] bufferArray = new int[arrayForExpend.length + arrayForExpend.length / 2];
        return merge(arrayForExpend, bufferArray);
    }

    /**
     * Метод заполняет элементами исхоодного массива начало массива целевого с заменой элементов по индексу.
     *
     * @param fromArray
     * @param toArray
     * @return конечный массив с замененными элементами. Если целевой массив меньше исходного — возвращаем исходный массив.
     */
    private int[] merge(int[] fromArray, int[] toArray) {
        if (fromArray.length > toArray.length) {
            return fromArray;
        }
        for (int i = 0; i < fromArray.length; i++) {
            toArray[i] = fromArray[i];
        }

        return toArray;
    }


    public void add(int element) {
        if (size >= this.array.length) {
            this.array = expandArray(this.array);
        }
        this.array[size] = element;
        size++;
    }

    public boolean add(int index, int element) {
        if (index > size) {
            System.out.printf("Добавление элемента не выполнено! \nВыход за пределы пользовательского массива, индекс не существует. \nArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
            return false;
        }
        if (this.array.length <= size) {
            this.array = expandArray(this.array);
        }
        int[] arrayBuffer = merge(this.array, new int[this.array.length]);
        for (int i = index; i < arrayBuffer.length - 1; i++) {
            this.array[i + 1] = arrayBuffer[i];
        }
        this.array[index] = element;
        size++;

        return true;
    }

    public void add(int[] elements) {
        while (size + elements.length >= this.array.length) {
            this.array = expandArray(this.array);
        }
        for (int i = 0; i < elements.length; i++) {
            this.array[i + size] = elements[i];
        }
        size += elements.length;
    }

    public boolean add(int index, int[] elements) {
        if (index > size) {
            System.out.printf("Добавление элемента не выполнено! \nВыход за пределы пользовательского массива, индекс не существует. \nArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n", index, size);
            return false;
        }
        int[] bufferArray = merge(this.array, new int[this.array.length]);
        while (size + elements.length >= this.array.length) {
            this.array = expandArray(this.array);
        }
        for (int i = 0; i < elements.length; i++) {
            this.array[i + index] = elements[i];
        }
        for (int i = 0; i < size - index; i++) {
            this.array[i + index + elements.length] = bufferArray[i + index];
        }
        size += elements.length;
        return true;
    }

    public int[] getArray() {
        int[] userArray = new int[size];
        // Идея предлагает заменить на System.arraycopy(array, 0, userArray, 0, size);
        for (int i = 0; i < size; i++) {
            userArray[i] = array[i];
        }

        return userArray;
    }

    public int lenght() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomList that)) return false; // что за that?
        return lenght() == that.lenght() && Arrays.equals(getArray(), that.getArray());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lenght());
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



