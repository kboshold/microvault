package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption
import net.palatzky.microvault.service.VaultService
import java.nio.file.Path

/**
 * Implementation of the vault.
 *
 * @property mode
 * @property salt
 * @property encryption
 * @property decryption
 * @constructor Create empty Micro vault
 */
class MicroVault() : Vault {

	private val data: MutableMap<String, String> = mutableMapOf()

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