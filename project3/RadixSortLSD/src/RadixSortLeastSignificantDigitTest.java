import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RadixSortLeastSignificantDigitTest {
	/**
	 * 26 upper-case letters for A~Z 
	 */
	public static final int RADIX = 26;
	
	/**
	 * maximum length that each string has.
	 */
	public static final int K = 21;
	
	/**
	 * There are two arguments, input file name and output file name.
	 * This program first checks if the size of input arguments is 2. If it is less
	 * than 2, then print "Please enter valid number of input arguments" and quit.
	 * Otherwise, it will handle the first 2 arguments.
	 * 
	 * User can arbitrarily change the names of the files. The default of files is
	 * set to f and g, as well as formatted with .txt. They turn out to be f.txt and
	 * g.txt. If the file name does not exist, then a new file will be created for
	 * reading. The content for the new file will be copied from f.txt.
	 * 
	 * Then go through the entire file to obtain size. Declare and initialize a 2D
	 * array to store the file and a pointer array to assign it with p[i] = i. Print
	 * out the unsorted 2D array.
	 * 
	 * Then link each element of the pointer array with each line of the 2D array.
	 * Pad each string shorter than k = 21 with whitespace to k = 21. Create an array
	 * to store all values in the 2D array. Use this array to sort by Least 
	 * Significant Digit.
	 * 
	 * Finally print out the sorted array and write these arrays to the output file
	 * with a line for a string whose whitespace is being removed.
	 * 
	 * @param args arguments of the program. Input file name and output file name.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 2) {
			System.out.println("Please specify the input file (default = f.txt ).");
			System.out.println("Please specify the output file (default = g.txt ).");
			return;
		}
		
		String defaultName = "f.txt";
		
		/*
		 * Read file. If a file with specified name does not exist, then create a new
		 * file and copy the content from f.txt to the new file. Name should be specified 
		 * in command line. Default: f.txt
		 */
		String readInFileName = args[0];
		File inputFile = new File(readInFileName);
		if(!inputFile.exists()) {
			try {
				inputFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try(BufferedReader in = new BufferedReader(new FileReader(defaultName)); 
					BufferedWriter out = new BufferedWriter(new FileWriter(readInFileName))) {
				String s;
	 
				while ((s = in.readLine()) != null) { // read a line
					out.write(s);// write to output file
					out.newLine();
					out.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int lineCounter = obtainFileSize(inputFile);
        int n = lineCounter;
		char[][] s = new char[n][K];
		
		try(BufferedReader in = new BufferedReader(new FileReader(inputFile))) {
			/*
			 * Represent input file as a stream of strings, map each string to an array of 
			 * char values, and collect stream as a 2D array of char values.
			 */
	        Stream<String> fileLines = in.lines();
	        s = fileLines.map(String::toCharArray).toArray(char[][]::new);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(s.length == 0) {
			System.out.println("The default file is not in directory. New file is empty");
			return;
		}
		
		printSplitLines();
        System.out.println("\nUnsorted array is:");
        printUnsortedArray(s);
        
        /*
         * pointer array p, initially assign p[i] = i. After sorting, P[i] will be the 
         * index of the string which is the ith in the sorted order.
         */
        int[] p = new int[n];
        for(int i = 0; i < n; i++) {
        	p[i] = i;
        }
        
        /*
         * link pointer array value with lines of strings
         */
        HashMap<Integer, String> mapSP = new HashMap<>();
        
        /*
         * Link every pointer to string and store the entire data into a string. If a 
         * string is shorter than k = 21, then pad it to the fixed length of k = 21 with 
         * whitespace.
         */
        String[] entireData = new String[n];
        for(int i = 0; i < n; i++) {
        	String stringLine = String.valueOf(s[i]);
        	if(stringLine.length() < K) {
        		stringLine = String.format("%-21s", stringLine);
        	}
        	mapSP.put(p[i], stringLine);
        	entireData[i] = stringLine;
        }
        
        LeastSignificantDigitSort(entireData, mapSP, p);
        
        printSplitLines();
        System.out.println("\nSorted array is:");
        printSortedString(entireData);
        
        /*
         * Write file. Name should be also specified in command line similar to read
         * file. Default: g.txt. 
         */
        String writeOutFileName = args[1];
		try(BufferedWriter out = new BufferedWriter(new FileWriter(writeOutFileName))) {
			int fileSize = entireData.length;
	        for(int i = 0; i < fileSize - 1; i++) {
	        	out.write(entireData[i].trim());
	        	out.newLine();
	        }
	        out.write(entireData[fileSize - 1].trim());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

	private static void printSplitLines() {
		// TODO Auto-generated method stub
		for(int i = 0; i < 80; i++) {
			System.out.print("-");
		}
	}

	/**
	 * print unsorted 2D array.
	 * 
	 * @param s 2D array
	 */
	private static void printUnsortedArray(char[][] s) {
		// TODO Auto-generated method stub
		int n = s.length;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < s[i].length; j++) {
				System.out.print(s[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * print sorted array with whitespace being removed.
	 * 
	 * @param entireData sorted array
	 */
	private static void printSortedString(String[] entireData) {
		// TODO Auto-generated method stub
		int size = entireData.length;
		for(int i = 0; i < size - 1; i++) {
			System.out.println(entireData[i].trim());
		}
		System.out.print(entireData[size - 1].trim());
	}

	/**
	 * Obtain file size because we only know that the maximum size should be 1000, 
	 * but it consumes too many space if the size is far less than 1000.
	 * 
	 * @param fileName
	 * @return
	 */
	private static int obtainFileSize(File fileName) {
		// TODO Auto-generated method stub
		int lineCounter = 0;
		try (BufferedReader in = new BufferedReader(new FileReader(fileName))){
			while(in.readLine() != null) {
	        	lineCounter++;
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return lineCounter;
	}

	/**
	 * Sort string with Least Significant Digit.
	 * 
	 * First, create an array with size n to store the strings after each iteration 
	 * of sorting. 
	 * 
	 * Then sort by key-indexed counting on the specified character.
	 * Loop:
	 * Use a pointer for the array above to add strings to specific location.
	 * Create a bucket for each letter as keys and for a list of strings for each 
	 * value, where a letter should be mapped to a list of strings.
	 * Determine which strings can be added to the bucket. If the specified character
	 * is a whitespace, then the corresponding string will be directly added to the 
	 * array above. Otherwise, add it to the bucket.
	 * Iterate through the bucket, extract all values from bucket, and add these 
	 * values to the array above.
	 * Overwrite the results from the array above to the original(input) array.
	 * Iterate through entire data. If an element matches with the value of initial 
     * map, then extract the corresponding key(s) of the value. 
     * Remove duplicated key values above and keep the number of duplicated key to be
     * 1.
     * Finally overwrite this key to the original(input) pointer array.
	 * Done with loop.
	 * 
	 * @param entireData array that stores all strings in file
	 * @param map map that stores pointer values being mapped to strings
	 * @param pointerArray pointer array
	 */
	private static void LeastSignificantDigitSort(String[] entireData, 
		HashMap<Integer, String> map, int[] pointerArray) {
		int n = entireData.length;
		
		/*
		 * sorted array by the jth character
		 */
		String[] temp = new String[n];
		
		int pointerIntegerListSize;
		
		/*
		 * sort by key-indexed counting on jth character
		 */
		for(int j = K - 1; j >= 0; j--) {
		    /*
		     * count pointer for temp to add string 
		     */
			int count = 0;
			
			/*
			 * bucket
			 */
			Map<Character, List<String>> bucket = new HashMap<>(RADIX);
			
			/*
			 * create buckets and initialize each with a new empty List<String>
			 */
			char a = 'A';
			for(int i = 0; i < RADIX; i++, a++) {
				bucket.put(a, new ArrayList<>());
			}
			
			/*
			 * determine which strings to be added into bucket.
			 * If the jth character is whitespace, then the corresponding string will be 
			 * directly added to temp. Otherwise add it to the bucket. 
			 */
			for(int i = 0; i < n; i++) {
				if(entireData[i].charAt(j) == ' ') {
					temp[count] = entireData[i];
					count++;
				}else {
					bucket.get(entireData[i].charAt(j)).add(entireData[i]);
				}
			}
			
			/*
			 * Iterate through bucket, extract all values from bucket, and add these values 
			 * to temp.
			 */
			for(List<String> listStringValue : bucket.values()) {
				for(int i = 0; i < listStringValue.size(); i++) {
					String listStringElement = listStringValue.get(i);
					if(listStringElement != null) {
						temp[count] = listStringElement;
						count++;
					}
				}
			}
			
			/*
			 * copy the result string array(temp) to the original array to rewrite
			 */
			System.arraycopy(temp, 0, entireData, 0, n);
			
			/*
			 * Iterate through entire data. If an element matches with the value of initial
			 * map, then extract the corresponding key(s) of the value. 
			 */
			List<Integer> pointerList = new ArrayList<>();
			for(int i = 0; i < n; i++) {
				Set<Integer> keys = obtainKeys(map, entireData[i]);
				
				for(Integer integer : keys) {
					pointerList.add(integer);
				}
			}
			
			/*
			 * Remove the duplicate values of keys and overwrite them into pointer array
			 */
			List<Integer> pointerListWithoutDuplicates = pointerList.stream()
																	.distinct()
																	.collect(Collectors.toList());
			pointerIntegerListSize = pointerListWithoutDuplicates.size();
			for(int i = 0; i < pointerIntegerListSize; i++) {
				pointerArray[i] = pointerListWithoutDuplicates.get(i);
			}
		}
	}

	/**
	 * Obtain key from values. For each value, add its corresponding keys to a set.
	 * 
	 * @param map map that stores pointer values being mapped to strings
	 * @param value value of map
	 * @return set of keys
	 */
	private static Set<Integer> obtainKeys(HashMap<Integer, String> map, 
	    String value) {
		// TODO Auto-generated method stub
		Set<Integer> keys = new HashSet<Integer>();
	    for (Map.Entry<Integer, String> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	        	Integer key = entry.getKey();
	            keys.add(key);
	        }
	    }
	    return keys;
	}

}
