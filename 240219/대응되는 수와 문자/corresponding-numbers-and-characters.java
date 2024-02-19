import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Map<Integer, String> numMap = new HashMap<>();
        Map<String, Integer> strMap = new HashMap<>();
        for (int i=1;i<=N;i++) {
            String key = br.readLine();
            numMap.put(i, key);
            strMap.put(key, i);
        }
        for (int i=0;i<M;i++) {
            String key = br.readLine();
            if (isNumber(key)) {
                System.out.println(numMap.get(Integer.parseInt(key)));
            } else {
                System.out.println(strMap.get(key));
            }
        }
    }

    public static boolean isNumber(String isNum) {
        return isNum.chars().allMatch(Character::isDigit);
    }
}