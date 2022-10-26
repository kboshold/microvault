package net.palatzky.microvault.command.converter

import picocli.CommandLine.ITypeConverter

class UpperCaseConverter : ITypeConverter<String?> {
	override fun convert(value: String?): String? {
		return value?.uppercase()
	}
}