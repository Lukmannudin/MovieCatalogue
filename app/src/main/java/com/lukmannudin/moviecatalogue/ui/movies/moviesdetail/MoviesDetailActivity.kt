package com.lukmannudin.moviecatalogue.ui.movies.moviesdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lukmannudin.moviecatalogue.R
import com.lukmannudin.moviecatalogue.databinding.ActivityDetailBinding
import com.lukmannudin.moviecatalogue.databinding.ActivityMoviesDetailBinding

class MoviesDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_COURSE = "extra_course"
    }

    private lateinit var binding: ActivityMoviesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewBinding()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        )[MoviesDetailViewModel.DetailCourseViewModel::class.java]
//
//        val adapter = DetailCourseAdapter()
//
//        val extras = intent.extras
//        if (extras != null) {
//            val courseId = extras.getString(EXTRA_COURSE)
//            if (courseId != null) {
//                viewModel.setSelectedCourse(courseId)
//                val modules = viewModel.getModules()
//                adapter.setModules(modules)
//                for (course in DataDummy.generateDummyCourses()) {
//                    if (course.courseId == courseId) {
//                        populateCourse(viewModel.getCourse())
//                    }
//                }
//            }
//        }
//
//        with(binding.rvModule) {
//            isNestedScrollingEnabled = false
//            layoutManager = LinearLayoutManager(this@DetailCourseActivity)
//            setHasFixedSize(true)
//            this.adapter = adapter
//            val dividerItemDecoration =
//                DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
//            addItemDecoration(dividerItemDecoration)
//        }
//    }
//
//    private fun populateCourse(courseEntity: CourseEntity) {
//        binding.textTitle.text = courseEntity.title
//        binding.textDescription.text = courseEntity.description
//        binding.textDate.text =
//            resources.getString(R.string.deadline_date, courseEntity.deadline)
//
//        Glide.with(this)
//            .load(courseEntity.imagePath)
//            .transform(RoundedCorners(20))
//            .apply(
//                RequestOptions.placeholderOf(R.drawable.ic_loading)
//                    .error(R.drawable.ic_error)
//            )
//            .into(binding.imagePoster)
//
//        binding.btnStart.setOnClickListener {
//            val intent = Intent(this@DetailCourseActivity, CourseReaderActivity::class.java)
//            intent.putExtra(CourseReaderActivity.EXTRA_COURSE_ID, courseEntity.courseId)
//            startActivity(intent)
//        }
    }

    private fun setContentViewBinding(){
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        with(activityDetailBinding.vsContentDetail){
            setOnInflateListener { _, inflated ->
                binding = ActivityMoviesDetailBinding.bind(inflated)
            }
            layoutResource = R.layout.activity_movies_detail
            inflate()
        }

        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)
    }
}