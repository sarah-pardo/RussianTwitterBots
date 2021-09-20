package javafoundations;

import javafoundations.*;
import javafoundations.exceptions.*;
import java.util.Vector;

public class HeapSort<T extends Comparable<T>> {

    private MaxHeap<T> maxHeap;

    public HeapSort() {
        maxHeap = new LinkedMaxHeap<T>();
    }

    /**
     * 
     * sorts the input in descending order using heap sorting
     * 
     * @param Vector<T> of comparable items to be sorted
     * @return a sorted version of the original vector
     */
    public Vector<T> sortInDescending(Vector<T> toSort) {
        //Constructing the MaxHeap using the elements of the toSort vector.
        for (int i = 0; i <toSort.size(); i++) {
            maxHeap.add(toSort.get(i));
        }

        //Creating a new vector that will store the sorted objects.
        Vector<T> sortedVector = new Vector<T>();
        
        //Deconstructing the MaxHeap. The root will be removed until the heap is empty.
        while (!maxHeap.isEmpty()) {
            sortedVector.add(maxHeap.removeMax());
        }
        return sortedVector;
    }
    

    /**
     * 
     * 
     */
    public static void main (String[] args) {
        
        HeapSort<Integer> hs1 = new HeapSort<Integer>();
        Vector<Integer> v1 = new Vector<Integer>();
        v1.add(3); v1.add(9); v1.add(7); v1.add(8); v1.add(12);
        System.out.println(hs1.sortInDescending(v1));
        
        HeapSort<String> hs2 = new HeapSort<String>();
        Vector<String> v2 = new Vector<String>();
        v2.add("caterpillar"); v2.add("abacus"); v2.add("ewe"); v2.add("bagel"); 
        v2.add("dobby");
        System.out.println(hs2.sortInDescending(v2));
        
        
        

        
        
        
    }

}