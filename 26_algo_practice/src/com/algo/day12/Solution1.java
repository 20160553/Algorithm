package com.algo.day12;

/*
최단경로
시간 제한	메모리 제한
1 초	256 MB
문제

방향그래프가 주어지면 주어진 시작점에서 다른 모든 정점으로의 최단 경로를 구하는 프로그램을 작성하시오.
단, 모든 간선의 가중치는 10 이하의 자연수이다.

입력

첫째 줄에 정점의 개수 V와 간선의 개수 E가 주어진다. (1 ≤ V ≤ 20,000, 1 ≤ E ≤ 300,000)
모든 정점에는 1부터 V까지 번호가 매겨져 있다고 가정한다.
둘째 줄에는 시작 정점의 번호 K(1 ≤ K ≤ V)가 주어진다.
셋째 줄부터 E개의 줄에 걸쳐 각 간선을 나타내는 세 개의 정수 (u, v, w)가 순서대로 주어진다.
이는 u에서 v로 가는 가중치 w인 간선이 존재한다는 뜻이다. u와 v는 서로 다르며 w는 10 이하의 자연수이다.
서로 다른 두 정점 사이에 여러 개의 간선이 존재할 수도 있음에 유의한다.

출력

첫째 줄부터 V개의 줄에 걸쳐, i번째 줄에 i번 정점으로의 최단 경로의 경로값을 출력한다.
시작점 자신은 0으로 출력하고, 경로가 존재하지 않는 경우에는 INF를 출력하면 된다.

예제 입력 1
5 6
1
5 1 1
1 2 2
1 3 3
2 3 4
2 4 5
3 4 6
예제 출력 1
0
2
3
7
INF
 */

import java.util.*;
import java.io.*;

public class Solution1 {
    public static void main(String[] args) throws IOException {
        /*
         * 입력
         * V E (V <= 2만, E <= 30만)
         * 시작점
         * S, E, Cost (e번)
         *
         * 한 지점 -> 다른 모든 정점까지의 최단거리
         * => 다익스트라 알고리즘
         *
         * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int v, e;
        int start;

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(br.readLine());

        int[][] costs = new int[v + 1][v + 1];
        int INF = Integer.MAX_VALUE / 2;

        ArrayList<ArrayList<int[]>> roads = new ArrayList<>();
        for (int i = 0; i <= v; i++) {
            roads.add(new ArrayList<>());
            for (int j = 0; j <= v; j++) {
                costs[i][j] = INF;
                if (i == j) costs[i][j] = 0;
            }
        }

        for (int i = 0; i < e; i++) {
            int[] road = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            roads.get(road[0]).add(new int[]{road[1], road[2]});
            costs[road[0]][road[1]] = Math.min(costs[road[0]][road[1]], road[2]);
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.add(new int[] {start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int p = current[0];
            int curCost = current[1];

            for (int mid = 1; mid <= v; mid++) {
                if (costs[p][mid] >= INF) continue;
                for (int dest = 1; dest <= v; dest++) {
                    if (costs[mid][dest] >= INF) continue;
                    int nextCost = costs[p][mid] + costs[mid][dest];
                    if (nextCost >= costs[p][dest]) continue;
                    pq.add(new int[] {mid, curCost + costs[p][mid]});
                    costs[p][dest] = Math.min(costs[p][dest], nextCost);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= v; i++) {
            String str = "INF";
            if (costs[start][i] == INF)
                sb.append(str + "\n");
            else
                sb.append(costs[start][i]+ "\n");
        }

        System.out.println(sb);

    }
}

class Solution1Answer {
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        int start = Integer.parseInt(br.readLine().trim());

        List<int[]>[] graph = new List[v + 1];
        for (int i = 1; i <= v; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph[u].add(new int[]{w, cost}); // {도착정점, 비용}
        }

        int[] dist = new int[v + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;

        // {정점, 시작점으로부터의 거리} — 거리 기준 최소 힙
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0];
            int d = cur[1];

            if (d > dist[node]) continue; // 낡은(이미 더 짧은 경로로 확정된) 항목

            for (int[] next : graph[node]) {
                int to = next[0];
                int nd = d + next[1];
                if (nd < dist[to]) {
                    dist[to] = nd;
                    pq.offer(new int[]{to, nd});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= v; i++) {
            sb.append(dist[i] == INF ? "INF" : dist[i]).append('\n');
        }
        System.out.print(sb);
    }
}
