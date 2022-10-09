package net.palatzky.microvault.encryption.pbe

import net.palatzky.microvault.encryption.Encryption
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.PBEParameterSpec

class PbeEncryption(
	override val key: Key,
	val salt: ByteArray
) : Encryption {
	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		val pbeParamSpec = PBEParameterSpec(salt, 42)
		val cipher = Cipher.getInstance("PBEWITHHMACSHA512ANDAES_256")
		cipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec)
		return cipher.doFinal(content.toByteArray(Charsets.UTF_8))
	}
}