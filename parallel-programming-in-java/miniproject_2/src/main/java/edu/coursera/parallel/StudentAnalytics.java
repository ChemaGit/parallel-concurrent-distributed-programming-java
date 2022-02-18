package edu.coursera.parallel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple wrapper class for various analytics methods.
 */
public final class StudentAnalytics {
    /**
     * Sequentially computes the average age of all actively enrolled students
     * using loops.
     *
     * @param studentArray Student data for the class.
     * @return Average age of enrolled students
     */
    public double averageAgeOfEnrolledStudentsImperative(
            final Student[] studentArray) {
        List<Student> activeStudents = new ArrayList<Student>();

        for (Student s : studentArray) {
            if (s.checkIsCurrent()) {
                activeStudents.add(s);
            }
        }

        double ageSum = 0.0;
        for (Student s : activeStudents) {
            ageSum += s.getAge();
        }

        return ageSum / (double) activeStudents.size();
    }

    /**
     * TODO compute the average age of all actively enrolled students using
     * parallel streams. This should mirror the functionality of
     * averageAgeOfEnrolledStudentsImperative. This method should not use any
     * loops.
     *
     * @param studentArray Student data for the class.
     * @return Average age of enrolled students
     */
    public double averageAgeOfEnrolledStudentsParallelStream(
            final Student[] studentArray) {
        double retVal = Stream.of(studentArray)
                .parallel()
                .filter(s -> s.checkIsCurrent())
                .mapToDouble(a -> a.getAge())
                .average()
                .getAsDouble();
        return retVal;
    }

    /**
     * Sequentially computes the most common first name out of all students that
     * are no longer active in the class using loops.
     *
     * @param studentArray Student data for the class.
     * @return Most common first name of inactive students
     */
    public String mostCommonFirstNameOfInactiveStudentsImperative(
            final Student[] studentArray) {
        List<Student> inactiveStudents = new ArrayList<Student>();

        for (Student s : studentArray) {
            if (!s.checkIsCurrent()) {
                inactiveStudents.add(s);
            }
        }

        Map<String, Integer> nameCounts = new HashMap<String, Integer>();

        for (Student s : inactiveStudents) {
            if (nameCounts.containsKey(s.getFirstName())) {
                nameCounts.put(s.getFirstName(),
                        new Integer(nameCounts.get(s.getFirstName()) + 1));
            } else {
                nameCounts.put(s.getFirstName(), 1);
            }
        }

        String mostCommon = null;
        int mostCommonCount = -1;
        for (Map.Entry<String, Integer> entry : nameCounts.entrySet()) {
            if (mostCommon == null || entry.getValue() > mostCommonCount) {
                mostCommon = entry.getKey();
                mostCommonCount = entry.getValue();
            }
        }

        return mostCommon;
    }

    /**
     * TODO compute the most common first name out of all students that are no
     * longer active in the class using parallel streams. This should mirror the
     * functionality of mostCommonFirstNameOfInactiveStudentsImperative. This
     * method should not use any loops.
     *
     * @param studentArray Student data for the class.
     * @return Most common first name of inactive students
     */
    public String mostCommonFirstNameOfInactiveStudentsParallelStream(
            final Student[] studentArray) {
        String retVal = Arrays.stream(studentArray)
                .parallel()
                .filter(student -> !student.checkIsCurrent())
                .collect(Collectors.groupingBy(Student::getFirstName, Collectors.counting()))
                .entrySet()
                .stream()
                .parallel()
                .max(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();

        return retVal;
    }

    /**
     * Sequentially computes the number of students who have failed the course
     * who are also older than 20 years old. A failing grade is anything below a
     * 65. A student has only failed the course if they have a failing grade and
     * they are not currently active.
     *
     * @param studentArray Student data for the class.
     * @return Number of failed grades from students older than 20 years old.
     */
    public int countNumberOfFailedStudentsOlderThan20Imperative(
            final Student[] studentArray) {
        int count = 0;
        for (Student s : studentArray) {
            if (!s.checkIsCurrent() && s.getAge() > 20 && s.getGrade() < 65) {
                count++;
            }
        }
        return count;
    }

    /**
     * TODO compute the number of students who have failed the course who are
     * also older than 20 years old. A failing grade is anything below a 65. A
     * student has only failed the course if they have a failing grade and they
     * are not currently active. This should mirror the functionality of
     * countNumberOfFailedStudentsOlderThan20Imperative. This method should not
     * use any loops.
     *
     * @param studentArray Student data for the class.
     * @return Number of failed grades from students older than 20 years old.
     */
    public int countNumberOfFailedStudentsOlderThan20ParallelStream(
            final Student[] studentArray) {
        long retVal = Stream.of(studentArray)
                .parallel()
                .filter(student -> !student.checkIsCurrent())
                .filter(student -> student.getAge() > 20)
                .filter(student -> student.getGrade() < 65)
                .count();

        return (int)retVal;
    }

    private static Student[] generateStudentData() {
        final int REPEATS = 10;
        final String[] firstNames = {"Sanjay", "Yunming", "John", "Vivek", "Shams", "Max"};
        final String[] lastNames = {"Chatterjee", "Zhang", "Smith", "Sarkar", "Imam", "Grossman"};

        final int N_STUDENTS = 2000000;
        final int N_CURRENT_STUDENTS = 600000;

        Student[] students = new Student[N_STUDENTS];
        Random r = new Random(123);

        for (int s = 0; s < N_STUDENTS; s++) {
            final String firstName = firstNames[r.nextInt(firstNames.length)];
            final String lastName = lastNames[r.nextInt(lastNames.length)];
            final double age = r.nextDouble() * 100.0;
            final int grade = 1 + r.nextInt(100);
            final boolean current = (s < N_CURRENT_STUDENTS);

            students[s] = new Student(firstName, lastName, age, grade, current);
        }

        return students;
    }

    public static void main(String[] args) {
      Student[] students = generateStudentData();
    }
}
