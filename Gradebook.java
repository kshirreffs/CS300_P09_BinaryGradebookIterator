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
//                  Hobbes Lecture Code - Lec1_BinaryTree.java
                    // used for addStudent() method help
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Iterator;
import java.util.NoSuchElementException;

// CITE: JavaDocs - for most class and comments
/**
 * This class models a grade book for a specific course used to store student records. 
 * Every gradebook is iterable, meaning the enhanced for loop can be used to iterate through it.
 */
public class Gradebook implements Iterable<StudentRecord>{

  /**
   * Name of this course
   */
  public final String course;
  
  /**
   * Minimum passing grade for this course
   */
  public final double PASSING_GRADE;
  
  /**
   * Root node of the BST
   */
  private BSTNode<StudentRecord> root;
  
  /**
   * Total number of StudentRecords stored in this Gradebook
   */
  private int size;
  
  /**
   * Indicates whether the passing grade iterator is enabled 
   * (meaning equals true if this Gradebook is set to iterate through passing grades only)
   */
  private boolean passingGradeIteratorEnabled;
  
  /**
   * Constructs an empty Gradebook for a given course and define its passing grade. 
   * We assume that that this gradebook iterates through every stored grade 
   *    (meaning that the passingGradeIterator is not enabled by default).
   * 
   * @param course - name of the course
   * @param passingGrade - passing grade of the course
   * @throws IllegalArgumentException - if the course name is null or blank, 
   * or the passing grade is invalid. Valid passing grades are in the range [0 .. 100.0]
   */
  public Gradebook(String course, double passingGrade) {
    // checks for our exceptions
    if (course == null || course.equals("") || 
        passingGrade < 0.0 || passingGrade > 100.0) {
      throw new IllegalArgumentException();
    }
    
    // if no problems, implement our constructor per usual
    this.course = course;
    this.PASSING_GRADE = passingGrade;
    passingGradeIteratorEnabled = false;
    this.size = 0;
  }
  
  /**
   * Enables the passing grade iterator
   */
  public void enablePassingGradeIterator() {
    passingGradeIteratorEnabled = true;
  }
  
  /**
   * Disables the passing grade iterator
   */
  public void disablePassingGradeIterator() {
    passingGradeIteratorEnabled = false;
  }
  
  /**
   * Checks whether this Gradebook is empty
   * 
   * @return true if this Gradebook is empty and false otherwise
   */
  public boolean isEmpty() {
    return (size == 0 && root == null);
  }
  
  /**
   * Returns the size of this Gradebook
   * 
   * @return the total number of StudentRecord objects stored in this Gradebook
   */
  public int size() {
    return size;
  }
  
  // CITE: Hobbes lecture - used to implement recursive addStudentHelper method
  /**
   * Adds a new StudentRecord to this Gradebook. 
   * This method tries to add record to this tree and updates its size accordingly. 
   * Be sure to update the root to the BSTNode returned by the addStudentHelper() method.
   * 
   * @param record - to be added to this Gradebook
   * @throws IllegalStateException - If a match with record is already in this tree
   */
  public void addStudent(StudentRecord record) {
    if (root == null) {
      root = new BSTNode<StudentRecord>(record);
    }
    else {
      addStudentHelper(record, root); // exception, if applicable, will be thrown here
    }
    size++;
  }
  
  /**
   * Recursive helper method to add a record to the subtree rooted at node
   * 
   * @param record - new Student to add
   * @param node - root of a subtree
   * @throws IllegalStateException - if the subtree rooted at node contains a duplicate record
   * @return the new root of this BST after adding the record to this tree
   */
  protected static BSTNode<StudentRecord> addStudentHelper(StudentRecord record,
                                                           BSTNode<StudentRecord> node) {
    int compare = record.compareTo(node.getData());
    
    if (compare == 0) throw new IllegalStateException("No duplicates!");
    
    if (compare < 0) {
      // go left: base case, null
      if (node.getLeft() == null) {
        // add this value there
        node.setLeft(new BSTNode<StudentRecord>(record));
      }
      else {
        addStudentHelper(record, node.getLeft());
      }
    } else {
      // go right: base case, null
      if (node.getRight() == null) {
        // add this value there
        node.setRight(new BSTNode<StudentRecord>(record));
      } else {
        addStudentHelper(record, node.getRight());
      }
    }
    return node;
  }
  
  /**
   * Finds a StudentRecord given the associated email address
   * 
   * @param email - email address of a student
   * @return the Student associated with the email argument if there is a match, or null otherwise
   */
  public StudentRecord lookup(String email) {
    if (root == null) {
      return null;
    }
    else {
      StudentRecord target = new StudentRecord("x", email, 50.0); // will find it by email
      return lookupHelper(target, root);
    }
  }
  
  /**
   * Recursive helper method which looks for a given StudentRecord given in the BST rooted at node
   * 
   * @param target - the StudentRecord to search in the subtree rooted at node
   * @param node - root of a subtree of this BST
   * @return the StudentRecord which matches the one passed as input 
   * if a match is found in the subtree rooted at node, or null if no match found
   */
  protected static StudentRecord lookupHelper(StudentRecord target,
                                              BSTNode<StudentRecord> node) {
    int compare = target.compareTo(node.getData());
    
    if (compare == 0) {
      return node.getData();
    }
    else if (compare < 0) {
      // go left: base case, null
      if (node.getLeft() == null) {
        // the value isn't in the tree
        return null;
      }
      else {
        return lookupHelper(target, node.getLeft());
      }
    } else {
      // go right: base case, null
      if (node.getRight() == null) {
        // the value isn't in the tree
        return null;
      } else {
        return lookupHelper(target, node.getRight());
      }
    }
  }
  
  /**
   * Searches for the StudentRecord associated with the provided input email in this BST 
   * and checks whether it has a passing grade for this course. 
   * The student with the provided email passes the course if their grade is greater or equal 
   * to this Gradebook's passingGrade data field. 
   * Returns: "No match found." if no match found with email in this Gradebook 
   * If a matching StudentRecord found, this method returns: matchingStudent.toString() + ": PASS" 
   * if the student has a passing grade 
   * matchingStudent.toString() + ": FAIL" if the student does not have a passing grade 
   * 
   * For instance, "Charlie (charlie@wisc.edu) 85: PASS" "Andy (andy@wisc.edu) 56: FAIL"
   * 
   * @param email - the email of the StudentRecord to find
   * @return A String indicating whether the student having the input email 
   * has a passing or failing grade.
   */
  public String checkPassingCourse(String email) {
    if (lookup(email) == null) return "No match found.";
    else {
      StudentRecord found = lookup(email);
      if (found.getGrade() >= PASSING_GRADE) {
        return found.name + " (" + email + ") " + found.getGrade() + ": PASS";
      } else {
        // not a passing grade
        return found.name + " (" + email + ") " + found.getGrade() + ": FAIL";
      }
    }
  }
  
  /**
   * Returns the StudentRecord with the lexicographically smallest email in this BST, 
   * or null if this Gradebook is empty.
   * 
   * @return the StudentRecord with the lexicographically smallest email in this BST
   */
  protected StudentRecord getMin() {
    if (isEmpty()) return null;
    else return getMinHelper(root);
  }
  
  /**
   * Returns the smallest StudentRecord (with respect to the result of Student.compareTo() method) 
   * in the subtree rooted at node
   * 
   * @param node - root of a subtree of a binary search tree
   * @return the smallest Student in the subtree rooted at node, or null if the node is null
   */
  // CITE: Hobbes lecture - used to implement recursive getMinHelper method
  protected static StudentRecord getMinHelper(BSTNode<StudentRecord> node) {
    // find the smallest value in the subtree rooted at node: go left
    
    // base case: there is no left child
    if (node.getLeft() == null) return node.getData();
    
    // recursive case: there IS a left child
    return getMinHelper(node.getLeft());
  }
  
  /**
   * Returns the successor of a target StudentRecord 
   * (smallest value in the BST that is larger than the target), 
   * or returns null if there is no successor in this Gradebook.
   * 
   * @param target - the StudentRecord to find the successor of
   * @return the successor of the target in the Gradebook, or null if none exists
   */
  protected StudentRecord successor(StudentRecord target) {
    return successorHelper(target, root);
  }
  
  /**
   * Returns the successor of a target StudentRecord within the subtree 
   * (smallest value in the subtree that is larger than the target), 
   * or returns null if there is no successor in this subtree.
   * 
   * @param target - the StudentRecord to find the successor of
   * @param node - the subtree to search for a successor to the target
   * @return the successor of the target in the subtree rooted at node, or null if none exists
   */
  protected static StudentRecord successorHelper(StudentRecord target,
                                                 BSTNode<StudentRecord> node) {
    int compare = target.compareTo(node.getData());
    
    // case 1: target data is greater than or equal to root node, go to right subtree
    if (compare >= 0) {
      if (node.getRight() == null) {
        return null; // no proper successor
      }
      else return successorHelper(target, node.getRight());
    }
    
    // case 2: target data is less than the root node, go to left subtree
    else if (compare < 0) {
      if (node.getLeft() == null) {
        return node.getData(); // no left subtree, found our successor
      }
      else {
        // check if there are any nodes in our subtree that are the better successor
        if (successorHelper(target, node.getLeft()) != null) {
          return successorHelper(target, node.getLeft());
        } else {
          // if there are no other nodes in the subtree that are better, return our current node
          return node.getData();
        }
      }
    }
    // no successor was found using this node
    return null;
  }
  
  /**
   * Deletes a StudentRecord from this Gradebook given their email, 
   * or throws a NoSuchElementException if there is no StudentRecord with the given email.
   * 
   * @param email - the email of the student to delete
   * @throws NoSuchElementException - if there is no matching StudentRecord in this Gradebook
   */
  public void removeStudent(String email) {
    if (lookup(email) == null) throw new NoSuchElementException("No match");
    else if (size == 1){ // we only have one item to remove
      root = null;
    }
    else {
      StudentRecord toDrop = new StudentRecord("x", email, 50.0); // will find it by email
      removeStudentHelper(toDrop, root);
    }
  }
  
  /**
   * 
   * @param toDrop - the StudentRecord to be removed from this tree
   * @param node - the root of the subtree to remove the student from
   * @return the new root of the subtree after removing the matching StudentRecord
   * @throws NoSuchElementException - if there is no matching StudentRecord in this subtree
   */
  protected static BSTNode<StudentRecord> removeStudentHelper(StudentRecord toDrop,
                                                              BSTNode<StudentRecord> node) {
    // base case
    if (node == null) {
      return node;
    }
    
    int compare = toDrop.compareTo(node.getData());
    
    if (compare == 0) { // we've found our node to remove
      
      // node with only one child or no child:
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      }
      
      // node with two children:
      // our successor will be the smallest value in the right subtree
      // find our successor:
      StudentRecord successor = getMinHelper(node.getRight());
      
      // replace our data of node to remove with data of successor
      node.setData(successor);
      
      // delete the successor:
      node.setRight(removeStudentHelper(successor, node.getRight()));
    }
    
    // traverse the left side of our tree to find node to remove
    else if (compare < 0) {
      node.setLeft(removeStudentHelper(toDrop, node.getLeft()));
    } 
    
    // traverse the right side of our tree to find node to remove
    else {
      node.setRight(removeStudentHelper(toDrop, node.getRight()));
    }
    
    return node;
    
  }
  
  /**
   * Returns a String representation of the contents of this Gradebook in increasing order
   * 
   * @return an in-order String representation of this Gradebook
   */
  @Override
  public String toString() {
    return toStringHelper(root);
  }
  
  /**
   * Returns a String representation of the subtree rooted at node in increasing order
   * 
   * @param node - root of a subtree
   * @return an in-order String representation of the subtree rooted at node
   */
  protected static String toStringHelper(BSTNode<StudentRecord> node) {
    // base case: node is null
    if (node == null) {
      return "";
    }
    // recursively completes an in-order traversal of our tree
    return toStringHelper(node.getLeft()) + node.getData()
    + "\n" + toStringHelper(node.getRight());
  }
  
  /**
   * Returns a String representation of the structure of this BST. 
   * The String should print the StudentRecords in decreasing order (largest-to-smallest), 
   * and each StudentRecord should have an indentation 
   * (space from the left side of the screen to the student names) that increases by four (4) spaces 
   * for each level of depth in the tree. 
   * 
   * For instance, the root has no indentation, 
   * the root's left subtree has an indentation of 4 spaces, 
   * and the root's left subtree's right child has an indentation of 8 spaces.
   * 
   * @return a String representation of the structure of this BST
   */
  public String prettyString() {
    String original = prettyStringHelper(root, 0);
    String trimmed = original.substring(0, original.length()-1); // need to delete our extra new line
    return trimmed;
  }
  
  /**
  * Returns a decreasing-order String representation of the structure of this subtree,
  * indented by four spaces for each level of depth in the larger tree.
  *
  * @author Ashley Samuelson
  * @param node current subtree within the larger tree
  * @param depth - depth of the current node within the larger tree
  * @return a String representation of the structure of this subtree
  */
  protected static String prettyStringHelper(BSTNode<StudentRecord> node, int depth) {
    if (node == null) {
      return "";
    }
    String indent = " ".repeat(depth*4);
    return prettyStringHelper(node.getRight(), depth + 1) + indent + node.getData().name
    + "\n" + prettyStringHelper(node.getLeft(), depth + 1);
  }
  
  /**
  * Returns true if this BST has an identical layout (all subtrees equal) to the given tree.
  *
  * @author Ashley Samuelson
  * @see BSTNode#equals(Object)
  * @param node tree to compare this Gradebook to
  * @return true if the given tree looks identical to the root of this Gradebook
  */
  public boolean equalBST(BSTNode<StudentRecord> node) {
    return root == node || (root != null && root.equals(node));
  }
  
  /**
   * Returns an iterator over the student records in this gradebook in the increasing order. 
   * If the passing grade iterator is enabled, this method returns an iterator that iterates 
   * through records with passing grades only while skipping the ones that fail to pass.
   * 
   * @return an Iterator over the elements in this gradebook in proper sequence.
   */
  @Override
  public Iterator<StudentRecord> iterator() {
    if (passingGradeIteratorEnabled) {
      return new PassingGradeIterator(this);
    } else {
      return new GradebookIterator(this);
    }
  }

}
