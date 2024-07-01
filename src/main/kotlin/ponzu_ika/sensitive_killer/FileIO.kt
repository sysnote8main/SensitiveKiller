package ponzu_ika.sensitive_killer

import java.io.BufferedWriter
import java.io.File

fun reader(fileName: String): MutableList<String> {
    return File(fileName).readLines().toMutableList()
}

fun writer(fileName: String): BufferedWriter {
    return File(fileName).bufferedWriter()
}