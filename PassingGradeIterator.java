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

import java.util.NoSuchElementException;

//CITE: JavaDocs - for most class and comments
/**
* Iterator for traversing the records in a Gradebook in increasing order, 
* while also skipping over StudentRecords who do not have a passing grade.
* This iterator iterates through the StudentRecord objects with passing grades, only.
*/
public class PassingGradeIterator extends GradebookIterator {

  /**
   * Reference to the current StudentRecord with a passing grade to be returned by this iterator
   */
  private StudentRecord next;
  
  /**
   * Passing grade
   */
  private double passingGrade;
  
  /**
   * Constructs a new PassingGradeIterator to iterate over the StudentRecords with passing grades 
   * in a given Gradebook (StudentRecords with NO passing grades are skipped by this iterator).
   * 
   * This iterator sets the passing grade to the gradebook passing grade and advances the iterator 
   * to the first student record with passing grade in the iteration 
   * by calling the advanceToNextPassingGrade() helper method.
   * 
   * @param gradebook - Gradebook to iterate over.
   */
  public PassingGradeIterator(Gradebook gradebook) {
    super(gradebook);
    passingGrade = gradebook.PASSING_GRADE;
    // advance iterator to first student record with passing grade
    next = gradebook.getMin(); // temporary placement
    advanceToNextPassingGrade();
  }
  
  /**
   * Private helper method that advances iterator to the next StudentRecord with a passing grade. 
   * Then, it stores it into next. 
   * If no more StudentRecord with a passing grade are available in the iteration, 
   * this method sets next to null. 
   * This method uses super.hasNext() and super.next() in a while loop to operate.
   */
  private void advanceToNextPassingGrade() {
    boolean notFoundYet = true;
    
    while (super.hasNext() && notFoundYet) {      
      StudentRecord nextVal = super.next();
      if (nextVal.getGrade() >= passingGrade) {
        next = nextVal;
        notFoundYet = false; // will make sure we exit the while loop after finding passing grade
      }
    }
    
    // if we never found another passing grade, we set next to null
    if (notFoundYet) next = null;
  }
  
  /**
   * Returns true if the iteration has more elements (if next is not null). 
   * (In other words, returns true if next() returns an element rather than throwing exception.)
   * 
   * @return true if the iteration has more elements
   */
  @Override
  public boolean hasNext() {
    if (next != null) return true;
    else return false;
  }
  
  /**
   * Returns the next StudentRecord object with a passing grade in the iteration 
   * and advances the iteration to the next record with passing grade.
   * 
   * @throws NoSuchElementException - if the iteration has no more elements 
   *    (meaning has no more StudentRecord objects with a passing grade)
   * @return the next StudentRecord with a passing grade in the iteration
   */
  @Override
  public StudentRecord next() {
    if (next == null) throw new NoSuchElementException("No more objects with a passing grade");
    else {
      StudentRecord toReturn = next;
      advanceToNextPassingGrade();
      return toReturn;
    }  
  }


}