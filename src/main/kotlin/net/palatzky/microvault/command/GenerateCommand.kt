package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import kotlin.properties.Delegates

/**
 * Sets a value for a key
 *
 * @property vaultService
 * @constructor Create empty Set command
 */
@CommandLine.Command(name = "generate", aliases = ["gen"],  description = ["Generates an entry in the micro vault"])
class GenerateCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Parameters(index = "0", description = ["Key of the micro vault entry"])
	lateinit var key: String;

	@CommandLine.Option(names = ["--length", "-l"], description = ["Length of the generated password"], defaultValue = "42")
	var length: Int = 0

	@CommandLine.Option(names = ["--upper", "-U"], description = ["Minimum number of upper case characters in the generated password"], defaultValue = "8")
	var upperCase: Int = 0

	@CommandLine.Option(names = ["--lower", "-L"], description = ["Minimum number of lower case characters in the generated password"], defaultValue = "8")
	var lowerCase: Int = 0
	
	@CommandLine.Option(names = ["--numeric", "-N"], description = ["Minimum number of numeric characters in the generated password"], defaultValue = "4")
	var numeric: Int = 0

	@CommandLine.Option(names = ["--symbol", "-S"], description = ["Minimum number of symbol characters in the generated password"], defaultValue = "4")
	var symbol: Int = 0

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		// open vault & get value of passed key
		vaultService.open(entryCommand.file, entryCommand.key)
		vaultService.generate(key, length, upperCase, lowerCase, numeric, symbol)
		vaultService.write(entryCommand.file)
	}
}