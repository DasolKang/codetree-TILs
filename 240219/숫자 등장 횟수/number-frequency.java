import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Map<Long, Integer> map = new HashMap<>();
        st = new StringTokenizer(br.readLine());
        for (int i=0;i<N;i++) {
            Long key = Long.parseLong(st.nextToken());
            if (map.containsKey(key)) {
                int count = map.get(key);
                map.put(key, ++count);
            } else {
                map.put(key, 1);
            }
        }
        st = new StringTokenizer(br.readLine());
        for (int i=0;i<M;i++){
            Long key = Long.parseLong(st.nextToken());
            if (map.containsKey(key)) {
                System.out.print(map.get(key)+" ");
            } else {
                System.out.print(0+ " ");
            }
        }
    }
}