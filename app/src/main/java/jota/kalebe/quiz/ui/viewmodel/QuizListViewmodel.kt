package jota.kalebe.quiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.model.QuizHttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizListViewmodel : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    val _list = MutableLiveData<List<Quiz>>()


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
                _list.value = result?.results
                _state.value = State.Loaded(result?.results)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Loaded(val items: List<Quiz>) : State()
        data class Error(val e: Throwable, var hasConsumed: Boolean) : State()
    }
}