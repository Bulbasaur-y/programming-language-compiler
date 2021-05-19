package tree;

import java.util.HashSet;

/**
 * <stmt-seq> ::= <stmt> | <stmt><stmt-seq>
 */
public class StmtSeq implements Node {

    private Stmt stmt;
    private StmtSeq stmtSeq;

    @Override
    public void build(Scanner scanner) {
        this.stmt = new Stmt();
        this.stmt.build(scanner);
        if(scanner.currentToken() == Core.END
            || scanner.currentToken() == Core.ENDIF
            || scanner.currentToken() == Core.ELSE
            || scanner.currentToken() == Core.ENDWHILE){
           return;
        }

        this.stmtSeq = new StmtSeq();
        this.stmtSeq.build(scanner);
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        HashSet<String> copy = new HashSet<>(set);
        this.stmt.printInfo(set,tCount);
        if(this.stmt.getDecl() == null){
            set.clear();
            set.addAll(copy);
        }
        if(this.stmtSeq != null){
            this.stmtSeq.printInfo(set,tCount);
        }
        set.clear();
        set.addAll(copy);
    }
}
