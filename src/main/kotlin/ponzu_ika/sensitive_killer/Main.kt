package ponzu_ika.sensitive_killer

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import java.io.File


class Main : ListenerAdapter() {

    private lateinit var receivedMessage:String
    private lateinit var retruned:String

    fun main(token:String, guild_id:String) {
        val jda = JDABuilder.createDefault(token,
            GatewayIntent.MESSAGE_CONTENT,
            GatewayIntent.GUILD_MESSAGES)

            .addEventListeners(this)
            .build()

        jda.awaitReady()

        val guild = jda.getGuildById(guild_id)
        requireNotNull(guild) {"ギルドが存在しません"}
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if(event.author.isBot) return
        receivedMessage = event.message.contentDisplay
        retruned = SensitiveKiller().sensitiveKiller(receivedMessage)
        if (retruned.contains("*"))
            event.message.reply(retruned).queue()
    }

}
//"1252613009076125738"
//println("引数1: tokenファイル, 引数2: GUILD_ID")
fun main(args: Array<String>){
    if(args.size != 2) {
        println("引数が二つより多い、若しくは少ないです。")
        return
    }
    val bot = Main()

    bot.main(File(args[0]).readText(),args[1])
}