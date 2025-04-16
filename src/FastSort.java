import java.util.Random;

public class fastpseudocode {

    /**
     * Fast sort method that mimics the pseudo-code from the paper.
     * Processes an array by:
     * 1. Finding max and min;
     * 2. Separating and counting zeros, positive, and negative numbers
     *    using auxiliary arrays.
     * 3. Reconstructing a sorted output (here built into a StringBuilder).
     *
     * This version discards printing the output to avoid I/O overhead in benchmarking.
     */
    public static void fastSortPseudo(int[] input) {
        if (input.length == 0)
            return;

        // STEP 1: Find maximum and minimum in the input.
        int max = input[0];
        int min = input[0];
        for (int i = 1; i < input.length; i++) {
            int val = input[i];
            if (val > max) {
                max = val;
            }
            if (val < min) {
                min = val;
            }
        }

        // Use offset = max( max, |min| ) to properly map negative values.
        int offset = Math.max(max, Math.abs(min));

        // STEP 2: Declare arrays.
        // For positive numbers:
        int[] b = new int[offset + 1];      // Stores positive number on first occurrence.
        int[] countp = new int[offset + 1];   // Counts occurrences of positive numbers.
        // For negative numbers:
        int[] k = new int[offset + 1];        // Stores first occurrence of mapped negatives.
        int[] countn = new int[offset + 1];   // Counts occurrences of negatives.
        // For zeros:
        int zeroCount = 0;

        // STEP 3: Process each element in input.
        // According to the pseudo-code using nested if/else
        for (int x = 0; x < input.length; x++) {
            int val = input[x];
            if (val == 0) {
                zeroCount++;
            } else if (val > 0) {
                // Process positive numbers
                if (b[val] == 0) {  // Not yet stored
                    b[val] = val;
                }
                if (b[val] == val) {
                    countp[val]++;
                }
            } else { // Negative values
                // Map negative value: index = val + offset.
                int index = val + offset;
                if (k[index] == 0) {  // Not yet stored
                    k[index] = val;
                }
                if (k[index] == val) {
                    countn[index]++;
                }
            }
        }

        // STEP 4: Reconstruct sorted output.
        // We build the sorted output into a StringBuilder and then discard it.
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

        // Output zeros.
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
        // For benchmarking, we simply discard the result.
    }

    /**
     * Benchmarks the fastSortPseudo method.
     * The method performs a warm-up phase followed by a number of iterations,
     * measuring the execution time with System.nanoTime().
     *
     * @param input The array to sort.
     * @param iterations The number of timed iterations.
     * @return The average execution time per iteration in nanoseconds.
     */
    public static long benchmarkPseudoSort(int[] input, int iterations) {
        long totalTime = 0;
        // Warm-up phase: run a fraction of iterations to allow JVM optimizations.
        int warmup = iterations / 10;
        for (int i = 0; i < warmup; i++) {
            fastSortPseudo(input);
        }
        // Timed iterations.
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            fastSortPseudo(input);
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        return totalTime / iterations;
    }

    /**
     * Utility method to generate an array with random integers.
     * Values are generated between minValue and maxValue (inclusive).
     */
    public static int[] generateRandomArray(int size, int minValue, int maxValue, Random rand) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(maxValue - minValue + 1) + minValue;
        }
        return array;
    }

    public static void main(String[] args) {
        // Define input sizes for benchmarking.
        int[] sizes = {10, 100, 10000, 100000};
        Random rand = new Random();

        // Set iterations based on size (more iterations for small sizes).
        int iterationsSmall = 10000;  // for size 10 and 100
        int iterationsMedium = 1000;  // for size 10,000
        int iterationsLarge = 100;    // for size 100,000

        for (int size : sizes) {
            int iterations;
            if (size <= 100) {
                iterations = iterationsSmall;
            } else if (size <= 10000) {
                iterations = iterationsMedium;
            } else {
                iterations = iterationsLarge;
            }
            // Generate a random array with values in [-1000, 1000].
            int[] testArray = generateRandomArray(size, -1000, 1000, rand);
            long avgTime = benchmarkPseudoSort(testArray, iterations);
            System.out.println("Size: " + size
                    + " | Average execution time: " + avgTime + " ns ("
                    + (avgTime / 1e9) + " s) over " + iterations + " iterations.");
        }
    }
}
