import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {

        /*
         * 1억 이하 연산
         *
         * 2 <= N <= 99
         *
         * 7일간 두 팀으로 나눔.
         * 모든 두 원숭이는 한 번은 적팀.
         *
         * i)
         * N <= 7일 때는 팀원 1인 팀 만들고 매일 여기 원소만 바꿔 끼면됨.
         * ii)
         * List<Set>으로 적으로 만났던 애들 저장
         * 맨 앞부터 하나씩 꺼내서 반대팀에 저장
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        ArrayList<HashSet<Integer>> notMeetIds = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            HashSet<Integer> set = new HashSet<>();
            notMeetIds.add(set);
            //나 빼고 다 안 만남
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                set.add(j);
            }
        }

        for (int day = 0; day < 7; day++) {
            //방문 처리
            boolean[] v = new boolean[n];
            //팀
            boolean[] team = new boolean[n];
            //한 팀 당 최소 1명
            team[0] = true;
            //처음부터 끝까지
            for (int i = 0; i < n; i++) {
                LinkedList<Integer> q = new LinkedList<>();
                //이미 방문한 경우 스킵
                if (v[i]) continue;
                v[i] = true;
                q.offer(i);
                while (!q.isEmpty()) {
                    //현재 팀 지정한 원숭이
                    int current = q.poll();
                    HashSet<Integer> set = notMeetIds.get(current);

                    if (set.isEmpty()) continue;
                    int next = -1;
                    for (int element : set) {
                        if (v[element]) continue;
                        next = element;
                        break;
                    }
                    if (next == -1) continue;
                    v[next] = true;
                    team[next] = !team[current];
                    set.remove(next);
                    notMeetIds.get(next).remove(current);
                    q.offer(next);
                }
            }
            for (int i = 0; i < n; i++) {
                String teamString;
                if (team[i])
                    teamString = "A";
                else
                    teamString = "B";
                answer.append(teamString);

                for (int j = 0; j < n; j++) {
                    if (i == j || team[i] == team[j]) continue;
                    if (notMeetIds.get(i).contains(j)) {
                        notMeetIds.get(i).remove(j);
                    }
                }
            }
            if (day != 6) ;
            answer.append("\n");
        }

        System.out.println(answer);
    }
}