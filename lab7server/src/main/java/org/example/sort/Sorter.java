package org.example.sort;

import org.example.entity.Ticket;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Sorter<T> {

    public abstract List<Ticket> sortFirstN(List<Ticket> items, int n, Comparator comparator);

    protected  <T> void selectAndSortTopN(List<T> list, int n, Comparator<? super T> comparator) {
        // Найти n-й наименьший элемент
        T nthElement = quickSelect(list, 0, list.size() - 1, n - 1, comparator);

        // Сортировка первых n наименьших элементов
        List<T> sublist = list.subList(0, n);
        sublist.sort(comparator);
    }

    protected <T> T quickSelect(List<T> list, int left, int right, int n, Comparator<? super T> comparator) {
        if (left == right) {
            return list.get(left);
        }

        int pivotIndex = partition(list, left, right, comparator);

        if (n == pivotIndex) {
            return list.get(n);
        } else if (n < pivotIndex) {
            return quickSelect(list, left, pivotIndex - 1, n, comparator);
        } else {
            return quickSelect(list, pivotIndex + 1, right, n, comparator);
        }
    }

    protected <T> int partition(List<T> list, int left, int right, Comparator<? super T> comparator) {
        T pivot = list.get(right);
        int i = left;

        for (int j = left; j < right; j++) {
                var res = comparator.compare(list.get(j), pivot);

                if (res < 0) {
                Collections.swap(list, i, j);
                i++;
            }

        }
        Collections.swap(list, i, right);
        return i;
    }

}
