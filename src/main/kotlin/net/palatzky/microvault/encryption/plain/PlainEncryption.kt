package net.palatzky.microvault.encryption.plain

import net.palatzky.microvault.encryption.EmptyKey
import net.palatzky.microvault.encryption.Encryption
import java.security.Key

class PlainEncryption() : Encryption {
	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		return content.toByteArray(Charsets.UTF_8)
	}

	override val key: Key
		get() = EmptyKey
}