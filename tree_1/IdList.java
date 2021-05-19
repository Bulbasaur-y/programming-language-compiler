package tree;

import java.util.HashSet;

/**
 * <id-list> ::= id | id , <id-list>
 */
public class IdList implements Node {

    private String id;
    private IdList idList;

    @Override
    public void build(Scanner scanner) {
        if(scanner.currentToken() == Core.ID){
            this.id = scanner.getID();
            Core next = scanner.nextToken();
            if(next == Core.COMMA){
                this.idList = new IdList();
                scanner.nextToken();
                idList.build(scanner);
            }
        }
        else {
            missCore(Core.ID);
            System.exit(2);
        }
    }

    @Override
    public void printInfo(HashSet<String> set, int tCount){
       if(set.add(id)){
            System.out.print(id);
            if(this.idList != null){
                System.out.print(",");
                this.idList.printInfo(set,0);
            }
       }
       else {
           errorDoubleId(id);
       }
    }

}
