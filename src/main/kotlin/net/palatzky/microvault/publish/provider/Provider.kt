package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault

interface Provider {

	fun export(vault: Vault, options: ExportOptions)

}