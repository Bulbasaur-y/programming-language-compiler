package tree;

import java.util.HashSet;

/**
 * <decl> ::= int <id-list> ;
 */
public class Decl implements Node {

    private IdList idList;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.INT){
            this.idList = new IdList();
            scanner.nextToken();
            idList.build(scanner);
            if(scanner.currentToken() == Core.SEMICOLON){
                scanner.nextToken();
            }
            else {
                missCore(Core.SEMICOLON);
                System.exit(2);
            }
        }
        else {
            missCore(Core.INT);
            System.exit(2);
        }
    }


    @Override
    public void printInfo(HashSet<String> set, int tCount){
        System.out.print(getTString(tCount) + "int ");
        idList.printInfo(set,0);
        System.out.print(";");
        System.out.print("\n");
    }
}
