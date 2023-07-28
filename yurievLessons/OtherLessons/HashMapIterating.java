package yurievLessons.OtherLessons;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapIterating {

    public static void main(String[] args) {
        Map<Integer, String> myMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            myMap.put(i, "This is value of map: " + i);
        }

        for (Map.Entry<Integer, String> entry: myMap.entrySet()) {
            System.out.println(entry);
        }

        for (Integer index : myMap.keySet()){
            System.out.println(myMap.get(index));
        }

        Iterator<Map.Entry<Integer,String>> iter = myMap.entrySet().iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }

}
