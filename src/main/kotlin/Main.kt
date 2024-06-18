package ponzu_ika

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import okhttp3.EventListener
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.file.Files
import kotlin.io.path.Path


class Main : ListenerAdapter() {
    lateinit var jda:JDA
    private lateinit var receivedMessage:String
    private lateinit var retruned:String

    fun main(token:String) {
        val jda = JDABuilder.createDefault(token,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_MESSAGES)

            .addEventListeners(this)
            .build()

        jda.awaitReady()
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        receivedMessage = event.message.contentDisplay
        retruned = SensitiveKiller().sensitiveKiller(receivedMessage)
        if (retruned.contains("*"))
            event.message.reply(retruned).queue()

    }

}

fun main(){
    val bot = Main()
    bot.main(File("token").readText())
}