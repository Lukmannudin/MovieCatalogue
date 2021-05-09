package com.lukmannudin.moviecatalogue.mapper.mapperhelper

/**
 * Created by Lukmannudin on 09/05/21.
 */


// Non-nullable to nullable
interface NullableOutputListMapper<I,O>: Mapper<List<I>, List<O>?>

class NullableOutputListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : NullableOutputListMapper<I, O> {
    override fun map(input: List<I>): List<O>? {
        return if (input.isEmpty()) null else input.map { mapper.map(it) }
    }
}