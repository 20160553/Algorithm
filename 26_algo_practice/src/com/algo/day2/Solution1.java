package com.algo.day2;

import java.util.*;
import java.io.*;

/*
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

import java.util.ArrayDeque;
import java.util.Scanner;

public class Solution1 {

    static String[] fourthArr = {"ABAB", "BCBC", "CACA"};
    static String[] thirdArr = {"AAA", "BBB", "CCC"};

    static boolean checkAvailability(String str) {
        int length = str.length();

        if (str.length() >= 3) {
            String subStr = str.substring(length - 3);
            for (String t: thirdArr) {
                if (subStr.equals(t)) {
                    return false;
                }
            }
        }
        if (str.length() >= 4) {
            String subStr = str.substring(length - 4);
            for (String f: fourthArr) {
                if (subStr.equals(f)) {
                    return false;
                }
            }
        }
        return true;
    }

    static String convertToString(ArrayList<Character> arr) {
        StringBuilder sb = new StringBuilder();
        for (char c: arr) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        /*
        * 비밀번호 길이 : N
        *
        * N개 수열 만드는 건 완탐으로도 가능
        *
        * 풀이 핵심은 패턴을 포함 여부를 어떻게 판단할 것인가?
        *
        * 1.하나 추가할 때마다 검사.
        * 2. 예외 케이스일 경우 가지치기
        *
        * 가지치기로도 충분할 것 같은데?
        * */

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Deque<ArrayList<Character>> q = new LinkedList<>();
        q.add(new ArrayList<>());

        while(!q.isEmpty()) {
            ArrayList<Character> pw = q.poll();

            if (pw.size() == n) {
                System.out.println(convertToString(pw));
                return;
            }

            // C
            ArrayList<Character> copy = new ArrayList<>(pw);
            copy.add('C');
            if (checkAvailability(convertToString(copy))) {
                q.addFirst(copy);
            }

            // B
            copy = new ArrayList<>(pw);
            copy.add('B');
            if (checkAvailability(convertToString(copy))) {
                q.addFirst(copy);
            }

            // A
            copy = new ArrayList<>(pw);
            copy.add('A');
            if (checkAvailability(convertToString(copy))) {
                q.addFirst(copy);
            }
        }
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
 * 사용자 풀이 대비 개선점:
 * - checkAvailability(pw) 버그 수정: 문자 추가 전 pw가 아닌 추가 후 copy를 검사해야 함
 * - ArrayList<Character> + convertToString → StringBuilder (불필요한 O(N) 복사 제거)
 *
 * 시간복잡도: O(3^N) 최악, 가지치기로 실제 탐색 공간 대폭 감소
 * 공간복잡도: O(N) 재귀 스택 + StringBuilder
 */
class Solution1Answer {
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

/*
 * 모범 답안 2: 반복문 DFS (명시적 스택)
 *
 * 핵심 아이디어:
 * 1. 재귀 대신 Deque를 스택으로 사용 → 콜 스택 오버플로우 회피
 * 2. 사전순 최소 보장: 스택은 LIFO이므로 C → B → A 순으로 push하면
 *    A가 top에 와서 먼저 pop → A 경로를 가장 먼저 끝까지 탐색
 *    (사용자 원본 풀이의 addFirst(C,B,A) 발상과 동일)
 * 3. 길이 N에 도달한 첫 문자열이 곧 사전순 최솟값
 *
 * 재귀 버전(Solution1Answer) 대비 차이:
 * - 상태(부분 문자열)를 스택에 독립 보관 → 백트래킹 불필요
 * - 트레이드오프: 상태마다 문자열 복사 비용 O(길이) 발생
 *   (StringBuilder 공유 백트래킹보다 단순하지만 메모리/복사 더 씀)
 *
 * 시간복잡도: O(3^N) 최악, 가지치기로 실제 탐색 공간 대폭 감소
 * 공간복잡도: O(3^N) 최악 (스택에 동시 보관되는 부분 문자열들)
 */
class Solution1AnswerIterative {

    static boolean isValid(String s) {
        int len = s.length();
        if (len >= 3) {
            String t = s.substring(len - 3);
            if (t.equals("AAA") || t.equals("BBB") || t.equals("CCC")) return false;
        }
        if (len >= 4) {
            String t = s.substring(len - 4);
            if (t.equals("ABAB") || t.equals("BCBC") || t.equals("CACA")) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Deque<String> stack = new ArrayDeque<>();
        stack.push("");

        while (!stack.isEmpty()) {
            String cur = stack.pop();

            if (cur.length() == n) {
                System.out.println(cur);
                return;
            }

            // C, B, A 순 push → A가 top → 사전순 우선 탐색
            for (char c : new char[]{'C', 'B', 'A'}) {
                String next = cur + c;
                if (isValid(next)) stack.push(next);
            }
        }

        System.out.println(-1);
    }
}
