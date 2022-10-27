package net.palatzky.microvault.encryption

import java.security.SecureRandom
import kotlin.random.asKotlinRandom

class PasswordGenerator {
	companion object {
		fun charArrayOfRange(start: Char, end: Char): CharArray {
			return arrayOfNulls<Char>(end - start + 1).mapIndexed { index, value ->
				start + index
			}.toCharArray()
		}

		private val ALPHA_UPPER_CHARACTERS = charArrayOfRange('A', 'Z')
		private val ALPHA_LOWER_CHARACTERS = charArrayOfRange('a', 'z')
		private val NUMERIC_CHARACTERS = charArrayOfRange('0', '9')
		private val SPECIAL_CHARACTERS = charArrayOf('@', '#', '$', '&', '*', '|', ';', ':', '?', '!')

		private val CHARACTERS = ALPHA_UPPER_CHARACTERS + ALPHA_LOWER_CHARACTERS + NUMERIC_CHARACTERS + SPECIAL_CHARACTERS
	}

	private var length: Int = 42
	private val minUpperCase = 8
	private val minLowerCase = 8
	private val minNumeric = 4
	private val minSpecial = 4

	fun generate(): String {
		val upperCase =
			arrayOfNulls<Char>(this.minUpperCase).map { this.getRandomCharacter(ALPHA_UPPER_CHARACTERS) }
		val lowerCase =
			arrayOfNulls<Char>(this.minLowerCase).map { this.getRandomCharacter(ALPHA_LOWER_CHARACTERS) }
		val numeric = arrayOfNulls<Char>(this.minNumeric).map { this.getRandomCharacter(NUMERIC_CHARACTERS) }
		val special = arrayOfNulls<Char>(this.minSpecial).map { this.getRandomCharacter(SPECIAL_CHARACTERS) }

		val remainingLength =
			length.coerceAtMost((minUpperCase + minLowerCase + minNumeric + minSpecial))
		val other = arrayOfNulls<Char>(length - remainingLength).map { this.getRandomCharacter(CHARACTERS) }

		val combined = upperCase + lowerCase + numeric + special + other
		val secureRandom = SecureRandom.getInstanceStrong()
		return combined.shuffled(secureRandom).joinToString("")
	}

	private fun getRandomCharacter(characters: CharArray): Char {
		val secureRandom = SecureRandom.getInstanceStrong()
		val random = secureRandom.nextInt(characters.size)
		return characters[random]
	}
}