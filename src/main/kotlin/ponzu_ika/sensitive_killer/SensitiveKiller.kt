package ponzu_ika.sensitive_killer

import com.atilika.kuromoji.ipadic.Tokenizer
import com.ibm.icu.text.Transliterator
import java.io.File


class SensitiveKiller {
    val ngWords = File("SensitiveWords.txt").readLines().map { it.split(",") }

    fun sensitiveKiller(input:String): String {
        println(ngWords)
        println("入力: $input")
        var out: String
        val tokenizer = Tokenizer()
        val katakanaToLatin = Transliterator.getInstance("Katakana-Latin")
        val kanaToKatakana = Transliterator.getInstance("Hiragana-Katakana")

        val res = tokenizer.tokenize(input).joinToString {
            if (it.reading == "*") it.surface else it.reading
        }.replace(Regex("""\s|,"""),"")
/*
        for (token:Token in tokenizer.tokenize(input)) {
            res += token.surface.replace(Regex("""\n"""),"")
        }*/
        println("カナ: $res")

        out = kanaToKatakana.transliterate(res)
        println(out)

        out = out.replace(Regex("""[^ア-ン]"""),"")
        println("編集済みカナ: $out")

        out = katakanaToLatin.transliterate(out).uppercase()
        println("英大文字: $out")

        ngWords.forEach { words ->
            words.forEach { word ->
                println(out)
                println(word)
                out = (out.replace(
                    Regex(word
                        .replace(Regex("""CHI|TI"""), "(CHI|TI)")
                        .replace(Regex("""N|NN"""), "(N|NN)")
                        .replace(Regex("""CO|KO"""), "(CO|KO)")
                        .replace(Regex("""RA|LLA"""), "(RA|LLA)")
                    ), "**$word**"
                ))
            }
        }

        println("あれば太字: $out")

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // o see how IntelliJ IDEA suggests fixing it.

        return out
    }
}

fun main() {
    SensitiveKiller().sensitiveKiller("我慢、このカフェラテは(ry ")
}