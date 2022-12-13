package puzzle

import Puzzle

object Day13 : Puzzle {
    override val day: Int = 13
    override val fileInputPath: String = "src/main/resources/packet_received.txt"
    override fun solvePart1(input: String): Int {
        return input.split("\n\n")
            .map { packetString ->
                val packetList = packetString.split("\n")
                packetList[0].toPackets() to packetList[1].toPackets()
            }
            .mapNotNull { (packet1, packet2) ->
                if (packet1 != null && packet2 != null) packet1.compare(packet2) else null
            }
            .mapIndexed { index, result -> index to result }
            .sumOf { (index, result) ->
                if (result == -1) index + 1 else 0
            }
    }

    override fun solvePart2(input: String): Int {
        val divider1 = Packets.PList(listOf(Packets.PList(listOf(Packets.PValue(2)))))
        val divider2 = Packets.PList(listOf(Packets.PList(listOf(Packets.PValue(6)))))

        val packets = (input.split("\n\n")
            .flatMap { packetString ->
                val packetList = packetString.split("\n")
                listOf(packetList[0].toPackets(), packetList[1].toPackets())
            } + divider1 + divider2)
            .filterNotNull()
            .sortedWith { lhs, rhs -> lhs.compare(rhs) }

        return (1 + packets.indexOf(divider1)) * (1 + packets.indexOf(divider2))
    }


    private fun Packets.compare(with: Packets): Int {
        return when {
            this is Packets.PValue && with is Packets.PValue -> (this.packets.compareTo(with.packets))
            this is Packets.PList && with is Packets.PList -> this.compare(with)
            this is Packets.PList && with is Packets.PValue -> this.compare(Packets.PList(listOf(with)))
            this is Packets.PValue && with is Packets.PList -> Packets.PList(listOf(this)).compare(with)
            else -> throw Error()
        }
    }

    private fun Packets.PList.compare(with: Packets.PList): Int {
        val pairs = this.packets.zip(with.packets)
        pairs.forEach { (packet1, packet2) ->
            val result = packet1.compare(packet2)
            if (result != 0) return result
        }
        return when {
            this.packets.size == with.packets.size -> 0
            this.packets.size < with.packets.size -> -1
            else -> 1
        }
    }

    private fun String.toPackets(): Packets? {
        return when {
            isEmpty() -> null
            this[0].isDigit() -> Packets.PValue(toInt())
            else -> {
                var bracketCount = 0
                var lastComma = 0

                val packets = mutableListOf<Packets?>()

                forEachIndexed { index, char ->
                    when {
                        char == '[' -> bracketCount++
                        char == ']' -> {
                            bracketCount--
                            if (bracketCount == 0) packets += take(index).drop(lastComma + 1).toPackets()
                        }
                        char == ',' && bracketCount == 1 -> {
                            packets += take(index).drop(lastComma + 1).toPackets()
                            lastComma = index
                        }
                    }
                }
                Packets.PList(packets.filterNotNull())
            }
        }
    }
}

private sealed class Packets {
    data class PList(val packets: List<Packets>) : Packets()
    data class PValue(val packets: Int) : Packets()
}