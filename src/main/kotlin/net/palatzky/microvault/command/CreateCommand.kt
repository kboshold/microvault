package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

@CommandLine.Command(name = "create", aliases = ["c"], description = ["Creates a new micro vault"] )
class CreateCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Option(names = ["-m", "--mode"], description = ["Enables symmetric encryption"], defaultValue = false.toString())
	var mode: VaultService.EncryptionMode = VaultService.EncryptionMode.asymmetric

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		println("GOT " + this.entryCommand.password)
	}
}