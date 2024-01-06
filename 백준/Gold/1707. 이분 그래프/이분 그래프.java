import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws IOException {

        /*
         * 제한시간 : 2초 -> 2억 연산 이하
         * 테케 : 2 <= K <= 5
         * 정점 수 : 1 <= V <= 2만
         * 간선 수 : 1 <= E <= 20만
         *
         * 브루트포스 : 2 ^ V => 최대 2의 2만승 * K => 불가능
         *
         *
         * */

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int K = Integer.parseInt(in.readLine());
        ArrayList<ArrayList<Integer>> graph;
        StringBuilder answer = new StringBuilder();

        roop1 : for (int k = 0; k < K; k++) {
//            System.out.println("\nTest " + k);
            //그래프 초기화
            int[] graphInfo = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            graph = new ArrayList<>();
            for (int i = 0; i < graphInfo[0]; i++) {
                graph.add(new ArrayList<>());
            }
            for (int i = 0; i < graphInfo[1]; i++) {
                int[] edgeInfo = Arrays.stream(in.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                graph.get(edgeInfo[0] - 1).add(edgeInfo[1]);
                graph.get(edgeInfo[1] - 1).add(edgeInfo[0]);
            }

            //0 -> 미정, 1, 2는 각 그룹
            int[] nodeColors = new int[graph.size()];
            LinkedList<Integer> q = new LinkedList<>();

            for (int node = 0; node < graph.size(); node++) {
                if (nodeColors[node] == 0) {
                    //첫 노드 그룹 1로 지정
                    nodeColors[node] = 1;
                    q.add(node + 1);
                }

                while (!q.isEmpty()) {
                    int currentNodeNum = q.poll();
                    int currentNodeColor = nodeColors[currentNodeNum - 1];
                    int afterChangedNextNodeColor = (currentNodeColor % 2) + 1;

                    for (int next : graph.get(currentNodeNum - 1)) {
                        if (nodeColors[next - 1] == 0) { // 방문 안 했던 노드
                            nodeColors[next - 1] = afterChangedNextNodeColor;
                            q.offer(next);
                        } else if (currentNodeColor == nodeColors[next - 1]) { // 조건 위배 : NO
                            answer.append("NO" + "\n");
                            continue roop1;
                        }
                    }
                }
            }
            answer.append("YES" + "\n");
        }
        System.out.println(answer);
    }
}