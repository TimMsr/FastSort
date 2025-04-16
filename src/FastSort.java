import java.util.Random;

public class FastSort {

    public static void fastSortPseudo(int[] input) {
        if (input.length == 0)
            return;
        

        //Finds the min and max of the input array.
        int max = input[0];
        int min = input[0];
        for (int i = 1; i < input.length; i++) {
            int cur = input[i];
            if (cur > max) {
                max = cur;
            }
            if (cur < min) {
                min = cur;
            }
        }

        //Maps the negative numbers to positive indices.
        //The offset is the absolute value of the minimum number.
        int offset = Math.max(max, Math.abs(min));

        // Declaring the arrays according to the paper
        
        //positve numbers:
        int[] b = new int[offset + 1];      //Stores positive number on first occurrence.
        int[] countp = new int[offset + 1];   //Counts occurrences of positive numbers.
        
        //negative numbers:
        int[] k = new int[offset + 1];        //Stores first occurrence of mapped negatives.
        int[] countn = new int[offset + 1];   //Counts occurrences of negatives.
        
        //zeros:
        int zeroCount = 0;

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

    /**
     * Benchmarks the code's execution time.
     * The method performs a warm-up phase followed by a number of iterations (which we set at 1000, gives a wide range of output to see the performance).,
     * We then measure the execution time with System.nanoTime().
     *
     * @param input The array to sort.
     * @param iterations The number of timed iterations.
     * @return The average execution time per iteration in nanoseconds.
     */
    public static long benchmarkPseudoSort(int[] input, int iterations) {
        long totalTime = 0;
        // Warm-up phase: run a fraction of iterations to allow JVM optimizations, allowing us to get the best performance out of the code.
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

    //Below is the utility method to generate a random array of integers, based on the min and max values.
    public static int[] generateRandomArray(int size, int minValue, int maxValue, Random rand) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(maxValue - minValue + 1) + minValue;
        }
        return array;
    }

    public static void main(String[] args) {
        // Define input sizes for benchmarking.
        int[] sizes = {10, 100, 1000, 10000, 100000};
        Random rand = new Random();

        // Set Number of iterations for benchmarking. Used to reduce set-up time and actually calculate run time.
        int iterations = 100; 

        for (int size : sizes) {
            
            // Generate a random array with values in [-10000000, 10000000].
            int[] testArray = generateRandomArray(size, -10000000, 10000000, rand);
            long avgTime = benchmarkPseudoSort(testArray, iterations);
            System.out.println("Size: " + size
                    + " | Average execution time: " + avgTime + " ns ("
                    + (avgTime / 1e9) + " s) over " + iterations + " iterations.");
        }
    }
}
