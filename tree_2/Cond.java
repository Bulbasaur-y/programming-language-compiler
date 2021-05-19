package tree;

import java.util.HashSet;

/**
 * <cond> ::= <cmpr> | ! ( <cond> )
 * | <cmpr> or <cond>
 */
public class Cond implements Node {

    private Cmpr cmpr;
    private Cond cond;

    public boolean execute(){
        if(cond == null){
            return cmpr.execute();
        }
        else if(cmpr == null){
            return !cond.execute();
        }
        else {
            return cmpr.execute() || cond.execute();
        }
    }

    @Override
    public void parse(Scanner scanner) {
        if(scanner.currentToken() == Core.NEGATION){
            this.cond = new Cond();
            Core next = scanner.nextToken();
            if(next == Core.LPAREN){
                scanner.nextToken();
                cond.parse(scanner);
                if(scanner.currentToken() == Core.RPAREN){
                    scanner.nextToken();
                }
                else {
                    missCore(Core.RPAREN);
                    System.exit(2);
                }
            }
            else {
                missCore(Core.LPAREN);
                System.exit(2);
            }
        }
        else {
            this.cmpr = new Cmpr();
            cmpr.parse(scanner);
            if(scanner.currentToken() == Core.OR){
                this.cond = new Cond();
                scanner.nextToken();
                cond.parse(scanner);
            }
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        if(cond == null){
            this.cmpr.printInfo(set,tCount);
        }
        else if(cmpr == null){
            System.out.print("!(");
            this.cond.printInfo(set,tCount);
            System.out.print(")");
        }
        else {
            this.cmpr.printInfo(set,tCount);
            System.out.print(" or ");
            this.cond.printInfo(set,tCount);
        }
    }
}
