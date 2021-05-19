package tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public interface Node {

    void parse(Scanner scanner);


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
        System.out.print("Error is bad function call (missing '"+ core +"')");
    }

    default void missCore(Core core){
        System.out.print("ERROR:tree.Core:" + core.toString() + " miss");
    }

    default void missFunction(String functionName){
        System.out.print("ERROR:function body missing :" + functionName);
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

    HashMap<String,Integer> idMap = new HashMap<>();
    HashSet<String> idSet = new HashSet<>();

    ArrayList<Integer> inputValues = new ArrayList<>();

    AtomicInteger inputIndex = new AtomicInteger(0);

    HashMap<String,FuncDecl> functionMap = new HashMap<>();

    default void initInputValues(ArrayList<Integer> list){
        inputValues.addAll(list);
    }

    default int getInputValue(){
        if(inputIndex.getAndIncrement() < inputValues.size()){
            return inputValues.get(inputIndex.get() - 1);
        }
        else {
            System.out.print("ERROR: not enough input value");
            System.exit(2);
            return 0;
        }
    }

    default void errorInitId(String id){
        System.out.println();
        System.out.print("ERROR:uninitialized the ID:" + id);
        System.exit(2);
    }
}
