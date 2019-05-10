//Aoife Whelan
//15200913
//Java Project - Radix Sort

package impl;

import java.lang.reflect.Array;
import java.util.Random;

import core.List;

public class SortingUtils {
	//A collection of Bucket and Radix Sorting methods for integers and strings
	
	@SuppressWarnings("unchecked")
	public static void singleDigitBucketSort(List<Integer> list) {
		List<Integer>[] buckets = (List<Integer>[]) Array.newInstance(List.class, 10);
		
		// Step 1: Copy the values from the list into the buckets.
		while (!list.isEmpty()) {
			int value = list.remove(list.first());
			if (value < 0 || value > 9) throw new UnsortableException("The list contains an invalid value: " + value);
			if (buckets[value] == null) {
				buckets[value] = new LinkedList<Integer>();
			}
			buckets[value].insertLast(value);
		}
		
		// Step 2: Copy the values from the buckets to the list.
		for (int i=0; i < buckets.length; i++) {
			if (buckets[i] != null) {
				while (!buckets[i].isEmpty()) {
					list.insertLast(buckets[i].remove(buckets[i].first()));
				}
			}
		}
	}

	public static void integerBucketSort(List<Integer> list, int lower, int upper) {
		//Sorts a list of integers between the bounds lower and upper using Bucket Sort
		
		@SuppressWarnings("unchecked")
		
		//Initialise buckets
		List<Integer>[] buckets = (List<Integer>[]) Array.newInstance(List.class, 1000);
		
		//Copy each value from list to appropriate bucket
		while (!list.isEmpty()) {
			
			//Current value
			int value = list.remove(list.first());
			
			//If value exceeds bounds, throw exception
			if (value < lower || value > upper) throw new UnsortableException("The list contains an invalid value: " + value);
			
			//Insert value into corresponding bucket
			if (buckets[value] == null) {
				buckets[value] = new LinkedList<Integer>();
			}
			buckets[value].insertLast(value);
		}
		
		//Copy sorted values from buckets back to list
		for (int i=0; i < buckets.length; i++) {
			if (buckets[i] != null) {
				while (!buckets[i].isEmpty()) {
					list.insertLast(buckets[i].remove(buckets[i].first()));
				}
			}
		}
	}
	
	public static void ibsTest(){
		//Tests the Integer Bucket Sort method integerBucketSort
		
		//Creates the list
		List<Integer> list = new ArrayList<Integer>(1000);
		
		//Adds 500 random integers in the range 50-250
		Random gen = new Random();
		for(int i = 0; i <500; i++){
			list.insertFirst(gen.nextInt((250 - 50)+1) + 50);
		}
		//Print list before sorting
		System.out.println("Unsorted:\n"+list);
		
		//Sort list by Bucket Sort
		integerBucketSort(list, 50, 250);
		
		//Print list after sorting
		System.out.println("Sorted:\n"+list);
	}
	
	public static void integerRadixSort(List<Integer> list, int digits) {
		//Sorts a list of integers with at most the specified number of digits using Radix Sort from the least to the most significant digit
		
		@SuppressWarnings("unchecked")
		//Initialise buckets
		List<Integer>[] buckets = (List<Integer>[]) Array.newInstance(List.class, 1000);
		
		//Iterate over number of digits
		for(int i = 0; i < digits; i++){
			//Sort into buckets based on i-th significant digit
			while (!list.isEmpty()) {
				//Current value
				int value = list.remove(list.first());
				
				//i-th significant digit of value
				int digit = (int)(value/(Math.pow(10, i)))%10; 
				
				//Throw exception if number in list exceeds specified number of digits
				if ((int)(value/(Math.pow(10, digits)))%10 > 0) throw new UnsortableException("The list contains an invalid value: " + value);
				
				//Add value to the bucket at position of i-th significant digit
				if (buckets[digit] == null) {
					buckets[digit] = new LinkedList<Integer>();
				}
				buckets[digit].insertLast(value);
			}
			//Copy sorted values from buckets back into list
			for (int j=0; j < buckets.length; j++) {
				if (buckets[j] != null) {
					while (!buckets[j].isEmpty()) {
						list.insertLast(buckets[j].remove(buckets[j].first()));
					}
				}
			}
			//Print result of each iteration
			//System.out.println((i+1)+" Least Significant Digit:\n"+list);
		}
		
	}
	public static void irsTest(){
		//Tests the Integer Radix Sort method
		
		//Creates the list
		List<Integer> list = new ArrayList<Integer>(1000);
				
		//Adds 500 random integers with at most three digits
		Random gen = new Random();
		for(int i = 0; i <500; i++){
			list.insertFirst(gen.nextInt((999 - 0)+1));
		}
		//Print list before sorting
		System.out.println("Unsorted:\n"+list);
			
		//Sort list by Radix Sort
		integerRadixSort(list, 3);
		
		//Print list after sorting
		System.out.println("Sorted:\n"+list);
	}
	
	public static void stringRadixSort(List<String> list, int digits) {
		//Sorts a list of strings with at most 'digits' length using Radix sort 
		
		@SuppressWarnings("unchecked")
		
		//Initialise buckets
		List<String>[] buckets = (List<String>[]) Array.newInstance(List.class, 1000);
		
		//Keep track of current letter number 
		int letter = digits;
		
		//Iterate over number of digits
		for(int i = 0; i < digits; i++){
			//Sort into buckets based on i-th significant character
			while (!list.isEmpty()) {
				
				//Current value or word
				String value = list.remove(list.first());
				
				//Throw exception if length of string exceeds specified number of digits
				if (value.length() > digits) throw new UnsortableException("The list contains an invalid value: " + value);
				
				//Index of bucket to sort word into
				int index; 
				
				//Sort words into bucket based on the current letter number, if the word is shorter than this sort into bucket[0]
				if(value.length() >= letter){
					char c = value.charAt(letter - 1); //i-th significant character of value
					index = c - 'a' + 1; // +1 shifts index by 1 to leave bucket[0] for words shorter than current letter number
				}	
				else{	
					index = 0;
				}
				if (buckets[index] == null) {
					buckets[index] = new LinkedList<String>();
				}
				//Add value to the bucket at position of i-th significant character
				buckets[index].insertLast(value);
			
				
			}	
			//Copy sorted buckets back into list
			for (int j=0; j < buckets.length; j++) {
				if (buckets[j] != null) {
					while (!buckets[j].isEmpty()) {
						list.insertLast(buckets[j].remove(buckets[j].first()));
					}
				}
			}
			//Print result of each iteration
			//System.out.println((i+1)+" Significant Character:\n"+list);
			
			//Move to next letter
			letter--;
		}
				
	}

	
	public static void srsTest(){
		//Tests the String Radix Sort method
		
		//Creates the list
		List<String> list = new ArrayList<String>(1000);
						
		//Adds words in the sentence to list
		list.insertLast("the");
		list.insertLast("big");
		list.insertLast("black");
		list.insertLast("cat");
		list.insertLast("sat");
		list.insertLast("on");
		list.insertLast("the");
		list.insertLast("beautiful");
		list.insertLast("brown");
		list.insertLast("mat");
		
		//Find the string in the list with the max length
	    int maxLength = 0;
	    for (String s : list) {
	        if (s.length() > maxLength) {
	            maxLength = s.length();
	        }
	    }
		
		//Print list before sorting
		System.out.println("Unsorted:\n"+list);
					
		//Sort list by Radix Sort
		stringRadixSort(list, maxLength);
		
		//Print list after sorting
		System.out.println("Sorted:\n"+list);
	}
	
	public static void main(String[] args){
		
		//Test integer bucket sort
		System.out.println("*******************************Integer Bucket Sort*******************************");
		ibsTest();
		
		//Test integer radix sort
		System.out.println("\n*******************************Integer Radix Sort*******************************");
		irsTest();
		
		//Test string radix sort
		System.out.println("\n*******************************String Radix Sort*******************************");
		srsTest();
	}
}
