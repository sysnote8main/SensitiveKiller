package ponzu_ika.sensitive_killer

import com.atilika.kuromoji.ipadic.Token
import com.atilika.kuromoji.ipadic.Tokenizer
import com.ibm.icu.text.Transliterator
import java.io.File


class SensitiveKiller {
    val ngWords = File("SensitiveWords.csv").readLines().map { it.split(",") }

    fun sensitiveKiller(input:String): String {
        println(ngWords)
        println("入力: $input")
        var res = ""
        var out: String
        val tokenizer = Tokenizer()
        val kanaToLatin = Transliterator.getInstance("Katakana-Latin")

        res = tokenizer.tokenize(input).joinToString {
            if (it.reading == "*") it.surface else it.reading
        }.replace(Regex("""\s|,"""),"")
/*
        for (token:Token in tokenizer.tokenize(input)) {
            res += token.surface.replace(Regex("""\n"""),"")
        }*/
        println("カナ: $res")

        out = res.replace(Regex("""[^ア-ン]"""),"")
        println(out)

        out = kanaToLatin.transliterate(out).uppercase()
        println(out)

        ngWords[1].forEach {word ->
            out = (out
                .replace(Regex(word
                    .replace(Regex("""CHI|TI"""),"(CHI|TI)")
                    .replace(Regex("""N|NN"""),"(N|NN)")
                    .replace(Regex("""CO|KO"""),"(CO|KO)")
                    .replace(Regex("""RA|LLA"""),"(RA|LLA)")

                ),"**$word**"))
        }



        println(out)

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // o see how IntelliJ IDEA suggests fixing it.

        return out
    }
}

fun main() {
    SensitiveKiller().sensitiveKiller("我慢、このカフェラテは(ry ")
}