package net.palatzky.microvault.vault.serialization.data

data class EncryptionOptions(
	var secretKey: String?,
	var privateKey: String?,
	var publicKey: String?,
)
