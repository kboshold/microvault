package net.palatzky.microvault.session

import java.io.File

/**
 * Session
 *
 * @constructor Create empty Session
 */
interface Session {
	val vaultPath: File;
	val vaultKey: String;
}