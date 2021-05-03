package com.lukmannudin.moviecatalogue.data

/**
 * Created by Lukmannudin on 5/3/21.
 */


object TvShowsDummy {
    fun generateDummyTvShows(): List<TvShow> {
        val tvShows = ArrayList<TvShow>()

        tvShows.add(
            TvShow(
                "88396",
                "The Falcon and the Winter Soldier",
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                "Mar 19, 2021",
                0.79f,
                "https://www.themoviedb.org/t/p/w440_and_h660_face/6kbAMLteGO8yyewYau6bJ683sw7.jpg"
            )
        )

        tvShows.add(
            TvShow(
                "95557",
                "Invincible",
                "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
                "Mar 26, 2021",
                0.89f,
                "https://www.themoviedb.org/t/p/w440_and_h660_face/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg"
            )
        )

        tvShows.add(
            TvShow(
                "120587",
                "Haunted: Latin America",
                "Real people's terrifying tales of the chilling, unexplained and paranormal come to life with dramatic reenactments in this reality series.",
                "Mar 31, 2021",
                0.74f,
                "https://www.themoviedb.org/t/p/w440_and_h660_face/Q1ZYG3kDS8iVIHOYOJ9NQmV0q7.jpg"
            )
        )

        return tvShows
    }
}