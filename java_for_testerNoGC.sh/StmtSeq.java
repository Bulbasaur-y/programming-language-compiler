class StmtSeq {
	Stmt stmt;
	StmtSeq ss;
	
	void parse() {
		if (Parser.scanner.currentToken() == Core.ID) {
			stmt = new Assign();
		} else if (Parser.scanner.currentToken() == Core.INPUT) {
			stmt = new Input();
		} else if (Parser.scanner.currentToken() == Core.OUTPUT) {
			stmt = new Output();
		}  else if (Parser.scanner.currentToken() == Core.IF) {
			stmt = new If();
		} else if (Parser.scanner.currentToken() == Core.WHILE) {
			stmt = new Loop();
		}  else if (Parser.scanner.currentToken() == Core.INT) {
			stmt = new Decl();
		} else if (Parser.scanner.currentToken() == Core.BEGIN) {
			stmt = new FuncCall();
		} else if(Parser.scanner.currentToken() == Core.DEFINE){
			stmt = new NewDecl();
		}
		else {
			System.out.println("ERROR: Bad start to statement: " + Parser.scanner.currentToken());
			System.exit(0);
		}
		stmt.parse();
		if ((Parser.scanner.currentToken() != Core.END) 
			&& (Parser.scanner.currentToken() != Core.ENDIF)
			&& (Parser.scanner.currentToken() != Core.ENDWHILE)
			&& (Parser.scanner.currentToken() != Core.ELSE)
			&& (Parser.scanner.currentToken() != Core.ENDFUNC)) {
			ss = new StmtSeq();
			ss.parse();
		}
	}
	
	void semantic() {
		stmt.semantic();
		if (ss != null) {
			ss.semantic();
		}
	}
			
	void print(int indent) {
		stmt.print(indent);
		if (ss != null) {
			ss.print(indent);
		}
	}
	
	void execute() {
		stmt.execute();
		if (ss != null) {
			ss.execute();
		}
	}
}