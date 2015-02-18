import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    //This variable contains all the combinations for specific no of 4 sized blocks
    //public Map<<BigInteger>,<ArrayList>> solns;
    public ArrayList<BigInteger> combination;
    public static int counter=0;
    public static HashMap<ArrayList,ArrayList> solns;
    public static HashMap<BigInteger,ArrayList> solnsForNBlocks;
    public int n;
    
    public Solution() {
      solnsForNBlocks = new HashMap<BigInteger,ArrayList>();
      solns = new HashMap<ArrayList,ArrayList>();
      combination = new ArrayList<BigInteger>();
    }
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        solns = new HashMap<ArrayList,ArrayList>();
        Scanner in = new Scanner(System.in);
        int noOfInputs = Integer.parseInt(in.nextLine());
        ArrayList<BigInteger> combination = new ArrayList<BigInteger>();
        combination.add(BigInteger.valueOf(1));
        combination.add(BigInteger.valueOf(2));
        combination.add(BigInteger.valueOf(1));
        combination.add(BigInteger.valueOf(1));
        
        Solution s = new Solution();
        s.n = 10;
       
        
        for(int i = 0; i < noOfInputs; i++) {
            Solution solution = new Solution();
                    
            
            String input = in.nextLine();
            int noOfBricks = Integer.parseInt(input);
            solution.n = noOfBricks;
            System.out.println(solution.getNoOfPrimes(solution.getNoOfFormats()));
            
           
            //solution.n = 32;
            //System.out.println(solution.getNoOfPrimes(solution.getNoOfFormats()));
        }
        
    }
    
    public int getNoOfCombinations(int x, int y) {
      if(x == 0 || y == 0) {
          return 1;
      }
      int greater=0;
      int smaller=0;
      int result = 0;
      if(x > y) {
        greater = x;
        smaller = y;
      } else if(x < y){
        greater = y;
        smaller = x;
      } else {
        greater = x;
        result = 0;
        int numerator = greater-1;
        int denominator = greater - 1;
        int denominator_1 = 1;
        for(int i=denominator; i>=0; i--) {
          result = result + combinationFormula(greater-1,denominator) * combinationFormula(greater + 1, denominator_1);
          denominator = denominator - 1;
          denominator_1 = denominator_1 + 1;
        }
        return result;
      }
      
      int numerator = greater-1;
      int denominator = smaller;
      int denominator_1 = 0;
      for(int i=denominator; i>=0; i--) {
        result = result + combinationFormula(greater-1,denominator) * combinationFormula(smaller + 1, denominator_1);
         denominator = denominator - 1;
         denominator_1 = denominator_1 + 1;
      }
      return result;
    }
    
    public int combinationFormula(int x,int y) {
      int numerator = 1;
      int temp = x;
      for(int i=y; i>0; i--) {
        numerator = numerator * temp;
        temp = temp - 1;
      }
      int denominator = 1;
      for(int i = y; i > 0; i--) {
        denominator = denominator * i;    
      }
      return numerator/denominator;
    }
        
    public int getNoOfFormats() {
      int total_no_combinations = 0;
      int noOfBlocks = this.n/4;
      if(noOfBlocks == 0) {
        return 1;
      } else {
          
        for(int i=noOfBlocks;i >= 1;i--) {
          //Get all the combinations first.
          total_no_combinations += this.getNoOfCombinations(i, n-4*i);
        }
      }
      //return totalFormats + 1;
      return total_no_combinations + 1;
    }
    
    public ArrayList<ArrayList> getAllCombinationsForNBlocks(int noOfFourBlocks){
      ArrayList<ArrayList> combinations = new ArrayList<ArrayList>();
      if(noOfFourBlocks >= 1) {
        combinations = solnsForNBlocks.get(BigInteger.valueOf(noOfFourBlocks + 1));
        for(int i = 0; i<4; i++) {
          Iterator<ArrayList> itr = combinations.iterator();
          ArrayList<ArrayList> temp_combinations = new ArrayList<ArrayList>();
          
          while(itr.hasNext()) {
            ArrayList combination = itr.next();
            temp_combinations = this.mergeCombinations(temp_combinations, this.getAllCombinationsAddOneSingleBlock(combination));
            //temp_combinations = this.mergeCombinations(return_combinations, temp_combinations);
          }
          combinations = (ArrayList<ArrayList>)temp_combinations.clone();
        }
        
        Iterator<ArrayList> itr = combinations.iterator();
        ArrayList<ArrayList> temp_combinations = new ArrayList<ArrayList>();
        while(itr.hasNext()) {
            ArrayList combination = itr.next();
            temp_combinations = this.mergeCombinations(temp_combinations, this.getAllCombinationsRemoveOneFourBlock(combination));
            //temp_combinations = this.mergeCombinations(return_combinations, temp_combinations);
        }
        combinations = (ArrayList<ArrayList>)temp_combinations.clone();
      }
      return combinations;  
    }
    
    
    public ArrayList<ArrayList> getAllCombinationsForNBlocks(int noOfSingleBlocks, int noOfFourBlocks) {
      
       ArrayList blockCombination = new ArrayList(Arrays.asList(BigInteger.valueOf(noOfSingleBlocks), BigInteger.valueOf(noOfFourBlocks)));
       
        //Checking if it aldready exists in the cache.
       if(solns.get(blockCombination) != null) {  
         return solns.get(blockCombination);
       }
         
      ArrayList<ArrayList> combinations = new ArrayList<ArrayList>();
      if(noOfSingleBlocks == 0) {
        ArrayList zeroCombination = new ArrayList();
        for(int i=0; i<noOfFourBlocks + 1; i++) {
          zeroCombination.add(BigInteger.valueOf(0));
        }
        //this.solns.put(BigInteger.valueOf(0), zeroCombination);
        combinations.add(zeroCombination);
        //return combinations;
      } else {
        
        Iterator<ArrayList> itr = this.getAllCombinationsForNBlocks(noOfSingleBlocks-1, noOfFourBlocks).iterator();
          
        while (itr.hasNext()) {
          ArrayList<BigInteger> combination = itr.next();
          ArrayList<ArrayList> temp_combinations = this.getAllCombinationsAddOneSingleBlock(combination);
          combinations = this.mergeCombinations(combinations, temp_combinations);
        }
      }
      solns.put(blockCombination, combinations);
      return combinations;
    }
    
    
    
    public ArrayList<ArrayList> mergeCombinations(ArrayList<ArrayList> original_combinations, ArrayList<ArrayList> combinations) {
      Iterator<ArrayList> itr = combinations.iterator();
      while(itr.hasNext()) {
        counter++;
        ArrayList combination = itr.next();
        if(!original_combinations.contains(combination)) {
          original_combinations.add(combination);      
        }
      }
      return original_combinations;
    }
    
    public ArrayList<ArrayList> getAllCombinationsRemoveOneFourBlock(ArrayList<BigInteger> combination) {
      ArrayList<ArrayList> combinations = new ArrayList<ArrayList>();
      if(combination.size() > 1) {
        
        for(int i=0; i<combination.size()-1; i++) {
          counter++;
          ArrayList<BigInteger> temp_combination = (ArrayList<BigInteger>)combination.clone();
            
          BigInteger prevInteger = temp_combination.remove(i);
          BigInteger nextInteger = temp_combination.remove(i);
          BigInteger tempInteger = prevInteger.add(nextInteger);
          temp_combination.add(i, tempInteger);
          combinations.add(temp_combination);
        }    
      }
      return combinations;
    }
    
    /**
     * Get all the combinations when added new element.
     */
    public ArrayList<ArrayList> getAllCombinationsAddOneSingleBlock(ArrayList<BigInteger> combination) {
      int size = combination.size();
      ArrayList<ArrayList> combinations = new ArrayList();
      for(int i = 0; i<size;i++) {
        counter++;
        ArrayList<BigInteger> temp_combination = new ArrayList<BigInteger>();
        temp_combination = (ArrayList<BigInteger>)combination.clone();
        BigInteger value = combination.get(i);
        value = value.add(BigInteger.valueOf(1));
        temp_combination.set(i, value);
        combinations.add(temp_combination);
      }
      
      return combinations;
    }
    
    public int getNoOfPrimes(int no) {
      int noOfPrimes = 0;
      if(no <= 1) {
        return 0;
      }  
      for(int i=2;i<=no;i++) { 
        if(isPrime(i)) {
          noOfPrimes++;      
        }
      }
      return noOfPrimes;
    }
    
    public boolean isPrime(int no) {
      int sqrt_no = (int)(Math.sqrt((double)(no)));
      if(sqrt_no == 1) {
        return true;  
      } else {
        for(int i=2; i<=sqrt_no; i++) {
          if(no%i == 0) {
            return false;    
          }
        }
      }
      return true;
    }
    
}