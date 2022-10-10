package net.palatzky.microvault.encryption.asymmetric

import net.palatzky.microvault.encryption.Decryption
import java.security.Key
import javax.crypto.Cipher

class RsaEcbDecryption (
	override val key: Key
): Decryption {
	/*
				val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
			keyPairGenerator.initialize(4096, SecureRandom());
			return keyPairGenerator.generateKeyPair()
	 */
	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
		cipher.init(Cipher.DECRYPT_MODE, key)
		return cipher.doFinal(content).toString(Charsets.UTF_8)
	}
}