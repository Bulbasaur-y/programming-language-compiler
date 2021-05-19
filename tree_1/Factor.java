package tree;

import java.util.HashSet;

/**
 * <term> ::= <factor> | <factor> * <term>
 */
public class Factor implements Node {

    private int constValue;
    private String id;
    private Expr expr;

    @Override
    public void build(Scanner scanner) {
        switch (scanner.currentToken()){
            case CONST:
                constValue = scanner.getCONST();
                scanner.nextToken();
                break;
            case ID:
                id = scanner.getID();
                scanner.nextToken();
                break;
            case LPAREN:
                scanner.nextToken();
                this.expr = new Expr();
                expr.build(scanner);
                if(scanner.currentToken() == Core.RPAREN){
                    scanner.nextToken();
                }
                else {
                    missCore(Core.RPAREN);
                    System.exit(2);
                }
                break;
            default:
                errorCore(scanner.currentToken());
                System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount) {
        if(this.id != null){
            if(set.contains(this.id)){
                System.out.print(this.id);
            }
            else {
                errorNoId(id);
            }
        }
        else if(this.expr != null){
            System.out.print("(");
            this.expr.printInfo(set,tCount);
            System.out.print(")");
        }
        else {
            System.out.print(constValue);
        }
    }
}
