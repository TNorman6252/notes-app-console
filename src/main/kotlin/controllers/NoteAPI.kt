package controllers

import models.Note
import persistence.Serializer

class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
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

    fun deleteNote(indexToDelete: Int) : Note? {
        return if(isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }

    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        val foundNote = findNote(indexToUpdate)

        if((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        return false
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

    fun isValidIndex(index: Int) : Boolean {
        return isValidListIndex(index, notes)
    }

    fun listActiveNotes(): String {
        var activeNotes = ""
        var index = -1
        for(Note in notes) {
            index++
            if(!Note.isNoteArchived) {
                activeNotes += "$index :  ${Note}\n"
            }
        }
      return if(activeNotes.isEmpty()) {
             "No active notes"
        } else {
          activeNotes
      }
    }

    fun archiveNote(noteIndex: Int) : Boolean {
        if(isValidIndex(noteIndex)) {
            var note = notes[noteIndex]
            if(!note.isNoteArchived) {
                note.isNoteArchived = true
                return true
            }
        }
        return false
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


    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }





    fun numberOfNotesByPriority(priority : Int): Int {
        //helper method to determine how many notes there are of a specific priority

        var priority1 = 0
        var priority2 = 0
        var priority3 = 0
        var priority4 = 0
        var priority5 = 0

        for(Note in notes) {
            when (Note.notePriority) {
                1 -> priority1++
                2 -> priority2++
                3 -> priority3++
                4 -> priority4++
                5 -> priority5++
            }
        }
        when(priority){
            1 -> return priority1
            2 -> return priority2
            3 -> return priority3
            4 -> return priority4
            5 -> return priority5

        }
        return -1
}
}