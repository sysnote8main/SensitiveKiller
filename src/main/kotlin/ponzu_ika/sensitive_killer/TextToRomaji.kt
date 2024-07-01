package ponzu_ika.sensitive_killer

import com.ibm.icu.text.Transliterator

object TextToRomaji {
    private val katakanaToLatin: Transliterator = Transliterator.getInstance("Katakana-Latin")
    private val kanaToKatakana: Transliterator = Transliterator.getInstance("Hiragana-Katakana")
    fun convert(rawText: String): String {
        return rawText
    }
}