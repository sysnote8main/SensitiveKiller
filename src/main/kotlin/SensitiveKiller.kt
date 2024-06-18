package ponzu_ika

import com.atilika.kuromoji.ipadic.Token
import com.atilika.kuromoji.ipadic.Tokenizer
import com.ibm.icu.text.Transliterator
import java.io.File


class SensitiveKiller() {
    fun sensitiveKiller(input:String): String {
        val ngWords = File("SensitiveWords.csv").readLines()
            .map { it.split(",") }

        var res = ""
        var out: String
        val tokenizer = Tokenizer()
        val kanaToLatin = Transliterator.getInstance("Katakana-Latin")

        for (token:Token in tokenizer.tokenize(input)) {
            res += token.reading.replace(Regex("""\n"""),"")
        }

        println(res)

        out = res.replace(Regex("""[^ア-ン]"""),"")
        println(out)

        out = kanaToLatin.transliterate(out).uppercase()
        println(out)

        ngWords.forEach { nglist ->
            out = out.replace(Regex(nglist[0]),"**${nglist[1]}**")
        }



        println(out)

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // o see how IntelliJ IDEA suggests fixing it.

        out = ""
        return out
    }
}

fun main(){
    SensitiveKiller().sensitiveKiller("コインランドリー")
}