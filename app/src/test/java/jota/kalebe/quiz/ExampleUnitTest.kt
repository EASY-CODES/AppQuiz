package jota.kalebe.quiz

import io.mockk.MockKAnnotations
import jota.kalebe.quiz.model.QuizHttp
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {
    // pesquisar html unescape kotlin

    lateinit var viewmodel: QuizListViewmodel

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        viewmodel = QuizListViewmodel()
        Dispatchers.setMain(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    @After
    fun onAfter(){

        Dispatchers.resetMain()
    }

    @Test
    fun `when category equals math`()= runBlocking{
        val category = "19"
        val category_name = "Mathematics"

        viewmodel.loadQuestions(category)
        print(viewmodel._list.value)
        assertEquals(category_name, viewmodel._list.value?.get(0)?.category)

    }



    @Test
    fun `when the size of request equals the 10`() {

        val size_of_a_list = QuizHttp.searchQuestions(
            QuizHttp.AMOUT,
            QuizHttp.GEOGRAFY,
            QuizHttp.EASY,
            QuizHttp.MULTIPLE
        )
        assertEquals(10, size_of_a_list?.results?.size)
    }





}