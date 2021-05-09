package com.lukmannudin.moviecatalogue.mapper.mapperhelper

/**
 * Created by Lukmannudin on 09/05/21.
 */


interface Mapper<I, O> {
    fun map(input: I): O
}