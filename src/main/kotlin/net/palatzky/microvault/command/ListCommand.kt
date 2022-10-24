package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

/**
 * List all entries of this vault
 *
 * @property vaultService
 * @constructor Create empty List command
 */
@CommandLine.Command(name = "list",  aliases = ["l"], description = ["Lists the entries in the micro vault"])
class ListCommand(
	private val vaultService: VaultService
): Runnable {

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		// open vault & get value of passed key
		vaultService.open(entryCommand.file, entryCommand.key)
		val value = vaultService.list()

		// write value
		print(value ?: "")
		System.out.flush()
	}
}