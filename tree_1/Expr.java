package tree;

import java.util.HashSet;

/**
 * <expr> ::= <term> | <term> + <expr> | <term> – <expr>
 */
public class Expr implements Node {

    private Term term;
    private Expr expr;
    private String coreValue;

    @Override
    public void build(Scanner scanner) {
        this.term = new Term();
        this.term.build(scanner);
        if(scanner.currentToken() == Core.ADD){
            this.coreValue = "+";
            scanner.nextToken();
            this.expr = new Expr();
            this.expr.build(scanner);
        }
        else if(scanner.currentToken() == Core.SUB){
            this.coreValue = "-";
            scanner.nextToken();
            this.expr = new Expr();
            this.expr.build(scanner);
        }
    }


    @Override
    public void printInfo(HashSet<String> set, int tCount){
       this.term.printInfo(set,tCount);
        if(null != coreValue){
            System.out.print(coreValue);
            this.expr.printInfo(set,tCount);
        }
    }
}
