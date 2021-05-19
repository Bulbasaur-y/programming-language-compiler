import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

class Executor {
    //data file is stored here as a static field so it is avaiable to the Input execute method
    public static Scanner scanner;

    /*
     *
     * These are the data structures and helper functions to handle variables
     *
     */
	public static ArrayList<Integer> heap;
	public static ArrayList<Integer> heapCount;

    //gobalVars is a data structure to store the global variable values
    public static HashMap<String, Integer> globalVars;
    //variables represents the call stack
    public static Stack<Stack<StackData>> variables;

    //funcMap is a data structure to associate funciton names with definition, used in FuncDecl and FuncCall
    public static HashMap<String, FuncDecl> funcMap;

    //Called from Program class to initialize all data structures
    static void initExecutor() {
        variables = new Stack<Stack<StackData>>();
        globalVars = new HashMap<String, Integer>();
        funcMap = new HashMap<String, FuncDecl>();

        heap = new ArrayList<Integer>();
        heapCount = new ArrayList<Integer>();

    }

    //Push a nested scope onto the current frame, handles new nested scope for If or Loop statements
    static void pushScope() {
        variables.peek().push(new StackData());
    }

    //Pop a nested scope off of the current frame
    static void popScope() {
    	variables.peek().pop();
    }

    //Called from Id class to handle assigning variables, handles new nested scope for If or Loop statements
    static void varSet(String x, int value) {
        if (!variables.peek().empty()) {
			StackData stackData = variables.peek().pop();
            if (stackData.type.containsKey(x)) {
            	int type = stackData.type.get(x);
            	if(type == 0){
					stackData.intVar.put(x,value);
				}
            	else {
					stackData.refVar.put(x,value);
				}
            } else {
                varSet(x, value);
            }
            variables.peek().push(stackData);
        } else {
            // If we get here, it must be a global variable
            globalVars.put(x, value);
        }
    }

    //Called from Id class to handle fetching the value of a variable
    static Integer varGet(String x) {
        Integer value = null;
        if (!variables.peek().empty()) {
			StackData stackData = variables.peek().pop();
			if (stackData.type.containsKey(x)) {
				int type = stackData.type.get(x);
				if(type == 0){
					value = stackData.intVar.get(x);
				}
				else {
                    value = stackData.refVar.get(x);
				}
			} else {
				value = varGet(x);
			}
			variables.peek().push(stackData);
        } else {
            value = globalVars.get(x);
        }
        return value;
    }

    static Integer typeGet(String x) {
		Integer value = null;
		if (!variables.peek().empty()) {
			StackData stackData = variables.peek().pop();
			if (stackData.type.containsKey(x)) {
				value = stackData.type.get(x);
			} else {
				value = typeGet(x);
			}
			variables.peek().push(stackData);
		} else {
			value = null;
		}
		return value;
	}

    //Called from Id class to handle declaring a variable
    static void varInit(String x) {
        //Put null here so we can tell later if variable is uninitialized
        //If variables.peek() is null, initializing a global variable
        if (variables.size() == 0) {
            globalVars.put(x, null);
        } else {
            variables.peek().peek().type.put(x, 0);
        }
    }

	static void heapInit(String x) {
    	variables.peek().peek().type.put(x, 1);
		variables.peek().peek().refVar.put(x, -1);
	}

    /*
     *
     * These are the data structures and helper functions to handle function calls
     *
     */

    //Called from FuncDecl to add function definition to funcMap
    static void registerFunction(Id id, FuncDecl temp) {
        funcMap.put(id.getString(), temp);
    }

    //Called from FuncCall to retrieve definition of called function
    static FuncDecl retrieveFunction(Id id) {
        return funcMap.get(id.getString());
    }


    /*
     *
     * These are the helper functions to handle maintaining the call stack
     *
     */

    //Called to push a new frame onto the call stack
    static void pushFrame() {
        variables.push(new Stack<StackData>());
        variables.peek().push(new StackData());
    }

    //Called to pop a frame off the call stack
    static void popFrame() {
        variables.pop();
    }

    //Called to push a new frame onto the call stack and pass parameters
    static void pushFrame(List<String> formals, List<String> arguments) {
        Stack<StackData> newFrame = new Stack<StackData>();
        StackData newStackData = new StackData();
        newFrame.push(newStackData);
        for (int i = 0; i < formals.size(); i++) {
        	Integer type = typeGet(arguments.get(i));
        	if(type != null){
				newStackData.type.put(formals.get(i),type);
        		if(type.intValue() == 1){
					newFrame.peek().refVar.put(formals.get(i), varGet(arguments.get(i)));
				}
        		else {
					newFrame.peek().intVar.put(formals.get(i), varGet(arguments.get(i)));
				}
			}
        }
        variables.push(newFrame);
    }

    //Called to pop a frame off the call stack and pass back parameters
    static void popFrame(List<String> formals, List<String> arguments) {
        Stack<StackData> oldFrame = variables.pop();
        for (int i = 0; i < formals.size(); i++) {
			Integer type = typeGet(arguments.get(i));
			if(type != null){
				if(type == 0){
					varSet(arguments.get(i), oldFrame.peek().intVar.get(formals.get(i)));
				}
				else {
					varSet(arguments.get(i), oldFrame.peek().refVar.get(formals.get(i)));
				}
			}
        }
    }

    static class StackData{
		HashMap<String, Integer> type;
		HashMap<String, Integer> intVar;
		HashMap<String, Integer> refVar;

		StackData(){
			type = new HashMap<>();
			intVar = new HashMap<>();
			refVar = new HashMap<>();
		}
	}

	static int saveHeapValue(int heapValue){
    	int index = heap.indexOf(heapValue);
    	if(index < 0){
    		heap.add(heapValue);
    		heapCount.add(0);
    		return heap.size() - 1;
		}
    	else {
    	    return index;
        }
	}
}