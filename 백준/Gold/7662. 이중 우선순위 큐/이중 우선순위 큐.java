import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(st.nextToken());

		TreeMap<Integer, Integer> treeMap = new TreeMap();
		
		for (int test_case = 0; test_case < T; test_case++) {
			treeMap.clear();
			int n = Integer.parseInt(br.readLine());
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(br.readLine());
				String s = st.nextToken();
				int num = Integer.parseInt(st.nextToken());

				if (s.equals("I")) {
					treeMap.put(num, treeMap.getOrDefault(num, 0) + 1);
				} else {
					if (treeMap.size() < 1) continue;
					if (num < 0) {
						int fK = treeMap.firstKey();
						int fV = treeMap.firstEntry().getValue();
						if (fV > 1)
							treeMap.put(fK, fV-1);
						else treeMap.pollFirstEntry();
					} else {
						int lK = treeMap.lastKey();
						int lV = treeMap.lastEntry().getValue();
						if (lV > 1)
							treeMap.put(lK, lV-1);
						else treeMap.pollLastEntry();
					}
				}
			}
			String result = "EMPTY\n";
			switch(treeMap.size()) {
			case 0:
				break;
			case 1:
				result = treeMap.lastEntry().getKey() + " " + treeMap.lastEntry().getKey() + "\n";
				break;
			default:
				result = treeMap.pollLastEntry().getKey() + " " + treeMap.pollFirstEntry().getKey() + "\n";
			}
			sb.append(result);
		}
		System.out.println(sb);
	}

}