package com.example.laba4

import NoteAdapter
import NotesDBHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FirstFragment : Fragment(){

    private lateinit var listView: ListView
    private lateinit var dbHelper: NotesDBHelper
    private lateinit var noteAdapter: NoteAdapter
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        val notes = dbHelper.getAllNotes()
        noteAdapter = NoteAdapter(requireContext(), notes)
        listView.adapter = noteAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_first, container, false)
        listView = rootView.findViewById(R.id.ListView)

        dbHelper = NotesDBHelper(requireContext())
        val notes = dbHelper.getAllNotes()
        noteAdapter = NoteAdapter(requireContext(), notes)
        listView.adapter = noteAdapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}