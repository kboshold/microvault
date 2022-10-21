package net.palatzky.microvault.vault.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import net.palatzky.microvault.encryption.pbe.PbeEncryption
import net.palatzky.microvault.util.createPBEKey
import net.palatzky.microvault.util.encodeKey
import net.palatzky.microvault.vault.Vault
import net.palatzky.microvault.vault.option.Options
import net.palatzky.microvault.vault.serialization.data.OptionsData
import net.palatzky.microvault.vault.serialization.data.VaultData
import java.io.OutputStream
import java.util.*

/**
 * Vault serializer to serialize a vault.
 *
 * @constructor Create empty Vault serializer
 */
class VaultSerializer() {
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
	fun serialize(vault: Vault, options: Options, output: OutputStream, password: String) {
//		val encryption = vault.encryption;
//		var decryption = vault.decryption;

		val base64 = Base64.getEncoder()
		val salt = base64.encodeToString(options.salt)

		val pbeKey = createPBEKey(password)
		val pbeEncryption = PbeEncryption(pbeKey, options.salt)

		// encode write/read key using base64
		val encodedEncryptionKey = encodeKey(options.encryptionKey)
		val encodedDecryptionKey = encodeKey(options.decryptionKey)

		// only encrypt read key since the public key should be readable
		val encryptedDecryptionKey = base64.encodeToString(pbeEncryption.encrypt(encodedDecryptionKey))

		//
		val encryptionOptions = if (encodedEncryptionKey === encodedDecryptionKey) {
			OptionsData(mode = options.mode, salt = salt, key = encryptedDecryptionKey)
		} else {
			OptionsData(
				mode = options.mode,
				salt = salt,
				encryptionKey = encodedEncryptionKey,
				decryptionKey = encryptedDecryptionKey
			)
		}

		val vaultData = VaultData(
			version = "1.0.0",
			encryption = encryptionOptions,
			data = vault.entries.map {
				it.key to it.value
			}.toMap()
		)

		val objectMapper = ObjectMapper()
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		objectMapper.writeValue(output, vaultData)
	}
}