package net.palatzky.microvault.encryption.asymmetric

import net.palatzky.microvault.encryption.Decryption
import java.security.Key
import javax.crypto.Cipher

/**
 * Rsa ecb decryption
 *
 * @property key
 * @constructor Create empty Rsa ecb decryption
 */
class RsaEcbDecryption (
	override val key: Key
): Decryption {

	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING")
		cipher.init(Cipher.DECRYPT_MODE, key)
		return cipher.doFinal(content).toString(Charsets.UTF_8)
	}
}