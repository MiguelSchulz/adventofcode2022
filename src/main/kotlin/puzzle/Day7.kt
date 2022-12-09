package puzzle

import Puzzle

object Day7 : Puzzle {
    override val day: Int = 7
    override val fileInputPath: String = "src/main/resources/terminal_file_system.txt"

    override fun solvePart1(input: String): Long {
        val homeDir = input.readDirectoriesFromCommandLineInput()
        return homeDir.allDirs()
            .filter { it.size <= 100_000 }
            .sumOf { it.size }
    }

    override fun solvePart2(input: String): Long {
        val homeDir = input.readDirectoriesFromCommandLineInput()
        val toDelete = 30_000_000 - (70_000_000 - homeDir.size)
        return homeDir.allDirs()
            .sortedBy { it.size }
            .first { it.size >= toDelete }
            .size
    }
}

private fun String.readDirectoriesFromCommandLineInput(): Object.Directory {
    var pathStore: ArrayDeque<Object.Directory> = ArrayDeque()
    pathStore.addLast(Object.Directory("/"))

    for (line in lines()) {
        // Handle actions
        line.toAction()?.let { action ->
            when (action) {
                is Action.toHome -> {
                    val homeDir = pathStore.first()
                    pathStore = ArrayDeque()
                    pathStore.addLast(homeDir)
                }

                is Action.moveOutOne -> {
                    pathStore.removeLast()
                }

                is Action.moveInto -> {
                    val dir = Object.Directory(action.dirName)
                    pathStore.lastOrNull()?.content?.add(dir)
                    pathStore.addLast(dir)
                }

                else -> {}
            }
        }
        // Handle files, ignore dirs
        Object.fromString(line)
            .takeIf { it is Object.File }
            ?.let { file ->
                pathStore.lastOrNull()?.content?.add(file)
            }
    }
    return pathStore.first()
}

private sealed class Action {
    object toHome : Action()
    object list : Action()
    object moveOutOne : Action()
    class moveInto(val dirName: String) : Action()
}

private fun String.toAction(): Action? {
    if (!startsWith('$')) return null
    return when (val cleaned = drop(2)) {
        "ls" -> Action.list
        "cd .." -> Action.moveOutOne
        "cd /" -> Action.toHome
        else -> {
            if (cleaned.startsWith("cd")) {
                return Action.moveInto(cleaned.split(' ')[1])
            }
            return null
        }
    }
}

private sealed class Object {
    abstract val size: Long
    abstract val name: String

    data class File(
        override val size: Long,
        override val name: String
    ) : Object()

    data class Directory(
        override val name: String,
        val content: MutableList<Object> = mutableListOf()
    ) : Object() {

        override val size: Long
            get() = content.sumOf { it.size }

        fun allDirs(): Set<Directory> {
            val subDirs = content.mapNotNull { it as? Directory }
            return (listOf(this) +
                    subDirs +
                    subDirs.map { it.allDirs() }.flatten())
                .toSet()

        }
    }

    companion object {
        fun fromString(command: String): Object? {
            val commandSplitted = command.split(" ")
            if (commandSplitted[0] == "dir") return Directory(commandSplitted[1])
            commandSplitted[0].toLongOrNull()?.let {
                return File(it, commandSplitted[1])
            }
            return null
        }
    }
}