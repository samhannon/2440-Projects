package solution;
/**
 * A DoubleArraySeq keeps track of a sequence of double numbers. The sequence
 * can have a special current element, which is specified and accessed through
 * four methods that are not available in the IntArrayBag class (start,
 * getCurrent, advance, and isCurrent.
 * 
 * Note that the addAfter and addBefore methods do not allocate more memory
 * until the current capacity of the sequence is reached.
 * 
 * Note also that any attempt to increase the capacity of any sequence beyond
 * Integer.MAX_VALUE will cause the sequence to fail with an arithmetic
 * overflow.
 * 
 * Limitations:
 * 
 * 1. The capacity of a sequence change change after it is created, but the
 * maximum capacity is limited by the amount of free memory on the machine. The
 * constructor, addAfter, addBefore, clone, and concatenation will result in an
 * OutOfMemoryError when free memory is exhausted. 2. A sequence's capacity
 * cannot exceed the largest integer 2,147,483,647 (Integer.Max_VALUE). Any
 * attempt to create a larger capacity results in failure due to an arithmetic
 * overflow.
 * 
 * @author Michael Main
 * @version 4th edition 2012
 */

public class DoubleArraySeq implements Cloneable
{
    /*
     * Invariant of the DoubleArraySeq class: 1. The number of elements in the
     * sequences is in the instance variable manyItems. 2. For an empty sequence
     * (with no elements), we do not care what is stored in any of data; for a
     * non-empty sequence, the elements of the sequence are stored in data[0]
     * through data[manyItems - 1], and we don't care what's in the rest of
     * data. 3. If there is a current element, then it lies in
     * data[currentIndex]; if there is no current element, then currentIndex
     * equals manyItems.
     */

    /**
     * The default capacity of a newly constructed DoubleArraySeq. (10 elements)
     */
    public final static int DEFAULT_CAPACITY = 10;

    /** The elements of this sequence. */
    // Hint: private double[] data;
    private double[] data;
    
    /**
     * The current length of this sequence (i.e., how many items are in this
     * sequence).
     */
    // Hint: private int manyItems;
    private int manyItems;
    /** The index of the current element in this sequence. */
    // Hint: private int currentIndex;
    private int currentIndex;
    /**
     * Initializes an empty sequence with an initial capacity of
     * DEFAULT_CAPACITY.
     * 
     * @postcondition This sequence is empty and has an initial capacity of
     *                DEFAULT_CAPACITY.
     * 
     * @throws OutOfMemoryError
     *             if there is insufficient memory for: new
     *             double[DEFAULT_CAPACITY].
     */
    public DoubleArraySeq() throws OutOfMemoryError
    {
        data = new double[DEFAULT_CAPACITY];
        manyItems = 0;
        currentIndex = 0;
    }

    /**
     * Initializes an empty sequence with the specified initial capacity.
     * 
     * @postcondition This sequence is empty and has an initial capacity of
     *                initialCapacity.
     * 
     * @param initialCapacity
     *            initial size of the array
     * @throws OutOfMemoryError
     *             if there is insufficient memory for: new
     *             double[initialCapacity].
     */
    public DoubleArraySeq(int initialCapacity) throws OutOfMemoryError
    {
        if (initialCapacity > 0)
        {
            data = new double[initialCapacity];
            manyItems = 0;   
            currentIndex = 0;
        }
        else
        {
            throw new IllegalArgumentException(
            "The capacity cannot be negetive");
        }    
    }

    /**
     * Adds a new element to this sequence after the current element. If this
     * new element would take this beyond its current capacity, then the
     * capacity is increased before adding the new element.
     * 
     * @param element
     *            the new element that is being added to this sequence.
     * 
     * @postcondition a new copy of the element has been added to this sequence.
     *                If there was a current element, then this method places
     *                the new element after the current element. If there was no
     *                current element, then this method places the new element
     *                at the end of this sequence. The newly added element
     *                becomes the new current element of this sequence.
     * 
     * @throws OutOfMemoryError
     *             if there is insufficient memory to increase the size of this
     *             sequence.
     */
    public void addAfter(double element) throws OutOfMemoryError
    {
        if (manyItems == getCapacity())
        {
            ensureCapacity(2 * manyItems + 10);
        }
        
        
        if (isCurrent())
        {
            
            for (int i = manyItems - 1; i > currentIndex; i--)
            {
                data[i + 1] = data[i];
            }
            
            data[currentIndex + 1] = element; 
            advance();
        }
        else 
        {
            data[manyItems] = element;
            currentIndex = manyItems;           
        }
        
        manyItems++;
        
        
    }

    /**
     * Adds a new element to this sequence before the current element. If this
     * new element would take this beyond its current capacity, then the
     * capacity is increased before adding the new element.
     * 
     * @param element
     *            the new element that is being added to this sequence.
     * 
     * @postcondition a new copy of the element has been added to this sequence.
     *                If there was a current element, then this method places
     *                the new element before the current element. If there was
     *                no current element, then this method places the new
     *                element at the front of this sequence. The newly added
     *                element becomes the new current element of this sequence.
     * 
     * @throws OutOfMemoryError
     *             if there is insufficient memory to increase the size of this
     *             sequence.
     */
    public void addBefore(double element) throws OutOfMemoryError
    {
//        try{
//            ensureCapacity(manyItems + 1);
//        
//        
//        for(int i = manyItems; i > currentIndex-1; i--)
//        {
//            data[i] = data[i-1];
//        }
//             
//          data[currentIndex] = element;
//          
//          manyItems++;
//        }
//          catch(ArrayIndexOutOfBoundsException e)
//          {
//              throw new RuntimeException("Out of bounds :(");
//          }
        if (manyItems == getCapacity())
        {
            ensureCapacity(2 * manyItems + 10);
        }
        
        if (isCurrent())
        {
            for (int i = manyItems - 1; i >= currentIndex; i--)
            {
                data[i + 1] = data[i];
            }
            
            data[currentIndex] = element; 
        }
        else
        {
            for (int i = manyItems - 1; i >= 0; i--)
            {
                data[i + 1] = data[i];
            }
            
            data[0] = element;
            currentIndex = 0;
        }
        manyItems++;
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     * 
     * @precondition addend must not be null.
     * 
     * @param addend
     *            a sequence whose contents will be placed at the end of this
     *            sequence.
     * 
     * @postcondition the elements from addend have been placed at the end of
     *                this sequence. The current element of this sequence
     *                remains where it was, and addend is unchanged.
     * 
     * @throws NullPointerException
     *             if addend is null.
     * @throws OutOfMemoryError
     *             if there is insufficient memory to increase the capacity of
     *             this sequence.
     */
    public void addAll(DoubleArraySeq addend)
        throws OutOfMemoryError
    {
        if (addend == null)  
        {
            throw new NullPointerException(
            "DoubleArraySeq is null");
        }
        ensureCapacity(manyItems + addend.manyItems);

        System.arraycopy(addend.data, 0, data, manyItems, addend.manyItems);
        
        manyItems += addend.manyItems;   
    }

    /**
     * Move forward so that the current element is now the next element in the
     * sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @postcondition If the current element was already the end element of this
     *                sequence (with nothing after it), then there is no longer
     *                any current element. Otherwise, the new element is the
     *                element immediately after the original current element.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public void advance() 
    {
        if (isCurrent())
        {
            currentIndex++;        
        }
        else
        {
            throw new IllegalStateException(
                "No current element, cannot advance");
        }
    }

    /**
     * Creates a copy of this sequence.
     * 
     * @return a copy of this sequence. Subsequent changes to the copy will not
     *         affect the original, nor vice versa.
     * 
     * @throws OutOfMemoryError
     *             if there is insufficient memory for creating the clone
     *             object.
     * @throws RuntimeException
     *             if this class does not implement Cloneable.
     */
    @Override
    public DoubleArraySeq clone()
        throws OutOfMemoryError, RuntimeException
    {
        DoubleArraySeq clone;
        
        try
        {
            clone = (DoubleArraySeq) super.clone();
        }
        catch (CloneNotSupportedException e) 
        {
            throw new RuntimeException("Error");       
        }
        
        clone.data = data.clone();      
        return clone; 
    }

    /**
     * Creates a new sequence that contains all the elements from s1 followed by
     * all of the elements from s2.
     * 
     * @precondition neither s1 nor s2 are null.
     * 
     * @param s1
     *            the first of two sequences.
     * @param s2
     *            the second of two sequences.
     * 
     * @return a new sequence that has the elements of s1 followed by the
     *         elements of s2 (with no current element).
     * 
     * @throws NullPointerException
     *             if s1 or s2 are null.
     * @throws OutOfMemoryError
     *             if there is insufficient memory for the new sequence.
     */
    public static DoubleArraySeq concatenation(DoubleArraySeq s1,
            DoubleArraySeq s2)
        throws NullPointerException, OutOfMemoryError
    {
        
        DoubleArraySeq s3 = new DoubleArraySeq(s1.manyItems + s2.manyItems);
        s3.manyItems = s1.manyItems + s2.manyItems;  
        s3.currentIndex = s1.currentIndex + s2.currentIndex;
        //System.arraycopy(s1, 0, s3, 0, s1.manyItems);
        //System.arraycopy(s2, 0, s3, s1.manyItems, s2.manyItems);
        
        //s3.manyItems = (s1.manyItems + s2.manyItems);
        
        return s3;         
    }

    /**
     * Change the current capacity of this sequence to be at least the specified
     * value.
     * 
     * @param minimumCapacity
     *            the new minimum capacity for this sequence.
     * 
     * @postcondition This sequence's capacity has been changed to be at least
     *                minimumCapacity, but no less than size.
     * 
     * @throws OutOfMemoryError
     *             if there is insufficient memory for the a new array: new
     *             double[minimumCapacity].
     */
    public void ensureCapacity(int minimumCapacity)
        throws OutOfMemoryError
    {
        double biggerArray[];
        if (data.length < minimumCapacity)
        {
            biggerArray = new double[minimumCapacity];
            System.arraycopy(data, 0, biggerArray, 0, manyItems);
            data = biggerArray;
        }
    }

    /**
     * Determines the current capacity of this sequence.
     * 
     * @return the current capacity of this sequence.
     */
    public int getCapacity()
    {
        return data.length;
    }

    /**
     * Returns a copy of the current element in this sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @return the current element of this sequence.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public double getCurrent() 
    {
        if (isCurrent())
        {
            return data[currentIndex];
        }
        else
        {
            throw new IllegalStateException("ISE");
        }
    }

    /**
     * Determines whether this sequence has specified a current element.
     * 
     * @return true if there is a current element, or false otherwise.
     */
    public boolean isCurrent()
    {
        
        if (currentIndex == manyItems)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Removes the current element from this sequence.
     * 
     * @precondition isCurrent() returns true.
     * 
     * @postcondition The current element has been removed from this sequence,
     *                and the following element (if there is one) is now the new
     *                current element. If there was no following element, then
     *                there is now no current element.
     * 
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public void removeCurrent()  
    {
        if (isCurrent())
        {

            for (int i = currentIndex; i < manyItems - 1; i++)
            {
                data[i] = data[i + 1];
            }
            data[manyItems - 1] = 0.0;
            manyItems--;
        }
        else
        {
            throw new IllegalStateException("ISE");
        }  
    }

    /**
     * Determines the number of elements in this sequence.
     * 
     * @return the number of elements in this sequence.
     */
    public int size()
    {
        return manyItems;
    }

    /**
     * Sets the current element at the front of this sequence.
     * 
     * @postcondition If this sequence is not empty, the front element of this
     *                sequence is now the current element; otherwise, there is
     *                no current element.
     */
    public void start()
    {
        if (data[0] != 0)
        {
            currentIndex = 0;
        }
            
    }

    /**
     * Reduces the capacity of the sequence to the number of elements currently
     * in the sequence.
     * 
     * @postcondition This sequence's capacity has been changed to its current
     *                size.
     * 
     * @throws OutOfMemoryError
     *             if there is insufficient memory for altering the capacity.
     */
    public void trimToSize()
        throws OutOfMemoryError
    {
        double[] trimmed;
        
        if (data.length != manyItems)
        {
            trimmed = new double[manyItems];
            System.arraycopy(data, 0, trimmed, 0, manyItems);
            data = trimmed;
        }
    }

    /**
     * Returns a String representation of this sequence. If the sequence is
     * empty, the method should return <>. If the sequence has one item, say
     * 1.1, and that item is not the current item, the method should return
     * <1.1>. If the sequence has more than one item, they should be separated
     * by commas, for example: <1.1, 2.2, 3.3>. If there exists a current item,
     * then that item should be surrounded by square braces. For example, if the
     * second item is the current item, the method should return: <1.1, [2.2],
     * 3.3>.
     * 
     * @return a String representation of this sequence.
     */
    @Override
    public String toString()
    {
        String s = "<";
        
        int i = 0;
        if (manyItems == 0)
        {
            s += ">";
            return s;
        }
        else if (manyItems == 1)
        {
            if (currentIndex == 0)
            {
                s += "[" + data[i] + "]>";
            }
            else
            {
                s += data[i] + ">";
            }
            
            return s;
        }
        else
        {
            while (i < manyItems)
            {
                if (i == currentIndex)
                {
                    if (i < manyItems - 1)
                    {
                        s += "[" + data[i] + "], ";
                        i++;
                    }
                    else
                    {
                        s += "[" + data[i] + "]>";
                        i++;
                    }
                }
                else if (i == manyItems - 1)
                {
                    s += data[i] + ">";
                    i++;
                }
                else
                {
                    s += data[i] + ", ";
                    i++;
                }
            }               
            return s;
        }
        
    }

    /**
     * Determines if this object is equal to the other object. Two sequences are
     * the same if they have the same number of elements, and the elements at
     * each position in the sequence are the same. (It does not matter if the
     * two sequences have the same capacity or not.)
     * 
     * @param other
     *            The object to compare.
     * 
     * @return true if this object is equal to the other object, false
     *         otherwise.
     */
    @Override
    public boolean equals(Object other)
    {      
        try 
        {



            DoubleArraySeq eq = (DoubleArraySeq) other;

            if (this == eq)
            {
                return true;       
            }           
            if (other == null)
            {
                return false;
            }
            if (manyItems == 0 && eq.manyItems == 0)
            {
                return true;
            }
            if (manyItems == eq.manyItems)
            {
                if (getCurrent() != eq.getCurrent())
                {
                    return false;
                }
                else
                {
                    for (int i = 0; i < manyItems; i++)
                    {
                        if (data[i] != eq.data[i])
                        {
                            return false;
                        }
                    }

                    return true;
                }

            }
            return false;
        }
        catch (Exception e_) 
        {
            return false;
        }

        
    }
}
