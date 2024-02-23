import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] indegree = new int[n+1];
        List<Integer>[] graph = new List[n+1];
        for (int i=0;i<=n;i++) {
            graph[i] = new ArrayList<Integer>();
        }

        for (int i=0;i<m;i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            indegree[b]++;
        }

        Queue<Integer> sequence = new PriorityQueue<>();
        for (int i=1;i<=n;i++) {
            if (indegree[i]==0) {
                sequence.add(i);
            }
        }

        StringBuilder answer = new StringBuilder();
        while (!sequence.isEmpty()) {
            int cur = sequence.poll();
            answer.append(cur).append(" ");
            for (int next : graph[cur]) {
                indegree[next]--;
                if (indegree[next]==0) {
                    sequence.add(next);
                }
            }
        }

        System.out.println(answer.toString());
    }
}