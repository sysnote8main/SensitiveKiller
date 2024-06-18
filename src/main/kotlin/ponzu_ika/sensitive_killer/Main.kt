package ponzu_ika.sensitive_killer

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File


class Main : ListenerAdapter() {
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