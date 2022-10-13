package net.palatzky.microvault.vault

/**
 * Entry of a vault containing a key and value
 *
 * @property key
 * @property value
 * @constructor Create empty Vault entry
 */
data class VaultEntry(
	val key: String,
	val value: String
)
