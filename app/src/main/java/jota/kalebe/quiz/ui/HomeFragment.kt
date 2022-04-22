package jota.kalebe.quiz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import jota.kalebe.quiz.R
import jota.kalebe.quiz.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBindingItens()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun setBindingItens() {
        binding.itemCategory1.setOnClickListener {
            binding.run {
                //matematics
                val action=HomeFragmentDirections.actionHomeFragmentToQuizFragment("19")
                findNavController().navigate(action)
            }
        }

        binding.itemCategory2.setOnClickListener {
            binding.run {
                //movie
                val action=HomeFragmentDirections.actionHomeFragmentToQuizFragment("11")
                findNavController().navigate(action)
            }
        }

        binding.itemCategory3.setOnClickListener {
            binding.run {
                //geography
                val action=HomeFragmentDirections.actionHomeFragmentToQuizFragment("22")
                findNavController().navigate(action)
            }
        }

        binding.itemCategory4.setOnClickListener {
            binding.run {
                //music
                val action=HomeFragmentDirections.actionHomeFragmentToQuizFragment("10")
                findNavController().navigate(action)
            }
        }

        binding.itemCategory5.setOnClickListener {
            binding.run {
                //science
                val action=HomeFragmentDirections.actionHomeFragmentToQuizFragment("17")
                findNavController().navigate(action)
            }
        }

        binding.itemCategory6.setOnClickListener {
            binding.run {
                //history
                val action=HomeFragmentDirections.actionHomeFragmentToQuizFragment("23")
                findNavController().navigate(action)
            }
        }
    }
}