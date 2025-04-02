public class FastSort {
    public static void main(String[] args) {
        int[] input = {-3, 0, 2, -3, 2, 5, -1};  // your original data

        int max = input[0];
        int min = input[0];

        for (int i = 1; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            }
            if (input[i] < min) {
                min = input[i];
            }
        }

        int range = Math.max(Math.abs(max), Math.abs(min));

        int[] sortPos = new int[range + 1];
        int[] sortNeg = new int[range + 1];
        int[] countPos = new int[range + 1];
        int[] countNeg = new int[range + 1];
        int[] zeroCount = new int[1];

        System.out.println("Max: " + max);
        System.out.println("Min: " + min);

        // processInput(input, sortPos, sortNeg, countPos, countNeg, zeroCount);
    }

    public static void processInput(int[] input, int[] sortPos, int[] sortNeg, int[] countPos, int[] countNeg, int[] zeroCount) {
        // implement sorting logic
    }
}