/**
 * Задание:
 *  1. Реализовать методы:
 *      add(int element) - добавить элемент в конец массива (с проверкой длины, увеличение в полтора раза)
 *      add(int index, int element) - добавить элемент в начало массива
 *                                  - добавить элемент в массив по индексу
 *      add(int[] elements) - добавить массив чисел в конец
 *      add(int index, int[] elements) - добавить массив чисел в начало
 *                                     - добавить массив чисел по индексу (начиная с индекса)
 *      remove(int index) - удалить объект по индексу
 *      remove(int element) - удалить объект по значению
 *      remove(int... elements) - удалить n-индексов (реализовать через int... args) с пропорциональным уменьшением массива при необходимости
 *      getArray() - возвращение массива чисел, которые вводил пользователь (с отсечением хвоста из нулей)
 *      removeAll(int[] elements) - принимать массив чисел и удалять всякое вхождение в этот массив (аналог replace в строках)
 *      replaceByIndex(int index, int element) - замена в индексе (почему не через присваивание? фиг с ним, больше инкапсуляции богу инкапсуляции)
 *      length() - вернуть длину массива без хвоста из нулей
 *  2. Геттеры и сеттеры приватных переменных почистить от лишних (сгенерированных)
 */

package jurievLessons.Lesson_2;

import java.util.Arrays;
import java.util.Objects;

public class CustomList {
    private int[] array;
    private int size;

    public CustomList(int[] array, int size) {
        this.array = array;
        this.size = size;
    }

    public int[] getArray() { // он тут вообще нужен? Возвращать с хвостом не труЪ. В хеш и иквалз можно передавать поле внутри класса (this.array)
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) { //и зачем оно мне тут? Оно должно инкрементироваться же ж или декрементироваться в работе
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomList that)) return false; // что за that?
        return getSize() == that.getSize() && Arrays.equals(getArray(), that.getArray());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSize());
        result = 31 * result + Arrays.hashCode(getArray());
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomList{");
        sb.append("array=").append(Arrays.toString(array));
        sb.append('}');
        return sb.toString();
    }
}



