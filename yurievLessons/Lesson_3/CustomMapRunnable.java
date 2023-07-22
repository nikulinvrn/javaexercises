package yurievLessons.Lesson_3;

public class CustomMapRunnable {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void main(String[] args) {
        CustomMap<String, String> map1 = new CustomMap<>();

/*        for (int i = 10; i < 21; i++) {
            map1.put(String.valueOf(getRandomNumber(1000, 1000000)), "Value : " + i % 10);
        }*/
        map1.put("560046","1");
        map1.put("563237","2");
        map1.put("351048","3");
        map1.put("212317","4");
        map1.put("590575","5");
        map1.put("729347","6");
        map1.put("170685","7");
        map1.put("399044","8");
        map1.put("384698","9");
        map1.put("887581","10");


        System.out.println("Я нашел: " + map1.binaryFindByKeyInIndexes("399044"));
/*
        for (CustomMap.Node<String, String> node : map1) {
            System.out.println(node);
        }*/

    }
}
