package LeetCode;

import java.util.Arrays;

public class MajorityElement {
    public static void main(String[] args) {
        int result = majorityElement(new int[]{3,2,3,4,3,2});
        System.out.println(result);
//        expected 12
    }


    public static int majorityElement(int[] nums) {
        Arrays.sort(nums);

        if (nums.length == 1){
            return nums[0];
        } else {
            return nums[nums.length/2];
        }
    }
}
