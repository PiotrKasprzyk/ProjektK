package com.example.myapplication212

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

import android.widget.Button



class ExerciseAdapter(
    private val exercises: MutableList<Exercise>,
    private val listener: ExerciseAdapterListener
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    fun addExercise(exercise: Exercise) {
        exercises.add(exercise)
        notifyDataSetChanged()
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exerciseImageView: ImageView = itemView.findViewById(R.id.exerciseImageView)
        private val exerciseNameTextView: TextView = itemView.findViewById(R.id.exerciseNameTextView)
        private val exerciseDescriptionTextView: TextView = itemView.findViewById(R.id.exerciseDescriptionTextView)
        private val repetitionsTextView: TextView = itemView.findViewById(R.id.repetitionsTextView)
        private val dayCompletedTextView: TextView = itemView.findViewById(R.id.dayCompletedTextView)
        private val resetButton: Button = itemView.findViewById(R.id.resetButton)

        fun bind(exercise: Exercise) {
            exerciseImageView.setImageResource(exercise.imageResId)
            exerciseNameTextView.text = exercise.name
            exerciseDescriptionTextView.text = exercise.description
            repetitionsTextView.text =
                itemView.context.getString(R.string.repetitions, exercise.repetitions)

            if (exercise.dayCompleted != null) {
                dayCompletedTextView.visibility = View.VISIBLE
                dayCompletedTextView.text =
                    itemView.context.getString(R.string.completed_on, exercise.dayCompleted)
            } else {
                dayCompletedTextView.visibility = View.GONE
            }

            itemView.setOnClickListener {
                exercise.repetitions++
                exercise.dayCompleted = getCurrentDate()
                exercise.history.add(
                    ExerciseHistory(
                        exercise.dayCompleted ?: "",
                        exercise.repetitions
                    )
                )
                notifyDataSetChanged()
            }

            repetitionsTextView.setOnClickListener {
                exercise.repetitions = 0
                exercise.history.clear()
                notifyDataSetChanged()
            }

            resetButton.setOnClickListener {
                exercise.repetitions = 0
                exercise.history.clear()
                notifyDataSetChanged()
            }

            dayCompletedTextView.setOnClickListener {
                listener.onExerciseHistoryClicked(exercise)
            }
        }

        private fun getCurrentDate(): String {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }
    }
}
