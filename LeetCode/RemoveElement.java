/*
 *Given an integer array <nums> and an integer <val>, remove all occurrences of <val> in <nums> _in-place_.
 * The order of the elements may be changed. Then return the number of elements in <nums> which are not equal to <val>.
 *
 * Consider the number of elements in <nums> which are not equal to <val> be <k>, to get accepted, you need
 * to do the following things:
 *        Change the array <nums> such that the first <k> elements of <nums> contain the elements which are
 *               not equal to <val>. The remaining elements of <nums> are not important as well as the size of <nums>.
 *        Return <k>.
 *
 * Example 1:
 *      Input: nums = [3,2,2,3], val = 3
 *      Output: 2, nums = [2,2,_,_]
 *      Explanation: Your function should return k = 2, with the first two elements of nums being 2.
 *                   It does not matter what you leave beyond the returned k (hence they are underscores).
 *
 * Example 2:
 *      Input: nums = [0,1,2,2,3,0,4,2], val = 2
 *      Output: 5, nums = [0,1,4,0,3,_,_,_]
 *      Explanation: Your function should return k = 5, with the first five elements of
 *                   nums containing 0, 0, 1, 3, and 4.
 *                   Note that the five elements can be returned in any order.
 *                   It does not matter what you leave beyond the returned k (hence they are underscores).
 *
 * Constraints:
 *   0 <= nums.length <= 100
 *   0 <= nums[i] <= 50
 *   0 <= val <= 100
 */

package LeetCode;

public class RemoveElement {

    public static void main(String[] args) {
        System.out.println("Leet Code task #27: \"Remove element\".\n");

        int[] nums1 = new int[]{3, 2, 2, 3};
        int[] nums2 = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        removeElement(nums1, 3);
        removeElement(nums2, 2);
    }

    public static int removeElement(int[] nums, int val) {
        int k = nums.length;
        int buffer;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                nums[i] = -1;
                k--;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            buffer = nums[i];
            if(nums[i] < 0){
                for (int j = i; j < nums.length; j++) {
                    if(nums[j] >= 0){
                        nums[i] = nums[j];
                        nums[j] = buffer;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < k; i++) {
            System.out.println(nums[i]);
        }
        System.out.println("k " + k);

        return k;
    }
}
