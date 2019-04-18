package recursion;

/**
 * Use recursion to solve permutation problem
 */
public class Permutation {

    private int count; // global variable that count total cases

    public void fullArrangement(int[] data, int n, int k) {
        count = 0;
        printPermutations(data, n, k, n, k);
        System.out.println("Total cases:" + count);
    }

    /**
     * @param data store all elements used for permutation
     * @param n    total element number
     * @param k    number of elements that needed to be arrange
     * @param in   use to print, equal to initial n
     * @param ik   use to print, equal to initial k
     */
    public void printPermutations(int[] data, int n, int k, int in, int ik) {
        if (k == 1) { // means one arrangement have finished
            for (int i = in - ik; i < in; i++) { //print last k element
                System.out.print(data[i] + " ");
            }
            System.out.println();
            count++;
            return;
        }

        for (int i = 0; i < n; i++) {
            // swap focus digit to array tail
            int tmp = data[i];
            data[i] = data[n - 1];
            data[n - 1] = tmp;

            // recursive arrange other position
            // k and n both need to reduce
            printPermutations(data, n - 1, k - 1, in, ik);

            // recovery swap to start next loop, change another focus digit
            tmp = data[i];
            data[i] = data[n - 1];
            data[n - 1] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        Permutation p = new Permutation();
        p.fullArrangement(a, 4, 4);
    }
}
