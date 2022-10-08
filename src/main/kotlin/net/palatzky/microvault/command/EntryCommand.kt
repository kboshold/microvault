package net.palatzky.microvault.command

import io.quarkus.picocli.runtime.annotations.TopCommand
import picocli.CommandLine
import java.nio.file.Path

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = [
	CreateCommand::class,
	OpenCommand::class,
	GetCommand::class,
	SetCommand::class,
	PublishCommand::class,
	ListCommand::class,
	CloseCommand::class
])
class EntryCommand  {
	@CommandLine.Option(names = ["-p", "--password"], description = ["Password to use for the micro vault"], interactive = true)
	lateinit var password: String

	@CommandLine.Option(names = ["-f", "--file"], description = ["Path of the micro vault"], interactive = true)
	lateinit var file: Path

	@CommandLine.Option(names = ["-s", "--session"], description = ["Session for your current micro vault"])
	lateinit var session: String
}