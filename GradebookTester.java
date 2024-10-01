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

// CITE: JavaDocs - for most class and comments
/**
 * This class tests the Gradebook, GradebookIterator, and PassingGradeIterator classes.
 */
public class GradebookTester {

  /**
   * verify the constructor works as intended, including error throwing
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean constructorTester() {
    // TEST 1: calling constructor with valid values
    Gradebook g = new Gradebook("Math", 50.0);
    // make sure our gradebook's fields are updated appropriately
    if (!(g.course.equals("Math"))) return false;
    if (g.PASSING_GRADE != 50.0) return false;
    
    // TEST 2: calling constructor with invalid values, make sure error is thrown
    try {
      Gradebook g2 = new Gradebook("", 40.0);
      return false; // no exception was thrown when it should have been; it's a broken implementation
    } catch (IllegalArgumentException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    try {
      Gradebook g3 = new Gradebook("Science", 101.1);
      return false; // no exception was thrown when it should have been; it's a broken implementation
    } catch (IllegalArgumentException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  /**
   * verify that isEmpty works as intended when the gradebook is empty
   * verify adding things to the gradebook correctly increases the size
   * and that the ordering of all elements is correct
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean isEmptySizeAddTester() {
    
    // TEST 1: if isEmpty() and size() works on a new gradebook
    
    Gradebook g = new Gradebook("Math", 50.0);
    if (!(g.isEmpty())) return false; // should be empty
    if (g.size() != 0) return false; // should have size 0
    
    // TEST 2: if addStudent() works appropriately, incrementing size
    
    StudentRecord s = new StudentRecord("Daphne", "daphne@wisc.edu", 56.0);
    g.addStudent(s);
    if (g.size() != 1) return false;
    // make sure our tree looks as expected so far
    BSTNode<StudentRecord> expectedRoot = new BSTNode<StudentRecord>(s);
    if (!(g.equalBST(expectedRoot))) return false;
    
    
    // add until a depth of 3
    StudentRecord sLeft = new StudentRecord("Bertha", "bertha@wisc.edu", 45.0);
    StudentRecord sLeftLeft = new StudentRecord("Alicia", "alicia@wisc.edu", 60.0);
    StudentRecord sLeftRight = new StudentRecord("Candice", "candice@wisc.edu", 80.0);
    StudentRecord sRight = new StudentRecord("Susan", "susan@wisc.edu", 70.0);
    StudentRecord sRightLeft = new StudentRecord("Larry", "larry@wisc.edu", 90.0);
    StudentRecord sRightRight = new StudentRecord("Tristan", "tristan@wisc.edu", 30.0);
    g.addStudent(sLeft);
    g.addStudent(sRight);
    g.addStudent(sLeftLeft);
    g.addStudent(sLeftRight);
    g.addStudent(sRightLeft);
    g.addStudent(sRightRight);
    
    // set up our expected tree to compare against our actual one
    // setting up expected left subtree
    BSTNode<StudentRecord> expectedLeft = new BSTNode<StudentRecord>(sLeft);
    BSTNode<StudentRecord> expectedLeftLeft = new BSTNode<StudentRecord>(sLeftLeft);
    BSTNode<StudentRecord> expectedLeftRight = new BSTNode<StudentRecord>(sLeftRight);
    expectedLeft.setLeft(expectedLeftLeft);
    expectedLeft.setRight(expectedLeftRight);
    // setting up expected right subtree
    BSTNode<StudentRecord> expectedRight = new BSTNode<StudentRecord>(sRight);
    BSTNode<StudentRecord> expectedRightLeft = new BSTNode<StudentRecord>(sRightLeft);
    BSTNode<StudentRecord> expectedRightRight = new BSTNode<StudentRecord>(sRightRight);
    expectedRight.setLeft(expectedRightLeft);
    expectedRight.setRight(expectedRightRight);
    // connect the two subtrees to our root node
    expectedRoot.setLeft(expectedLeft);
    expectedRoot.setRight(expectedRight);
    
    // check actual tree, after adding our new elements, against our expected tree
    if (!(g.equalBST(expectedRoot))) return false;
    
    // TEST 3: if addStudent() throws error for a duplicate value

    try {
      g.addStudent(sRightRight); // adding a duplicate value
      return false; // no exception was thrown when it should have been; it's a broken implementation
    } catch (IllegalStateException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  /**
   * verify toString() method returns a String representation of the contents of the gradebook 
   * in increasing order
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean toStringTester() {
  
    // set up our gradebook to test on
    Gradebook g = new Gradebook("Math", 50.0);
    
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord B = new StudentRecord("B", "b@wisc.edu", 45.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 70.0);
    g.addStudent(D);
    g.addStudent(B);
    g.addStudent(E);
    g.addStudent(A);
    g.addStudent(C);
    
    // TEST 1: expected string matches our actual toString method's return value
    String actual = g.toString();
    String expected = "A (a@wisc.edu): 56.0\n"
                    + "B (b@wisc.edu): 45.0\n"
                    + "C (c@wisc.edu): 60.0\n"
                    + "D (d@wisc.edu): 80.0\n"
                    + "E (e@wisc.edu): 70.0\n"; // should be in increasing order  
    if (!actual.equals(expected)) return false; // make sure our actual is as expected
    
    return true;
  }
  
  /**
   * verify prettyString() method returns a decreasing-order 
   * String representation of the structure of this subtree, 
   * indented by four spaces for each level of depth in the larger tree.
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean prettyStringTester() {
    
    // set up our gradebook to test on
    Gradebook g = new Gradebook("Math", 50.0);
    
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord B = new StudentRecord("B", "b@wisc.edu", 45.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 70.0);
    g.addStudent(D);
    g.addStudent(B);
    g.addStudent(E);
    g.addStudent(A);
    g.addStudent(C);
    
    // TEST 1: expected string matches our actual prettyString method's return value
    
    String actual = g.prettyString();
    String expected = "    E\nD\n        C\n    B\n        A"; // should have varying indents 
    if (!actual.equals(expected)) return false; // make sure our actual is as expected
    
    return true;
  }
  
  /**
   * verifies that lookup() finds a StudentRecord given the associated email address
   * tests when given email for lookup() has no match
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean lookupTester() {
    
    // set up our gradebook to test on
    Gradebook g = new Gradebook("Math", 50.0);
    
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord B = new StudentRecord("B", "b@wisc.edu", 45.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 70.0);
    g.addStudent(D);
    g.addStudent(B);
    g.addStudent(E);
    g.addStudent(A);
    g.addStudent(C);
    
    // TEST 1: verify lookup() finds given StudentRecords
    StudentRecord found = g.lookup("a@wisc.edu");
    if (!found.equals(A)) return false;
    
    StudentRecord found2 = g.lookup("c@wisc.edu");
    if (!found2.equals(C)) return false;
    
    StudentRecord found3 = g.lookup("d@wisc.edu");
    if (!found3.equals(D)) return false;
    
    // TEST 2: verify lookup() returns null when there is no match for a given email
    StudentRecord noMatch = g.lookup("z@wisc.edu");
    if (noMatch != null) return false;
    
    return true;
  }
  
  /**
   * verifies that getMin() works as intended
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean getMinTester() {
    // set up our gradebook to test on
    Gradebook g = new Gradebook("Math", 50.0);
    
    // TEST 1: getMin() returns null for an empty gradebook
    if (g.getMin() != null) return false;
    
    // TEST 2: getMin() returns the smallest value after adding items to gradebook
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord B = new StudentRecord("B", "b@wisc.edu", 45.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 70.0);
    g.addStudent(D);
    g.addStudent(B);
    g.addStudent(E);
    g.addStudent(A);
    g.addStudent(C);
    
    if (!(g.getMin().equals(A))) return false; // our smallest value is A
    
    return true;
  }
  
  /**
   * verifies that removeStudent() works as intended
   * tests error throwing
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean removeStudentTester() {
    // set up our gradebook to test on
    Gradebook g = new Gradebook("Math", 50.0);
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord B = new StudentRecord("B", "b@wisc.edu", 45.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord F = new StudentRecord("F", "f@wisc.edu", 80.0);
    StudentRecord G = new StudentRecord("G", "g@wisc.edu", 80.0);
    g.addStudent(D);
    g.addStudent(B);
    g.addStudent(A);
    g.addStudent(C);
    g.addStudent(F);
    g.addStudent(G);
    
    // TEST 1a: removeStudent() correctly removes a student with one child
    g.removeStudent("f@wisc.edu");
    
    // set up our expected tree to compare against our actual one
    BSTNode<StudentRecord> expectedRoot = new BSTNode<StudentRecord>(D);
    // setting up expected left subtree
    BSTNode<StudentRecord> expectedLeft = new BSTNode<StudentRecord>(B);
    BSTNode<StudentRecord> expectedLeftLeft = new BSTNode<StudentRecord>(A);
    BSTNode<StudentRecord> expectedLeftRight = new BSTNode<StudentRecord>(C);
    expectedLeft.setLeft(expectedLeftLeft);
    expectedLeft.setRight(expectedLeftRight);
    // setting up expected right subtree
    BSTNode<StudentRecord> expectedRight = new BSTNode<StudentRecord>(G);
    // connect the two subtrees to our root node
    expectedRoot.setLeft(expectedLeft);
    expectedRoot.setRight(expectedRight);
    
    // check actual tree, after removing an element, against our expected tree
    if (!(g.equalBST(expectedRoot))) return false;
    
    // TEST 1b: removeStudent() correctly removes a student with two children
    g.removeStudent("b@wisc.edu");
    
    // set up our expected tree to compare against our actual one
    BSTNode<StudentRecord> expectedRoot2 = new BSTNode<StudentRecord>(D);
    // setting up expected left subtree
    BSTNode<StudentRecord> expectedLeft2 = new BSTNode<StudentRecord>(C);
    BSTNode<StudentRecord> expectedLeftLeft2 = new BSTNode<StudentRecord>(A);
    expectedLeft2.setLeft(expectedLeftLeft2);
    // setting up expected right subtree
    BSTNode<StudentRecord> expectedRight2 = new BSTNode<StudentRecord>(G);
    // connect the two subtrees to our root node
    expectedRoot2.setLeft(expectedLeft2);
    expectedRoot2.setRight(expectedRight2);
    
    // check actual tree, after removing an element, against our expected tree
    if (!(g.equalBST(expectedRoot2))) return false;
    
    // TEST 1c: removeStudent() correctly removes a student with no child
    g.removeStudent("a@wisc.edu");
    
    // set up our expected tree to compare against our actual one
    BSTNode<StudentRecord> expectedRoot3 = new BSTNode<StudentRecord>(D);
    // setting up expected left subtree
    BSTNode<StudentRecord> expectedLeft3 = new BSTNode<StudentRecord>(C);
    // setting up expected right subtree
    BSTNode<StudentRecord> expectedRight3 = new BSTNode<StudentRecord>(G);
    // connect the two subtrees to our root node
    expectedRoot3.setLeft(expectedLeft3);
    expectedRoot3.setRight(expectedRight3);
    
    // check actual tree, after removing an element, against our expected tree
    if (!(g.equalBST(expectedRoot3))) return false;
    
    // TEST 2: removing a value that doesn't exist should throw an error
    try {
      g.removeStudent("z@wisc.edu");
      return false; // no exception was thrown when it should have been; it's a broken implementation
    } catch (NoSuchElementException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  /**
   * verifies that successor() works as intended
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean successorTester() {
    // set up our gradebook to test on
    Gradebook g = new Gradebook("Math", 50.0);
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 30.0);
    StudentRecord G = new StudentRecord("G", "g@wisc.edu", 80.0);
    g.addStudent(D);
    g.addStudent(C);
    g.addStudent(A);
    g.addStudent(G);
    g.addStudent(E);
    
    // TEST 1: successor() correctly finds the smallest value in tree larger than the target
    StudentRecord target = new StudentRecord("B", "b@wisc.edu", 45.0);
    if (!g.successor(target).equals(C)) return false; // expected successor to B is C

    if (!g.successor(A).equals(C)) return false; // expected successor to A is C
    
    if (!g.successor(D).equals(E)) return false; // expected successor to D is E
    
    // TEST 2: successor() returns null when there is expected successor
    if (g.successor(G) != null) return false; // no expected successor to G
    
    StudentRecord target2 = new StudentRecord("H", "h@wisc.edu", 75.0);
    if (g.successor(target2) != null) return false; // no expected successor to H
    
    return true;
  }
  
  /**
   * verifies that GradebookIterator constructor, hasNext() and next() work as intended
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean iteratorTester() {
    
    // TEST 1: calling constructor with valid values
    // first create gradebook to iterate through
    Gradebook g = new Gradebook("Math", 50.0);
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 30.0);
    StudentRecord G = new StudentRecord("G", "g@wisc.edu", 80.0);
    g.addStudent(D);
    g.addStudent(C);
    g.addStudent(A);
    g.addStudent(G);
    g.addStudent(E);
    
    // create our gradebook iterator
    GradebookIterator gI;
    try {
      gI = new GradebookIterator(g);
    } catch (Exception e) {
      return false; // no exceptions should be thrown, otherwise our constructor is broken
    }
    
    // TEST 2: calling hasNext() when we have a successor to our current node
    if (gI.hasNext() == false) return false; // should be true (current = A)
    
    // TEST 3: calling next() returns the successor value of a node
    if (!gI.next().equals(A)) return false; // value should be A (first time next() is called)
    if (!gI.next().equals(C)) return false; // value after A should be C (current = C)
    if (!gI.next().equals(D)) return false; // value after C should be D (current = D)
    if (!gI.next().equals(E)) return false; // value after D should be E (current = E)
    if (!gI.next().equals(G)) return false; // value after E should be G (current = G)
    
    // TEST 4: calling hasNext() and next() when there are no successor's to current node
    if (gI.hasNext() == true) return false; // we are on the last item
    
    try {
      gI.next();
      return false; // no exception was thrown when it should have
    } catch (NoSuchElementException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  /**
   * verifies that PassingGradebookIterator constructor, 
   * advanceToNextPassingGrade(), hasNext() and next() work as intended
   * 
   * @return true if all test scenarios pass, false otherwise
   */
  public static boolean passingIteratorTester() {
    // TEST 1: calling constructor with valid values
    // first create gradebook to iterate through
    Gradebook g = new Gradebook("Math", 60.0);
    StudentRecord A = new StudentRecord("A", "a@wisc.edu", 56.0);
    StudentRecord C = new StudentRecord("C", "c@wisc.edu", 60.0);
    StudentRecord D = new StudentRecord("D", "d@wisc.edu", 80.0);
    StudentRecord E = new StudentRecord("E", "e@wisc.edu", 30.0);
    StudentRecord G = new StudentRecord("G", "g@wisc.edu", 80.0);
    StudentRecord H = new StudentRecord("H", "h@wisc.edu", 50.0);
    g.addStudent(D);
    g.addStudent(C);
    g.addStudent(A);
    g.addStudent(G);
    g.addStudent(E);
    g.addStudent(H);
    
    // create our passing gradebook iterator
    PassingGradeIterator gI;
    try {
      gI = new PassingGradeIterator(g);
    } catch (Exception e) {
      return false; // no exceptions should be thrown, otherwise our constructor is broken
    }
    
    // TEST 2: calling hasNext() when we have a successor to our current (next) node
    if (gI.hasNext() == false) return false; // should be true (next = C)
    
    // TEST 3: calling next() should advance to next passing grade and return it
    // automatically checks implementation of advanceToNextPassingGrade()
    // our current "next" value should be C -- the first passing grade
    if (!gI.next().equals(C)) return false; // first passing value is C
    if (!gI.next().equals(D)) return false; // passing value after C should be D (next = D)
    if (!gI.next().equals(G)) return false; // passing value after D should be G (next = G)    
    
    // TEST 4: calling next() when there are no successor's to current node
    try {
      gI.next();
      return false; // no exception was thrown when it should have
    } catch (NoSuchElementException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  /**
   * Main method
   * 
   * @param args input arguments if any
   */
  /*
  public static void main(String[] args) {
    System.out.println("constructorTester(): " + constructorTester());
    System.out.println("isEmptySizeAddTester(): " + isEmptySizeAddTester());
    System.out.println("toStringTester(): " + toStringTester());
    System.out.println("prettyStringTester(): " + prettyStringTester());
    System.out.println("lookupTester(): " + lookupTester());
    System.out.println("getMinTester(): " + getMinTester());
    System.out.println("removeStudentTester(): " + removeStudentTester());
    System.out.println("successorTester(): " + successorTester());
    System.out.println("iteratorTester(): " + iteratorTester());
    System.out.println("passingIteratorTester(): " + passingIteratorTester());
  }
  */
}
