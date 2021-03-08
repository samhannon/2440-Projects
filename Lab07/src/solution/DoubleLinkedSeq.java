package solution;

import util.DoubleNode;

/**
 * A DoubleLinkedSeq is a sequence of double numbers. The sequence can have a
 * special &quot;current element&quot;, which is specified and accessed through
 * four methods that are not available in the IntArrayBag class (start,
 * getCurrent, advance, and isCurrent).
 * 
 * Limitations:
 * 
 * Beyond Integer.MAX_VALUE element, the size method does not work.
 * 
 * @author ???
 * @version ???
 */
public class DoubleLinkedSeq implements Cloneable
{

    // your non-static fields go here.
    private int manyNodes;
    private DoubleNode head;
    private DoubleNode tail;
    private DoubleNode cursor;
    private DoubleNode precursor;
    /**
     * Initializes an empty DoubleLinkedSeq.
     * 
     * @postcondition This sequence is empty.
     */
    public DoubleLinkedSeq()
    {
        head = null;
        tail = null;
        cursor = null;
        precursor = null;
        manyNodes = 0;
    }

    /** 
     * Adds a new element to this sequence.
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
     */
    public void addBefore(double element)
    {
        if (cursor == null)
        {
            head = new DoubleNode(element, head);
            cursor = head;
            precursor = null;
        }
        else
        {          
            if (cursor == head)
            {
                head = new DoubleNode(element, head);
                cursor = head;
                precursor = null;
            }
            else
            {
                precursor.addNodeAfter(element);
                cursor = precursor.getLink();
            }    
        }   
        manyNodes++;  
        tail = DoubleNode.listPosition(head, manyNodes);
    }

    /**
     * Adds a new element to this sequence.
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
     */
    
    public void addAfter(double element)
    {
        
        if (manyNodes == 0)
        {
            head = new DoubleNode(element, head);
            cursor = head;
            precursor = null;
            tail = head;
        }
        else if (cursor == null || cursor == tail)
        {
            tail.addNodeAfter(element);
            precursor = tail;
            tail = tail.getLink();
            cursor = precursor.getLink();
        }
        else
        {
            cursor.addNodeAfter(element);
            advance();
        }
        manyNodes++;
        
//        try
//        {
//            DoubleNode add = new DoubleNode(element, null);
//            if(isCurrent())
//            {
// 
//                add.setLink(cursor.getLink());
//                cursor.setLink(add);
//                if(tail == cursor)
//                    tail = add;
//            }
//            else
//            {
//                tail.setLink(add);
//                tail = add;
//            }
//            manyNodes++;
//            advance();
//        }
//        catch(OutOfMemoryError e)
//        {
//            throw new OutOfMemoryError
//            ("Insufficient memory for a new node.");
//        }
//        if(cursor == null || cursor == tail)
//        {
//            tail = new DoubleNode(element, tail);
//            cursor = tail;
//            precursor = precursor.listPosition(head, manyNodes - 1);
//        }
//        else
//        {          
//            if (cursor == head)
//            {
//                head = new DoubleNode(element, head);
//                cursor = head;
//                precursor = null;
//            }
//            else
//            {
//                precursor.addNodeAfter(element);
//                cursor = precursor.getLink();
//            }    
//        }   
//        manyNodes++;  
//        tail = tail.listPosition(head, manyNodes);
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     * 
     * @precondition other must not be null.
     * 
     * @param other
     *            a sequence show contents will be placed at the end of this
     *            sequence.
     * 
     * @postcondition the elements from other have been placed at the end of
     *                this sequence. The current element of this sequence
     *                remains where it was, and other is unchanged.
     * 
     * @throws NullPointerException
     *             if other is null.
     */
    public void addAll(DoubleLinkedSeq other) 
    {
        DoubleNode[] copy = DoubleNode.listCopyWithTail(other.head);
        if (other.manyNodes > 0)
        {
            tail.setLink(copy[0]);
            tail = copy[1];
            manyNodes += other.manyNodes;
        }
        else
        {
            throw new NullPointerException("object is null");
        }
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
     *             if there is not current element.
     */
    public void advance() 
    {
        if (isCurrent() == true)
        {
            if (cursor == tail)
            {
                cursor = null;
                precursor = null;
            }    
            else if (cursor == head)
            {
                precursor = head;
                cursor = head.getLink();
            }
            else
            {
                precursor = precursor.getLink();
                cursor = cursor.getLink();
            }            
        }
        else 
        {
            throw new IllegalStateException("isCurrent returns false");
        }
    }

    /**
     * Creates a copy of this sequence.
     * 
     * @return a copy of this sequence. Subsequent changes to the copy will not
     *         affect the original, nor vice versa.
     * @throws RuntimeException
     *             if this class does not implement Cloneable.
     * 
     */
    public DoubleLinkedSeq clone() 
    {

        
        DoubleLinkedSeq cloney;
        DoubleNode[] arr;
        DoubleNode[] arr2;
        
        try
        {
            cloney = (DoubleLinkedSeq) super.clone();
            if (cursor == null)
            {
                arr = DoubleNode.listCopyWithTail(head);
                cloney.head = arr[0];
                cloney.tail = arr[1];
                cloney.cursor = null;
                cloney.precursor = null;
                cloney.manyNodes = manyNodes;
            }
            else if (cursor == head)
            {
                arr = DoubleNode.listCopyWithTail(head);
                cloney.head = arr[0];
                cloney.tail = arr[1];
                cloney.cursor = cloney.head;
                cloney.precursor = null;
                cloney.manyNodes = manyNodes;
            }
            else
            {
                arr = DoubleNode.listPart(head, precursor);
                arr2 = DoubleNode.listPart(cursor, tail);
                cloney.precursor.setLink(cloney.cursor);
                cloney.head = arr[0];
                cloney.precursor = arr[1];
                cloney.cursor = arr2[0];
                cloney.tail = arr2[1];
                cloney.manyNodes = manyNodes;
            }
        }
        catch (CloneNotSupportedException e) 
        {
            throw new RuntimeException("Error");       
        }             
        return cloney;   
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
     */
    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1,
            DoubleLinkedSeq s2)  
    {
        DoubleLinkedSeq s3;
        DoubleLinkedSeq s4;
//        s1.cursor = s1.tail;
//        {
//            s1.cursor.setLink(s2.cursor);
//            s1.cursor = s1.cursor.getLink();
//        }
//        s1.cursor = null;
//        s1.precursor = null;
//        s1.manyNodes += s2.manyNodes;
//        return s1;
        if (s1 != null && s2 != null)
        {
            s3 = s1.clone();
            s4 = s2.clone();
            s3.addAll(s4);
            s3.cursor = null;
            s3.precursor = null;
            return s3;
        }
        else
        {
            throw new NullPointerException("null value");
        }
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
            return cursor.getData();
        }
        else
        {
            throw new IllegalStateException("No current element");
        }
    }

    /**
     * Determines whether this sequence has specified a current element.
     * 
     * @return true if there is a current element, or false otherwise.
     */
    public boolean isCurrent()
    {
        if (cursor == null)
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
            if (manyNodes == 1)
            {
                cursor = null;
                head = null;
                tail = null;
            }
            else if (cursor == head)
            {
                head = head.getLink();
                cursor = head;
            }
            else if (cursor == tail)
            {
                precursor.removeNodeAfter();
                tail = precursor;
                cursor = null;
                precursor = null;
            }
            else
            {
                cursor = cursor.getLink();
                precursor.removeNodeAfter();
            }
            manyNodes--;
        }
        else
        {
            throw new IllegalStateException("isCurrent returns false");
        }
    }

    /**
     * Determines the number of elements in this sequence.
     * 
     * @return the number of elements in this sequence.
     */
    public int size()
    {
        return manyNodes;
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
        if (manyNodes != 0)
        {
            cursor = head;
            precursor = null;
        }
        else
        {
            cursor = null;
            precursor = null;
        }
           
    }

    /**
     * Returns a String representation of this sequence. If the sequence is
     * empty, the method should return &quot;&lt;&gt;&quot;. If the sequence has
     * one item, say 1.1, and that item is not the current item, the method
     * should return &quot;&lt;1.1&gt;&quot;. If the sequence has more than one
     * item, they should be separated by commas, for example: &quot;&lt;1.1,
     * 2.2, 3.3&gt;&quot;. If there exists a current item, then that item should
     * be surrounded by square braces. For example, if the second item is the
     * current item, the method should return: &quot;&lt;1.1, [2.2],
     * 3.3&gt;&quot;.
     * 
     * @return a String representation of this sequence.
     */
    @Override
    public String toString()
    {
        String s = "<";
        DoubleLinkedSeq scopy = clone();
        //int i = 1;
        
        if (manyNodes == 0)
        {
            return "<>";
        }
        else if (manyNodes == 1)
        {
            if (isCurrent())
            {
                return "<[" + cursor.getData() + "]>";
            }
            else
            {
                return "<" + head.getData() + ">";
            }
            
            
        }
        else
        {
            
            for (DoubleNode i = head; i != null; i = i.getLink())
            {
                if (i == cursor)
                {
                    if (i != tail)
                    {
                        s += "[" + i.getData() + "], ";
                    }
                    else
                    {
                        s += "[" + i.getData() + "]>";
                    }
                }
                else if (i == tail)
                {
                    s += i.getData() + ">";
                }
                else
                {
                    s += i.getData() + ", ";
                }
            }               
            return s;
        }
    }

    /**
     * Determines if this object is equal to the other object.
     * 
     * @param other
     *            The other object (possibly a DoubleLinkedSequence).
     * @return true if this object is equal to the other object, false
     *         otherwise. Two sequences are equal if they have the same number
     *         of elements, and each corresponding element is equal
     */
    public boolean equals(Object other)
    {
//        if (other != null)
//        {
//           
//        }
//        else
//        {
//            return false;
//        }
//        
        //for (DoubleNode i = eq.head; i != null; i = i.getLink())
            
        DoubleLinkedSeq eq = (DoubleLinkedSeq) other;
        if (eq.manyNodes == 0 && manyNodes == 0)
        {
            return true;
        }
        else if (eq.manyNodes == 1 && manyNodes == 1)
        {
            if (eq.head.equals(head))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (eq.manyNodes == manyNodes && eq.head == head && eq.tail == tail && eq.cursor == cursor)
        {
            return true;
        }
        else
        {
            return false;
        }
       
    }
}
