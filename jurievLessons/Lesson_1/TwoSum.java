package jurievLessons.Lesson_1;
//Input: nums = [2,7,11,15], target = 9
//Output: [0,1]
//Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

// Как сделать без хешей сложность O(N)? Пока только O(N^2)...

public class TwoSum {
    public static void main(String[] args){
        int[] nums = {1,7,5,4,3};
        int target = 6;
        int[] result = new int[2];
        result = twoSum(nums, target);
        System.out.println(result[0] + " , " +  result[1]);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for(int i = 0; i < nums.length; i++) {
            for(int j = i; j < nums.length; j++){
                if( i != j ) {
                    if(nums[i]+nums[j] == target){
                        result[0] = i;
                        result[1] = j;
                        return result;
                    }
                }
            }
        }
        System.out.println("Что-то не то. Я не нашел целевую пару.");
        return result;
    }
}
