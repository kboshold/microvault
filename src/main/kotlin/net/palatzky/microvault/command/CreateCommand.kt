package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import picocli.CommandLine.MissingParameterException
import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Path
import java.util.*

@CommandLine.Command(name = "create", description = ["Creates a new micro vault"], aliases = ["c"] )
class CreateCommand(
	private val commandLine: CommandLine,
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Option(names = ["-s", "--symmetric"], description = ["Enables symmetric encryption"], defaultValue = false.toString())
	var symmetric: Boolean = false

	@CommandLine.Option(names = ["-p", "--password"], description = ["Password to use for the micro vault"])
	var password: String? = null

	@CommandLine.Option(names = ["--password-stdin"], description = ["Reads the micro vault password from standard input"])
	var passwordStdIn: Boolean = false

	@CommandLine.Parameters(index = "0", description = ["Path of the micro vault"])
	lateinit var vaultPath: Path;

	override fun run() {
		var password = this.password;
		if (this.passwordStdIn) {
			val scanner = Scanner(System.`in`)
			password = scanner.nextLine()
		}
		if (password === null) {
			throw MissingParameterException (commandLine, commandLine.commandSpec.findOption("password"), "\"--password\" or \"--password-stdin\" must be set.\n");
		}
		this.vaultService.create(this.vaultPath, password, this.symmetric);
	}
}