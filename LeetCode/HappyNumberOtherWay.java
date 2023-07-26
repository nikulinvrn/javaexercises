package LeetCode;

public class HappyNumberOtherWay {
    public static void main(String[] args) {
        System.out.println(isHappy(19));
        System.out.println(isHappy(2));
        System.out.println(isHappy(2342346));
    }

    public static boolean isHappy(int n) {
        int slowResolve = n;
        int fastResolve = n;

        while (slowResolve != 1) {
            slowResolve = resolver(slowResolve);
            fastResolve = resolver(resolver(fastResolve));

            if (slowResolve != 1 && slowResolve == fastResolve) {
                return false;
            }
        }
        return true;
    }

    public static int resolver(int n) {
        int i = 0;
        int sum = 0;
        int[] dec = new int[10];
        while (n != 0 && i < 10) {
            dec[i] = n % 10;
            n /= 10;
            sum += dec[i] * dec[i];
            i++;
        }
        return sum;
    }
}

