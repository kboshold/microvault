package net.palatzky.microvault.vault

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption
import net.palatzky.microvault.service.VaultService

interface Vault {
	val mode: VaultService.EncryptionMode
	val salt: ByteArray
	val encryption: Encryption
	val decryption: Decryption

	val entries: Set<VaultEntry>;

	fun get(key: String): String?

	fun set(key: String, value: String)
}