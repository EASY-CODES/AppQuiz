package jota.kalebe.quiz.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.network.QuizService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewmodel @Inject constructor(private val repository: QuizService) : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state


    fun loadQuestions(category: String, type: String) =
        viewModelScope.launch {
            _state.value = State.Loading

            repository.getAllQuestions(
                "10",
                category,
                "easy",
                type
            ).let { result ->
                if (result.results == null) {
                    _state.value = State.Error(Exception("Error loading questions"), false)
                } else {
                    _state.value = State.Loaded(result.results)
                }
            }


        }


    sealed class State {
        object Loading : State()
        data class Loaded(val items: List<Quiz>) : State()
        data class Error(val e: Throwable, var hasConsumed: Boolean) : State()
    }
}