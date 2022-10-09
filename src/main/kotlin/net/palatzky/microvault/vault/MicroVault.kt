package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption
import net.palatzky.microvault.service.VaultService
import java.nio.file.Path

class MicroVault(
	override val mode: VaultService.EncryptionMode,
	override val salt: ByteArray,
	override val encryption: Encryption,
	override val decryption: Decryption,
) : Vault {

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