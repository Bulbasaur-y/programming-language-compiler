package tree;

import java.util.HashSet;

/**
 * <prog> ::= program <decl-seq> begin <stmt-seq> end
 */
public class Program implements Node {

    private DeclSeq declSeq;
    private StmtSeq stmtSeq;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.PROGRAM){
            this.declSeq = new DeclSeq();
            scanner.nextToken();
            this.declSeq.build(scanner);
            if(scanner.currentToken() == Core.BEGIN){

                if(scanner.currentToken() == Core.END){
                    return;
                }
                else {
                    this.stmtSeq = new StmtSeq();
                    scanner.nextToken();
                    this.stmtSeq.build(scanner);
                    if(scanner.currentToken() == Core.END){
                        Core core = scanner.nextToken();
                        if(core == Core.EOF){
                            return;
                        }
                        else {
                            System.out.print("ERROR:ids after end");
                            System.exit(2);
                        }
                    }
                    else {
                        missCore(Core.END);
                        System.exit(2);
                    }
                }
            }
            else {
                missCore(Core.BEGIN);
                System.exit(2);
            }
        }
        else {
            missCore(Core.PROGRAM);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set,int tCount){
        System.out.print("program");
        System.out.print("\n");
        if(this.declSeq != null){
            this.declSeq.printInfo(set,tCount+1);
        }
        System.out.print("begin");
        System.out.print("\n");
        if(this.stmtSeq != null){
            this.stmtSeq.printInfo(set,tCount+1);
        }
        System.out.print("end");
    }
}
