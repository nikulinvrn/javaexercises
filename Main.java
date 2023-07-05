import yurievLessons.Lesson_2.CustomList;

public class Main {
    public static void main(String[] args) {
        // Here is the place for your advertisement =)
        CustomList list = new CustomList(new int[]{9, 9, 9, 9, 9, 9, 9, 9});
        System.out.println(list);
        System.out.println(list.length());

        list.add(3, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});


        System.out.println(list);
        System.out.println(list.length());

    }
}
