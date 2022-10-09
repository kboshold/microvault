package net.palatzky.microvault.encryption.pbe

import net.palatzky.microvault.encryption.Decryption
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.PBEParameterSpec

class PbeDecryption (
	override val key: Key,
	val salt: ByteArray
): Decryption {

	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val pbeParamSpec = PBEParameterSpec(salt, 42)
		val cipher = Cipher.getInstance("PBEWITHHMACSHA512ANDAES_256")
		cipher.init(Cipher.DECRYPT_MODE, key, pbeParamSpec)
		return cipher.doFinal(content).toString(Charsets.UTF_8)
	}
}