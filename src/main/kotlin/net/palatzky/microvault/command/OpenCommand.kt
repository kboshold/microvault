package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

/**
 * Opens a existing vault
 *
 * @property vaultService
 * @constructor Create empty Open command
 */
@CommandLine.Command(name = "open", aliases = ["o"],  description = ["Opens the micro vault"])
class OpenCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Option(names = ["-t", "--temporary"], description = ["Who will we greet?"], defaultValue = false.toString())
	private var temporary: Boolean = false

	@CommandLine.Option(names = ["-i", "--interactive"], description = ["Password to use for the micro vault"])
	lateinit var interactive: String

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		vaultService.open(entryCommand.file, entryCommand.key);
	}
}