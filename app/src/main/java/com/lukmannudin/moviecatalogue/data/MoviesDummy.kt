package com.lukmannudin.moviecatalogue.data

object MoviesDummy {

    fun generateDummyMovies(): List<Movie> {
        val movies = ArrayList<Movie>()

        movies.add(
            Movie(
                "460465",
                "Mortal Combat",
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe",
                "Apr 07, 2021",
                0.78f,
                "https://www.themoviedb.org/t/p/w440_and_h660_face/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg"
            )
        )

        movies.add(
            Movie(
                "399566",
                "Godzilla vs. Kong",
                "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
                "Mar 24, 2021",
                0.82f,
                "https://www.themoviedb.org/t/p/w440_and_h660_face/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg"
            )
        )

        movies.add(
            Movie(
                "804435",
                "Vanquish",
                "Victoria is a young mother trying to put her dark past as a Russian drug courier behind her, but retired cop Damon forces Victoria to do his bidding by holding her daughter hostage. Now, Victoria must use guns, guts and a motorcycle to take out a series of violent gangsters—or she may never see her child again.",
                "04/23/2021",
                0.78f,
                "https://www.themoviedb.org/t/p/w440_and_h660_face/AoWY1gkcNzabh229Icboa1Ff0BM.jpg"
            )
        )

        return movies
    }
}