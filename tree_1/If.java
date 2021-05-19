package tree;

import java.util.HashSet;

/**
 * <if> ::= if <cond> then <stmt-seq> endif ;
 * | if <cond> then <stmt-seq> else <stmt-seq> endif ;
 */
public class If implements Node {

    private Cond cond;
    private StmtSeq thenStmtSeq;
    private StmtSeq elseStmtSeq;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.IF){
            this.cond = new Cond();
            scanner.nextToken();
            this.cond.build(scanner);
            if(scanner.currentToken() == Core.THEN){
                scanner.nextToken();
                this.thenStmtSeq = new StmtSeq();
                this.thenStmtSeq.build(scanner);
                if(scanner.currentToken() == Core.ELSE){
                    scanner.nextToken();
                    this.elseStmtSeq = new StmtSeq();
                    this.elseStmtSeq.build(scanner);
                }

                if(scanner.currentToken() == Core.ENDIF){
                    scanner.nextToken();
                }
                else {
                    missCore(Core.ENDIF);
                    System.exit(2);
                }
            }
            else {
                missCore(Core.THEN);
                System.exit(2);
            }
        }
        else {
            missCore(Core.IF);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        System.out.print(getTString(tCount) +  "if ");
        this.cond.printInfo(set,tCount);
        System.out.print(" then");
        System.out.print("\n");
        this.thenStmtSeq.printInfo(set,tCount+1);
        if(this.elseStmtSeq != null){
            System.out.print(getTString(tCount) +  "else");
            System.out.print("\n");
            this.elseStmtSeq.printInfo(set,tCount+1);
        }
        System.out.print(getTString(tCount) +  "endif");
        System.out.print("\n");
    }
}
