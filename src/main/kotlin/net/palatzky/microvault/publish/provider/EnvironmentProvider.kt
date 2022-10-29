package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault

/**
 * Environment provider
 *
 * @constructor Create empty Environment provider
 */
class EnvironmentProvider: Provider {
	override fun export(vault: Vault, options: ExportOptions) {
		var mapping = options.mapping;
		if (options.mapping.isEmpty()) {
			mapping = vault.entries.associate {
				it.key.uppercase().replace("[^A-Z0-9]+".toRegex(), "_") to it.key
			}
		}
		mapping.forEach {(envKey, vaultKey) ->
			println("export ${envKey}=${vault.get(vaultKey) ?: ""}")
		}
		System.out.flush();
	}
}