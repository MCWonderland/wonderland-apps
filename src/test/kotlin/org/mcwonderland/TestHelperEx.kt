package org.mcwonderland

import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

inline fun <reified T : Exception> assertError(message: String, crossinline block: () -> Unit) {
    assertThrows<T> {
        block()
    }.let {
        assertEquals(message, it.message)
    }
}

inline fun assertRuntimeError(message: String, crossinline block: () -> Unit) {
    assertError<RuntimeException>(message, block)
}