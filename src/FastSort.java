public class FastSort {
    public static void main(String[] args) {
        int[] input = {-3, 0, 2, -3, 2, 5, -1};

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

        int range = max - min;

        int[] sortPos = new int[range + 1];
        int[] sortNeg = new int[range + 1];
        int[] countPos = new int[range + 1];
        int[] countNeg = new int[range + 1];
        int[] zeroCount = new int[1];

        processInput(input, sortPos, sortNeg, countPos, countNeg, zeroCount);
    }

    public static void processInput(int[] input, int[] sortPos, int[] sortNeg, int[] countPos, int[] countNeg, int[] zeroCount) {
        for (int i = 0; i < input.length; i++) {
            if(input[i]==0) { // zero case
                zeroCount[0]++;
            } else if (input[i]>0) { // positive case
                sortPos[sortPos[i]] = input[i];
                countPos[0]++;
            } else { // negative case

            }
        }
    }
}