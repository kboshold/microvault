package net.palatzky.microvault.vault.serialization

import com.fasterxml.jackson.databind.ObjectMapper
import net.palatzky.microvault.encryption.EmptyKey
import net.palatzky.microvault.encryption.pbe.PbeDecryption
import net.palatzky.microvault.service.VaultService
import net.palatzky.microvault.util.createDecryption
import net.palatzky.microvault.util.createEncryption
import net.palatzky.microvault.util.createPBEKey
import net.palatzky.microvault.util.toPair
import net.palatzky.microvault.vault.MicroVault
import net.palatzky.microvault.vault.Vault
import net.palatzky.microvault.vault.serialization.data.VaultData
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.EncodedKeySpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.spec.SecretKeySpec

class VaultFactory() {
	fun fromFile(location: Path, password: String): Vault {
		val content = Files.readString(location, Charsets.UTF_8);
		return this.parse(content, password)

	}

	fun parse(content: String, password: String): Vault {
		val objectMapper = ObjectMapper()

		val vaultData = objectMapper.readValue(content, VaultData::class.java)


		val mode = vaultData.encryption.mode;
		val decoder = Base64.getDecoder();
		val salt = decoder.decode(vaultData.encryption.salt)
		val pbeKey = createPBEKey(password)
		val pbeDecryption = PbeDecryption(pbeKey, salt)

		val encodedReadKey = decoder.decode(
			pbeDecryption.decrypt(
				decoder.decode(vaultData.encryption.key ?: vaultData.encryption.readKey),
				VaultSerializer.AUTHENTICATION_DATA
			)
		)

		val encodedWriteKey = if (vaultData.encryption.key !== null) {
			encodedReadKey
		} else {
			decoder.decode(vaultData.encryption.writeKey)
		}

		val (readKey, writeKey) = when (mode) {
			VaultService.EncryptionMode.plain -> EmptyKey.toPair()
			VaultService.EncryptionMode.asymmetric -> {
				val generator = KeyFactory.getInstance("RSA")

				val privateKeySpec: EncodedKeySpec = PKCS8EncodedKeySpec(encodedReadKey)
				val privateKey: PrivateKey = generator.generatePrivate(privateKeySpec)

				val publicKeySpec: EncodedKeySpec = X509EncodedKeySpec(encodedWriteKey)
				val publicKey: PublicKey = generator.generatePublic(publicKeySpec)
				privateKey to publicKey
			}

			VaultService.EncryptionMode.symmetric -> {
				SecretKeySpec(encodedReadKey, "AES").toPair()
			}
		}

		val decryption = createDecryption(mode, readKey)
		val encryption = createEncryption(mode, writeKey)
		val vault = MicroVault(mode, salt, encryption, decryption);

		vaultData.data.forEach {
			val key = decryption.decrypt(decoder.decode(it.key), VaultSerializer.AUTHENTICATION_DATA);
			val value = decryption.decrypt(decoder.decode(it.value), VaultSerializer.AUTHENTICATION_DATA);
			vault.set(key, value)
		}

		return vault
	}

}