# parallel-concurrent-distributed-programming-java
The fundamentals of parallel, concurrent, and distributed programming in Java.

# Course 1
### Parallel Programming in Java
````text
This course teaches learners (industry professionals and students) the fundamental concepts of parallel programming in the context of Java 8. Parallel programming enables developers to use multicore computers to make their applications run faster by using multiple processors at the same time. By the end of this course, you will learn how to use popular parallel Java frameworks (such as ForkJoin, Stream, and Phaser) to write parallel programs for a wide range of multicore platforms including servers, desktops, or mobile devices, while also learning about their theoretical foundations including computation graphs, ideal parallelism, parallel speedup, Amdahl's Law, data races, and determinism.

Why take this course?

•	All computers are multicore computers, so it is important for you to learn how to extend your knowledge of sequential Java programming to multicore parallelism.
•	Java 7 and Java 8 have introduced new frameworks for parallelism (ForkJoin, Stream) that have significantly changed the paradigms for parallel programming since the early days of Java.
•	Each of the four modules in the course includes an assigned mini-project that will provide you with the necessary hands-on experience to use the concepts learned in the course on your own, after the course ends.
•	During the course, you will have online access to the instructor and the mentors to get individualized answers to your questions posted on forums.

The desired learning outcomes of this course are as follows:

•	Theory of parallelism: computation graphs, work, span, ideal parallelism, parallel speedup, Amdahl's Law, data races, and determinism
•	Task parallelism using Java’s ForkJoin framework
•	Functional parallelism using Java’s Future and Stream frameworks
•	Loop-level parallelism with extensions for barriers and iteration grouping (chunking)
•	Dataflow parallelism using the Phaser framework and data-driven tasks

Mastery of these concepts will enable you to immediately apply them in the context of multicore Java programs, and will also provide the foundation for mastering other parallel programming systems that you may encounter in the future  (e.g., C++11, OpenMP, .Net Task Parallel Library).
````

# Course 2
### Concurrent Programming in Java
````text
This course teaches learners (industry professionals and students) the fundamental concepts of concurrent programming in the context of Java 8.   Concurrent programming enables developers to efficiently and correctly mediate the use of shared resources in parallel programs.  By the end of this course, you will learn how to use basic concurrency constructs in Java such as threads, locks, critical sections, atomic variables, isolation, actors, optimistic concurrency and concurrent collections, as well as their theoretical foundations (e.g., progress guarantees, deadlock, livelock, starvation, linearizability).

Why take this course?

•	It is important for you to be aware of the theoretical foundations of concurrency to avoid common but subtle programming errors. 
•	Java 8 has modernized many of the concurrency constructs since the early days of threads and locks.
•	During the course, you will have online access to the instructor and mentors to get individualized answers to your questions posted on the forums.
•	Each of the four modules in the course includes an assigned mini-project that will provide you with the necessary hands-on experience to use the concepts learned in the course on your own, after the course ends.

The desired learning outcomes of this course are as follows:

•	Concurrency theory: progress guarantees, deadlock, livelock, starvation, linearizability
•	Use of threads and structured/unstructured locks in Java
•	Atomic variables and isolation
•	Optimistic concurrency and concurrent collections in Java (e.g., concurrent queues, concurrent  hashmaps)
•	Actor model in Java

Mastery of these concepts will enable you to immediately apply them in the context of concurrent Java programs, and will also help you master other concurrent programming system that you may encounter in the future  (e.g., POSIX threads, .NET threads).
````

# Course 3
### Distributed Programming in Java
````text
This course teaches learners (industry professionals and students) the fundamental concepts of Distributed Programming in the context of Java 8.  Distributed programming enables developers to use multiple nodes in a data center to increase throughput and/or reduce latency of selected applications.  By the end of this course, you will learn how to use popular distributed programming frameworks for Java programs, including Hadoop, Spark, Sockets, Remote Method Invocation (RMI), Multicast Sockets, Kafka, Message Passing Interface (MPI), as well as different approaches to combine distribution with multithreading.

Why take this course?

•	All data center servers are organized as collections of distributed servers, and it is important for you to also learn how to use multiple servers for increased bandwidth and reduced latency.
•	In addition to learning specific frameworks for distributed programming, this course will teach you how to integrate multicore and distributed parallelism in a unified approach.
•	Each of the four modules in the course includes an assigned mini-project that will provide you with the necessary hands-on experience to use the concepts learned in the course on your own, after the course ends.
•	During the course, you will have online access to the instructor and the mentors to get individualized answers to your questions posted on forums.

The desired learning outcomes of this course are as follows:

•	Distributed map-reduce programming in Java using the Hadoop and Spark frameworks
•	Client-server programming using Java's Socket and Remote Method Invocation (RMI) interfaces
•	Message-passing programming in Java using the Message Passing Interface (MPI)
•	Approaches to combine distribution with multithreading, including processes and threads, distributed actors, and reactive programming

Mastery of these concepts will enable you to immediately apply them in the context of distributed Java programs, and will also provide the foundation for mastering other distributed programming frameworks that you may encounter in the future  (e.g., in Scala or C++).
````
