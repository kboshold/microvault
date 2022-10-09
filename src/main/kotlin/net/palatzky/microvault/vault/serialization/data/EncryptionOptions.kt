package net.palatzky.microvault.vault.serialization.data

import com.fasterxml.jackson.annotation.JsonInclude
import net.palatzky.microvault.service.VaultService

data class EncryptionOptions(
	var mode: VaultService.EncryptionMode,
	var salt: String,
	var readKey: String? = null,
	var writeKey: String? = null,
	var key: String? = null
)
