package net.palatzky.microvault.vault.serialization

import net.palatzky.microvault.encryption.Decryption
import net.palatzky.microvault.vault.Vault
import java.nio.file.Files
import java.nio.file.Path

abstract class VaultFactory (
	protected val decryption: Decryption? = null
){

	fun fromFile(location: Path): Vault {
		val content = Files.readString(location, Charsets.UTF_8);
		return this.parse(content);
	}

	protected abstract fun parse(content: String): Vault;

}