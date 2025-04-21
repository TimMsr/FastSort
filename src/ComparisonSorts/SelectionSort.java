package ComparisonSorts;

import java.util.Comparator;
import java.util.Random;

/**
 * Benchmarks array‑based Selection Sort on random {@code Integer} arrays of
 * various sizes.  A warm‑up phase lets the JIT optimise the byte‑code before
 * measurements begin.  The reported value is the *average* nanoseconds per
 * sort.
 */
public class SelectionSort {

    /* -------------------------------------------------------------- */
    /*  Generic *in‑place* Selection Sort for arrays                  */
    /* -------------------------------------------------------------- */
    public static <T> void selectionSort(T[] a, Comparator<? super T> cmp) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (cmp.compare(a[j], a[min]) < 0) min = j;
            }
            T tmp = a[min];
            a[min] = a[i];
            a[i] = tmp;
        }
    }

    /* -------------------------------------------------------------- */
    /*  Benchmark helper                                              */
    /* -------------------------------------------------------------- */
    private static long benchmark(Integer[] sample,
                                  int iterations,
                                  Comparator<Integer> cmp) {

        long total = 0L;
        int warmup = Math.max(1, iterations / 10);

        // --- warm‑up (not timed) -----------------------------------
        for (int i = 0; i < warmup; i++) {
            Integer[] copy = sample.clone();
            selectionSort(copy, cmp);
        }

        // --- timed runs -------------------------------------------
        for (int i = 0; i < iterations; i++) {
            Integer[] copy = sample.clone();  // work on an unsorted copy each run
            long start = System.nanoTime();
            selectionSort(copy, cmp);
            total += System.nanoTime() - start;
        }
        return total / iterations;
    }

    /* -------------------------------------------------------------- */
    /*  Driver / utility                                              */
    /* -------------------------------------------------------------- */
    private static Integer[] randomIntArray(int size, int min, int max, Random r) {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) arr[i] = r.nextInt(max - min + 1) + min;
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 10000, 100000};
        Random rnd = new Random();
        Comparator<Integer> cmp = Integer::compareTo;

        for (int n : sizes) {
            int runs = n <= 1_000 ? 1_000 : 10; 
            Integer[] sample = randomIntArray(n, -10000000, 10000000, rnd);
            long avgNs = benchmark(sample, runs, cmp);
            System.out.printf("Size: %,7d | avg %9.0f ns (%.6f s) over %,5d runs%n", n, (double) avgNs, avgNs / 1e9, runs);
        }
    }
}
