import groovy.util.GroovyTestCase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by dung on 10/24/16.
 */
public class Test extends GroovyTestCase{
    @org.junit.Test
    public void testIsValid() {
//        System.out.println("Test isValid");
        LogicApp app = new LogicApp();
        ArrayList<String> arg = new ArrayList<>();
        String[] argument = {"if(a,b)", "a"};
        arg.addAll(Arrays.asList(argument));
        assertTrue(app.isValid(arg,"b"));
        arg.clear();
        arg.add("or(not(d),not(f))");
        arg.add("if(g,and(d,f))");
        assertTrue(app.isValid(arg,"not(g)"));
        arg.clear();
        arg.add("not(or(d,not(k)))");
        arg.add("if(h,d)");
        assertTrue(app.isValid(arg, "if(h,k)"));
        arg.clear();
        arg.add("bi(a,b)");
        assertTrue(app.isValid(arg, "or(not(b),a)"));
        arg.clear();
        arg.add("bi(a,b)");
        arg.add("if(not(and(a,not(r))),and(a,s))");
        assertTrue(app.isValid(arg, "if(not(and(b,s)),not(and(a,r)))"));
        arg.clear();
        arg.add("not(and(d,not(or(e,b))))");
        arg.add("not(or(e,f))");
        arg.add("if(c,or(e,a))");
        assertTrue(app.isValid(arg, "or(not(and(not(a),not(b))),not(or(c,d)))"));
        arg.clear();
        arg.add("if(f,g)");
        arg.add("or(and(g,h),k)");
        assertFalse(app.isValid(arg,"f"));
        arg.clear();
        arg.add("if(m,n)");
        arg.add("if(k,p)");
        arg.add("or(n,k)");
        assertFalse(app.isValid(arg,"or(m,p)"));
        arg.clear();
        arg.add("if(q,w)");
        arg.add("if(not(p), not(w))");
        arg.add("not(n)");
        arg.add("if(w, or(p,q))");
        arg.add("if(r, or(s,t))");
        arg.add("if(s, or(q,n))");
        assertFalse(app.isValid(arg,"if(r, if(not(q),w))"));
        arg.clear();
        arg.add("and(or(h,not(r)),if(k,l))");
        arg.add("if(s,if(m,t))");
        arg.add("if(r,if(t,n))");
        arg.add("or(k,r)");
        arg.add("if(or(h,l),and(m,s))");
        assertFalse(app.isValid(arg,"r"));
    }

    @org.junit.Test
    public void testGetType() {
        LogicApp app = new LogicApp();
        Sentence sen = new Sentence("NOT(AND(A,NOT(A)))");
        assertEquals("Tautology", app.getType(sen));
        sen.setStr("OR(A,IF(A,B))");
        assertEquals("Tautology", app.getType(sen));
        sen.setStr("BI(A,OR(B, NOT(B)))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("if(and(a,b),if(not(a),not(b)))");
        assertEquals("Tautology", app.getType(sen));
        sen.setStr("bi(bi(a,b),if(a,b))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("if(if(a,b),if(b,c))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("or(and(and(a,d),c), or(and(a,c),and(a,b)))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("bi(if(if(c,d),and(a,or(c,d))), if(a, if(d,c)))");
        assertEquals("Contingency", app.getType(sen));

        sen.setStr("not(bi(p, not(p)))");
        assertEquals("Tautology", app.getType(sen));
        sen.setStr("if(p, or(p,q))");
        assertEquals("Tautology", app.getType(sen));
        sen.setStr("bi(p, or(q, p))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("if(if(q,p),if(p,q))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("if(or(q,p),if(p,q))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("if(p, if(q,if(p,r)))");
        assertEquals("Contingency", app.getType(sen));
        sen.setStr("or(if(p, or(or(q,r),or(s,not(q)))), not(p))");
        assertEquals("Tautology", app.getType(sen));
    }

    @org.junit.Test
    public void testEquiv() {
        LogicApp app = new LogicApp();
        Sentence s1 = new Sentence("a");
        Sentence s2 = new Sentence("and(a,c)");
        s1.setStr("if(a,b)");
        s2.setStr("or(not(a),b)");
        assertTrue(app.isEquiv(s1,s2));
        s1.setStr("l"); s2.setStr("or(l,l)");
        assertTrue(app.isEquiv(s1,s2));
        s1.setStr("f"); s2.setStr("if(not(f),g)");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("and(a, not(a))"); s2.setStr("or(a, not(a))");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("and(m,not(r))"); s2.setStr("or(or(m,l),not(l))");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("not(if(m, not(m)))"); s2.setStr("or(k,m)");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("and(or(m,a),or(m, not(a)))"); s2.setStr("m");
        assertTrue(app.isEquiv(s1,s2));
        s1.setStr("or(and(m, not(a)),or(m,a))"); s2.setStr("m");
        assertFalse(app.isEquiv(s1,s2));

        s1.setStr("p"); s2.setStr("or(p,q)");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("p"); s2.setStr("or(p, not(p))");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("and(a, not(a))"); s2.setStr("bi(a, not(a))");
        assertTrue(app.isEquiv(s1,s2));
        s1.setStr("and(a, not(b))"); s2.setStr("or(a, not(b))");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("if(p,not(q))"); s2.setStr("and(p,q)");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("or(p,q)"); s2.setStr("if(p,if(q,q))");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("if(and(p,not(q)),and(p,q))"); s2.setStr("and(p,q)");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("if(p,if(q,p))"); s2.setStr("if(q,if(p,q))");
        assertTrue(app.isEquiv(s1,s2));
        s1.setStr("not(and(p, not(q)))"); s2.setStr("or(and(not(p),not(q)),or(p,q))");
        assertFalse(app.isEquiv(s1,s2));
        s1.setStr("or(and(p,q),and(q,r))"); s2.setStr("or(and(not(p),not(q)),and(not(q),not(r)))");
        assertFalse(app.isEquiv(s1,s2));
    }

    @org.junit.Test
    public void testEval() {
        LogicApp app = new LogicApp();
        // test connectives
        Sentence sen = new Sentence("and(a,b)");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 0);
        assertEquals(app.eval(sen, "01"), 0);
        assertEquals(app.eval(sen, "00"), 0);

        sen = new Sentence("or(a,b)");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 1);
        assertEquals(app.eval(sen, "01"), 1);
        assertEquals(app.eval(sen, "00"), 0);

        sen = new Sentence("not(a)");
        assertEquals(app.eval(sen, "1"), 0);
        assertEquals(app.eval(sen, "0"), 1);

        sen = new Sentence("if(a,b)");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 0);
        assertEquals(app.eval(sen, "01"), 1);
        assertEquals(app.eval(sen, "00"), 1);

        sen = new Sentence("bi(a,b)");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 0);
        assertEquals(app.eval(sen, "01"), 0);
        assertEquals(app.eval(sen, "00"), 1);

        // test complex case
        sen = new Sentence("Or(a,if(a,b))");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 1);
        assertEquals(app.eval(sen, "01"), 1);
        assertEquals(app.eval(sen, "00"), 1);

        sen = new Sentence("bi(a,or(b,not(b)))");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 1);
        assertEquals(app.eval(sen, "01"), 0);
        assertEquals(app.eval(sen, "00"), 0);

        sen = new Sentence("if(and(a,b),if(not(a),not(b)))");
        assertEquals(app.eval(sen, "11"), 1);
        assertEquals(app.eval(sen, "10"), 1);
        assertEquals(app.eval(sen, "01"), 1);
        assertEquals(app.eval(sen, "00"), 1);

//        // 3 terminals
        sen = new Sentence("if(if(a,b), if(b,c))");
        assertEquals(app.eval(sen,"111"), 1);
        assertEquals(app.eval(sen,"110"), 0);
        assertEquals(app.eval(sen,"101"), 1);
        assertEquals(app.eval(sen,"100"), 1);
        assertEquals(app.eval(sen,"011"), 1);
        assertEquals(app.eval(sen,"010"), 0);
        assertEquals(app.eval(sen,"001"), 1);
        assertEquals(app.eval(sen,"000"), 1);

        sen = new Sentence("or(and(and(a,b),c), or(and(a,c),and(a,b)))");
        ArrayList<Character> terms = new ArrayList<>(); // setTerminals because getTerminals works yet don't follow the order
        terms.add('a'); terms.add('b'); terms.add('c');
        sen.setTerminals(terms);
        assertEquals(1, app.eval(sen,"111"));
        assertEquals(1, app.eval(sen,"110"));
        assertEquals(1, app.eval(sen,"101"));
        assertEquals(0, app.eval(sen,"100"));
        assertEquals(0, app.eval(sen,"011"));
        assertEquals(0, app.eval(sen,"010"));
        assertEquals(0, app.eval(sen,"001"));
        assertEquals(0, app.eval(sen,"000"));

//        // 4 terminals
        sen = new Sentence("bi(if(if(c,d),and(a, or(c,d))), if(a,if(d,c)))");
        terms.add('d');
        sen.setTerminals(terms);
        assertEquals(1, app.eval(sen,"1111"));
        assertEquals(1, app.eval(sen,"1110"));
        assertEquals(0, app.eval(sen,"1101"));
        assertEquals(0, app.eval(sen,"1100"));
        assertEquals(1, app.eval(sen,"1011"));
        assertEquals(1, app.eval(sen,"1010"));
        assertEquals(0, app.eval(sen,"1001"));
        assertEquals(0, app.eval(sen,"1000"));
        assertEquals(0, app.eval(sen,"0111"));
        assertEquals(1, app.eval(sen,"0110"));
        assertEquals(0, app.eval(sen,"0101"));
        assertEquals(0, app.eval(sen,"0100"));
        assertEquals(0, app.eval(sen,"0011"));
        assertEquals(1, app.eval(sen,"0010"));
        assertEquals(0, app.eval(sen,"0001"));
        assertEquals(0, app.eval(sen,"0000"));

        sen = new Sentence("or(if(a, or( or(b,c), or(d,not(b)))), not(a))");
        sen.setTerminals(terms);
//        System.out.println(sen.getTerminals());
        assertEquals(1, app.eval(sen,"1111"));
        assertEquals(1, app.eval(sen,"1110"));
        assertEquals(1, app.eval(sen,"1101"));
        assertEquals(1, app.eval(sen,"1100"));
        assertEquals(1, app.eval(sen,"1011"));
        assertEquals(1, app.eval(sen,"1010"));
        assertEquals(1, app.eval(sen,"1001"));
        assertEquals(1, app.eval(sen,"1000"));
        assertEquals(1, app.eval(sen,"0111"));
        assertEquals(1, app.eval(sen,"0110"));
        assertEquals(1, app.eval(sen,"0101"));
        assertEquals(1, app.eval(sen,"0100"));
        assertEquals(1, app.eval(sen,"0011"));
        assertEquals(1, app.eval(sen,"0010"));
        assertEquals(1, app.eval(sen,"0001"));
        assertEquals(1, app.eval(sen,"0000"));

    }

    @org.junit.Test
    public void testGetParts() {
        LogicApp app = new LogicApp();
        Sentence sen = new Sentence("bi(if(if(c,d),and(a, or(c,d))), if(a,if(d,c)))");
        String[] parts = app.getParts(sen.getStr());
        assertEquals("if(if(c,d),and(a, or(c,d)))", parts[0]);
        assertEquals("if(a,if(d,c))", parts[1]);
    }
    @org.junit.Test
    public void testGetTerminals() {
        Sentence sen = new Sentence("AND(a,b)");
        ArrayList<Character> terminals = new ArrayList<Character>();
        terminals.add('a');
        terminals.add('b');
        assertTrue(compareTerminalList(sen.getTerminals(), terminals));

        // either of the part is compound
        sen = new Sentence("and(a,or(b,c))");
        terminals.add('c');
        assertTrue(compareTerminalList(sen.getTerminals(), terminals));

        sen = new Sentence("and(or(a,b),c)");
        assertTrue(compareTerminalList(sen.getTerminals(), terminals));

        // both are compound
        sen = new Sentence("and(not(a),or(b,c))");
        assertTrue(compareTerminalList(sen.getTerminals(), terminals));

        sen = new Sentence("and(bi(a,d),or(b,c))");
        terminals.add('d');
        assertTrue(compareTerminalList(sen.getTerminals(), terminals));

        // more complex cases
        sen = new Sentence("and(bi(a,not(d)),or(b,if(c,e)))");
        terminals.add('e');
        assertTrue(compareTerminalList(sen.getTerminals(), terminals));
    }

    private boolean compareTerminalList(ArrayList<Character> terminals, ArrayList<Character> test) {
        if (terminals.size() != test.size()) {
            return false;
        }
        for (Character c : terminals) {
            if (test.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }
}
