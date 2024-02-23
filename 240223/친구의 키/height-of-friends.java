import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] indegree = new int[N+1];
        List<Integer>[] graph = new List[N+1];
        for (int i = 0;i<=N;i++) {
            graph[i] = new ArrayList<Integer>();
        }

        for (int i=0;i<M;i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            indegree[b]++;
        }

        Queue<Integer> sequence = new ArrayDeque<>();
        for (int i=1;i<=N;i++) {
            if (indegree[i]==0) sequence.add(i);
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