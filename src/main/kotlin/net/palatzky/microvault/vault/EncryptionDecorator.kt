package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Encryption
import java.util.Base64

class EncryptionDecorator(
	val encryption: Encryption,
	val vault: Vault
) : Vault {

	override val entries: Set<VaultEntry>
		get() = vault.entries

	override fun get(key: String): String? {
		return vault.get(key)
	}

	override fun set(key: String, value: String) {
		val encoder = Base64.getEncoder()
		vault.set(
			key,
			encoder.encodeToString(encryption.encrypt(value))
		)
	}
}