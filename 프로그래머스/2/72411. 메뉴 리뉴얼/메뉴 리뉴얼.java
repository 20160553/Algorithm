import java.util.*;

class Solution {
    
    static HashMap<String, Integer> map = new HashMap();
    static int[] cnts;
    static ArrayList<ArrayList<String>> courseList = new ArrayList();
    
    public static void combination(int idx, char[] str, String tempStr, int[] course) {
        if (idx == str.length) {
            return;
        }
        
        combination(idx + 1, str, tempStr, course);
        String nextStr = tempStr + str[idx];
        for (int i = 0; i < course.length; i++) {
            if (course[i] == nextStr.length()) {
                if (!map.containsKey(nextStr)) {
                    map.put(nextStr, 1);
                } else {
                    map.put(nextStr, map.get(nextStr) + 1);
                }
                if (cnts[i] < map.get(nextStr)) {
                    cnts[i] = map.get(nextStr);
                    courseList.get(i).clear();
                }
                if (cnts[i] > 1 && cnts[i] == map.get(nextStr)) {
                    courseList.get(i).add(nextStr);
                }
            }
        }
        combination(idx + 1, str, nextStr, course);
    }
    
    public String[] solution(String[] orders, int[] course) {
        ArrayList<String> answer = new ArrayList();
        
        cnts = new int[course.length];
        for (int i = 0; i < course.length; i++) {
            courseList.add(new ArrayList<String>());
        }
        
        for (String str: orders) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            combination(0, chars, "", course);
        }
        for (ArrayList<String> arr: courseList) {
            answer.addAll(arr);
        }
        
        Collections.sort(answer);
        return answer.toArray(new String[answer.size()]);
    }
}