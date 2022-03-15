import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.Integer.parseInt
import java.lang.System.exit


private val logger = KotlinLogging.logger {}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List notes                |
         > |   3) List Active Notes             |
         > |   4) List Archived Note             |
         > |   5) Number Of Archived Notes
         > |   6) Number Of Active Notes
         > |   7) List Notes by Selected Priority
         > |   8) Number of Notes by Priority
         > |   9) Update Note
         > |   10) Delete Note
         > |   11) Save Notes
         > |   12) Load Notes
         > |   13) Archive Note
         > |   14) Search Note by Title
         > ----------------------------------
         > |   0) Exit Program               |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when(option) {
            1 -> addNote()
            2 -> listNotes()
            3 -> listActiveNotes()
            4 -> listArchivedNotes()
            5 -> numberOfArchivedNotes()
            6 -> numberOfActiveNotes()
            7 -> listNotesBySelectedPriority()
            8 -> numberOfNotesByPriority()
            9 -> updateNote()
            10 -> deleteNote()
            11 -> save()
            12 -> load()
            13 -> archiveNote()
            14 -> searchNoteByTitle()
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while(true)
}

fun addNote() {
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false))

    if(isAdded) {
        println("Added successfully")
    } else {
        println("Add failed")
    }
}

fun listNotes() {
   // logger.info { "listNotes() function invoked" }
    println("NOTE MENU\n")
    println(" 1) List all notes")
    println(" 2) List active notes")
    println(" 3) List archived notes")

    var userChoice = parseInt(readNextLine("Enter choice [1-3]: "))

    when(userChoice) {
        1 -> println(noteAPI.listAllNotes())
        2 -> println(noteAPI.listActiveNotes())
        3 -> println(noteAPI.listArchivedNotes())
        else -> println("Invalid Choice Entered")
    }
}

fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

fun numberOfArchivedNotes() {
    println(noteAPI.numberOfArchivedNotes())
}

fun numberOfActiveNotes() {
    println(noteAPI.numberOfActiveNotes())
}

fun listNotesBySelectedPriority() {
    var notePriority = parseInt(readNextLine("Enter Note Priority Level: "))
    println(noteAPI.listNotesBySelectedPriority(notePriority))
}

fun numberOfNotesByPriority() {
    var priorityLevel = parseInt(readNextLine("Enter priority level: "))
    println(noteAPI.numberOfNotesByPriority(priorityLevel))
}

fun updateNote() {
   // logger.info { "updateNote() function invoked" }
    listNotes()
    if(noteAPI.numberOfNotes() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if(noteAPI.isValidIndex(indexToUpdate)) {
            val noteTitle = readNextLine("Enter a title for the note: ")
            val notePriority = parseInt(readNextLine("Enter a priority (1-low, 2, 3, 4, 5-high): "))
            val noteCategory = readNextLine("Enter a category for the note: ")

            if(noteAPI.updateNote(indexToUpdate, Note(noteTitle, notePriority, noteCategory, false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        }
    }
}

fun deleteNote() {
   // logger.info { "deleteNote() function invoked" }
    listNotes()
    if(noteAPI.numberOfNotes() > 0) {
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")

        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if(noteToDelete != null) {
            println("Delete Successful! Deleted Note: ${noteToDelete.noteTitle}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveNote() {
    println(noteAPI.listActiveNotes() + "\n")
    if(noteAPI.numberOfNotes() > 0) {
        var noteIndex = parseInt(readNextLine("Enter index number of active note you want to update: "))
        var result = noteAPI.archiveNote(noteIndex)

        if (result) {
            println("Note has been Successfully Archived")
        } else {
            println("Note Archiving NOT Successful")
        }
    }
}

fun searchNoteByTitle() {
    var searchTitle = readNextLine("Enter title to search Notes by here: ")
    var notesByTitle = noteAPI.searchByTitle(searchTitle)

    if(notesByTitle.isEmpty()) {
        println("No notes found")
    }
    else{
        println(notesByTitle)
    }
}

fun save() {
    try {
        noteAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    println("Exiting...bye")
    exit(0)
}