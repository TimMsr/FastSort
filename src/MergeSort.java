public class MergeSort {

    ///  Merge sort for an integer array
    public static void mergeSort(int[] arr){
        int[] buf = new int[arr.length];
        merge(arr, 0, arr.length, buf);
    }

    // Merge Sort helper
    private static void merge(int[] arr, int l, int r, int[] buf){
        if (r - l < 2) return;
        int m = (l + r ) / 2;

        merge(arr, l, m, buf);
        merge(arr, m , r, buf);
        int i = l, j = m, k = l;

        while (i < m || j < r){
            if ( j == r || (i < m && arr[i] <= arr[j])) {
                buf[k++] = arr[i++];
            } else {
                buf[k++] = arr[j++];
            }
        }
        System.arraycopy(buf, l, arr, l, r - l);
    }
}