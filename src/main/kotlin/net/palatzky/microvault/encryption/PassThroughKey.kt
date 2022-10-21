package net.palatzky.microvault.encryption

import java.security.Key

class PassThroughKey (
	val data: ByteArray
) : Key {
	companion object {
		const val ALGORITHM = "PASS_THROUGH_ENCODED"
	}

	override fun getAlgorithm(): String {
		return ALGORITHM
	}

	override fun getFormat(): String {
		return ALGORITHM
	}

	override fun getEncoded(): ByteArray {
		return data;
	}
}