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

	fun get(key: String): String {
		return "";
	}

	fun set(key: String, value: String) {

	}

	fun open(path: Path, password: String): Vault {
		val factory = VaultFactory()

		val vault = factory.fromFile(path, password)

		println(vault.entries)
		println("KEY " + vault.get("exampleKey"));

		return vault;
	}

	fun close() {

	}

	fun list() {

	}



	fun create(path: Path, password: String, mode: EncryptionMode) {
		// create write/read key depending on encryption mode.
		val (readKey, writeKey) = createReadWriteKey(mode)

		// create decryption/encryption depending on mode
		val decryption = createDecryption(mode, writeKey)
		val encryption = createEncryption(mode, readKey)

		val salt = createRandomSalt()
		val vault = MicroVault(mode, salt, encryption, decryption)
		vault.set("exampleKey", "exampleValue")
		val vaultSerializer = VaultSerializer()

		val stream = Files.newOutputStream(path)
		vaultSerializer.serialize(vault, stream, password)
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

}

// --password=test --file=C:\Users\kevin\workspace\microsecrets\test.vault create --mode=asymmetric