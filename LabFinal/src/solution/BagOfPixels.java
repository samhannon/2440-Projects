package solution;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BagOfPixels 
{
    private HashMap<Double, Integer> map;
    /**
     * Initialize an empty bag with an initial capacity of 10.  
     * @postcondition
     *   This bag is empty and has an initial capacity of 10.
     **/   
    public BagOfPixels()
    {
        map = new HashMap<Double, Integer>(10);
    }

    /**
     * Initialize an empty bag with a specified initial capacity. 
     * @param initialCapacity
     *   the initial capacity of this bag
     * @precondition
     *   initialCapacity is non-negative.
     * @postcondition
     *   This bag is empty and has the given initial capacity.
     **/   
    public BagOfPixels(int initialCapacity)
    {
        if (initialCapacity >= 0) {
            map = new HashMap<Double, Integer>(initialCapacity);
        }
    }

    /**
     * Add a new intensity to this bag. 
     * @param pixel
     *   the new pixel's intensity that is being inserted
     * @postcondition
     *   A new copy of the pixel's intensity has been added to this bag.
     **/
    public void add(double pixel)
    {
        Integer value = (Integer) map.get(pixel);
        if (value == null) {
            value = 0;
        }
        map.put(pixel, value + 1);
    }

    /**
     * Accessor method to count the number of occurrences of a particular 
     * pixel intensity in this bag.
     * @param pixel
     *   the pixel intensity that needs to be counted
     * @return
     *   the number of pixels in the bag with that intensity
     **/
    public int countOccurrences(double pixel) throws NullPointerException
    {
        if (map.get(pixel) == null) {
            return 0;
        }
        else {
            return map.get(pixel);
        }
        
    }

    /**
     * Remove one copy of a specified pixel intensity from this bag.
     * @param pixel
     *   the pixel intensity to remove from the bag
     * @postcondition
     *   If target was found in the bag, then one copy of
     *   target has been removed and the method returns true. 
     *   Otherwise the bag remains unchanged and the method returns false. 
     * @return true if the target was successfully removed.
     **/
    public boolean remove(double pixel)
    {
        if (map.containsKey(pixel)) {
            map.remove(pixel);
            return true;
        }
        else {
            return false;       
        }      
    }

    /**
     * Determine the number of pixels in this bag.
     * @return
     *   the number of pixels in this bag
     **/ 
    public int size() 
    {
        Set<Map.Entry<Double, Integer>> mset = map.entrySet();
        int val = 0;
        for (Map.Entry<Double, Integer> mentry : mset) {
            val += mentry.getValue();
        }
        return val;
    }

    /** 
     * @return the average pixel intensity for the image represented by this 
     * BagOfPixels.
     */
    public double averageIntensity()
    {
        Set<Map.Entry<Double, Integer>> mset = map.entrySet();
        int val = 0;
        double total = 0, avg, tense;
        
        for (Map.Entry<Double, Integer> mentry : mset) {
            val = mentry.getValue();
            tense = mentry.getKey();
            total += val * tense;
        }
        avg = total / map.size();
        return avg;
    }




}
