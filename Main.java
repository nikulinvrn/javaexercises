import yurievLessons.Lesson_2.CustomList;

public class Main {
    public static void main(String[] args) {
        // Here is the place for your advertisement =)
        CustomList list = new CustomList(new int[]{11, 12, 13, 14, 15});
        System.out.println(list);
        System.out.println(list.length());

        list.add(2, new int[]{1, 2, 3, 4, 5, 6});
//        list.replaceByIndex(11, 10001);

        System.out.println(list);
        list.remove(8);
        System.out.println(list);
        System.out.println(list.length());



    }
}
