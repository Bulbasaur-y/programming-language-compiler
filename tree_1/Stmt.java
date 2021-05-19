package tree;

import java.util.HashSet;

/**
 * <stmt> ::= <assign> | <if> | <loop> | <in> | <out> | <decl>
 */
public class Stmt implements Node {

    private Assign assign;
    private If ifValue;
    private Loop loop;
    private In in;
    private Out out;
    private Decl decl;

    public Decl getDecl(){
        return this.decl;
    }

    @Override
    public void build(Scanner scanner) {
        switch (scanner.currentToken()){
            case ID:
                this.assign = new Assign();
                this.assign.build(scanner);
                break;
            case IF:
                this.ifValue = new If();
                this.ifValue.build(scanner);
                break;
            case INPUT:
                this.in = new In();
                this.in.build(scanner);
                break;
            case OUTPUT:
                this.out = new Out();
                this.out.build(scanner);
                break;
            case WHILE:
                this.loop = new Loop();
                this.loop.build(scanner);
                break;
            case INT:
                this.decl = new Decl();
                this.decl.build(scanner);
                break;
            default:
                errorCore(scanner.currentToken());
                System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        if(null != assign){
            this.assign.printInfo(set,tCount);
        }
        else if (null != ifValue){
            this.ifValue.printInfo(set,tCount);
        }
        else if (null != in){
            this.in.printInfo(set,tCount);
        }
        else if (null != out){
            this.out.printInfo(set,tCount);
        }
        else if (null != loop){
            this.loop.printInfo(set,tCount);
        }
        else if (null != decl){
            this.decl.printInfo(set,tCount);
        }
    }
}
