package net.palatzky.microvault.encryption

import java.nio.ByteBuffer

interface Decryption {
	fun decrypt(content: ByteArray, authenticationData: String? = null) : String
}