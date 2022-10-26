package net.palatzky.microvault.command.converter

import net.palatzky.microvault.util.createPBEKey
import net.palatzky.microvault.vault.option.Options
import picocli.CommandLine
import java.security.Key

class EncryptionModeConverter : CommandLine.ITypeConverter<Options.EncryptionMode?> {
	override fun convert(value: String?): Options.EncryptionMode? {
		return value?.let {
			Options.EncryptionMode.valueOf(value.uppercase())
		}
	}
}