import java.util.*;
import java.io.*;

public class 감시 {
    static int[][] map;
    static boolean[][] originBlocks;
    static int N,M = 0, answer = Integer.MAX_VALUE, totalCnt = 0;
    static List<CCTV> cctv = new ArrayList<>();
    static class CCTV implements Cloneable{
        int x, y, type, cnt;
        public CCTV(int x, int y,int cnt, int type) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.type = type;
        }
        @Override protected  Object clone() throws CloneNotSupportedException{
            return super.clone();
        }
        @Override
        public String toString() {
            return "x " + x
                + " y " + y
                + "type " + type
                + "cnt " + cnt;
        }
    }
    public static void main(String[] args) throws Exception{
        input();
        solution();
    }

    private static void input() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        originBlocks = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] != 0)
                    originBlocks[i][j] = true;
                if (map[i][j] != 6 && map[i][j] != 0) {
                    int dir = 4;
                    if (map[i][j] == 2)
                        dir = 2;
                    if (map[i][j] == 5)
                        dir = 1;
                    cctv.add(new CCTV(i, j, dir, map[i][j]));
                }
            }
        }
    }
    private static void solution() throws Exception{
        recursion(cctv, 0, 0);
        System.out.println(answer);
    }

    private static void insertMap(boolean[][] blocks, List<CCTV> arr) {
        resetBlocks(blocks);
        for (int i = 0; i < arr.size(); i++) {
            CCTV cctv = arr.get(i);
            if (cctv.type == 1){
                insertMap1(blocks, cctv.x, cctv.y, cctv.cnt);
            }else if (arr.get(i).type == 2) {
                insertMap2(blocks, cctv.x, cctv.y, cctv.cnt);
            }else if (arr.get(i).type == 3) {
                insertMap3(blocks, cctv.x, cctv.y, cctv.cnt);
            }else if (arr.get(i).type == 4) {
                insertMap4(blocks, cctv.x, cctv.y, cctv.cnt);
            }else if (arr.get(i).type == 5) {
                insertMap5(blocks, cctv.x, cctv.y);
            }
        }
//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(blocks[i]));
//        }
        totalCnt++;
        answer = Math.min(answer, calculate(blocks));
    }
    /**
     * make comb
     */
    private static void recursion(List<CCTV> arr, int r, int i) throws CloneNotSupportedException {
        if (r == arr.size()){
            insertMap(new boolean[N][M], arr);
            return;
        }
        int cnt = arr.get(i).cnt;
        while (cnt > 0){
            arr.get(i).cnt--;
            List<CCTV> list = new ArrayList<>();
            for (CCTV cctv1 : arr) {
                Object clone = cctv1.clone();
                list.add((CCTV) clone);
            }
            recursion(list, r + 1, i +1);
            cnt--;
        }
    }

    /**
     * calculate
     */
    private static int calculate(boolean[][] blocks) {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!blocks[i][j])
                    ++sum;
            }
        }
        return sum;
    }

    /**
     * reset blocks
     */
    private static void resetBlocks(boolean[][] blocks) {
        for (int i = 0; i < N; i++) {
            blocks[i] = Arrays.copyOf(originBlocks[i], M);
        }
    }

    /**
     * check left
     */
    private static void insertLeft(int i, int j, boolean flag, boolean[][] blocks) {
        for (int k = j; k >= 0; k--) {
            if (map[i][k] == 6) return;
            if (map[i][k] != 0) continue;
            blocks[i][k] = flag;
        }
    }

    /**
     * check right
     */
    private static void insertRight(int i, int j, boolean flag, boolean[][] blocks) {
        for (int k = j; k < M; k++) {
            if (map[i][k] == 6) return;
            if (map[i][k] != 0) continue;
            blocks[i][k] = flag;
        }
    }

    /***
     * insert Up
     */
    private static void insertUp(int i, int j, boolean flag, boolean[][] blocks) {
        for (int k = i; k >= 0; k--) {
            if (map[k][j] == 6) return;
            if (map[k][j] != 0) continue;
            blocks[k][j] = flag;
        }
    }

    /**
     * insert down
     */
    private static void insertDown(int i, int j, boolean flag, boolean[][] blocks) {
        for (int k = i; k < N; k++) {
            if (map[k][j] == 6) return;
            if (map[k][j] != 0) continue;
            blocks[k][j] = flag;
        }
    }

    /**
     * insert 1
     */
    private static boolean[][] insertMap1(boolean[][] blocks, int i, int j, int dir) {
        /**
         * left
         */
        if (dir == 0) {
            insertLeft(i, j, true, blocks);
        }

        /**
         * right
         */
        else if (dir == 1) {
            insertRight(i, j, true, blocks);
        }

        /**
         * up
         */
        else if (dir == 2) {
            insertUp(i, j, true, blocks);
        }

        /**
         * down
         */
        else if (dir == 3) {
            insertDown(i, j ,true, blocks);
        }
        return blocks;
    }

    /**
     * insert 2
     */
    private static void insertMap2(boolean[][] blocks, int i, int j, int dir) {
        if (dir == 0) {
            insertLeft(i, j, true, blocks);
            insertRight(i, j, true, blocks);
        }

        else if (dir == 1) {
            insertUp(i, j, true, blocks);
            insertDown(i, j, true, blocks);
        }
    }

    /**
     * insert 3
     */
    private static void insertMap3(boolean[][] blocks, int i, int j, int dir) {
        /**
         * up
         */
        if (dir == 0) {
            insertUp(i, j, true, blocks);
            insertRight(i, j, true, blocks);
        }
        /*
        right
         */
        else if (dir == 1) {
            insertDown(i, j, true, blocks);
            insertRight(i, j, true, blocks);
        }

        /**
         * down
         */
        else if(dir == 2) {
            insertDown(i, j, true, blocks);
            insertLeft(i, j, true, blocks);
        }
        /**
         * left
         */
        else if (dir == 3) {
            insertLeft(i, j, true, blocks);
            insertUp(i, j, true, blocks);
        }
    }

    /**
     * insert4
     */
    private static void insertMap4(boolean[][] blocks, int i, int j, int dir) {
        /**
         * up
         */
        if (dir == 0) {
            insertLeft(i, j, true, blocks);
            insertRight(i, j, true, blocks);
            insertUp(i, j, true, blocks);
        }
        /**
         * down
         */
        else if (dir == 1){
            insertLeft(i, j, true, blocks);
            insertRight(i, j, true, blocks);
            insertDown(i, j, true, blocks);
        }
        /**
         * left
         */
        else if (dir == 2) {
            insertLeft(i, j, true, blocks);
            insertDown(i, j, true, blocks);
            insertUp(i, j, true, blocks);
        }
        /**
         *right
         */
        else if (dir == 3) {
            insertRight(i, j, true, blocks);
            insertDown(i, j, true, blocks);
            insertUp(i, j, true, blocks);
        }
    }

    /**
     * insert 5
     */
    private static void insertMap5(boolean[][] blocks, int i, int j) {
        insertUp(i, j, true, blocks);
        insertDown(i, j, true, blocks);
        insertLeft(i, j, true, blocks);
        insertRight(i, j, true, blocks);
    }
}
