//Personal note: Complete
package heapfunctions;

import java.util.*;

import model.*;

/*
 * This class stores all of the required heap-functions specified in the guidelines.
 * These heapfunctions have been adjusted from the textbook to work in Java and with the WebPage class.
 * All these heap functions use the PageRank as comparisons and values. 
 */


public class Heap_Functions
{
	int heapsize; // keeps track of the size of the heap.
	int priority_size = 10; // must have top 10 scores in a priority queue.
	ArrayList<WebPage> Storage = new ArrayList<WebPage>(); //Stores a copy of the original array
	WebPage[] priority_Queue = new WebPage[priority_size]; //Array to store top 10 results
	
	//Constructor for the heap. This takes O(n) time.
	public Heap_Functions(WebPage[] a1) //Create a Heap and enables usage of all heap functions.
	{
		for(int i = 0; i < a1.length; i++)
			Storage.add(a1[i]); // fills the ArrayList with the contents of the original array.
	}
	
	/*
	 * retrieve index of parent node (Modified because index starts at 0, not 1)
	 * This function runs in O(1) time.
	 */
	public int Parent(int i)
	{
		return (i - 1)/2;
	}
	
	/*
	 * retrieves index of left child node (Modified because index starts at 0, not 1)
	 * This function runs in O(1) time
	 */
	public int Left(int i)
	{
		return (2 * i) + 1;
	}
	
	/*
	 * retrieves index of right child node (Modified because index starts at 0, not 1)
	 * This function runs in O(1) time
	 */
	public int Right(int i)
	{
		return (2 * i) + 2;
	}
	
	/*
	 * This function is used to "override" the equal operator so that we can say WebPage a1 = a2.
	 * This runs in O(1) time.
	 */
	public void override(WebPage a1, WebPage a2)
	{
		a1.setURL(a2.getURL()); //overrides URL variable
		a1.setRel(a2.getRel()); //overrides PageRank variable
		a1.setFreq(a2.getFreq()); //overrides Frequency and Location variable
		a1.setExis(a2.getExis()); // overrides Existence variable
		a1.setConn(a2.getConn()); // overrides connection variable
		a1.setAdvt(a2.getAdvt()); // overrides advertisement variable.
		a1.setIndex(a2.getIndex()); //overrides index variable
	}

	/*
	 * This function is used to exchange 2 webpages (It does the feature of exchange specified in heap pseudocode). 
	 * It calls override 3 times, and it runs in O(1) time.
	 */
	public void exchange(WebPage a1, WebPage a2)
	{
		WebPage temp = new WebPage();
		override(temp, a1); // Set the temp (temp = a1)
		override (a1, a2); // store the content of a2 into a1. (a1 = a2)
		override (a2, temp); // store the content of temp, which holds a1 content, into a2 (a2 = temp)
	}
	
	/*
	 * This function checks if, at point i, the heap is a Max-Heap. If it is not, it will
	 * modify the heap to be a Max-Heap. If it already is a maxheap, then it will call only once.
	 * This runs in O(lgn) time.
	 */
		public void MaxHeapify(int i) 
	{
		int largest; // stores the index of the largest key.
		int l = Left(i); // stores the index of left child.
		int r = Right(i); //stores the index of the right child.
		//check if index is not outside heapsize and if left score is larger than node i score.
		if(l < heapsize && Storage.get(l).getRel() > Storage.get(i).getRel())
			largest = l; //largest is the left child node index.
		else
			largest = i; //largest is the current node index.
		//check if index is not outside heapsize and if right score is larger than current largest score.
		if(r < heapsize && Storage.get(r).getRel() > Storage.get(largest).getRel())
			largest = r; //largest is right node child index.
		if(largest != i) //if index of largest is not the current node, we need to fix the heap by exchanging the two nodes.
		{
			exchange(Storage.get(i), Storage.get(largest)); //exchange current node with largest node.
			MaxHeapify(largest); //call maxheapify to check if it is now a maxheap.
		}
	}	
	
	/*
	 * Builds a maxheap by starting at the leafs, and then calling max heapify with each new additional node.
	 * We call buildMaxHeap n times, and MaxHeapify is lgn times, so we have O(nlgn).
	 */	
	public void BuildMaxHeap()
	{
		heapsize = Storage.size(); //set heapsize to be the size of the arraylist.
		for(int i = (Storage.size() - 1)/2; i >= 0; i--) //leaves are in maxheap order, so only focus on the internal nodes.
			MaxHeapify(i); //max heapify each time you add a new node to the heap.
	}
	
	/*
	 * Sort the heap using maxheap properties. The ArrayList will be sorted in ascending order.
	 * This takes O(nlgn) time.
	 */
	public void Heapsort()
	{	
		BuildMaxHeap(); //build a maxheap initially.
		for(int i = (Storage.size() - 1); i > 0; i--)
		{
			exchange(Storage.get(0), Storage.get(i)); //change the root with the last element of the heap.
			heapsize = heapsize - 1; //remove the new last element (now the largest element)
			MaxHeapify(0); //fix the heap to become a maxheap again.
		}
	}
	
	/*
	 * Expand the heap to include a new element. Then increase the key of that element, and fix the heap to be maxheap.
	 * This calls heapincreasekey, so it runs in O(lgn) time.
	 */
	public void MaxHeapInsert(int key)
	{
		heapsize = heapsize + 1; //increase heapsize by one.
		WebPage web = new WebPage(); //create a new webpage.
		web.setRel(-99999999); //set the relevency score to a small number.
		web.setURL("new page"); //give it a temporary name.
		Storage.add(web); //add the element into the array.
		HeapIncreaseKey(heapsize - 1, key); //change the total score, then fix the heap to be maxheap.
	}
	
	/*
	 * Removes an element from the heap (the root). and returns the pagerank score of that element.
	 * Fix the heap to be maxheap.
	 * this runs in O(lgn) time since we call MaxHeapify.
	 */
	public WebPage HeapExtractMax()
	{
			WebPage max = new WebPage();
			override(max, HeapMaximum());
			exchange(Storage.get(0), Storage.get(heapsize - 1)); //set root equal to content of last element.
			heapsize = heapsize - 1; //reduce the size of the heap by 1. (now excludes the last element).
			MaxHeapify(0); //reheap to be a maxheap.
			return max; //returns the webpage stored in max.
	}
	
	/*
	 * From user input, increase the pageRank of a given page if the key is larger than the current pagerank score.
	 * This runs a while loop through a tree, so it runs in O(lgn) time.
	 * 
	 */
	public void HeapIncreaseKey(int i, int key)
	{	
		if(key < Storage.get(i).getRel()) //if key is less than current key
			System.out.print("new key is smaller than current key"); //ignore it, you aren't increasing the key.
		else
		{
			Storage.get(i).setRel(key); //Set the element pagerank to be the key.
			//check the tree to make sure it is still in max heap format.
			while (i > 0 && Storage.get(Parent(i)).getRel() < Storage.get(i).getRel()) 
			{
				//if it is not in maxheap format, exchange nodes until it is a maxheap.
				exchange(Storage.get(i), Storage.get(Parent(i)));
				i = Parent(i);
			}
		}
	}
	
	/*
	 * returns the root of the tree (since the maximum value is at the root of the tree.
	 * This runs in O(1) time.
	 */
	public WebPage HeapMaximum()
	{
		return Storage.get(0);
	}
	
	/*
	 * This function replaces the content of the array with a new array obtained after searching for a new word.
	 * This function will take O(n) time.
	 */
	public void replaceArray(WebPage[] newArray)
	{
		Storage.clear();
		for(int i = 0; i < newArray.length; i++)
		{
			Storage.add(newArray[i]);
		}
	}
	
	/*
	 * Extracts the top 10 results of a max heap to create a priority Queue.
	 * This takes 10O(lgn) time since it calls heapextract 10 times. so O(lgn).
	 * 
	 */
	public void createPriorityQueue()
	{
        for(int i = 0; i < priority_Queue.length; i++)
        {
        	priority_Queue[i] = HeapExtractMax();
        	priority_Queue[i].setPRank(i + 1);
        }
	}
	
	/*
	 * This function prints the results after heapsort is called.
	 * This function takes O(n) time.
	 */
	public void printHeapSort()
	{
		int counter = Storage.size();
		for(int i = 0; i < Storage.size(); i++)
		{
			Storage.get(i).setPRank(counter);
			counter--;
		}
		System.out.println("Using HeapSort to display order from least Score to highest Score");
		System.out.println("Index" + "\t" + "PageRank" + "\t" + "Score" + "\t" + "URL");
		for(int i = 0; i < Storage.size(); i++)
		{
			Storage.get(i).printContents();
		}
		System.out.print("\n");
	}
	
	/*
	 * Prints the priority Queue. This takes O(1) time.
	 */
	public void printPQueue()
	{
		System.out.println("Printing the top 10 results:");
		System.out.println("Index" + "\t" + "PageRank" + "\t" + "Score" + "\t" + "URL");
        for(int i = 0; i < priority_Queue.length; i++)
        {
        	priority_Queue[i].printContents();
        }
        System.out.print("\n");
	}
	
	/*
	 * This takes in user input to choose which url to increase its score. It then calls
	 * HeapIncrease Key to do so. This takes O(n) + O(lgn) time, so total of O(n) time.
	 */
	public void ModifyScore()
	{
		int user_input;
		Scanner in = new Scanner(System.in);
		for(int i = 0; i < Storage.size(); i++)
		{
			System.out.println(i + ". " + Storage.get(i).getURL() + " , " + Storage.get(i).getRel());
		}
		System.out.println("Please choose a page to increase its score: ");
		user_input = in.nextInt();
		in.nextLine();
		while(user_input >= Storage.size() || user_input < 0)
		{
			System.out.println("Please choose a valid page to increase its score: ");
			user_input = in.nextInt();	
			in.nextLine();
		}
		Storage.get(user_input).ModifyRel();
		HeapIncreaseKey(user_input, Storage.get(user_input).getRel());
	}
}
