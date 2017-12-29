import java.io.File

fun main(args: Array<String>) {
    val srcDir = File(args[0])

    val classCount = 500
    val methodCount = 1

    for (i in 1..classCount) {
        val code = buildString {
            appendln("package foo")
            appendln("class NumberProducer$i {")
            for (j in 1..methodCount) {
                appendln("  \"fun get$j() = $j\"")
            }
            appendln("}")
        }
        File(srcDir, "utils_$i.kt").writeText(code)
    }
}