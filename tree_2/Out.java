package tree;

import java.util.HashSet;

/**
 * <out> ::= output <expr> ;
 */
public class Out implements Node {

    private Expr expr;

    public void execute(){
        System.out.println(expr.execute());
    }

    @Override
    public void parse(Scanner scanner) {
        if(scanner.currentToken() == Core.OUTPUT){
            scanner.nextToken();
            this.expr = new Expr();
            this.expr.parse(scanner);
            if(scanner.currentToken() == Core.SEMICOLON){
                scanner.nextToken();
            }
            else {
                missCore(Core.SEMICOLON);
                System.exit(2);
            }
        }
        else {
            missCore(Core.OUTPUT);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        System.out.print(getTString(tCount) + "output ");
        expr.printInfo(set,tCount);
        System.out.print(";");
        System.out.print("\n");
    }
}
