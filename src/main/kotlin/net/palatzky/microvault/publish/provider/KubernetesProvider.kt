package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault

/**
 * Kubernetes provider
 *
 * @property name
 * @constructor Create empty Kubernetes provider
 */
class KubernetesProvider(
	private val name: String
) : Provider {
	override fun export(vault: Vault, options: ExportOptions) {
		val values = options.mapping.map {(kubernetesKey, vaultKey) ->
			kubernetesKey to vault.get(vaultKey)
		}.toMap().toMutableMap()

		val builder = ProcessBuilder()
		if (values.containsKey("docker-username") && values.containsKey("docker-password")) {
			var genericOptions = "--docker-username=${values["docker-username"]} --docker-password=${values["docker-password"]}"
			if (values.containsKey("docker-server")) {
				genericOptions += " --docker-server=${values["docker-server"]}"
			}
			if (values.containsKey("docker-email")) {
				genericOptions += " --docker-server=${values["docker-email"]}"
			}
			builder.command("kubectl create secret docker-registry $name $genericOptions")

			val process = builder.start()

			values.remove("docker-username");
			values.remove("docker-password");
			values.remove("docker-server")
			values.remove("docker-email");

			process.waitFor()
		}

		val genericOptions = values.map {(key, value) ->
			"--from-literal=$key=$value"
		}.joinToString(" ")

		builder.command("kubectl create secret generic $name $genericOptions")
		val process = builder.start()
		process.waitFor()
	}
}