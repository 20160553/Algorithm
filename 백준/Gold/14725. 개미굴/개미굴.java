import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static class Node {
        int depth;
        HashMap<String, Node> childs = new HashMap<>();

        public Node() {
            this.depth = 0;
        }
        
        private Node(int depth) {
            this.depth = depth;
        }

        public void addChild(LinkedList<String> list) {
            if (list.isEmpty()) return;
            String nowLayer = list.poll();
            if (!childs.containsKey(nowLayer)) {
                childs.put(nowLayer, new Node(depth + 1));
            }
            childs.get(nowLayer).addChild(list);
        }

        public void showTree() {
            List<String> childKeyList = childs.keySet().stream().sorted().collect(Collectors.toList());

            String str = "";
            for (int i = 0; i < this.depth; i++) {
                str += "--";
            }
            for (String childKey : childKeyList){
                System.out.println(str + childKey);
                childs.get(childKey).showTree();
            }
            
        }
        
    }

    public static void main(String[] args) throws IOException {
        /*
        * 트리 사용
        * Map 사용
        * */

        Node tree = new Node();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int layerCnt = Integer.parseInt(st.nextToken());

        for (int i = 0; i < layerCnt; i++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            LinkedList<String> detectedLayers = new LinkedList<>();
            for (int j = 0; j < n; j++) {
                detectedLayers.add(st.nextToken());
            }
            tree.addChild(detectedLayers);
        }
        tree.showTree();
    }
}