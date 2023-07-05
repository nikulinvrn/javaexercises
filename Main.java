import yurievLessons.Lesson_2.CustomList;

public class Main {
    public static void main(String[] args) {
        // Here is the place for your advertisement =)
        CustomList list = new CustomList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Создаем объект класса CustomList: ");
        System.out.println(list);

        System.out.println("Проверим методы добавления элемеентов: ");
        list.add(6);
        System.out.println(list);

        list.add(0,0);
        System.out.println(list);

        list.add(new int[]{7,8,9});
        System.out.println(list);

        list.add(3, new int[]{33,22,11});
        System.out.println(list);

        System.out.println("Проверим методы удаления элемеентов: ");
        list.remove(0);
        System.out.println(list);
        list.removeAllOfValue(new int[]{5,6});
        System.out.println(list);
        list.removeAllOfIndex(new int[]{3,4});
        System.out.println(list);
        list.removeElementWithValue(33);
        System.out.println(list);










    }
}
