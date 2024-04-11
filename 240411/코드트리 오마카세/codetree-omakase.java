import java.io.*;
import java.util.*;

public class Main {
    private static int L, Q, curTime;
    private static Map<String, int[]> person;
    private static Map<String, ArrayList<Integer>> sushi;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        sushi = new HashMap<>();
        person = new HashMap<>();
        for (int i = 0; i < Q; i++) {
            String[] command = br.readLine().split(" ");
            turnSushi(Integer.parseInt(command[1]));
            if (command[0].equals("100")) {
                // 스시 놓기
                int time = Integer.parseInt(command[2]);
                ArrayList<Integer> temp;
                if (sushi.containsKey(command[3]))
                    temp = sushi.get(command[3]);
                else temp = new ArrayList<>();
                temp.add(time % L);
                sushi.put(command[3], temp);
            } else if (command[0].equals("200")) {
                // 사람 입장
                int time = Integer.parseInt(command[2]);
                int[] temp = new int[]{time % L, Integer.parseInt(command[4])};
                person.put(command[3], temp);
            } else if (command[0].equals("300")) {
                // 상태 출력
                System.out.println(getPersonNum() + " " + getSushiNum());
            }
        }
    }

    private static void turnSushi(int time) {
        // time까지 회전초밥 회전 시키기
        int move = time - curTime;
        for (String name : sushi.keySet()) {
            ArrayList<Integer> location = sushi.get(name);
            if (location.isEmpty()) continue;
            ArrayList<Integer> nextLocation = new ArrayList<>();
            int[] personInfo = new int[2];
            ArrayList<Integer> eatenSushi = new ArrayList<>();
            for (int i = 0; i < location.size(); i++) {
                int newLocation = (location.get(i) + move) % L;
                nextLocation.add(newLocation);
                // 지나온 길에 주인 있었다면
                if (person.containsKey(name)) {
                    personInfo = person.get(name);
                    if (location.get(i) <= personInfo[0] && personInfo[0] <= newLocation) {
                        personInfo[1]--;
                        eatenSushi.add(i);
                    } else if (newLocation < location.get(i)) {
                        if (location.get(i) <= personInfo[0] || personInfo[0] <= newLocation) {
                            personInfo[1]--;
                            eatenSushi.add(i);
                        }
                    }
                    person.put(name, personInfo);
                }
            }
            nextLocation = eatSushi(nextLocation, eatenSushi);
            sushi.put(name, nextLocation);
            if (person.containsKey(name))
                person.put(name, personInfo);
        }
        curTime = time;
    }

    private static ArrayList<Integer> eatSushi(ArrayList<Integer> loc, ArrayList<Integer> eatInfo) {
        int[] temp = new int[loc.size()];
        for (int i = 0; i < loc.size(); i++) {
            temp[i] = loc.get(i);
        }
        for (int e : eatInfo) {
            temp[e] = -1;
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int t : temp) {
            if (t != -1) result.add(t);
        }
        return result;
    }

    private static int getPersonNum() {
        int result = 0;
        for (String name : person.keySet()) {
            int[] temp = person.get(name);
            if (temp[1] > 0) result++;
        }
        return result;
    }

    private static int getSushiNum() {
        int result = 0;
        for (String name : sushi.keySet()) {
            result += sushi.get(name).size();
        }
        return result;
    }

}