package net.palatzky.microvault.encryption

import java.security.Key

/**
 * Simple empty key with an encoded key of 0
 *
 * @constructor Create empty Empty key
 */
object EmptyKey : Key {
	override fun getAlgorithm(): String {
		return "none"
	}

	override fun getFormat(): String {
		return "none"
	}

	override fun getEncoded(): ByteArray {
		return ByteArray(0)
	}
}