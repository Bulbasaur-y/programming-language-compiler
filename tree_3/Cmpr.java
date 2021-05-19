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

    public boolean execute(){
        int value1 = expr1.execute();
        int value2 = expr2.execute();
        switch (coreValue){
            case "==":
                return value1 == value2;
            case "<":
                return value1 < value2;
            case "<=":
                return value1 <= value2;
            default:
                System.exit(2);
                return false;
        }
    }

    @Override
    public void parse(Scanner scanner) {
        this.expr1 = new Expr();
        this.expr1.parse(scanner);
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
        this.expr2 .parse(scanner);
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
       this.expr1.printInfo(set,tCount);
       System.out.print(coreValue);
       this.expr2.printInfo(set,tCount);
    }
}
