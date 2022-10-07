package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

@CommandLine.Command(name = "create", description = ["Creates a new micro vault"])
class CreateCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Option(names = ["-a", "--asymmetric"], description = ["Enables asymmetric encryption"], defaultValue = false.toString())
	var asymmetric: Boolean = false

	@CommandLine.Option(names = ["-s", "--symmetric"], description = ["Enables symmetric encryption"], defaultValue = false.toString())
	var symmetric: Boolean = false

	@CommandLine.Option(names = ["-p", "--password"], description = ["Password to use for the micro vault"])
	var password: String? = null

	@CommandLine.Option(names = ["--password-stdin"], description = ["Reads the micro vault password from standard input"])
	var passwordStdIn: Boolean = false

	@CommandLine.Parameters(index = "0", description = ["Path of the micro vault"])
	lateinit var vaultPath: File;

	override fun run() {
		this.vaultService.create("example123")
	}
}