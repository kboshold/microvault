package net.palatzky.microvault.vault.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import net.palatzky.microvault.encryption.EmptyKey
import net.palatzky.microvault.encryption.PassThroughKey
import net.palatzky.microvault.encryption.pbe.PbeDecryption
import net.palatzky.microvault.service.VaultService
import net.palatzky.microvault.util.createDecryption
import net.palatzky.microvault.util.createEncryption
import net.palatzky.microvault.util.createPBEKey
import net.palatzky.microvault.util.toPair
import net.palatzky.microvault.vault.DecryptionDecorator
import net.palatzky.microvault.vault.EncryptionDecorator
import net.palatzky.microvault.vault.MicroVault
import net.palatzky.microvault.vault.Vault
import net.palatzky.microvault.vault.option.MicroOptions
import net.palatzky.microvault.vault.option.Options
import net.palatzky.microvault.vault.serialization.data.VaultData
import java.nio.file.Files
import java.nio.file.Path
import java.security.Key
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.spec.SecretKeySpec

/**
 * Create a vault from the given file of content
 *
 * @constructor Create empty Vault factory
 */
class VaultFactory() {

	companion object {
		/**
		 * Reads the file and parses its content to return the vault.
		 *
		 * @param location
		 * @param password
		 * @return
		 */
		fun fromFile(location: Path, pbeKey: Key?): VaultFactory {
			val content = Files.readString(location, Charsets.UTF_8)
			val vaultFactory = VaultFactory();
			vaultFactory.parse(content, pbeKey)
			return vaultFactory
		}
	}

	lateinit var options: Options
		private set

	lateinit var vault: Vault
		private set


	private fun parseVaultData(content: String): VaultData {
		val objectMapper = ObjectMapper()
		return objectMapper.readValue(content, VaultData::class.java)
	}

	/**
	 * Parses the given content by decrypting the data with the passed password and creating a vault object out of it.
	 *
	 * @param content
	 * @param password
	 * @return
	 */
	fun parse(content: String, pbeKey: Key?) {
		val vaultData = parseVaultData(content);

		val mode = vaultData.encryption.mode;
		val decoder = Base64.getDecoder();

		val salt = decoder.decode(vaultData.encryption.salt)

		val decodedDecryptionKey = decoder.decode(vaultData.encryption.key ?: vaultData.encryption.decryptionKey)
		val decodedEncryptionKey = decoder.decode(vaultData.encryption.key ?: vaultData.encryption.encryptionKey)

		var decryptionKey: Key? = null;
		if (pbeKey != null) {
			val pbeDecryption = PbeDecryption(pbeKey, salt)

			val encodedDecryptionKey = decoder.decode(
				pbeDecryption.decrypt(
					decodedDecryptionKey,
					VaultSerializer.AUTHENTICATION_DATA
				)
			)

			decryptionKey = when (mode) {
				Options.EncryptionMode.plain -> EmptyKey
				Options.EncryptionMode.symmetric -> SecretKeySpec(encodedDecryptionKey, "AES")
				Options.EncryptionMode.asymmetric -> {
					val generator = KeyFactory.getInstance("RSA")
					val privateKeySpec: EncodedKeySpec = PKCS8EncodedKeySpec(encodedDecryptionKey)
					generator.generatePrivate(privateKeySpec)
				}
			}
		}

		val encryptionKey = when (mode) {
			Options.EncryptionMode.plain -> EmptyKey
			Options.EncryptionMode.symmetric -> decryptionKey
			Options.EncryptionMode.asymmetric -> {
				val generator = KeyFactory.getInstance("RSA")
				val publicKeySpec: EncodedKeySpec = X509EncodedKeySpec(decodedEncryptionKey)
				generator.generatePublic(publicKeySpec)
			}
		}

		var vault: Vault = MicroVault(vaultData.data);

		if (encryptionKey != null) {
			val encryption = createEncryption(mode, encryptionKey)
			vault = EncryptionDecorator(encryption, vault);
		}

		if (decryptionKey != null) {
			val decryption = createDecryption(mode, decryptionKey)
			vault = DecryptionDecorator(decryption, vault);
		}

		this.vault = vault;
		this.options = MicroOptions(
			salt = salt,
			mode = mode,
			encryptionKey = encryptionKey ?: PassThroughKey(decodedEncryptionKey),
			decryptionKey = decryptionKey ?: PassThroughKey(decodedDecryptionKey),
			pbeKey = pbeKey
		)
	}

}