package net.palatzky.microvault.encryption

import java.nio.ByteBuffer

interface Encryption {
	fun encrypt(content: String, authenticationData: String? = null) : ByteArray
}