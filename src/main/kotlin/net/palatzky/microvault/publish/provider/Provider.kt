package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault

/**
 * Provider interface
 *
 * @constructor Create empty Provider
 */
interface Provider {

	/**
	 * Export the vault using the options
	 *
	 * @param vault
	 * @param options
	 */
	fun export(vault: Vault, options: ExportOptions)

}