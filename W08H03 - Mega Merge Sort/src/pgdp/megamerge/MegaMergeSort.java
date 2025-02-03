package pgdp.megamerge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MegaMergeSort {

    /**
     * Sorts the array using mega merge sort with div splits
     *
     * @param array the array to be sorted
     * @param div   the split factor
     * @return the sorted array
     */
    protected int[] megaMergeSort(int[] array, int div) {
        return megaMergeSort(array, div, 0, array.length);
    }

    /**
     * Sorts the array using mega merge sort with div splits in the defined range
     *
     * @param array the array to be sorted
     * @param div   the split factor
     * @param from  the lower bound (inclusive)
     * @param to    the upper bound (exclusive)
     * @return the sorted array
     */
    protected int[] megaMergeSort(int[] array, int div, int from, int to) {
        if(from >= to){
            return new int[0];
        }
        if(to-from ==1){
            return new int[]{array[from]};
        }

        int length = to - from;
        int size = length / div;

        int[][] subArrays = new int[div][];
        int start = from;
        int end;

        for (int i = 0; i < div; i++) {
            if(i < (length % div)){
                end = start +size +1;
            }else {
                end = start + size;
            }

            subArrays[i] = megaMergeSort(array, div, start, end);
            start = end;}

        return  merge(subArrays, 0, subArrays.length);
        }

    /**
     * Merges all arrays in the given range
     *
     * @param arrays to be merged
     * @param from   lower bound (inclusive)
     * @param to     upper bound (exclusive)
     * @return the merged array
     */
    protected int[] merge(int[][] arrays, int from, int to) {
        if(from >= to){
            return new int[0];
        }

        if(from+1 == to){
            return arrays[from];
        }
        int mid = (from + to) / 2;

        int[] left = merge(arrays,from,mid);
        int[] right = merge(arrays, mid, to);

        return merge(left,right);
    }

    /**
     * Merges the given arrays into one
     *
     * @param arr1 the first array
     * @param arr2 the second array
     * @return the resulting array
     */
    protected int[] merge(int[] arr1, int[] arr2) {
        // TODO
        int[] ret = new int[arr1.length+arr2.length];
        int one = 0;
        int two = 0;
        int i = 0;
        while (one < arr1.length && two < arr2.length){
            if (arr1[one] < arr2[two]){
                ret[i] = (arr1[one]);
                one++;
                i++;
            }else {
                ret[i] =(arr2[two]);
                two++;
                i++;
            }
        }
        while (one < arr1.length){
            ret[i] =(arr1[one]);
            one++;
            i++;
        }

        while (two < arr2.length){
            ret[i] = (arr2[two]);
            two++;
            i++;
        }
        return ret;
    }

    public static void main(String[] args) {
        MegaMergeSort mms = new MegaMergeSort();
        int[] arr = new int[]{1, 2, 6, 7, 4, 3, 8, 9, 0, 5};
        int[] res = mms.megaMergeSort(arr, 4);
        System.out.println(Arrays.toString(res));
    }
}
