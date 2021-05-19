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
    private FuncCall funcCall;

    public Decl getDecl(){
        return this.decl;
    }

    public void execute(){
        if(null != assign){
            this.assign.execute();
        }
        else if (null != ifValue){
            this.ifValue.execute();
        }
        else if (null != in){
            this.in.execute();
        }
        else if (null != out){
            this.out.execute();
        }
        else if (null != loop){
            this.loop.execute();
        }
        else if (null != decl){
            this.decl.execute();
        }
        else if (null != funcCall){
            this.funcCall.execute();
        }
    }

    @Override
    public void parse(Scanner scanner) {
        switch (scanner.currentToken()){
            case ID:
                this.assign = new Assign();
                this.assign.parse(scanner);
                break;
            case IF:
                this.ifValue = new If();
                this.ifValue.parse(scanner);
                break;
            case INPUT:
                this.in = new In();
                this.in.parse(scanner);
                break;
            case OUTPUT:
                this.out = new Out();
                this.out.parse(scanner);
                break;
            case WHILE:
                this.loop = new Loop();
                this.loop.parse(scanner);
                break;
            case INT:
                this.decl = new Decl();
                this.decl.parse(scanner);
                break;
            case BEGIN:
                this.funcCall = new FuncCall();
                scanner.nextToken();
                this.funcCall.parse(scanner);
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
