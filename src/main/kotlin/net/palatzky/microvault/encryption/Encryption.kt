package net.palatzky.microvault.encryption

import java.nio.ByteBuffer
import java.security.Key

interface Encryption {
	fun encrypt(content: String, authenticationData: String? = null) : ByteArray

	val key: Key;
}