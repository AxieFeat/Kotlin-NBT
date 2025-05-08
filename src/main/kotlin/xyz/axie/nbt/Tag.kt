package xyz.axie.nbt

import xyz.axie.nbt.visitor.StreamingTagVisitor
import xyz.axie.nbt.visitor.StringTagVisitor
import xyz.axie.nbt.visitor.TagVisitor
import java.io.DataOutput
import java.io.IOException

/**
 * The base supertype for all NBT tags.
 */
interface Tag {

    /**
     * Gets the ID of this tag.
     *
     * @return The ID of this tag.
     */
    val id: Int

    /**
     * Gets the type of this tag.
     *
     * @return The type of this tag.
     */
    fun type(): TagType<*>

    /**
     * Writes this tag's contents to the given output.
     *
     * @param output The output to write to.
     *
     * @throws IOException If an IO error occurs.
     */
    @Throws(IOException::class)
    fun write(output: DataOutput)

    /**
     * Visits this tag's contents using the given visitor.
     *
     * @param visitor The visitor.
     */
    fun visit(visitor: TagVisitor)

    /**
     * Visits this tag's contents using the given visitor.
     *
     * @param visitor The visitor.
     *
     * @return The result.
     */
    fun visit(visitor: StreamingTagVisitor): StreamingTagVisitor.ValueResult

    /**
     * Converts this tag in to its SNBT form.
     *
     * @return The SNBT form of this tag.
     */
    fun asString(): String {
        return StringTagVisitor().visit(this)
    }

    /**
     * Creates a copy of this tag and returns the result.
     *
     * If this tag is immutable, it will return itself, as there is
     * no need to make a copy of it. Otherwise, it will copy/clone all relevant
     * fields and construct a new object with the results.
     *
     * @return The resulting copy.
     */
    fun copy(): Tag
}

