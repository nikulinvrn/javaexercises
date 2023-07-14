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

import java.util.HashMap;

public class ValidParentheses {
    public static void main(String[] args) {
        System.out.println(testLC());
    }

    private static boolean validParentheses(String s) {
        HashMap<Character, Character> parantheses = new HashMap<>();
        char[] inputString = s.toCharArray();
        parantheses.put(')','(');
        parantheses.put('{','}');
        parantheses.put('[',']');

        System.out.println(parantheses);

        return false;
    }

    private static boolean[] testLC() {

            return new boolean[]{validParentheses("()"), validParentheses("()[]{}"), validParentheses("(]")};
    }
}
