/*
 * Задание:
 *  1. Реализовать методы:
 *      + add(int element) - добавить элемент в конец массива (с проверкой длины, увеличение в полтора раза)
 *      + add(int index, int element) - добавить элемент в начало массива
 *      +                             - добавить элемент в массив по индексу
 *      + add(int[] elements) - добавить массив чисел в конец
 *      + add(int index, int[] elements) - добавить массив чисел в начало
 *      +                               - добавить массив чисел по индексу (начиная с индекса)
 *      + merge(int[] fromArray, int[] toArray) - соединить массивы с заменой элементов по индексу в целевом из исходного. Полезно в конструкторах и добавлении
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
    private static final int CUSTOM_ARRAY_CAPACITY = 10;
    private int[] array;
    private int size;

    /**
     * Конструктор при путых параметрах: создаем массив емкостью CUSTOM_ARRAY_CAPACITY
     * и числом пользовательских элементов 0
     */
    public CustomList() {
        this.array = new int[CUSTOM_ARRAY_CAPACITY];
        this.size = 0;
    }

    public CustomList(int size) {
        this.array = new int[size];
        this.size = size;
    }

    /**
     * Конструктор с передачей ссылки на существующий массив: число пользовательских
     * элементов вычисляется через размер исходного массива, емкость CustomList'а формируем
     * через проверку соответствия длины пользовательского массива текущей ёмкости CustomList'a.
     *
     * @param array
     */
    public CustomList(int[] array) {
        this.size = array.length;
        this.array = array;
        /*if (this.size <= CUSTOM_ARRAY_CAPACITY) {
            this.array = new int[CUSTOM_ARRAY_CAPACITY];
            merge(array, this.array);
        } else {
            int arrayCapacity = CUSTOM_ARRAY_CAPACITY;
            while (array.length > arrayCapacity) {
                arrayCapacity += arrayCapacity / 2;
            }
            this.array = new int[arrayCapacity];
            merge(array, this.array);
        }*/
    }


    private int[] expandArray(int[] arrayForExpend) {
        int[] bufferArray = new int[arrayForExpend.length + arrayForExpend.length / 2];
        return merge(arrayForExpend, bufferArray);
    }

    /**
     * Метод заполняет элементами исхоодного массива начало массива целевого с заменой элементов по индексу.
     *
     * @param fromArray — исходный массив;
     * @param toArray — целевой массив;
     * @return конечный массив с замененными элементами.
     * Если целевой массив меньше исходного — возвращаем исходный массив.
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
    // подразумевалось: есть первоначальное состояние и конечное, размер должен был соответствовать размеру конечного массива;

    public void add(int element) {
        if (this.size >= this.array.length) {
            this.array = expandArray(this.array);
        }
        this.array[this.size] = element;
        this.size++;
    }

    public void add(int[] elements) { //лучше переобозвать на addAll: если меняется логика — нужно менять именование
        while (this.size + elements.length >= this.array.length) {
            this.array = expandArray(this.array);
        }
        for (int i = 0; i < elements.length; i++) {
            this.array[i + size] = elements[i];
        }
        size += elements.length;
    }

    public void add(int index, int value) { // лучше переобозвать на insert, put etc.
        if (isIndexInvalid(index, this.size)) return;
        if (this.array.length <= this.size) {
            this.array = expandArray(this.array);
        }
        int[] arrayBuffer = merge(this.array, new int[this.array.length]);
        for (int i = index; i < arrayBuffer.length - 1; i++) {
            this.array[i + 1] = arrayBuffer[i];
        }
        this.array[index] = value;
        this.size++;
    }

    public void add(int index, int[] elements) { // todo перебрать все методы с чеками, принты разбивать на строки (небольно)
        if (isIndexInvalid(index, this.size)) {
            System.out.printf("Выход за пределы пользовательского массива, индекс не существует. \n" +
                    "ArrayIndexOutOfBoundException: Index %d out of bounds for length %d \n",
                    index,
                    this.size);
            return; // выбрасываем текст на слое представления
        }
        int[] bufferArray = merge(this.array, new int[this.array.length]);
        while (this.size + elements.length >= this.array.length) {
            this.array = expandArray(this.array);
        }
        for (int i = 0; i < elements.length; i++) {
            this.array[i + index] = elements[i];
        }
        for (int i = 0; i < this.size - index; i++) {
            this.array[i + index + elements.length] = bufferArray[i + index];
        }
        this.size += elements.length;
    }

    public int length() {
        return this.size;
    }

    public int getValue(int index) {
        if (isIndexInvalid(index, this.size)) return -1;

        return this.array[index];
    }

    public int[] getArray() {
        int[] userArray = new int[this.size];
        // IDEA предлагает заменить на System.arraycopy(array, 0, userArray, 0, size);
        for (int i = 0; i < this.size; i++) {
            userArray[i] = this.array[i];
        }

        return userArray;
    }

    public void replaceByIndex(int index, int element) {
        if (isIndexInvalid(index, this.size)) {
            return;
        }
        this.array[index] = element;
    }

    public void remove(int index) {
        if (isIndexInvalid(index, this.size)) {
            return;
        }
        int[] arrayBuffer = new int[this.array.length];
        for (int i = 0; i < this.array.length; i++) {
            arrayBuffer[i] = this.array[i];
        }
        for (int i = 0; i < this.array.length; i++) {
            if (i >= index && i < arrayBuffer.length - 1) {
                this.array[i] = arrayBuffer[i + 1];
            }
        }
        this.size--;
    }

    /**
     * Удаляет все вхождения. Можно доработать до удаления только первого, только последнего или i-го вхождения.
     */
    public void removeElementByValue(int value) {
        CustomList removableArray = new CustomList(this.array);
        //boolean isRemoved = false;
        for (int i = 0; i < this.size; i++) {
            if (removableArray.getValue(i) == value) {
                removableArray.remove(i);
                this.size--;
                this.array = removableArray.getArray();
                System.out.println("Совпадений в массиве не найдено.");
                return;
            }
        }
    }

    public void removeAllOfIndex(int... indexes) { // на будущее - собрать на примитивах не взирая на сложность
        CustomList modifiedList = new CustomList(this.array);
        int counter = 0;
        int flagCorrector = 0;
        while (counter < indexes.length) {
            modifiedList.remove(indexes[counter] - flagCorrector);
            flagCorrector++;
            counter++;
            this.size--;
        }
        this.array = modifiedList.getArray();
    }

    public void removeAllOfValue(int[] elements) {
        CustomList list = new CustomList(this.array);
        int coincidenceCounter = 0;
        int cursor = 0;
        for (int i = 0; i < list.length(); i++) {
            if (list.getValue(i) == elements[0]) {
                cursor = i;
                for (int element : elements) {
                    if (list.getValue(cursor) == element) {
                        coincidenceCounter++;
                        cursor++;
                    }
                }
            }
            if (coincidenceCounter != elements.length) {
                cursor = 0;
            } else {
                while (cursor > i) {
                    list.remove(cursor - 1);
                    this.size--;
                    cursor--;
                }
                coincidenceCounter = 0;
            }
        }
        this.array = list.getArray();
    }

    private boolean isIndexInvalid(int index, int arrayLength) { // isIndexValid — переименовать, правило хорошего тона
        return index > arrayLength - 1 || index < 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomList that)) return false; // что за that?
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
        for (int i = 0; i < this.size; i++) {
            if (i < this.size - 1) {
                sb.append(array[i]).append(", ");
            } else {
                sb.append(array[i]).append("]");
            }
        }

        return sb.toString();
    }
}



