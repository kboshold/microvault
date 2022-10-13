package net.palatzky.microvault.encryption

import java.nio.ByteBuffer
import java.security.Key

/**
 * Decryption
 *
 * @constructor Create empty Decryption
 */
interface Decryption {
	/**
	 * Decrypts the content to a decrypted string
	 *
	 * @param content
	 * @param authenticationData
	 * @return
	 */
	fun decrypt(content: ByteArray, authenticationData: String? = null) : String

	/**
	 * Key of the decryption
	 */
	val key: Key;
}