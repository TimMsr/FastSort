import java.util.Random;
import java.util.Arrays;  // for Arrays.fill

public class FastSort {

    /**
     * @param input   the array to be sorted
     * @param offset  the offset for negative integers
     * @param b       array to store first occurrences of pos integers
     * @param countp  array to count occurrences of pos integers
     * @param k       array to store first occurrences of neg integers
     * @param countn  array to count occurrences of neg integers
     */
    public static void fastSortPseudo(int[] input, int offset, int[] b, int[] countp, int[] k, int[] countn) {
        if (input.length == 0)
            return;  //nothing to sort

        int zeroCount = 0;  // zero's are counted separately

         //Process the input array
        //According to the pseudo-code using nested if statements.]
        //This iterates  through the input array and counts the occurrences of each number.
        for (int x = 0; x < input.length; x++) {
            int cur = input[x];
            if (cur == 0) {
                zeroCount++;
            } else if (cur > 0) {
                // Process positive numbers
                if (b[cur] == 0) {  // Not yet stored
                    b[cur] = cur;
                }
                if (b[cur] == cur) {
                    countp[cur]++;
                }
            } else { // Negative values
                // Map negative value: index = cur + offset.
                int index = cur + offset;
                if (k[index] == 0) {  // Not yet stored
                    k[index] = cur;
                }
                if (k[index] == cur) {
                    countn[index]++;
                }
            }
        }

        //Reconstructs sorted output.
        //WE build the output in a string buffer to avoid multiple print calls, hence improving the performance of the program
        StringBuilder sb = new StringBuilder();

        // Output negatives: iterate from index = offset downto 1.
        // Mapped negative: original value = index - offset.
        for (int i = offset; i >= 1; i--) {
            if (countn[i] != 0) {
                for (int j = 0; j < countn[i]; j++) {
                    sb.append((i - offset)).append(" ");
                }
            }
        }

        //Output zeros.
        for (int i = 0; i < zeroCount; i++) {
            sb.append("0 ").append(" ");
        }

        // Output positives: iterate from index 1 to offset.
        for (int i = 1; i <= offset; i++) {
            if (countp[i] != 0) {
                for (int j = 0; j < countp[i]; j++) {
                    sb.append(i).append(" ");
                }
            }
        }
        // (Optional) If needed, you can return sb.toString() for verification.
        //However be careful with large arrays, as this will create a large string, so change the input size.
        //System.out.println(sb.toString());
        // For benchmarking, we simply discard the result.
    }

    //benchmarkPseudoSort: measures average runtime, reusing arrays to remove GC overhead, since we were having issues with low input sizes taking too much time.
    public static long benchmarkPseudoSort(int[] input, int iterations) {
        // 1) find min & max in O(n) — same as paper requires.
        int max = input[0];
        int min = input[0];
        for (int i = 1; i < input.length; i++) {
            int cur = input[i];
            if (cur > max) max = cur;
            if (cur < min) min = cur;
        }
        int offset = Math.max(max, Math.abs(min));  // maps negatives to offset

        // 2) pre-alloc arays once per size (avoid new int[] in each run (hence increasing performance)) — matches Paper’s single allocation per size
        int[] b = new int[offset + 1];
        int[] countp = new int[offset + 1];
        int[] k = new int[offset + 1];
        int[] countn = new int[offset + 1];

        long totalTime = 0;
        int warmup = iterations / 10;  // warm-up portion to trigger JIT

        // warm-up runs (not timed), to reduce JIT overhead
        // This is to ensure that the JIT compiler optimizes the code before we start measuring the time.

        // The fill command is done so that we can clear the arrays before each run, as we are reusing them, because otherwise they would
        // blead over from the previous run, and we would not be able to measure the time correctly.
        // This is important for the benchmark, as we want to measure the time taken by the algorithm, not the time taken by the garbage collectiuon.
        for (int i = 0; i < warmup; i++) {
            Arrays.fill(b, 0);      // clear counts
            Arrays.fill(countp, 0);
            Arrays.fill(k, 0);
            Arrays.fill(countn, 0);
            fastSortPseudo(input, offset, b, countp, k, countn);
        }

        // timed runs
        for (int i = 0; i < iterations; i++) {
            Arrays.fill(b, 0);      // reset before each run
            Arrays.fill(countp, 0);
            Arrays.fill(k, 0);
            Arrays.fill(countn, 0);
            long start = System.nanoTime();
            fastSortPseudo(input, offset, b, countp, k, countn);
            long end = System.nanoTime();
            totalTime += (end - start);
        }

        return totalTime / iterations;  // average ns per iteration
    }

    // utility to produce random test arrays
    public static int[] generateRandomArray(int size, int minValue, int maxValue, Random rand) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(maxValue - minValue + 1) + minValue;
        }
        return array;
    }

    public static void main(String[] args) {
        // defines the input sizes for benchmarking (used the ones from the paper)
        int[] sizes = {10, 100, 1000, 10000, 100000};
        Random rand = new Random();

        for (int size : sizes) {
            int iterations = size < 1000 ? 1000 : 100;  // more runs for small n to decrease overhead


            // Here we use the 8 digits from the paper, but you can change the range to test other values, which if reduced will make it significantly faster.
            int[] testArray = generateRandomArray(size, -10000000, 10000000, rand);
            long avgTime = benchmarkPseudoSort(testArray, iterations);  // capture avg runtime
            System.out.println("Size: " + size
                    + " | Average execution time: " + avgTime + " ns ("
                    + (avgTime / 1e9) + " s) over " + iterations + " iterations.");
        }
    }
}
