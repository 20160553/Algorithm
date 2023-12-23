import java.util.*;

class SegmentTree {
    int start, end;
    long played_number_sum;
    SegmentTree left = null, right = null;
    
    public SegmentTree(int start, int end, int[] played_numbers) {
        this.start = start;
        this.end = end;
        if (start == end) {
            this.played_number_sum = played_numbers[start];
        } else {
            int middle = (start + end) / 2;
            this.left = new SegmentTree(start, middle, played_numbers);
            this.right = new SegmentTree(middle + 1, end, played_numbers);
            this.played_number_sum = this.left.played_number_sum + this.right.played_number_sum;
        }
    }
    
    public long getSum(int start, int end) {
        if (this.end < start || end < this.start)
            return 0;
        if (start <= this.start && this.end <= end) {
            return this.played_number_sum;
        }
        return this.left.getSum(start, end) + this.right.getSum(start, end);
    }
}

class Solution {
    
    public int convertTimeToInt(String time) {
        int[] times = Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();
        return (times[0] * 60 + times[1]) * 60 + times[2];
    }
    
    public String convertTimeToString(int time) {
        int hour, minute, second;
        String convertedTime = "";
        
        second = time % 60;
        time /= 60;
        minute = time % 60;
        time /= 60;
        hour = time;
        
        if (hour < 10)
            convertedTime += "0";
        convertedTime += hour + ":";
        if (minute < 10)
            convertedTime += "0";
        convertedTime += minute + ":";
        if (second < 10)
            convertedTime += "0";
        convertedTime += second;
        return convertedTime;
    }
    
    public String solution(String play_time, String adv_time, String[] logs) {
        
        /*
        정렬 두 번?
        최대 시간 : 100시간 - 1 : 359_999초
        
        풀이 방식 : 누적합인가? -> 세그먼트 트리
        
        */
        
        long max = 0;
        int answer_int = 0;
        String answer = "";
        
        int converted_play_time = convertTimeToInt(play_time);
        int converted_adv_time = convertTimeToInt(adv_time);
        int[] played_numbers = new int[converted_play_time];
        
        for (String log: logs) {
            String[] times = log.split("-");
            int start, end;
            start = convertTimeToInt(times[0]);
            end = convertTimeToInt(times[1]);
            
            for (int i = start; i < end; i++) {
                played_numbers[i]++;
            }
        }
        
        SegmentTree segmentTree = new SegmentTree(0, converted_play_time - 1, played_numbers);
        
        int max_start_time = converted_play_time - converted_adv_time;
        for (int i = 0; i <= max_start_time; i++) {
            long current = segmentTree.getSum(i, i + converted_adv_time - 1);
            if (max < current) {
                max = current;
                answer_int = i;
            }
        }
        // System.out.print(answer_int);
        answer = convertTimeToString(answer_int);
        return answer;
    }
}