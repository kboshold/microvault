package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

/**
 * Returns the value of the passed key
 *
 * @property vaultService
 * @constructor Create empty Get command
 */
@CommandLine.Command(name = "get", aliases = ["g"], description = ["Returns an entry of the micro vault"])
class GetCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Parameters(index = "0", description = ["Key of the micro vault entry"])
	lateinit var key: String;

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		// open vault & get value of passed key
		vaultService.open(entryCommand.file, entryCommand.key)
		val value = vaultService.get(key)

		// write value
		print(value ?: "")
		System.out.flush()
	}
}