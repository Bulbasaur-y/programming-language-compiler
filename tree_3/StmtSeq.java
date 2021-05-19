package tree;

import java.util.HashSet;

/**
 * <stmt-seq> ::= <stmt> | <stmt><stmt-seq>
 */
public class StmtSeq implements Node {

    private Stmt stmt;
    private StmtSeq stmtSeq;

    public void execute(){
        stmt.execute();
        if(this.stmtSeq != null){
            stmtSeq.execute();
        }
    }

    @Override
    public void parse(Scanner scanner) {
        this.stmt = new Stmt();
        this.stmt.parse(scanner);
        if(scanner.currentToken() == Core.END
            || scanner.currentToken() == Core.ENDIF
            || scanner.currentToken() == Core.ELSE
            || scanner.currentToken() == Core.ENDWHILE
            || scanner.currentToken() == Core.ENDFUNC){
           return;
        }

        this.stmtSeq = new StmtSeq();
        this.stmtSeq.parse(scanner);
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
