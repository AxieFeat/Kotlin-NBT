package xyz.axie.nbt.io

import xyz.axie.nbt.Tag

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