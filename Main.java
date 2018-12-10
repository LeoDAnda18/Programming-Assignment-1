package main;

import java.util.*;
import crawler.*;
import heapfunctions.*;
import model.*;

/*
 * The Main function is in this class. 
 * To compute complexity, we have:
 * Constant time statements, so O(1)
 * First for loop: 1 constant time statements traversing n items, so cO(n) --> O(n).
 * Spider.search runs between O(n^2) and O(m^2) time, since it search n pages, and then m elements within the page.
 *		 This takes the longest time, and while it is an inefficient web crawler, it is a simple one and suitable for this
 *		 simulation.
 * Option 1 is heapsort and printheap sort, so it takes O(nlgn) time.
 * Option 2 is printPriority Queue, which calls buildMaxHeap first, so it takes O(nlgn) time.
 * Option 3 is modify score, which takes O(n) time.
 * Option 4 calls search again, which takes between O(n^2) and O(m^2) times.
 * The while loop takes r amount of user input.
 * At the end, we have insertion sort, which takes O(n^2) and print, which takes O(10).
 * To sum it up, the one taking up the most run time is spider.search, with a run time of O(n^2) to O(m^2).
 * Therefore: This entire program takes rO(n^2) to rO(m^2) time to complete.
 * 
 * Run time: O(1) + O(n) + O(n) + O(nlgn) + O(nlgn) + O(1) = 2O(1) + 2O(n) + 2O(nlgn) = 2O(nlgn) = O(nlgn).
 * Therefore, the worst case running time of this program for largen is cnlgn (which is O(nlgn)).
 */
public class Main 
{
	public static void main(String[] args)
	{	
		Scanner in = new Scanner(System.in);
		int User_input, size = 31; // size of array of webpages (31 is a complete heap)
		String searchTerm; // user input stored into here.
		WebPage[] urlStorage = new WebPage[size]; //Array to store WebPages
		ArrayList<UniqueSearch> Unique = new ArrayList<UniqueSearch>();
		
		for(int i = 0; i < size; i++){	urlStorage[i] = new WebPage();} //initialize all the webpages in the array.
        Spider spider = new Spider(); //Web Crawler
      
        System.out.println("Enter a search word to browse for: ");
        searchTerm = in.nextLine(); // user input for searching
        System.out.println("Crawling the web and retrieving sites. This will take some time. Please be patient.");
        spider.search("https://www.google.com/", searchTerm, urlStorage); //Search the web for the word, starting at google.com
        UniqueSearch temp = new UniqueSearch(searchTerm);
        Unique.add(temp); // adds the search term into a unique arraylist (used at the end).
        Heap_Functions f1 = new Heap_Functions(urlStorage); //creates a heap
    do
    {
    	// Lists available options for users to select from.
		System.out.println("Choose an option and enter the corresponding number:");
        System.out.println("1. HeapSort and print results");
        System.out.println("2. Print top 10 urls through priority Queue");
        System.out.println("3. Increase score to a WebPage");
        System.out.println("4. Search for new term");
        System.out.println("0. Quit");
        User_input = in.nextInt(); // takes in the user input
        in.nextLine();
        while(User_input < 0 || User_input > 4) //ensures user enters a valid score.
        {
    		System.out.println("Choose a valid option and enter the corresponding number:");
            User_input = in.nextInt(); // takes in the user input
            in.nextLine();
        }
        switch(User_input)
        {
        case 1:	
        	{
        		//prints out the results of heapsort.
        		f1.Heapsort();
        		f1.printHeapSort();
        		break;
        	}
        case 2:
        	{
        		//prints out the top 10 urls based off their scores.
        		f1.BuildMaxHeap();
        		f1.createPriorityQueue();  
        		f1.printPQueue();
                break;
        	}
        case 3:
        	{
        		//Lets user increase the score of one url entry.
        		f1.ModifyScore();
        		break;
        	}
        case 4:
        	{
        		//Lets the user enter a new search word.
        		 System.out.println("Enter a search word to browse for: ");
        	     searchTerm = in.nextLine();
        	     System.out.println("Crawling the web and retrieving sites. This will take some time. Please be patient.");
        	     spider.search("https://www.google.com/", searchTerm, urlStorage); //Search the web for the word, starting at google.com
        	     f1.replaceArray(urlStorage); //replaces the stored array in the heap to account for the new results.
        	     int i;
        	     for(i = 0; i < Unique.size(); i++)
        	     {
        	    	 //check if the search term already exists.
        	    	 if(searchTerm.equals(Unique.get(i).getSearch()))
        	    	 {
        	    	 Unique.get(i).frequencyIncrement(); //if it does exist, increment frequency counter by 1.
        	    	 break;
        	    	 }
        	     }
        	     if(i == Unique.size()) // if it reaches the end and still hasn't found an entry, add it as a new entry
        	     {
        	    	 UniqueSearch temp2 = new UniqueSearch(searchTerm); 
        	    	 Unique.add(temp2);
        	     }
        	     break;
        	}
        } 
    }while(User_input != 0); //keep running until user quits (enters 0).
    
    //Display the top 10 results
    descendingInsertionSort(Unique); //sort the top 10 into the first 10 slots.
    System.out.println("Printing terms searched for");
    for(int i = 0; i < Unique.size() && i < 10; i++) //if number of terms less than 10, display all. if greater, display
    												// top 10 only.
    {
    	Unique.get(i).printContent();
    }
    in.close();
}

	/*
	 * This runs insortion sort on the UniqueSearch arraylist in descending order. Therefore, top 10 are the first 10 elements.
	 * For this simulation, insertion sort runs fast since total unique searches will be small. For a real search engine, with
	 * billions of results, this sorting method would be too slow and inefficient.
	 */
	public static void descendingInsertionSort(ArrayList<UniqueSearch> A)
	{
		int j;
		UniqueSearch key = new UniqueSearch();
		int i;
		
		for (j = 1; j < A.size(); j++)
		{
			key.override(A.get(j));
			i = j - 1;
			while (i >= 0 && A.get(i).getFrequency() < key.getFrequency())
			{
				A.get(i+1).override(A.get(i));
				i = i - 1;
			}
			A.get(i+1).override(key);
		}
	}
}


