package cli;

import java.util.List;
import java.util.Scanner;

/**
 * Classes implementing that interface are commands from the CLI interface.
 * They must be registered in the commands enum to work.
 * @author excilys
 *
 */
public interface Command {
	
	/**
	 * Execute the command's result.
	 * @param args
	 * @param sc
	 */
	
	void doAction(List<String> args, Scanner sc);

}
