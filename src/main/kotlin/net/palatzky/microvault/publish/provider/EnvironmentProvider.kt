package net.palatzky.microvault.publish.provider

import net.palatzky.microvault.vault.Vault
import java.lang.reflect.Field
import java.util.*

class EnvironmentProvider: Provider {
	override fun export(vault: Vault, options: ExportOptions) {
		options.mapping.forEach {(envKey, vaultKey) ->
			println("export ${envKey}=${vault.get(vaultKey) ?: ""}")
		}
		System.out.flush();
	}
}