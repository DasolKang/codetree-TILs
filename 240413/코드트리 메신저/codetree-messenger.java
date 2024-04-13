import java.io.*;
import java.util.*;

public class Main {
    private static int N, Q;
    private static Node[] nodeInfo;

    private static class Node {
        int number, authority;
        Node parents;
        ArrayList<Node> child;
        boolean wall = false;

        Node() {
            this.child = new ArrayList<>();
        }

        public void setNode(int number, int authority, Node parents) {
            this.number = number;
            this.authority = authority;
            this.parents = parents;
        }

        public void addChild(Node child) {
            this.child.add(child);
        }

        public void changeParent(Node parents) {
            this.parents = parents;
        }

        public void switchWall() {
            this.wall = !this.wall;
        }

        public void changeAuthority(int power) {
            this.authority = power;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        init(br);
        for (int i = 0; i < Q - 1; i++) {
            String[] command = br.readLine().split(" ");
            if (command[0].equals("200")) {
                // 알림망 설정 ON / OFF
                int c = Integer.parseInt(command[1]);
                nodeInfo[c].switchWall();
            } else if (command[0].equals("300")) {
                // 권한 세기 변경
                int c = Integer.parseInt(command[1]);
                int power = Integer.parseInt(command[2]);
                nodeInfo[c].changeAuthority(power);
            } else if (command[0].equals("400")) {
                // 부모 채팅방 교환
                int c1 = Integer.parseInt(command[1]);
                int c2 = Integer.parseInt(command[2]);
                Node c1Parent = nodeInfo[c1].parents;
                c1Parent.child.remove(nodeInfo[c1]);
                c1Parent.addChild(nodeInfo[c2]);
                Node c2Parent = nodeInfo[c2].parents;
                c2Parent.child.remove(nodeInfo[c2]);
                c2Parent.addChild(nodeInfo[c1]);
                nodeInfo[c1].changeParent(c2Parent);
                nodeInfo[c2].changeParent(c1Parent);
            } else if (command[0].equals("500")) {
                // 알림 받을 수 있는 채팅방 수 조회
                System.out.println(possReceiveNode(Integer.parseInt(command[1]), 0, true) - 1);
            }
        }
    }

    private static int possReceiveNode(int c, int auth, boolean start) {
        // c번까지 알림이 도달할 수 있는 채팅방의 수 (c는 제외)
        if (!start && nodeInfo[c].wall) {
            // 이 이후부터는 모두 받기 불가
            return 0;
        }
        int result = 0;
        if (nodeInfo[c].authority >= auth) result++; // 해당 노드에서도 보낼 수 있다면
        for (Node child : nodeInfo[c].child) {
            result += possReceiveNode(child.number, auth + 1, false);
        }
        return result;
    }

    private static void init(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        nodeInfo = new Node[N + 1];
        st = new StringTokenizer(br.readLine());
        st.nextToken();
        int[][] temp = new int[N + 1][2];
        for (int i = 0; i <= N; i++) nodeInfo[i] = new Node();
        for (int i = 0; i < 2 * N; i++) {
            if (i >= N) {
                // 권한
                temp[i - N][1] = Integer.parseInt(st.nextToken());
            } else {
                temp[i][0] = Integer.parseInt(st.nextToken());
            }
        }
        // 입력받은 정보로 트리 저장
        nodeInfo[0].setNode(0, 0, null);
        for (int i = 0; i < N; i++) {
            // i+1번 노드
            Node parent = nodeInfo[temp[i][0]];
            nodeInfo[i + 1].setNode(i + 1, temp[i][1], parent);
            parent.addChild(nodeInfo[i + 1]);
        }
    }

}