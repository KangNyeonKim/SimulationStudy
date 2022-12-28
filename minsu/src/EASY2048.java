import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class EASY2048 {
    static int n, max = Integer.MIN_VALUE;
    static int[][] map;
    public static void main(String[] args) throws Exception {
        input();
        solution();
        System.out.println(max);
    }

    private static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void solution() {
        move(0, map);
    }

    private static void move(int cnt, int[][] map) {
        if (cnt == 5){
            findMax(map);
            return;
        }
        /*
         * 복사
         * 상하좌우 움직이기
         * move 재귀
         */
        for (int i = 0; i < 4; i++) {
            int[][] nextMap = copyMap(map);
            moveDir(i, nextMap);
            move(cnt + 1, nextMap);
        }
    }

    private static void findMax(int[][] map) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[i][j] > max) {
                    max = map[i][j];
                }
            }
        }
    }

    private static int[][] copyMap(int[][] map) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            copy[i] = Arrays.copyOf(map[i], n);
        }
        return copy;
    }

    private static void moveDir(int dir, int[][] map) {
        /**
         * up
         */
        if (dir == 0){
            for (int i = 0; i < n; i++) {
                int lastMoveBlock = 0;
                for (int j = 1; j < n ; j++) {
                    if (map[j][i] != 0){
                        lastMoveBlock = moveUpOrDownBlock(j, i, lastMoveBlock, map, 1);
                    }
                }

            }
        }
        /**
         * down
         */
        else if (dir == 1) {
            for (int i = 0; i < n; i++) {
                int lastMoveBlock = n - 1;
                for (int j = n - 2; j >= 0; j--) {
                    lastMoveBlock = moveUpOrDownBlock(j, i, lastMoveBlock, map, -1);
                }
            }
        }
        /**
         * left
         */
        else if (dir == 2) {
            for (int i = 0; i < n; i++) {
                int lastMoveBlock = 0;
                for (int j = 1; j < n; j++) {
                    lastMoveBlock = moveLeftOrRightBlock(i, j, lastMoveBlock, map, 1);
                }
            }
        }
        /**
         * right
         */
        else {
            for (int i = 0; i < n; i++) {
                int lastMoveBlock = n - 1;
                for (int j = n - 2; j >= 0; j--) {
                    lastMoveBlock = moveLeftOrRightBlock(i, j, lastMoveBlock, map, -1);
                }
            }
        }
    }

    /**
     * 상하 이동 함수
     * 이전에 움직인 블럭 인덱스
     * 1. 이전이 0이면 바로 블럭 한칸 이동
     * 2. 이전이 현재 블럭과 같으면 합쳐서 한칸 이동 lastMove 갱신
     * 3. 다르면 위에 한칸 이전으로 이동하고 lastMove 갱신
     */
    private static int moveUpOrDownBlock(int r, int c, int lastMoveBlock, int[][] inputMap, int dir) {
        int prev = inputMap[lastMoveBlock][c];
        if (inputMap[r][c] != 0) {
            if (prev == 0){
                inputMap[lastMoveBlock][c] = inputMap[r][c];
                inputMap[r][c] = 0;
            }
            else if (prev == inputMap[r][c]) {
                inputMap[r][c] = 0;
                inputMap[lastMoveBlock][c] *= 2;
                lastMoveBlock += dir;
            }
            else if(prev != inputMap[r][c]) {
                lastMoveBlock += dir;
                inputMap[lastMoveBlock][c] = inputMap[r][c];
                if (lastMoveBlock != r)
                    inputMap[r][c] = 0;
            }
        }
        return lastMoveBlock;
    }


    private static int moveLeftOrRightBlock(int r, int c, int lastMoveBlock, int[][] inputMap, int dir) {
        int prev = inputMap[r][lastMoveBlock];
        if (inputMap[r][c] != 0) {
            if (prev == 0){
                inputMap[r][lastMoveBlock] = inputMap[r][c];
                inputMap[r][c] = 0;
            }
            else if (prev == inputMap[r][c]) {
                inputMap[r][c] = 0;
                inputMap[r][lastMoveBlock] *= 2;
                lastMoveBlock += dir;
            }
            else if (prev != inputMap[r][c]) {
                lastMoveBlock += dir;
                inputMap[r][lastMoveBlock] = inputMap[r][c];
                if (lastMoveBlock != c)
                    inputMap[r][c] = 0;
            }
        }
        return lastMoveBlock;
    }

}
