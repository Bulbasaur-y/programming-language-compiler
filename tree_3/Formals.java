package tree;

import java.util.HashSet;

public class Formals implements Node {

    public String id;
    public Formals formals;

    public void execute(){

    }

    @Override
    public void parse(Scanner scanner) {
        if(scanner.currentToken() == Core.ID){
            this.id = scanner.getID();
            scanner.nextToken();
            if(scanner.currentToken() == Core.COMMA){
                scanner.nextToken();
                this.formals = new Formals();
                this.formals.parse(scanner);
            }
        }
        else {
            if(scanner.currentToken() == Core.CONST){
                System.out.println("Error is function call with constant as actual parameter");
            }
            else {
                System.out.println("Error is bad function call (empty id list)");
            }


            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount) {

    }
}
