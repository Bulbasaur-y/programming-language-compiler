package tree;

import java.util.HashSet;

/**
 * <id-list> ::= id | id , <id-list>
 */
public class IdList implements Node {

    private String id;
    private IdList idList;

    public void execute(){
        if(idSet.add(id)){
            if(this.idList != null){
                idList.execute();
            }
        }
        else {
            errorDoubleId(id);
        }
    }

    @Override
    public void parse(Scanner scanner) {
        if(scanner.currentToken() == Core.ID){
            this.id = scanner.getID();
            Core next = scanner.nextToken();
            if(next == Core.COMMA){
                this.idList = new IdList();
                scanner.nextToken();
                idList.parse(scanner);
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
