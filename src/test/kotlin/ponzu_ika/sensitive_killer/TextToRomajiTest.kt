package ponzu_ika.sensitive_killer

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TextToRomajiTest: FunSpec({
    test("Simple Convert Test") {
        TextToRomaji.convert("a") shouldBe "a"
    }
})