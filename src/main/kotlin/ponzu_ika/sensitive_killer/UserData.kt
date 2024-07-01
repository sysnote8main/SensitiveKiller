package ponzu_ika.sensitive_killer

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class UserData(val userid: Int, var count: Long = 0)

@Serializable
data class UserDataMap(val userDataMap: MutableMap<Int, UserData> = mutableMapOf())

internal val userDataPath = File("userdata.json")

var userDataMap = readUserDataMap()
    set(value) {
        field = value
        writeUserData(field)
    }

fun readUserDataMap(): UserDataMap {
    return Json.decodeFromString(userDataPath.readText())
}

fun writeUserData(data: UserDataMap) {
    userDataPath.writeText(Json.encodeToString(data))
}
