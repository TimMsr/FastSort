import java.util.Arrays;


///  Tim Sort for an integer array
public class TimSort {

    public static void timSort(int[] data) {
        // Tim Sort for objects, the integer array is boxed.
        Integer[] boxed = Arrays.stream(data).boxed().toArray(Integer[]::new);
        Arrays.sort(boxed); // TimSort on objects

        for (int i = 0; i < data.length; i++) {
            data[i] = boxed[i];
        }
    }
}
