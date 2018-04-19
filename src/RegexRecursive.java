import java.util.ArrayList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
/**
 * Created by dung on 9/27/16.
 */
public class RegexRecursive {
    public boolean test(String str) throws Exception{
        System.out.println(str);
        Pattern p = Pattern.compile("^[a-zA-Z][&}#][a-zA-Z]$");
        Matcher m = p.matcher(str);
        boolean isMatched = m.matches();
        if (!isMatched) {
            // string starts with (
            if (str.charAt(0) == '(') {
                ArrayList<Character> q = new ArrayList<Character>();
                int i = 0;
                while (i < str.length() && str.charAt(i) != ')') {
                    if (str.charAt(i) == '(') {
                        q.add('(');
                    }
                    ++i;
                }

                while (!q.isEmpty() && i < str.length()) {
                    if (str.charAt(i) == ')') {
                        q.remove(0);
                    }
                    ++i;
                }
                --i;

                if (!q.isEmpty())
                    throw new Exception("Input form is wrong: Missing closing parenthesis");
                else {
                    return test(str.substring(1,i));
                }
            }
        }

        return isMatched;
    }
    public static void main(String[] args) {
        RegexRecursive regex = new RegexRecursive();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            try {
                boolean result = (regex.test(scanner.next()));
                System.out.println(result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
    }
}
