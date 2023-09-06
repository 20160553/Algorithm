import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static class FastIn {
        BufferedReader br = null;
        StringTokenizer st = null;

        public FastIn() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            if (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
    
	static int find(int a) {
		if (ports[a] == a) return a;
		return ports[a] = find(ports[a]);
	}
	
	static boolean doDorking(int a) {
		a = find(a);
		if (a == 0) {
			return false;
		}
		ans++;
		ports[a]--;
		return true;
	}
	
	static int g, p, ans = 0;
	static int[] ports;
	public static void main(String[] args) {

		FastIn sc = new FastIn();
		g = sc.nextInt();
		p = sc.nextInt();
		
		ports = new int[g+1];
		for (int i = 1; i <= g; i++)
			ports[i] = i;
		//전략 1. 자기 자신이랑 가까운 거
		//전략 2. 최소 경우 
		mainRoop: for (int i = 0; i < p; i++) {
			int pId = sc.nextInt();
			if(!doDorking(pId)) break;
		}
		System.out.println(ans);
	}

}