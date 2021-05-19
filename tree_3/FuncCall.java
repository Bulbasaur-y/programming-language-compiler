package tree;

import java.util.HashSet;

public class FuncCall implements Node {

    private String id;
    private Formals formals;

    public void execute() {
        if (functionMap.containsKey(id)) {
            FuncDecl funcDecl = functionMap.get(id);

            Formals f1 = this.formals;
            Formals f2 = funcDecl.formals;
            while (f1 != null && f2 != null){
                idMap.put(f2.id,idMap.get(f1.id));
                idSet.remove(f1.id);
                if(!idSet.add(f2.id)){
                    errorDoubleId(f2.id);
                }
                f1 = f1.formals;
                f2 = f2.formals;
            }

            if(f1!= f2){
                System.out.println("Error is function call does not match function definition");
                System.exit(2);
            }

            funcDecl.stmtSeq.execute();

            f1 = this.formals;
            f2 = funcDecl.formals;
            while (f1 != null && f2 != null){
                idMap.put(f1.id,idMap.get(f2.id));
                idSet.remove(f2.id);
                idSet.add(f1.id);
                f1 = f1.formals;
                f2 = f2.formals;
            }

        } else {
            System.out.println("no this function:" + id);
            System.exit(2);
        }

    }

    @Override
    public void parse(Scanner scanner) {
        if (scanner.currentToken() == Core.ID) {
            this.id = scanner.getID();
            scanner.nextToken();
            if (scanner.currentToken() == Core.LPAREN) {
                scanner.nextToken();
                this.formals = new Formals();
                this.formals.parse(scanner);
                if (scanner.currentToken() == Core.RPAREN) {
                    scanner.nextToken();
                    if (scanner.currentToken() == Core.SEMICOLON) {
                        scanner.nextToken();
                    } else {
                        errorCore(scanner.currentToken());
                        System.exit(2);
                    }
                } else {
                    errorCore(scanner.currentToken());
                    System.exit(2);
                }
            } else {
                missFunction("bad");
                System.exit(2);
            }
        } else {
            errorCore(scanner.currentToken());
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount) {

    }
}
