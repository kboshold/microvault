package net.palatzky.microvault.service

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
		val (writeKey, readKey) = when(mode) {
			EncryptionMode.asymmetric -> {
				val keyPair = this.createKeyPair()
				keyPair.public to keyPair.private
			}
			EncryptionMode.symmetric -> {
				val key = this.createSecretKey()
				key to key
			}
			EncryptionMode.plain -> {
				null to null
			}
		}

		val encoder =  Base64.getEncoder();
		val encodedWriteKey = encoder.encode(writeKey?.encoded ?: ByteArray(0)).toString(Charsets.UTF_8);
		val encodedReadKey = encoder.encode(readKey?.encoded ?: ByteArray(0)).toString(Charsets.UTF_8);


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

//	private fun loadKeyPair(keyStoreData: String, password: String): KeyPair {
//		val stream: InputStream = ByteArrayInputStream(keyStoreData.toByteArray(Charsets.UTF_8))
//		val keystore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
//		keystore.load(stream, password.toCharArray())
//
//		val privateKey = keystore.getKey("private", password.toCharArray()) as PrivateKey
//		val publicKey = keystore.getKey("public", password.toCharArray()) as PublicKey
//
//		return KeyPair(publicKey, privateKey);
//	}
//
//	private fun serializeKeyPair(keyPair: KeyPair, password: String): String {
//		val keystore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
//		keystore.load(null, null);
//
//		keystore.setKeyEntry("private", keyPair.private, password.toCharArray(), arrayOf())
//		keystore.setKeyEntry("public", keyPair.public, password.toCharArray(), arrayOf())
//
//		val stream = ByteArrayOutputStream();
//		keystore.store(stream, password.toCharArray());
//		return stream.toByteArray().toString(Charsets.UTF_8)
//	}
}