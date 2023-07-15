/*
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * An input string is valid if:
 *     Open brackets must be closed by the same type of brackets.
 *     Open brackets must be closed in the correct order.
 *     Every close bracket has a corresponding open bracket of the same type.
 *
 *       // round brackets - ()
 *       // square brackets - []
 *       // curly braces - {}
 *
 *     Example 1:
 *          Input: s = "()"
 *          Output: true
 *
 *     Example 2:
 *          Input: s = "()[]{}"
 *          Output: true
 *
 *     Example 3:
 *          Input: s = "(]"
 *          Output: false
 */

package LeetCode;

import java.util.ArrayList;
import java.util.HashMap;

public class ValidParentheses {
    public static void main(String[] args) {

        System.out.println(testOfValidParentheses());

    }

    private static boolean validParentheses(String s) {
        HashMap<Character, Character> parentheses = new HashMap<>();
        ArrayList<Character> parenthesesStack = new ArrayList<>();
        parentheses.put(')', '(');
        parentheses.put('}', '{');
        parentheses.put(']', '[');
        parentheses.put('>', '<'); // немного отсебятины

        for (int i = 0; i < s.toCharArray().length; i++) {
            if (parentheses.containsValue(s.toCharArray()[i])) {
                parenthesesStack.add(s.toCharArray()[i]);
            }
            if (parentheses.containsKey(s.toCharArray()[i])) {
                if (!parenthesesStack.isEmpty() &&
                        parenthesesStack.get(parenthesesStack.toArray().length - 1)
                                == parentheses.get(s.toCharArray()[i])) {
                    parenthesesStack.remove(parenthesesStack.toArray().length - 1);
                } else {
                    return false;
                }
            }
        }

        return parenthesesStack.isEmpty();
    }

    private static StringBuilder testOfValidParentheses() {
        StringBuilder sb = new StringBuilder("Результаты тестов: \n");
        sb.append("() : ");
        sb.append(validParentheses("()")).append("\n");
        sb.append("()[]{} : ");
        sb.append(validParentheses("()[]{}")).append("\n");
        sb.append("(] : ");
        sb.append(validParentheses("(]")).append("\n");
        sb.append("[(]) : ");
        sb.append(validParentheses("[(])")).append("\n");
        sb.append("[(])> : ");
        sb.append(validParentheses("[(])>")).append("\n");
        sb.append("([{}])> : ");
        sb.append(validParentheses("([{}])>")).append("\n");
        sb.append("<(Текст не помеха[ - {c текстом также работает}])>. : ");
        sb.append(validParentheses("<(Текст не помеха[ - {c текстом также работает}])>.")).append("\n");

        return sb;
    }
}
