/** 
 * Problem statement 
 * https://www.hackerrank.com/challenges/stockmax
 */


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public ArrayList<BigInteger> stockPrices;
    public BigInteger maxProfit;
    
    public static void main(String[] args) {        
        //Getting the inputs
        Scanner in = new Scanner(System.in);
        int noOfInputs = Integer.parseInt(in.nextLine());
        for(int i = 0; i < noOfInputs; i++) {
            Solution solution = new Solution();
            solution.stockPrices = new ArrayList<BigInteger>();
            solution.maxProfit = BigInteger.valueOf(0);
            
            String noOfItems = in.nextLine();
            String inputString = in.nextLine();
            for(String stockPrice: inputString.split(" ")) {
              solution.stockPrices.add(new BigInteger(stockPrice));
            }
            solution = calculateMaxProfit(solution);
            System.out.println(solution.maxProfit);
        }
        ArrayList<BigInteger> stockPrices = new ArrayList<BigInteger>();
    }
    
    /**
     * This is how the logic works 
     *
     * Come from the last element. Check if there is any element, which is lesser than the current element.
     * If, then calculate the profit in that transaction. Sum it up from the last to second element
     */
  public static Solution calculateMaxProfit(Solution solution) {
      //Iterating in reverse order.
      ListIterator<BigInteger> itr = solution.stockPrices.listIterator(solution.stockPrices.size());
      while(solution.stockPrices.size() > 1) {
          solution = getLastProfit(solution);
      }
      return solution;
  }
  
  /**
   * Updates the maxProfit attribute of Solution object and returns it.
   */
  public static Solution getLastProfit(Solution solution) {
     ListIterator<BigInteger> itr = solution.stockPrices.listIterator(solution.stockPrices.size()-1);
     BigInteger lastStockPrice = solution.stockPrices.get(solution.stockPrices.size() - 1);
     int noOfStocksBought = 0;
     int lastStockPriceBoughtIndex = 0; 
     BigInteger moneySpent = BigInteger.valueOf(0);
     while(itr.previousIndex() >= 0) {
       int stockPriceIndex = itr.previousIndex();  
       BigInteger currentStockPrice = itr.previous();
       if(lastStockPrice.compareTo(currentStockPrice) == 1) {
           noOfStocksBought++;
           moneySpent = moneySpent.add(currentStockPrice);
           lastStockPriceBoughtIndex = stockPriceIndex;
          
       } else {
           break;
       }
     }
      
     BigInteger moneyEarned = BigInteger.valueOf(noOfStocksBought).multiply(lastStockPrice);
      
      //If the buy of current stock is profitable, then return the profit.
     //Other wise return 0. Because buying this stock will be a shit choice.
     if(moneyEarned.compareTo(moneySpent) == 1) {
         BigInteger profit = moneyEarned.subtract(moneySpent);
         solution.maxProfit = solution.maxProfit.add(profit);
         for(int i = solution.stockPrices.size()-1; i>=lastStockPriceBoughtIndex; i--) {
            solution.stockPrices.remove(i);  
         }
     } else {
         solution.stockPrices.remove(solution.stockPrices.size() - 1);
     }
     
     return solution;
  }   
}