public class FastSort {
    public static void main(String[] args) {
        // Sample input array
        int[] input = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Capture start time in nanoseconds
        long startTime = System.nanoTime();
        System.out.println("Start time (ns): " + startTime);

        // --- Fast sort algorithm ---
        int max = input[0];
        int min = input[0];

        // Find the maximum and minimum values in the array
        for (int i = 1; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            }
            if (input[i] < min) {
                min = input[i];
            }
        }

        // Calculate the range for both positive and negative numbers
        int range = max + Math.abs(min);

        // Arrays to hold positive, negative values and their counts
        int[] sortPos = new int[range + 1];
        int[] sortNeg = new int[range + 1];
        int[] countPos = new int[range + 1];
        int[] countNeg = new int[range + 1];
        int[] zeroCount = new int[1];

        // Process the input array
        processInput(input, sortPos, sortNeg, countPos, countNeg, zeroCount, max);
        // Print the sorted values
        printSorted(sortPos, sortNeg, countPos, countNeg, zeroCount, max);
        // --- End of fast sort algorithm ---

        // Capture stop time in nanoseconds
        long stopTime = System.nanoTime();
        System.out.println("\nStop time (ns): " + stopTime);

        // Calculate the duration in nanoseconds and milliseconds
        long durationNs = stopTime - startTime;
        double durationMs = durationNs / 1e9;
        System.out.println("Duration: " + durationNs + " ns (" + durationMs + " seconds)");
    }

    public static void processInput(int[] input, int[] sortPos, int[] sortNeg, int[] countPos, int[] countNeg, int[] zeroCount, int max) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 0) { // Handle zeros separately
                zeroCount[0]++;
            } else if (input[i] > 0) { // Positive numbers
                sortPos[input[i]] = input[i];
                countPos[input[i]]++;
            } else { // Negative numbers
                // Using offset (input[i] + max) to handle negative indices
                sortNeg[input[i] + max] = input[i];
                countNeg[input[i] + max]++;
            }
        }
    }

    public static void printSorted(int[] sortPos, int[] sortNeg, int[] countPos, int[] countNeg, int[] zeroCount, int max) {
        // Print negative numbers in sorted order
        for (int i = max - 1; i >= 0; i--) {
            if (countNeg[i] > 0) {
                for (int j = 0; j < countNeg[i]; j++) {
                    System.out.print(-(i + 1) + " ");
                }
            }
        }
        // Print zeros
        for (int i = 0; i < zeroCount[0]; i++) {
            System.out.print("0 ");
        }
        // Print positive numbers in sorted order
        for (int i = 0; i < countPos.length; i++) {
            if (countPos[i] > 0) {
                for (int j = 0; j < countPos[i]; j++) {
                    System.out.print(i + " ");
                }
            }
        }
    }
}
