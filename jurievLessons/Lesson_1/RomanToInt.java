package jurievLessons.Lesson_1;

import java.util.Arrays;
import java.util.Scanner;

public class RomanToInt {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Ввведите римское число и нажмите Enter: ");
        String inputString = scanner.nextLine();
        calculateInt(splitRomanianToInt(inputString));
    }

    public static int[] splitRomanianToInt(String romanNumStr) {
        int[] numBuffer = new int[romanNumStr.length()];
        for (int i = 0; i < numBuffer.length; i++) {
            numBuffer[i] = decoder(romanNumStr.charAt(i));
        }

        return numBuffer;
    }

    public static int decoder(char ch) {
        if (ch == 'I') return 1;
        else if (ch == 'V') return 5;
        else if (ch == 'X') return 10;
        else if (ch == 'L') return 50;
        else if (ch == 'C') return 100;
        else if (ch == 'D') return 500;
        else if (ch == 'M') return 1000;

        return 0; // в идеале throw
    }

    public static void calculateInt(int[] intNumberList) { //  нужно
        int resultNumber = 0;
        for (int i = intNumberList.length - 1; i >= 0; i--) {
            if (i == intNumberList.length - 1) {
                resultNumber += intNumberList[i];
            } else {
                if (intNumberList[i + 1] > intNumberList[i]) {
                    resultNumber -= intNumberList[i];
                } else if (intNumberList[i + 1] <= intNumberList[i]) {
                    resultNumber += intNumberList[i];
                } else {
                    System.out.println("Я не понимаю что происходит. Если бы мы знали, что это такое, но мы не знаем что это такое.. Очень страшно.");
                }
            }
        }
        System.out.println(resultNumber); // можно и ретерн сделать
    }
}