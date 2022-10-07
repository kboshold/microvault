package net.palatzky.microvault.encryption

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import javax.crypto.Cipher

class AsymmetricCryptor(
	val keyPair: KeyPair,
): Decryption, Encryption {

	companion object {
		fun createKeyPair(): KeyPair {
			val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
			keyPairGenerator.initialize(4096, SecureRandom());
			return keyPairGenerator.generateKeyPair()
		}
	}

	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING")
		cipher.init(Cipher.DECRYPT_MODE, keyPair.private)
		return cipher.doFinal(content).toString(Charsets.UTF_8)
	}

	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		val cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING")
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.public)
		return cipher.doFinal(content.toByteArray(Charsets.UTF_8))
	}
}