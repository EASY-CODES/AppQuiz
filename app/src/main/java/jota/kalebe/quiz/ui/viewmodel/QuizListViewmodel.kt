package jota.kalebe.quiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import jota.kalebe.quiz.model.Answer
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.model.QuizHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizListViewmodel : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    private val _answer = MutableLiveData<Answer>()
    val answer: LiveData<Answer>
        get() = _answer

    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
        get() = _score

    fun loadQuestions(category: String ) {
        viewModelScope.launch {
            _state.value = State.Loading

            val result = withContext(Dispatchers.IO) {
                QuizHttp.searchQuestions(QuizHttp.AMOUT,
                    category,
                    QuizHttp.EASY,
                    QuizHttp.MULTIPLE)

            }
            if (result?.results == null) {
                _state.value = State.Error(Exception("Error loading questions"), false)
            } else {
                _state.value = State.Loaded(result?.results)
            }
        }
    }

    fun setAnswer(a: Answer){
        _answer.value = a
        println(a.toString())
    }

    fun setScore(){
        _score.value = _score.value?.plus(1)
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val items: List<Quiz>) : State()
        data class Error(val e: Throwable, var hasConsumed: Boolean) : State()
    }
}