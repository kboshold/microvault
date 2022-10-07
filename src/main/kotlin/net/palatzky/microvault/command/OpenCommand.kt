package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

@CommandLine.Command(name = "open", description = ["Opens the micro vault"])
class OpenCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Option(names = ["-t", "--temporary"], description = ["Who will we greet?"], defaultValue = false.toString())
	private var temporary: Boolean = false

	@CommandLine.Parameters(index = "0", description = ["Path of the micro vault"])
	lateinit var vaultPath: File;

	override fun run() {
	}
}