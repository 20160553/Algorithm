package com.algo.day6;

import java.util.*;
import java.io.*;

/*
문제: 임계 네트워크

난이도 목표: BOJ 골드 1

문제 설명

정점 1부터 N까지 번호가 붙은 무방향 그래프가 있다.
각 간선은 세 값을 가진다.

u, v: 연결된 두 정점
w: 간선의 위험도
c: 간선의 통행 비용

당신은 정점 1에서 정점 N까지 이동하려고 한다.

어떤 정수 X를 정하면, 위험도 w ≤ X인 간선만 사용할 수 있다.
그때 사용할 수 있는 간선들만으로 정점 1에서 정점 N까지 가는 경로 중, 통행 비용 합이 K 이하인 경로가 존재해야 한다.

조건을 만족하는 최소 X를 구하라.
만약 어떤 X를 골라도 조건을 만족할 수 없다면 -1을 출력하라.

입력
N M K
u1 v1 w1 c1
u2 v2 w2 c2
...
uM vM wM cM
제한
2 ≤ N ≤ 100,000
1 ≤ M ≤ 300,000
1 ≤ K ≤ 10^18

1 ≤ ui, vi ≤ N
ui ≠ vi
1 ≤ wi ≤ 10^9
1 ≤ ci ≤ 10^9

그래프는 중복 간선을 가질 수 있다.

출력

조건을 만족하는 최소 위험도 X를 출력한다.
불가능하면 -1을 출력한다.

예제 입력 1
5 6 10
1 2 4 3
2 5 7 4
1 3 2 8
3 5 3 5
2 3 5 1
4 5 1 1
예제 출력 1
7
설명

X = 3이면 1 → 3 → 5 경로를 사용할 수 있지만 비용은 13이다.
X = 7이면 1 → 2 → 5 경로를 사용할 수 있고 비용은 7이다.

따라서 최소 X는 7이다.
 */

public class Solution2 {

    static int n, m;
    static long k;
    private static boolean dijkstra( int start, int end, long x, long k, ArrayList<ArrayList<int[]>> roads) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        long[][] dist = new long[n + 1][n + 1];
        boolean result = false;
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dist[i], -1);
        }
        for (int i = 1; i <= n; i++) {
            for (int[] road: roads.get(i)) {
                if (road[1] < x) {
                    dist[i][road[0]] = road[2];
                }
            }
        }
        dist[1][1] = 0;


        return result;
    }

    public static void main(String[] args) throws IOException {
        /*
        * 양방향 그래프
        *
        * int n, m
        * long k
        * 2 ≤ N ≤ 100,000
        * 1 ≤ M ≤ 300,000
        * 1 ≤ K ≤ 10^18
        *
        * 1 -> N 까지 최소거리 (다익스트라)
        * K 구하기 : 이진탐색
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Long.parseLong(st.nextToken());

        ArrayList<ArrayList<int[]>> roads = new ArrayList<>();

        for (int i = 0; i < n+1; i++) {
            roads.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            roads.get(u).add(new int[] {v, w, cost});
            roads.get(v).add(new int[] {u, w, cost});
        }

        long l = 0;
        long r = (long)1_000_000_000 * (n - 1);

        long answer = -1;

        while(l <= r) {
            long mid = (l + r) / 2;
        }

    }
}

/*
 * 핵심 아이디어:
 *  - X를 키울수록 사용 가능한 간선만 늘어나므로 "비용 ≤ K 경로 존재" 여부는 X에 대해 단조 증가.
 *    → 정답 X는 간선 위험도 중 하나이므로, 서로 다른 w 값들을 정렬해 그 위에서 이분 탐색.
 *  - 각 후보 X마다 w ≤ X 간선만으로 1→N 최소 비용 다익스트라 → K 이하면 feasible.
 *
 * 사용자 풀이 대비 개선점:
 *  - dist[n+1][n+1] (O(N^2) 메모리, N=1e5에서 폭발)을 1차원 dist[n+1]로 교체.
 *  - 이분 탐색 범위를 "비용"이 아니라 "위험도 X(간선 w 집합)"로 잡음 — 탐색 대상 오류 수정.
 *  - 조건은 w ≤ X (사용자는 w < x), 비용·K는 long, INF 안전값 사용.
 *
 * 시간복잡도: O(M log M)(정렬) + O(log D · M log N)  (D = 서로 다른 w 개수)
 * 공간복잡도: O(N + M)
 */
class Solution2Answer {
    static final long INF = Long.MAX_VALUE / 4;
    static int n, m;
    static long k;
    static List<long[]>[] graph;   // graph[u] = {v, w, cost}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Long.parseLong(st.nextToken());

        graph = new List[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        long[] weights = new long[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long w = Long.parseLong(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            graph[u].add(new long[] {v, w, c});
            graph[v].add(new long[] {u, w, c});
            weights[i] = w;
        }

        // 후보 X = 서로 다른 위험도 값 (정렬). 이 위에서 이분 탐색.
        long[] cand = Arrays.stream(weights).distinct().sorted().toArray();

        int lo = 0, hi = cand.length - 1;
        long answer = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (feasible(cand[mid])) {     // w ≤ X 간선만으로 1→N 최소 비용 ≤ K?
                answer = cand[mid];
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        System.out.println(answer);
    }

    static boolean feasible(long x) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        dist[1] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.add(new long[] {1, 0});
        while (!pq.isEmpty()) {
            long[] top = pq.poll();
            int u = (int) top[0];
            long d = top[1];
            if (d > dist[u]) continue;
            if (u == n) return d <= k;     // 최소 비용 경로 도달 즉시 판정
            for (long[] e : graph[u]) {
                if (e[1] > x) continue;    // 위험도 초과 간선 사용 불가
                long nd = d + e[2];
                if (nd < dist[(int) e[0]]) {
                    dist[(int) e[0]] = nd;
                    pq.add(new long[] {e[0], nd});
                }
            }
        }
        return dist[n] <= k;
    }
}
