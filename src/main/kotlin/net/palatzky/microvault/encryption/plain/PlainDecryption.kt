package net.palatzky.microvault.encryption.plain

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.EmptyKey
import java.security.Key

/**
 * Plain decryption
 *
 * @constructor Create empty Plain decryption
 */
class PlainDecryption() : Decryption {
	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		return content.toString(Charsets.UTF_8)
	}

	override val key: Key
		get() = EmptyKey
}