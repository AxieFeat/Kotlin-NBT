package xyz.arial.nbt.visitor

import xyz.arial.nbt.*
import java.util.regex.Pattern

/**
 * A tag visitor that visits a tag and converts it in to standard SNBT form.
 */
class StringTagVisitor : TagVisitor {

    private val builder = StringBuilder()

    fun visit(tag: Tag): String {
        tag.visit(this)
        return builder.toString()
    }

    override fun visitEnd(tag: EndTag) {
        builder.append("END")
    }

    override fun visitByte(tag: ByteTag) {
        builder.append(tag.value).append('b')
    }

    override fun visitShort(tag: ShortTag) {
        builder.append(tag.value).append('s')
    }

    override fun visitInt(tag: IntTag) {
        builder.append(tag.value)
    }

    override fun visitLong(tag: LongTag) {
        builder.append(tag.value).append('L')
    }

    override fun visitFloat(tag: FloatTag) {
        builder.append(tag.value).append('f')
    }

    override fun visitDouble(tag: DoubleTag) {
        builder.append(tag.value).append('d')
    }

    override fun visitByteArray(tag: ByteArrayTag) {
        builder.append("[B;")
        for (i in 0 until tag.size) {
            if (i != 0) builder.append(',')
            builder.append(tag.data[i]).append('B')
        }
        builder.append(']')
    }

    override fun visitString(tag: StringTag) {
        builder.append(quoteAndEscape(tag.value))
    }

    override fun visitList(tag: ListTag) {
        builder.append('[')
        for (i in 0 until tag.size) {
            if (i != 0) builder.append(',')
            builder.append(StringTagVisitor().visit(tag.get(i)))
        }
        builder.append(']')
    }

    override fun visitCompound(tag: CompoundTag) {
        builder.append('{')
        for (key in tag.data.keys) {
            if (builder.length != 1) builder.append(',')
            builder.append(escape(key)).append(':').append(StringTagVisitor().visit(tag.get(key)!!))
        }
        builder.append('}')
    }

    override fun visitIntArray(tag: IntArrayTag) {
        builder.append("[I;")
        for (i in 0 until tag.size) {
            if (i != 0) builder.append(',')
            builder.append(tag.data[i])
        }
        builder.append(']')
    }

    override fun visitLongArray(tag: LongArrayTag) {
        builder.append("[L;")
        for (i in 0 until tag.size) {
            if (i != 0) builder.append(',')
            builder.append(tag.data[i]).append('L')
        }
        builder.append(']')
    }

    companion object {
        private val VALUE_REGEX: Pattern = Pattern.compile("[A-Za-z0-9._+-]+")
        private const val NULL_CHARACTER = 0.toChar()
        private const val BACKSLASH = '\\'
        private const val SINGLE_QUOTE = '\''
        private const val DOUBLE_QUOTE = '"'

        private fun quoteAndEscape(text: String): String {
            val builder = StringBuilder(" ")
            var quote = NULL_CHARACTER
            for (element in text) {
                if (element == BACKSLASH) {
                    builder.append(BACKSLASH)
                } else if (element == DOUBLE_QUOTE || element == SINGLE_QUOTE) {
                    if (quote == NULL_CHARACTER) quote = if (element == DOUBLE_QUOTE) SINGLE_QUOTE else DOUBLE_QUOTE
                    if (quote == element) builder.append('\\')
                }
                builder.append(element)
            }
            if (quote == NULL_CHARACTER) quote = DOUBLE_QUOTE
            builder.setCharAt(0, quote)
            builder.append(quote)
            return builder.toString()
        }

        private fun escape(text: String): String {
            if (VALUE_REGEX.matcher(text).matches()) return text
            return quoteAndEscape(text)
        }
    }
}