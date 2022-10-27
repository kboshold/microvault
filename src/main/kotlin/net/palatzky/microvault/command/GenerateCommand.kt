package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

/**
 * Sets a value for a key
 *
 * @property vaultService
 * @constructor Create empty Set command
 */
@CommandLine.Command(name = "generate", aliases = ["G"],  description = ["Sets an entry in the micro vault"])
class GenerateCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Parameters(index = "0", description = ["Key of the micro vault entry"])
	lateinit var key: String;

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		// open vault & get value of passed key
		vaultService.open(entryCommand.file, entryCommand.key)
		vaultService.generate(key)
		vaultService.write(entryCommand.file)
	}
}