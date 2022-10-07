package net.palatzky.microvault.command

import io.quarkus.picocli.runtime.annotations.TopCommand
import picocli.CommandLine

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
class EntryCommand