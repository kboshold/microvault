package net.palatzky.microvault.vault.serialization

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.encryption.Encryption

abstract class VaultSerializer (
	protected val encryption: Encryption
){
}