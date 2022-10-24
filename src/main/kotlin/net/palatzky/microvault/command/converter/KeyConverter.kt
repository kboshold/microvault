package net.palatzky.microvault.command.converter

import net.palatzky.microvault.util.createPBEKey
import picocli.CommandLine.ITypeConverter
import java.security.Key

class KeyConverter : ITypeConverter<Key?> {
	override fun convert(value: String?): Key? {
		return if (value == null) {
			null
		} else {
			createPBEKey(value)
		}
	}
}