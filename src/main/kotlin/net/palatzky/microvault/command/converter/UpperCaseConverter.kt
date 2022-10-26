package net.palatzky.microvault.command.converter

import net.palatzky.microvault.util.createPBEKey
import picocli.CommandLine.ITypeConverter
import java.security.Key

class UpperCaseConverter : ITypeConverter<String?> {
	override fun convert(value: String?): String? {
		return value?.uppercase()
	}
}