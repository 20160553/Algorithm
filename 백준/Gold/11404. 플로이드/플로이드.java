import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main
{
	public static void main(String[] args) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    StringTokenizer st = null;
	    
	    int n = Integer.parseInt(br.readLine());
	    int b_num = Integer.parseInt(br.readLine());
	    int [][] arr = new int[n+1][n+1];
	    for (int i = 1; i < n+1; i++) {
	        for (int j = 1; j < n+1; j++) {
	            //발생할 수 있는 최대 비용 : 천만 일 (버스 한 번 최대 비용 * 도시 수)
                arr[i][j] = 10_000_001;
            }
	    }
	    for (int i = 0; i < b_num; i++) {
	        st = new StringTokenizer(br.readLine());
	        int s = Integer.parseInt(st.nextToken());
	        int e = Integer.parseInt(st.nextToken());
	        int c = Integer.parseInt(st.nextToken());
            arr[s][e] = Math.min(
                arr[s][e]
                , c
            );
	    }
	    
	   // k = 경유위치, i = 출발위치, j = 도착위치
	    for (int k = 1; k <= n; k++) {
	        for (int i = 1; i <= n; i++) {
	            for (int j = 1; j <= n; j++) {
	                if (i == j)
	                    continue;
	                if (arr[i][k] + arr[k][j] < arr[i][j]) {
	                    arr[i][j] = arr[i][k] + arr[k][j];
	                }
	            }
	        }
	    }
		for (int i = 1; i <= n; i++) {
		    for (int j = 1; j <= n; j++) {
		        if (arr[i][j] == 10_000_001)
		            arr[i][j] = 0;
		        System.out.print(arr[i][j] + " ");
		    }
		    System.out.println();
		}
	}
}
