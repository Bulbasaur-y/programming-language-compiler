class Assign implements Stmt {
	Id id;
	Expr expr;
	boolean hasNew = false;
	NewDecl newDecl;
	
	public void parse() {
		id = new Id();
		id.parse();
		Parser.expectedToken(Core.ASSIGN);
		Parser.scanner.nextToken();
		if (Parser.scanner.currentToken() == Core.NEW){
			hasNew = true;
			Parser.scanner.nextToken();
			expr = new Expr();
			expr.parse();
			Parser.expectedToken(Core.SEMICOLON);
			Parser.scanner.nextToken();
		}
		else if(Parser.scanner.currentToken() == Core.DEFINE) {
			newDecl = new NewDecl();
			newDecl.parse();
		}
		else{
			expr = new Expr();
			expr.parse();
			Parser.expectedToken(Core.SEMICOLON);
			Parser.scanner.nextToken();
		}
	}
	
	public void semantic() {
		id.semantic();
		if(expr != null){
			expr.semantic();
		}

	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		id.print();
		System.out.print("=");
		if(newDecl != null){
			newDecl.print(indent);
		}
		else {
			if(hasNew){
				System.out.print("new");
			}
			expr.print();
			System.out.println(";");
		}


	}
	
	public void execute() {
		if(newDecl != null){
			Integer index = Executor.typeGet(newDecl.id.identifier);
			if(index == 1){
				Executor.varSet(id.identifier,Executor.varGet(newDecl.id.identifier));
			}
		}
		else {
			if(hasNew){
				int heapValue = expr.execute();
				int heapIndex = Executor.saveHeapValue(heapValue);
				Executor.varSet(id.identifier,heapIndex);
			}
			else {
				id.executeAssign(expr.execute());
			}
		}

	}
}