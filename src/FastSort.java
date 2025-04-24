
import java.util.Arrays;
import java.util.Random;

public class FastSort {

    /**
     * Sorts the given integer array in ascending order using the FastSort
     * algorithm (Idrizi et al., 2017). Runs in O(n + R) time, where
     * R = (max − min), also uses O(R) extra memory.
     * Fixes the only negative input drawback:
     * countPos allocated only if max > 0, countNeg only if min < 0.
     */
    public static int[] fastSort(int[] inputArr) {
        if (inputArr == null || inputArr.length == 0) return new int[0];

        //1. min & max
        int min = inputArr[0], max = inputArr[0];
        for (int v : inputArr) {
            if (v < min) min = v;
            if (v > max) max = v;
        }

        //2. allocate counters – only if range > 0
        int[] countPos = (max > 0) ? new int[max + 1] : new int[1];
        int[] countNeg = (min < 0) ? new int[-min + 1] : new int[1];
        int zeroCnt = 0;

        //3. counting phase
        for (int v : inputArr) {
            if      (v == 0) zeroCnt++;
            else if (v > 0)  countPos[v]++;
            else             countNeg[-v]++;
        }

        //4. rebuild
        int[] outputArr = new int[inputArr.length];
        int k = 0;
        for (int i = countNeg.length - 1; i >= 1; i--)
            while (countNeg[i]-- > 0) outputArr[k++] = -i;
        while (zeroCnt-- > 0) outputArr[k++] = 0;
        for (int i = 1; i < countPos.length; i++)
            while (countPos[i]-- > 0) outputArr[k++] = i;
        return outputArr;
    }





    private static int[] randomIntArray(int size, int min, int max, Random r) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = r.nextInt(max - min + 1) + min;
        return arr;
    }

    public static void main(String[] args) {

        Random rnd = new Random();
        int[] data = randomIntArray(10, -1000, 100, rnd);

        System.out.println(Arrays.toString(fastSort(data)));
        // [-3, -3, -1, 0, 2, 2, 5]
    }
}