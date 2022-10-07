package net.palatzky.microvault.session

import java.io.File

interface Session {

	val vaultPath: File;

	val vaultKey: String;
}