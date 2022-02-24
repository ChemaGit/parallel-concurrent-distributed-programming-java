# Mini Project 3: Parallelizing Matrix-Matrix Multiply Using Loop Parallelism
### Project Goals & Outcomes
````text
The aim of this project is intended to test your ability to exploit parallelism in loops.  
The provided code includes a sequential program for multiplying two matrices.  
Your task is to write a correct parallel version of the program that runs faster than the sequential version.  

In lectures this week, you learned about expressing parallelism across loop iterations. 
Sequential loops are common targets for parallelization for two reasons. 
First, because loop bodies are often repeated many times, the majority of execution time of sequential programs 
is often spent inside of one or more loops. Second, because loops express the same computation 
repeated across many points in the loop iteration space, parallelism arises naturally as long as 
those iterations are independent of each other. Of course, it is not always legal to parallelize a loop 
as data races may result.

More specifically, this week covered two ways of expressing loop parallelism in Java: 1) PCDP’s forall methods, 
and 2) parallel streams in standard Java. 
In this mini-project, you will get hands-on experience with PCDP's forall methods by parallelizing 
matrix-matrix multiply. Before getting started with this mini-project, it is recommended you review 
the first demo video in Week 3 in which Professor Sarkar walks through an example similar to this project.
````
### Project Instructions
````text
Your modifications should be done entirely inside of  MatrixMultiply.java. 
You may not make any changes to the signatures of any public or protected methods inside of MatrixMultiply, 
or remove any of them. However, you  are free to add any new methods you like. 
Any changes you make to  MatrixMultiplyTest.java will be ignored in the final grading process.  
As in past mini-projects  in this course, the provided code is a fully functioning Maven project  
but you are not required to use Maven if you prefer since we have also  provided the necessary JARs 
to use an alternate approach to build and  execute your code

Your main goals for this assignment are as follows (note that MatrixMultiply.java also contains helpful TODOs):

Modify the MatrixMultiply.parMatrixMultiply method to implement matrix multiply in parallel 
using PCDP's forall or forallChunked methods. This will closely follow the demo by Prof. Sarkar 
in the first demo video of Week 3. There is one TODO in MatrixMultiply.parMatrixMultiply to help indicate the changes 
to be made. A parallel implementation of MatrixMultiply.parMatrixMultiply should result in near-linear speedup 
(i.e. the speedup achieved should be close to the number of cores in your machine).
````
