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

        //読みがカタカナのためそのまま流用。しかし、読みがない場合があるためその際は文字そのものを取得
        //恐らくここの文字取得でひらがな/カタカナ以外が取得されるとエラーを吐く。要するにバグの温床
        //Regexで削除してしまえば解決できるがRegexの多用は重そう
        val res = tokenizer.tokenize(input).joinToString {
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
        out = out.replace(Regex("""[^ア-ンA-Z]"""),"")
        println("編集済みカナ: $out")

        //カタカナをアルファベットに変換
        out = katakanaToLatin.transliterate(out).uppercase()
        println("英大文字: $out")

        ngWords.forEach { words ->
            words.forEach { word ->
                println(out)
                println(word)
                out = (out.replace(
                    Regex(
                        //regex内でRegexしていて大変気持ちが悪い。
                        //やっていることは単純で、wordの表記ゆれを押さえているだけ。
                        word.replace(Regex("""CHI|TI"""), "(CHI|TI)")
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
/* debug酔う
fun main() {
    SensitiveKiller().sensitiveKiller("我慢、このカフェラテは(ry ")
}*/