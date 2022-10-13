package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

/**
 * Creates a new vault
 *
 * @property vaultService
 * @constructor Create empty Create command
 */
@CommandLine.Command(name = "create", aliases = ["c"], description = ["Creates a new micro vault"] )
class CreateCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Option(names = ["-m", "--mode"], description = ["Encryption mode"], defaultValue = false.toString())
	var mode: VaultService.EncryptionMode = VaultService.EncryptionMode.asymmetric

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		vaultService.create(entryCommand.file, entryCommand.password, this.mode)
	}
}