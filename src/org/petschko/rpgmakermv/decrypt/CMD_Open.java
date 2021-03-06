package org.petschko.rpgmakermv.decrypt;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 15.02.2021
 * Time: 23:41
 *
 * Notes: CMD_Open Class
 */
class CMD_Open implements CMD_Command {
	private String pathToFile;

	/**
	 * Runs the Command
	 *
	 * @param args - Command-Line commands
	 */
	@Override
	public void run(String[] args) {
		/*if(args.length < 2) {
			App.showMessage("To less arguments, see help", CMD.STATUS_WARNING);
			App.showMessage("");
			this.printHelp();
			CMD.exitCMD(CMD.STATUS_WARNING);
			return;
		}

		// Set Path to Project
		pathToFile = args[1];
		App.showMessage("Open file: \"" + pathToFile + "\"...");

		openFile();*/
		printHelp();
		CMD.exitCMD(CMD.STATUS_WARNING);
	}

	/**
	 * Opens a RPG-MV/MZ Image-File
	 */
	public void openFile() {
		try {
			// todo fix issue file is deleted before shown

			// Restore image
			org.petschko.lib.File file = new org.petschko.lib.File(pathToFile);
			Decrypter decrypter = new Decrypter();
			decrypter.decryptFile(file, true);

			// Write to tmp file
			File tmpFile = File.createTempFile("temp", ".png");
			Path tmpFilePath = Paths.get(tmpFile.getAbsolutePath());
			Files.write(tmpFilePath, file.getContent());

			// Open and set delete delete tmp file
			Desktop.getDesktop().open(tmpFile);
			tmpFile.deleteOnExit();
		} catch (Exception e) {
			e.printStackTrace();
			CMD.exitCMD(CMD.STATUS_ERROR);
		}

		CMD.exitCMD(CMD.STATUS_OK);
	}

	/**
	 * Prints help for the command
	 */
	@Override
	public void printHelp() {
		App.showMessage("open -> !! NOT IMPLEMENTED YET !!", CMD.STATUS_WARNING);
		App.showMessage("");
		App.showMessage("Usage: java -jar \"RPG Maker MV Decrypter.jar\" open [path to rpgmvp|png_ file]");
		App.showMessage("");
		App.showMessage(CMD.HELP_INDENT + "Params: (Separate each param by a space, for paths use \"\" around the path)");
		App.showMessage(CMD.HELP_INDENT + "  [path to rpgmvp|png_ file] - Path to the rpgmvp/png_ file you want to open");
		App.showMessage("");
	}
}
