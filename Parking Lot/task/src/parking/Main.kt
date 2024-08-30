package parking

fun main() {
    val pl = ParkingLot()
    while (true) {
        val list = readln().split(" ")

        when (list[0].lowercase()) {
            "park" -> pl.park(list[2], list[1]).also(::println)
            "leave" -> pl.leave(list[1].toIntOrNull())
            "create" -> pl.create(list[1].toIntOrNull())
            "status" -> pl.status()
            "spot_by_color", "reg_by_color", "spot_by_reg" -> pl.query(list[0], list[1])
            "exit" -> break
        }
    }
}