package jota.kalebe.quiz.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jota.kalebe.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentResultBinding.inflate(inflater, container, false).apply {
        _binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val score = args.totalQuestionsCorrects
        Log.d("score", score.toString())
        binding.questionsResults.text = "${score!!}/10"
        binding.coins.text = "${score*10}"

        binding.btnNewQuiz.setOnClickListener {
            val action =
                ResultFragmentDirections.actionResultFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
}