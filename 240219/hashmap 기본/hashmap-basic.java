import java.util.*;
import java.io.*;

public class Main {

    private static HashMap<Integer, Integer> map;

    public static void main(String[] args) throws IOException {
        map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for (int i=0;i<N;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            if (command.equals("add")) {
                int key = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                map.put(key, value);
            } else if (command.equals("find")) {
                int key = Integer.parseInt(st.nextToken());
                if (map.containsKey(key)) {
                    System.out.println(map.get(key));
                } else {
                    System.out.println("None");
                }               
            } else {
                map.remove(Integer.parseInt(st.nextToken()));
            }
            
        }
    }
}