package net.palatzky.microvault.service

import net.palatzky.microvault.encryption.*
import net.palatzky.microvault.encryption.asymmetric.RsaEcbDecryption
import net.palatzky.microvault.encryption.asymmetric.RsaEcbEncryption
import net.palatzky.microvault.encryption.symmetric.AesGcmDecryption
import net.palatzky.microvault.encryption.symmetric.AesGcmEncryption
import net.palatzky.microvault.util.toPair
import net.palatzky.microvault.vault.MicroVault
import net.palatzky.microvault.vault.serialization.MicroVaultSerializer
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

	fun get(key: String): String {
		return "";
	}

	fun set(key: String, value: String) {

	}

	fun open() {

	}

	fun close() {

	}

	fun list() {

	}



	fun create(path: Path, password: String, mode: EncryptionMode) {
		// create write/read key depending on encryption mode.
		val (readKey, writeKey) = createReadWriteKey(mode)

		// encode write/read key using base64
		val encodedWriteKey = encodeKey(writeKey)
		val encodedReadKey = encodeKey(readKey)

		// create decryption/encryption depending on mode
		val decryption = createDecryption(mode, writeKey)
		val encryption = createEncryption(mode, readKey)

		val vault = MicroVault()
		var vaultSerializer = MicroVaultSerializer(encryption)


//		var keyPair = AsymmetricCryptor.createKeyPair();
//
//////
////		var outStream = ByteArrayOutputStream()
////		val out = ObjectOutputStream(outStream)
////		out.writeObject(keyPair)
////		out.flush();
////		out.close()
//////
//		var encoder =  Base64.getEncoder();
//		println("PRIVATE1 " + keyPair.private);
//		println("PUBLIC1 " +  keyPair.public);
////
//		var encodedPrivateKey =  encoder.encode(keyPair.private.encoded).toString(Charsets.UTF_8);
//		var encodedPublicKey =   encoder.encode(keyPair.public.encoded).toString(Charsets.UTF_8);
//		println("PRIVATE1 " + encodedPrivateKey);
//		println("PUBLIC1 " + encodedPublicKey);
//
//		var encryptor = AsymmetricCryptor(keyPair);
////
//		var data = encryptor.encrypt("Hallo Welt");
//		println("ENCRYPTED " + data);
//		println("DECRYPTED " + encryptor.decrypt(data));
//		println("===========================");
//
//		var x = KeyPairGenerator.getInstance("RSA")
//
//		var decoder =  Base64.getDecoder();
//		val privateKeySpec = PKCS8EncodedKeySpec(decoder.decode(encodedPrivateKey))
//		val publicKeySpec = X509EncodedKeySpec(decoder.decode(encodedPublicKey))
//		val kf = KeyFactory.getInstance("RSA")
//		var privateKey = kf.generatePrivate(privateKeySpec)
//		var publicKey = kf.generatePublic(publicKeySpec);
//		var encryptor2 = AsymmetricCryptor(KeyPair(publicKey, privateKey))
//		println("ENCRYPTED2 " + data);
//		println("DECRYPTED2 " + encryptor2.decrypt(data));
//		var encryptor = A();
//
//		var value = encryptor.encrypt("Hallo das ist ein Test");
//		println("ENCRYPTED1 " + value);
//		println("DECRYPTED1 " + encryptor.decrypt(value));
//		var data = outStream.toByteArray().toString(Charsets.UTF_8);
//
//		var inStream = ByteArrayInputStream(data.toByteArray(Charsets.UTF_8));
//		val inObjectStream = ObjectInputStream(inStream)
//
//		val keyPair2 = inObjectStream.readObject() as KeyPair
//
//		println("PRIVATE2 " + keyPair2.private);
//		println("PUBLIC2 " + keyPair2.public);

//		println("CREATE KEYS " + );
//
//		var data = this.serializeKeyPair(keyPair, password);
//
//		println("DATA" + data);
//
//		var keyPair2 = this.loadKeyPair(data, password);
//
//		println("CREATE KEYS2 " + keyPair2.private.toString() + " - " + keyPair2.public.toString());

	}

	private fun createSecretKey(): SecretKey {
		val keyGenerator = KeyGenerator.getInstance("AES")
		keyGenerator.init(256)
		return keyGenerator.generateKey()
	}

	private fun createKeyPair(): KeyPair {
		val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
		keyPairGenerator.initialize(4096, SecureRandom());
		return keyPairGenerator.generateKeyPair()
	}

	private fun createDecryption(mode: EncryptionMode, key: Key): Decryption {
		return when(mode) {
			EncryptionMode.asymmetric -> RsaEcbDecryption(key)
			EncryptionMode.symmetric -> AesGcmDecryption(key)
			EncryptionMode.plain -> PlainDecryption()
		}
	}

	private fun createEncryption(mode: EncryptionMode, key: Key): Encryption {
		return when(mode) {
			EncryptionMode.asymmetric -> RsaEcbEncryption(key)
			EncryptionMode.symmetric -> AesGcmEncryption(key)
			EncryptionMode.plain -> PlainEncryption()
		}
	}

	private fun createReadWriteKey(mode: EncryptionMode): Pair<Key, Key> {
		return when(mode) {
			EncryptionMode.asymmetric -> this.createKeyPair().let { it.private to it.public}
			EncryptionMode.symmetric -> this.createSecretKey().toPair()
			EncryptionMode.plain -> EmptyKey.toPair()
		}
	}

	private fun encodeKey(key: Key): String {
		val encoder =  Base64.getEncoder()
		return encoder.encode(key.encoded).toString(Charsets.UTF_8)
	}
}