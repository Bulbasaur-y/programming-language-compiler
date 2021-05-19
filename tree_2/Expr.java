package tree;

import java.util.HashSet;

/**
 * <expr> ::= <term> | <term> + <expr> | <term> – <expr>
 */
public class Expr implements Node {

    private Term term;
    private Expr expr;
    private String coreValue;

    public int execute(){
        int value1 = this.term.execute();
        if(null != coreValue){
            int value2 = expr.execute();
            if(coreValue.equals("+")){
                value1 = value1 + value2;
            }
            else {
                value1 = value1 - value2;
            }
        }
        return value1;
    }

    @Override
    public void parse(Scanner scanner) {
        this.term = new Term();
        this.term.parse(scanner);
        if(scanner.currentToken() == Core.ADD){
            this.coreValue = "+";
            scanner.nextToken();
            this.expr = new Expr();
            this.expr.parse(scanner);
        }
        else if(scanner.currentToken() == Core.SUB){
            this.coreValue = "-";
            scanner.nextToken();
            this.expr = new Expr();
            this.expr.parse(scanner);
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
