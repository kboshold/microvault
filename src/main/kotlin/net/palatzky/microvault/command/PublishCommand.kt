package net.palatzky.microvault.command

import net.palatzky.microvault.publish.provider.EnvironmentProvider
import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

@CommandLine.Command(name = "publish", description = ["Publishes multiple entries in the micro vault"])
class PublishCommand(
	private val vaultService: VaultService
): Runnable {
	@CommandLine.Parameters(index = "0", description = ["Publishing provider"], )
	lateinit var provider: String

	@CommandLine.Option(names = ["-m", "--map"], description = ["Mapping for the export. <ExportKey>=<VaultKey>"])
	var mapping: Map<String, String> = mapOf()

	@CommandLine.Option(names = ["--kubernetes-name"], description = ["Name of the kubernetes secret store"])
	var kubernetesName: String? = null

	@CommandLine.Option(names = ["--generic-template"], description = ["Template for the generic provider"])
	var genericTemplate: String? = null

	override fun run() {
		vaultService.publish()
	}
}