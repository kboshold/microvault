package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine

@CommandLine.Command(name = "set", aliases = ["s"],  description = ["Sets an entry in the micro vault"])
class SetCommand(
	private val vaultService: VaultService
) : Runnable {

	@CommandLine.Parameters(index = "0", description = ["Key of the micro vault entry"])
	lateinit var key: String;

	@CommandLine.Parameters(index = "1", description = ["Value of the micro vault entry"])
	lateinit var value: String;

	override fun run() {

	}
}