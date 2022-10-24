package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

/**
 * Publishes entries of the vault
 *
 * @property vaultService
 * @constructor Create empty Publish command
 */
@CommandLine.Command(name = "publish", aliases = ["p", "export"],  description = ["Publishes multiple entries in the micro vault"])
class PublishCommand(
	private val vaultService: VaultService,
): Runnable {
	enum class Provider {
		kubernetes, environment, generic
	}

	@CommandLine.Parameters(index = "0", description = ["Publishing provider"])
	lateinit var provider: Provider

	@CommandLine.Option(names = ["-m", "--map"], description = ["Mapping for the export. <ExportKey>=<VaultKey>"])
	var mapping: Map<String, String> = mapOf()

	@CommandLine.Option(names = ["-D", "--parameter"], description = ["Parameter for the given provider"])
	var parameter: Map<String, String> = mapOf()

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		// open vault & get value of passed key
		vaultService.open(entryCommand.file, entryCommand.key)
		vaultService.publish()
	}
}