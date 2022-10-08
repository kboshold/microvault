package net.palatzky.microvault.command

import io.quarkus.runtime.annotations.CommandLineArguments
import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import picocli.CommandLine.MissingParameterException
import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Path
import java.util.*

@CommandLine.Command(name = "create", aliases = ["c"], description = ["Creates a new micro vault"] )
class CreateCommand(
	private val vaultService: VaultService
) : Runnable {
	enum class Mode {
		plain, asymmetric, symmetric
	}

	@CommandLine.Option(names = ["-m", "--mode"], description = ["Enables symmetric encryption"], defaultValue = false.toString())
	var mode: Mode = Mode.asymmetric

	@CommandLine.Parameters(index = "0", description = ["Path of the micro vault"])
	lateinit var vaultPath: Path;

	@CommandLine.ParentCommand
	lateinit var entryCommand: EntryCommand;

	override fun run() {
		println("GOT " + this.entryCommand.password)
	}
}