package org.example.sort;

import lombok.RequiredArgsConstructor;
import org.example.entity.Ticket;
import org.example.managers.Collection;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
@RequiredArgsConstructor
@Component
public class TicketSorter extends Sorter<Ticket>{
    private final Collection collection ;




    public List<Ticket> getSortedList(List<Ticket> list,int n,List<Sort> sorts){

        var comparator = sorts.stream().map( sort ->
             ComparatorHashMap.getInstance().getComparatorByFieldAndOrder(sort.getField(), sort.getOrder()))
                .reduce(Comparator::thenComparing).get();
        System.out.println(comparator);
        return sortFirstN(list,n,comparator);
    }
    @Override
    public List<Ticket> sortFirstN(List<Ticket> items, int n, Comparator comparator) {


        if (n >= items.size()) {
            items.sort(comparator);
        } else {
            selectAndSortTopN(items, n, comparator);
        }
        return items;
    }
}
