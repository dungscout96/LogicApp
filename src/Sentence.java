import java.util.ArrayList;

/**
 * Created by dung on 9/22/16.
 */
public class Sentence {
    private String str;
    private ArrayList<Character> terminals = new ArrayList<Character>();

    public Sentence(String str)
    {
        this.str = str;
    }

    /**
     * Getters
     */
    public ArrayList<Character> getTerminals() {
        if (terminals.isEmpty()) {
            getTerminals(str);
        }
        return terminals;
    }

    public String getStr() {
        return str;
    }

    /**
     * Setters
     */
    public void setTerminals(ArrayList<Character> terms) {
        if (terminals.isEmpty()) {
            terminals.addAll(terms);
        }
        else {
            terminals.clear();
            terminals.addAll(terms);
        }
    }

    /**
     * Set new string to the sentence
     * and reset the terminal list
     * @param str   new string
     */
    public void setStr(String str) {
        this.str = str;
        terminals = new ArrayList<>();
    }

    private String[] getParts(String sen) {
//        System.out.println("get part of: " + sen);
        String[] parts = new String[2];
        // 1. Identify and Get rid of the connective and the enclosing parens, assuming input is always in the right form
        int indexParen = sen.indexOf('(');
        String conn = sen.substring(0, indexParen);
        conn = conn.substring(conn.lastIndexOf(" ") + 1, conn.length());
        String noConn = sen.substring(indexParen+1, sen.length()-1);
//        System.out.println("noConn: " + noConn);

        // 2. Get 2 parts by finding index of , that separates the two parts
        // if connective is NOT
        if (conn.toLowerCase().equals("not")) {
            parts[0] = noConn; // noConn still have the last closing paren so need to remove it
            parts[1] = "";
        }
        else {
            int numComma = 1;
            int i = 0;
            while (numComma != 0) {
//                System.out.println("char: " + noConn.charAt(i));
                if (noConn.charAt(i) == '(') {
                    if (i > 2  && noConn.substring(i-3,i).toLowerCase().equals("not")) {

                    }
                    else {
                        ++numComma;
                    }
                }
                if (noConn.charAt(i) == ',') {
                    --numComma;
                }
                ++i;
            }
            --i; // back to the index of the comma
            parts[0] = noConn.substring(0, i);
//            System.out.println(noConn);
            if (noConn.charAt(i+1) == ' ') {
                String secondPart = noConn.substring(i + 1);
//                System.out.println("second part: " + secondPart);
                parts[1] = secondPart.substring(secondPart.indexOf(' ') + 1);
            }
            else {
                parts[1] = noConn.substring(i+1);
            }
        }
//        System.out.println("1st part: " + parts[0] + ", 2nd part: " + parts[1]);
        return parts;
    }

    /**
     * get terminals of the sentence
     * @param sen the string representation of the sentence
     */
    private void getTerminals(String sen) {
        if (sen.length() == 1) {
            terminals.add(sen.charAt(0));
            return;
        }
//        System.out.println("sen: " + sen);
        int indexParen = sen.indexOf('(');
        String conn = sen.substring(0, indexParen);
        conn = conn.substring(conn.lastIndexOf(" ") + 1, conn.length());

        String[] parts = getParts(sen);

        if (parts[0].length() == 1 && parts[1].length() == 0) { // if NOT sentence
            if (terminals.indexOf(parts[0].toCharArray()[0]) == -1) {
                terminals.add(parts[0].toCharArray()[0]);
            }
            return;
        }
        else if (parts[0].length() == 1 && parts[1].length() == 1) {
            if (terminals.indexOf(parts[0].toCharArray()[0]) == -1) {
                terminals.add(parts[0].toCharArray()[0]);
            }
            if (terminals.indexOf(parts[1].toCharArray()[0]) == -1) {
                terminals.add(parts[1].toCharArray()[0]);
            }
            return;
        }
        else {
            if (parts[0].length() == 1) {
                if (terminals.indexOf(parts[0].toCharArray()[0]) == -1) {
                    terminals.add(parts[0].toCharArray()[0]);
                }
                getTerminals(parts[1]);
            }
            else {
                if (parts[1].length() > 1) { // when both are compound
                    getTerminals(parts[0]);
                    getTerminals(parts[1]);
                }
                else {
                    if (parts[1].length() > 0 && terminals.indexOf(parts[1].toCharArray()[0]) == -1) {
                        terminals.add(parts[1].toCharArray()[0]);
                    }
                    getTerminals(parts[0]);
                }
            }
            return;
        }
    }
}
