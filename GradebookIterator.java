//////////////// FILE HEADER //////////////////////////
//
// Title:    P09 Binary Gradebook
// Course:   CS 300 Spring 2024
//
// Author:   Katelyn Shirreffs
// Email:    kshirreffs@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         NONE
// Online Sources:  JavaDocs - https://cs300-www.cs.wisc.edu/sp24/p09/doc/package-summary.html
                    // for most commenting
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

//CITE: JavaDocs - for most class and comments
/**
* Iterator for traversing the records in a Gradebook in increasing order without skipping any element.
*/
public class GradebookIterator implements Iterator<StudentRecord>{
  
  /**
   * Current StudentRecord reference. 
   * It also referenced the next StudentRecord to be returned by this iterator.
   */
  private StudentRecord current;
  
  /**
   * Gradebook to iterate over
   */
  private Gradebook gradebook;
  
  /**
   * Creates a new GradebookIterator to iterate over the given Gradebook and 
   * initializes current to references the minimum student record in the gradebook.
   * 
   * @param gradebook - Gradebook to iterate over.
   */
  public GradebookIterator(Gradebook gradebook) {
    this.gradebook = gradebook;
    current = gradebook.getMin();
  }
  
  /**
   * Returns true if the iteration has more elements (if current is not null). 
   * (In other words, returns true if next() would return an element 
   * rather than throwing an exception.)
   * 
   * @return true if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    if (current != null) return true;
    else return false;
  }

  /**
   * Returns the next element in the iteration 
   * (meaning the current StudentRecord from the Gradebook), 
   * and advances the current pointer to the next StudentRecord in the gradebook 
   * in the increasing order.
   * 
   * @throws NoSuchElementException - if the iteration has no more elements 
   *    (meaning if hasNext() returns false)
   * @return the next element in the iteration (current StudentRecord)
   */
  @Override
  public StudentRecord next() {
    if (!hasNext()) throw new NoSuchElementException("No more elements");
    else {
      StudentRecord toReturn = current;
      current = gradebook.successor(current); // move to the next value
      return toReturn;
    }
  }

}
