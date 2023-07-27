import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int[][] map;
	static boolean[][] v;
	static int[] dy = new int[] {
			-1, 0, 1, 0
	};
	static int[] dx = new int[] {
			0, 1, 0, -1
	};
	static Queue<int []> tQ = new LinkedList<>();
	static void initOutAir(int n, int m) {
		//녹는 치즈 체크
		Queue<int []> mQ = new LinkedList<>();  
		v = new boolean[n][m];
		tQ.add(new int[] {0, 0});
		v[0][0] = true;
		
		int count = 0;
		
		while(!tQ.isEmpty()) {
			count++;
			//밖 공기 탐색
			while(!tQ.isEmpty()) {
				int[] c = tQ.poll();
				int cy = c[0];
				int cx = c[1];
				
				for (int i = 0; i < dx.length; i++) {
					int ty = cy + dy[i];
					int tx = cx + dx[i];

					//배열 범위 밖인 경우
					if (ty < 0 || tx < 0 || ty >= n || tx >= m)continue;
					//방문할 예정인 경우
					if (v[ty][tx]) continue;
					//바깥 공기인 경우
					if (map[ty][tx] == -1) {
						v[ty][tx] = true;
						tQ.add(new int[] {ty, tx});
					} else { // 치즈인 경우 접촉하는 바깥 공기 카운트
						if (++map[ty][tx] == 2) mQ.add(new int[] {ty, tx});
					}
				}
			}
			while(!mQ.isEmpty()) {
				int[] c = mQ.poll();
				int cy = c[0];
				int cx = c[1];
				v[cy][cx] = true;
				map[cy][cx] = -1;
				tQ.add(new int[] {cy, cx});
			}
		}
		System.out.println(count -1);
	}
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken()), m = Integer.parseInt(st.nextToken());
		map = new int[n][m];
		//맵 초기화
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				int isCheese = Integer.parseInt(st.nextToken());
				map[i][j] = isCheese - 1;
			}
		}
		
		initOutAir(n, m);
	}

}