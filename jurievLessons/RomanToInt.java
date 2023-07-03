package jurievLessons;

import java.util.Arrays;
import java.util.Scanner;

public class RomanToInt {
    static String s;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ввведите римское число и нажмите Enter: ");
        s = scanner.nextLine();

        calculateInt(splitRomanianToInt(s));
    }

    public static int[] splitRomanianToInt(String s){
        String[] symbolBuffer = new String[s.length()];

        for(int i=0; i < s.length(); i++){
            symbolBuffer[i] = s.substring(i,i+1);
            // System.out.println("Символ " + i + " = " + symbolBuffer[i]);
        }

        int[] numBuffer = new int[symbolBuffer.length];
        for(int i=0; i < symbolBuffer.length; i++){
            if (Arrays.asList(symbolBuffer[i]).contains("I")) {numBuffer[i] = 1;}
            else if (Arrays.asList(symbolBuffer[i]).contains("V")) {numBuffer[i] = 5;}
            else if (Arrays.asList(symbolBuffer[i]).contains("X")) {numBuffer[i] = 10;}
            else if (Arrays.asList(symbolBuffer[i]).contains("L")) {numBuffer[i] = 50;}
            else if (Arrays.asList(symbolBuffer[i]).contains("C")) {numBuffer[i] = 100;}
            else if (Arrays.asList(symbolBuffer[i]).contains("D")) {numBuffer[i] = 500;}
            else if (Arrays.asList(symbolBuffer[i]).contains("M")) {numBuffer[i] = 1000;}
            else {
                System.out.println("Введенная строка имеет недопустимые символы");
            }
        }
        return numBuffer;
    }

    public static void calculateInt (int[] intNumberList){
        int resultNumber = 0;
        for(int i = intNumberList.length - 1; i >= 0; i--){
            if (i == intNumberList.length - 1) {
                resultNumber += intNumberList[i];
                continue;
            } else {
                if (intNumberList[i+1] > intNumberList[i]){
                    resultNumber -= intNumberList[i];
                    continue;
                } else if (intNumberList[i+1] <= intNumberList[i]){
                    resultNumber += intNumberList[i];
                    continue;
                } else {
                    System.out.println("Я не понимаю что происходит. Если бы мы знали, что это такое, но мы не знаем что это такое.. Очень страшно.");
                }
            }
        }
        System.out.println(resultNumber); // можно и ретерн сделать
    }
}