import java.util.ArrayList;
import java.util.Arrays;

public class Tester {

    public static void main(String[] args) {
        Answer ans = new Answer();
        
        int[][] input = {{4, 2, 3}, {1,2,3}, {5,6,2}, {-1, 0, -3}};
        int[][] expectedOutput = {{2, 3, 4}, {1,2,3}, {2, 5,6}, {-3, -1, 0}};
        
        // List<Integer> output = new ArrayList<>();

        for(int i = 0; i < input.length; i++) {
            int[] op = ans.compute(input[i]);
            boolean areEqual = Arrays.equals(op, expectedOutput[i]);
            if(areEqual) {
                System.out.println("Test case " + i + " passed");
            } else {
                System.out.println("Test case " + i + " failed");
                System.out.println("Expected: " + expectedOutput[i]);
                System.out.println("Actual: " + op);
            }
            
        }
        
    }
    
}


