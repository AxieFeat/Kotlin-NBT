package xyz.arial.nbt

import xyz.arial.nbt.visitor.StreamingTagVisitor
import java.io.DataInput
import java.io.IOException

/**
 * A type of tag that can be used to perform certain operations on tags of the
 * given type [T].
 *
 * @param T the actual tag type
 */
interface TagType<T : Tag> {

    /**
     * Gets the name of the tag.
     *
     * @return the name
     */
    val name: String

    /**
     * Gets the pretty print name of the tag.
     *
     * @return the pretty print name
     */
    val prettyName: String

    /**
     * Returns whether tags of this type are considered "value" tags, meaning
     * they just hold a value, like numeric and string tags (true), or they are
     * composed of other types, such as arrays, lists, and compounds (false).
     *
     * @return whether tags of this type are value tags
     */
    val isValue: Boolean
        get() = false

    /**
     * Loads a tag of this type from the given input data, recursively loading
     * if required, using the given depth to keep track of how many levels deep
     * in a recursive load we are.
     *
     * @param input the input to read the tag from
     * @param depth the current depth of a recursive read
     * @return the loaded tag
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun load(input: DataInput, depth: Int): T

    /**
     * Loads a tag of this type from the given input data and calls the
     * corresponding visit method on the given visitor with the loaded data.
     *
     * @param input the input to read the tag from
     * @param visitor the visitor to pass the loaded data to
     * @return the result of the visit
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun parse(input: DataInput, visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult

    /**
     * Loads a tag of this type from the given input data and uses the visitor
     * to visit the data as a root entry.
     *
     *
     * The semantics vary based on the result of [StreamingTagVisitor.visitRootEntry]:
     *
     *  * If the result is [StreamingTagVisitor.ValueResult.CONTINUE], we call [.parse].
     *  * If the result is [StreamingTagVisitor.ValueResult.HALT], we stop visiting.
     *  * If the result is [StreamingTagVisitor.ValueResult.BREAK], we call [.skip].
     *
     *
     * @param input the input to read the tag from
     * @param visitor the visitor to visit
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun parseRoot(input: DataInput, visitor: StreamingTagVisitor) {
        when (visitor.visitRootEntry(this)) {
            StreamingTagVisitor.ValueResult.CONTINUE -> parse(input, visitor)
            StreamingTagVisitor.ValueResult.BREAK -> skip(input)
            StreamingTagVisitor.ValueResult.HALT -> {}
        }
    }

    /**
     * Skips the given amount of bytes in the given input.
     *
     * @param input the input to skip bytes in
     * @param bytes the amount of bytes to skip
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun skip(input: DataInput, bytes: Int)

    /**
     * Skips the one byte in the given input.
     *
     * @param input the input to skip bytes in
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun skip(input: DataInput)

    /**
     * A specialized tag type with a statically known size in bytes.
     *
     *
     * This is used for value types, where their size is always known.
     * For example, we always know that a long value will be 8 bytes.
     *
     * @param <T> the type of the tag
    </T> */
    interface StaticSize<T : Tag> : TagType<T> {
        /**
         * Gets the statically known size in bytes.
         *
         * @return the size
         */
        val size: Int

        @Throws(IOException::class)
        override fun skip(input: DataInput, bytes: Int) {
            input.skipBytes(size * bytes)
        }

        @Throws(IOException::class)
        override fun skip(input: DataInput) {
            input.skipBytes(size)
        }
    }

    /**
     * A specialized tag type with a dynamic size.
     *
     *
     * This is used for non-value types and string, where their size is
     * dependent on the value itself. For example, an array could be 5 elements
     * or 50 elements long.
     *
     * @param <T> the type of the tag
    </T> */
    interface VariableSize<T : Tag> : TagType<T> {
        @Throws(IOException::class)
        override fun skip(input: DataInput, bytes: Int) {
            for (i in 0 until bytes) {
                skip(input)
            }
        }
    }

    companion object {
        /**
         * Creates a new tag type that is invalid, used for when a tag ID with no
         * actual representation is read.
         *
         * @param id the invalid tag ID
         * @return a new invalid tag type
         */
        fun createInvalid(id: Int): TagType<EndTag> {
            return object : TagType<EndTag> {

                override val name: String = "INVALID[$id]"

                override val prettyName: String = "UNKNOWN_$id"

                @Throws(IOException::class)
                override fun load(input: DataInput, depth: Int): EndTag {
                    throw createException()
                }

                @Throws(IOException::class)
                override fun parse(
                    input: DataInput,
                    visitor: StreamingTagVisitor
                ): StreamingTagVisitor.ValueResult {
                    throw createException()
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput, bytes: Int) {
                    throw createException()
                }

                @Throws(IOException::class)
                override fun skip(input: DataInput) {
                    throw createException()
                }

                private fun createException(): IOException {
                    return IOException("Invalid tag ID $id!")
                }
            }
        }
    }
}