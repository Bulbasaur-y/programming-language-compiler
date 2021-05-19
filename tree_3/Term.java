package tree;

import java.util.HashSet;

/**
 * <term> ::= <factor> | <factor> * <term>
 */
public class Term implements Node {

    private Factor factor;
    private Term term;

    public int execute(){
        int value1 = factor.execute();
        if(term != null){
            value1 = value1 * term.execute();
        }
        return value1;
    }

    @Override
    public void parse(Scanner scanner) {
        this.factor = new Factor();
        factor.parse(scanner);
        if(scanner.currentToken() == Core.MULT){
            scanner.nextToken();
            this.term = new Term();
            term.parse(scanner);
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
