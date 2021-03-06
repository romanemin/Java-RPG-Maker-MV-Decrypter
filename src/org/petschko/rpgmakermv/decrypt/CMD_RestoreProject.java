package org.petschko.rpgmakermv.decrypt;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:37
 *
 * Notes: CMD_RestoreProject Class
 */
class CMD_RestoreProject implements CMD_Command {
	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	@Override
	public void run(String[] args) {
		printHelp();
		CMD.exitCMD(CMD.STATUS_WARNING);
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("restoreproject -> !! NOT IMPLEMENTED YET !!", CMD.STATUS_WARNING);
		App.showMessage("");
		App.showMessage("Usage: java -jar \"RPG Maker MV Decrypter.jar\" restoreproject");
		App.showMessage("");
	}
}
