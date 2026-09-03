package control;

import collision.CollisionBox;

import java.util.Arrays;
import java.util.Map;

public class ViewHelper {

    private static class MergeSort {

        private static Integer[] sortAscMidY(Map<Integer, CollisionBox> m, Integer[] keys) {
            if (keys.length < 2) return keys;

            Integer[] left = Arrays.copyOfRange(keys, 0, keys.length / 2);
            Integer[] right = Arrays.copyOfRange(keys, keys.length / 2, keys.length);

            left = sortAscMidY(m, left);
            right = sortAscMidY(m, right);

            return merge(m, left, right);
        }

        private static Integer[] merge(Map<Integer, CollisionBox> m, Integer[] left, Integer[] right) {
            Integer[] sorted = new Integer[left.length + right.length];
            int l = 0;
            int r = 0;
            int s = 0;

            while (l < left.length && r < right.length) {

                double lVal = m.get(left[l]).getMidY();
                double rVal = m.get(right[r]).getMidY();

                // Compare the left's mid Y value with the right's mid Y value.
                double cmp = Math.abs(lVal) - Math.abs(rVal);
                if (cmp < 0.0) sorted[s++] = left[l++];
                else if (cmp > 0.0) sorted[s++] = right[r++];
                else sorted[s++] = left[l++];
            }

            while (l < left.length) sorted[s++] = left[l++];
            while (r < right.length) sorted[s++] = right[r++];

            return sorted;
        }
    }

    /**
     * @param b All collidable sprites that are to be sorted by their WorldBox's getMidY() value.
     */
    public static void updateViewOrder(Map<Integer, CollisionBox> b) {
        Integer[] sorted = MergeSort.sortAscMidY(b, b.keySet().toArray(new Integer[0]));
        for (int i = 0; i < sorted.length; ++i) { // lower mid-y = more north = render last = render below everything.
            b.get(sorted[i]).getColBox().getParent().toFront();
        }
    }
}
