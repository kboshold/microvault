package net.palatzky.microvault.vault

import java.nio.file.Path

interface Vault {

	val entries: Set<VaultEntry>;

	fun get(key: String): String?

	fun set(key: String, value: String)
}