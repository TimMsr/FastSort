public class QuickSort {

    ///  Quick sort for an integer array
    public static void quickSort(int[] arr) {
        quick(arr, 0, arr.length);
    }

    private static void quick(int[] arr, int l, int r){
        if (r - l < 2) {
            return;
        }
        int piv = arr[l + (r - l) / 2];
        int i = l, j = r - 1;

        while (i <= j) {
            while (arr[i] < piv) i++;
            while (arr[j] > piv) j--;

            if (i <= j){
                // swap
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++; j--;
            }
        }
        quick(arr, l, j + 1); quick(arr, i, r);
    }
}