import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collector;

public class Main {

    public static void main(String[] args) throws IOException {

        //크레인 : n대, 1분에 박스 1개
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n, m, answer = 0, maxCrane = 0;
        Integer[] boxes;
        PriorityQueue<int[]> cranes = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] != o2[1]) return o1[1] - o2[1];
                else return o1[0] - o2[0];
            }
        });

        //Input 받기
        //첫 줄에 n
        n = Integer.valueOf(br.readLine());
        //둘째 줄에 크레인 별 무게 제한
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int w = Integer.valueOf(st.nextToken());
            cranes.add(new int[]{
                    w,
                    0
            });

            maxCrane = Math.max(maxCrane, w);
        }
        //셋째 줄 m
        m = Integer.valueOf(br.readLine());
        boxes = new Integer[m];
        //넷째 줄 박스별 무게
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            boxes[i] = Integer.valueOf(st.nextToken());
        }

        //박스, 크레인 정렬
        Arrays.sort(boxes, Collections.reverseOrder());

        if (boxes[0] > maxCrane) {
            //적재 못하는 게 있을 경우 -1 출력
            System.out.println(-1);
            return;
        }

        ArrayList tempList = new ArrayList<int[]>();
        for (int i = 0; i < m; i++) {
            while (!cranes.isEmpty()) {
                //가장 적재가 적게 된 놈 뽑아옴
                int[] currentCrane = cranes.poll();
                //못 넣는 경우
                if (currentCrane[0] < boxes[i]) {
                    tempList.add(currentCrane);
                } else {
                    currentCrane[1]++;
                    cranes.add(currentCrane);
                    cranes.addAll(tempList);
                    tempList.clear();
                    break;
                }
            }
        }

        while(!cranes.isEmpty()) {
            answer = Math.max(answer, cranes.poll()[1]);
        }

        //결과 출력
        //System.out.println(cranes.peek()[0] + " " + cranes.peek()[1]);
        System.out.println(answer);

    }
}