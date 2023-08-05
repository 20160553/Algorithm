import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	static class Node {
		int dest, dist;

		public Node(int dest, int dist) {
			this.dest = dest;
			this.dist = dist;
		}

	}

	static int n = 0;
	static ArrayList<Node>[] tree;
	static boolean[] v;
	static int max = 0;
	static int maxIdx = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		tree = new ArrayList[n + 1];
		v = new boolean[n + 1];

		// 트리 구성
		for (int i = 1; i <= n; i++) {
			tree[i] = new ArrayList();
		}

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int idx = Integer.parseInt(st.nextToken());
			while (true) {
				int dest = Integer.parseInt(st.nextToken());
				if (dest == -1)
					break;

				int dist = Integer.parseInt(st.nextToken());
				tree[idx].add(new Node(dest, dist));
			}
		}

		v[1] = true;
		dfs(1, 0);
		v = new boolean[n + 1];
		v[maxIdx] = true;
		dfs(maxIdx, 0);
		System.out.println(max);
	}

	static private void dfs(int idx, int dist) {
		if (max < dist) {
			max = dist;
			maxIdx = idx;
		}
		for (int i = 0; i < tree[idx].size(); i++) {
			Node node = tree[idx].get(i);
			//이미 방문한 경우
			if (v[node.dest]) continue;
			// 방문 안했을 경우
			v[node.dest] = true;
			dfs(node.dest, dist + node.dist);
		}
	}
}