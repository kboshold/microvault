package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption
import net.palatzky.microvault.service.VaultService
import java.nio.file.Path

/**
 * Implementation of the vault.
 *
 * @constructor Create empty Micro vault
 */
class MicroVault(entries: Map<String, String> = mapOf()) : Vault {

	private val data: MutableMap<String, String>;

	init {
		this.data = entries.toMutableMap()
	}


	override val entries: Set<VaultEntry>
		get() = data.entries.map {
			VaultEntry(it.key, it.value)
		}.toSet()

	override fun get(key: String): String? {
		return data[key]
	}

	override fun set(key: String, value: String) {
		this.data[key] = value
	}
}