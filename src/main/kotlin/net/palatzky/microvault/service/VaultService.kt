package net.palatzky.microvault.service

import net.palatzky.microvault.encryption.*
import net.palatzky.microvault.encryption.asymmetric.RsaEcbDecryption
import net.palatzky.microvault.encryption.asymmetric.RsaEcbEncryption
import net.palatzky.microvault.encryption.symmetric.AesGcmDecryption
import net.palatzky.microvault.encryption.symmetric.AesGcmEncryption
import net.palatzky.microvault.util.*
import net.palatzky.microvault.vault.MicroVault
import net.palatzky.microvault.vault.Vault
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

	enum class EncryptionMode {
		plain, asymmetric, symmetric
	}

	fun publish() {
		println("HELLO TEST")
	}

	fun get(vault: Vault, key: String): String? {
		return vault.get(key)
	}

	fun set(vault: Vault, key: String, value: String) {
		vault.set(key, value)
	}

	fun open(path: Path, password: String): Vault {
		val factory = VaultFactory()
		return factory.fromFile(path, password)
	}

	fun close() {

	}

	fun list() {

	}

	fun write(vault: Vault, path: Path, password: String) {
		val vaultSerializer = VaultSerializer()
		val stream = Files.newOutputStream(path)
		vaultSerializer.serialize(vault, stream, password)
	}

	fun create(path: Path, password: String, mode: EncryptionMode) {
		// create write/read key depending on encryption mode.
		val (readKey, writeKey) = createReadWriteKey(mode)

		// create decryption/encryption depending on mode
		val decryption = createDecryption(mode, readKey)
		val encryption = createEncryption(mode, writeKey)

		val salt = createRandomSalt()
		val vault = MicroVault(mode, salt, encryption, decryption)
		vault.set("exampleKey", "exampleValue")

		this.write(vault, path, password)
	}

}

// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault create --mode=asymmetric
// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault open
// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault get exampleKey
// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault set exampleKey2 exampleValue2