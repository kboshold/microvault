package net.palatzky.microvault.encryption

import java.security.Key

class PlainDecryption() : Decryption {
	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		return content.toString(Charsets.UTF_8)
	}

	override val key: Key
		get() = EmptyKey
}