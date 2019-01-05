package recursion;

/**
 * Use recursion to solve permutation problem
 */
public class Permutation {

    private int count; // global variable that count total cases

    public void fullArrangement(int[] data, int n, int k) {
        count = 0;
        printPermutations(data, n, k);
        System.out.println("Total cases:" + count);
    }

    /**
     * @param data store all elements used for permutation
     * @param n    total number
     * @param k    number of elements that needed to be arrange
     */
    public void printPermutations(int[] data, int n, int k) {
        if (k == 1) { // means one arrangement have finished
            for (int i = 0; i < n; i++) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
            count++;
            return;
        }

        for (int i = 0; i < k; i++) {
            // swap fix digit to array tail
            int tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;

            // recursive arrange other position
            printPermutations(data, n, k - 1);

            // recovery swap to start next loop, change another fix digit
            tmp = data[i];
            data[i] = data[k - 1];
            data[k - 1] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        Permutation p = new Permutation();
        p.fullArrangement(a, 4, 4);
    }
}
