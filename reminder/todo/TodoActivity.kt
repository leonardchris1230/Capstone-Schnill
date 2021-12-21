package com.lazibear.capstone_schnill.ui.reminder.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lazibear.capstone_schnill.R
import com.lazibear.capstone_schnill.data.reminder.TodoViewModelFactory
import com.lazibear.capstone_schnill.databinding.ActivityTodoBinding
import com.lazibear.capstone_schnill.ui.reminder.ReminderActivity


class TodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var todoFactory: TodoViewModelFactory
    private lateinit var todoAdapter: TodoAdapter

    private lateinit var fabAddSchedule : FloatingActionButton


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMenu()


        todoFactory = TodoViewModelFactory.getInstance(this)
        todoViewModel = ViewModelProvider(this, todoFactory)[TodoViewModel::class.java]
        todoAdapter = TodoAdapter()

        binding.rvTodo.setHasFixedSize(true)
        binding.rvTodo.layoutManager = LinearLayoutManager(this)
        binding.rvTodo.adapter = todoAdapter




        todoViewModel.getAllTodo().observe(this, {
            todoAdapter.submitList(it)
            if (it.isEmpty()) {
                binding.tvEmptyList.visibility = View.VISIBLE
            }
        })



        setFabClick()


    }
    private fun setFabClick() {

        fabAddSchedule = findViewById(R.id.fab)
        fabAddSchedule.setOnClickListener {
            val addIntent = Intent(this, ReminderActivity::class.java)
            startActivity(addIntent)
        }
    }

    private fun initMenu() {

        binding.todoToolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.todoToolbar.setNavigationOnClickListener { onBackPressed() }
        binding.todoToolbar.inflateMenu(R.menu.todo_menu)

//        binding.todoToolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.action_deleteAll -> deleteAllTodo()
//
//
//            }
//            true
//        }

    }
//    private fun deleteAllTodo() {
//        AlertDialog.Builder(requireContext())
//            .setTitle("Delete All")
//            .setMessage("Are you sure?")
//            .setPositiveButton("Yes"){dialog, _ ->
//                todoViewModel.deleteTodo(todo)
//                dialog.dismiss()
//            }.setNegativeButton("No"){dialog, _ ->
//                dialog.dismiss()
//            }.create().show()
//    }


}