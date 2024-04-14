import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static class Node {
        int groupId, nodePosition;

        public Node(int groupId, int nodePosition) {
            this.groupId = groupId;
            this.nodePosition = nodePosition;
        }
    }

    public static void main(String[] args) throws IOException {

        /*
         * 시간제한 : 3초
         * 학생 수 : 10만개
         *
         * 텀 프로젝트 속하지 않은 사람 수를 출력하라.
         *
         * 1. 자기 자신을 선택할 수 있음.
         * 2. 그룹의 끝은 처음으로 돌아가야함
         *
         * ==============
         * 완탐 : N ^ 2 => 10 ^ 10
         *
         * 흠...? 이건 Union - Find는 아닌 것 같은데?
         *
         *
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int T, n;

        T = Integer.parseInt(in.readLine());

        for (int t = 0; t < T; t++) {
            n = Integer.parseInt(in.readLine());
            int answer = n;

            st = new StringTokenizer(in.readLine());
            int[] wantStudents = new int[n + 1];
            Node[] v = new Node[n + 1];
            for (int i = 1; i <= n; i++) {
                wantStudents[i] = Integer.parseInt(st.nextToken());
                v[i] = new Node(0, 0);
            }

            int groupNum = 0;
            for (int i = 1; i < wantStudents.length; i++) {
                if (v[i].groupId != 0) continue;
                groupNum++;
                int currentGroupNodeNum = 0;
                int next = wantStudents[i];
                while (v[next].groupId == 0) {
                    int current = next;
                    v[current].groupId = groupNum;
                    v[current].nodePosition = ++currentGroupNodeNum;
                    next = wantStudents[current];
                }
                if (v[next].groupId == groupNum) {
                    answer -= currentGroupNodeNum - v[next].nodePosition + 1;
                }
            }

            sb.append(answer + "\n");
        }
        System.out.println(sb);
    }
}