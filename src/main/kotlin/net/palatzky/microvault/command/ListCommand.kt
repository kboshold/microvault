package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

@CommandLine.Command(name = "list",  aliases = ["l"], description = ["Lists the entries in the micro vault"])
class ListCommand(
	private val vaultService: VaultService
): Runnable {
	override fun run() {
	}
}