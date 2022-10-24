package net.palatzky.microvault.vault.option

import java.security.Key

interface Options {
	enum class EncryptionMode {
		PLAIN, ASYMMETRIC, SYMMETRIC
	}

	val salt: ByteArray

	val mode: EncryptionMode

	val encryptionKey: Key

	val decryptionKey: Key

	val pbeKey: Key?
}