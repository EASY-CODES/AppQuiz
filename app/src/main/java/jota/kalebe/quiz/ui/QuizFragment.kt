package jota.kalebe.quiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import jota.kalebe.quiz.R
import jota.kalebe.quiz.databinding.FragmentQuizBinding
import jota.kalebe.quiz.model.Quiz
import jota.kalebe.quiz.ui.viewmodel.QuizListViewmodel


@AndroidEntryPoint
class QuizFragment : Fragment(), View.OnClickListener {

    val viewModel : QuizListViewmodel by viewModels()

    private lateinit var list: List<Quiz>
    private var _binding: FragmentQuizBinding? = null
    private val binding: FragmentQuizBinding get() = _binding!!

    private var mCurrentPosition: Int = 1
    private var selectedOptionPosition: Int = 0
    private var correctAnswers: Int = 0

    private val args: QuizFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentQuizBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category

        viewModel.state.observe(this,{ state ->
            when (state) {

                is QuizListViewmodel.State.Loading -> {
                    binding.vwLoading.root.visibility = View.VISIBLE
                }

                is QuizListViewmodel.State.Loaded -> {
                    binding.vwLoading.root.visibility = View.GONE
                    list = state.items
                    setQuestion()
                    setListeners()

                }

                is QuizListViewmodel.State.Error -> {
                    binding.vwLoading.root.visibility = View.GONE
                    if (!state.hasConsumed) {
                        state.hasConsumed = true
                        Toast.makeText(requireContext(), R.string.error_loading, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })

        viewModel.loadQuestions(category, "multiple")

    }

    fun setQuestion() {
        val question = list[mCurrentPosition - 1]

        defaultOptionsView()

        if (mCurrentPosition == list.size) {
            binding.btNext.text = "Finish"
        } else {
            binding.btNext.text = "Submit"
        }

        if (question.type == "boolean")
            binding.apply {
                titleCategory.text = question.category
                questionN.text = "Question ${mCurrentPosition}"
                questionT.text = question.question
                item1.text = question.incorrect_answers[0]
                item2.text = question.correct_answer
                item3.visibility = View.GONE
                item4.visibility = View.GONE
            }
        else {
            binding.apply {
                titleCategory.text = question.category
                questionN.text = "Question ${mCurrentPosition}"
                questionT.text = question.question
                item1.text = question.incorrect_answers[0]
                item2.text = question.incorrect_answers[1]
                item3.text = question.incorrect_answers[2]
                item4.text = question.correct_answer

                item3.visibility = View.VISIBLE
                item4.visibility = View.VISIBLE
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        options.add(0, binding.item1)
        options.add(1, binding.item2)
        options.add(2, binding.item3)
        options.add(3, binding.item4)

        for (option in options) {
            option.setBackgroundResource(R.drawable.bg_item_default)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.item1 -> {
                selectedOptionView(binding.item1, 1)
            }
            R.id.item2 -> {
                selectedOptionView(binding.item2, 2)
            }
            R.id.item3 -> {
                selectedOptionView(binding.item3, 3)
            }
            R.id.item4 -> {
                selectedOptionView(binding.item4, 4)
            }
            R.id.btQuit -> {
                val action =
                    QuizFragmentDirections.actionQuizFragmentToHomeFragment()
                findNavController().navigate(action)
            }
            R.id.btNext -> {
                if (selectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= list.size -> {
                            setQuestion()
                            binding.scrollView.scrollTo(0, 0)
                        }
                        else -> {
                            val action =
                                QuizFragmentDirections.actionQuizFragmentToResultFragment(
                                    correctAnswers
                                )
                            findNavController().navigate(action)
                        }
                    }

                } else {
                    val question = list?.get(mCurrentPosition - 1)

                    if (4 != selectedOptionPosition) {
                        answerView(selectedOptionPosition, R.drawable.bg_alternative_incorrect)
                    } else {
                        correctAnswers++
                    }

                    answerView(4, R.drawable.bg_alternative_correct)

                    if (mCurrentPosition == list.size) {
                        binding.btNext.text = "Finish"
                    } else {
                        binding.btNext.text = "Next"
                    }

                    selectedOptionPosition = 0
                }
            }
        }
    }

    private fun setListeners() {
        binding.item1.setOnClickListener(this)
        binding.item2.setOnClickListener(this)
        binding.item3.setOnClickListener(this)
        binding.item4.setOnClickListener(this)
        binding.btQuit.setOnClickListener(this)
        binding.btNext.setOnClickListener(this)
    }

    private fun selectedOptionView(tv: TextView, selectedOption: Int) {
        defaultOptionsView()
        selectedOptionPosition = selectedOption

//        tv.setTextColor(Color.parseColor("#243BB8"))
//        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background =
            context?.let { ContextCompat.getDrawable(it, R.drawable.bg_alternative_selected) }
    }


    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.item1.background = context?.let {
                    ContextCompat.getDrawable(it, drawableView)
                }
            }
            2 -> {
                binding.item2.background = context?.let {
                    ContextCompat.getDrawable(it, drawableView)
                }
            }
            3 -> {
                binding.item3.background = context?.let {
                    ContextCompat.getDrawable(it, drawableView)
                }
            }
            4 -> {
                binding.item4.background = context?.let {
                    ContextCompat.getDrawable(it, drawableView)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}