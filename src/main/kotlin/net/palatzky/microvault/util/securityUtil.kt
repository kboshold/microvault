package net.palatzky.microvault.util

import net.palatzky.microvault.encryption.*
import net.palatzky.microvault.encryption.asymmetric.RsaEcbDecryption
import net.palatzky.microvault.encryption.asymmetric.RsaEcbEncryption
import net.palatzky.microvault.encryption.plain.PlainDecryption
import net.palatzky.microvault.encryption.plain.PlainEncryption
import net.palatzky.microvault.encryption.symmetric.AesGcmDecryption
import net.palatzky.microvault.encryption.symmetric.AesGcmEncryption
import net.palatzky.microvault.vault.option.Options
import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Create secret key
 *
 * @return
 */
fun createSecretKey(): SecretKey {
	val keyGenerator = KeyGenerator.getInstance("AES")
	keyGenerator.init(256)
	return keyGenerator.generateKey()
}

/**
 * Create key pair
 *
 * @return
 */
fun createKeyPair(): KeyPair {
	val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
	keyPairGenerator.initialize(4096, SecureRandom());
	return keyPairGenerator.generateKeyPair()
}

/**
 * Create decryption
 *
 * @param mode
 * @param key
 * @return
 */
fun createDecryption(mode: Options.EncryptionMode, key: Key): Decryption {
	return when (mode) {
		Options.EncryptionMode.asymmetric -> RsaEcbDecryption(key)
		Options.EncryptionMode.symmetric -> AesGcmDecryption(key)
		Options.EncryptionMode.plain -> PlainDecryption()
	}
}

/**
 * Create encryption
 *
 * @param mode
 * @param key
 * @return
 */
fun createEncryption(mode: Options.EncryptionMode, key: Key): Encryption {
	return when (mode) {
		Options.EncryptionMode.asymmetric -> RsaEcbEncryption(key)
		Options.EncryptionMode.symmetric -> AesGcmEncryption(key)
		Options.EncryptionMode.plain -> PlainEncryption()
	}
}

/**
 * Create read and write key as pair
 *
 * @param mode
 * @return
 */
fun createReadWriteKey(mode: Options.EncryptionMode): Pair<Key, Key> {
	return when (mode) {
		Options.EncryptionMode.asymmetric -> createKeyPair().let { it.private to it.public }
		Options.EncryptionMode.symmetric -> createSecretKey().toPair()
		Options.EncryptionMode.plain -> EmptyKey.toPair()
	}
}

/**
 * Encode key
 *
 * @param key
 * @return
 */
fun encodeKey(key: Key): String {
	val encoder = Base64.getEncoder()
	return encoder.encode(key.encoded).toString(Charsets.UTF_8)
}

/**
 * Create pbe key
 *
 * @param password
 * @return
 */
fun createPBEKey(password: String): Key{
	val keySpec = PBEKeySpec(password.toCharArray())
	val keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
	return keyFactory.generateSecret(keySpec)
}

/**
 * Create random salt
 *
 * @return
 */
fun createRandomSalt(): ByteArray {
	var random = SecureRandom();
	var bytes = ByteArray(42)
	random.nextBytes(bytes)
	return bytes;
}