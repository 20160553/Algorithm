package com.algo.day15;

/*
D1 S1 복습
문제: 비밀번호 복구

서버 비밀번호는 길이 N의 문자열이다. 각 문자는 A, B, C 중 하나다.

단, 보안 규칙 때문에 비밀번호에는 다음 패턴이 연속 부분 문자열로 등장하면 안 된다.

AAA
BBB
CCC
ABAB
BCBC
CACA

길이 N이 주어졌을 때, 가능한 비밀번호를 사전순으로 가장 앞서는 것부터 최대 1개 출력하라.
불가능하면 -1을 출력하라.
*/

import java.util.*;

public class Solution3 {
    public static void main(String[] args) {
        /*
        * DFS 응용
        * 1. A, B, C 순으로 사전순이므로 사전순서대로 우선 수행하면서 N 길이 도달하면 바로 출력 후 종료시키면 됨
        *
        * 그 과정에서 조건 안맞는 경우 가지치기 수행
        *
        *
        *
        * */

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Queue<String> q = new LinkedList<>();
        q.offer("");

        while(!q.isEmpty()) {
            String c = q.poll();
            if (c.length() == n) {
                System.out.println(c);
                return;
            }
            for (int i = 0; i < 3; i++) {
                char nc = (char)('A' + i);
                String next = c + nc;
                int len = next.length();
                if (len >= 3) {
                    String t = next.substring(len - 3);
                    if (t.equals("AAA") || t.equals("BBB") || t.equals("CCC")) continue;
                }
                if (len >= 4) {
                    String t = next.substring(len - 4);
                    if (t.equals("ABAB") || t.equals("BCBC") || t.equals("CACA")) continue;
                }
                q.offer(next);
            }
        }
        System.out.println(-1);
    }
}

/*
 * 모범 답안: 백트래킹 (DFS + 가지치기)
 *
 * 핵심 아이디어:
 * 1. A, B, C 순으로 추가하며 DFS → 첫 완성 문자열이 사전순 최소 보장
 * 2. 금지 패턴은 길이 3~4 → 마지막 4글자만 검사하면 충분
 * 3. 금지 패턴 발생 시 즉시 가지치기 (백트래킹)
 *
 * 사용자 풀이(BFS) 대비 개선점:
 * - BFS는 큐에 한 레벨(최악 O(3^N))의 부분 문자열을 동시에 들고 있어 메모리 폭발 위험.
 *   DFS 백트래킹은 StringBuilder 하나를 공유해 O(N) 공간만 사용한다.
 * - 문자열 매번 새로 생성(c + nc) 대신 append/deleteCharAt로 복사 비용 제거.
 *
 * 시간복잡도: O(3^N) 최악, 가지치기로 실제 탐색 공간 대폭 감소
 * 공간복잡도: O(N) 재귀 스택 + StringBuilder
 */
class Solution3Answer {
    static int n;

    static boolean isValid(StringBuilder sb) {
        int len = sb.length();
        if (len >= 3) {
            String t = sb.substring(len - 3);
            if (t.equals("AAA") || t.equals("BBB") || t.equals("CCC")) return false;
        }
        if (len >= 4) {
            String t = sb.substring(len - 4);
            if (t.equals("ABAB") || t.equals("BCBC") || t.equals("CACA")) return false;
        }
        return true;
    }

    static String dfs(StringBuilder sb) {
        if (sb.length() == n) return sb.toString();
        for (char c : new char[]{'A', 'B', 'C'}) {
            sb.append(c);
            if (isValid(sb)) {
                String res = dfs(sb);
                if (res != null) return res;
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        String ans = dfs(new StringBuilder());
        System.out.println(ans != null ? ans : -1);
    }
}
