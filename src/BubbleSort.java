public class BubbleSort {

    ///  Bubble sort for an integer array
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;
        // Repeat passes until no swaps
        for (int pass = 0; pass < n - 1; pass++) {
            swapped = false;
            // bubble the largest element in the unsorted portion to the final position
            for (int j = 0; j < n - 1 - pass; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j + 1]
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            // If no swap, array is sorted
            if (!swapped) {
                break;
            }
        }
    }

}