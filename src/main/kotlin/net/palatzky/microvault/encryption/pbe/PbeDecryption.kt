package net.palatzky.microvault.encryption.pbe

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.pbe.PbeEncryption.Companion.ITERATION_COUNT
import net.palatzky.microvault.encryption.pbe.PbeEncryption.Companion.IV_SPEC_DATA
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEParameterSpec

/**
 * Pbe decryption
 *
 * @property key
 * @property salt
 * @constructor Create empty Pbe decryption
 */
class PbeDecryption(
	override val key: Key,
	val salt: ByteArray,
): Decryption {

	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val ivSpec = IvParameterSpec(IV_SPEC_DATA)
		val pbeParamSpec = PBEParameterSpec(salt, ITERATION_COUNT, ivSpec)
		val cipher = Cipher.getInstance("PBEWITHHMACSHA512ANDAES_256")
		cipher.init(Cipher.DECRYPT_MODE, key, pbeParamSpec)
		return cipher.doFinal(content).toString(Charsets.UTF_8)
	}
}