import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public ArrayList<ArrayList> maxArrayIndexSolns = new ArrayList<ArrayList>();
    public static ArrayList<BigInteger> inputArray;
    public BigInteger maxContinousSum;
    public BigInteger maxSum;
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
       inputArray = new ArrayList();
       //2 -1 2 3 4 -5
       Scanner in = new Scanner(System.in);
       String noOfInputsString = in.nextLine();
       int noOfInputs = Integer.parseInt(noOfInputsString);
       for(int i=0; i<noOfInputs; i++) {
         int noOfElements = Integer.parseInt(in.nextLine());
         inputArray = new ArrayList();
         String inputString = in.nextLine();
         String[] inputStringElements = inputString.split(" ");
         ArrayList<BigInteger> inputElements = new ArrayList();
         for(int j=0;j<inputStringElements.length;j++) {
           BigInteger inputElement = new BigInteger(inputStringElements[j]);    
           inputArray.add(inputElement);
         }
         
         Solution s = new Solution();
         Solution result = s.getMaxSum(0, noOfElements-1);
         System.out.println(result.maxContinousSum + " " + s.getMaxPositiveSum());
       }
        
    }
    
    
    /**
     * Gets the max positive sum.
     */
    public BigInteger getMaxPositiveSum() {
      BigInteger max = inputArray.get(0);
      BigInteger sum = BigInteger.valueOf(-10000);
      BigInteger lowValue = BigInteger.valueOf(-10000);
      for(int i=0; i<inputArray.size(); i++) {
        int compareStatus = inputArray.get(i).compareTo(BigInteger.valueOf(0));
        //Add only if its positive integer
        // Calculating sum
        if(compareStatus>=0) {
          if(sum.equals(lowValue)) {
            sum = BigInteger.valueOf(0);
            sum = sum.add(inputArray.get(i));
          } else {
            sum = sum.add(inputArray.get(i));  
          }          
        }
        //Calculating the max
        compareStatus = inputArray.get(i).compareTo(max);
        if(compareStatus > 0) {
          max = inputArray.get(i);    
        }
      }
      return max.max(sum);
    }
    
    public Solution getMaxSum(int startIndex, int endIndex) {
      int arrayLength = endIndex-startIndex;
      int midElementIndex = startIndex + arrayLength/2;
      Solution result = new Solution();
    
        
      //Condition when there is only one element.  
      if(startIndex == endIndex) {
        ArrayList<ArrayList> maxArrayIndexSolns = new ArrayList<ArrayList>();
        ArrayList<BigInteger> maxArrayIndexSoln = new ArrayList<BigInteger>();
        maxArrayIndexSoln.add(BigInteger.valueOf(startIndex));
        
        maxArrayIndexSolns.add(maxArrayIndexSoln);
        result.maxArrayIndexSolns = maxArrayIndexSolns;
        result.maxContinousSum = inputArray.get(startIndex);
        return result;
      }  
      
      //Getting solutions from left part and right part.    
      Solution leftSoln = this.getMaxSum(startIndex, midElementIndex);
      Solution rightSoln = this.getMaxSum(midElementIndex+1, endIndex);
      
      //Calculate the thirdsum. 
        
      //That is connecting sum of the left half and right half
      //The third Sum is calculated by getting extreme right soln in first part and extreme left soln
      // in right part.    
      BigInteger thirdSum = BigInteger.valueOf(-10000);
      ArrayList<BigInteger> thirdSoln = new ArrayList<BigInteger>();
     
      ArrayList<BigInteger> leftLastSoln = leftSoln.maxArrayIndexSolns.get(leftSoln.maxArrayIndexSolns.size()-1);
      ArrayList<BigInteger> rightFirstSoln = rightSoln.maxArrayIndexSolns.get(0);
      
      BigInteger leftLast = leftLastSoln.get(leftLastSoln.size()-1);
      BigInteger rightFirst = rightFirstSoln.get(0);
      
      if(leftLast.subtract(rightFirst).abs().equals(BigInteger.valueOf(1))) {
        
        thirdSum = rightSoln.maxContinousSum.add(leftSoln.maxContinousSum);
        //thirdSum = thirdSum.add(rightSoln.maxContinousSum);
        thirdSoln.addAll(leftLastSoln);
        thirdSoln.addAll(rightFirstSoln);
       
      } else {
        //Calculating the middle sum.
        BigInteger middleSum = BigInteger.valueOf(0);
        ArrayList<BigInteger> middleSoln = new ArrayList<BigInteger>();
        for(int i=leftLast.intValue() + 1; i<rightFirst.intValue(); i++) {
          
          middleSum = middleSum.add(inputArray.get(i));  
          middleSoln.add(BigInteger.valueOf(i));
        }
          
        thirdSum = rightSoln.maxContinousSum.add(leftSoln.maxContinousSum);
        thirdSum = thirdSum.add(middleSum);
        thirdSoln.addAll(leftLastSoln);
        thirdSoln.addAll(middleSoln);
        thirdSoln.addAll(rightFirstSoln);
      }
      
      // Calculate the maximum
      BigInteger max = leftSoln.maxContinousSum.max(rightSoln.maxContinousSum);
      max = max.max(thirdSum);
      result.maxContinousSum = max;
        
      //Getting the solns.
      ArrayList<ArrayList> solns = new ArrayList<ArrayList>();
      if(leftSoln.maxContinousSum.equals(max)) {
        solns.addAll(leftSoln.maxArrayIndexSolns);    
      }
      if(thirdSum.equals(max)) {
        solns.add(thirdSoln);    
      }
      if(rightSoln.maxContinousSum.equals(max)) {
        solns.addAll(rightSoln.maxArrayIndexSolns);    
      }
      result.maxArrayIndexSolns = solns;  
      return result;
    }
}