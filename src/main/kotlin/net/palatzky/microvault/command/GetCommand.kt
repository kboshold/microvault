package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

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
		val vault = vaultService.open(entryCommand.file, entryCommand.password)
		val value = vaultService.get(vault, key)

		// write value
		print(value ?: "")
		System.out.flush()
	}
}