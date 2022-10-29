package net.palatzky.microvault.encryption

import java.security.SecureRandom

class PasswordGenerator {
	companion object {
		fun charArrayOfRange(start: Char, end: Char): CharArray {
			return arrayOfNulls<Char>(end - start + 1).mapIndexed { index, _ ->
				start + index
			}.toCharArray()
		}

		private val ALPHA_UPPER_CHARACTERS = charArrayOfRange('A', 'Z')
		private val ALPHA_LOWER_CHARACTERS = charArrayOfRange('a', 'z')
		private val NUMERIC_CHARACTERS = charArrayOfRange('0', '9')
		private val SYMBOL_CHARACTERS = charArrayOf('@', '#', '$', '&', '*', '|', ';', ':', '?', '!')

		private val CHARACTERS = ALPHA_UPPER_CHARACTERS + ALPHA_LOWER_CHARACTERS + NUMERIC_CHARACTERS + SYMBOL_CHARACTERS
	}

	var length: Int = 42
	var minUpperCase = 8
	var minLowerCase = 8
	var minNumeric = 4
	var minSymbol = 4

	fun generate(): String {
		val upperCase =
			arrayOfNulls<Char>(this.minUpperCase).map { this.getRandomCharacter(ALPHA_UPPER_CHARACTERS) }
		val lowerCase =
			arrayOfNulls<Char>(this.minLowerCase).map { this.getRandomCharacter(ALPHA_LOWER_CHARACTERS) }
		val numeric = arrayOfNulls<Char>(this.minNumeric).map { this.getRandomCharacter(NUMERIC_CHARACTERS) }
		val symbol = arrayOfNulls<Char>(this.minSymbol).map { this.getRandomCharacter(SYMBOL_CHARACTERS) }

		val remainingLength =
			length.coerceAtMost((minUpperCase + minLowerCase + minNumeric + minSymbol))
		val other = arrayOfNulls<Char>(length - remainingLength).map { this.getRandomCharacter(CHARACTERS) }

		val combined = upperCase + lowerCase + numeric + symbol + other
		val secureRandom = SecureRandom.getInstanceStrong()
		return combined.shuffled(secureRandom).joinToString("")
	}

	private fun getRandomCharacter(characters: CharArray): Char {
		val secureRandom = SecureRandom.getInstanceStrong()
		val random = secureRandom.nextInt(characters.size)
		return characters[random]
	}
}
