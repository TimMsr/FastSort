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

        int range = max + Math.abs(min);

        int[] sortPos = new int[range + 1];
        int[] sortNeg = new int[range + 1];
        int[] countPos = new int[range + 1];
        int[] countNeg = new int[range + 1];
        int[] zeroCount = new int[1];

        processInput(input, sortPos, sortNeg, countPos, countNeg, zeroCount, max);
        printSorted(sortPos, sortNeg, countPos, countNeg, zeroCount, max);
    }

    public static void processInput(int[] input, int[] sortPos, int[] sortNeg, int[] countPos, int[] countNeg, int[] zeroCount, int max) {
        for (int i = 0; i < input.length; i++) {
            if(input[i]==0) { // zero case
                zeroCount[0]++;
            } else if (input[i]>0) { // positive case
                sortPos[input[i]] = input[i];
                countPos[input[i]]++;
            } else { // negative case
                sortNeg[input[i]+max] = input[i];
                countNeg[input[i]+max]++;
            }
        }
    }

    public static void printSorted(int[] sortPos, int[] sortNeg, int[] countPos, int[] countNeg, int[] zeroCount, int max) {
        for (int i = max - 1; i >= 0; i--) { //negative case
            if(countNeg[i]>0) {
                for (int j = 0; j < countNeg[i]; j++) {
                    System.out.print(-(i + 1) + " ");
                }
            }
        }
        for (int i = 0; i < zeroCount[0]; i++) { //print zeros
            System.out.print("0 ");
        }
        for (int i = 0; i < countPos.length; i++) { //positive case
            if(countPos[i]>0) {
                for (int j = 0; j < countPos[i]; j++) {
                    System.out.print(i + " ");
                }
            }
        }
    }
}