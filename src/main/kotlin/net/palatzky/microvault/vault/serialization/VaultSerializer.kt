package net.palatzky.microvault.vault.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import net.palatzky.microvault.encryption.PassThroughKey
import net.palatzky.microvault.encryption.pbe.PbeEncryption
import net.palatzky.microvault.util.createPBEKey
import net.palatzky.microvault.util.encodeKey
import net.palatzky.microvault.vault.DecryptionDecorator
import net.palatzky.microvault.vault.EncryptionDecorator
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
	fun serialize(vault: Vault, options: Options, output: OutputStream) {
		val base64 = Base64.getEncoder()
		val salt = base64.encodeToString(options.salt)

//		val pbeKey = createPBEKey(password)
		// encode write/read key using base64

		val encodedEncryptionKey = encodeKey(options.encryptionKey)
		val encodedDecryptionKey = encodeKey(options.decryptionKey)

		// only encrypt read key since the public key should be readable
		val pbeKey = options.pbeKey
		val encryptedDecryptionKey = if (pbeKey != null && options.decryptionKey !is PassThroughKey) {
		val pbeEncryption = PbeEncryption(pbeKey, options.salt)
     base64.encodeToString(pbeEncryption.encrypt(encodedDecryptionKey))
		} else {
			encodedDecryptionKey
		}

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

		var rawVault = vault;
		while (rawVault is DecryptionDecorator || rawVault is EncryptionDecorator) {
			rawVault = if (rawVault is DecryptionDecorator) {
				rawVault.vault
			} else {
				(rawVault as EncryptionDecorator).vault;
			}
		}

		val vaultData = VaultData(
			version = "1.0.0",
			encryption = encryptionOptions,
			data = rawVault.entries.associate {
				it.key to it.value
			}
		)

		val objectMapper = ObjectMapper()
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		objectMapper.writeValue(output, vaultData)
	}
}