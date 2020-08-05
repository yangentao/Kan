package dev.entao.kan.appbase

object IdGen {
	private var id = 0

	@Synchronized
	fun gen(): Int {
		return ++id
	}
}
