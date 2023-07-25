package LeetCode;

import java.sql.SQLOutput;
import java.util.Arrays;

public class MergeSortedArray {
    public static void main(String[] args) {

    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        System.arraycopy(nums2, 0, nums1, nums1.length - 1, nums1.length);
        Arrays.sort(nums1);
        System.out.println(nums1);
    }
}
