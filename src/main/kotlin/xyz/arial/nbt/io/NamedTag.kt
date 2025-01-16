package xyz.arial.nbt.io

import xyz.arial.nbt.Tag

/**
 * A holder for a tag with a name.
 *
 * @param name the name of the tag
 * @param tag the tag
 */
class NamedTag(
    val name: String,
    val tag: Tag
)