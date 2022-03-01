package controllers

import models.Note

class NoteAPI {

    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String {
        return if(notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for(i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }

    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listActiveNotes(): String {
        var activeNotes = ""
        for(Note in notes) {
            if(!Note.isNoteArchived) {
                activeNotes += "${Note}\n"
            }
        }
      return if(activeNotes.isEmpty()) {
             "No active notes"
        } else {
          activeNotes
      }
    }

    fun listArchivedNotes(): String {
        var archivedNotes = ""
        for(Note in notes) {
            if(Note.isNoteArchived) {
                archivedNotes += "${Note}\n"
            }
        }
        return if(archivedNotes.isEmpty()) {
            "No archived notes"
        } else {
            archivedNotes
        }
    }

    fun numberOfArchivedNotes(): Int {
        //helper method to determine how many archived notes there are
        var numOfArchivedNotes = 0;
        for(Note in notes) {
            if(Note.isNoteArchived) {
                numOfArchivedNotes++
            }
        }
        return numOfArchivedNotes
    }

    fun numberOfActiveNotes(): Int {
        //helper method to determine how many active notes there are
        var numOfActiveNotes = 0;
        for(Note in notes) {
            if(!Note.isNoteArchived) {
                numOfActiveNotes++
            }
        }
        return numOfActiveNotes
    }

    fun listNotesBySelectedPriority(priority: Int): String {

        var notesByPriority = ""
        for(Note in notes) {
            if(Note.notePriority == priority) {
                notesByPriority += "${Note}\n"
            }
        }
        if(notesByPriority.isEmpty()) {
            return "No Note at that priority level"
        }
        return notesByPriority
    }

//    fun numberOfNotesByPriority(): Int {
//        //helper method to determine how many notes there are of a specific priority
//    }

}