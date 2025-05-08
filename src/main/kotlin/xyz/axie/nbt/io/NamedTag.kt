package xyz.axie.nbt.io

import xyz.axie.nbt.Tag

/**
 * A holder for a tag with a name.
 *
 * @param name The name of the tag.
 * @param tag The tag.
 */
class NamedTag(
    val name: String,
    val tag: Tag
)