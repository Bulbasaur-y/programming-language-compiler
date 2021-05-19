package tree;

import java.util.HashSet;

/**
 * <in> ::= input <id> ;
 */
public class In implements Node {

    private String id;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.INPUT){
            Core next = scanner.nextToken();
            if(next == Core.ID){
                this.id = scanner.getID();
                scanner.nextToken();
                if(scanner.currentToken() == Core.SEMICOLON){
                    scanner.nextToken();
                }
                else {
                    missCore(Core.SEMICOLON);
                    System.exit(2);
                }
            }
            else {
                missCore(Core.ID);
                System.exit(2);
            }
        }
        else {
            missCore(Core.INPUT);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
        if(set.contains(id)){
            System.out.print(getTString(tCount)+"input " + id + ";");
            System.out.print("\n");
        }
        else {
            errorNoId(id);
        }
    }
}
