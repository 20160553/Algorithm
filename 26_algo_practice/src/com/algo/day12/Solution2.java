package com.algo.day12;

/*
최소 스패닝 트리
시간 제한	메모리 제한
1 초	256 MB
문제

그래프가 주어졌을 때, 그 그래프의 최소 스패닝 트리를 구하는 프로그램을 작성하시오.

최소 스패닝 트리는, 주어진 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 그 가중치의 합이
최소인 트리를 말한다.

입력

첫째 줄에 정점의 개수 V(1 ≤ V ≤ 10,000)와 간선의 개수 E(1 ≤ E ≤ 100,000)가 주어진다.
다음 E개의 줄에는 각 간선에 대한 정보를 나타내는 세 정수 A, B, C가 주어진다.
이는 A번 정점과 B번 정점이 가중치 C인 간선으로 연결되어 있다는 의미이다.
C는 음수일 수도 있으며, 그 절댓값이 1,000,000을 넘지 않는다.

그래프의 정점은 1번부터 V번까지 번호가 매겨져 있고, 임의의 두 정점 사이에 경로가 있다.
최소 스패닝 트리의 가중치가 -2,147,483,648보다 크거나 같고, 2,147,483,647보다 작거나 같은
데이터만 입력으로 주어진다.

출력

첫째 줄에 최소 스패닝 트리의 가중치를 출력한다.

예제 입력 1
3 3
1 2 1
2 3 2
1 3 3
예제 출력 1
3
 */

import java.util.*;
import java.io.*;

public class Solution2 {

    static int find(int a, int[] p) {
        if (a == p[a]) return a;
        return p[a] = find(p[a], p);
    }
    // 유니온 수행 시 true, 아닐 시 false
    static boolean union(int a, int b, int[] p) {
        a = find(a, p);
        b = find(b, p);
        if (a == b) return false;
        p[b] = a;
        return true;
    }

    public static void main(String[] args) throws IOException {
        /*
        * 최소 스패닝 트리
        *
        * 1. 양 방향 그래프
        * 2. 모든 정점 연결하는 그래프
        *
        * 알고리즘
        * 1. 비용 순으로 간선 정렬
        * 2. 비용 적은 순번부터 간선 선택
        * 3. 사이클 발생 시 스킵 (Union-find 활용, 부모 동일 여부 판단)
        * */

        int v, e;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        List<int[]> edges = new ArrayList<>();
        int[] p = new int[v+1];
        long answer = 0L;

        for (int i = 0; i < v; i++) {
            p[i+1] = i + 1;
        }
        for (int i = 0; i < e; i++) {
            int[] edge = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            edges.add(edge);
        }

        Collections.sort(edges, Comparator.comparingInt(o -> o[2]));

        for (int[] edge: edges) {
            if (union(edge[0], edge[1], p)) {
                answer += edge[2];
            }
        }
        System.out.println(answer);
    }
}

/*
 * 모범답안: 크루스칼 (Union-Find + Sorting)
 *
 * 핵심 아이디어:
 *   - MST: 간선을 가중치 오름차순으로 정렬한 뒤, 싼 간선부터 차례로 보며
 *     "두 끝점이 아직 다른 집합이면" 채택(합치고 비용 누적)한다.
 *   - 사이클 판별 = 두 정점이 이미 같은 집합에 속하는지 → 유니온 파인드(Disjoint Set)로 O(α(V)).
 *   - 정점이 V개일 때 간선을 V-1개 채택하면 트리가 완성되므로 조기 종료 가능.
 *
 * 사용자 풀이 대비 개선점:
 *   - 크루스칼 선택과 간선 정렬까지 방향은 정확. 빠졌던 "사이클 판별"을
 *     경로 압축 + union by rank 유니온 파인드로 채워 완성.
 *   - 가중치 합이 int 범위로 보장되지만 누적 과정 안전을 위해 long 사용.
 *
 * 시간복잡도: O(E log E)  — 간선 정렬이 지배
 * 공간복잡도: O(V + E)
 */
class Solution2Answer {
    static int[] parent;
    static int[] rank_;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        int[][] edges = new int[e][3];
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            edges[i][0] = Integer.parseInt(st.nextToken());
            edges[i][1] = Integer.parseInt(st.nextToken());
            edges[i][2] = Integer.parseInt(st.nextToken());
        }

        // 가중치 오름차순 정렬
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);

        parent = new int[v + 1];
        rank_ = new int[v + 1];
        for (int i = 1; i <= v; i++) parent[i] = i;

        long total = 0;
        int used = 0;
        for (int[] edge : edges) {
            if (union(edge[0], edge[1])) { // 다른 집합이면 합치고 채택
                total += edge[2];
                if (++used == v - 1) break; // 트리 완성 → 조기 종료
            }
        }

        System.out.println(total);
    }

    // 경로 압축
    static int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    // 이미 같은 집합이면 false(=사이클), 합쳤으면 true
    static boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;

        if (rank_[ra] < rank_[rb]) {
            parent[ra] = rb;
        } else if (rank_[ra] > rank_[rb]) {
            parent[rb] = ra;
        } else {
            parent[rb] = ra;
            rank_[ra]++;
        }
        return true;
    }
}
