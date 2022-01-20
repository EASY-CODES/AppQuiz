package jota.kalebe.quiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
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

    private val _listAnswer = MutableLiveData<ArrayList<Answer>>()
    val listAnswer: LiveData<ArrayList<Answer>>
        get() = _listAnswer

    private val _current = MutableLiveData<Int>()
    val current: LiveData<Int>
        get() = _current


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

    fun setAnswer(answer: Answer){
        _listAnswer.value?.add(answer)
    }

    fun updateCurrentItem(current: Int) {
        _current.value = current
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val items: List<Quiz>) : State()
        data class Error(val e: Throwable, var hasConsumed: Boolean) : State()
    }
}