package com.example.laba4

import NotesDBHelper
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FourthFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var noteIdEditText: EditText
    private lateinit var newTitleEditText: EditText
    private lateinit var newTextEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var dbHelper: NotesDBHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_fourth, container, false)

        noteIdEditText = rootView.findViewById(R.id.noteIdEditText)
        newTitleEditText = rootView.findViewById(R.id.newTitleEditText)
        newTextEditText = rootView.findViewById(R.id.newTextEditText)
        updateButton = rootView.findViewById(R.id.updateButton)


        dbHelper = NotesDBHelper(requireContext())

        updateButton.setOnClickListener {
            val noteId = noteIdEditText.text.toString().toLongOrNull()
            val newTitle = newTitleEditText.text.toString()
            val newText = newTextEditText.text.toString()
            if (noteId != null && newTitle.isNotEmpty() && newText.isNotEmpty()) {
                val rowsAffected = dbHelper.updateNoteById(noteId, newTitle, newText)
                if (rowsAffected > 0) {

                    Toast.makeText(context, "Заметка $newTitle была изменена!", Toast.LENGTH_SHORT).show()
                    noteIdEditText.setText("")
                    newTitleEditText.setText("")
                    newTextEditText.setText("")
                } else {
                    Toast.makeText(context, "Заметка c ID $noteId не была найдена", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Введите в поле ID заметки", Toast.LENGTH_LONG).show()
            }
        }

        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FourthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
