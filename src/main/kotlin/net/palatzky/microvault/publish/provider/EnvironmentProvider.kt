package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault

/**
 * Environment provider
 *
 * @constructor Create empty Environment provider
 */
class EnvironmentProvider: Provider {
	override fun export(vault: Vault, options: ExportOptions) {
		options.mapping.forEach {(envKey, vaultKey) ->
			println("export ${envKey}=${vault.get(vaultKey) ?: ""}")
		}
		System.out.flush();
	}
}