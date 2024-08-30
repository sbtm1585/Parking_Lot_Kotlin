package parking

class ParkingLot {
    private val spots = emptyList<Spot>().toMutableList()

    fun create(size: Int?) {
        spots.apply { clear(); for (i in 1..size!!) add(Spot(i)) }
        println("Created a parking lot with $size spots.")
    }

    fun status() {
        with(spots) {
            run { if (this.all { it.vacant } && this.size > 0) println(messages(3)) }
            filter { !it.vacant }.forEach { println("${it.num} ${it.reg} ${it.color}") }
            ifEmpty { println(messages(1)) }
        }
    }

    fun park(color: String, reg: String): String {
        val colorFormatted = color.lowercase().replaceFirstChar { it.uppercase() }
        val freeSpot = spots.find { it.vacant }?.also {
            it.vacant = false
            it.reg = reg
            it.color = colorFormatted
        }
        if (spots.isEmpty()) return messages(1)
        return with(freeSpot) {
            run { if (this != null) "$colorFormatted car parked in spot ${this.num}." else messages(2) }
        }
    }

    fun leave(spot: Int?) {
        try {
            val occupiedSpot = spots.find { it.num == spot }
            with(occupiedSpot!!) {
                run { if (vacant) "There is no car in spot $spot." else "Spot $spot is free." }
                    .also(::println)
                apply {
                    vacant = true
                    reg = ""
                    color = ""
                }
            }
        } catch (e: NullPointerException) {
            println(messages(1))
        }
    }

    fun query(command: String, arg: String?) {
        val argLower = arg?.lowercase()
        if (spots.isEmpty()) {
            println(messages(1))
        } else {
            println(
                when (command) {
                    "spot_by_color" -> spotByColor(argLower)
                    "spot_by_reg" -> spotByReg(argLower)
                    "reg_by_color" -> regByColor(argLower)
                    else -> return
                }
            )
        }
    }

    private fun spotByColor(arg: String?): String {
        return spots.filter { it.color.lowercase() == arg }
            .joinToString { it.num.toString() }
            .ifEmpty { "color".output(arg?.uppercase()) }
    }

    private fun spotByReg(arg: String?): String {
        return spots.filter { it.reg.lowercase() == arg }
            .joinToString { it.num.toString() }
            .ifEmpty { "registration number".output(arg?.uppercase()) }
    }

    private fun regByColor(arg: String?): String {
        return spots.filter { it.color.lowercase() == arg }
            .joinToString { it.reg }
            .ifEmpty { "color".output(arg?.uppercase()) }
    }

    private fun String.output(arg: String?) = "No cars with $this $arg were found."

    private fun messages(id: Int): String {
        return when (id) {
            1 -> "Sorry, a parking lot has not been created."
            2 -> "Sorry, the parking lot is full."
            3 -> "Parking lot is empty."
            else -> ""
        }
    }
}