package tree;

import java.util.HashSet;

public class FuncDecl implements Node {

    private String id;
    public Formals formals;
    public StmtSeq stmtSeq;

    public void execute(){
        if(functionMap.containsKey(id)){
            System.out.println("Error is two different functions named:" + id);
            System.exit(2);
        }
        else {
            functionMap.put(id,this);
        }
    }

    @Override
    public void parse(Scanner scanner) {
        if(scanner.currentToken() == Core.ID){
            this.id = scanner.getID();
            scanner.nextToken();
            if(scanner.currentToken() == Core.LPAREN){
                scanner.nextToken();
                this.formals = new Formals();
                this.formals.parse(scanner);
                if(scanner.currentToken() == Core.RPAREN){
                    scanner.nextToken();
                    if(scanner.currentToken() == Core.BEGIN){
                        scanner.nextToken();
                        if(scanner.currentToken() == Core.ENDFUNC){
                            missFunction("stmtSeq");
                            System.exit(2);
                        }
                        else {
                            this.stmtSeq = new StmtSeq();
                            this.stmtSeq.parse(scanner);
                            if(scanner.currentToken() == Core.ENDFUNC){
                                scanner.nextToken();
                            }
                            else {
                                errorCore(scanner.currentToken());
                                System.exit(2);
                            }
                        }
                    }
                    else {
                        errorCore(scanner.currentToken());
                        System.exit(2);
                    }
                }
                else {
                    errorCore(scanner.currentToken());
                    System.exit(2);
                }
            }else {
                errorCore(scanner.currentToken());
                System.exit(2);
            }
        }
        else {
            errorCore(scanner.currentToken());
            System.exit(2);
        }


    }

    @Override
    public void printInfo(HashSet<String> set, int tCount) {

    }
}
