package net.palatzky.microvault.encryption

class PlainDecryption : Decryption {
	override fun decrypt(content: ByteArray, authenticationData: String?): String {
		return content.toString(Charsets.UTF_8)
	}
}