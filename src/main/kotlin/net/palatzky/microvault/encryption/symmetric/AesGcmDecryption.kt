package net.palatzky.microvault.encryption.symmetric

import net.palatzky.microvault.encryption.Decryption
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec

/**
 * Aes gcm decryption
 *
 * @property key
 * @constructor Create empty Aes gcm decryption
 */
class AesGcmDecryption(
	override val key: Key,
): Decryption {
	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val cipher = Cipher.getInstance("AES/GCM/NoPadding")

		val gcmParameter = GCMParameterSpec(128, content, 0, AesGcmEncryption.GCM_IV_LENGTH)
		cipher.init(Cipher.DECRYPT_MODE, key, gcmParameter)

		if (authenticationData != null) {
			cipher.updateAAD(authenticationData.toByteArray(Charsets.UTF_8))
		}

		return cipher.doFinal(content, AesGcmEncryption.GCM_IV_LENGTH, content.size - AesGcmEncryption.GCM_IV_LENGTH).toString(Charsets.UTF_8)
	}
}