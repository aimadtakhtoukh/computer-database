package cli;

import java.util.List;
import java.util.Scanner;

public class ExitCommand implements Command {

	@Override
	public void doAction(List<String> args, Scanner sc) {
		System.out.println("Program end.");
		sc.close();
		System.exit(0);
	}

}
