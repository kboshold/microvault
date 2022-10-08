package net.palatzky.microvault.command

import net.palatzky.microvault.publish.provider.EnvironmentProvider
import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

@CommandLine.Command(name = "publish", aliases = ["p", "export"],  description = ["Publishes multiple entries in the micro vault"])
class PublishCommand(
	private val vaultService: VaultService
): Runnable {
	@CommandLine.Parameters(index = "0", description = ["Publishing provider"], )
	lateinit var provider: String

	@CommandLine.Option(names = ["-m", "--map"], description = ["Mapping for the export. <ExportKey>=<VaultKey>"])
	var mapping: Map<String, String> = mapOf()

	@CommandLine.Option(names = ["-D", "--parameter"], description = ["Parameter for the given provider"])
	var parameter: Map<String, String> = mapOf()

	override fun run() {
		vaultService.publish()
	}
}