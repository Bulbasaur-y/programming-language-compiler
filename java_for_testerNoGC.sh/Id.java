class Id {
	String identifier;
	
	void parse() {
		Parser.expectedToken(Core.ID);
		identifier = Parser.scanner.getID();
		Parser.scanner.nextToken();
	}
	
	void semantic() {
		if (!Parser.nestedScopeCheck(identifier)) {
			System.out.println("ERROR: No matching declaration found: " + identifier);
			System.exit(0);
		}
	}
	
	//Called by Decl.semantic to add the variable to the scopes data structure
	void addToScope() {
		Parser.scopes.peek().add(identifier);
	}
	
	//Called by Decl.semantic to check for doubly declared variables
	void doublyDeclared() {
		if (Parser.currentScopeCheck(identifier)) {
			System.out.println("ERROR: Doubly declared variable detected: " + identifier);
			System.exit(0);
		}
	}
	
	void print() {
		System.out.print(identifier);
	}
	
	//Called by IdList.execute
	void executeAllocate() {
		Executor.varInit(identifier);
	}
	
	//Called by Assign.execute and Input.execute
	void executeAssign(int value) {
		Executor.varSet(identifier, value);
	}
	
	//Called by Factor.execute
	int executeValue() {
		Integer value = Executor.varGet(identifier);
		//This is where we catch uninitialized variables

		if (value == null) {
			System.out.println("Error: Using initialized variable " + identifier);
			System.exit(0);
		}

		Integer type = Executor.typeGet(identifier);
		if(type == null || type == 0){
			return value;
		}
		else {
			return Executor.heap.get(value);
		}
	}

	//Called by NewDecl.execute
	void executeReference(){
		Executor.heapInit(identifier);
	}
	
	String getString() {
		return identifier;
	}
}