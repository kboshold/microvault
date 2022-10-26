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

	companion object {
		val KEY_DOCKER_USERNAME = "docker-username";
		val KEY_DOCKER_PASSWORD = "docker-password";

		val KEY_DOCKER_SERVER = "docker-server"
		val KEY_DOCKER_EMAIL = "docker-email";
	}
	override fun export(vault: Vault, options: ExportOptions) {
		val values = options.mapping.map {(kubernetesKey, vaultKey) ->
			kubernetesKey to vault.get(vaultKey)
		}.toMap().toMutableMap()

		val builder = ProcessBuilder()
		if (values.containsKey(KEY_DOCKER_USERNAME) && values.containsKey(KEY_DOCKER_PASSWORD)) {
			var genericOptions = "--docker-username=${values[KEY_DOCKER_USERNAME]} --docker-password=${values[KEY_DOCKER_PASSWORD]}"
			if (values.containsKey(KEY_DOCKER_SERVER)) {
				genericOptions += " --docker-server=${values[KEY_DOCKER_SERVER]}"
			}
			if (values.containsKey(KEY_DOCKER_EMAIL)) {
				genericOptions += " --docker-email=${values[KEY_DOCKER_EMAIL]}"
			}
			builder.command("kubectl create secret docker-registry $name $genericOptions")

			val process = builder.start()

			values.remove(KEY_DOCKER_USERNAME);
			values.remove(KEY_DOCKER_PASSWORD);
			values.remove(KEY_DOCKER_SERVER)
			values.remove(KEY_DOCKER_EMAIL);

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