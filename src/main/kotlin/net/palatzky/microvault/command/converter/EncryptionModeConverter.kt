package net.palatzky.microvault.command.converter

import net.palatzky.microvault.vault.option.Options
import picocli.CommandLine

class EncryptionModeConverter : CommandLine.ITypeConverter<Options.EncryptionMode?> {
	override fun convert(value: String?): Options.EncryptionMode? {
		return value?.let {
			Options.EncryptionMode.valueOf(value.uppercase())
		}
	}
}