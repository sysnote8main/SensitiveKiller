package ponzu_ika

import com.atilika.kuromoji.ipadic.Token
import com.atilika.kuromoji.ipadic.Tokenizer
import com.ibm.icu.text.Transliterator


class SensitiveKiller() {
    fun sensitiveKiller(input:String): String {

        val ngWords: List<List<String>> = listOf(
            listOf("MANCO|MANKO", "MANKO"),
            listOf("FERA|FELLA", "FELLA"),
            listOf("CHINKO|TINKO","CHINKO"),
            listOf("ANARU|ANAL","ANAL"),
            listOf("INRAN|INNRAN","INRAN")
        )

        var res = "";
        var out: String;
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
        // to see how IntelliJ IDEA suggests fixing it.

        return out
    }
}