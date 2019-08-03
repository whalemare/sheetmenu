import java.io.File
import java.io.PrintWriter

import java.util.regex.Pattern

/**
 * This script find extension variables in your *root* variablesPath
 * like
 * ext.app_version = "1.2.32"
 * or "
 * ext.borsch = '1.0'
 *
 * and change variables from your README-template.md to their values.
 * For example script process
 * from: compile 'com.github.whalemare:sheetmenu:$app_version'
 * to: compile 'com.github.whalemare:sheetmenu:1.3.2'
 *
 * Easy, but maybe buggy. Be careful, if you can use it for yourself
 */

class App {
    val variablesPath = "build.gradle"
    val versionNameVariable = "app_version"

    val parameters = listOf(
            "major",
            "minor",
            "patch",
            "ignore"
    )

    fun main() {
        val map = getVariables()
        val value = map[versionNameVariable] ?: ""

        if (!value.isNullOrEmpty()) {
            ask("What you what update: $parameters ?").let {

                val mass: MutableList<String> = value.split(".") as MutableList<String>

                when(it.toLowerCase()) {
                    parameters[0] -> mass[0] = (mass[0].toInt() + 1).toString()
                    parameters[1] -> mass[1] = (mass[1].toInt() + 1).toString()
                    parameters[2] -> mass[2] = (mass[2].toInt() + 1).toString()
                    parameters[3] -> return@let
                }

                map[versionNameVariable] = "${mass[0]}.${mass[1]}.${mass[2]}"

                File(variablesPath).findAndReplace(value, map[versionNameVariable] ?: "NULL_ON_MAP")

                println("version updated to ${map[versionNameVariable]}")
            }

        } else {
            println("Can`t find $versionNameVariable in your gradle.file, and can`t auto update your version. " +
                    "Only readme")
        }

        val writer = PrintWriter("README.md", "UTF-8")

        openReadmeTemplate().readLines().forEach {
            val line = it
            var lineChanges = line

            map.forEach { name, version ->
                val needFind = "$$name"

                if (line.contains(needFind)) {
                    lineChanges = line.replace(needFind, version)

                    askBinary("\nReplace?\n$line\n$lineChanges").let { needDo ->
                        lineChanges = if (needDo) lineChanges else line
                    }
                } else {
                    lineChanges = line
                }
            }

            writer.println(lineChanges)
        }
        writer.close()
    }

    fun openReadmeTemplate(path: String = "screens/README-template.md"): File {
        return File(path)
    }

    fun getVariables(): MutableMap<String, String> {
        val map = HashMap<String, String>()

        File(variablesPath).readLines().forEach {
            val pattern = Pattern.compile("ext\\..*")
            val matcher = pattern.matcher(it)

            if (matcher.find()) {
                (0..matcher.groupCount()).forEach {
                    var rawVar = matcher.group(it)

                    rawVar = rawVar.replace("ext.", "")

                    val index = rawVar.indexOf("=")

                    var name = rawVar.substring(0, index)
                    name = name.replace("=", "")
                    name = name.trim()

                    var value = rawVar.substring(index)
                    value = value.replace("=", "")
                    value = value.replace("\"", "")
                    value = value.replace("\'", "")
                    value = value.trim()

                    map.put(name, value)
                }
            }
        }

//        for ((name, value) in map) {
//            println("$name = $value")
//        }
        return map
    }

    fun ask(answer: String): String {
        println(answer)
        return readLine() ?: ""
    }

    fun askBinary(answer: String): Boolean {
        val line = ask(answer)
        return line == "y" || line == "yes"
    }

    fun File.findAndReplace(find: String, replace: String) {
        val toReplace = mutableListOf<String>()

        this.readLines().forEach {
            toReplace.add(it.replace(find, replace))
        }

        val writer = PrintWriter(this.name, "UTF-8")
        toReplace.forEach { writer.println(it) }
        writer.close()
    }
}

App().main()
println("\n\nDone.")