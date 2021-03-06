package org.petschko.rpgmakermv.decrypt;

import org.petschko.lib.Const;
import org.petschko.lib.gui.JOptionPane;
import org.petschko.lib.gui.notification.ErrorWindow;
import org.petschko.lib.gui.notification.InfoWindow;
import org.petschko.lib.update.Update;
import org.petschko.lib.update.UpdateException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: Peter Dragicevic
 * Authors-Website: https://petschko.org/
 * Date: 20.02.2021
 * Time: 17:29
 *
 * Notes: -
 */

class GUI_Update {
	private GUI gui;
	private Update update = null;
	private String[] options;
	private boolean autoOptionExists = false;
	private boolean ranAutomatically = false;

	/**
	 * GUI_Update constructor
	 *
	 * @param gui - Main GUI-Object
	 */
	GUI_Update(GUI gui) {
		this.gui = gui;
		this.options = new String[] {"Update", "Show whats new", "Cancel"};

		this.init();
	}

	/**
	 * GUI_Update constructor
	 *
	 * @param gui - Main GUI-Object
	 * @param auto - This ran automatically
	 */
	GUI_Update(GUI gui, boolean auto) {
		this.gui = gui;
		this.options = new String[] {"Update", "Show whats new", "Disable update check", "Cancel"};
		this.autoOptionExists = true;
		this.ranAutomatically = auto;

		this.init();
	}

	/**
	 * Inits the Object
	 */
	private void init() {
		try {
			if(this.ranAutomatically)
				update = new Update(Config.updateUrl, Config.versionNumber, Config.updateCheckEverySecs);
			else
				update = new Update(Config.updateUrl, Config.versionNumber, true);
		} catch (IOException e) {
			if(! this.ranAutomatically) {
				ErrorWindow ew = new ErrorWindow("Can't check for Updates...", ErrorWindow.ERROR_LEVEL_WARNING, false);
				ew.show(this.gui.getMainWindow());
			}
		}

		this.checkIfUpdate();
	}

	/**
	 * Checks if an update exists
	 */
	private void checkIfUpdate() {
		if(update != null) {
			if(update.isHasNewVersion()) {
				// Ask the user what to do
				int response = JOptionPane.showOptionDialog(
						this.gui.getMainWindow(),
						"Update found!" + Const.newLine +
								"Your Version: " + Config.versionNumber + Const.newLine +
								"New Version: " + update.getNewestVersion() + Const.newLine + Const.newLine +
								"What do you want to do?",
						"Update found",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						this.options,
						this.options[0]
				);

				if(response == 0)
					this.runUpdate();
				else if(response == 1)
					this.showWhatsNew();
				else if(this.autoOptionExists && response == 2) {
					App.preferences.switchBoolConfig(Preferences.autoCheckForUpdates);
					gui.getMainMenu().checkForUpdates.setState(false);
				}
			} else if(! this.ranAutomatically) {
				InfoWindow infoWindow = new InfoWindow("You're using the newest Version!", "No update found");
				infoWindow.show(this.gui.getMainWindow());
			}
		}
	}

	/**
	 * Runs the update
	 */
	private void runUpdate() {
		try {
			update.runUpdate(Config.jarFileUpdate, true, true, null);
		} catch(UpdateException e) {
			ErrorWindow errorWindow = new ErrorWindow("Update Failed!", ErrorWindow.ERROR_LEVEL_ERROR, false, e);
			errorWindow.show(this.gui.getMainWindow());

			e.printStackTrace();
		}
	}

	/**
	 * Brings the user to the whats new url
	 */
	private void showWhatsNew() {
		if(Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();

			if(desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					URI uri = new URI(update.getWhatsNewUrl().toString());
					desktop.browse(uri);
				} catch(IOException | URISyntaxException e) {
					ErrorWindow errorWindow = new ErrorWindow("Can't open \"What's new...\"", ErrorWindow.ERROR_LEVEL_ERROR, false, e);
					errorWindow.show(this.gui.getMainWindow());

					e.printStackTrace();
				}
			}
		} else {
			ErrorWindow errorWindow = new ErrorWindow("Can't open \"What's new...\"..." + Const.newLine + "This operation isnt supported by your OS!", ErrorWindow.ERROR_LEVEL_ERROR, false);
			errorWindow.show(this.gui.getMainWindow());
		}
	}
}
