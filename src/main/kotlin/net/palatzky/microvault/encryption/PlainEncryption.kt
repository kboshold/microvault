package net.palatzky.microvault.encryption

class PlainEncryption : Encryption {
	override fun encrypt(content: String, authenticationData: String?): ByteArray {
		return content.toByteArray(Charsets.UTF_8)
	}
}