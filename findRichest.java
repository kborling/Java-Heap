/*
* Kevin Borling
* CSCD 320 | Algorithms
* Finding The Richest Poeple
* 02/16/2014
* findRichest will read in an input text file containing n integers and find the 10,000 largest integers.
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;
import java.util.Arrays;

public class findRichest {

	public static final int CAPACITY = 10000;
	public static int[] heap;
   public static int[] sorted;
   public static int[] descsort;
   public static int size = 0;
   public static int input = 0;

   /*
    * Reads in input file from command line or input.txt
    * Reads in list of n integers while maintaing a min heap of the largest 10,000 integers
    * Calls helper methods for removing smallest, sorting, and printing the heap elements
    */
	public static void main (String [] args) {

   Scanner fin;
   heap = new int[CAPACITY];

      try
      {
         if(args.length == 0)
         {
            fin = new Scanner(new File("input.txt")); // Read from input
         }
         else
         {
            fin = new Scanner(new File(args[0])); // Read from command line
         }

         while(fin.hasNext()) 
         {
            input = fin.nextInt();
            // Replace smallest with larger integer
          if(input > heap[0] && heap[0] != 0 && size == CAPACITY)
               removeSmallest();

          if(size < CAPACITY)
            buildMinHeap(input);
         }

         fin.close();

         ascendSort();
         descendSort();
         printHeap();


      }// End try
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }// End catch

	}// End Main()

/*
 * Adds elements to heap while incrementing size of heap
 * @param input the integer input from input.txt file
 */
public static void buildMinHeap(int input) {
   heap[size] = input;
   heapify(size);
   size++;
}// End buildMinHeap()

/*
 * Ensures heap order is maintained when adding elements to heap
 * @param index the array index used to find the parent node
 */
public static void heapify(int index) {
   int parent;

   if(index > 0) 
   {
      parent = (int)Math.floor((index - 1) / 2);
      
      if(heap[parent] > heap[index])
      {
         exchange(parent, index);
         heapify(parent);
      }
   }
}// End heapify()

/*
 * Removes smallest element from min heap
 */
public static void removeSmallest() {
   if(heap == null)
   {
      System.out.println("Heap is Empty");
      return;
   }
   else 
   {
      heap[0] = heap[size - 1];
      size--;
      if (size > 0)
         minHeapify(0);
   }
}// End removeSmallest()

/*
 * Maintains a min heap when removing smallest element from min heap
 * @param index the parent index to find left and right children of it
 */
public static void minHeapify(int index) {
   int temp, min, rightchild, leftchild;

   leftchild = (index * 2);
   rightchild = (index * 2) + 1;

   if(rightchild >= size) 
   {
      if (leftchild >= size)
      {
      return;
      }
      else
      {
      min = leftchild;
      }
   } else 
{
   if(heap[leftchild] <= heap[rightchild])
      min = leftchild;
   else
      min = rightchild;
}
if(heap[index] > heap[min]) 
{
   exchange(min, index);
   minHeapify(min);
}
}// End minHeapify()

/*
 * Helper to exchange two elements in array
 * @param first the first element to be exchanged
 * @param second the second element to be exchanged
 */
public static void exchange(int first, int second) {
   int temp = heap[first];
   heap[first] = heap[second];
   heap[second] = temp;
}// End exchange()

/*
 * Insert min heap elements into array in ascending order
 */
public static void ascendSort() {
   int n = 0;
   sorted = new int[heap.length];

   for(int x = 0; x < heap.length; x++)
   {
      sorted[x] = heap[n];
      removeSmallest();
   }
}// End ascendSort()

/*
 * Insert sorted elements into array in descending order
 */
public static void descendSort() {
   descsort = new int[sorted.length];
   int i = sorted.length - 1;

   for(int x = 0; x < sorted.length; i--,x++)
      descsort[x] = sorted[i];
}// End descendSort()

/*
 * Prints the heap in descending order to output.txt file
 */
public static void printHeap() {
   try {
   PrintStream ps = new PrintStream("output.txt");
   for(int x = 0; x < descsort.length; x++)
      ps.println(descsort[x]);
   ps.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
}// End printHeap()

}// End Class