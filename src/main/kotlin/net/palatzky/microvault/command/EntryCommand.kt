package net.palatzky.microvault.command

import io.quarkus.picocli.runtime.annotations.TopCommand
import net.palatzky.microvault.command.converter.KeyConverter
import picocli.CommandLine
import java.nio.file.Path
import java.security.Key

/**
 * Entry command
 *
 * @constructor Create empty Entry command
 */
@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = [
	CreateCommand::class,
	OpenCommand::class,
	GetCommand::class,
	SetCommand::class,
	PublishCommand::class,
	ListCommand::class,
	CloseCommand::class,
	GenerateCommand::class
])
class EntryCommand  {
	@CommandLine.Option(names = ["-p", "--password"], description = ["Password to use for the micro vault"], interactive = true, converter = [KeyConverter::class])
	var key: Key? = null

	@CommandLine.Option(names = ["-f", "--file"], description = ["Path of the micro vault"], interactive = true)
	lateinit var file: Path

	@CommandLine.Option(names = ["-s", "--session"], description = ["Session for your current micro vault"])
	lateinit var session: String
}