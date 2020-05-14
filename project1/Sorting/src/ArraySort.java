import java.security.SecureRandom;
import java.util.Arrays;

public class ArraySort {
	/**
	 * Store given array.
	 */
	int[] array;
	
	/**
	 * Store the size of the array.
	 */
	int size;
	
	/**
	 * Count the number of key comparisons.
	 */
	int compCount = 0;
	
	public ArraySort(int[] array, int size) {
		this.array = array;
		this.size = size;
	}
	
	public void setArray(int[] array) {
		this.array = array;
	}
	
	public int[] getArray() {
		return array;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setCompCount(int compCount) {
		this.compCount = compCount;
	}
	
	public int getCompCount() {
		return compCount;
	}
	
	/**
	 * Execute merge sort. 
	 * When the start index of given array is equal to the end index of given array or
	 * the size of given array is 0 or 1, return the given array.
	 * 
	 * Otherwise, do:
	 * Divide the array into two halves;
	 * Sort each half recursively;
	 * Merge the two sorted halves.
	 * 
	 * @param array given array
	 * @param startIndex start index of given array
	 * @param endIndex end index of given array
	 * @param size size of given array
	 * @return array array that has been sorted
	 */
	public int[] mergeSort(int[] array, int startIndex, int endIndex, 
			int size) {
		if(startIndex == endIndex || size < 2) {
			return array;
		}else {
			/*
			 * extract the left and the right halves of array
			 */
			int[] left = extract(array, 0, size/2);
			int[] right = extract(array, size/2, size);
			
			int middle = (startIndex + endIndex)/2;
			int[] leftArray = mergeSort(left, startIndex, middle, size/2);
			int[] rightArray = mergeSort(right, middle + 1, endIndex, size/2);
			int[] result = new int[size];
			
			/*
			 * leftArray.length - 1 and rightArray.length - 1 refer to the end indices of left
			 * array and right array, respectively
			 */
			array = merge(leftArray, leftArray.length, rightArray, rightArray.length, 
					result);
			return array;
		}
	}

	/**
	 * Returns a sub array of elements, starting with index start in elements, continuing 
	 * to (but not including) end
	 * 
	 * @param elements: given array
	 * @param start: start index of given array
	 * @param end: end index of given array
	 * @return toArray: either the left half or the right half of the given array
	 */
	private int[] extract(int[] elements, int start, int end) {
		// TODO Auto-generated method stub
		int[] toArray = new int[end - start];
		int size = toArray.length;
		for(int i = 0; i < size; i++) {
			toArray[i] = elements[start + i];
		}
		return toArray;
	}

	/**
	 * Merge two sorted arrays. Inputs are sorted arrays s1[0 : m - 1] and s2[0 : n - 1] for 
	 * size of s1 = m and size of s2 = n. 
	 * Output is sorted result result[0 : m + n]
	 * 
	 * @param leftArray the left half of given array
	 * @param lastIndexLeftArray the last index of given array
	 * @param rightArray the right half of given array
	 * @param lastIndexRightArray the last index of given array
	 * @param result sorted result
	 * @return result
	 */
	private int[] merge(int[] leftArray, int LeftArraySize, int[] rightArray, 
			int rightArraySize, int[] result) {
		// TODO Auto-generated method stub
		/*
		 * index into left array
		 */
		int i = 0;
		
		/*
		 * index into right array
		 */
		int j = 0;
		
		/*
		 * index into result array
		 */
		int k = 0;
		
		while(LeftArraySize - 1 >= i && rightArraySize - 1 >= j) {
			if(isLessThanOrEqualTo(leftArray[i], rightArray[j])) {
				result[k] = leftArray[i];
				i++;
			}else {
				result[k] = rightArray[j];
				j++;
			}
			k++;
		}
		
		/*
		 * empty remaining of left array
		 */
		while(LeftArraySize - 1 >= i) {
			result[k] = leftArray[i];
			i++;
			k++;
		}
		
		/*
		 * empty remaining of right array
		 */
		while(rightArraySize - 1 >= j) {
			result[k] = rightArray[j];
			j++;
			k++;
		}
		return result;
	}
	
	/**
	 * Execute heap sort.
	 * First, build heap.
	 * Then, keep deleting the first element of the array 
	 * and putting this value to the end of the array until 
	 * the pointer points to the end of the array.
	 * 
	 * @param array given array
	 * @param size the size of given array
	 * @return result array that has been sorted
	 */
	public int[] heapSort(int[] array, int size) {
		buildHeap(array, size);
		int[] result = new int[size];
		for (int i = 0; i <= size - 1; i++) {
			result[i] = deleteMin(array, size - i - 1);  
		}
		return result;
	}

	/**
	 * Delete the first element of the array and push this element to the end of the array. 
	 * The size of the array decrements before the element is pushed down to the end of the array.
	 * 
	 * @param array given array
	 * @param endIndex the last index of shrinking array
	 * @return temp value that has been deleted
	 */
	private int deleteMin(int[] array, int endIndex) {
		// TODO Auto-generated method stub
		int temp = array[0];
		array[0] = array[endIndex];
		endIndex = endIndex - 1;
		pushDown(array, 0, endIndex);
		return temp;
	}

	/**
	 * Build heap for an array.
	 * 
	 * @param array given array
	 * @param size the size of given array
	 */
	private void buildHeap(int[] array, int size) {
		// TODO Auto-generated method stub
		for (int i = size / 2; i >= 1; i--) {
			pushDown(array, i - 1, size - 1); 
		}
	}

	/**
	 * At the start of the heap, the root should be 0. When the root becomes r, its left child and right
	 * child should be 2r + 1 and 2r + 2, respectively.
	 * 
	 * When it is leaf, return.
	 * When it just has a left child, or its left child is equal to its right child, the smaller child 
	 * should be 2r + 1.
	 * Otherwise, the smaller child should be 2r + 2.
	 * When the root is less than or equal to its smaller child, return.
	 * Otherwise, swap root and smaller child and keep pushing down with its root in place of smaller
	 * child.
	 * 
	 * @param array given array
	 * @param root the index that is referred as the parent of two child nodes. Can also be leaf. 
	 * @param size the size of given array
	 */
	private void pushDown(int[] array, int root, int size) {
		// TODO Auto-generated method stub
		if (2 * root + 1 > size) { // Leaf
			return;
		}

		int smallerChild;
		if ((2 * root + 1 == size) || isLessThanOrEqualTo(array[2 * root + 1], 
				array[2 * root + 2])) {
			smallerChild = 2 * root + 1;
		} else {
			smallerChild = 2 * root + 2;
		}

		if (isLessThanOrEqualTo(array[root], array[smallerChild])) {
			return;
		}

		/*
		 * swap array[root] and array[smallerChild]
		 */
		int temp = array[root];
		array[root] = array[smallerChild];
		array[smallerChild] = temp;

		pushDown(array, smallerChild, size);
	}

	/**
	 * Execute quick sort.
	 * First, pick a random number as the index of pivot and declare pivot.
	 * Then, swap the start element with pivot. Now pivot is left.
	 * Declare left and right as the next index of the starting index and the ending index, 
	 * respectively. 
	 * 
	 * When partitioning, left pointer looks for the element greater than the pivot from left to right, 
	 * while right pointer looks for the element less than or equal to the pivot from right to left.
	 * Compare the indices of these two elements, if left is less than right, then swap these two 
	 * elements and move these two pointers forward(left pointer to right, right pointer to left). 
	 * Done with partitioning.
	 * 
	 * Swap the starting element(pivot) with the one the right pointer pointing to. Now pivot is right.
	 * Keep calling quick sort recursively with the two sub-arrays divided by the pivot. The ending 
	 * index for the left sub-array should be right - 1. The starting index for the right sub-array 
	 * should be right + 1. 
	 * 
	 * @param array given array
	 * @param start starting index of given array
	 * @param end ending index of given array
	 */
	public void quickSort(int[] array, int start, int end) {
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
			while(left <= right && isLessThanOrEqualTo(array[left], pivot)) {
				left = left + 1;
			}
			
			while(left <= right && isGreaterThan(array[right], pivot)) {
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
		}//end of outer while loop
		
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
	 * Increment the number of comparisons and check if an element is less than or equal to another.
	 * 
	 * @param x an element
	 * @param y another element
	 * @return true if x is less than or equal to y. Otherwise false.
	 */
	private boolean isLessThanOrEqualTo(int x, int y) {
		compCount++;
		return x <= y;
	}
	
	/**
	 * Increment the number of comparisons and check if an element is greater than another.
	 * 
	 * @param x an element
	 * @param y another element
	 * @return true if x is greater than y. Otherwise false.
	 */
	private boolean isGreaterThan(int x, int y) {
		compCount++;
		return x > y;
	}
	
	/**
	 * Size of array is 32.
	 */
	private static final int SIZE_32 = 32;
	
	/**
	 * Size of array is 2^10 = 2 << 9 = 1024.
	 */
	private static final int SIZE_1024 = 1024;
	
	/**
	 * Size of array is 2^15 = 2 << 14 = 32768.
	 */
	private static final int SIZE_32768 = 32768;
	
	/**
	 * Size of array is 2^20 = 2 << 19 = 1048576.
	 */
	private static final int SIZE_1048576 = 1048576;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Sorted array
		 */
		int[] arraySorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 
				20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
		int[] arraySorted2 = new int[SIZE_32];
		/*
		 * copy everything from array to another array because the original array has been changed
		 * after heap sort.
		 */
		copy(arraySorted, arraySorted2, SIZE_32);
		printSortedArrayAndNumberOfComparisonsMergeSort(arraySorted);
		printSortedArrayAndNumberOfComparisonsHeapSort(arraySorted);
		printSortedArrayAndNumberOfComparisonsQuickSort(arraySorted2);
		
		/**
		 * Reversely sorted array
		 */
		int[] arrayReverselySorted = {32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19,
				18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
		int[] arrayReverselySorted2 = new int[SIZE_32];
		copy(arrayReverselySorted, arrayReverselySorted2, SIZE_32);
		printSortedArrayAndNumberOfComparisonsMergeSort(arrayReverselySorted);
		printSortedArrayAndNumberOfComparisonsHeapSort(arrayReverselySorted);
		printSortedArrayAndNumberOfComparisonsQuickSort(arrayReverselySorted2);

		
		/**
		 * randomly generated array
		 */
		int[] generatedArray = new int[SIZE_32];
		
		SecureRandom sr = new SecureRandom();
		for(int i = 0; i < SIZE_32; i++) {
			generatedArray[i] = sr.nextInt(SIZE_32);
		}
		System.out.println("input randomly generated array: " + Arrays.toString(generatedArray));
		
		int[] generatedArray2 = new int[SIZE_32];
		copy(generatedArray, generatedArray2, SIZE_32);
		printSortedArrayAndNumberOfComparisonsMergeSort(generatedArray);
		printSortedArrayAndNumberOfComparisonsHeapSort(generatedArray);
		printSortedArrayAndNumberOfComparisonsQuickSort(generatedArray2);

		generateArrayRandomly(SIZE_1024);
		generateArrayRandomly(SIZE_32768);
		generateArrayRandomly(SIZE_1048576);
	}
	
	private static void generateArrayRandomly(int size) {
		System.out.println("The size of array is " + size);
		
		int[] generatedArray = new int[size];
		
		SecureRandom sr = new SecureRandom();
		for(int i = 0; i < size; i++) {
			generatedArray[i] = sr.nextInt(size);
		}
		
		int[] generatedArray2 = new int[size];
		copy(generatedArray, generatedArray2, size);
		printRunningTimeAndNumberOfComparisonsMergeSort(generatedArray);
		printRunningTimeAndNumberOfComparisonsHeapSort(generatedArray);
		printRunningTimeAndNumberOfComparisonsQuickSort(generatedArray2);
	}

	private static void printRunningTimeAndNumberOfComparisonsQuickSort(int[] array) {
		// TODO Auto-generated method stub
		ArraySort as = new ArraySort(array, array.length);
		long quickSortStartTime = System.currentTimeMillis();
		as.quickSort(array, 0, as.getSize() - 1);
		long quickSortEndTime = System.currentTimeMillis();
		System.out.println("The running time for quick sort is " + 
		(quickSortEndTime - quickSortStartTime)+ "ms");
		System.out.println("The number of key comparisons is " + as.getCompCount() + "\n");
	}

	private static void printRunningTimeAndNumberOfComparisonsHeapSort(int[] array) {
		// TODO Auto-generated method stub
		ArraySort as = new ArraySort(array, array.length);
		long quickSortStartTime = System.currentTimeMillis();
		as.heapSort(array, array.length);
		long quickSortEndTime = System.currentTimeMillis();
		System.out.println("The running time for heap sort is " + 
		(quickSortEndTime - quickSortStartTime) + "ms");
		System.out.println("The number of key comparisons is " + as.getCompCount());
	}

	private static void printRunningTimeAndNumberOfComparisonsMergeSort(int[] array) {
		// TODO Auto-generated method stub
		ArraySort as = new ArraySort(array, array.length);
		long quickSortStartTime = System.currentTimeMillis();
		as.mergeSort(as.getArray(), 0, as.getSize() - 1, as.getSize());
		long quickSortEndTime = System.currentTimeMillis();
		System.out.println("The running time for merge sort is " + 
		(quickSortEndTime - quickSortStartTime) + "ms");
		System.out.println("The number of key comparisons is " + as.getCompCount());
	}

	private static void printSortedArrayAndNumberOfComparisonsQuickSort(int[] array) {
		// TODO Auto-generated method stub
		ArraySort as = new ArraySort(array, array.length);
		as.quickSort(array, 0, as.getSize() - 1);
		System.out.println("Quick sort with dataset: " + Arrays.toString(array));
		System.out.println("The number of key comparisons is " + as.getCompCount() + "\n");
	}

	private static void printSortedArrayAndNumberOfComparisonsHeapSort(int[] array) {
		// TODO Auto-generated method stub
		ArraySort as = new ArraySort(array, array.length);
		int[] result = as.heapSort(array, array.length);
		System.out.println("Heap sort with dataset: " + Arrays.toString(result));
		System.out.println("The number of key comparisons is " + as.getCompCount());
	}

	private static void printSortedArrayAndNumberOfComparisonsMergeSort(int[] array) {
		// TODO Auto-generated method stub
		ArraySort as = new ArraySort(array, array.length);
		int[] result = as.mergeSort(as.getArray(), 0, as.getSize() - 1, as.getSize());
		System.out.println("Merge sort with dataset: " + Arrays.toString(result));
		System.out.println("The number of key comparisons is " + as.getCompCount());
	}

	private static void copy(int[] array, int[] array2, int size) {
		// TODO Auto-generated method stub
		for(int i = 0; i < size; i++) {
			array2[i] = array[i];
		}
	}

}
