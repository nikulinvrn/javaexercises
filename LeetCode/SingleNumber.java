package LeetCode;

import java.util.*;

public class SingleNumber {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{4,1,2,1,2}));
    }

    public static int singleNumber(int[] nums) {
        Set<Integer> list = new HashSet<>();
        for (int element : nums) {
            if (!list.contains(element)){
                list.add(element);
            } else {
                list.remove(element);
            }
        }
        return list.iterator().next();
    }
}
