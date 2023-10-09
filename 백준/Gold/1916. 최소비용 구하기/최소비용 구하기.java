import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	static int n, m;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[][] costs;

	public static void main(String[] args) throws IOException {
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());

		costs = new int[n + 1][n + 1];

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				costs[i][j] = Integer.MAX_VALUE;
			}
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

//			if (costs[start][end] > cost) {
			costs[start][end] = Math.min(costs[start][end], cost);
//				for (int j = 1; j < n; j++) {
//					if (i != j && costs[start][j] != -1) {
//						costs[start][j] = Math.min(costs[start][j], cost + costs[end][j]);
//					}
//				}
//			}
		}

		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		dijkstra(start, end);

		System.out.println(costs[start][end]);
	}

	static void dijkstra(int start, int end) {
		int[] d = new int[costs[0].length + 1];

		PriorityQueue<int[]> q = new PriorityQueue<int []>(new Comparator<int []>() {

			@Override
			public int compare(int [] o1, int []o2) {
				return o1[1] - o2[1];
			}
		});

		for (int i = 1; i <= n; i++) {
			if (i == start)
				continue;
			d[i] = costs[start][i];
			if (d[i] < Integer.MAX_VALUE)
				q.add(new int[] { i, d[i] });
		}

		while (!q.isEmpty()) {
			int[] t = q.poll();
			int idx = t[0];
			int cost = t[1];
			for (int i = 1; i <= n; i++) {
				if (costs[idx][i] == Integer.MAX_VALUE) continue;
				int newCost = cost + costs[idx][i];
				if (d[i] > newCost) {
					d[i] = newCost;
					costs[start][i] = newCost;
					q.add(new int[] {i, d[i]});
				}
			}
		}

	}

}