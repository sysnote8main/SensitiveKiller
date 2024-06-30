package ponzu_ika.sensitive_killer

import java.io.BufferedWriter
import java.io.File

fun channelReader(guildid:String): MutableList<String> {
    return File(guildid).readLines().toMutableList()

}
fun writer(guildid: String): BufferedWriter {
    return File(guildid).bufferedWriter()
}