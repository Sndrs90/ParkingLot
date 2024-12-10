package parking

fun main() {
    var parkingLot: List<Spot> = listOf()

    while (true) {
        val input = readln()
        val inputList = parseInput(input)
        val command = inputList[0]
        when (command) {
            "create" -> {
                val spotsNum = inputList[1].toInt()
                parkingLot = List(spotsNum) {
                    Spot(it + 1)
                }
                println("Created a parking lot with $spotsNum spots.")
            }

            "park" -> {
                if (parkingLot.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val firstFreeSpotIndex = parkingLot.indexOfFirst { !it.isOccupied }
                if (firstFreeSpotIndex == -1) {
                    println("Sorry, the parking lot is full.")
                } else {
                    val car = Car(
                        registrationNumber = inputList[1],
                        color = inputList[2].lowercase()
                    )
                    val spot = parkingLot[firstFreeSpotIndex]
                    spot.car = car
                    spot.isOccupied = true
                    println("${car.color} car parked in spot ${spot.id}.")
                }
            }

            "leave" -> {
                if (parkingLot.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val spotId = inputList[1].toInt()
                val spot = parkingLot[spotId - 1]
                if (spot.isOccupied) {
                    spot.isOccupied = false
                    spot.car = null
                    println("Spot ${spot.id} is free.")
                } else println("There is no car in spot ${spot.id}.")
            }

            "status" -> {
                if (parkingLot.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val occupiedSpots = parkingLot.filter { spot -> spot.isOccupied  }
                if (occupiedSpots.isNotEmpty()) {
                    occupiedSpots.forEach { spot ->
                        println("${spot.id} ${spot.car?.registrationNumber} ${spot.car?.color}")
                    }
                } else println("Parking lot is empty.")
            }

            "reg_by_color" -> {
                if (parkingLot.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val color = inputList[1].lowercase()
                val carsWithColor = parkingLot.filter { spot -> spot.car?.color == color  }
                if (carsWithColor.isNotEmpty()) {
                    val regList = mutableListOf<Car>()
                    carsWithColor.forEach { spot: Spot ->
                        spot.car?.let { regList.add(it) }
                    }
                    println(regList.joinToString{car: Car -> car.registrationNumber })
                } else println("No cars with color $color were found.")
            }

            "spot_by_color" -> {
                if (parkingLot.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val color = inputList[1].lowercase()
                val spotsWithColor = parkingLot.filter { spot -> spot.car?.color == color  }
                if (spotsWithColor.isNotEmpty())
                    println(spotsWithColor.joinToString{spot: Spot -> spot.id.toString() })
                else println("No cars with color $color were found.")
            }

            "spot_by_reg" -> {
                if (parkingLot.isEmpty()) {
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val regNum = inputList[1]
                val spotWithRegNum = parkingLot.find { spot -> spot.car?.registrationNumber == regNum  }
                println(spotWithRegNum?.id ?: "No cars with registration number $regNum were found.")
            }

            "exit" -> break
        }
    }
}

fun parseInput(input: String): List<String> {
    return input.split(" ")
}

data class Spot(val id: Int, var isOccupied: Boolean = false) {
    var car: Car? = null
}

data class Car(val registrationNumber: String, val color: String)