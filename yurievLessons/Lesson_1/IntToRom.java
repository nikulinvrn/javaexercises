package yurievLessons.Lesson_1;

public class IntToRom {
    public static void main(String []args){
        System.out.println(divider(3278));
    }

    public static String divider(int number){
        String[] t1000 = {"", "M", "MM", "MMM"};
        String[] t100 = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] t10 = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] t1 = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        return t1000[number / 1000] + t100[(number % 1000)/ 100] + t10[(number % 100) / 10] + t1[number % 10];
    }
}
// решение ок, но есть нюанс с потенциальным дебагом, if'ы были бы нагляднее.