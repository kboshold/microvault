package net.palatzky.microvault.service

import net.palatzky.microvault.encryption.*
import net.palatzky.microvault.encryption.asymmetric.RsaEcbDecryption
import net.palatzky.microvault.encryption.asymmetric.RsaEcbEncryption
import net.palatzky.microvault.encryption.symmetric.AesGcmDecryption
import net.palatzky.microvault.encryption.symmetric.AesGcmEncryption
import net.palatzky.microvault.util.*
import net.palatzky.microvault.vault.DecryptionDecorator
import net.palatzky.microvault.vault.EncryptionDecorator
import net.palatzky.microvault.vault.MicroVault
import net.palatzky.microvault.vault.Vault
import net.palatzky.microvault.vault.option.MicroOptions
import net.palatzky.microvault.vault.option.Options
import net.palatzky.microvault.vault.serialization.VaultFactory
import net.palatzky.microvault.vault.serialization.VaultSerializer
import java.nio.file.Files
import java.nio.file.Path
import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.enterprise.context.Dependent

@Dependent
class VaultService {

	private var vault: Vault? = null
	private var options: Options? = null

	fun publish() {
		println("HELLO TEST")
	}

	fun get(vault: Vault, key: String): String? {
		return vault.get(key)
	}

	fun set(vault: Vault, key: String, value: String) {
		vault.set(key, value)
	}

	fun open(path: Path, password: String) {
		val factory = VaultFactory.fromFile(path, password)

		this.vault = factory.vault
		this.options = factory.options
	}

	fun close() {

	}

	fun list() {

	}

	fun write(vault: Vault, options: Options, path: Path, password: String) {
		val vaultSerializer = VaultSerializer()
		val stream = Files.newOutputStream(path)
		vaultSerializer.serialize(vault, options, stream, password)
	}

	fun create(path: Path, password: String, mode: Options.EncryptionMode) {
		// create write/read key depending on encryption mode.
		val (decryptionKey, encryptionKey) = createReadWriteKey(mode)

		// create decryption/encryption depending on mode
		val decryption = createDecryption(mode, decryptionKey)
		val encryption = createEncryption(mode, encryptionKey)

		val salt = createRandomSalt()
		val vault = EncryptionDecorator(encryption, DecryptionDecorator(decryption, MicroVault()))
		vault.set("exampleKey", "exampleValue")

		val options = MicroOptions(
			salt = salt,
			mode = mode,
			encryptionKey = encryptionKey,
			decryptionKey = decryptionKey
		)

		this.write(vault, options, path, password)
	}

}

// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault create --mode=asymmetric
// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault open
// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault get exampleKey
// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault set exampleKey2 exampleValue2