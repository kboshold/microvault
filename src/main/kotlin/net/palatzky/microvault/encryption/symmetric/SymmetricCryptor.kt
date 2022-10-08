package net.palatzky.microvault.encryption.symmetric

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption
import java.nio.ByteBuffer
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class SymmetricCryptor (
	private val secretKey: SecretKey,
) : Decryption, Encryption {
	companion object {
		private const val GCM_IV_LENGTH = 12

		fun createSecretKey(): SecretKey {
			val keyGenerator = KeyGenerator.getInstance("AES")
			keyGenerator.init(256, SecureRandom())
			return keyGenerator.generateKey()
		}
	}

	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val cipher = Cipher.getInstance("AES/GCM/NoPadding")

		val gcmParameter = GCMParameterSpec(128, content, 0, GCM_IV_LENGTH)
		cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameter)

		if (authenticationData != null) {
			cipher.updateAAD(authenticationData.toByteArray(Charsets.UTF_8))
		}

		return cipher.doFinal(content, GCM_IV_LENGTH, content.size - GCM_IV_LENGTH).toString(Charsets.UTF_8)
	}

	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		val cipher = Cipher.getInstance("AES/GCM/NoPadding")

		val gcmData = ByteArray(GCM_IV_LENGTH) //NEVER REUSE THIS IV WITH SAME KEY
		SecureRandom().nextBytes(gcmData)

		val parameterSpec = GCMParameterSpec(128, gcmData);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)

		if (authenticationData != null) {
			cipher.updateAAD(authenticationData.toByteArray(Charsets.UTF_8));
		}

		val cipherText = cipher.doFinal(content.toByteArray(Charsets.UTF_8))

		val byteBuffer = ByteBuffer.allocate(gcmData.size + cipherText.size)
		byteBuffer.put(gcmData)
		byteBuffer.put(cipherText)
		return byteBuffer.array()
	}
}