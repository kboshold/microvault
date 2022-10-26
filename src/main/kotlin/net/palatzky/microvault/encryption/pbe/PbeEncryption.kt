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
	companion object {
		val IV_SPEC_DATA = ByteArray(16).map { 10.toByte() }.toByteArray()
		val ITERATION_COUNT = 12032;
	}
	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		val ivSpec = IvParameterSpec(IV_SPEC_DATA)
		val pbeParamSpec = PBEParameterSpec(salt, ITERATION_COUNT, ivSpec)
		val cipher = Cipher.getInstance("PBEWITHHMACSHA512ANDAES_256")
		cipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec)
		return cipher.doFinal(content.toByteArray(Charsets.UTF_8))
	}
}