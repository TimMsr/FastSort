import java.util.ArrayList;
import java.util.Collections;

public class BucketSort {

    public static void bucketSort(int[] arr) {
        if (arr.length == 0) {
            return; // Nothing to sort
        }

        // Find range
        int min = arr[0];
        int max = arr[0];
        for (int num : arr) {
            if (num < min) {
                min = num;
            }
            if (num > max) {
                max = num;
            }
        }
        int bucketCount = arr.length; 
        ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
        for (int i = 0; i < bucketCount; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (int num : arr) {
            int bucketIndex = (int) ((long) (num - min) * (bucketCount - 1) / (max - min));
            buckets[bucketIndex].add(num);
        }
        //Sort each bucket and merge
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket);
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }
}