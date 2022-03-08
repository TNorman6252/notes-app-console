package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class NoteAPITest {

    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var archiveNoteTest: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var alreadyArchivedNoteTest: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var emptyNotes: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var notesArchived: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var notesActive: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var notesPriority: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))
    private var noteNumbers: NoteAPI? = NoteAPI(XMLSerializer(File("notes.xml")))

    @BeforeEach
    fun setup(){
        learnKotlin = Note("Learning Kotlin", 5, "College", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", false)
        swim = Note("Swim - Pool", 3, "Hobby", false)

        //adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)

    }

    @AfterEach
    fun tearDown(){
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedNotes = null
        emptyNotes = null
    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(6, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfNotes())
            assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
        }
    }

    @Nested
    inner class ListNotes {

        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
        }
    }

    @Nested
    inner class ActiveNotes {

        @Test
        fun `listActiveNotes returns No Active Notes message when all arraylist objects have isNoteArchived set to true`() {
            val noteArchivedTest = Note("Test title", 2, "Test category", true)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue( notesArchived!!.add(noteArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listActiveNotes().lowercase().contains("no active notes"))
        }

        @Test
        fun `listAllNotes returns Active Notes when all arrayList objects have isNoteArchived set to false`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.add(noteNotArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listActiveNotes().contains(noteNotArchivedTest.toString()))
        }
    }

    @Nested
    inner class ArchivedNotes {

        @Test
        fun `listArchivedNotes returns No Archived Notes message when all arraylist objects have isNoteArchived set to false`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue( notesArchived!!.add(noteNotArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listArchivedNotes().lowercase().contains("no archived notes"))
        }

        @Test
        fun `listArchivedNotes returns Archived Notes when all arrayList objects have isNoteArchived set to true`() {
            val noteArchivedTest = Note("Test title", 2, "Test category", true)
            assertEquals(0, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.add(noteArchivedTest))
            assertEquals(1, notesArchived!!.numberOfNotes())
            assertTrue(notesArchived!!.listArchivedNotes().contains(noteArchivedTest.toString()))
        }
    }

    @Nested
    inner class NumberOfArchivedNotes {

        @Test
        fun `prints number of Archived Notes`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            val noteArchivedTest = Note("Test title", 2, "Test category", true)

            assertEquals(0, notesArchived!!.numberOfArchivedNotes())
            assertTrue( notesArchived!!.add(noteNotArchivedTest))
            assertTrue( notesArchived!!.add(noteArchivedTest))
            assertEquals(1, notesArchived!!.numberOfArchivedNotes())
        }
    }

    @Nested
    inner class NumberOfActiveNotes {

        @Test
        fun `prints number of Active Notes`() {
            val noteNotArchivedTest = Note("Test title", 2, "Test category", false)
            val noteNotArchivedTest2 = Note("Another Test title", 3, "Test category", false)
            val noteArchivedTest = Note("Test title", 2, "Test category", true)

            assertEquals(0, notesActive!!.numberOfActiveNotes())
            assertTrue( notesActive!!.add(noteNotArchivedTest))
            assertTrue( notesActive!!.add(noteNotArchivedTest2))
            assertTrue( notesActive!!.add(noteArchivedTest))
            assertEquals(2, notesActive!!.numberOfActiveNotes())
        }
    }

    @Nested
    inner class ListNotesByPriority {

        @Test
        fun `prints Notes by selected priority`() {
            val note1 = Note("Test title", 2, "Test category", false)
            val note2 = Note("Another Test title", 5, "Test category", false)
            val note3 = Note("Test title", 2, "Test category", true)

            assertEquals(0, notesPriority!!.numberOfNotes())
            assertTrue( notesPriority!!.add(note1))
            assertTrue( notesPriority!!.add(note2))
            assertTrue( notesPriority!!.add(note3))
            val answer = notesPriority!!.listNotesBySelectedPriority(2)
            assertTrue(answer.contains(note1.toString()))
            assertTrue(!answer.contains(note2.toString()))
            assertTrue(answer.contains(note3.toString()))
        }
    }

    @Nested
    inner class NumberOfNotesByPriority {

        @Test
        fun `prints Note object size categorized by selected priority level`() {
            val note1 = Note("Test title", 2, "Test category", false)
            val note2 = Note("Another Test title", 5, "Test category", false)
            val note3 = Note("Test title", 2, "Test category", true)

            assertEquals(0, noteNumbers!!.numberOfNotes())
            assertTrue( noteNumbers!!.add(note1))
            assertTrue( noteNumbers!!.add(note2))
            assertTrue( noteNumbers!!.add(note3))
            val answer = noteNumbers!!.numberOfNotesByPriority(2)
            assertTrue(answer == 2)
        }
    }

    @Nested
    inner class DeleteNotes {

        @Test
        fun `deleting a Note that does not exist, returns null`() {
            assertNull(emptyNotes!!.deleteNote(0))
            assertNull(populatedNotes!!.deleteNote(-1))
            assertNull(populatedNotes!!.deleteNote(5))
        }

        @Test
        fun `deleting a note that exists delete and returns deleted object`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertEquals(swim, populatedNotes!!.deleteNote(4))
            assertEquals(4, populatedNotes!!.numberOfNotes())
            assertEquals(learnKotlin, populatedNotes!!.deleteNote(0))
            assertEquals(3, populatedNotes!!.numberOfNotes())
        }
    }

    @Nested
    inner class UpdateNotes {
        @Test
        fun `updating a note that does not exist returns false`(){
            assertFalse(populatedNotes!!.updateNote(6, Note("Updating Note", 2, "Work", false)))
            assertFalse(populatedNotes!!.updateNote(-1, Note("Updating Note", 2, "Work", false)))
            assertFalse(emptyNotes!!.updateNote(0, Note("Updating Note", 2, "Work", false)))
        }

        @Test
        fun `updating a note that exists returns true and updates`() {
            //check note 5 exists and check the contents
            assertEquals(swim, populatedNotes!!.findNote(4))
            assertEquals("Swim - Pool", populatedNotes!!.findNote(4)!!.noteTitle)
            assertEquals(3, populatedNotes!!.findNote(4)!!.notePriority)
            assertEquals("Hobby", populatedNotes!!.findNote(4)!!.noteCategory)

            //update note 5 with new information and ensure contents updated successfully
            assertTrue(populatedNotes!!.updateNote(4, Note("Updating Note", 2, "College", false)))
            assertEquals("Updating Note", populatedNotes!!.findNote(4)!!.noteTitle)
            assertEquals(2, populatedNotes!!.findNote(4)!!.notePriority)
            assertEquals("College", populatedNotes!!.findNote(4)!!.noteCategory)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty notes.XML file.
            val storingNotes = NoteAPI(XMLSerializer(File("notes.xml")))
            storingNotes.store()

            //Loading the empty notes.xml file into a new object
            val loadedNotes = NoteAPI(XMLSerializer(File("notes.xml")))
            loadedNotes.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingNotes.numberOfNotes())
            assertEquals(0, loadedNotes.numberOfNotes())
            assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 notes to the notes.XML file.
            val storingNotes = NoteAPI(XMLSerializer(File("notes.xml")))
            storingNotes.add(testApp!!)
            storingNotes.add(swim!!)
            storingNotes.add(summerHoliday!!)
            storingNotes.store()

            //Loading notes.xml into a different collection
            val loadedNotes = NoteAPI(XMLSerializer(File("notes.xml")))
            loadedNotes.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingNotes.numberOfNotes())
            assertEquals(3, loadedNotes.numberOfNotes())
            assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
            assertEquals(storingNotes.findNote(0), loadedNotes.findNote(0))
            assertEquals(storingNotes.findNote(1), loadedNotes.findNote(1))
            assertEquals(storingNotes.findNote(2), loadedNotes.findNote(2))
        }
    }

    @Test
    fun `saving and loading an empty collection in JSON doesn't crash app`() {
        // Saving an empty notes.json file.
        val storingNotes = NoteAPI(JSONSerializer(File("notes.json")))
        storingNotes.store()

        //Loading the empty notes.json file into a new object
        val loadedNotes = NoteAPI(JSONSerializer(File("notes.json")))
        loadedNotes.load()

        //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        assertEquals(0, storingNotes.numberOfNotes())
        assertEquals(0, loadedNotes.numberOfNotes())
        assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
    }

    @Test
    fun `saving and loading an loaded collection in JSON doesn't loose data`() {
        // Storing 3 notes to the notes.json file.
        val storingNotes = NoteAPI(JSONSerializer(File("notes.json")))
        storingNotes.add(testApp!!)
        storingNotes.add(swim!!)
        storingNotes.add(summerHoliday!!)
        storingNotes.store()

        //Loading notes.json into a different collection
        val loadedNotes = NoteAPI(JSONSerializer(File("notes.json")))
        loadedNotes.load()

        //Comparing the source of the notes (storingNotes) with the json loaded notes (loadedNotes)
        assertEquals(3, storingNotes.numberOfNotes())
        assertEquals(3, loadedNotes.numberOfNotes())
        assertEquals(storingNotes.numberOfNotes(), loadedNotes.numberOfNotes())
        assertEquals(storingNotes.findNote(0), loadedNotes.findNote(0))
        assertEquals(storingNotes.findNote(1), loadedNotes.findNote(1))
        assertEquals(storingNotes.findNote(2), loadedNotes.findNote(2))
    }

    @Nested
    inner class ArchiveNote {
        @Test
        fun `adding Note and archiving it`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(0, archiveNoteTest!!.numberOfNotes())
            assertTrue(archiveNoteTest!!.add(newNote))
            assertEquals(1, archiveNoteTest!!.numberOfNotes())
            assertTrue(archiveNoteTest!!.archiveNote(0))
        }

        @Test
        fun `adding Note but it's already archived so a message telling the user is displayed`() {
            val newNote = Note("Study Lambdas", 1, "College", true)
            assertEquals(0, alreadyArchivedNoteTest!!.numberOfNotes())
            assertTrue(alreadyArchivedNoteTest!!.add(newNote))
            assertEquals(1, alreadyArchivedNoteTest!!.numberOfNotes())
            assertFalse(alreadyArchivedNoteTest!!.archiveNote(0))
        }
    }




}