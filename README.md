# Programming-Assignment-1
Simulating SERP through a Heap and Priority Queue.
The following is the requirements for this assignment.

For this programming assignment, your job is to design and implement a simulator of Google Search
Engine Results Page (SERP) using Heap Sort for the following two major features:
1. For each search keyword/term, display only the top 10 priority search result items/links based
on the PageRank.
2. You have a billion google searches a day, design a data structure which lets you pull out the
top 10 unique ones at the end of the day.

Programming Requirements:
1. The Google Search Engine Simulator MUST be written in Java and is required to use Max-Heap
Priority Queue approach specified in lecture ppt slides and in textbook. (You MUST directly
use/revise the pseudo codes provided in textbook to write your Java codes. Any other codes (e.g.
copied from internet) will be treated as failing requirements.
2. You can either download a Web Crawler software tool from Internet (links provided) or develop
your own simple Web Crawler in Java (Link Provided) to collect research results (See the
references.) The purpose of the webcrawler is to retrieve a list of random links. This is so that you 
do not hardcode a set of links.
3. Based on the PageRank calculation, the priority queue with an integer score implemented in a Max-
Heap Tree will store a collection of at least 30 search results of PageRank for the priority queue.
(You may use either ArrayList or Vector in Java)
4. You can make your own way/assumption of assigning a score to each factor for PageRank
calculation. The simulator must allow users to enter a score for each of the four factors.
5. When a PageRank factor of a keyword/search item changes it’s value, let’s say a website owner has
paid more money than other websites in the results of search to Google, the display result of the
specific priority order increased. In this case, you have to restructure the Max-Heap tree. So your
simulator must provide the user interface for changing the score for each PageRank factor.
6. The Google Search Engine Simulator MUST contain the following functions:
The following functions must be implemented.
a) Max-Heapify()
b) Build-Max-Heap()
c) Heapsort()
d) Max-Heap-Insert()
e) Heap-Extract-Max
f) Heap-Increase-Key
g) Heap-Maximum
7. Each java file/class/subroutine/function call MUST contain a header comment at the beginning of it
and each end of line in your codes. (Points will be taken off if your codes did not provide
comments.)
8. The end of your implementation, compute the complexity of your codes.
