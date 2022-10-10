package net.palatzky.microvault.encryption.pbe

import net.palatzky.microvault.encryption.Decryption
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEParameterSpec

class PbeDecryption(
	override val key: Key,
	val salt: ByteArray,
): Decryption {

	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		val ivSpec = IvParameterSpec(ByteArray(16).map { 10.toByte() }.toByteArray())
		val pbeParamSpec = PBEParameterSpec(salt, 1012, ivSpec)
		val cipher = Cipher.getInstance("PBEWITHHMACSHA512ANDAES_256")
		cipher.init(Cipher.DECRYPT_MODE, key, pbeParamSpec)
		println("GOT "+  Arrays.toString(content))
		return cipher.doFinal(content).toString(Charsets.UTF_8)
	}
}