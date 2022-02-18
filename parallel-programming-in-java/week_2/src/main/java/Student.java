import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Student {
    // START OF INSTANCE FIELDS
    int id;
    boolean isCurrent;
    double age;
    // END OF INSTANCE FIELDS

    Student(int id, boolean isCurrent, double age) {
        this.id = id;
        this.isCurrent = isCurrent;
        this.age = age;
    }

    private static void printResults(String name, long timeInNanos, double sum) {
        System.out.printf("   %s completed in %8.3f milliseconds, with sum = %8.5f \n", name, timeInNanos / 1e6, sum);
    }

    /**
     *
     * @param studentArray
     * @return
     */
    public static double seqIteration(Student[] studentArray) {
        long startTime = System.nanoTime();
        List<Student> activeStudents = new ArrayList<>();
        for (Student s: studentArray)
            if(s.isCurrent) activeStudents.add(s);

        long ageSum = 0;
        for(Student a : activeStudents) ageSum += a.age;

        double retVal = (double) ageSum / (double) activeStudents.size();

        long timeInNanos = System.nanoTime() - startTime;
        printResults("seqIteration", timeInNanos, retVal);

        return  retVal;
    }

    public static double parStream(Student[] studentArray) {
        long startTime = System.nanoTime();

        double retVal = Stream.of(studentArray)
                .parallel()
                .filter(s -> s.isCurrent)
                .mapToDouble(a -> a.age)
                .average()
                .getAsDouble();

        long timeInNanos = System.nanoTime() - startTime;
        printResults("parStream", timeInNanos, retVal);

        return retVal;
    }

    public static void main(String[] args) {
        // Fill up an array of students

        // Call seqIteration

        // Call parStream
    }
}
