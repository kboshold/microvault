package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Decryption
import java.util.*

class DecryptionDecorator(
	val decryption: Decryption,
	val vault: Vault
) : Vault {

	override val entries: Set<VaultEntry>
		get() = vault.entries

	override fun get(key: String): String? {
		val decoder = Base64.getDecoder()
		val encryptedData = vault.get(key);
		return decryption.decrypt(decoder.decode(encryptedData))
	}

	override fun set(key: String, value: String) {
		return vault.set(key, value);
	}
}