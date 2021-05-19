package tree;

import java.util.HashSet;

/**
 *  <assign> ::= id = <expr> ;
 */
public class Assign implements Node {

    private String id;
    private Expr expr;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.ID){
            this.id = scanner.getID();
            Core next = scanner.nextToken();
            if(next == Core.ASSIGN){
                scanner.nextToken();
                this.expr = new Expr();
                this.expr.build(scanner);
                if(scanner.currentToken() == Core.SEMICOLON){
                    scanner.nextToken();
                }
                else {
                    missCore(Core.SEMICOLON);
                    System.exit(2);
                }
            }
            else {
                missCore(Core.ASSIGN);
                System.exit(2);
            }
        }
        else {
            missCore(Core.ID);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        if(set.contains(id)){
            System.out.print(getTString(tCount) + id);
            System.out.print("=");
            this.expr.printInfo(set,tCount);
            System.out.print(";");
            System.out.print("\n");
        }
        else {
            errorNoId(id);
        }
    }
}
