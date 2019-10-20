package com.example.fuckinggussit.game

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.fuckinggussit.R
import com.example.fuckinggussit.databinding.GameFragmentBinding
import com.example.fuckinggussit.databinding.TitleFragmentBinding

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )



        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)


        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()

        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })

        viewModel.word.observe(this, Observer { newWord ->
            binding.wordText.text = newWord

        })

        viewModel.gameState.observe(this, Observer { hasEnd ->
            if (hasEnd) {
                gameFinished()
                viewModel.OnGameHasComplated()
            }

        })

        viewModel.currentTime.observe(this, Observer {newTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })
        return binding.root

    }


    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0)
        NavHostFragment.findNavController(this).navigate(action)
    }


}
