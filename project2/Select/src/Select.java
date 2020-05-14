import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Select {
	private static final int SIZE_10000 = 10000;
	private static final int SIZE_100000 = 100000;
	private static final int SIZE_1000000 = 1000000;
	private static final int SMALL_SIZE = 25;
	private static final int GROUP_SIZE = 5;

	/**
	 * given integer array
	 */
	private int[] array;
	
	/**
	 * size of given array
	 */
	private int size;
	
	/**
	 * kth element of given array
	 */
	private int k;

	/**
	 * the number of key comparisons
	 */
	private int compCount = 0;
	
	/**
	 * Constructor for Select object.
	 * 
	 * @param array
	 * @param size
	 */
	Select(int[] array, int size){
		this.array = array;
		this.size = size;
	}
	
	/**
	 * @param array array to set
	 */
	public void setArray(int[] array) {
		this.array = array;
	}
	
	/**
	 * @return current array
	 */
	public int[] getArray() {
		return array;
	}
	
	/**
	 * @param size array size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	/**
	 * @return current array size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @param k current k to set
	 */
	public void setK(int k) {
		this.k = k;
	}
	
	/**
	 * @return current k
	 */
	public int getK() {
		return k;
	}
	
	/**
	 * @param compCount current number of key comparisons to set
	 */
	public void setCompCount(int compCount) {
		this.compCount = compCount;
	}
	
	/**
	 * @return current number of key comparisons
	 */
	public int getCompCount() {
		return compCount;
	}
	
	/**
	 * Sort the array using Quicksort and pick the kth smallest element. This has 
	 * average time complexity of O(n log n) and worst-case time of O(n^2).
	 * 
	 * We have array[0...n-1] with n elements in total.
	 * When k = 1, the first smallest element should be A[0]. 
	 * When k = n/2, kth smallest element should be A[n/2 - 1].
	 * When k = n, nth smallest element should be A[n - 1].
	 * 
	 * @param array given array
	 * @param size size of given array
	 * @param k the index of kth smallest element in given array
	 * @return kth smallest element in given array
	 */
	public int select1(int[] array, int size, int k) {
		quickSort(array, 0, size - 1);
		return array[k - 1];
	}
	
	/**
	 * Execute quick sort.
	 * First, pick a random number as the index of pivot and declare pivot.
	 * Then, swap the start element with pivot. Now pivot is left.
	 * Declare left and right as the next index of the starting index and the ending 
	 * index, respectively. 
	 * 
	 * When partitioning, left pointer looks for the element greater than the pivot 
	 * from left to right, while right pointer looks for the element less than or 
	 * equal to the pivot from right to left. Compare the indices of these two 
	 * elements, if left is less than right, then swap these two elements and move 
	 * these two pointers forward(left pointer to right, right pointer to left). 
	 * Done with partitioning.
	 * 
	 * Swap the starting element(pivot) with the one the right pointer pointing to. 
	 * Now pivot is right. Keep calling quick sort recursively with the two 
	 * sub-arrays divided by the pivot. The ending index for the left sub-array 
	 * should be right - 1. The starting index for the right sub-array should be 
	 * right + 1. 
	 * 
	 * @param array given array
	 * @param start starting index of given array
	 * @param end ending index of given array
	 */
	private void quickSort(int[] array, int start, int end) {
		// TODO Auto-generated method stub
		if(start >= end) {
			return;
		}
		
		SecureRandom sr = new SecureRandom();
		
		/*
		 * end - start is exclusive, so add 1 to make it inclusive.
		 * Add start at the end because start may be other values in addition to 0.
		 */
		int k = sr.nextInt(end - start + 1) + start;
		
		int pivot = array[k];		
		
		/*
		 * swap array[start] and array[k]. Now pivot is left.
		 */
		int temp = array[start];
		array[start] = array[k];
		array[k] = temp;
		
		int left = start + 1;
		int right = end;
		
		/*
		 * Partitioning
		 */
		while(left <= right) {
			while(left <= right && compare(array[left], pivot) <= 0) {
				left = left + 1;
			}
			
			while(left <= right && compare(array[right], pivot) > 0) {
				right = right - 1;
			}
			
			if(left < right) {
				/*
				 * swap array[left] and array[right]
				 */
				int temp2 = array[left];
				array[left] = array[right];
				array[right] = temp2;
				
				left = left + 1;
				right = right - 1;
			}
		}
		
		/*
		 * swap array[start] and array[right]. Now array[right] is the pivot
		 */
		int temp3 = array[start];
		array[start] = array[right];
		array[right] = temp3;
		
		quickSort(array, start, right - 1);
		quickSort(array, right + 1, end);
	}

	/**
	 * Compare two values.
	 * If the first value is less than the second value, return a negative number.
	 * If the first value is greater than the second value, return a positive number.
	 * Return 0 otherwise.
	 * 
	 * @param x a value
	 * @param y the other value
	 * @return the difference between the two values above
	 */
	private int compare(int x, int y) {
		// TODO Auto-generated method stub
		compCount++;
		return x - y;
	}

	/**
	 * Randomly select a pivot. Compare the pivot with A[i].
	 * If A[i] < pivot, add A[i] to the arrayList that stores the elements less than 
	 * the pivot.
	 * If A[i] > pivot, add A[i] to the arrayList that stores the elements greater 
	 * than the pivot.
	 * Otherwise, add A[i] to the arrayList that stores the elements equal to the 
	 * pivot.
	 * Convert these 3 arraylists to array and select the kth smallest with 
	 * recursion.
	 * 
	 * @param array given array
	 * @param size size of given array
	 * @param k the index of kth smallest element in given array
	 * @return kth smallest element in given array
	 */
	public int select2(int[] array, int size, int k) {
		// TODO Auto-generated method stub
		if(size < SMALL_SIZE) {
			insertionSort(array);
			return array[k - 1];
		}else {
			int start = 0;
			int end = size - 1;
			
			SecureRandom sr = new SecureRandom();
			int pivotIndex = sr.nextInt(end - start + 1) + start;
			int pivot = array[pivotIndex];
			
			/*
			 * lists to store the elements less than, equal to, or greater than the pivot, 
			 * will be converted to array later. I declare ArrayList variables because 
			 * I am not aware of the exact sizes of elements less than, equal to, or greater 
			 * than the pivot yet.
			 */
			List<Integer> lessList = new ArrayList<Integer>();
			List<Integer> equalList = new ArrayList<Integer>();
			List<Integer> greaterList = new ArrayList<Integer>();
			
			int i = 0;
			while(i != size) {
				if(compare(array[i], pivot) < 0) {
					lessList.add(array[i]);
				}else if(compare(array[i], pivot) > 0) {
					greaterList.add(array[i]);
				}else {
					equalList.add(array[i]);
				}
				i++;
			}
			
			int n1 = lessList.size();
			int n2 = equalList.size();
			int n3 = greaterList.size();
			
			int[] lessArray = new int[n1];
			int[] equalArray = new int[n2];
			int[] greaterArray = new int[n3];
			
			lessArray = convertToArray(lessList, lessArray, n1);
			equalArray = convertToArray(equalList, equalArray, n2);
			greaterArray = convertToArray(greaterList, greaterArray, n3);
			
			if(k <= n1) {
				return select2(lessArray, n1, k);
			}else if(k <= n1 + n2) {
				return pivot;
			}else {
				return select2(greaterArray, n3, k - n1 - n2);
			}
		}
	}

	/**
	 * Convert array list to array
	 * @param list given array list
	 * @param array given sub-array, initially empty
	 * @param size the size of sub-array
	 * @return the sub-array
	 */
	private int[] convertToArray(List<Integer> list, int[] array, int size) {
		// TODO Auto-generated method stub
		for(int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * Sort an array with insertion sort.
	 * 
	 * @param array given array
	 */
	private void insertionSort(int[] array) {
		// TODO Auto-generated method stub
		int size = array.length;
		for(int i = 1; i < size; i++) {
			int j = i;
			while(j > 0 && compare(array[j], array[j - 1]) < 0) {
				int temp = array[j];
				array[j] = array[j - 1];
				array[j - 1] = temp;
				j--;
			}
		}
	}

	/**
	 * Find the kth smallest element in an array with median of medians.
	 * 
	 * Divide the set of n elements into subsets (¡°rows¡±) of size 5 each, and find 
	 * the median of each subset. (If n is not a multiple of 5, the last row will 
	 * have less than 5 elements.) To find the median of each row of 5, use insertion 
	 * sort.
     * 
     * Make a recursive call to SELECT3 to find the median of the n/5 row-medians.
     * 
     * Use this median-of-medians as the pivot to partition the array of n elements 
     * into three sets. Then make a recursive call to select3() for either the left 
     * or right partition.
	 * 
	 * @param array given array
	 * @param size size of given array
	 * @param k the index of kth smallest of given array
	 * @return kth smallest element in given array
	 */
	public int select3(int[] array, int size, int k) {		
		
		if(size < SMALL_SIZE) {
			if(size == 1) {
				return array[0];
			}
			
			insertionSort(array);
			return array[k - 1];
		}
		
		int left = 0;
		int right = size - 1;
		
		/*
		 * pointer to the right most element after array division
		 */
		int subRight;
		
		/*
		 * store median index for each row
		 */
		int medianIndex;
		
		/*
		 * store median for each row
		 */
		int median;
		
		/*
		 * store medians in each sub-group
		 */
		int[] rowMedian = new int[(size + GROUP_SIZE - 1)/GROUP_SIZE];
		
		/*
		 * Divide the array into group of group size. 
		 * floor(n/group size) groups of group size + 1 last group in total.
		 */
		for(int i = left; i <= right; i += GROUP_SIZE) {
			subRight = i + GROUP_SIZE - 1;
			if(subRight > right) {
				subRight = right;
			}
			
			/*
			 * Sort the sub-group with insertion sort
			 */
			insertionSort(array, i, subRight);
			
			medianIndex = (i + subRight) >> 1;
			median = array[medianIndex];	
		    rowMedian[i/GROUP_SIZE] = median;
		}
		
		/*
		 * Use median of medians to find the pivot with recursion.
		 */
		int pivot = select3(rowMedian, 
				Math.floorDiv(size, GROUP_SIZE), 
				Math.floorDiv(size, GROUP_SIZE << 1));
		
		/*
		 * lists to store the elements less than, equal to, or greater than the pivot, 
		 * will be converted to array later. I declare ArrayList variables because 
		 * I am not aware of the exact sizes of elements less than, equal to, or greater 
		 * than the pivot yet.
		 */
		List<Integer> lessList = new ArrayList<Integer>();
		List<Integer> equalList = new ArrayList<Integer>();
		List<Integer> greaterList = new ArrayList<Integer>();
		
		int i = 0;
		while(i != size) {
			if(compare(array[i], pivot) < 0) {
				lessList.add(array[i]);
			}else if(compare(array[i], pivot) > 0) {
				greaterList.add(array[i]);
			}else {
				equalList.add(array[i]);
			}
			i++;
		}
		
		int n1 = lessList.size();
		int n2 = equalList.size();
		int n3 = greaterList.size();
		
		int[] lessArray = new int[n1];
		int[] equalArray = new int[n2];
		int[] greaterArray = new int[n3];
		
		lessArray = convertToArray(lessList, lessArray, n1);
		equalArray = convertToArray(equalList, equalArray, n2);
		greaterArray = convertToArray(greaterList, greaterArray, n3);
		
		if(k <= n1) {
			return select3(lessArray, n1, k);
		}else if(k <= n1 + n2) {
			return pivot;
		}else {
			return select3(greaterArray, n3, k - n1 - n2);
		}
	}

	/**
	 * Sort the array group with at most (group size) elements using insertion sort.
	 * 
	 * @param array given array
	 * @param left the index on the left of the array
	 * @param right the index on the right of the array
	 */
	private void insertionSort(int[] array, int left, int right) {
		// TODO Auto-generated method stub
		for(int i = left + 1; i < right + 1; i++) {
			int j = i;
			while(j > left && compare(array[j], array[j - 1]) < 0) {
				int temp = array[j];
				array[j] = array[j - 1];
				array[j - 1] = temp;
				j--;
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(FileOutputStream fos = new FileOutputStream("outputProduced.txt"); 
			PrintStream out = new PrintStream(fos)) {
			init(SIZE_10000, out);
			init(SIZE_100000, out);
			init(SIZE_1000000, out);
			out.flush();
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void init(int size, PrintStream out) {
		// TODO Auto-generated method stub
		/**
		 * randomly generated array
		 */
		int[] array = new int[size];
		int[] array2 = new int[size];
		int[] array3 = new int[size];
		
		SecureRandom sr = new SecureRandom();
		for(int i = 0; i < size; i++) {
			array[i] = sr.nextInt(size);
		}
		
		System.arraycopy(array, 0, array2, 0, size);
		System.arraycopy(array, 0, array3, 0, size);
		
		int k;
		if(array.length % 2 == 0) {
			k = array.length >> 1;
		}else {
			k = (array.length >> 1) + 1;
		}
		
		Select select1 = new Select(array, array.length);
		int kthSmallest1 = select1.select1(array, select1.getSize(), k);
		System.out.println("Algorithm 1: " + select1.getSize() + "," + k + "," 
		    + kthSmallest1 + " " + select1.getCompCount());
		out.println("Algorithm 1: " + select1.getSize() + "," + k + "," 
			+ kthSmallest1 + " " + select1.getCompCount());
		
		Select select2 = new Select(array2, array2.length);
		int kthSmallest2 = select2.select2(array2, select2.getSize(), k);
		System.out.println("Algorithm 2: " + select2.getSize() + "," + k + "," 
		    + kthSmallest2 + " " + select2.getCompCount());
		out.println("Algorithm 2: " + select2.getSize() + "," + k + "," 
		    + kthSmallest2 + " " + select2.getCompCount());
		
		Select select3 = new Select(array3, array3.length);
		int kthSmallest3 = select3.select3(array3, select3.getSize(), k);
		System.out.println("Algorithm 3: " + select3.getSize() + "," + k + "," 
		    + kthSmallest3 + " " + select3.getCompCount());
		out.println("Algorithm 3: " + select3.getSize() + "," + k + "," 
		    + kthSmallest3 + " " + select3.getCompCount());
		
		System.out.println();
		out.println();
	}
}
