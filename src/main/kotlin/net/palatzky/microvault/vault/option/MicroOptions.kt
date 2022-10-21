package net.palatzky.microvault.vault.option

import java.security.Key

class MicroOptions(
	override val salt: ByteArray,
	override val mode: Options.EncryptionMode,
	override val encryptionKey: Key,
	override val decryptionKey: Key
) : Options