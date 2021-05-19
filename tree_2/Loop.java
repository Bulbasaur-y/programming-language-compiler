package tree;

import java.util.HashSet;

/**
 * <loop> ::= while <cond> begin <stmt-seq> endwhile ;
 */
public class Loop implements Node {

    private Cond cond;
    private StmtSeq stmtSeq;


    public void execute(){
        while (cond.execute()){
            stmtSeq.execute();
        }
    }

    @Override
    public void parse(Scanner scanner) {
        if (scanner.currentToken() == Core.WHILE){
            scanner.nextToken();
            this.cond = new Cond();
            this.cond.parse(scanner);
            if (scanner.currentToken() == Core.BEGIN){
                scanner.nextToken();
                this.stmtSeq = new StmtSeq();
                this.stmtSeq.parse(scanner);

                if (scanner.currentToken() == Core.ENDWHILE){
                    scanner.nextToken();
                }
                else {
                    missCore(Core.ENDWHILE);
                    System.exit(2);
                }
            }
            else {
                missCore(Core.WHILE);
                System.exit(2);
            }
        }
        else {
            missCore(Core.BEGIN);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        System.out.print(getTString(tCount) + "while ");
        this.cond.printInfo(set,tCount);
        System.out.print(" begin");
        System.out.print("\n");
        this.stmtSeq.printInfo(set,tCount+1);
        System.out.print(getTString(tCount) +"endwhile");
        System.out.print("\n");
    }

}
