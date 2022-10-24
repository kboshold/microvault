package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Decryption
import java.util.*

class DecryptionDecorator(
	val decryption: Decryption,
	val vault: Vault
) : Vault {

	override val entries: Set<VaultEntry>
		get() {
			val decoder = Base64.getDecoder()
			return vault.entries.map {
				VaultEntry(it.key, decryption.decrypt(decoder.decode(it.value)))
			}.toSet()
		}

	override fun get(key: String): String? {
		val decoder = Base64.getDecoder()
		val encryptedData = vault.get(key) ?: return null
		return decryption.decrypt(decoder.decode(encryptedData))
	}

	override fun set(key: String, value: String) {
		return vault.set(key, value);
	}
}