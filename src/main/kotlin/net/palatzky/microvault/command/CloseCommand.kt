package net.palatzky.microvault.command

import net.palatzky.microvault.service.VaultService
import picocli.CommandLine
import java.io.File

/**
 * Closes an opened vault
 *
 * @property vaultService
 * @constructor Create empty Close command
 */
@CommandLine.Command(name = "close", aliases = ["C"],  description = ["Closes the vault"])
class CloseCommand(
	private val vaultService: VaultService
) : Runnable {

	override fun run() {
		vaultService.close()
	}
}