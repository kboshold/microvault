package net.palatzky.microvault.encryption.pbe

import net.palatzky.microvault.encryption.Encryption
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEParameterSpec

/**
 * Pbe encryption
 *
 * @property key
 * @property salt
 * @constructor Create empty Pbe encryption
 */
class PbeEncryption(
	override val key: Key,
	val salt: ByteArray
) : Encryption {
	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		val ivSpec = IvParameterSpec(ByteArray(16).map { 10.toByte() }.toByteArray())
		val pbeParamSpec = PBEParameterSpec(salt, 1012, ivSpec)
		val cipher = Cipher.getInstance("PBEWITHHMACSHA512ANDAES_256")
		cipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec)
		return cipher.doFinal(content.toByteArray(Charsets.UTF_8))
	}
}