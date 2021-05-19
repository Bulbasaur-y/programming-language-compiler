package tree;

import java.util.HashSet;

/**
 * <decl-seq> ::= <decl> | <decl><decl-seq>
 */
public class DeclSeq implements Node {

    private Decl decl;
    private DeclSeq declSeq;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.BEGIN){
            return;
        }

        this.decl = new Decl();
        this.decl.build(scanner);
        if(scanner.currentToken() == Core.INT){
            this.declSeq = new DeclSeq();
            this.declSeq.build(scanner);
        }
    }

    @Override
    public void printInfo(HashSet<String> set,int tCount){
        if(this.decl != null){
           this.decl.printInfo(set,tCount);
           if(this.declSeq != null){
               this.declSeq.printInfo(set,tCount);
           }
        }

    }
}
