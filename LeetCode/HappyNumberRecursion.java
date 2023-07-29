/*   202. Happy Number
 *
 *   Write an algorithm to determine if a number n is happy.
 *
 *   A happy number is a number defined by the following process:
 *
 *   Starting with any positive integer, replace the number by the sum of the squares of its digits.
 *   Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 *   Those numbers for which this process ends in 1 are happy.
 *
 *   Return true if n is a happy number, and false if not.
 *
 *   Example 1:
 *     Input: n = 19
 *     Output: true
 *     Explanation:
 *         1^2 + 9^2 = 82
 *         8^2 + 2^2 = 68
 *         6^2 + 8^2 = 100
 *         1^2 + 0^2 + 0^2 = 1
 *
 *
 *  Example 2:
 *      Input: n = 2
 *      Output: false
 *
 *  Constraints:
 *      1 <= n <= 2^31 - 1
 *
 */

// Очень плохая идея — записать в файл все счастливые числа в пределах 0...Integer.MAX_VALUE.
// Когда файл перевалил за 1 Gb эксперимент было решено остановить и просто найти
// колличество таких чисел. Count happy numbers: 312337045

package LeetCode;

public class HappyNumberRecursion {
    public static void main(String[] args) {
        System.out.println(isHappy(19));
        System.out.println(isHappy(2));
    }

    public static boolean isHappy(int n) {
        int inputNumber = n;
        int cycleMarker = n;

        return stepOfRecursion(inputNumber, cycleMarker);
    }

    public static boolean stepOfRecursion(Integer lastNumberOfStep, Integer fasterNumberOfStep){ // обертки передают копию ссылки, а не копию объекта в памяти
        lastNumberOfStep = sumQrtOfDigits(lastNumberOfStep);
        fasterNumberOfStep = sumQrtOfDigits(sumQrtOfDigits(fasterNumberOfStep));

        if (lastNumberOfStep == 1 || fasterNumberOfStep == 1) {
            return true;
        } else if (lastNumberOfStep >= Integer.MAX_VALUE || lastNumberOfStep == fasterNumberOfStep) {
            return false;
        }

        return stepOfRecursion(lastNumberOfStep, fasterNumberOfStep);
    }

    private static int[] splitNumberToDigits(int number) {
        int[] arrayOfDigits = new int[10];

        int i = 0;
        while (number > 0) {
            arrayOfDigits[i] = number % 10;
            number = number / 10;
            i++;
        }

        return arrayOfDigits;
    }

    private static int sumQrtOfDigits(int num) {
        int sumOfQrt = 0;

        for (int x : splitNumberToDigits(num)) {
            sumOfQrt += x * x;
        }

        return sumOfQrt;
    }
}

