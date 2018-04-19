/**
 * Created by dung on 9/30/16.
 */

import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicApp {
    JFrame frame = new JFrame();
    JButton bnEquiv, bnSenType, bnValidity;
    JPanel panelCurr, panelWelcome, panelEquivTest, panelSenType, panelValidityTest, panelPremise;
    JLabel lblSenType, lblEquiv, lblValidity;
    JScrollPane scrollPane;
    JTextField sen, sen1, sen2;
    ArrayList<JTextField> argument = new ArrayList<JTextField>();

    public void init() {
        frame.setSize(500,400);
        frame.setLayout(new GridLayout(1,2));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        bnEquiv = new JButton("Equivalency Test");
        bnEquiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { showEquivTest(); }
        });
        bnSenType = new JButton("Sentence Type");
        bnSenType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSenType();
            }
        });
        bnValidity = new JButton("Validity Test");
        bnValidity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showValidity();
            }
        });

        JPanel options = new JPanel(new GridLayout(3,1));
        options.add(bnEquiv);
        options.add(bnSenType);
        options.add(bnValidity);
        frame.add(options);

        panelWelcome = new JPanel(new BorderLayout());
        panelWelcome.add(new JLabel("<html><div align='center'><h3>Welcome to Logic App.<br>Select a tool on the left menu to start</h4></div></html>"), BorderLayout.CENTER);
        panelCurr = panelWelcome;
        frame.add(panelWelcome);
        setupEquivTest();
        setupSenType();
        setupValidity();
        frame.setVisible(true);
    }

    public void setupEquivTest() {
        panelEquivTest = new JPanel(new GridLayout(4,1));
        sen1 = new JTextField(10);
        sen2 = new JTextField(10);
        lblEquiv = new JLabel();
        JButton compare = new JButton("Compare");
        compare.setSize(50,30);
        compare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sentence sentence1 = new Sentence(sen1.getText());
                Sentence sentence2 = new Sentence(sen2.getText());
                if (isEquiv(sentence1, sentence2)) {
                    lblEquiv.setText("Equivalent");
                }
                else {
                    lblEquiv.setText("Not Equivalent");
                }
            }
        });
        panelEquivTest.add(sen1);
        panelEquivTest.add(sen2);
        panelEquivTest.add(compare);
        panelEquivTest.add(lblEquiv);
    }

    public void showEquivTest() {
        sen1.setText("");
        sen2.setText("");
        lblEquiv.setText("");
        frame.remove(panelCurr);
        panelCurr = panelEquivTest;
        bnEquiv.setEnabled(false);
        bnSenType.setEnabled(true);
        bnValidity.setEnabled(true);
        frame.add(panelEquivTest);
        frame.validate();
        frame.repaint();
    }

    public void setupSenType() {
        panelSenType = new JPanel(new GridLayout(3,1));
        sen = new JTextField(30);
        JButton bnGetSenType = new JButton("Get Sentence Type");
        bnGetSenType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("here");
                String sentence = sen.getText();
                String type = getType(new Sentence(sentence));
                lblSenType.setText(type);
            }
        });
        lblSenType = new JLabel();
        panelSenType.setSize(200, 300);
        panelSenType.add(sen);
        panelSenType.add(bnGetSenType);
        panelSenType.add(lblSenType);
    }

    public void showSenType() {
        sen.setText("");
        lblSenType.setText("");
        frame.remove(panelCurr);
        panelCurr = panelSenType;
        bnSenType.setEnabled(false);
        bnEquiv.setEnabled(true);
        bnValidity.setEnabled(true);
        frame.add(panelSenType);
        frame.validate();
        frame.repaint();
    }
    public void setupValidity() {
        panelValidityTest = new JPanel(new GridLayout(5,1));
        WrapLayout wrapLayout = new WrapLayout();
        wrapLayout.preferredLayoutSize(panelValidityTest);
        panelPremise = new JPanel(wrapLayout);

        argument.add(new JTextField(20));
        argument.add(new JTextField(20));

        for (JTextField txf : argument) {
            panelPremise.add(txf);
        }

        scrollPane = new JScrollPane(panelPremise);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JButton bnAddPremise = new JButton("Add Premise");
        bnAddPremise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                argument.add(new JTextField(20));
                panelPremise.add(argument.get(argument.size()-1));
                System.out.println(panelPremise.getSize());
                panelValidityTest.validate();
                panelValidityTest.repaint();
            }
        });

        final JTextField conclusion = new JTextField("Conclusion");

        JButton bnCheck = new JButton("Check Validity");
        bnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> premises = new ArrayList<String>();
                for (JTextField txf : argument) {
                    premises.add(txf.getText());
                }
//                System.out.println(premises);
//                System.out.println(conclusion.getText());
                if (isValid(premises, conclusion.getText())) {
                    lblValidity.setText("Valid");
                }
                else {
                    lblValidity.setText("Invalid");
                }
            }
        });

        lblValidity = new JLabel();


        panelValidityTest.add(scrollPane);
        panelValidityTest.add(bnAddPremise);
        panelValidityTest.add(conclusion);
        panelValidityTest.add(bnCheck);
        panelValidityTest.add(lblValidity);
    }

    public void showValidity() {
        argument.clear();
        setupValidity();
        bnEquiv.setEnabled(true);
        bnSenType.setEnabled(true);
        bnValidity.setEnabled(false);
        frame.remove(panelCurr);
        panelCurr = panelValidityTest;
        frame.add(panelValidityTest);
        frame.validate();
        frame.repaint();
    }

    /**
     * Determine whether the argument is valid
     * @return true if the argument is valid
     *          otherwise false
     */
    public boolean isValid(ArrayList<String> premises, String conclusion) {
        // check empty string
        for (String str : premises) {
            if (str.isEmpty()) {
                return false;
            }
        }

        // add conclusion before getting terminals
        ArrayList<String> argument = new ArrayList<>(premises);
        argument.add(conclusion);
        ArrayList<Character> terminals = getAllTerminals(argument);
        System.out.println("terminals: " + terminals);
        ArrayList<String> combs = getAllCombs(terminals.size());

        for (String comb : combs) {
            HashMap<Character, Integer> termMaster = buildTruthValuesMap(terminals, comb);
            if (isAllPremisesTrue(argument, termMaster)) { // if all premises are true
                if (eval(conclusion, termMaster) == 0) { // yet conclusion is false
                    return false; // then the argument is invalid
                }
            }
        }

        return true;
    }

    private boolean isAllPremisesTrue(ArrayList<String> argument, HashMap<Character, Integer> termMaster) {
        for (int i = 0; i < argument.size()-1; ++i) {
            Sentence sen = new Sentence(argument.get(i));
            if (eval(sen.getStr(), termMaster) == 0) {
                return false;
            }
        }
        return true;
    }
    private ArrayList<Character> getAllTerminals(ArrayList<String> sentences) {
        ArrayList<Character> terms = new ArrayList<>();
        for (String sen : sentences) {
            Sentence sentence = new Sentence(sen);
            terms.addAll(sentence.getTerminals());
        }
        ArrayList<Character> allTerms = sort(terms);
//        System.out.println("all terms: " + allTerms);
        ArrayList<Character> allTermsUnique = new ArrayList<>();
        allTermsUnique.add(allTerms.get(0));
        for (int i = 1; i < allTerms.size(); ++i) {
//            System.out.println("all term unique: " + allTermsUnique);
            if (allTerms.get(i) != allTermsUnique.get(allTermsUnique.size()-1)) {
                allTermsUnique.add(allTerms.get(i));
            }
        }

        return allTermsUnique;
    }

    private ArrayList<Character> sort(ArrayList<Character> terminals) {
        Object[] toArray = terminals.toArray();
        Character[] terminalsArray = Arrays.copyOf(toArray, toArray.length, Character[].class);
        Arrays.sort(terminalsArray);
        ArrayList<Character> result = new ArrayList<>();
        result.addAll(Arrays.asList(terminalsArray));
        return result;
    }

    /**
     * Decide whether the sentence is tautology, contingency, or contradiction
     * @param sen   the sentence
     * @return  "Tautology", "Contingency", or "Contradiction"
     */
    public String getType(Sentence sen) {
        if (sen.getStr().isEmpty()) {
            return "Empty sentence";
        }
        ArrayList<String> combs = getAllCombs(sen.getTerminals().size());
        int sum = 0;

        // assume tautology
        for (String comb : combs) {
            sum += eval(sen, comb);
        }

//        System.out.println(sum);
        if (sum == 0) {
            return "Contradiction";
        }
        else if (sum == combs.size()){
            return "Tautology";
        }
        else {
            return "Contingency";
        }
    }

    /**
     * Determine whether s1 is logically equivalent to s2
     * @param s1    first sentence
     * @param s2    second sentence
     * @return  true if s1 is logically equivalent to s2
     *          false otherwise
     */
    public boolean isEquiv(Sentence s1, Sentence s2) {
        if (s1.getStr().isEmpty() || s2.getStr().isEmpty()) {
            return false;
        }
        ArrayList<Character> termMaster = getAllTerminals(s1, s2);
        ArrayList<String> combs = getAllCombs(termMaster.size());

        for (String comb : combs) {
            HashMap<Character, Integer> truthValuesMap = buildTruthValuesMap(termMaster, comb);
            if (eval(s1.getStr(), truthValuesMap) != eval(s2.getStr(), truthValuesMap)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get all terminals of both sentence
     * @param sen1
     * @param sen2
     * @return
     */
    private ArrayList<Character> getAllTerminals(Sentence sen1, Sentence sen2) {
        ArrayList<Character> termMaster = sen1.getTerminals(); // add all terminals of sen1 to termMaster
        for (char c : sen2.getTerminals()) {
            if (!termMaster.contains(c)) {
                termMaster.add(c);
            }
        }
        return termMaster;
    }

    /**
     * Generate all combinations of truth values given the number of terminals
     * @param len number of terminals
     * @return all combinations of truth values
     */
    public ArrayList<String> getAllCombs(int len) {
        ArrayList<String> combs = new ArrayList<String>();
        for (int i = (int) (Math.pow(2, len) - 1); i >= 0; --i) {
            String comb = Integer.toBinaryString(i);

            // add leading 0s
            while (comb.length() < len) {
                comb = "0" + comb;
            }
            combs.add(comb);
        }
//        System.out.println("comb: " + combs);

        return combs;
    }

    public HashMap<Character, Integer> buildTruthValuesMap(ArrayList<Character> terms, String comb) {
        HashMap<Character, Integer> truthValues = new HashMap<>();
        int i = 0;
        for (Character c : terms) {
            truthValues.put(c, Integer.parseInt(comb.charAt(i) + ""));
            ++i;
        }
        return truthValues;
    }

    /**
     * Receive the main sentence and produce the truth value of that sentence
     * given the truth value combination
     * @param sen   the main sentence
     * @param comb  the truth value combination
     * @return  truth value of the sentence given the combination
     */
    public int eval(Sentence sen, String comb) {
    // assign truth values to each terminal
        HashMap<Character, Integer> truthValues = new HashMap<>();
        int i = 0;
//        System.out.println("terminals: " + sen.getTerminals());
        for (Character c : sen.getTerminals()) {
            truthValues.put(c, Integer.parseInt(comb.charAt(i) + ""));
            ++i;
        }
        return eval(sen.getStr(), truthValues);
    }

    /**
     * Return the truth value of the sentence given the truth value table
     * @param sen   sentence
     * @param truthValues   truth value table
     * @return  truth value of the sentence
     */
    public int eval(String sen, HashMap<Character, Integer> truthValues) {
//        System.out.println("truthvalues: " + truthValues);
//        System.out.println("sen: " + sen);
        if (sen.length() == 1) {
            return truthValues.get(sen.charAt(0));
        }
        int indexParen = sen.indexOf('(');
        String conn = sen.substring(0, indexParen);
        conn = conn.substring(conn.lastIndexOf(" ") + 1, conn.length());

        String[] parts = getParts(sen);

        if (parts[1].length() == 0) { // if NOT
            if(parts[0].length() == 1) {
                return eval(conn, truthValues.get(parts[0].charAt(0)), -1);
            }
            else {
                return eval(conn, eval(parts[0], truthValues), -1);
            }
        }
        else { // other syllogism connectives
            // both are atomic
            if (parts[0].length() == 1 && parts[1].length() == 1) {
                return eval(conn, truthValues.get(parts[0].charAt(0)), truthValues.get(parts[1].charAt(0)));
            }
            // 1st compound, 2nd atomic
            else if (parts[0].length() > 1 && parts[1].length() == 1) {
                return eval(conn, eval(parts[0], truthValues), truthValues.get(parts[1].charAt(0)));
            }
            // 1st atomic, 2nd compound
            else if (parts[0].length() == 1 && parts[1].length() > 1) {
                return eval(conn, truthValues.get(parts[0].charAt(0)), eval(parts[1], truthValues));
            }
            // both are compound
            else {
                return eval(conn, eval(parts[0], truthValues), eval(parts[1], truthValues));
            }
        }
    }

    /**
     * Return the truth value of a sentence given the connective and the truth values of the constituent parts
     * @param conn  connective
     * @param t1    atomic sentence 1
     * @param t2    atomic sentence 2
     * @return truth value of the sentence
     */
    public static int eval(String conn, int t1, int t2) {
        String connLower = conn.toLowerCase();
//        System.out.println("t1: " + t1 + ", t2: " + t2);
        int val = 0;

        if (connLower.equals("not")) {
            val = negate(t1);
        }
        else if (connLower.equals("or")) {
            val = t1 | t2;
        }
        else if (connLower.equals("and")) {
            val = t1 & t2;
        }
        else if (connLower.equals("if")) {
            val = negate(t1) | t2;
        }
        else if (connLower.equals("bi")){
            val = (t1 & t2) | (negate(t1) & negate(t2));
        }
//        System.out.println("val of " + conn + ": " + val);
        return val;
    }

    public static int negate(int t) {
        return t == 0 ? 1 : 0;
    }

    public String[] getParts(String sen) {
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
//            System.out.println("second part: " + secondPart);
                parts[1] = secondPart.substring(secondPart.indexOf(' ') + 1);
            }
            else {
                parts[1] = noConn.substring(i+1);
            }
        }
//        System.out.println("1st part: " + parts[0] + ", 2nd part: " + parts[1]);
        return parts;
    }

    public static void main(String[] args) {

        LogicApp app = new LogicApp();
        app.init();


    }
}
