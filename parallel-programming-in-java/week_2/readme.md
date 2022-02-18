# 2 Functional Parallelism
### 2.1 Future Tasks
````text
Lecture Summary: In this lecture, we learned how to extend the concept of asynchronous tasks to future tasks 
and future objects (also known as promise objects). Future tasks are tasks with return values, 
and a future object is a “handle” for accessing a task’s return value. 
There are two key operations that can be performed on a future object, A: 

    1. Assignment — A can be assigned a reference to a future object returned by a task of the form, 
       future { ⟨ task-with-return-value ⟩ } (using pseudocode notation). 
       The content of the future object is constrained to be single assignment (similar to a final variable in Java), 
       and cannot be modified after the future task has returned.

    2. Blocking read — the operation, A.get(), waits until the task associated with future object A has completed, 
       and then propagates the task’s return value as the value returned by A.get().  
       Any statement, S, executed after A.get() can be assured that the task associated with future object A 
       must have completed before S starts execution.

These operations are carefully defined to avoid the possibility of a race condition on a task’s return value, 
which is why futures are well suited for functional parallelism. 
````
In fact, one of the earliest use of futures for parallel computing was in an extension to Lisp known as [MultiLisp](https://en.wikipedia.org/wiki/MultiLisp).
````text
Optional Reading: 
````
1. Wikipedia article on [Futures and promises](https://en.wikipedia.org/wiki/Futures_and_promises).

### 2.2 Creating Future  Tasks  in Java’s  Fork/Join Framework
````text
Lecture Summary: In this lecture, we learned how to express future tasks in Java’s Fork/Join (FJ) framework. 
Some key differences between future tasks and regular tasks in the FJ framework are as follows:
````
1. A future task extends the [RecursiveTask](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveTask.html) class in the FJ framework, instead of [RecursiveAction](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html) as in regular tasks.
````text
2.   The compute(){\tt compute()}compute() method of a future task must have a non-void return type, whereas it has a void return type  for  regular tasks.

3.   A method call like left.join(){\tt left.join()}left.join() waits for the task referred to by object left{\tt left}left in both cases, but also provides the task’s return value  in the case of future    tasks.

Optional Reading:
````
1. [Documentation on Java’s  RecursiveTask class](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveTask.html)

### 2.3 Memoization
````text
Lecture Summary: In this lecture, we learned the basic idea of “memoization”, which is to remember results of function calls f (x) as follows:
 1. Create a data structure that stores the set 
    {(x1_11​, y1_11​ = f (x1_11​)), (x2_22​,y2_22​ = f (x2_22​)), . . .}
    for each call f (xi_ii​) that returns yi_ii​.
 2. Perform look ups in that data structure when processing calls of the form f(x') when x'equals one of the 
    xi_ii​ inputs for which f (xi_ii​) 
    has already been computed.
````
Memoization can be especially helpful for algorithms based on [dynamic programming](https://en.wikipedia.org/wiki/Dynamic_programming).
In the lecture, we  used [Pascal’s triangle](https://en.wikipedia.org/wiki/Pascal%27s_triangle) as an illustrative example to motivate memoization.
````text
The memoization pattern lends itself easily to parallelization using futures by modifying the memoized data structure 
to store {(x1_11​, y1_11​ = future(f (x1_11​))), (x2_22​, y2_22​ = future(f (x2_22​))), . . .}. 
The lookup operation can then be replaced by a get() operation on the future value, 
if a future has already been created for the result of a given input.

Optional Reading:
````
1. Wikipedia article  on [Memoization](https://en.wikipedia.org/wiki/Memoization).

### 2.4 Java Streams
````text
Lecture Summary: In this lecture we learned about Java streams, and how they provide  a  
functional approach to operating on collections of data. For example, the statement, 
“students.stream().forEach(s →\rightarrow→ System.out.println(s));”, 
is a succinct way of specifying an action to be performed on each element  s in the collection, students.  
An aggregate data query or data transformation can be specified by  building a stream  pipeline consisting 
of a source (typically by  invoking the .stream() method on a data collection , 
a sequence of intermediate operations such as map() and filter(), and an optional terminal operation such as forEach() 
or average().  As an example, the following pipeline can be used to compute the average age  
of  all  active  students using Java streams:
````
````java
students.stream()
    .filter(s -> s.getStatus() == Student.ACTIVE)
    .mapToInt(a -> a.getAge())
    .average();
````
````text
From the viewpoint of this course, an important benefit of using Java  streams when possible 
is that the  pipeline can be made to execute in parallel by designating the source to be a parallel stream, i.e., 
by simply replacing students.stream() in the above code by students.parallelStream()  or Stream.of(students).parallel(). 
This form of functional parallelism is a major convenience for the programmer, 
since they do not need to worry about explicitly allocating intermediate collections 
(e.g., a collection of all active students), or about ensuring that parallel accesses to data collections 
are  properly synchronized.

Optional Reading:
````
[1.Article on “Processing Data with Java SE 8 Streams”](https://www.oracle.com/technical-resources/articles/java/ma14-java-se-8-streams.html)

[2.Tutorial on specifying Aggregate Operations using Java streams](https://docs.oracle.com/javase/tutorial/collections/streams/)

[3.Documentation on java.util.stream.Collectors class for performing reductions on streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html)

### 2.5 Determinism and Data Races
````text
Lecture Summary: In this lecture, we studied the relationship between determinism and data races in parallel programs. 
A parallel program is said to be functionally deterministic if it always computes the same answer 
when given the same input, and structurally deterministic if it always computes the same computation graph, 
when given the same input. The presence of data races often leads to functional and/or structural non determinism 
because a parallel program with data races may exhibit different behaviors for the same input, 
depending on the relative scheduling and timing of memory accesses involved in a data race. 
In general, the absence of data races is not sufficient to guarantee determinism. 
However, all the parallel constructs introduced in this course (“Parallelism”) were carefully selected to ensure 
the following Determinism Property:
If a parallel program is written using the constructs introduced in this course and is guaranteed 
to never exhibit a data race, then it must be both functionally and structurally deterministic.

Note that the determinism property states that all data-race-free parallel programs written using 
the constructs introduced in this course are guaranteed to be deterministic, but it does not imply 
that a program with a data race must be functionally/structurally non-deterministic. 
Furthermore, there may be cases of “benign” nondeterminism for programs with data races in which 
different executions with the same input may generate different outputs, but all the outputs 
may be acceptable in the context of the application, e.g., different locations for a 
search pattern in a target string.

Optional Reading:
````
[1.Wikipedia article on Race condition](https://en.wikipedia.org/wiki/Race_condition)

