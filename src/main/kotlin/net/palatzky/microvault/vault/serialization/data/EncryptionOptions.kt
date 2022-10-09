package net.palatzky.microvault.vault.serialization.data

data class EncryptionOptions(
	var readKey: String?,
	var writeKey: String?,
	var key: String?
)
