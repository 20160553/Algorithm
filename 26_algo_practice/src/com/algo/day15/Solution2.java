package com.algo.day15;
/*
Day1 S2 복습
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
 */

import java.io.*;
import java.util.*;

public class Solution2 {
    public static void main(String[] args) throws IOException {
        /*
        * 중요 조건 : 단방향 그래프
        *
        * 같은 재료를 반복해서 사용안하고 만들 수 있는 요리법의 최대 길이를 구하는 것.
        * 즉 같은 재료를 여러 번 써도 되지만, 자체가 모순임.
        *
        * 따라서 애초에 최적해를 못푸는 문제..
        *
        * But 시도라도 하면
        *
        * 1. 순방향 DFS
        * 2. 위상정렬 응용 역방향 DFS
        *
        * */
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

/*
 * 추가 답안 (DAG 한정): 위상정렬 최장 경로 DP
 *
 * ※ 중요 전제:
 *   위 Solution2Answer(SCC 응축)는 "SCC 하나를 전부 순회 가능"이라고 가정하는데,
 *   이는 SCC 내부 해밀턴 경로 존재를 가정하는 것과 같아 일반적으로 성립하지 않는다.
 *   사이클이 있는 일반 방향 그래프의 최장 단순 경로는 NP-hard이므로,
 *   N=10^5에서 다항 시간 최적해는 존재하지 않는다(SCC 답안은 과대추정될 수 있음).
 *
 *   그러나 **사이클이 없는 DAG로 한정**하면 모든 경로가 자동으로 단순 경로가 되어,
 *   최장 경로가 위상정렬 DP로 O(N+M)에 "정확히" 풀린다. 아래가 그 정답 풀이다.
 *
 * 핵심 아이디어:
 *   dp[v] = v에서 시작하는 최장 요리 순서 길이(노드 수)
 *         = 1 + max(dp[w])  (v→w 간선), 나가는 간선 없으면 1
 *   위상정렬 역순(sink→source)으로 채우면 dp[w]가 항상 먼저 계산돼 있다.
 *   topo.size() < n 이면 사이클이 남은 것 → 이 풀이는 성립하지 않는다.
 *
 * 시간복잡도: O(N+M)
 * 공간복잡도: O(N+M)
 */
class Solution2AnswerDAG {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> g = new ArrayList<>();
        int[] indeg = new int[n + 1];
        for (int i = 0; i <= n; i++) g.add(new ArrayList<>());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            g.get(a).add(b);
            indeg[b]++;
        }

        // Kahn 위상정렬
        Deque<Integer> q = new ArrayDeque<>();
        List<Integer> topo = new ArrayList<>();
        for (int i = 1; i <= n; i++) if (indeg[i] == 0) q.push(i);
        while (!q.isEmpty()) {
            int u = q.pop();
            topo.add(u);
            for (int v : g.get(u)) if (--indeg[v] == 0) q.push(v);
        }
        // topo.size() < n 이면 사이클 존재 → DAG 전제 위반

        int[] dp = new int[n + 1];
        int ans = 0;
        // 역위상 순서로 DP
        for (int i = topo.size() - 1; i >= 0; i--) {
            int u = topo.get(i);
            int best = 0;
            for (int v : g.get(u)) best = Math.max(best, dp[v]);
            dp[u] = 1 + best;
            ans = Math.max(ans, dp[u]);
        }
        System.out.println(ans);
    }
}
