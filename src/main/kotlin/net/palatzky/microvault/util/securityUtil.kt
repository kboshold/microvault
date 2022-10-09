package net.palatzky.microvault.util

import net.palatzky.microvault.encryption.*
import net.palatzky.microvault.encryption.asymmetric.RsaEcbDecryption
import net.palatzky.microvault.encryption.asymmetric.RsaEcbEncryption
import net.palatzky.microvault.encryption.plain.PlainDecryption
import net.palatzky.microvault.encryption.plain.PlainEncryption
import net.palatzky.microvault.encryption.symmetric.AesGcmDecryption
import net.palatzky.microvault.encryption.symmetric.AesGcmEncryption
import net.palatzky.microvault.service.VaultService
import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

fun createSecretKey(): SecretKey {
	val keyGenerator = KeyGenerator.getInstance("AES")
	keyGenerator.init(256)
	return keyGenerator.generateKey()
}

fun createKeyPair(): KeyPair {
	val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
	keyPairGenerator.initialize(4096, SecureRandom());
	return keyPairGenerator.generateKeyPair()
}

fun createDecryption(mode: VaultService.EncryptionMode, key: Key): Decryption {
	return when (mode) {
		VaultService.EncryptionMode.asymmetric -> RsaEcbDecryption(key)
		VaultService.EncryptionMode.symmetric -> AesGcmDecryption(key)
		VaultService.EncryptionMode.plain -> PlainDecryption()
	}
}

fun createEncryption(mode: VaultService.EncryptionMode, key: Key): Encryption {
	return when (mode) {
		VaultService.EncryptionMode.asymmetric -> RsaEcbEncryption(key)
		VaultService.EncryptionMode.symmetric -> AesGcmEncryption(key)
		VaultService.EncryptionMode.plain -> PlainEncryption()
	}
}

fun createReadWriteKey(mode: VaultService.EncryptionMode): Pair<Key, Key> {
	return when (mode) {
		VaultService.EncryptionMode.asymmetric -> createKeyPair().let { it.private to it.public }
		VaultService.EncryptionMode.symmetric -> createSecretKey().toPair()
		VaultService.EncryptionMode.plain -> EmptyKey.toPair()
	}
}

fun encodeKey(key: Key): String {
	val encoder = Base64.getEncoder()
	return encoder.encode(key.encoded).toString(Charsets.UTF_8)
}

fun createPBEKey(password: String): Key{
	val keySpec = PBEKeySpec(password.toCharArray())
	val keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
	return keyFactory.generateSecret(keySpec)
}

fun createRandomSalt(): ByteArray {
	var random = SecureRandom();
	var bytes = ByteArray(42)
	random.nextBytes(bytes)
	return bytes;
}