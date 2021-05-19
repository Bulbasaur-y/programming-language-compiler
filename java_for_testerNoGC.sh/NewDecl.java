public class NewDecl implements Stmt {

    Id id;

    @Override
    public void parse() {
        Parser.scanner.nextToken();
        id = new Id();
        id.parse();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
    }

    @Override
    public void semantic() {
        id.addToScope();
    }

    @Override
    public void print(int indent) {
        id.print();
    }

    @Override
    public void execute() {
        id.executeReference();
    }
}
