import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Main {

    static class Node {
        int id;
        ArrayList<Node> childs = new ArrayList<>();
        boolean activate = true;

        Node(int id) {
            this.id = id;
        }

        public int countLeaf() {
            if (!this.activate) return 0;
            int cnt = 0;
            int activateCnt = 0;
            for (Node node : childs) {
                if (node.activate) activateCnt++;
                cnt += node.countLeaf();
            }
            if (activateCnt == 0) return 1;
            return cnt;
        }
    }

    public static void main(String[] args) throws Exception {

        HashMap<Integer, Node> nodes = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());

        int rootIdx = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            Node current = nodes.computeIfAbsent(i, key -> new Node(key));
            int parentId = Integer.parseInt(st.nextToken());
            if (parentId == -1) {
                rootIdx = i;
                continue;
            }
            Node parent = nodes.computeIfAbsent(parentId, key -> new Node(key));
            parent.childs.add(current);
        }

        int deletedId = Integer.parseInt(br.readLine());
        nodes.get(deletedId).activate = false;

        if (nodes.get(0) == null) {
            System.out.println(0);
        } else
            System.out.println(nodes.get(rootIdx).countLeaf());

    }
}