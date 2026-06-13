package com.algo.day1;

import java.util.*;
import java.io.*;
/*
* 문제: 냉장고 털이 루트

당신은 퇴근 후 집에 왔다. 냉장고 안에는 여러 재료가 있고, 각 재료는 다른 재료와 조합해서 요리할 수 있다.

요리는 다음 규칙을 따른다.

재료는 1번부터 N번까지 있다.
어떤 재료 A를 사용하면, 그 다음에는 정해진 재료들 중 하나로만 이어서 요리할 수 있다.
즉, 재료 관계는 방향 그래프다.
같은 재료를 여러 번 써도 되지만, 요리 순서 안에서 같은 재료가 다시 등장하면 실패한다.
시작 재료는 아무거나 고를 수 있다.
만들 수 있는 가장 긴 요리 순서의 길이를 구하라.

단, 재료 관계에 사이클이 있을 수 있다.
사이클에 들어가면 같은 재료를 다시 쓰게 되므로 그 경로는 실패 처리해야 한다.

입력
N M
A1 B1
A2 B2
...
AM BM
1 ≤ N ≤ 100,000
0 ≤ M ≤ 200,000
Ai Bi는 Ai 다음에 Bi를 사용할 수 있다는 뜻이다.
출력
가장 긴 성공 요리 순서의 길이
예시
입력
6 7
1 2
2 3
3 4
4 2
2 5
5 6
1 6
출력
4

가능한 순서:

1 → 2 → 5 → 6

2 → 3 → 4 → 2는 같은 재료 2가 다시 나오므로 실패 경로다.
* ㅁ*/
public class Solution2 {

    static int find(int a, int[] p) {
        if (p[a] == a) return a;
        return p[a] = find(p[a], p);
    }

    static void union(int a, int b, int[] p) {
        a = find(a, p);
        b = find(b, p);
        if (a != b) {
            p[a] = b;
        }
    }

    public static void main(String[] args) throws IOException {
        /*
        * 사이클 들어가지 않는 가장 긴 수열 길이
        * 풀이 실패. 시도기간 : 30분
        *
        * N <= 10만 (10^5)
        * M <= 20만 (2*10^5)
        *
        * N*M => 10 ^ 10 완탐은 시간초과?
        *
        * 사이클이면 유니온 파인드인 것 같은데..
        * 방향 그래프라 일반적 유니온 파인드가 아닌가?
        *
        * 일단 내가 해야하는 건?
        *
        * 1. 모든 재료를 시작 점으로 검사 (완탐)
        * 2. 사이클 들어가는 경우 가지치기
        *
        * 위상 정렬 응용인가?
        *
        * 음.. 접근도 못하겠네..
        *
        * */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Integer>> items = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            items.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            items.get(a).add(b);
        }

    }
}

/*
 * 모범 답안 (수정): SCC(Kosaraju) + 응축 DAG + dpEnter/dpStart 이중 DP
 *
 * 기존 풀이의 오류:
 *   dp[c] = sz[c] + max(dp[succ]) 는 SCC 전체 순회 후 탈출이 항상 가능하다고 가정 → 오답(과대 추정)
 *   예시: {1}→{2,3,4}→{5}→{6} = 1+3+1+1 = 6 (실제 불가능한 경로)
 *   1→2 진입 시 2→3→4 순회하면 4→2(방문됨) 막혀서 탈출 불가
 *
 * 핵심 관찰:
 *   SCC 진입 방식에 따라 순회 가능한 노드 수가 달라진다.
 *   ┌─────────────────────────────────────────────────────────────────┐
 *   │ dpStart[c]: SCC c 내부 어디서든 시작 (시작점 자유 선택)          │
 *   │   → 최적 시작점 선택으로 sz[c] 전부 순회 후 탈출 가능           │
 *   │   → dpStart[c] = sz[c] + max(dpEnter[후계 SCC])               │
 *   │                                                                 │
 *   │ dpEnter[c]: 외부 SCC에서 진입 (진입 노드 = 탈출 노드로 고정)    │
 *   │   → 탈출 옵션 A: 진입 노드 1개만 쓰고 즉시 탈출 후 계속        │
 *   │   → 탈출 옵션 B: sz[c] 전부 쓰고 여기서 종료 (terminal)        │
 *   │   → dpEnter[c] = max(sz[c], 1 + max(dpEnter[후계 SCC]))       │
 *   └─────────────────────────────────────────────────────────────────┘
 *
 * 예시 추적 ({1}=A, {2,3,4}=B, {5}=C, {6}=D):
 *   dpEnter[D]=1, dpStart[D]=1
 *   dpEnter[C]=max(1, 1+1)=2,  dpStart[C]=1+1=2
 *   dpEnter[B]=max(3, 1+2)=3,  dpStart[B]=3+2=5  ← 경로: 3→4→2→5→6
 *   dpStart[A]=1+max(3,1)=4
 *   정답 = max(5,4,2,1) = 5
 *
 * ※ 예시 출력 4 역시 오류 가능성 있음 (최장 단순 경로 3→4→2→5→6 = 5노드 누락)
 *
 * 시간복잡도: O(N+M)
 */
class Solution2Answer {
    static int n;
    static List<List<Integer>> g  = new ArrayList<>();
    static List<List<Integer>> rg = new ArrayList<>();
    static boolean[] vis;
    static Deque<Integer> order = new ArrayDeque<>();
    static int[] comp;

    // Step 1: 원본 그래프 DFS, 종료 순서 기록 (반복 DFS - N=10^5 스택 오버플로우 방지)
    static void dfs1(int s) {
        Deque<int[]> stk = new ArrayDeque<>();
        vis[s] = true;
        stk.push(new int[]{s, 0});
        while (!stk.isEmpty()) {
            int[] cur = stk.peek();
            List<Integer> adj = g.get(cur[0]);
            if (cur[1] < adj.size()) {
                int nxt = adj.get(cur[1]++);
                if (!vis[nxt]) { vis[nxt] = true; stk.push(new int[]{nxt, 0}); }
            } else {
                order.push(cur[0]);
                stk.pop();
            }
        }
    }

    // Step 2: 역방향 그래프 DFS, SCC 번호 할당
    static void dfs2(int s, int id, int[] sz) {
        Deque<Integer> stk = new ArrayDeque<>();
        comp[s] = id; sz[id]++;
        stk.push(s);
        while (!stk.isEmpty()) {
            int v = stk.pop();
            for (int nxt : rg.get(v))
                if (comp[nxt] < 0) { comp[nxt] = id; sz[id]++; stk.push(nxt); }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= n; i++) { g.add(new ArrayList<>()); rg.add(new ArrayList<>()); }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
            g.get(a).add(b);
            rg.get(b).add(a);
        }

        // Kosaraju Step 1
        vis = new boolean[n + 1];
        for (int i = 1; i <= n; i++) if (!vis[i]) dfs1(i);

        // Kosaraju Step 2
        comp = new int[n + 1];
        Arrays.fill(comp, -1);
        int[] sz = new int[n + 1];
        int sccCnt = 0;
        while (!order.isEmpty()) {
            int v = order.pop();
            if (comp[v] < 0) dfs2(v, sccCnt++, sz);
        }

        // 응축 DAG 구성
        List<Set<Integer>> dag = new ArrayList<>();
        int[] indeg = new int[sccCnt];
        for (int i = 0; i < sccCnt; i++) dag.add(new HashSet<>());
        for (int u = 1; u <= n; u++)
            for (int v : g.get(u))
                if (comp[u] != comp[v] && dag.get(comp[u]).add(comp[v]))
                    indeg[comp[v]]++;

        // 위상정렬 순서 수집 (Kahn's)
        List<Integer> topo = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < sccCnt; i++) if (indeg[i] == 0) q.offer(i);
        while (!q.isEmpty()) {
            int cur = q.poll();
            topo.add(cur);
            for (int nxt : dag.get(cur)) if (--indeg[nxt] == 0) q.offer(nxt);
        }

        // 역위상 순서로 DP (sink → source)
        int[] dpEnter = new int[sccCnt];
        int[] dpStart = new int[sccCnt];
        for (int i = topo.size() - 1; i >= 0; i--) {
            int cur = topo.get(i);
            int bestSucc = 0;
            for (int nxt : dag.get(cur)) bestSucc = Math.max(bestSucc, dpEnter[nxt]);
            // bestSucc == 0 이면 terminal SCC: dpEnter = sz, dpStart = sz (자동 처리)
            dpEnter[cur] = Math.max(sz[cur], 1 + bestSucc);
            dpStart[cur] = sz[cur] + bestSucc;
        }

        int ans = 0;
        for (int i = 0; i < sccCnt; i++) ans = Math.max(ans, dpStart[i]);
        System.out.println(ans);
    }
}
