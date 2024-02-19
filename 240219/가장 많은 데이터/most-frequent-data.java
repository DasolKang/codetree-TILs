import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int result = 0;
        Map<String, Integer> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for (int i=0;i<N;i++) {
            String key = br.readLine();
            if (map.containsKey(key)) {
                int count = map.get(key);
                map.put(key, ++count);
                result = Integer.max(result, count);
            } else {
                map.put(key, 1);
                result = Integer.max(result, 1);
            }   
        }
        System.out.println(result);
    }
}