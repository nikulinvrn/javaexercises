package yurievLessons.Lesson_1;

public class isPalindrome {
    static int x = 31213;
    public static void main (String[] args){
        int x1 = x;
        int x2 = 0;
        if (x > 0) {
            while (x != 0){
                x2 *= 10;
                x2 += x % 10;
                x /= 10;
            }
            System.out.println(x2==x1);
        } else {
            System.out.println("false");
        }
    }
}
// не забывать смотреть на подсказки идеи