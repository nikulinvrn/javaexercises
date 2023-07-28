/*
 * 80. Remove duplicates from sorted array II
 *
 * Задача удалить из массива позиции так, чтобы каждая повторялась не более двух раз
 * Сделать это нужно в том же массиве, прибегая только к О(1) дополнительной памяти
 * Порядок элементов должен остаться прежним, элементы соответствующие условиям
 * должны располагаться в первых k элементах массива.
 *
 */

package LeetCode;

public class RemoteDuplicatesFromSortedArrayII {
    public static void main(String[] args) {
        // Write test here
        System.out.println(removeDuplicates(new int[]{1, 1, 1, 2, 2, 3}));
        System.out.println(removeDuplicates(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3}));
//        System.out.println(removeDuplicates(new int[]{}));
//        System.out.println(removeDuplicates(new int[]{}));
    }

    public static int removeDuplicates(int[] nums) {
        int cursor = 1;
        for (int index = 2; index < nums.length; index++){
            if (nums[index] != nums[cursor - 1]){
                nums[++cursor] = nums[index];
            }
        }

        for (int x: nums) {
            System.out.print(" [" + x + "] ");
        }

        return cursor + 1;
    }

}
