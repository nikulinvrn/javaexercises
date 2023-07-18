package yurievLessons.Lesson_2;

public class Main {
    public static void main(String[] args) {
        // Here is the place for your advertisement =)
        CustomList list = new CustomList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Создаем объект класса CustomList: ");
        System.out.println(list);

        System.out.println("Проверим методы добавления элемеентов: ");
        list.add(6);
        System.out.println(list);

        list.insert(5, 0);
        System.out.println(list);
        list.addAll(new int[]{7, 8, 9});
        System.out.println(list);

        list.insertAll(2, new int[]{33, 22, 11, 9});
        System.out.println(list);

        System.out.println("Проверим методы удаления элемеентов: ");
        list.remove(0);
        System.out.println(list);
        list.removeByValueOnce(33);
        System.out.println(list);

        list.removeAllOfValueList(new int[]{7, 8});
        list.removeByValueAll(9);
        System.out.println(list);
        list.removeAllOfIndexList(3, 4);
        System.out.println(list);

        System.out.println("Заменим первый элемент: ");
        list.replaceByIndex(0, 1001);
        System.out.println(list);
        System.out.println("Длина массива: " + list.length());


        System.out.println(list.getValue(10001));
    }
}
