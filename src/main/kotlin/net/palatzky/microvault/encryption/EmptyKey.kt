package net.palatzky.microvault.encryption

import java.security.Key

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