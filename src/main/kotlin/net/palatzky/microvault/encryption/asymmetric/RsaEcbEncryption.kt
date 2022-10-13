package net.palatzky.microvault.encryption.asymmetric

import net.palatzky.microvault.encryption.Encryption
import java.security.Key
import javax.crypto.Cipher

/**
 * Rsa ecb encryption
 *
 * @property key
 * @constructor Create empty Rsa ecb encryption
 */
class RsaEcbEncryption (
	override val key: Key
) : Encryption{
	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")

		cipher.init(Cipher.ENCRYPT_MODE, key)
		return cipher.doFinal(content.toByteArray(Charsets.UTF_8))
	}
}