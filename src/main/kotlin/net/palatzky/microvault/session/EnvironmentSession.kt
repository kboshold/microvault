package net.palatzky.microvault.session

import java.io.File

/**
 * Environment session
 *
 * @constructor Create empty Environment session
 */
class EnvironmentSession : Session {

	override val vaultPath: File
		get() = TODO("Not yet implemented")
	override val vaultKey: String
		get() = TODO("Not yet implemented")

}