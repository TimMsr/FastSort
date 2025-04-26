Instructions for running the code:
1. Clone the repository or save the file under FastSort.java
2. Compile the code: javac FastSort.java
3. Run the code: java FastSort

To see the sorted array:
1. The output will only display the performance output, if you want to verify the correct output, you need to uncomment the sb.tostring line (or incorprate your own print method)
Make sure to adjust the sizes of the array, due to the high sizes of inputs.

To reproduce the results:
If the code is cloned, the benchmark tests will be already included, so the only thing needed to do is run the fastSort file to see the benchmarks. 
To see the other benchmarks, the BenchmarkSuite.java needs to be run which will output all the numbers in this format:
size,pattern,algorithm,avgMillis,avgMemMB
This was done to help with data analysis and we recommend, putting the results in excel for ease of use.

If you want to do your own benchmarks, 
1. set the input size that the sort algorithm should sort (10,100,1000,10000)
2. Set the number of iterations (in our case 1000), which can be set to any value you would like, the higher the number of iterations the more accurate the average will be.
3. Maintain the same random range (-10000000,10000000), which has to be 8 digits as stated in the paper.

