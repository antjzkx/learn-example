package com.company.homework.utils;

import java.util.*;

public class MapSort {
    // 排序 By Value 从大到小
    public static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        if (map == null) {
            return null;
        }

        List list = new LinkedList<>(map.entrySet());
        
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                
                // Map.Entry 为 Map 声明的一个接口，此接口为泛型，定义为 Entry<K,V>，
                // 他表示 Map 中的一个实体(key-value) 有 getValue() 和 getKey() 方法。
                Comparable sort1 = (Comparable) ((Map.Entry) o1).getValue();
                Comparable sort2 = (Comparable) ((Map.Entry) o2).getValue();
                return sort2.compareTo(sort1);
            }
        });

        Map result = new LinkedHashMap();
        
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        
        return result;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        map.put("test", 3);
        map.put("hcy", 1);
        map.put("put", 2);

        map = sortByValue(map);

        for (String key: map.keySet()) {
            System.out.println(key + " ==> " + map.get(key));
        }
    }
}
