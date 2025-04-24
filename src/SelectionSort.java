public class SelectionSort {

    ///  Selection sort for an integer array
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        // Move boundary of unsorted subarray one by one
        for (int i = 0; i < n - 1; i++) {
            // index of the min in the unsorted part
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the min and first element of the unsorted part
            if (minIndex != i) {
                int tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
    }
}