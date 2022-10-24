package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import net.palatzky.microvault.vault.option.Options
import picocli.CommandLine
import java.lang.Exception

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

	@CommandLine.Option(names = ["-m", "--mode"], description = ["Encryption mode"], defaultValue = "asymmetric")
	var mode: Options.EncryptionMode = Options.EncryptionMode.asymmetric

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		val password = entryCommand.key ?: throw Exception("Password is required for create");
		vaultService.create(entryCommand.file, password, this.mode)
	}
}