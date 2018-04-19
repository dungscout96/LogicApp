import java.util.HashMap;

/**
 * Created by dung on 10/18/16.
 */

public class Solution {
    public static void main(String[] args) {
        System.out.println(fact(10));


    }
    static long fact(int n) {
        if (n == 0) {
            return 1;
        }
        else {
            return n * fact(n-1);
        }
    }
}
