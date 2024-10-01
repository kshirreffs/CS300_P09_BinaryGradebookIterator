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

// CITE: JavaDocs - for most class and comments
/**
 * This class models student course records.
 */
public class StudentRecord implements Comparable<StudentRecord> {
  
  /**
   * This student's name
   */
  public final String name;
  
  /**
   * This student's email address
   */
  public final String email;
  
  /**
   * This student's current grade
   */
  private double grade;
  
  /**
   * Constructs a new StudentRecord given the name, email, and grade of a given student
   * 
   * @param name - the student's name
   * @param email - the student's email
   * @param grade - the student's grade
   * @throws IllegalArgumentException - if the name or email is null or blank 
   * or if grade is not in the range 0.0 .. 100.0 (inclusive)
   */
  public StudentRecord(String name, String email, double grade) {
    // checks for our exceptions
    if (name == null || name.equals("") || 
        email == null || email.equals("") || 
        grade < 0.0 || grade > 100.0) {
      throw new IllegalArgumentException();
    }
    
    // if no problems, implement our constructor per usual
    this.name = name;
    this.email = email;
    this.grade = grade;
  }
  
  /**
   * Returns this student's current grade
   * 
   * @return this student's current grade
   */
  public double getGrade() {
    return grade;
  }
  
  /**
   * Updates this student's current grade
   * 
   * @param grade - the new value of this student's grade
   */
  public void setGrade(double grade) {
    this.grade = grade;
  }
  
  /**
   * Returns a String representation of this StudentRecord 
   * in the following format: "name (email): grade"
   * 
   * @return a String representation of this StudentRecord
   */
  @Override
  public String toString() {
    return name + " (" + email + "): " + grade;
  }

  /**
   * Compares this StudentRecord to other StudentRecord passed as input. 
   * StudentRecords are compared with respect to the lexicographical order of the students emails.
   * 0 if the email associated with the other Student is equal to the email of this Student; 
   * a value less than 0 if the email associated with this Student is 
   *    lexicographically less than the email of the other Student argument; 
   * and a value greater than 0 if the email associated with this Student is 
   *    lexicographically greater than the email of the other Student argument.
   * 
   * @param other - other StudentRecord to compare to
   * @return appropriate value based on comparison
   */
  @Override
  public int compareTo(StudentRecord o) {
    // use String compareTo method to compare the emails' lexicographical order
    return this.email.compareTo(o.email);
  }
  
  /**
   * Returns true if the given Object is a StudentRecord with an email 
   * that matches the email of this StudentRecord.
   * 
   * @param o - Object to compare with this StudentRecord
   * @return true if the given Object is a StudentRecord with an email 
   * that matches the email of this StudentRecord
   */
  @Override
  public boolean equals(Object o) {
    // check if o is of type StudentRecord
    if (!(o instanceof StudentRecord)) return false;
    
    StudentRecord s = (StudentRecord) o;
    if (s.email.equals(this.email)) return true; // compare emails
    else return false;
  }
 }
