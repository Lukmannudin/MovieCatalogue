package com.lukmannudin.moviecatalogue

import com.lukmannudin.moviecatalogue.data.entity.Movie
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.utils.Converters.toDate

object DummiesTest {
    val basePosterPath = "https://image.tmdb.org/t/p/w500"
    val dummyMovie = Movie(
        637649,
        "Wrath of Man",
        "A cold and mysterious new security guard for a Los Angeles cash truck company surprises his co-workers when he unleashes precision skills during a heist. The crew is left wondering who he is and where he came from. Soon, the marksman's ultimate motive becomes clear as he takes dramatic and irrevocable steps to settle a score.",
        "2021-04-22".toDate(),
        8.2f,
        "$basePosterPath/tMS2qcbhbkFpcwLnbUE9o9IK4HH.jpg"
    )

    val dummyTvShow = TvShow(
        63174,
        "Lucifer",
        "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
        "2016-01-25".toDate(),
        8.5f,
        "$basePosterPath/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg"
    )

    val errorMessage = "error_message"
}