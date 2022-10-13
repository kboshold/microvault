package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault

/**
 * Generic provider
 *
 * @property template
 * @constructor Create empty Generic provider
 */
class GenericProvider (
	private val template: String
	): Provider {

	override fun export(vault: Vault, options: ExportOptions) {
		options.mapping.forEach {(envKey, vaultKey) ->
			print(
				template.format(envKey, vault.get(vaultKey), vaultKey)
			)
		}
		System.out.flush()
	}
}