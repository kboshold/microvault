package net.palatzky.microvault.vault.serialization.data

import io.quarkus.runtime.annotations.RegisterForReflection
import net.palatzky.microvault.vault.option.Options

/**
 * Encryption options
 *
 * @property mode
 * @property salt
 * @property decryptionKey
 * @property encryptionKey
 * @property key
 * @constructor Create empty Encryption options
 */
@RegisterForReflection
data class OptionsData(
	var mode: Options.EncryptionMode,
	var salt: String,
	var decryptionKey: String? = null,
	var encryptionKey: String? = null,
	var key: String? = null
) {
	constructor(): this(Options.EncryptionMode.PLAIN, "")
}
