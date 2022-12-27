import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 스티커_붙이기 {
    static int n,m,k, answer = 0;
    static boolean[][] note;
    static List<int[][]> stickers = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        input();
        solution();
        calculate();
        System.out.println(answer);
    }

    private static void input() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        note = new boolean[n][m];
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int[][] sticker = new int[r][c];
            for (int j = 0; j < r; j++) {
                st = new StringTokenizer(br.readLine());
                for (int l = 0; l <c ; l++) {
                    sticker[j][l] = Integer.parseInt(st.nextToken());
                }
            }
            stickers.add(sticker);
        }
    }

    /**
     * sticker 칸 세기
     */
    private static void calculate() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (note[i][j])
                    ++answer;
            }
        }
    }

    /**
     * solution 코어함수
     */
    private static void solution() {
        stickers.forEach(sticker ->
            findSpace(sticker));
    }

    /**
     * 빈공간 찾기
     * @param sticker
     */
    private static void findSpace(int[][] sticker) {
        int[][] tempSticker = sticker;
        for (int i = 0; i < 4; i++) {
            tempSticker = rotate90(tempSticker, i);
            int nr = tempSticker.length;
            int nc = tempSticker[0].length;

            for (int j = 0; j < n; j++) {
                for (int l = 0; l < m; l++) {
                    if (j + nr > n || l + nc > m) break;
                    if (isSpace(j, nr, l, nc, tempSticker)) {
                        putSticker(j, l, nr, nc, tempSticker);
                        return;
                    }
                }
            }
        }
    }

    /**
     * 비어있는 공간에 스티커 붙이기
     * @param curr
     * @param curc
     * @param nr
     * @param nc
     * @param sticker
     */
    private static void putSticker(int curr, int curc, int nr, int nc, int[][] sticker) {
        for (int i = curr; i < curr + nr; i++) {
            for (int j = curc; j < curc + nc; j++) {
                if (sticker[i - curr][j - curc] == 1)
                    note[i][j] = true;
            }
        }
    }

    /**
     * 공간이 비어있는지 확인
     * @param j
     * @param nr
     * @param l
     * @param nc
     * @param sticker
     * @return
     */
    private static boolean isSpace(int j, int nr,int l,int nc, int[][] sticker) {
        for (int i = j; i < nr + j; i++) {
            for (int o = l; o < nc + l; o++) {
                if (note[i][o] && sticker[i -j][o - l] == 1)
                    return false;
            }
        }
        return true;
    }
    /**
     * 90도 돌린 배열 반환
     * @param sticker 스티커 배열
     * @param degree 회전
     * @return 90도회전한배열반환
     */
    private static int[][] rotate90(int[][] sticker, int degree) {
        if (degree == 0)
            return sticker;
        int r = sticker.length;
        int c = sticker[0].length;
        int[][] rotateSticker = new int[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                rotateSticker[j][r - i - 1] = sticker[i][j];
            }
        }
        return rotateSticker;
    }


}
