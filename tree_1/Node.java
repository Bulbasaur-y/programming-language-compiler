package tree;

import java.util.HashSet;

public interface Node {

    void build(Scanner scanner);


    default void errorNoId(String id){
        System.out.println();
        System.out.print("ERROR:no declaration the ID:" + id);
        System.exit(2);
    }

    default void errorDoubleId(String id){
        System.out.println();
        System.out.print("ERROR:double declaration the ID:" + id);
        System.exit(2);
    }

    default void errorCore(Core core){
        System.out.print("ERROR:invalid tree.Core:" + core.toString() + " when build parse tree");
    }

    default void missCore(Core core){
        System.out.print("ERROR:tree.Core:" + core.toString() + " miss");
    }

    void printInfo(HashSet<String> set,int tCount);

    default String getTString(int tCount){
        switch (tCount){
            case 1:
                return "\t";
            case 2:
                return "\t\t";
            case 3:
                return "\t\t\t";
            case 0:
                return "";
            default:
                StringBuilder stringBuilder = new StringBuilder();
                while (tCount-- > 0){
                    stringBuilder.append("\t");
                }
                return stringBuilder.toString();
        }
    }
}
