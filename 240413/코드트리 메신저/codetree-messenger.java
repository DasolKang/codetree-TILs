import java.util.Scanner;

public class Main {
    public static final int MAX_N = 100001;
    public static final int MAX_D = 22;

    public static int N, Q;
    public static int[] authority = new int[MAX_N];
    public static int[] parents = new int[MAX_N];
    public static int[] answer = new int[MAX_N];
    public static boolean[] wall = new boolean[MAX_N];
    public static int[][] sendInfo = new int[MAX_N][MAX_D]; //[i][j] : i번 부서에 j권한을 가진 알림

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        Q = sc.nextInt();

        while (Q-- > 0) {
            int query = sc.nextInt();
            if (query == 100) {
                init(sc);
            } else if (query == 200) {
                int chat = sc.nextInt();
                toggle_noti(chat);
            } else if (query == 300) {
                int chat = sc.nextInt();
                int power = sc.nextInt();
                change_power(chat, power);
            } else if (query == 400) {
                int chat1 = sc.nextInt();
                int chat2 = sc.nextInt();
                change_parent(chat1, chat2);
            } else if (query == 500) {
                int chat = sc.nextInt();
                System.out.println(answer[chat]);
            }
        }
    }

    // 채팅의 알림 상태를 토글합니다.
    public static void toggle_noti(int chat) {
        if (wall[chat]) {
            int cur = parents[chat];
            int num = 1;
            // 상위 채팅으로 이동하며 noti 값에 따라 nx와 val 값을 갱신합니다.
            while (cur != 0) {
                for (int i = num; i <= 21; i++) {
                    answer[cur] += sendInfo[chat][i];
                    if (i > num) sendInfo[cur][i - num] += sendInfo[chat][i];
                }
                if (wall[cur]) break;
                cur = parents[cur];
                num++;
            }
            wall[chat] = false;
        } else {
            int cur = parents[chat];
            int num = 1;
            // 상위 채팅으로 이동하며 noti 값에 따라 nx와 val 값을 갱신합니다.
            while (cur != 0) {
                for (int i = num; i <= 21; i++) {
                    answer[cur] -= sendInfo[chat][i];
                    if (i > num) sendInfo[cur][i - num] -= sendInfo[chat][i];
                }
                if (wall[cur]) break;
                cur = parents[cur];
                num++;
            }
            wall[chat] = true;
        }
    }

    // 채팅의 권한의 크기를 변경합니다.
    public static void change_power(int chat, int power) {
        int bef_power = authority[chat];
        power = Math.min(power, 20);  // 권한의 크기를 20으로 제한합니다.
        authority[chat] = power;

        sendInfo[chat][bef_power]--;
        if (!wall[chat]) {
            int cur = parents[chat];
            int num = 1;
            // 상위 채팅으로 이동하며 nx와 val 값을 갱신합니다.
            while (cur != 0) {
                if (bef_power >= num) answer[cur]--;
                if (bef_power > num) sendInfo[cur][bef_power - num]--;
                if (wall[cur]) break;
                cur = parents[cur];
                num++;
            }
        }

        sendInfo[chat][power]++;
        if (!wall[chat]) {
            int cur = parents[chat];
            int num = 1;
            // 상위 채팅으로 이동하며 nx와 val 값을 갱신합니다.
            while (cur != 0) {
                if (power >= num) answer[cur]++;
                if (power > num) sendInfo[cur][power - num]++;
                if (wall[cur]) break;
                cur = parents[cur];
                num++;
            }
        }
    }

    // 두 채팅의 부모를 교체합니다.
    public static void change_parent(int chat1, int chat2) {
        boolean bef_noti1 = wall[chat1];
        boolean bef_noti2 = wall[chat2];

        if (!wall[chat1]) toggle_noti(chat1);
        if (!wall[chat2]) toggle_noti(chat2);

        int temp = parents[chat1];
        parents[chat1] = parents[chat2];
        parents[chat2] = temp;

        if (!bef_noti1) toggle_noti(chat1);
        if (!bef_noti2) toggle_noti(chat2);
    }

    // 초기 설정 값을 받아옵니다.
    public static void init(Scanner sc) {
        // 부모 채팅과 채팅의 권한 정보를 입력받습니다.
        for (int i = 1; i <= N; i++) {
            parents[i] = sc.nextInt();
        }
        for (int i = 1; i <= N; i++) {
            authority[i] = sc.nextInt();
            // 채팅의 권한이 20을 초과하는 경우 20으로 제한합니다.
            if (authority[i] > 20) authority[i] = 20;
        }

        // nx 배열과 val 값을 초기화합니다.
        for (int i = 1; i <= N; i++) {
            int cur = i;
            int x = authority[i];
            sendInfo[cur][x]++;
            // 상위 채팅으로 이동하며 nx와 val 값을 갱신합니다.
            while (parents[cur] != 0 && x != 0) {
                cur = parents[cur];
                x--;
                if (x != 0) sendInfo[cur][x]++;
                answer[cur]++;
            }
        }
    }

}