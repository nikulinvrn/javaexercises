import yurievLessons.Lesson_2.CustomList;

public class Main {
    public static void main(String[] args) {
        // Here is the place for your advertisement =)
        CustomList list = new CustomList(new int[]{99, 99, 99, 99, 99, 99, 99});
        System.out.println(list);
        System.out.println(list.lenght());

        list.add(5, new int[]{1, 2, 3, 4, 5, 6});


        System.out.println(list);
        System.out.println(list.lenght());

    }
}
