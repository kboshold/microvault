package net.palatzky.microvault.vault.option

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption
import net.palatzky.microvault.service.VaultService
import java.security.Key

interface Options {
	enum class EncryptionMode {
		plain, asymmetric, symmetric
	}

	val salt: ByteArray

	val mode: EncryptionMode

	val encryptionKey: Key

	val decryptionKey: Key


}