package tree;

import java.util.HashSet;

/**
 * <cmpr> ::= <expr> == <expr> | <expr> < <expr>
 * | <expr> <= <expr>
 */
public class Cmpr implements Node {

    private Expr expr1;
    private Expr expr2;
    private String coreValue;

    @Override
    public void build(Scanner scanner) {
        this.expr1 = new Expr();
        this.expr1.build(scanner);
        switch (scanner.currentToken()){
            case EQUAL:
                this.coreValue = "==";
                break;
            case LESS:
                this.coreValue = "<";
                break;
            case LESSEQUAL:
                this.coreValue = "<=";
                break;
            default:
                errorCore(scanner.currentToken());
                System.exit(2);
        }
        scanner.nextToken();
        this.expr2 = new Expr();
        this.expr2 .build(scanner);
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
       this.expr1.printInfo(set,tCount);
       System.out.print(coreValue);
       this.expr2.printInfo(set,tCount);
    }
}
