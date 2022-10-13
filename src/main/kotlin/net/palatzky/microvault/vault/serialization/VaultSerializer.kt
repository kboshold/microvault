package net.palatzky.microvault.vault.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import net.palatzky.microvault.encryption.pbe.PbeEncryption
import net.palatzky.microvault.util.createPBEKey
import net.palatzky.microvault.util.encodeKey
import net.palatzky.microvault.vault.Vault
import net.palatzky.microvault.vault.serialization.data.EncryptionOptions
import net.palatzky.microvault.vault.serialization.data.VaultData
import java.io.OutputStream
import java.util.*

/**
 * Vault serializer to serialize a vault.
 *
 * @constructor Create empty Vault serializer
 */
class VaultSerializer (){
	companion object {
		const val AUTHENTICATION_DATA = "MicroVault"
	}

	/**
	 * Serialize the given vault into the given output stream and encrypting the data with the passed password.
	 *
	 * @param vault
	 * @param output
	 * @param password
	 */
	fun serialize(vault: Vault, output: OutputStream, password: String) {
		val encryption = vault.encryption;
		var decryption = vault.decryption;


		val base64 = Base64.getEncoder()
		val salt = base64.encodeToString(vault.salt)

		val pbeKey = createPBEKey(password)
		val pbeEncryption = PbeEncryption(pbeKey, vault.salt)

		// encode write/read key using base64
		val encodedWriteKey = encodeKey(encryption.key)
		val encodedReadKey = encodeKey(decryption.key)

		val encryptedReadKey = base64.encodeToString(pbeEncryption.encrypt(encodedReadKey))

		val encryptionOptions = if (encodedWriteKey === encodedReadKey) {
			EncryptionOptions(mode=vault.mode, salt=salt, key = encryptedReadKey)
		} else {
			EncryptionOptions(mode=vault.mode, salt=salt, writeKey = encodedWriteKey, readKey = encryptedReadKey)
		}

		val vaultData = VaultData(
			version = "1.0.0",
			encryption = encryptionOptions,
			data = vault.entries.map {
				val encryptedKey = base64.encodeToString(encryption.encrypt(it.key, AUTHENTICATION_DATA))
				val encryptedValue = base64.encodeToString(encryption.encrypt(it.value, AUTHENTICATION_DATA))
				encryptedKey to encryptedValue
			}.toMap()
		)

		val objectMapper = ObjectMapper()
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		objectMapper.writeValue(output, vaultData)
	}
}