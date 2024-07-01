package ponzu_ika.sensitive_killer

import com.atilika.kuromoji.ipadic.Tokenizer
import com.ibm.icu.text.Transliterator
import java.io.File


class SensitiveKiller {
    val ngWords = File("words/SensitiveWords.txt").readLines()
    val wordException = File("words/WordException.txt").readLines().map { it.split(",") }
    fun sensitiveKiller(input:String): String {
        println(ngWords)
        println("入力: $input")
        var out: String
        val tokenizer = Tokenizer()
        val katakanaToLatin = Transliterator.getInstance("Katakana-Latin")
        val kanaToKatakana = Transliterator.getInstance("Hiragana-Katakana")
        var wordExcepted = ""
        wordException.forEach {word ->
             wordExcepted = input.replace(Regex(word[0]),word[1]).uppercase()
        }

        //読みがカタカナのためそのまま流用。しかし、読みがない場合があるためその際は文字そのものを取得
        //恐らくここの文字取得でひらがな/カタカナ以外が取得されるとエラーを吐く。要するにバグの温床
        //Regexで削除してしまえば解決できるがRegexの多用は重そう
        val res = tokenizer.tokenize(wordExcepted).joinToString {
            if (it.reading == "*") it.surface else it.reading
        }.replace(Regex("""\s|,"""),"")
/*
        for (token:Token in tokenizer.tokenize(input)) {
            res += token.surface.replace(Regex("""\n"""),"")
        }*/
        println("カナ: $res")
        //ひらがなをカタカナに変換。主にsurfaceで取得されたデータ用
        out = kanaToKatakana.transliterate(res)
        println(out)

        //ここで句読点など余計なものを消去
        out = out.replace(Regex("""[^ア-ンA-Z0-9]"""),"")
        println("編集済みカナ: $out")

        //カタカナをアルファベットに変換
        out = katakanaToLatin.transliterate(out).uppercase()
        println("英大文字: $out")

        ngWords.forEach { word ->
            val uppercaseWord = word.uppercase()
            println(word)
            out = (
                    out.replace(Regex("N'N"),"NNN")
                        .replace(Regex("GYI"),"GI")
                        .replace(Regex(
                    //regex内でRegexしていて大変気持ちが悪い。
                    //やっていることは単純で、wordの表記ゆれを押さえているだけ。
                            uppercaseWord.replace(Regex("""CHI|TI"""), "(CHI|TI)")
                                .replace(Regex("""N|NN"""), "(N|NN)")
                                .replace(Regex("""CO|KO"""), "(CO|KO)")
                                .replace(Regex("""RA|LLA"""), "(RA|LLA)")
                                .replace(Regex("""HU|FU"""),"(HU|FU)")
                                .replace(Regex("""SI|SHI"""),"(SHI|SI)")
                                .replace(Regex("""JI|ZI"""),"(JI|ZI)")
                                .replace(Regex("""SHO|SYO"""),("(SHO|SYO)"))
                        ), "**$uppercaseWord**"
            ))
        }

        println("あれば太字: $out")

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // o see how IntelliJ IDEA suggests fixing it.

        return out
    }
}
/* debug酔う
fun main() {
    SensitiveKiller().sensitiveKiller("我慢、このカフェラテは(ry ")
}*/