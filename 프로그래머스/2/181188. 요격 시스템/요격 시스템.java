import java.util.*;

class Missile implements Comparable<Missile> {
    int start, end;
    
    public Missile(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public int compareTo(Missile m) {
        if (this.end == m.end) {
            return this.start - m.start;
        }
        
        return this.end - m.end;
    }
    
    public String toString() {
        return start + "-" + end;
    }
}

class Solution {
    public int solution(int[][] targets) {
        
        int n = targets.length;
        ArrayList<Missile> missiles = new ArrayList<>();
        
        //초기화
        for (int i = 0; i < n; i++) {
            int[] target = targets[i];
            missiles.add(new Missile(target[0], target[1]));
        }
        Collections.sort(missiles);
        boolean[] destroyed = new boolean[n];
        // System.out.println(missiles);
        // System.out.println(Arrays.toString(destroyed));
        
        // 풀이
        int answer = 0;
        int current = 0;
        while (current < n) {
            //currentMissile
            Missile cM = missiles.get(current);
            boolean status = destroyed[current];
            
            if (status) {
                current++;
                continue;
            }
            // System.out.println(cM);
            destroyed[current] = true;
            answer++;
            
            for (int i = current + 1; i < n; i++) {
                //nextMissile
                Missile nM = missiles.get(i);
                if (nM.start < cM.end) {
                    destroyed[i] = true;
                }
            }
            current++;
        }
        

        return answer;        
    }
}