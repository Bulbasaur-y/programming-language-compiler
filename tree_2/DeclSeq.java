package tree;

import java.util.HashSet;

/**
 * <decl-seq> ::= <decl> | <decl><decl-seq>
 */
public class DeclSeq implements Node {

    private Decl decl;
    private DeclSeq declSeq;

    public void execute(){
        if(this.decl != null){
            decl.execute();
        }
        if(this.declSeq != null){
            declSeq.execute();
        }
    }

    @Override
    public void parse(Scanner scanner) {
        if(scanner.currentToken() == Core.BEGIN){
            return;
        }

        this.decl = new Decl();
        this.decl.parse(scanner);
        if(scanner.currentToken() == Core.INT){
            this.declSeq = new DeclSeq();
            this.declSeq.parse(scanner);
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
