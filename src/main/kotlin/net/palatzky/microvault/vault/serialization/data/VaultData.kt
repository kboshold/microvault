package net.palatzky.microvault.vault.serialization.data

import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * Vault data
 *
 * @property version
 * @property encryption
 * @property data
 * @constructor Create empty Vault data
 */
@RegisterForReflection
data class VaultData(
	val version: String,
	val encryption: EncryptionOptions,
	val data: Map<String, String>
) {
	constructor() : this("0.0.0", EncryptionOptions(), mapOf())
}
