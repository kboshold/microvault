package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

@CommandLine.Command(name = "close", aliases = ["C"],  description = ["Closes the vault"])
class CloseCommand(
	private val vaultService: VaultService
) : Runnable {

	override fun run() {
	}
}