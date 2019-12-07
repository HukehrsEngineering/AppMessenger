package org.hukehrs.extensions

fun Collection<StackTraceElement>?.printableStacktrace(): String {
    return this?.joinToString(separator = "\n") { se -> formatElement(se) }
        ?: ""
}

fun Array<StackTraceElement>?.printableStacktrace(): String {
    return this?.joinToString(separator = "\n") { se -> formatElement(se) }
        ?: ""
}

fun Array<StackTraceElement>?.printableStacktrace(elements: Int): String {
    return this?.take(elements)?.joinToString(separator = "\n") { se -> formatElement(se) }
        ?: ""
}

fun formatElement(se: StackTraceElement): String {
    return "\t${se.className.toShortClassName()}.${se.methodName}(${se.fileName}:${se.lineNumber})"
}

fun String.toShortClassName(): String {
    return this.substring(lastIndexOf('.') + 1)
}
