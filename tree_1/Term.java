package tree;

import java.util.HashSet;

/**
 * <term> ::= <factor> | <factor> * <term>
 */
public class Term implements Node {

    private Factor factor;
    private Term term;

    @Override
    public void build(Scanner scanner) {
        this.factor = new Factor();
        factor.build(scanner);
        if(scanner.currentToken() == Core.MULT){
            scanner.nextToken();
            this.term = new Term();
            term.build(scanner);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount) {
        this.factor.printInfo(set,tCount);
        if(term != null){
            System.out.print("*");
            this.term.printInfo(set,tCount);
        }
    }
}
