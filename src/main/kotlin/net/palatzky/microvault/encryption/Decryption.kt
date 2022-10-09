package net.palatzky.microvault.encryption

import java.nio.ByteBuffer
import java.security.Key

interface Decryption {
	fun decrypt(content: ByteArray, authenticationData: String? = null) : String

	val key: Key;
}