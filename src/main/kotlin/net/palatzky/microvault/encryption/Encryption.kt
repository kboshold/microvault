package net.palatzky.microvault.encryption

import java.nio.ByteBuffer
import java.security.Key

/**
 * Encryption
 *
 * @constructor Create empty Encryption
 */
interface Encryption {
	/**
	 * Encrypts the content to an encrypted bytearray
	 *
	 * @param content
	 * @param authenticationData
	 * @return
	 */
	fun encrypt(content: String, authenticationData: String? = null) : ByteArray

	/**
	 * Key of the encryption
	 */
	val key: Key;
}