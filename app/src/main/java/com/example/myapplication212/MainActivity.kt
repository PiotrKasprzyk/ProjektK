package com.example.myapplication212

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.myapplication212.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*



class MainActivity : AppCompatActivity(), ExerciseAdapterListener {

    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseAdapter = ExerciseAdapter(generateExerciseList(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = exerciseAdapter

        val addExerciseButton: FloatingActionButton = binding.addExerciseButton
        addExerciseButton.setOnClickListener {
            showAddExerciseDialog()
        }
    }

    private fun generateExerciseList(): MutableList<Exercise> {
        val exerciseList = mutableListOf<Exercise>()
        exerciseList.add(Exercise("Push-ups", "Perform 10 push-ups", R.drawable.push_ups_image))
        exerciseList.add(Exercise("Sit-ups", "Perform 20 sit-ups", R.drawable.sit_ups_image))
        exerciseList.add(Exercise("Squats", "Perform 15 squats", R.drawable.squats_image))
        return exerciseList
    }

    override fun onExerciseHistoryClicked(exercise: Exercise) {
        val historyItems = exercise.history.map { "${it.date}: ${it.repetitions} repetitions" }.toTypedArray()

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Historia ćwiczeń")
        dialogBuilder.setItems(historyItems, null)
        dialogBuilder.setPositiveButton("OK", null)
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun showAddExerciseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_exercise, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Exercise")
            .setPositiveButton("Add") { dialog, _ ->
                val nameEditText = dialogView.findViewById<EditText>(R.id.nameEditText)
                val descriptionEditText = dialogView.findViewById<EditText>(R.id.descriptionEditText)

                val name = nameEditText.text.toString().trim()
                val description = descriptionEditText.text.toString().trim()

                if (name.isNotEmpty() && description.isNotEmpty()) {
                    val exercise = Exercise(name, description, R.drawable.default_exercise__image)
                    exerciseAdapter.addExercise(exercise)
                }

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}



