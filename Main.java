import tree.Program;
import tree.Scanner;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(args[0]);

        Program program = new Program();
        program.build(scanner);

        program.printInfo(new HashSet<>(), 0);
        System.out.print("\n");
    }
}
