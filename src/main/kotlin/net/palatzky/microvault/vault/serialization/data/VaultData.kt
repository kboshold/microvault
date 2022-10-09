package net.palatzky.microvault.vault.serialization.data

data class VaultData(
	val version: String,
	val encryption: EncryptionOptions,
	val data: Map<String, String>
)
