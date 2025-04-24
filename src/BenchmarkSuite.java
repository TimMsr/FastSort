import java.util.*;

/**
 * Benchmark for the treap sorting scheme
 * -
 * CSV‑style benchmark output:
 * Columns: [ size,pattern,algorithm,avgMillis,avgMemMB ]
 */
public class BenchmarkSuite {

    // Config
    private static final int[] SIZES = {1_000, 5_000, 10_000, 25_000, 50_000, 100_000};
    private static final int WARM_UPS = 2;
    private static final int TRIALS = 3;
    private static final int INT_RANGE = 100_000;

    private static final Random RAND = new Random();

    public static void main(String[] args) {
        // Prints CSV header once
        System.out.println("size,pattern,algorithm,avgMillis,avgMemMB");

        InputPattern[] patterns = InputPattern.values();
        Sorter[] sorters = {
                new FastSorter(),
                new TimSorter(),
                new QuickSorter(),
                new MergeSorter(),
                new SelectionSorter(),
                new BubbleSorter()
        };

        for (int n : SIZES) {
            for (InputPattern pattern : patterns) {
                int[] master = generateData(n, pattern);

                for (Sorter sorter : sorters) {
                    long totalNanos = 0;
                    long totalMemDelta = 0;

                    for (int run = 0; run < WARM_UPS + TRIALS; run++) {
                        int[] data = Arrays.copyOf(master, master.length);

                        long memBefore = memoryUsageMB();
                        long start = System.nanoTime();
                        sorter.sort(data);
                        long elapsed = System.nanoTime() - start;
                        long memAfter = memoryUsageMB();

                        if (run >= WARM_UPS) {
                            totalNanos += elapsed;
                            totalMemDelta += (memAfter - memBefore);
                        }
                        if (!isSorted(data))
                            throw new IllegalStateException(sorter.name() + " failed to sort");
                    }

                    double avgMillis = (totalNanos / (double) TRIALS) / 1_000_000d;
                    double avgMem = totalMemDelta / (double) TRIALS;

                    // One CSV row per algo/size/pattern
                    System.out.printf("%d,%s,%s,%.3f,%.1f%n",
                            n, pattern, sorter.name(), avgMillis, Math.abs(avgMem));
                }
            }
        }
    }

    // Input generation
    enum InputPattern { RANDOM, NEARLY_SORTED, REVERSE_SORTED }

    private static int[] generateData(int n, InputPattern pattern) {
        int[] arr = new int[n];
        switch (pattern) {
            case RANDOM:
                final int LOW  = -INT_RANGE; // inclusive
                final int HIGH =  INT_RANGE; // exclusive
                for (int i = 0; i < n; i++) {
                    // nextInt(bound) + offset
                    arr[i] = RAND.nextInt(HIGH - LOW) + LOW;
                }
                break;
            case NEARLY_SORTED:
                for (int i = 0; i < n; i++) arr[i] = i;
                int swaps = (int) (n * 0.05); // 5% unordered
                for (int s = 0; s < swaps; s++) {
                    int a = RAND.nextInt(n);
                    int b = RAND.nextInt(n);

                    int tmp = arr[a];
                    arr[a] = arr[b];
                    arr[b] = tmp;
                }
                break;
            case REVERSE_SORTED:
                for (int i = 0; i < n; i++) {
                    arr[i] = n - i;
                }
                break;
        }
        return arr;
    }

    // Helpers
    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1] > a[i]) {
                return false;
            }
        return true;
    }

    private static long memoryUsageMB() {
        Runtime rt = Runtime.getRuntime();
        return (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
    }

    // Interface plug
    interface Sorter {
        void sort(int[] data);
        String name();
    }

    // Adapters to concrete algorithms
    static class FastSorter implements Sorter {
        public void sort(int[] data) {
            int[] sorted = FastSort.fastSort(data);
            System.arraycopy(sorted, 0, data, 0, data.length);
        }
        public String name(){return"FastSort";} }

    static class TimSorter implements Sorter {
        public void sort(int[] d){TimSort.timSort(d);}
        public String name(){return"TimSort";} }

    static class QuickSorter implements Sorter {
        public void sort(int[] d){QuickSort.quickSort(d);}
        public String name(){return"QuickSort";} }

    static class MergeSorter implements Sorter {
        public void sort(int[] d){MergeSort.mergeSort(d);}
        public String name(){return"MergeSort";} }

    static class SelectionSorter implements Sorter {
        public void sort(int[] d){SelectionSort.selectionSort(d);}
        public String name(){return"SelectionSort";} }

    static class BubbleSorter implements Sorter {
        public void sort(int[] d){BubbleSort.bubbleSort(d);}
        public String name(){return"BubbleSort";} }
}