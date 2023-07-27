/* 58. Длина последнего слова.
 *
 * Дана строка, состоящая из нескольких слов или предложений. Необходимо найти длину наибольшей
 * подстроки в конце между пробелами.
 *
 * Ограничения:
 *     - строка обязательно содержит хотябы одно слово
 *     - длина строки не более 10000 символов
 *     - строка содержит только английские буквы и пробелы
 *
 * Выводы по итогам: есть вариант еще проще, через s.split(" ") - просто считаем длину последнего слова.
 */

package LeetCode;

public class LengthOfLastWord {

    public static void main(String[] args) {
        //This is test's
        System.out.println(lengthOfLastWord("   fly me   to   the moon  "));
        System.out.println(lengthOfLastWord("a"));
        System.out.println(lengthOfLastWord("    day"));
    }

    public static int lengthOfLastWord(String s) {
        int lengthOfLastWord = 0;
        String cleanS = s.trim();
        int i = cleanS.length() - 1;
        while(i >= 0 && cleanS.charAt(i) != ' ') {
            lengthOfLastWord++;
            i--;
        }

        return lengthOfLastWord;
    }
}
